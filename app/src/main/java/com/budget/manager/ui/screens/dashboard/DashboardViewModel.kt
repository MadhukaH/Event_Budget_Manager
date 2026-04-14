package com.budget.manager.ui.screens.dashboard

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.budget.manager.data.local.dao.CategoryTotal
import com.budget.manager.data.model.Workspace
import com.budget.manager.data.repository.ExpenseRepository
import com.budget.manager.data.repository.WorkspaceRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

data class DashboardUiState(
    val workspace: Workspace? = null,
    val categoryTotals: List<CategoryTotal> = emptyList(),
    val totalSpent: Double = 0.0,
    val isLoading: Boolean = true
)

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val workspaceRepository: WorkspaceRepository,
    private val expenseRepository: ExpenseRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val workspaceId: Long = checkNotNull(savedStateHandle["workspaceId"])

    val uiState: StateFlow<DashboardUiState> = combine(
        workspaceRepository.getWorkspaceById(workspaceId),
        expenseRepository.getCategoryTotals(workspaceId),
        expenseRepository.getTotalSpending(workspaceId)
    ) { workspace, categoryTotals, total ->
        DashboardUiState(
            workspace = workspace,
            categoryTotals = categoryTotals,
            totalSpent = total ?: 0.0,
            isLoading = false
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = DashboardUiState()
    )
}
