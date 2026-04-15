package com.budget.manager.ui.screens.expense

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.budget.manager.data.model.Expense
import com.budget.manager.data.repository.ExpenseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject
import androidx.work.ExistingWorkPolicy
import androidx.work.WorkManager
import com.budget.manager.util.NetworkObserver
import com.budget.manager.worker.BudgetSyncWorker

data class AddExpenseUiState(
    val selectedCategory: String = "",
    val amountText: String = "",
    val note: String = "",
    val isEditMode: Boolean = false,
    val isSaved: Boolean = false,
    val categoryError: Boolean = false,
    val amountError: Boolean = false,
    val isLoading: Boolean = false
)

@HiltViewModel
class AddExpenseViewModel @Inject constructor(
    private val expenseRepository: ExpenseRepository,
    savedStateHandle: SavedStateHandle,
    private val workManager: WorkManager,
    private val networkObserver: NetworkObserver
) : ViewModel() {

    private val workspaceId: Long = checkNotNull(savedStateHandle["workspaceId"])
    private val expenseId: Long? = savedStateHandle.get<Long>("expenseId")?.takeIf { it != -1L }

    private val _uiState = MutableStateFlow(AddExpenseUiState())
    val uiState: StateFlow<AddExpenseUiState> = _uiState.asStateFlow()

    init {
        expenseId?.let { loadExpense(it) }
    }

    private fun loadExpense(id: Long) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            val expense = expenseRepository.getExpenseById(id)
            expense?.let { e ->
                _uiState.update {
                    it.copy(
                        selectedCategory = e.category,
                        amountText = e.amount.toString(),
                        note = e.note,
                        isEditMode = true,
                        isLoading = false
                    )
                }
            } ?: _uiState.update { it.copy(isLoading = false) }
        }
    }

    fun setCategory(category: String) {
        _uiState.update { it.copy(selectedCategory = category, categoryError = false) }
    }

    fun setAmount(amount: String) {
        _uiState.update { it.copy(amountText = amount, amountError = false) }
    }

    fun setNote(note: String) {
        _uiState.update { it.copy(note = note) }
    }

    fun saveExpense() {
        val state = _uiState.value
        var hasError = false

        if (state.selectedCategory.isBlank()) {
            _uiState.update { it.copy(categoryError = true) }
            hasError = true
        }

        val amount = state.amountText.toDoubleOrNull()
        if (amount == null || amount <= 0) {
            _uiState.update { it.copy(amountError = true) }
            hasError = true
        }

        if (hasError) return

        viewModelScope.launch {
            val expense = Expense(
                id = expenseId ?: 0L,
                workspaceId = workspaceId,
                category = state.selectedCategory,
                amount = amount!!,
                note = state.note
            )
            expenseRepository.addExpense(expense)
            _uiState.update { it.copy(isSaved = true) }
            
            if (networkObserver.isCurrentlyConnected()) {
                workManager.enqueueUniqueWork(
                    BudgetSyncWorker.WORK_NAME_ONE_TIME,
                    ExistingWorkPolicy.REPLACE,
                    BudgetSyncWorker.oneTimeWorkRequest()
                )
            }
        }
    }
}
