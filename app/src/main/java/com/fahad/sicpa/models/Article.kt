package com.fahad.sicpa.models

import java.text.SimpleDateFormat
import java.util.*

data class Article(
    val title: String,
    val publishedDate: String
) {

    fun toReadableDate(): String {
        return try {
            val originalFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZZZZ", Locale.ENGLISH)
            val targetFormat = SimpleDateFormat("dd/MM/yyyy h:mm a", Locale.getDefault())
            val date = originalFormat.parse(publishedDate)
            targetFormat.format(date)
        } catch (ex: Exception) {
            ex.printStackTrace()
            publishedDate
        }
    }
}