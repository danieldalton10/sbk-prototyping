package com.example.spelling;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.textservice.SentenceSuggestionsInfo;
import android.view.textservice.SpellCheckerSession;
import android.view.textservice.SpellCheckerSession.SpellCheckerSessionListener;
import android.view.textservice.SuggestionsInfo;
import android.view.textservice.TextInfo;
import android.view.textservice.TextServicesManager;

public class MainActivity extends Activity {
    private final String[] words = { "Daniel", "Daniel", "aparently",
            "somethin", "correct", "htis", "daniel" };
    private SpellCheckerSession spellChecker;
    private long startTime;
    private int step = 0;

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
            spellCheckTest(words[step]);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
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
            spellCheckTest(words[step]);
        }
    }

    private void init() {
        SpellCheckerSessionListener spellCheckerListener = new SpellCheckerSessionListener() {

            @Override
            public void onGetSuggestions(SuggestionsInfo[] results) {
                StringBuilder sb = new StringBuilder("Results arrived in: "
                        + (System.currentTimeMillis() - startTime) + " ms");
                for (int j = 0; j < results.length; j++) {
                    SuggestionsInfo suggestionsInfo = results[j];
                    sb.append(" " + j + ": ");
                    for (int i = 0; i < suggestionsInfo.getSuggestionsCount(); i++) {
                        sb.append(" ").append(
                                suggestionsInfo.getSuggestionAt(i));
                    }
                }
                Log.d("SpellingTest", sb.toString());
                ++step;
                if (step < words.length) {
                    spellCheckTest(words[step]);
                }
            }

            @Override
            public void onGetSentenceSuggestions(
                    SentenceSuggestionsInfo[] results) {
            }
        };

        final TextServicesManager tsm = (TextServicesManager) getSystemService(Context.TEXT_SERVICES_MANAGER_SERVICE);
        spellChecker = tsm.newSpellCheckerSession(null, null,
                spellCheckerListener, true);
        Log.d("SpellingTest", "Init done");
    }

    // The first call here seems a little slow probably because onCreate doesn't
    // get called until now.
    // Once the dict is in memory results seem reasonable.
    private void spellCheckTest(String word) {
        if (spellChecker != null) {
            Log.d("SpellingTest", "Checking word: " + word);
            startTime = System.currentTimeMillis();
            spellChecker.getSuggestions(new TextInfo(word), 5);
        }
    }
}
