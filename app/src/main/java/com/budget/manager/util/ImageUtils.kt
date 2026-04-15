package com.budget.manager.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Base64
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.core.content.FileProvider
import java.io.ByteArrayOutputStream
import java.io.File

object ImageUtils {
    // Standardizing dimensions so Base64 strings do not exceed Firestore/Room limits
    private const val MAX_IMAGE_DIMENSION = 800

    /**
     * Converts a generic Uri (from Camera FileProvider or Gallery Pick)
     * into a heavily compressed, downscaled Base64 string for efficient storage.
     */
    fun uriToBase64(context: Context, uri: Uri): String? {
        return try {
            val inputStream = context.contentResolver.openInputStream(uri)
            val originalBitmap = BitmapFactory.decodeStream(inputStream)
            inputStream?.close()

            if (originalBitmap == null) return null

            val scaledBitmap = scaleBitmapDown(originalBitmap, MAX_IMAGE_DIMENSION)
            
            val outputStream = ByteArrayOutputStream()
            // Compress using JPEG at 70% quality for good size reduction
            scaledBitmap.compress(Bitmap.CompressFormat.JPEG, 70, outputStream)
            val byteArray = outputStream.toByteArray()

            Base64.encodeToString(byteArray, Base64.DEFAULT)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    /**
     * Instantly decodes an encoded Base64 string from the database back into a Compose ImageBitmap.
     */
    fun base64ToImageBitmap(base64: String?): ImageBitmap? {
        if (base64.isNullOrBlank()) return null
        return try {
            val decodedString = Base64.decode(base64, Base64.DEFAULT)
            val bitmap = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)
            bitmap?.asImageBitmap()
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    /**
     * Calculates size accurately while maintaining aspect ratio
     */
    private fun scaleBitmapDown(bitmap: Bitmap, maxDimension: Int): Bitmap {
        val originalWidth = bitmap.width
        val originalHeight = bitmap.height

        var newWidth = originalWidth
        var newHeight = originalHeight

        if (originalWidth > maxDimension || originalHeight > maxDimension) {
            if (originalWidth > originalHeight) {
                newWidth = maxDimension
                newHeight = (newWidth * originalHeight) / originalWidth
            } else {
                newHeight = maxDimension
                newWidth = (newHeight * originalWidth) / originalHeight
            }
        }

        return Bitmap.createScaledBitmap(bitmap, newWidth, newHeight, true)
    }

    /**
     * Generates a temporary secure Uri using FileProvider for the Camera to write into.
     */
    fun createImageFileUri(context: Context): Uri {
        val imagesDir = File(context.cacheDir, "images")
        if (!imagesDir.exists()) {
            imagesDir.mkdirs()
        }
        val file = File(imagesDir, "camera_capture_${System.currentTimeMillis()}.jpg")
        return FileProvider.getUriForFile(
            context,
            "${context.packageName}.fileprovider",
            file
        )
    }
}
