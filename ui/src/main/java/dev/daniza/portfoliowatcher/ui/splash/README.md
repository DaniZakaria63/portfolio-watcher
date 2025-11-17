<div align="center">

# Smart Splash Screen
**Intelligent Android App Initialization with Session Sync & Seamless Navigation**

![Kotlin](https://img.shields.io/badge/Kotlin-1.9.22-blue?logo=kotlin)
![Jetpack Compose](https://img.shields.io/badge/Compose-2024.02.00-orange)
![Coroutines](https://img.shields.io/badge/Coroutines-1.7.3-green)
![MVVM](https://img.shields.io/badge/Architecture-MVVM-blueviolet)

</div>

---

## Overview

Splash Screen performs **critical startup tasks** silently and decides where the user should land:  
**Welcome Screen** (first-time) or **Home Screen** (returning user).

---

## What Happens in Splash?

| Step | Action | Result |
|------|-------|--------|
| 1 | **Check Network Connection** | Ensures sync is possible |
| 2 | **Session Synchronization** | Aligns app with server |
| 3 | **First-Time User?** → **Generate New Session Key** | Secure onboarding |
| 4 | **Returning User?** → **Retrieve Existing Session** | Instant access |
| 5 | **Navigate** → `WelcomeScreen` or `HomeScreen` | Personalized entry |

---

## Flow Diagram

```mermaid
Base Flow
    A[Splash Screen] --> B{Internet Available?}
    B -->|No| C[Show Offline Message / Retry]
    B -->|Yes| D{Session Exists in Storage?}
    D -->|No| E[Generate New Session Key]
    D -->|Yes| F[Load & Validate Session]
    E --> G[Sync New Session with Server]
    F --> H[Refresh Session with Server]
    G --> I{Sync Success?}
    H --> I
    I -->|Yes| J{First Time User?}
    J -->|Yes| K[Navigate to WelcomeScreen]
    J -->|No| L[Navigate to HomeScreen]
    I -->|No| M[Show Error / Retry]