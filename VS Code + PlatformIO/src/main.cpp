#include <Arduino.h>

#include <WiFi.h>
#include <Firebase_ESP_Client.h>
#include <Wire.h>
#include <Adafruit_Sensor.h>

// Provide the token generation process info
#include "addons/TokenHelper.h"
// Provide the RTDB payload printing info and other helper functions
#include "addons/RTDBHelper.h"

// WiFi credentials
#define WIFI_SSID "Wokwi-GUEST"
#define WIFI_PASSWORD ""

// Firebase project credentials
#define API_KEY //"Enter Your API Key"
#define DATABASE_URL //"Enter Your Firebase Database URL"

// Firebase objects
FirebaseData fbdo;
FirebaseAuth auth;
FirebaseConfig config;

// MQ2 sensor pin
#define MQ2PIN 35

// LED pins
const int ledPins[5] = {14, 27, 26, 25, 33};
const String ledPaths[5] = {"led1", "led2", "led3", "led4", "led5"};

// Variables
float gasValue = 0.0;
unsigned long sendDataPrevMillis = 0;
bool signupOK = false;

void setup() {
  Serial.begin(115200);

  // Setup LED pins
  for (int i = 0; i < 5; i++) {
    pinMode(ledPins[i], OUTPUT);
    digitalWrite(ledPins[i], LOW);
  }

  // Connect to Wi-Fi
  WiFi.begin(WIFI_SSID, WIFI_PASSWORD);
  Serial.print("Connecting to WiFi");
  while (WiFi.status() != WL_CONNECTED) {
    Serial.print(".");
    delay(300);
  }
  Serial.println();
  Serial.print("Connected with IP: ");
  Serial.println(WiFi.localIP());

  // Configure Firebase
  config.api_key = API_KEY;
  config.database_url = DATABASE_URL;
  config.token_status_callback = tokenStatusCallback;

  if (Firebase.signUp(&config, &auth, "", "")) {
    Serial.println("Firebase signup OK");
    signupOK = true;
  } else {
    Serial.printf("Firebase signup failed: %s\n", config.signer.signupError.message.c_str());
  }

  Firebase.begin(&config, &auth);
  Firebase.reconnectWiFi(true);
}

void loop() {
  if (Firebase.ready() && signupOK && (millis() - sendDataPrevMillis > 1000 || sendDataPrevMillis == 0)) {
    sendDataPrevMillis = millis();

    // 1. Read MQ2 and send to Firebase
    gasValue = analogRead(MQ2PIN);
    gasValue = map(gasValue, 0, 4095, 0, 100);
    Serial.printf("Gas Value: %.2f%%\n", gasValue);

    if (Firebase.RTDB.setFloat(&fbdo, "/MQ2/GasValue", gasValue)) {
      Serial.println("Gas value sent to Firebase");
    } else {
      Serial.println("Failed to send gas value: " + fbdo.errorReason());
    }

    // 2. Read LED statuses and update local GPIOs
    for (int i = 0; i < 5; i++) {
      String path = "/leds/" + ledPaths[i];
      if (Firebase.RTDB.getInt(&fbdo, path)) {
        int ledState = fbdo.intData();
        digitalWrite(ledPins[i], ledState == 1 ? HIGH : LOW);
        Serial.printf("LED%d set to %d\n", i + 1, ledState);
      } else {
        Serial.printf("Failed to get %s: %s\n", ledPaths[i].c_str(), fbdo.errorReason().c_str());
      }
    }
  }
}
