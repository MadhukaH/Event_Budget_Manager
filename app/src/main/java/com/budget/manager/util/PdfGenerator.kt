package com.budget.manager.util

import android.content.Context
import android.content.Intent
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.graphics.Typeface
import android.graphics.pdf.PdfDocument
import android.net.Uri
import androidx.core.content.FileProvider
import com.budget.manager.data.model.Expense
import com.budget.manager.data.model.Workspace
import java.io.File
import java.io.FileOutputStream
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object PdfGenerator {

    private const val PAGE_WIDTH = 595
    private const val PAGE_HEIGHT = 842
    private const val MARGIN = 40f

    fun generateWorkspaceReport(
        context: Context,
        workspace: Workspace,
        expenses: List<Expense>,
        totalSpent: Double
    ): Uri? {
        val pdfDocument = PdfDocument()
        val pageInfo = PdfDocument.PageInfo.Builder(PAGE_WIDTH, PAGE_HEIGHT, 1).create()
        var page = pdfDocument.startPage(pageInfo)
        var canvas = page.canvas

        val titlePaint = Paint().apply {
            color = Color.DKGRAY
            textSize = 24f
            typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
            isAntiAlias = true
        }

        val subtitlePaint = Paint().apply {
            color = Color.GRAY
            textSize = 14f
            isAntiAlias = true
        }

        val textPaint = Paint().apply {
            color = Color.BLACK
            textSize = 12f
            isAntiAlias = true
        }

        val boldTextPaint = Paint().apply {
            color = Color.BLACK
            textSize = 14f
            typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
            isAntiAlias = true
        }

        val headerPaint = Paint().apply {
            color = Color.WHITE
            textSize = 12f
            typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
            isAntiAlias = true
        }

        val headerBgPaint = Paint().apply {
            color = Color.parseColor("#4CAF50") // Professional Green
            isAntiAlias = true
        }

        val formatter = java.text.DecimalFormat("Rs #,##0.00")
        val dateFormat = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())

        var currentY = MARGIN

        // Title
        canvas.drawText("Budget Report: ${workspace.name}", MARGIN, currentY, titlePaint)
        currentY += 25f

        // Subtitle
        val generatedDate = dateFormat.format(Date())
        canvas.drawText("Generated on: $generatedDate", MARGIN, currentY, subtitlePaint)
        currentY += 40f

        // Summary Box
        val summaryBoxRect = RectF(MARGIN, currentY, PAGE_WIDTH - MARGIN, currentY + 110f)
        val boxPaint = Paint().apply {
            color = Color.parseColor("#F5F5F5")
            style = Paint.Style.FILL
            isAntiAlias = true
        }
        val strokePaint = Paint().apply {
            color = Color.LTGRAY
            style = Paint.Style.STROKE
            strokeWidth = 1f
            isAntiAlias = true
        }
        canvas.drawRoundRect(summaryBoxRect, 8f, 8f, boxPaint)
        canvas.drawRoundRect(summaryBoxRect, 8f, 8f, strokePaint)

        currentY += 30f
        val remaining = workspace.totalBudget - totalSpent
        val budgetStr = "Total Budget: ${formatter.format(workspace.totalBudget)}"
        val spentStr = "Total Spent: ${formatter.format(totalSpent)}"
        val remStr = "Remaining: ${formatter.format(remaining)}"

        canvas.drawText(budgetStr, MARGIN + 20f, currentY, boldTextPaint)
        currentY += 30f
        canvas.drawText(spentStr, MARGIN + 20f, currentY, boldTextPaint)
        currentY += 30f
        
        val redPaint = Paint(boldTextPaint).apply { 
            color = if (remaining < 0) Color.RED else Color.parseColor("#388E3C") 
        }
        canvas.drawText(remStr, MARGIN + 20f, currentY, redPaint)

        currentY += 60f

        // Expenses Header
        canvas.drawText("Expense Details", MARGIN, currentY, titlePaint)
        currentY += 20f

        // Table Header
        canvas.drawRect(MARGIN, currentY, PAGE_WIDTH - MARGIN, currentY + 30f, headerBgPaint)
        
        val colDate = MARGIN + 10f
        val colCategory = MARGIN + 120f
        val colAmount = MARGIN + 270f
        val colNote = MARGIN + 380f

        val headerY = currentY + 20f
        canvas.drawText("Date", colDate, headerY, headerPaint)
        canvas.drawText("Category", colCategory, headerY, headerPaint)
        canvas.drawText("Amount", colAmount, headerY, headerPaint)
        canvas.drawText("Note", colNote, headerY, headerPaint)

        currentY += 30f

        // List Expenses
        var isAlternate = false
        val altRowPaint = Paint().apply { 
            color = Color.parseColor("#FAFAFA")
            style = Paint.Style.FILL
        }

        for (expense in expenses) {
            // Check for page overflow
            if (currentY > PAGE_HEIGHT - MARGIN - 30f) {
                pdfDocument.finishPage(page)
                page = pdfDocument.startPage(pageInfo)
                canvas = page.canvas
                currentY = MARGIN

                // Render Header again
                canvas.drawRect(MARGIN, currentY, PAGE_WIDTH - MARGIN, currentY + 30f, headerBgPaint)
                val hY = currentY + 20f
                canvas.drawText("Date", colDate, hY, headerPaint)
                canvas.drawText("Category", colCategory, hY, headerPaint)
                canvas.drawText("Amount", colAmount, hY, headerPaint)
                canvas.drawText("Note", colNote, hY, headerPaint)
                currentY += 30f
            }

            if (isAlternate) {
                canvas.drawRect(MARGIN, currentY, PAGE_WIDTH - MARGIN, currentY + 25f, altRowPaint)
            }
            
            val rowY = currentY + 18f
            canvas.drawText(dateFormat.format(Date(expense.createdAt)), colDate, rowY, textPaint)
            canvas.drawText(expense.category, colCategory, rowY, textPaint)
            canvas.drawText(formatter.format(expense.amount), colAmount, rowY, textPaint)
            
            val cleanNote = expense.note.replace('\n', ' ')
            val noteText = if (cleanNote.length > 25) cleanNote.take(22) + "..." else cleanNote
            canvas.drawText(noteText, colNote, rowY, textPaint)

            canvas.drawLine(MARGIN, currentY + 25f, PAGE_WIDTH - MARGIN, currentY + 25f, strokePaint)

            currentY += 25f
            isAlternate = !isAlternate
        }

        pdfDocument.finishPage(page)

        return try {
            // Use external files dir so other apps can read it
            val outputDir = File(context.cacheDir, "pdfs")
            if (!outputDir.exists()) outputDir.mkdirs()

            val safeName = workspace.name.replace(Regex("[^a-zA-Z0-9.-]"), "_")
            val file = File(outputDir, "Report_$safeName.pdf")
            pdfDocument.writeTo(FileOutputStream(file))
            pdfDocument.close()

            FileProvider.getUriForFile(
                context,
                "${context.packageName}.fileprovider",
                file
            )
        } catch (e: Exception) {
            e.printStackTrace()
            pdfDocument.close()
            null
        }
    }

    fun generateAllExpensesReport(
        context: Context,
        expenses: List<Expense>,
        grantTotal: Double,
        grantSpent: Double
    ): Uri? {
        val pdfDocument = PdfDocument()
        val pageInfo = PdfDocument.PageInfo.Builder(PAGE_WIDTH, PAGE_HEIGHT, 1).create()
        var page = pdfDocument.startPage(pageInfo)
        var canvas = page.canvas

        val titlePaint = Paint().apply {
            color = Color.DKGRAY
            textSize = 24f
            typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
            isAntiAlias = true
        }

        val subtitlePaint = Paint().apply {
            color = Color.GRAY
            textSize = 14f
            isAntiAlias = true
        }

        val textPaint = Paint().apply {
            color = Color.BLACK
            textSize = 12f
            isAntiAlias = true
        }

        val boldTextPaint = Paint().apply {
            color = Color.BLACK
            textSize = 14f
            typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
            isAntiAlias = true
        }

        val headerPaint = Paint().apply {
            color = Color.WHITE
            textSize = 12f
            typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
            isAntiAlias = true
        }

        val headerBgPaint = Paint().apply {
            color = Color.parseColor("#4CAF50") // Green
            isAntiAlias = true
        }

        val formatter = java.text.DecimalFormat("Rs #,##0.00")
        val dateFormat = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())

        var currentY = MARGIN

        canvas.drawText("කොවුල් වසත් නද - 2026 Report", MARGIN, currentY, titlePaint)
        currentY += 25f

        val generatedDate = dateFormat.format(Date())
        canvas.drawText("Generated on: $generatedDate", MARGIN, currentY, subtitlePaint)
        currentY += 40f

        // Global Summary Box
        val summaryBoxRect = RectF(MARGIN, currentY, PAGE_WIDTH - MARGIN, currentY + 110f)
        val boxPaint = Paint().apply {
            color = Color.parseColor("#F5F5F5")
            style = Paint.Style.FILL
            isAntiAlias = true
        }
        val strokePaint = Paint().apply {
            color = Color.LTGRAY
            style = Paint.Style.STROKE
            strokeWidth = 1f
            isAntiAlias = true
        }
        canvas.drawRoundRect(summaryBoxRect, 8f, 8f, boxPaint)
        canvas.drawRoundRect(summaryBoxRect, 8f, 8f, strokePaint)

        currentY += 30f
        val remainingGrant = grantTotal - grantSpent

        canvas.drawText("Total Grant Fund: ${formatter.format(grantTotal)}", MARGIN + 20f, currentY, boldTextPaint)
        currentY += 30f
        canvas.drawText("Total Expenditure: ${formatter.format(grantSpent)}", MARGIN + 20f, currentY, boldTextPaint)
        currentY += 30f
        
        val redPaint = Paint(boldTextPaint).apply { 
            color = if (remainingGrant < 0) Color.RED else Color.parseColor("#388E3C") 
        }
        canvas.drawText("Remaining Grant Fund: ${formatter.format(remainingGrant)}", MARGIN + 20f, currentY, redPaint)

        currentY += 60f

        canvas.drawText("Detailed Spending By Category", MARGIN, currentY, titlePaint)
        currentY += 20f

        val colDate = MARGIN + 10f
        val colDesc = MARGIN + 120f
        val colAmount = MARGIN + 400f

        val expensesByCategory = expenses.groupBy { it.category }

        for ((category, categoryExpenses) in expensesByCategory) {
            // Check for page overflow for category header
            if (currentY > PAGE_HEIGHT - MARGIN - 40f) {
                pdfDocument.finishPage(page)
                page = pdfDocument.startPage(pageInfo)
                canvas = page.canvas
                currentY = MARGIN
            }

            val categoryTotal = categoryExpenses.sumOf { it.amount }
            
            // Draw Category Header
            canvas.drawRect(MARGIN, currentY, PAGE_WIDTH - MARGIN, currentY + 30f, headerBgPaint)
            val headerY = currentY + 20f
            canvas.drawText("Category: $category", colDate, headerY, headerPaint)
            canvas.drawText("Total: -${formatter.format(categoryTotal)}", colAmount, headerY, headerPaint)
            
            currentY += 30f

            var isAlternate = false
            val altRowPaint = Paint().apply { 
                color = Color.parseColor("#FAFAFA")
                style = Paint.Style.FILL
            }

            for (expense in categoryExpenses) {
                if (currentY > PAGE_HEIGHT - MARGIN - 30f) {
                    pdfDocument.finishPage(page)
                    page = pdfDocument.startPage(pageInfo)
                    canvas = page.canvas
                    currentY = MARGIN

                    // Re-draw Category Header on new page
                    canvas.drawRect(MARGIN, currentY, PAGE_WIDTH - MARGIN, currentY + 30f, headerBgPaint)
                    val hY = currentY + 20f
                    canvas.drawText("Category: $category (cont.)", colDate, hY, headerPaint)
                    canvas.drawText("Date", colDate, hY + 30f, headerPaint) // optional helper
                    currentY += 30f
                }

                if (isAlternate) {
                    canvas.drawRect(MARGIN, currentY, PAGE_WIDTH - MARGIN, currentY + 25f, altRowPaint)
                }
                
                val rowY = currentY + 18f
                canvas.drawText(dateFormat.format(Date(expense.createdAt)), colDate, rowY, textPaint)
                
                // Limit Note Length
                val cleanDesc = expense.note.replace('\n', ' ')
                val descText = if (cleanDesc.length > 40) cleanDesc.take(37) + "..." else cleanDesc
                canvas.drawText(descText, colDesc, rowY, textPaint)
                
                val amtText = formatter.format(expense.amount)
                val amtPaint = Paint(textPaint).apply { color = Color.RED }
                canvas.drawText("-" + amtText, colAmount, rowY, amtPaint)

                canvas.drawLine(MARGIN, currentY + 25f, PAGE_WIDTH - MARGIN, currentY + 25f, strokePaint)

                currentY += 25f
                isAlternate = !isAlternate
            }
            
            // Add some spacing after each category
            currentY += 15f
        }

        pdfDocument.finishPage(page)

        return try {
            val outputDir = File(context.cacheDir, "pdfs")
            if (!outputDir.exists()) outputDir.mkdirs()

            val file = File(outputDir, "Kovul_Wasath_Nada_2026_Report.pdf")
            pdfDocument.writeTo(FileOutputStream(file))
            pdfDocument.close()

            FileProvider.getUriForFile(
                context,
                "${context.packageName}.fileprovider",
                file
            )
        } catch (e: Exception) {
            e.printStackTrace()
            pdfDocument.close()
            null
        }
    }

    fun viewPdf(context: Context, uri: Uri) {
        val intent = Intent(Intent.ACTION_VIEW).apply {
            setDataAndType(uri, "application/pdf")
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        }
        val chooser = Intent.createChooser(intent, "Open Report")
        chooser.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(chooser)
    }
}
