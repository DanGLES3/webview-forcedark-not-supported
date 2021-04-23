package ru.kuchanov.nightmode

import android.content.Context
import java.io.BufferedReader
import java.io.InputStreamReader

object StorageUtils {

    fun readFromAssets(context: Context, filename: String): String {
        val reader = BufferedReader(InputStreamReader(context.assets.open(filename), "UTF-8"))

        val sb = StringBuilder()
        var line: String? = reader.readLine()
        while (line != null) {
            sb.append(line)
            line = reader.readLine()
        }
        reader.close()
        return sb.toString()
    }
}