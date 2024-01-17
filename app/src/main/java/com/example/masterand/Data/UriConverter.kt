package com.example.masterand.Data

import android.net.Uri
import androidx.room.TypeConverter

class UriConverter {
    @TypeConverter
    fun fromRecord(value: String) = Uri.parse(value)

    @TypeConverter
    fun toRecord(values: Uri?): String = values.toString()
}