# ZEEK Notes - Setup Guide

## Quick Start

This guide will help you set up the ZEEK Notes Android project for development.

## Prerequisites

1. **Android Studio** (Arctic Fox or later)
2. **Android SDK** (API level 24 or higher)
3. **Java Development Kit (JDK)** 11 or later
4. **Firebase Account** for backend services
5. **Google Cloud Account** for AI services

## Project Setup

### 1. Open in Android Studio

1. Launch Android Studio
2. Select "Open an Existing Project"
3. Navigate to the project folder: `c:\Users\simi1\Documents\Dev\Zeek Studios\zeek-notes`
4. Click "OK" to open the project

### 2. Sync Project

1. Android Studio will automatically detect the Gradle files
2. Click "Sync Now" when prompted
3. Wait for the sync to complete

### 3. Firebase Configuration

**Important**: The current `google-services.json` is a placeholder. You need to replace it with your actual Firebase configuration.

1. Go to [Firebase Console](https://console.firebase.google.com/)
2. Create a new project or select an existing one
3. Add an Android app with package name: `com.zeekstudios.zeeknotes`
4. Download the `google-services.json` file
5. Replace the placeholder file in `app/google-services.json`
6. Enable the following Firebase services:
   - **Authentication** (Google Sign-In)
   - **Firestore Database**
   - **Cloud Storage** (for audio files)

### 4. Google Cloud Setup (for AI Features)

1. Enable the **Speech-to-Text API** in Google Cloud Console
2. Create service account credentials
3. Configure the credentials in your Firebase project

### 5. Build the Project

1. In Android Studio, go to **Build** → **Make Project**
2. Resolve any dependency issues if they arise
3. The project should build successfully

## Running the App

### Using Android Emulator

1. Create an AVD (Android Virtual Device) in Android Studio
2. Select API level 24 or higher
3. Start the emulator
4. Click the "Run" button in Android Studio

### Using Physical Device

1. Enable **Developer Options** on your Android device
2. Enable **USB Debugging**
3. Connect your device via USB
4. Select your device in Android Studio
5. Click the "Run" button

## Development Features

### Hot Reload / Live Preview

- **Jetpack Compose Preview**: Use `@Preview` annotations to see UI changes instantly
- **Live Edit**: Enable in Android Studio for real-time code changes
- **Instant Run**: Automatically enabled for faster development cycles

### Preview Capabilities

1. **UI Components**: All Compose components have preview functions
2. **Screen Previews**: Each screen can be previewed individually
3. **Theme Previews**: Test light/dark themes instantly
4. **Device Previews**: Test different screen sizes

## Project Structure Overview

```
app/src/main/java/com/zeekstudios/zeeknotes/
├── data/
│   ├── model/              # Data classes (Note, etc.)
│   └── repository/         # Data repositories
├── di/                     # Dependency injection
├── ui/
│   ├── components/         # Reusable UI components
│   ├── screens/           # App screens
│   ├── theme/             # App theming
│   └── navigation/        # Navigation setup
├── MainActivity.kt         # Main activity
└── ZeekNotesApplication.kt # Application class
```

## Current Implementation Status

✅ **Completed**:
- Project structure and build configuration
- Basic UI components and screens
- Navigation setup
- Firebase integration setup
- Material Design 3 theming
- Dependency injection with Hilt

🔄 **In Progress**:
- Audio recording implementation
- AI transcription integration
- Note management functionality

⏳ **Planned**:
- Google Cloud Speech-to-Text integration
- Advanced AI features
- Diary module with gamification
- Meeting platform integrations

## Troubleshooting

### Common Issues

1. **Build Errors**: Ensure all dependencies are properly synced
2. **Firebase Errors**: Verify `google-services.json` is correctly configured
3. **Permission Errors**: Check that required permissions are granted
4. **Emulator Issues**: Use API level 24 or higher

### Getting Help

- Check the main `README.md` for detailed project information
- Review `BUILDING_GUIDELINES.md` for development guidelines
- Consult `USER_FLOW.md` for app functionality overview

## Next Steps

1. **Set up Firebase** with your actual project credentials
2. **Test the basic app flow** on an emulator or device
3. **Begin implementing** the audio recording functionality
4. **Integrate AI services** for transcription and analysis

The project is now ready for active development! 🚀