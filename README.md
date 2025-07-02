# 🤖 Gesture-Controlled Smart Home System with Mobile Application

A smart home automation system enabling **gesture-based control** using a webcam and **mobile remote control** via Firebase, powered by the **ESP32 microcontroller**. Developed using **Python**, **PlatformIO in VS Code**, and **Firebase Realtime Database**, the system provides real-time control of home appliances, improving accessibility for everyone, especially individuals with disabilities.

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

- 👋 Gesture recognition with **Python + OpenCV**
- ⚙️ Real-time appliance control via **ESP32**
- 📱 Intuitive Android mobile app (manual + live status updates)
- ☁️ Realtime Firebase integration
- 🧠 All components synchronized through Firebase
- 🔁 "All Off" button to turn off all appliances instantly

---

## 🧠 Project Overview

Many individuals with disabilities encounter significant challenges in operating conventional home appliances. This system provides an **inclusive solution** by enabling users to control smart home appliances either:

- Hands-free using webcam-based **gesture recognition**
- Or remotely through a **mobile app**, synced in real-time via Firebase

Both gesture inputs and app commands update a centralized Firebase database, ensuring seamless and synchronized appliance control.

---

## 🏗️ System Architecture

```mermaid
graph TD
A[Webcam (Gesture Input)] --> B[Python + OpenCV Script]
B --> C[Firebase Realtime Database]
D[Mobile Application] --> C
C --> E[ESP32 with PlatformIO]
E --> F[Home Appliances (LEDs/Fans)]
