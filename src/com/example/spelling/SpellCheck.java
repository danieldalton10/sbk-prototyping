package com.example.spelling;

import java.io.File;

import lu.spellchecker.hunspell.Hunspell;
import android.os.Environment;
import android.util.Log;

public class SpellCheck {
    private final Hunspell hunspell;

    public SpellCheck() {
        String fileName = (new File(Environment.getExternalStorageDirectory(),
                "en_au.bdic")).getAbsolutePath();
        Log.d("SpellingTest", "Using dict: " + fileName);
        hunspell = new Hunspell();
        hunspell.init(fileName);
    }

    public String[] getSuggestions(String inputText) {
        final boolean result = hunspell.check(inputText);
        long startTime = System.currentTimeMillis();
        if (result) {
            Log.d("Spelling", "Duration: "
                    + (System.currentTimeMillis() - startTime));
            return null;
        }

        String[] suggestions = hunspell.getSuggestions(inputText);
        Log.d("Spelling", "Duration: "
                + (System.currentTimeMillis() - startTime));
        return suggestions;
    }
}
