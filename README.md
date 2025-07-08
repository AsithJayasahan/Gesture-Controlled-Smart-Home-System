# 🤖 Gesture-Controlled Smart Home System with Mobile Application

A smart home automation project that allows users to control household appliances using **hand gestures** detected through a webcam or via a **mobile application**. Built with **ESP32**, **Python (OpenCV + MediaPipe)**, **Firebase Realtime Database**, and a user-friendly **mobile app**, this project promotes accessibility and real-time smart home control.

---

## 📌 Table of Contents

- [🌟 Features](#-features)  
- [🧠 Project Overview](#-project-overview)  
- [🏗️ System Architecture](#-system-architecture)  
- [🧰 Hardware Components](#-hardware-components)  
- [💻 Software Stack](#-software-stack)  
- [⚙️ Setup Instructions](#-setup-instructions)  
- [🔥 Firebase Setup](#-firebase-setup)  
- [📁 Folder Structure](#-folder-structure)  
- [🖼️ Demo Screenshots](#-demo-screenshots)  
- [🚀 Future Enhancements](#-future-enhancements)  
- [👥 Team](#-team)  
- [📄 License](#-license)  

---

## 🌟 Features

- 👋 Real-time gesture recognition using **Python + OpenCV**
- 🧠 ESP32 integration for controlling physical devices
- 📱 Mobile app with live device status and control
- ☁️ Firebase Realtime Database for cloud sync
- 🔁 Synchronized control from both webcam and mobile app
- ⚡ One-tap "All Off" feature on mobile for convenience

---

## 🧠 Project Overview

This project bridges the gap between user accessibility and home automation. Designed to help individuals (especially differently-abled users), it allows home appliances to be controlled using either:
- **Hand gestures** via a webcam and OpenCV
- **Remote commands** via an Android mobile application

All state changes are updated and monitored in **Firebase Realtime Database**, and acted upon by an **ESP32 microcontroller** connected to the appliances.

---

## 🏗️ System Architecture

- Webcam captures gesture input
- Python script processes gesture and sends command to Firebase
- Firebase stores ON/OFF states
- ESP32 listens for changes and controls connected devices
- Mobile App syncs with Firebase for manual control

---

## 🧰 Hardware Components

| Component      | Description                                   |
|----------------|-----------------------------------------------|
| ESP32 Board    | Wi-Fi MCU to interface with Firebase & relays |
| Webcam         | Captures user hand gestures                   |
| LEDs / Relays  | Simulate or operate real home appliances      |
| Resistors      | Protect connected components                  |
| USB Cable      | Power and programming interface for ESP32     |
| Jumper Wires   | Circuit connections                           |

---

## 💻 Software Stack

| Layer               | Tools/Technologies                        |
|--------------------|-------------------------------------------|
| ESP32               | C++ (PlatformIO in VS Code)               |
| PC-side Gesture     | Python, OpenCV, MediaPipe                 |
| Cloud               | Firebase Realtime Database                |
| Mobile App          | Android (Java)                            |

---

## ⚙️ Setup Instructions

### ✅ Prerequisites
- [VS Code](https://code.visualstudio.com/) with PlatformIO extension
- [Python 3.9+](https://www.python.org/downloads/)
- [Firebase Console](https://console.firebase.google.com/)
- Android Studio

---

### 🔌 ESP32 Setup (PlatformIO)
1. Open the `ESP32Project/` folder in VS Code.
2. In `src/main.cpp`, replace placeholders with your Firebase URL and secret key:
   ```cpp
   const char* FIREBASE_HOST = "https://your-database.firebaseio.com/";
   const char* FIREBASE_AUTH = "your-database-secret";
   ```
3. Connect the ESP32 via USB.
4. Click “Upload” in PlatformIO to flash the code.

---

### 📷 Gesture Detection Script (Python)
1. Install required libraries:
   ```bash
   pip install opencv-python mediapipe firebase-admin
   ```
2. Make sure your webcam is working.
3. Run the Python script:
   ```bash
   python gesture_control.py
   ```
4. Detected gestures will be interpreted and corresponding commands sent to Firebase.

---

### 📱 Mobile Application
1. Open the `mobile_app/` project in Android Studio.
2. Add your `google-services.json` from Firebase to the project.
3. Connect an emulator or Android device and run the app.
4. Use the UI to:
   - Toggle devices ON/OFF
   - View real-time device status
   - Tap "All Off" to deactivate all appliances

---

## 🔥 Firebase Setup

1. Go to [Firebase Console](https://console.firebase.google.com/)
2. Create a new project and enable **Realtime Database**
3. In **Rules**, paste:
   ```json
   {
     "rules": {
       ".read": "true",
       ".write": "true"
     }
   }
   ```
4. Get your database URL and project secret.
5. Add the secret to `main.cpp` and include the `google-services.json` in your app.

---

## 📁 Folder Structure

```
Gesture-Controlled-Smart-System/
│
├── ESP32Project/         # ESP32 code using PlatformIO
│   └── src/main.cpp
│
├── gesture_control/      # Python gesture script
│   └── gesture_control.py
│
├── mobile_app/           # Android or Flutter mobile app
│
├── firebase.json         # Firebase configuration (optional)
├── images/               # Screenshots and diagrams
└── README.md             # Project documentation
```

---

## 🖼️ Demo Screenshots

Add your screenshots in the `images/` folder.

```markdown
![System Diagram](images/system-architecture.png)
![Mobile UI](images/mobile-app-ui.png)
![Gesture Control](images/gesture-demo.gif)
```

---

## 🚀 Future Enhancements

- 🔊 Add voice command support (Google Assistant)
- 🌐 Host mobile app controls via web dashboard
- 📡 Use MQTT for better IoT messaging
- 🔐 Secure Firebase with user auth
- 🧠 Add custom gesture training

---

## 👥 Team

| Name                       | GitHub Profile                                                     |
|----------------------------|--------------------------------------------------------------------|
| Suranga Prabash            | [@surangaprabash](https://github.com/surangaprabash)               |
| Asith Jayasahan            | [@AsithJayasahan](https://github.com/AsithJayasahan)               |
| Sachintha Senevirathna     | [@PKSNSenevirathna](https://github.com/PKSNSenevirathna)           |
| Farha                      | [@Farha](https://github.com/your-github-username4) |


---

