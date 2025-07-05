# ZEEK Notes

An AI-powered note-taking Android app that transforms voice recordings into intelligent, organized notes.

## Features

- 🎤 **Voice Recording**: Three recording modes - Quick Note, Memo, and Meeting
- 🤖 **AI Transcription**: Real-time speech-to-text conversion
- 📝 **Smart Organization**: AI-powered summaries, key points, and action items
- 👥 **Speaker Identification**: Identify different speakers in meetings
- ☁️ **Cloud Sync**: Firebase integration for cross-device synchronization
- 🎨 **Modern UI**: Material Design 3 with Jetpack Compose
- 💳 **Google Play Billing**: Subscription management for premium features

## Tech Stack

- **Language**: Kotlin
- **UI Framework**: Jetpack Compose
- **Architecture**: MVVM with Repository pattern
- **Dependency Injection**: Dagger Hilt
- **Backend**: Firebase (Auth, Firestore)
- **Audio Recording**: AndroidX Media3
- **Navigation**: Jetpack Navigation Compose
- **Billing**: Google Play Billing

## Setup Instructions

### Prerequisites

1. Android Studio Arctic Fox or later
2. Android SDK 24 (API level 24) or higher
3. Firebase project setup
4. Google Cloud project with Speech-to-Text API enabled

### Firebase Setup

1. Create a new Firebase project at [Firebase Console](https://console.firebase.google.com/)
2. Add an Android app to your project with package name: `com.zeekstudios.zeeknotes`
3. Download the `google-services.json` file
4. Replace the placeholder `google-services.json` in the `app/` directory
5. Enable Authentication with Google Sign-In
6. Enable Firestore Database

### Google Cloud Setup

1. Enable the Speech-to-Text API in your Google Cloud project
2. Create service account credentials
3. Add the credentials to your Firebase project

### Build and Run

1. Clone the repository
2. Open the project in Android Studio
3. Replace the placeholder `google-services.json` with your actual Firebase configuration
4. Sync the project with Gradle files
5. Run the app on an emulator or physical device

## Project Structure

```
app/
├── src/main/java/com/zeekstudios/zeeknotes/
│   ├── data/
│   │   ├── model/          # Data models
│   │   └── repository/     # Repository implementations
│   ├── di/                 # Dependency injection modules
│   ├── presentation/
│   │   ├── components/     # Reusable UI components
│   │   ├── screens/        # Screen composables
│   │   ├── theme/          # App theming
│   │   └── viewmodel/      # ViewModels
│   ├── navigation/         # Navigation setup
│   └── ZeekNotesApplication.kt
└── src/main/res/
    ├── values/
    │   ├── strings.xml
    │   └── themes.xml
    └── AndroidManifest.xml
```

## Development Phases

### Phase 1: Core Development (Current)
- ✅ Project setup and architecture
- ✅ Basic UI components and navigation
- ✅ Firebase integration
- 🔄 Audio recording functionality
- 🔄 Basic transcription
- 🔄 Note management

### Phase 2: AI Integration
- Speech-to-text with Google Cloud
- AI-powered summaries
- Key points extraction
- Action items identification
- Speaker identification

### Phase 3: Advanced Features
- Diary module with gamification
- Advanced search and filtering
- Export functionality
- Offline support
- Performance optimizations

### Phase 4: Future Enhancements
- Integration with meeting platforms (Zoom, Teams, Google Meet)
- Advanced AI features
- Collaboration features
- Desktop companion app

## Development Setup

The project includes hot-reloading capabilities for rapid UI development:

1. Use Android Studio's Compose Preview for instant UI feedback
2. Enable Live Edit in Android Studio for real-time code changes
3. Use the Android Emulator with Quick Boot for faster testing

## Contributing

1. Follow the existing code style and architecture patterns
2. Write unit tests for new features
3. Update documentation for significant changes
4. Test on multiple device sizes and Android versions

## License

This project is proprietary software developed by Zeek Studios.