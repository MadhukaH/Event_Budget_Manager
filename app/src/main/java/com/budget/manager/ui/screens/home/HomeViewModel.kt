package com.budget.manager.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.ExistingWorkPolicy
import androidx.work.WorkManager
import com.budget.manager.data.model.Workspace
import com.budget.manager.data.repository.ExpenseRepository
import com.budget.manager.data.repository.GrantRepository
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

data class GrantState(
    val totalGrant: Double = 0.0,
    val totalAllocated: Double = 0.0   // sum of all workspace budgets
) {
    val remaining: Double get() = totalGrant - totalAllocated
    val hasGrant: Boolean get() = totalGrant > 0.0
    val usagePercent: Float
        get() = if (totalGrant > 0) (totalAllocated / totalGrant).coerceIn(0.0, 1.0).toFloat() else 0f
}

data class HomeUiState(
    val workspaces: List<WorkspaceWithTotal> = emptyList(),
    val isLoading: Boolean = true,
    val showCreateDialog: Boolean = false,
    val showGrantDialog: Boolean = false,
    val isOnline: Boolean = false,
    val errorMessage: String? = null,
    val grantState: GrantState = GrantState()
)

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val workspaceRepository: WorkspaceRepository,
    private val expenseRepository: ExpenseRepository,
    private val grantRepository: GrantRepository,
    private val networkObserver: NetworkObserver,
    private val workManager: WorkManager
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        loadData()
        observeNetwork()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    private fun loadData() {
        // Merge workspace list + grant settings into one reactive stream
        viewModelScope.launch {
            combine(
                workspaceRepository.getAllWorkspaces()
                    .flatMapLatest { workspaces ->
                        if (workspaces.isEmpty()) flowOf(emptyList())
                        else combine(
                            workspaces.map { ws ->
                                expenseRepository.getTotalSpending(ws.id)
                                    .map { WorkspaceWithTotal(ws, it ?: 0.0) }
                            }
                        ) { it.toList() }
                    },
                grantRepository.getGrantSettings()
            ) { workspacesWithTotals, grant ->
                val totalAllocated = workspacesWithTotals.sumOf { it.workspace.totalBudget }
                val grantState = GrantState(
                    totalGrant = grant?.totalGrant ?: 0.0,
                    totalAllocated = totalAllocated
                )
                Pair(workspacesWithTotals, grantState)
            }
            .catch { e ->
                _uiState.update { it.copy(isLoading = false, errorMessage = e.message) }
            }
            .collect { (workspacesWithTotals, grantState) ->
                _uiState.update {
                    it.copy(
                        workspaces = workspacesWithTotals,
                        grantState = grantState,
                        isLoading = false
                    )
                }
            }
        }
    }

    private fun observeNetwork() {
        viewModelScope.launch {
            networkObserver.isConnected.collect { isOnline ->
                _uiState.update { it.copy(isOnline = isOnline) }
                if (isOnline) triggerImmediateSync()
            }
        }
    }

    fun triggerImmediateSync() {
        workManager.enqueueUniqueWork(
            BudgetSyncWorker.WORK_NAME_ONE_TIME,
            ExistingWorkPolicy.REPLACE,
            BudgetSyncWorker.oneTimeWorkRequest()
        )
    }

    fun showCreateDialog() = _uiState.update { it.copy(showCreateDialog = true) }
    fun hideCreateDialog() = _uiState.update { it.copy(showCreateDialog = false) }
    fun showGrantDialog() = _uiState.update { it.copy(showGrantDialog = true) }
    fun hideGrantDialog() = _uiState.update { it.copy(showGrantDialog = false) }

    fun saveGrantAmount(amount: Double) {
        viewModelScope.launch {
            grantRepository.setTotalGrant(amount)
            hideGrantDialog()
            if (_uiState.value.isOnline) triggerImmediateSync()
        }
    }

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
