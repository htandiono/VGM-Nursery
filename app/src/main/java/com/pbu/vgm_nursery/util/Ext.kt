package com.pbu.vgm_nursery.util

import android.content.ContentValues
import android.content.Context
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import com.pbu.vgm_nursery.R
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.io.OutputStream
import java.text.SimpleDateFormat
import java.util.Locale

private const val FILENAME_FORMAT = "dd-MMM-yyyy HH:mm:ss"

val timeStamp: String = SimpleDateFormat(
    FILENAME_FORMAT,
    Locale.US
).format(System.currentTimeMillis())

fun createCustomFile(context: Context): OutputStream? {
    val resolver = context.contentResolver
    val values = ContentValues()

    values.put(MediaStore.MediaColumns.DISPLAY_NAME, "$timeStamp.csv")
    values.put(MediaStore.MediaColumns.MIME_TYPE, "text/csv")
    values.put(
        MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_DOCUMENTS + context.getString(
            R.string.filepath
        )
    )

    val uri = resolver.insert(MediaStore.Files.getContentUri("external"), values)
    return resolver.openOutputStream(uri!!)
}

fun createCustomTempFile(context: Context): File {
    val filesDir = context.externalCacheDir
    return File.createTempFile(timeStamp, ".csv", filesDir)
}

fun Uri.toFile(context: Context): File {
    val myFile = createCustomTempFile(context)
    val inputStream = context.contentResolver.openInputStream(this) as InputStream
    val outputStream = FileOutputStream(myFile)
    val buffer = ByteArray(1024)
    var length: Int
    while (inputStream.read(buffer).also { length = it } > 0) outputStream.write(buffer, 0, length)
    outputStream.close()
    inputStream.close()
    return myFile
}