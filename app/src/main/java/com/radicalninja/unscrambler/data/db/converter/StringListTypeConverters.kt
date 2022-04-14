package com.radicalninja.unscrambler.data.db.converter

import androidx.room.TypeConverter

object StringListTypeConverters {

    @TypeConverter
    fun stringToStringList(value: String?): MutableList<String> {
        return value?.split("|") as MutableList<String> ?: mutableListOf()
    }

    @TypeConverter
    fun stringListToString(value: List<String>): String {
        return value.joinToString("|")
    }
}