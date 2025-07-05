package com.zeekstudios.zeeknotes.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import kotlin.math.sin
import kotlin.random.Random

@Composable
fun RecordingWaveform(
    isRecording: Boolean,
    modifier: Modifier = Modifier
) {
    val primaryColor = MaterialTheme.colorScheme.primary
    val animationSpec = infiniteRepeatable<Float>(
        animation = tween(1000, easing = LinearEasing),
        repeatMode = RepeatMode.Restart
    )
    
    val animatedValue by animateFloatAsState(
        targetValue = if (isRecording) 1f else 0f,
        animationSpec = tween(300),
        label = "waveform_animation"
    )
    
    val infiniteTransition = rememberInfiniteTransition(label = "waveform_infinite")
    val phase by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = animationSpec,
        label = "waveform_phase"
    )
    
    Canvas(
        modifier = modifier.fillMaxSize()
    ) {
        val width = size.width
        val height = size.height
        val centerY = height / 2
        
        if (isRecording) {
            // Draw animated waveform
            val barCount = 50
            val barWidth = width / barCount
            
            for (i in 0 until barCount) {
                val x = i * barWidth + barWidth / 2
                
                // Create varying heights with animation
                val baseHeight = height * 0.1f
                val maxHeight = height * 0.8f
                
                val animatedHeight = baseHeight + 
                    (maxHeight - baseHeight) * 
                    (sin((phase + i * 10) * Math.PI / 180).toFloat().coerceAtLeast(0f)) * 
                    animatedValue * 
                    (0.5f + Random.nextFloat() * 0.5f)
                
                val barHeight = animatedHeight.coerceIn(baseHeight, maxHeight)
                
                drawLine(
                    color = primaryColor,
                    start = Offset(x, centerY - barHeight / 2),
                    end = Offset(x, centerY + barHeight / 2),
                    strokeWidth = barWidth * 0.6f,
                    cap = StrokeCap.Round
                )
            }
        } else {
            // Draw static line when not recording
            drawLine(
                color = primaryColor.copy(alpha = 0.3f),
                start = Offset(0f, centerY),
                end = Offset(width, centerY),
                strokeWidth = 2f,
                cap = StrokeCap.Round
            )
        }
    }
}