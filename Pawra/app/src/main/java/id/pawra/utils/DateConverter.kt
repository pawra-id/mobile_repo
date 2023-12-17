package id.pawra.utils

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

object DateConverter {
    fun convertStringToDate(inputDateStr: String): String {
        if (inputDateStr.isEmpty()) return ""

        val originalDateTime = LocalDateTime.parse(inputDateStr, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSSXXX"))
        val desiredTime = originalDateTime.format(DateTimeFormatter.ofPattern("HH:mm"))
        val desiredDate = originalDateTime.format(DateTimeFormatter.ofPattern("dd MMMM yyyy"))
        return "$desiredTime, $desiredDate"
    }
}