package com.zeekstudios.zeeknotes.ui.screens.note

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.Stop
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.zeekstudios.zeeknotes.ui.components.RecordingWaveform
import com.zeekstudios.zeeknotes.ui.components.TranscriptionDisplay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteScreen(
    noteType: String,
    onNavigateBack: () -> Unit,
    viewModel: NoteViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    
    LaunchedEffect(noteType) {
        viewModel.setNoteType(noteType)
    }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = when (noteType) {
                            "quick" -> "Quick Note"
                            "memo" -> "Memo"
                            "meeting" -> "Meeting"
                            else -> "Note"
                        },
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    if (uiState.hasContent) {
                        TextButton(
                            onClick = { viewModel.saveNote() }
                        ) {
                            Text("Save")
                        }
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Note type description
            Text(
                text = when (noteType) {
                    "quick" -> "Capture a quick thought or idea"
                    "memo" -> "Record a detailed memo with AI analysis"
                    "meeting" -> "Record meeting with speaker identification"
                    else -> "Create a new note"
                },
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
            )
            
            Spacer(modifier = Modifier.height(32.dp))
            
            // Recording status
            if (uiState.isRecording) {
                Text(
                    text = "Recording...",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.error
                )
                
                Spacer(modifier = Modifier.height(16.dp))
                
                // Recording duration
                Text(
                    text = formatDuration(uiState.recordingDuration),
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )
                
                Spacer(modifier = Modifier.height(24.dp))
                
                // Waveform visualization
                RecordingWaveform(
                    isRecording = uiState.isRecording,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(80.dp)
                )
            }
            
            Spacer(modifier = Modifier.weight(1f))
            
            // Transcription display
            if (uiState.transcription.isNotEmpty()) {
                TranscriptionDisplay(
                    transcription = uiState.transcription,
                    isLive = uiState.isRecording,
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(2f)
                )
                
                Spacer(modifier = Modifier.height(16.dp))
            }
            
            // Recording button
            FloatingActionButton(
                onClick = {
                    if (uiState.isRecording) {
                        viewModel.stopRecording()
                    } else {
                        viewModel.startRecording()
                    }
                },
                shape = CircleShape,
                modifier = Modifier.size(80.dp),
                containerColor = if (uiState.isRecording) {
                    MaterialTheme.colorScheme.error
                } else {
                    MaterialTheme.colorScheme.primary
                }
            ) {
                Icon(
                    imageVector = if (uiState.isRecording) Icons.Default.Stop else Icons.Default.Mic,
                    contentDescription = if (uiState.isRecording) "Stop Recording" else "Start Recording",
                    modifier = Modifier.size(32.dp),
                    tint = Color.White
                )
            }
            
            Spacer(modifier = Modifier.height(32.dp))
        }
    }
    
    // Handle save completion
    LaunchedEffect(uiState.isSaved) {
        if (uiState.isSaved) {
            onNavigateBack()
        }
    }
}

fun formatDuration(seconds: Int): String {
    val minutes = seconds / 60
    val remainingSeconds = seconds % 60
    return String.format("%02d:%02d", minutes, remainingSeconds)
}