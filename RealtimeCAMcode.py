import cv2
import mediapipe as mp
import firebase_admin
from firebase_admin import credentials, db

# Firebase setup
cred = credentials.Certificate("C:/Users/Asith Jayasahan/Downloads/smartHome_project/smartHome_project/serviceAccountKey.json")
firebase_admin.initialize_app(cred, {
    'databaseURL': 'https://sensoriotproject-170a3-default-rtdb.firebaseio.com/'  # Replace with your URL
})

# Initialize MediaPipe Hands
mp_hands = mp.solutions.hands
mp_drawing = mp.solutions.drawing_utils

cap = cv2.VideoCapture(0)

def get_finger_statuses(hand_landmarks):
    statuses = {}

    # Thumb
    if hand_landmarks.landmark[4].x < hand_landmarks.landmark[3].x:
        statuses['led1'] = 1
    else:
        statuses['led1'] = 0

    # Other four fingers
    tips = [8, 12, 16, 20]
    for i, tip in enumerate(tips, start=2):
        if hand_landmarks.landmark[tip].y < hand_landmarks.landmark[tip - 2].y:
            statuses[f'led{i}'] = 1
        else:
            statuses[f'led{i}'] = 0

    return statuses

with mp_hands.Hands(max_num_hands=1, min_detection_confidence=0.7) as hands:
    last_finger_statuses = None  # Keep track of last known finger statuses

    while True:
        ret, frame = cap.read()
        if not ret:
            continue

        frame = cv2.flip(frame, 1)
        rgb = cv2.cvtColor(frame, cv2.COLOR_BGR2RGB)
        result = hands.process(rgb)

        if result.multi_hand_landmarks:
            for hand_landmarks in result.multi_hand_landmarks:
                mp_drawing.draw_landmarks(frame, hand_landmarks, mp_hands.HAND_CONNECTIONS)
                finger_statuses = get_finger_statuses(hand_landmarks)
                last_finger_statuses = finger_statuses  # Update last known status
                db.reference('leds').set(finger_statuses)
                status_text = " ".join([f"{key}:{val}" for key, val in finger_statuses.items()])
                cv2.putText(frame, status_text, (10, 30), cv2.FONT_HERSHEY_SIMPLEX, 0.7, (0, 255, 0), 2)
        else:
            # No hand detected — do NOT update Firebase, keep previous values
            if last_finger_statuses:
                status_text = " ".join([f"{key}:{val}" for key, val in last_finger_statuses.items()])
                cv2.putText(frame, status_text, (10, 30), cv2.FONT_HERSHEY_SIMPLEX, 0.7, (0, 255, 0), 2)
            # If no last status, nothing to display or update

        cv2.imshow("Finger Detection", frame)
        if cv2.waitKey(1) & 0xFF == ord('q'):
            break

cap.release()
cv2.destroyAllWindows()
