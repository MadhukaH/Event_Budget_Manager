package com.budget.manager.data.remote

import android.util.Log
import com.budget.manager.data.model.Expense
import com.budget.manager.data.model.Workspace
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

private const val TAG = "FirestoreDataSource"

/**
 * FirestoreDataSource — all direct Firestore read/write operations.
 *
 * ┌─────────────────────────────────────────────────────────────────┐
 * │  ANTI-DUPLICATION GUARANTEE                                     │
 * │                                                                 │
 * │  Every write uses:                                              │
 * │    document(workspace.firestoreId).set(data, merge)             │
 * │                                                                 │
 * │  NOT:  collection.add(data)   ← creates a NEW doc every call!  │
 * │                                                                 │
 * │  Because the document ID is a UUID generated at creation time,  │
 * │  calling set() 100 times produces exactly 1 document.           │
 * │  This makes every sync retry fully idempotent.                  │
 * └─────────────────────────────────────────────────────────────────┘
 *
 * Firestore structure:
 * workspaces/
 *   {uuid}/                   ← document ID = Room firestoreId
 *     name, description, totalBudget, colorIndex, createdAt, lastModified
 *     expenses/
 *       {uuid}/               ← document ID = Expense firestoreId
 *         category, amount, note, workspaceId, createdAt, lastModified
 */
@Singleton
class FirestoreDataSource @Inject constructor(
    private val firestore: FirebaseFirestore
) {

    // ── Collection helpers ────────────────────────────────────────────────────

    private fun workspaceDoc(firestoreId: String) =
        firestore.collection("workspaces").document(firestoreId)

    private fun expenseDoc(workspaceFirestoreId: String, expenseFirestoreId: String) =
        firestore.collection("workspaces")
            .document(workspaceFirestoreId)
            .collection("expenses")
            .document(expenseFirestoreId)

    // ── Workspace operations ──────────────────────────────────────────────────

    /**
     * Idempotent upsert — always writes to the same document ID.
     * Safe to call multiple times; will never create a duplicate.
     */
    suspend fun upsertWorkspace(workspace: Workspace) {
        require(workspace.firestoreId.isNotBlank()) {
            "firestoreId must not be blank — assign a UUID before calling upsertWorkspace()"
        }
        Log.d(TAG, "upsertWorkspace → doc/${workspace.firestoreId}")
        workspaceDoc(workspace.firestoreId)
            .set(workspace.toFirestoreMap(), SetOptions.merge())
            .await()
    }

    /**
     * Deletes a workspace and all its expenses from Firestore.
     * Uses a batch for atomic deletion of the subcollection.
     */
    suspend fun deleteWorkspace(firestoreId: String) {
        Log.d(TAG, "deleteWorkspace → doc/$firestoreId")
        val expenseDocs = firestore.collection("workspaces")
            .document(firestoreId)
            .collection("expenses")
            .get()
            .await()

        if (!expenseDocs.isEmpty) {
            val batch = firestore.batch()
            expenseDocs.documents.forEach { batch.delete(it.reference) }
            batch.commit().await()
        }
        workspaceDoc(firestoreId).delete().await()
    }

    suspend fun getAllWorkspaces(): List<RemoteWorkspace> {
        return firestore.collection("workspaces")
            .get()
            .await()
            .documents
            .mapNotNull { doc ->
                try {
                    RemoteWorkspace(
                        firestoreId = doc.id,
                        name = doc.getString("name") ?: "",
                        description = doc.getString("description") ?: "",
                        totalBudget = doc.getDouble("totalBudget") ?: 0.0,
                        colorIndex = (doc.getLong("colorIndex") ?: 0L).toInt(),
                        createdAt = doc.getLong("createdAt") ?: 0L,
                        lastModified = doc.getLong("lastModified") ?: 0L
                    )
                } catch (e: Exception) {
                    Log.w(TAG, "Skipping malformed workspace doc ${doc.id}", e)
                    null
                }
            }
    }

    // ── Expense operations ────────────────────────────────────────────────────

    /**
     * Idempotent upsert — writes to a fixed document ID.
     * Multiple calls with the same expense = exactly one Firestore document.
     */
    suspend fun upsertExpense(expense: Expense) {
        require(expense.firestoreId.isNotBlank()) { "expense firestoreId must not be blank" }
        require(expense.workspaceFirestoreId.isNotBlank()) { "workspaceFirestoreId must not be blank" }

        Log.d(TAG, "upsertExpense → ${expense.workspaceFirestoreId}/expenses/${expense.firestoreId}")
        expenseDoc(expense.workspaceFirestoreId, expense.firestoreId)
            .set(expense.toFirestoreMap(), SetOptions.merge())
            .await()
    }

    suspend fun deleteExpense(workspaceFirestoreId: String, expenseFirestoreId: String) {
        Log.d(TAG, "deleteExpense → $workspaceFirestoreId/expenses/$expenseFirestoreId")
        expenseDoc(workspaceFirestoreId, expenseFirestoreId).delete().await()
    }

    suspend fun getExpensesForWorkspace(workspaceFirestoreId: String): List<RemoteExpense> {
        return firestore.collection("workspaces")
            .document(workspaceFirestoreId)
            .collection("expenses")
            .get()
            .await()
            .documents
            .mapNotNull { doc ->
                try {
                    RemoteExpense(
                        firestoreId = doc.id,
                        workspaceFirestoreId = workspaceFirestoreId,
                        category = doc.getString("category") ?: "",
                        amount = doc.getDouble("amount") ?: 0.0,
                        note = doc.getString("note") ?: "",
                        receiptBase64 = doc.getString("receiptBase64"),
                        createdAt = doc.getLong("createdAt") ?: 0L,
                        lastModified = doc.getLong("lastModified") ?: 0L
                    )
                } catch (e: Exception) {
                    Log.w(TAG, "Skipping malformed expense doc ${doc.id}", e)
                    null
                }
            }
    }
}

// ── Serialization helpers ─────────────────────────────────────────────────────

private fun Workspace.toFirestoreMap(): Map<String, Any> = mapOf(
    "name" to name,
    "description" to description,
    "totalBudget" to totalBudget,
    "colorIndex" to colorIndex,
    "createdAt" to createdAt,
    "lastModified" to lastModified
)

private fun Expense.toFirestoreMap(): Map<String, Any> {
    val map = mutableMapOf<String, Any>(
        "workspaceId" to workspaceFirestoreId,
        "category" to category,
        "amount" to amount,
        "note" to note,
        "createdAt" to createdAt,
        "lastModified" to lastModified
    )
    if (receiptBase64 != null) {
        map["receiptBase64"] = receiptBase64
    }
    return map
}

// ── Remote DTOs ───────────────────────────────────────────────────────────────

data class RemoteWorkspace(
    val firestoreId: String = "",
    val name: String = "",
    val description: String = "",
    val totalBudget: Double = 0.0,
    val colorIndex: Int = 0,
    val createdAt: Long = 0L,
    val lastModified: Long = 0L
)

data class RemoteExpense(
    val firestoreId: String = "",
    val workspaceFirestoreId: String = "",
    val category: String = "",
    val amount: Double = 0.0,
    val note: String = "",
    val receiptBase64: String? = null,
    val createdAt: Long = 0L,
    val lastModified: Long = 0L
)
