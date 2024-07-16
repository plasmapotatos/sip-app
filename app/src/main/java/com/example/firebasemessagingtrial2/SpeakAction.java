package com.example.firebasemessagingtrial2;

import android.content.Context;
import android.speech.tts.TextToSpeech;
import android.media.AudioManager;
import android.util.Log;

import java.util.Locale;

public class SpeakAction {

    private TextToSpeech tts;
    public boolean textToSpeechIsInitialized = false;  // <--- add this line

    public SpeakAction(Context context) {
        tts = new TextToSpeech(context, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {
                if(i != TextToSpeech.ERROR)
                    tts.setLanguage(Locale.ENGLISH);
                textToSpeechIsInitialized = true;
            }
        });
    }

    public void execute(String message, int desiredVolume) {
        while (true) {
            if (textToSpeechIsInitialized) {
                break;
            }
        }

        if (this.textToSpeechIsInitialized) {
            Log.d("asdf", message);
            tts.speak(message, TextToSpeech.QUEUE_FLUSH, null);
        }
        else {
            Log.d("asdf", "not initialized");
        }
    }
}
