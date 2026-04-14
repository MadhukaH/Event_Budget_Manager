package com.budget.manager.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.ExistingWorkPolicy
import androidx.work.WorkManager
import com.budget.manager.data.model.Workspace
import com.budget.manager.data.repository.ExpenseRepository
import com.budget.manager.data.repository.WorkspaceRepository
import com.budget.manager.util.NetworkObserver
import com.budget.manager.worker.BudgetSyncWorker
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

data class WorkspaceWithTotal(
    val workspace: Workspace,
    val totalSpent: Double = 0.0
)

data class HomeUiState(
    val workspaces: List<WorkspaceWithTotal> = emptyList(),
    val isLoading: Boolean = true,
    val showCreateDialog: Boolean = false,
    val isOnline: Boolean = false,
    val errorMessage: String? = null
)

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val workspaceRepository: WorkspaceRepository,
    private val expenseRepository: ExpenseRepository,
    private val networkObserver: NetworkObserver,
    private val workManager: WorkManager
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        loadWorkspaces()
        observeNetwork()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    private fun loadWorkspaces() {
        viewModelScope.launch {
            workspaceRepository.getAllWorkspaces()
                .flatMapLatest { workspaces ->
                    if (workspaces.isEmpty()) {
                        flowOf(emptyList())
                    } else {
                        val totalFlows = workspaces.map { workspace ->
                            expenseRepository.getTotalSpending(workspace.id)
                                .map { total -> WorkspaceWithTotal(workspace, total ?: 0.0) }
                        }
                        combine(totalFlows) { it.toList() }
                    }
                }
                .catch { e ->
                    _uiState.update { it.copy(isLoading = false, errorMessage = e.message) }
                }
                .collect { workspacesWithTotals ->
                    _uiState.update {
                        it.copy(workspaces = workspacesWithTotals, isLoading = false)
                    }
                }
        }
    }

    /**
     * Observe network connectivity. When we come back online,
     * trigger an immediate background sync of pending local changes.
     */
    private fun observeNetwork() {
        viewModelScope.launch {
            networkObserver.isConnected.collect { isOnline ->
                _uiState.update { it.copy(isOnline = isOnline) }
                if (isOnline) {
                    triggerImmediateSync()
                }
            }
        }
    }

    /** Enqueues an expedited one-time sync work request */
    fun triggerImmediateSync() {
        workManager.enqueueUniqueWork(
            BudgetSyncWorker.WORK_NAME_ONE_TIME,
            ExistingWorkPolicy.REPLACE,
            BudgetSyncWorker.oneTimeWorkRequest()
        )
    }

    fun showCreateDialog() = _uiState.update { it.copy(showCreateDialog = true) }

    fun hideCreateDialog() = _uiState.update { it.copy(showCreateDialog = false) }

    fun createWorkspace(name: String, description: String, totalBudget: Double, colorIndex: Int) {
        if (name.isBlank()) return
        viewModelScope.launch {
            workspaceRepository.createWorkspace(
                Workspace(
                    name = name.trim(),
                    description = description.trim(),
                    totalBudget = totalBudget,
                    colorIndex = colorIndex
                )
            )
            hideCreateDialog()
            // Kick off sync immediately if online
            if (_uiState.value.isOnline) triggerImmediateSync()
        }
    }

    fun deleteWorkspace(workspace: Workspace) {
        viewModelScope.launch {
            workspaceRepository.deleteWorkspace(workspace)
            if (_uiState.value.isOnline) triggerImmediateSync()
        }
    }

    fun clearError() = _uiState.update { it.copy(errorMessage = null) }
}
