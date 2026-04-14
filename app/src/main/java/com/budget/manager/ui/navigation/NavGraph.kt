package com.budget.manager.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.budget.manager.ui.screens.dashboard.DashboardScreen
import com.budget.manager.ui.screens.expense.AddExpenseScreen
import com.budget.manager.ui.screens.home.HomeScreen
import com.budget.manager.ui.screens.workspace.WorkspaceScreen

@Composable
fun BudgetNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {
        composable(Screen.Home.route) {
            HomeScreen(
                onWorkspaceClick = { workspaceId ->
                    navController.navigate(Screen.WorkspaceDetail.createRoute(workspaceId))
                }
            )
        }

        composable(
            route = Screen.WorkspaceDetail.route,
            arguments = listOf(navArgument("workspaceId") { type = NavType.LongType })
        ) { backStackEntry ->
            val workspaceId = backStackEntry.arguments?.getLong("workspaceId") ?: return@composable
            WorkspaceScreen(
                workspaceId = workspaceId,
                onNavigateBack = { navController.popBackStack() },
                onAddExpense = {
                    navController.navigate(Screen.AddExpense.createRoute(workspaceId))
                },
                onEditExpense = { expenseId ->
                    navController.navigate(Screen.AddExpense.createRoute(workspaceId, expenseId))
                },
                onDashboard = {
                    navController.navigate(Screen.Dashboard.createRoute(workspaceId))
                }
            )
        }

        composable(
            route = Screen.AddExpense.route,
            arguments = listOf(
                navArgument("workspaceId") { type = NavType.LongType },
                navArgument("expenseId") {
                    type = NavType.LongType
                    defaultValue = -1L
                }
            )
        ) { backStackEntry ->
            val workspaceId = backStackEntry.arguments?.getLong("workspaceId") ?: return@composable
            val expenseId = backStackEntry.arguments?.getLong("expenseId") ?: -1L
            AddExpenseScreen(
                workspaceId = workspaceId,
                expenseId = expenseId.takeIf { it != -1L },
                onNavigateBack = { navController.popBackStack() }
            )
        }

        composable(
            route = Screen.Dashboard.route,
            arguments = listOf(navArgument("workspaceId") { type = NavType.LongType })
        ) { backStackEntry ->
            val workspaceId = backStackEntry.arguments?.getLong("workspaceId") ?: return@composable
            DashboardScreen(
                workspaceId = workspaceId,
                onNavigateBack = { navController.popBackStack() }
            )
        }
    }
}
