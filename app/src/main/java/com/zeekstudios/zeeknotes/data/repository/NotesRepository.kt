package com.zeekstudios.zeeknotes.data.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.zeekstudios.zeeknotes.data.model.Note
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

interface NotesRepository {
    suspend fun saveNote(note: Note): Result<Unit>
    suspend fun updateNote(note: Note): Result<Unit>
    suspend fun deleteNote(noteId: String): Result<Unit>
    suspend fun getNoteById(noteId: String): Result<Note?>
    fun getAllNotes(): Flow<List<Note>>
    fun getNotesByType(type: String): Flow<List<Note>>
    suspend fun searchNotes(query: String): Result<List<Note>>
}

@Singleton
class NotesRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val authRepository: AuthRepository
) : NotesRepository {
    
    private fun getUserNotesCollection() = firestore
        .collection("users")
        .document(authRepository.getCurrentUser()?.uid ?: "")
        .collection("notes")
    
    override suspend fun saveNote(note: Note): Result<Unit> {
        return try {
            getUserNotesCollection()
                .document(note.id)
                .set(note)
                .await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    override suspend fun updateNote(note: Note): Result<Unit> {
        return try {
            val updatedNote = note.copy(updatedAt = System.currentTimeMillis())
            getUserNotesCollection()
                .document(note.id)
                .set(updatedNote)
                .await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    override suspend fun deleteNote(noteId: String): Result<Unit> {
        return try {
            getUserNotesCollection()
                .document(noteId)
                .delete()
                .await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    override suspend fun getNoteById(noteId: String): Result<Note?> {
        return try {
            val document = getUserNotesCollection()
                .document(noteId)
                .get()
                .await()
            
            val note = document.toObject(Note::class.java)
            Result.success(note)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    override fun getAllNotes(): Flow<List<Note>> = callbackFlow {
        val listener = getUserNotesCollection()
            .orderBy("createdAt", Query.Direction.DESCENDING)
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    close(error)
                    return@addSnapshotListener
                }
                
                val notes = snapshot?.documents?.mapNotNull { document ->
                    try {
                        document.toObject(Note::class.java)
                    } catch (e: Exception) {
                        null
                    }
                } ?: emptyList()
                
                trySend(notes)
            }
        
        awaitClose { listener.remove() }
    }
    
    override fun getNotesByType(type: String): Flow<List<Note>> = callbackFlow {
        val listener = getUserNotesCollection()
            .whereEqualTo("type", type)
            .orderBy("createdAt", Query.Direction.DESCENDING)
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    close(error)
                    return@addSnapshotListener
                }
                
                val notes = snapshot?.documents?.mapNotNull { document ->
                    try {
                        document.toObject(Note::class.java)
                    } catch (e: Exception) {
                        null
                    }
                } ?: emptyList()
                
                trySend(notes)
            }
        
        awaitClose { listener.remove() }
    }
    
    override suspend fun searchNotes(query: String): Result<List<Note>> {
        return try {
            // Firebase doesn't support full-text search natively
            // This is a basic implementation that searches in title and content
            // For production, consider using Algolia or similar service
            
            val titleResults = getUserNotesCollection()
                .whereGreaterThanOrEqualTo("title", query)
                .whereLessThanOrEqualTo("title", query + "\uf8ff")
                .get()
                .await()
            
            val notes = titleResults.documents.mapNotNull { document ->
                try {
                    document.toObject(Note::class.java)
                } catch (e: Exception) {
                    null
                }
            }
            
            Result.success(notes)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}