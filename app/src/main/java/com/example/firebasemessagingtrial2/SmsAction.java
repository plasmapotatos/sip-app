package com.example.firebasemessagingtrial2;

import android.telephony.SmsManager;

public class SmsAction {

    public void execute(String phoneNumber, String message) {
        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage(phoneNumber, null, message, null, null);
    }
}
