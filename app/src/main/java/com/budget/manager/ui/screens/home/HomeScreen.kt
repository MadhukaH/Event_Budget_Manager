package com.budget.manager.ui.screens.home

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.compose.ui.platform.LocalContext
import android.widget.Toast
import kotlinx.coroutines.launch
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import com.budget.manager.util.PdfGenerator
import com.budget.manager.data.model.SyncStatus
import com.budget.manager.data.model.Workspace
import com.budget.manager.ui.components.DeleteConfirmationDialog
import com.budget.manager.ui.components.EmptyStateView
import com.budget.manager.ui.theme.WorkspaceColors
import java.text.NumberFormat
import java.util.*

// ─── Premium Colors ───────────────────────────────────────────────────────────
private val GrantGreen = Color(0xFF00C853)
private val GrantAmber = Color(0xFFFFAB00)
private val GrantRed   = Color(0xFFFF1744)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onWorkspaceClick: (Long) -> Unit,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    var isGeneratingPdf by remember { mutableStateOf(false) }
    var workspaceToDelete by remember { mutableStateOf<Workspace?>(null) }

    Scaffold(
        topBar = {
            LargeTopAppBar(
                title = {
                    Column {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Text(
                                text = "කොවුල් වසත් නද",
                                style = MaterialTheme.typography.headlineLarge,
                                fontWeight = FontWeight.ExtraBold
                            )
                            Box(
                                modifier = Modifier
                                    .size(10.dp)
                                    .clip(CircleShape)
                                    .background(
                                        if (uiState.isOnline) Color(0xFF4CAF50) else Color(0xFFBDBDBD)
                                    )
                            )
                        }
                        Text(
                            text = if (uiState.isOnline)
                                "${uiState.workspaces.size} workspace${if (uiState.workspaces.size != 1) "s" else ""} · Synced"
                            else
                                "${uiState.workspaces.size} workspace${if (uiState.workspaces.size != 1) "s" else ""} · Offline",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                },
                actions = {
                    if (isGeneratingPdf) {
                        CircularProgressIndicator(
                            modifier = Modifier
                                .padding(horizontal = 16.dp)
                                .size(24.dp),
                            strokeWidth = 2.dp
                        )
                    } else {
                        IconButton(onClick = {
                            isGeneratingPdf = true
                            scope.launch(Dispatchers.IO) {
                                val expenses = viewModel.getAllExpensesOnce()
                                val uri = PdfGenerator.generateAllExpensesReport(
                                    context = context,
                                    expenses = expenses,
                                    grantTotal = uiState.grantState.totalGrant,
                                    grantSpent = uiState.grantState.totalSpent
                                )
                                withContext(Dispatchers.Main) {
                                    isGeneratingPdf = false
                                    if (uri != null) {
                                        PdfGenerator.viewPdf(context, uri)
                                    } else {
                                        Toast.makeText(context, "Failed to generate system report", Toast.LENGTH_SHORT).show()
                                    }
                                }
                            }
                        }) {
                            Icon(
                                Icons.Default.Share,
                                contentDescription = "Generate PDF Report",
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                    // Grant Fund button
                    IconButton(onClick = { viewModel.showGrantDialog() }) {
                        Icon(
                            Icons.Default.AccountBalance,
                            contentDescription = "Set Grant Fund",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                    if (uiState.isOnline) {
                        IconButton(onClick = { viewModel.triggerImmediateSync() }) {
                            Icon(
                                Icons.Default.Sync,
                                contentDescription = "Sync now",
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                },
                colors = TopAppBarDefaults.largeTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            )
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = { viewModel.showCreateDialog() },
                icon = { Icon(Icons.Default.Add, contentDescription = "Add") },
                text = { Text("New Workspace") },
                containerColor = MaterialTheme.colorScheme.primary
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            if (uiState.isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            } else {
                LazyColumn(
                    contentPadding = PaddingValues(
                        start = 16.dp,
                        end = 16.dp,
                        top = 8.dp,
                        bottom = 96.dp
                    ),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    // ─── Grant Wallet Card ─────────────────────────────────────
                    item {
                        GrantWalletCard(
                            grantState = uiState.grantState,
                            onEditClick = { viewModel.showGrantDialog() }
                        )
                    }

                    // ─── Workspace List ────────────────────────────────────────
                    if (uiState.workspaces.isEmpty()) {
                        item {
                            EmptyStateView(
                                icon = Icons.Default.FolderOpen,
                                title = "No Workspaces Yet",
                                subtitle = "Create a workspace to start tracking your event budget",
                                modifier = Modifier.padding(top = 40.dp)
                            )
                        }
                    } else {
                        items(
                            items = uiState.workspaces,
                            key = { it.workspace.id }
                        ) { item ->
                            WorkspaceCard(
                                workspaceWithTotal = item,
                                onClick = { onWorkspaceClick(item.workspace.id) },
                                onDelete = { workspaceToDelete = item.workspace }
                            )
                        }
                    }
                }
            }
        }
    }

    // ─── Grant Dialog ──────────────────────────────────────────────────────────
    if (uiState.showGrantDialog) {
        SetGrantDialog(
            currentGrant = uiState.grantState.totalGrant,
            remaining = uiState.grantState.remaining,
            onDismiss = { viewModel.hideGrantDialog() },
            onSave = { viewModel.saveGrantAmount(it) }
        )
    }

    // ─── Create Workspace Dialog ───────────────────────────────────────────────
    if (uiState.showCreateDialog) {
        CreateWorkspaceDialog(
            onDismiss = { viewModel.hideCreateDialog() },
            remainingGrant = uiState.grantState.remaining,
            hasGrant = uiState.grantState.hasGrant,
            onCreate = { name, desc, budget, colorIdx ->
                viewModel.createWorkspace(name, desc, budget, colorIdx)
            }
        )
    }

    workspaceToDelete?.let { workspace ->
        DeleteConfirmationDialog(
            title = "Delete Workspace",
            message = "\"${workspace.name}\" and all its expenses will be permanently deleted.",
            onConfirm = {
                viewModel.deleteWorkspace(workspace)
                workspaceToDelete = null
            },
            onDismiss = { workspaceToDelete = null }
        )
    }

    uiState.errorMessage?.let {
        LaunchedEffect(it) { viewModel.clearError() }
    }
}

// ─── Grant Wallet Card ────────────────────────────────────────────────────────

@Composable
fun GrantWalletCard(
    grantState: GrantState,
    onEditClick: () -> Unit
) {
    val remainingColor = when {
        !grantState.hasGrant -> MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
        grantState.usagePercent >= 0.9f -> GrantRed
        grantState.usagePercent >= 0.7f -> GrantAmber
        else -> GrantGreen
    }

    val animatedProgress by animateFloatAsState(
        targetValue = grantState.usagePercent,
        animationSpec = tween(900, easing = EaseOutCubic),
        label = "grantProgress"
    )

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(8.dp, RoundedCornerShape(24.dp)),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    brush = Brush.horizontalGradient(
                        colors = listOf(
                            MaterialTheme.colorScheme.primaryContainer,
                            MaterialTheme.colorScheme.secondaryContainer
                        )
                    )
                )
        ) {
            Column(modifier = Modifier.padding(20.dp)) {
                // Header row
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(36.dp)
                                .clip(CircleShape)
                                .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.15f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                Icons.Default.AccountBalance,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                        Text(
                            "Grant Fund",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    }
                    IconButton(
                        onClick = onEditClick,
                        modifier = Modifier
                            .size(36.dp)
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.12f))
                    ) {
                        Icon(
                            Icons.Default.Edit,
                            contentDescription = "Edit Grant",
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Main figures
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    // Total Grant
                    Column {
                        Text(
                            "Total Grant",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.7f)
                        )
                        Text(
                            text = if (grantState.hasGrant) formatCurrency(grantState.totalGrant)
                                   else "Not Set",
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.ExtraBold,
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    }

                    // Remaining
                    Column(horizontalAlignment = Alignment.End) {
                        Text(
                            "Remaining",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.7f)
                        )
                        Text(
                            text = if (grantState.hasGrant) formatCurrency(grantState.remaining)
                                   else "—",
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.ExtraBold,
                            color = remainingColor
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Distribution bar
                if (grantState.hasGrant) {
                    Column {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                "Spent from Grant",
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.7f)
                            )
                            Text(
                                text = "${(grantState.usagePercent * 100).toInt()}%",
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.Bold,
                                color = remainingColor
                            )
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                        LinearProgressIndicator(
                            progress = { animatedProgress },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(8.dp)
                                .clip(RoundedCornerShape(4.dp)),
                            color = remainingColor,
                            trackColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                            strokeCap = StrokeCap.Round
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = "${formatCurrency(grantState.totalSpent)} spent across workspaces",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.6f)
                        )
                    }
                } else {
                    // Prompt to set grant
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(12.dp))
                            .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.08f))
                            .clickable { onEditClick() }
                            .padding(12.dp),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            Icons.Default.AddCircleOutline,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            "Tap to set your total grant fund",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }
        }
    }
}

// ─── Set Grant Dialog ─────────────────────────────────────────────────────────

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SetGrantDialog(
    currentGrant: Double,
    remaining: Double,
    onDismiss: () -> Unit,
    onSave: (Double) -> Unit
) {
    var amountText by remember {
        mutableStateOf(if (currentGrant > 0.0) currentGrant.toLong().toString() else "")
    }
    var error by remember { mutableStateOf(false) }

    AlertDialog(
        onDismissRequest = onDismiss,
        icon = {
            Icon(
                Icons.Default.AccountBalance,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(28.dp)
            )
        },
        title = {
            Text(
                "Grant Fund",
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.titleLarge
            )
        },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                Text(
                    "Set the total available grant money. Workspace budgets will be distributed from this pool.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                OutlinedTextField(
                    value = amountText,
                    onValueChange = { amountText = it; error = false },
                    label = { Text("Total Grant Amount (LKR)") },
                    placeholder = { Text("e.g. 500000") },
                    prefix = { Text("Rs. ") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                    singleLine = true,
                    isError = error,
                    supportingText = if (error) {
                        { Text("Please enter a valid amount") }
                    } else null,
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(14.dp)
                )
                if (currentGrant > 0.0) {
                    Surface(
                        shape = RoundedCornerShape(10.dp),
                        color = MaterialTheme.colorScheme.secondaryContainer
                    ) {
                        Row(
                            modifier = Modifier.padding(10.dp),
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                Icons.Default.Info,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.onSecondaryContainer,
                                modifier = Modifier.size(16.dp)
                            )
                            Text(
                                "Current remaining: ${formatCurrency(remaining)}",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSecondaryContainer
                            )
                        }
                    }
                }
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    val amount = amountText.toDoubleOrNull()
                    if (amount == null || amount < 0) {
                        error = true
                    } else {
                        onSave(amount)
                    }
                },
                shape = RoundedCornerShape(12.dp)
            ) {
                Icon(Icons.Default.Save, contentDescription = null, modifier = Modifier.size(16.dp))
                Spacer(modifier = Modifier.width(6.dp))
                Text("Save Grant")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Cancel") }
        }
    )
}

// ─── Workspace Card ───────────────────────────────────────────────────────────

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WorkspaceCard(
    workspaceWithTotal: WorkspaceWithTotal,
    onClick: () -> Unit,
    onDelete: () -> Unit
) {
    val workspace = workspaceWithTotal.workspace
    val totalSpent = workspaceWithTotal.totalSpent
    val accentColor = WorkspaceColors[workspace.colorIndex % WorkspaceColors.size]
    val progress = if (workspace.totalBudget > 0) (totalSpent / workspace.totalBudget).coerceIn(0.0, 1.0) else 0.0
    val progressColor = when {
        progress >= 0.9 -> MaterialTheme.colorScheme.error
        progress >= 0.7 -> Color(0xFFFF8F00)
        else -> MaterialTheme.colorScheme.primary
    }
    val isPendingSync = workspace.syncStatus != SyncStatus.SYNCED

    Card(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp)
                    .background(
                        brush = Brush.horizontalGradient(
                            colors = listOf(accentColor, accentColor.copy(alpha = 0.6f))
                        )
                    )
            )
            Column(modifier = Modifier.padding(16.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Top
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Text(
                                text = workspace.name,
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.Bold,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                modifier = Modifier.weight(1f, fill = false)
                            )
                            AnimatedVisibility(visible = isPendingSync) {
                                Surface(
                                    shape = RoundedCornerShape(4.dp),
                                    color = MaterialTheme.colorScheme.tertiaryContainer
                                ) {
                                    Text(
                                        text = "Pending",
                                        modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp),
                                        style = MaterialTheme.typography.labelSmall,
                                        color = MaterialTheme.colorScheme.onTertiaryContainer
                                    )
                                }
                            }
                        }
                        if (workspace.description.isNotBlank()) {
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = workspace.description,
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                    }
                    IconButton(
                        onClick = onDelete,
                        modifier = Modifier.size(32.dp)
                    ) {
                        Icon(
                            Icons.Default.DeleteOutline,
                            contentDescription = "Delete",
                            tint = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        Text(
                            text = "Spent",
                            style = MaterialTheme.typography.labelMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Text(
                            text = formatCurrency(totalSpent),
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.SemiBold,
                            color = progressColor
                        )
                    }
                    if (workspace.totalBudget > 0) {
                        Column(horizontalAlignment = Alignment.End) {
                            Text(
                                text = "Budget",
                                style = MaterialTheme.typography.labelMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Text(
                                text = formatCurrency(workspace.totalBudget),
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }
                }

                if (workspace.totalBudget > 0) {
                    Spacer(modifier = Modifier.height(10.dp))
                    LinearProgressIndicator(
                        progress = { progress.toFloat() },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(6.dp)
                            .clip(RoundedCornerShape(3.dp)),
                        color = progressColor,
                        trackColor = MaterialTheme.colorScheme.surfaceVariant
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "${(progress * 100).toInt()}% used",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}

// ─── Create Workspace Dialog ──────────────────────────────────────────────────

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateWorkspaceDialog(
    onDismiss: () -> Unit,
    remainingGrant: Double,
    hasGrant: Boolean,
    onCreate: (String, String, Double, Int) -> Unit
) {
    var name by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var budgetText by remember { mutableStateOf("") }
    var selectedColorIndex by remember { mutableIntStateOf(0) }
    var nameError by remember { mutableStateOf(false) }
    var budgetError by remember { mutableStateOf(false) }

    val enteredBudget = budgetText.toDoubleOrNull() ?: 0.0
    val exceedsGrant = hasGrant && enteredBudget > remainingGrant

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("New Workspace", fontWeight = FontWeight.Bold) },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                // Show remaining grant if set
                if (hasGrant) {
                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = if (remainingGrant > 0)
                            MaterialTheme.colorScheme.primaryContainer
                        else
                            MaterialTheme.colorScheme.errorContainer
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 12.dp, vertical = 8.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Icon(
                                if (remainingGrant > 0) Icons.Default.AccountBalance
                                else Icons.Default.Warning,
                                contentDescription = null,
                                tint = if (remainingGrant > 0)
                                    MaterialTheme.colorScheme.onPrimaryContainer
                                else
                                    MaterialTheme.colorScheme.onErrorContainer,
                                modifier = Modifier.size(16.dp)
                            )
                            Text(
                                text = if (remainingGrant > 0)
                                    "Remaining grant: ${formatCurrency(remainingGrant)}"
                                else
                                    "No remaining grant funds",
                                style = MaterialTheme.typography.labelMedium,
                                fontWeight = FontWeight.Medium,
                                color = if (remainingGrant > 0)
                                    MaterialTheme.colorScheme.onPrimaryContainer
                                else
                                    MaterialTheme.colorScheme.onErrorContainer
                            )
                        }
                    }
                }

                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it; nameError = false },
                    label = { Text("Workspace Name *") },
                    placeholder = { Text("e.g., Avurudu Festival 2025") },
                    isError = nameError,
                    supportingText = if (nameError) {{ Text("Name is required") }} else null,
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(14.dp)
                )
                OutlinedTextField(
                    value = description,
                    onValueChange = { description = it },
                    label = { Text("Description (optional)") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(14.dp)
                )
                OutlinedTextField(
                    value = budgetText,
                    onValueChange = { budgetText = it; budgetError = false },
                    label = { Text("Budget (LKR)") },
                    placeholder = { Text("0.00") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    prefix = { Text("Rs. ") },
                    isError = budgetError || exceedsGrant,
                    supportingText = when {
                        budgetError -> {{ Text("Enter a valid amount") }}
                        exceedsGrant -> {{ Text("Exceeds remaining grant of ${formatCurrency(remainingGrant)}") }}
                        else -> null
                    },
                    shape = RoundedCornerShape(14.dp)
                )
                Text(
                    text = "Color",
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    WorkspaceColors.forEachIndexed { index, color ->
                        Box(
                            modifier = Modifier
                                .size(36.dp)
                                .clip(RoundedCornerShape(10.dp))
                                .background(color)
                                .clickable(
                                    indication = null,
                                    interactionSource = remember { MutableInteractionSource() }
                                ) { selectedColorIndex = index },
                            contentAlignment = Alignment.Center
                        ) {
                            if (selectedColorIndex == index) {
                                Icon(
                                    Icons.Default.Check,
                                    contentDescription = null,
                                    tint = Color.White,
                                    modifier = Modifier.size(18.dp)
                                )
                            }
                        }
                    }
                }
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    if (name.isBlank()) { nameError = true; return@Button }
                    if (exceedsGrant) return@Button
                    onCreate(
                        name,
                        description,
                        budgetText.toDoubleOrNull() ?: 0.0,
                        selectedColorIndex
                    )
                },
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("Create")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Cancel") }
        }
    )
}

// ─── Currency formatter ────────────────────────────────────────────────────────

fun formatCurrency(amount: Double): String {
    val format = java.text.DecimalFormat("Rs #,##0.00")
    return format.format(amount)
}
