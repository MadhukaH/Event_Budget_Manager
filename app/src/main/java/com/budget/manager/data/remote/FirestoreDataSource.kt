package com.budget.manager.data.remote

import com.budget.manager.data.model.Expense
import com.budget.manager.data.model.Workspace
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

/**
 * FirestoreDataSource — all direct Firestore operations.
 *
 * Firestore structure:
 * ├── workspaces/
 * │   └── {workspaceId}/
 * │       ├── name, description, totalBudget, colorIndex, createdAt, lastModified
 * │       └── expenses/                   ← subcollection
 * │           └── {expenseId}/
 * │               ├── category, amount, note, createdAt, lastModified
 * │               └── workspaceId
 *
 * All operations use await() for coroutine-friendly execution.
 * Duplicates are prevented by using Firestore document IDs as idempotency keys.
 */
@Singleton
class FirestoreDataSource @Inject constructor(
    private val firestore: FirebaseFirestore
) {
    // ─── Collection references ───────────────────────────────────────────────

    private fun workspacesRef() = firestore.collection("workspaces")

    private fun expensesRef(workspaceFirestoreId: String) =
        firestore.collection("workspaces")
            .document(workspaceFirestoreId)
            .collection("expenses")

    // ─── Workspace operations ────────────────────────────────────────────────

    /**
     * Creates or upserts a workspace in Firestore.
     * Returns the Firestore document ID (used to link the local Room record).
     */
    suspend fun upsertWorkspace(workspace: Workspace): String {
        val data = workspace.toFirestoreMap()
        return if (workspace.firestoreId.isBlank()) {
            // New document — Firestore generates the ID
            val ref = workspacesRef().add(data).await()
            ref.id
        } else {
            // Existing document — merge to avoid overwriting unrelated fields
            workspacesRef().document(workspace.firestoreId).set(data, SetOptions.merge()).await()
            workspace.firestoreId
        }
    }

    /**
     * Soft-delete in Firestore: marks document with deleted=true.
     * Hard-delete is done by the caller after confirmation.
     */
    suspend fun deleteWorkspace(firestoreId: String) {
        workspacesRef().document(firestoreId).delete().await()
        // Cascading subcollection deletion is done via a batch
        val expenses = expensesRef(firestoreId).get().await()
        if (!expenses.isEmpty) {
            val batch = firestore.batch()
            expenses.documents.forEach { batch.delete(it.reference) }
            batch.commit().await()
        }
    }

    suspend fun getAllWorkspaces(): List<RemoteWorkspace> {
        return workspacesRef()
            .get()
            .await()
            .documents
            .mapNotNull { doc ->
                doc.toObject(RemoteWorkspace::class.java)?.copy(firestoreId = doc.id)
            }
    }

    // ─── Expense operations ──────────────────────────────────────────────────

    suspend fun upsertExpense(expense: Expense): String {
        val data = expense.toFirestoreMap()
        return if (expense.firestoreId.isBlank()) {
            val ref = expensesRef(expense.workspaceFirestoreId).add(data).await()
            ref.id
        } else {
            expensesRef(expense.workspaceFirestoreId)
                .document(expense.firestoreId)
                .set(data, SetOptions.merge())
                .await()
            expense.firestoreId
        }
    }

    suspend fun deleteExpense(workspaceFirestoreId: String, expenseFirestoreId: String) {
        expensesRef(workspaceFirestoreId).document(expenseFirestoreId).delete().await()
    }

    suspend fun getExpensesForWorkspace(workspaceFirestoreId: String): List<RemoteExpense> {
        return expensesRef(workspaceFirestoreId)
            .get()
            .await()
            .documents
            .mapNotNull { doc ->
                doc.toObject(RemoteExpense::class.java)?.copy(firestoreId = doc.id)
            }
    }
}

// ─── Extension: Domain → Firestore map ──────────────────────────────────────

private fun Workspace.toFirestoreMap(): Map<String, Any> = mapOf(
    "name" to name,
    "description" to description,
    "totalBudget" to totalBudget,
    "colorIndex" to colorIndex,
    "createdAt" to createdAt,
    "lastModified" to lastModified
)

private fun Expense.toFirestoreMap(): Map<String, Any> = mapOf(
    "workspaceId" to workspaceFirestoreId,
    "category" to category,
    "amount" to amount,
    "note" to note,
    "createdAt" to createdAt,
    "lastModified" to lastModified
)

// ─── Remote DTOs ─────────────────────────────────────────────────────────────

/** Data Transfer Object for Firestore Workspace documents */
data class RemoteWorkspace(
    val firestoreId: String = "",
    val name: String = "",
    val description: String = "",
    val totalBudget: Double = 0.0,
    val colorIndex: Int = 0,
    val createdAt: Long = 0L,
    val lastModified: Long = 0L
)

/** Data Transfer Object for Firestore Expense documents */
data class RemoteExpense(
    val firestoreId: String = "",
    val workspaceId: String = "",
    val category: String = "",
    val amount: Double = 0.0,
    val note: String = "",
    val createdAt: Long = 0L,
    val lastModified: Long = 0L
)
