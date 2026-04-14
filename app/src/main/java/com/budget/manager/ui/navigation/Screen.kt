package com.budget.manager.ui.navigation

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object WorkspaceDetail : Screen("workspace/{workspaceId}") {
        fun createRoute(workspaceId: Long) = "workspace/$workspaceId"
    }
    object AddExpense : Screen("workspace/{workspaceId}/add_expense?expenseId={expenseId}") {
        fun createRoute(workspaceId: Long, expenseId: Long = -1L) =
            "workspace/$workspaceId/add_expense?expenseId=$expenseId"
    }
    object Dashboard : Screen("workspace/{workspaceId}/dashboard") {
        fun createRoute(workspaceId: Long) = "workspace/$workspaceId/dashboard"
    }
}
