package com.budget.manager.ui.screens.workspace

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.budget.manager.data.model.Expense
import com.budget.manager.data.model.Workspace
import com.budget.manager.data.repository.ExpenseRepository
import com.budget.manager.data.repository.WorkspaceRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject
import androidx.work.ExistingWorkPolicy
import androidx.work.WorkManager
import com.budget.manager.util.NetworkObserver
import com.budget.manager.worker.BudgetSyncWorker

data class WorkspaceUiState(
    val workspace: Workspace? = null,
    val expenses: List<Expense> = emptyList(),
    val totalSpent: Double = 0.0,
    val isLoading: Boolean = true,
    val errorMessage: String? = null
)

@HiltViewModel
class WorkspaceViewModel @Inject constructor(
    private val workspaceRepository: WorkspaceRepository,
    private val expenseRepository: ExpenseRepository,
    savedStateHandle: SavedStateHandle,
    private val workManager: WorkManager,
    private val networkObserver: NetworkObserver
) : ViewModel() {

    private val workspaceId: Long = checkNotNull(savedStateHandle["workspaceId"])

    private val _uiState = MutableStateFlow(WorkspaceUiState())
    val uiState: StateFlow<WorkspaceUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            combine(
                workspaceRepository.getWorkspaceById(workspaceId),
                expenseRepository.getExpensesByWorkspace(workspaceId),
                expenseRepository.getTotalSpending(workspaceId)
            ) { workspace, expenses, total ->
                WorkspaceUiState(
                    workspace = workspace,
                    expenses = expenses,
                    totalSpent = total ?: 0.0,
                    isLoading = false
                )
            }.catch { e ->
                _uiState.update { it.copy(isLoading = false, errorMessage = e.message) }
            }.collect { state ->
                _uiState.value = state
            }
        }
    }

    fun deleteExpense(expense: Expense) {
        viewModelScope.launch {
            expenseRepository.deleteExpense(expense)
            if (networkObserver.isCurrentlyConnected()) {
                workManager.enqueueUniqueWork(
                    BudgetSyncWorker.WORK_NAME_ONE_TIME,
                    ExistingWorkPolicy.REPLACE,
                    BudgetSyncWorker.oneTimeWorkRequest()
                )
            }
        }
    }

    fun clearError() = _uiState.update { it.copy(errorMessage = null) }
}
