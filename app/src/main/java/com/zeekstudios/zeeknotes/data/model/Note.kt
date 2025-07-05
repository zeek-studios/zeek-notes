package com.zeekstudios.zeeknotes.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notes")
data class Note(
    @PrimaryKey
    val id: String,
    val title: String,
    val content: String,
    val type: NoteType,
    val audioPath: String? = null,
    val createdAt: Long,
    val updatedAt: Long,
    val tags: List<String> = emptyList(),
    val summary: String? = null,
    val keyPoints: List<String> = emptyList(),
    val actionItems: List<String> = emptyList(),
    val speakers: List<String> = emptyList(), // For meeting notes
    val isFavorite: Boolean = false,
    val isArchived: Boolean = false
)

enum class NoteType {
    QUICK,
    MEMO,
    MEETING
}