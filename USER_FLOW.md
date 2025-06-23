# ZEEK NOTE User Flow

```mermaid
graph TD
    A[Start App] --> B{User Status?}

    B -- New User --> C[Onboarding & Signup]
    B -- Existing User --> D[Login]

    C --> E[Notes List (Home Screen)]
    D --> E

    E -- Sorted/Filterable Grid --> K[Note List]
    E -- Tap New Button (Bottom Middle) --> F{Choose Recording Mode}

    F -- Quick Note --> G[Quick Note Recording]
    G --> H[Real-time Transcription]
    H --> I[Auto-Save & Playback]
    I --> J[Basic Text Editing]
    J --> J2[User Types/Edits Note]
    J2 --> K[Note List]

    F -- Memo Recording --> L[Memo Recording]
    L --> M[Simple Recording & Playback]
    M --> M2[User Types Note]
    M2 --> N{Save/Discard?}
    N -- Save --> K
    N -- Discard --> E

    F -- Meeting Recording --> O[Meeting Recording]
    O --> P[Advanced Transcription]
    P --> Q[Tagging/Highlighting]
    Q --> R[Summary Generation (Future)]
    R --> S[Playback with Timestamps]
    S --> K

    K -- Select Note --> T[Note Details View]
    T -- Edit --> J
    T -- Play Audio --> M
    T -- Share --> U[Share Note]
    T -- Delete --> V[Confirm Delete]
    V --> K

    K -- Search/Filter --> W[Filtered Note List]
    W --> T

    E -- Tap Premium --> X[Premium Subscription Page]
    X --> Y[Feature Comparison]
    Y --> Z[Initiate Purchase (Google Play Billing)]
    Z --> AA[Subscription Management]
    AA --> E

    E -- Tap Settings --> BB[Settings & Profile]
    BB -- Account Settings --> CC[Manage Account]
    BB -- Notification Settings --> DD[Configure Notifications]
    BB -- Transcription Settings --> EE[Adjust Transcription Prefs]
    BB -- Storage Management --> FF[View Storage/Clear Cache]
    BB -- Help & Support --> GG[Access Help/Support]
    BB -- About App --> HH[View App Info]
    CC --> BB
    DD --> BB
    EE --> BB
    FF --> BB
    GG --> BB
    HH --> BB

    K --> E
    AA --> E
    BB --> E
    F --> E
```