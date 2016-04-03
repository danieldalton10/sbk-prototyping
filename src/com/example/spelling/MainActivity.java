package com.example.spelling;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;

public class MainActivity extends Activity {
    private final String[] words = { "stuf", "daniel", "d", "da", "dan",
            "dani", "danie", "daniel", "daniel", "daniel", "stuf", "daniel" };
    private SpellCheck spellCheck;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (setup()) {
            init();
            spellCheckTest();
        }
    }

    private boolean setup() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[] { Manifest.permission.READ_EXTERNAL_STORAGE },
                    0);
            return false;
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
            String[] permissions, int[] grantResults) {
        if (requestCode == 0 && permissions.length > 0
                && grantResults.length > 0 && grantResults[0] != -1) {
            init();
            spellCheckTest();
        }
    }

    private void init() {
        spellCheck = new SpellCheck();
    }

    private void spellCheckTest() {
        if (spellCheck != null) {
            for (final String word : words) {
                Log.d("Spelling", "Checking word: " + word);
                String[] results = spellCheck.getSuggestions(word);
                if (results != null) {
                    StringBuilder sb = new StringBuilder();
                    for (String result : results) {
                        sb.append(result).append(" ");
                    }
                    Log.d("Spelling", "Results: " + sb.toString());
                }
                Log.d("Spelling", "Next: ");
            }
        }
    }
}
