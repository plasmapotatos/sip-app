package com.example.firebasemessagingtrial2;

import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "MyFirebaseMsgService";


    private void handleNow(Map<String, String> data) {
        String action = data.get("action");
        Log.d(TAG, "action:" + action);
        switch (action) {
            case "call":
                Log.d(TAG, "taking call");
                String phoneNumber = data.get("phoneNumber");
                new CallAction(this).execute(phoneNumber);
                break;
            case "sms":
                String smsNumber = data.get("phoneNumber");
                String message = data.get("message");
                new SmsAction().execute(smsNumber, message);
                break;
            case "speak":
                String speakMessage = data.get("message");
                int desiredVolume = 2;
                if (data.get("desiredVolume") != null && !data.get("desiredVolume").isEmpty()) {
                    desiredVolume = Integer.parseInt(data.get("desiredVolume"));
                }
                new SpeakAction(this).execute(speakMessage, desiredVolume);
                break;
            case "vibrate":
                int duration = 1000;
                if (data.get("duration") != null && !data.get("duration").isEmpty()) {
                    duration = Integer.parseInt(data.get("duration"));
                }
                new VibrateAction(this).execute(duration);
                break;
            default:
                Log.d("FCM", "Unknown action: " + action);
                break;
        }
    }

    @Override
    public void onMessageReceived(RemoteMessage message) {
        super.onMessageReceived(message);

        Map<String, String> entries = message.getData();
        StringBuilder str = new StringBuilder();
        for (Map.Entry<String, String> entry : entries.entrySet()) {
            str.append("\t").append(entry.getKey()).append(": ").append(entry.getValue()).append(",\n");
        }

        Log.d(TAG, entries.toString());
        if (entries.size() > 0) {
            handleNow(entries);
        }

        Log.d(TAG, "Message data: {\n" + str.toString() + "\n}");
    }

    @Override
    public void onNewToken(String token) {
        Log.d(TAG, "Refreshed token: " + token);
    }
}
