package com.zeekstudios.zeeknotes.data.repository

import android.content.Context
import android.media.MediaRecorder
import android.os.Build
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.File
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

interface AudioRepository {
    suspend fun startRecording(): String
    suspend fun stopRecording()
    suspend fun pauseRecording()
    suspend fun resumeRecording()
    fun isRecording(): Boolean
}

@Singleton
class AudioRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : AudioRepository {
    
    private var mediaRecorder: MediaRecorder? = null
    private var currentRecordingPath: String? = null
    private var isCurrentlyRecording = false
    
    override suspend fun startRecording(): String {
        if (isCurrentlyRecording) {
            throw IllegalStateException("Recording is already in progress")
        }
        
        val outputFile = createAudioFile()
        currentRecordingPath = outputFile.absolutePath
        
        try {
            mediaRecorder = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                MediaRecorder(context)
            } else {
                @Suppress("DEPRECATION")
                MediaRecorder()
            }.apply {
                setAudioSource(MediaRecorder.AudioSource.MIC)
                setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
                setAudioEncoder(MediaRecorder.AudioEncoder.AAC)
                setOutputFile(outputFile.absolutePath)
                setAudioSamplingRate(44100)
                setAudioEncodingBitRate(128000)
                
                prepare()
                start()
            }
            
            isCurrentlyRecording = true
            return currentRecordingPath!!
            
        } catch (e: IOException) {
            mediaRecorder?.release()
            mediaRecorder = null
            isCurrentlyRecording = false
            throw e
        }
    }
    
    override suspend fun stopRecording() {
        if (!isCurrentlyRecording) {
            return
        }
        
        try {
            mediaRecorder?.apply {
                stop()
                release()
            }
        } catch (e: Exception) {
            // Handle stop recording errors
        } finally {
            mediaRecorder = null
            isCurrentlyRecording = false
        }
    }
    
    override suspend fun pauseRecording() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N && isCurrentlyRecording) {
            try {
                mediaRecorder?.pause()
            } catch (e: Exception) {
                // Handle pause errors
            }
        }
    }
    
    override suspend fun resumeRecording() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N && isCurrentlyRecording) {
            try {
                mediaRecorder?.resume()
            } catch (e: Exception) {
                // Handle resume errors
            }
        }
    }
    
    override fun isRecording(): Boolean {
        return isCurrentlyRecording
    }
    
    private fun createAudioFile(): File {
        val audioDir = File(context.filesDir, "audio")
        if (!audioDir.exists()) {
            audioDir.mkdirs()
        }
        
        val timestamp = System.currentTimeMillis()
        return File(audioDir, "recording_$timestamp.m4a")
    }
}