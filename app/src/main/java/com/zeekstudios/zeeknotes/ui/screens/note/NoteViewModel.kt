package com.zeekstudios.zeeknotes.ui.screens.note

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zeekstudios.zeeknotes.data.model.Note
import com.zeekstudios.zeeknotes.data.model.NoteType
import com.zeekstudios.zeeknotes.data.repository.AudioRepository
import com.zeekstudios.zeeknotes.data.repository.NotesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

data class NoteUiState(
    val noteType: NoteType = NoteType.QUICK,
    val isRecording: Boolean = false,
    val recordingDuration: Int = 0,
    val transcription: String = "",
    val hasContent: Boolean = false,
    val isSaved: Boolean = false,
    val error: String? = null
)

@HiltViewModel
class NoteViewModel @Inject constructor(
    private val audioRepository: AudioRepository,
    private val notesRepository: NotesRepository
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(NoteUiState())
    val uiState: StateFlow<NoteUiState> = _uiState.asStateFlow()
    
    private var recordingJob: Job? = null
    private var currentRecordingPath: String? = null
    
    fun setNoteType(noteType: String) {
        val type = when (noteType) {
            "quick" -> NoteType.QUICK
            "memo" -> NoteType.MEMO
            "meeting" -> NoteType.MEETING
            else -> NoteType.QUICK
        }
        _uiState.value = _uiState.value.copy(noteType = type)
    }
    
    fun startRecording() {
        viewModelScope.launch {
            try {
                val recordingPath = audioRepository.startRecording()
                currentRecordingPath = recordingPath
                
                _uiState.value = _uiState.value.copy(
                    isRecording = true,
                    recordingDuration = 0,
                    error = null
                )
                
                // Start duration timer
                startDurationTimer()
                
                // Start live transcription for memo and meeting modes
                if (_uiState.value.noteType != NoteType.QUICK) {
                    startLiveTranscription()
                }
                
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    error = e.message ?: "Failed to start recording"
                )
            }
        }
    }
    
    fun stopRecording() {
        viewModelScope.launch {
            try {
                recordingJob?.cancel()
                audioRepository.stopRecording()
                
                _uiState.value = _uiState.value.copy(
                    isRecording = false,
                    hasContent = true
                )
                
                // Process the recording for transcription if not already done
                currentRecordingPath?.let { path ->
                    processRecording(path)
                }
                
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isRecording = false,
                    error = e.message ?: "Failed to stop recording"
                )
            }
        }
    }
    
    private fun startDurationTimer() {
        recordingJob = viewModelScope.launch {
            while (_uiState.value.isRecording) {
                delay(1000)
                _uiState.value = _uiState.value.copy(
                    recordingDuration = _uiState.value.recordingDuration + 1
                )
            }
        }
    }
    
    private fun startLiveTranscription() {
        // TODO: Implement live transcription with Google Cloud Speech-to-Text
        // For now, simulate with placeholder text
        viewModelScope.launch {
            delay(3000) // Simulate processing delay
            _uiState.value = _uiState.value.copy(
                transcription = "Live transcription will appear here..."
            )
        }
    }
    
    private suspend fun processRecording(recordingPath: String) {
        try {
            // TODO: Implement actual transcription with Google Cloud AI
            // For now, simulate transcription
            val transcription = "This is a simulated transcription of the recorded audio. " +
                    "In the final implementation, this will be processed by Google Cloud Speech-to-Text API."
            
            _uiState.value = _uiState.value.copy(
                transcription = transcription
            )
            
        } catch (e: Exception) {
            _uiState.value = _uiState.value.copy(
                error = e.message ?: "Failed to process recording"
            )
        }
    }
    
    fun saveNote() {
        viewModelScope.launch {
            try {
                val note = Note(
                    id = UUID.randomUUID().toString(),
                    title = generateNoteTitle(),
                    content = _uiState.value.transcription,
                    type = _uiState.value.noteType,
                    audioPath = currentRecordingPath,
                    createdAt = System.currentTimeMillis(),
                    updatedAt = System.currentTimeMillis()
                )
                
                notesRepository.saveNote(note)
                
                _uiState.value = _uiState.value.copy(isSaved = true)
                
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    error = e.message ?: "Failed to save note"
                )
            }
        }
    }
    
    private fun generateNoteTitle(): String {
        val timestamp = Date().toString().substring(0, 16)
        return when (_uiState.value.noteType) {
            NoteType.QUICK -> "Quick Note - $timestamp"
            NoteType.MEMO -> "Memo - $timestamp"
            NoteType.MEETING -> "Meeting - $timestamp"
        }
    }
    
    override fun onCleared() {
        super.onCleared()
        recordingJob?.cancel()
        if (_uiState.value.isRecording) {
            viewModelScope.launch {
                audioRepository.stopRecording()
            }
        }
    }
}