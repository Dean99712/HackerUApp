package com.example.hackeruapp

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

enum class IMAGE_TYPE {
    URI, URL
}

@Entity(tableName = "notesTable")
data class Note(
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "description") val description: String,
    @ColumnInfo(name = "image_uri") var imagePath: String? = null,
    @ColumnInfo(name = "image_type") var imageType: IMAGE_TYPE? = null
) {
    @PrimaryKey(autoGenerate = true)
    var id = 0
}
