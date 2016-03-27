package com.example.spelling;

import java.io.File;

import lu.spellchecker.hunspell.Hunspell;
import android.os.Environment;
import android.service.textservice.SpellCheckerService;
import android.util.Log;
import android.view.textservice.SuggestionsInfo;
import android.view.textservice.TextInfo;

public class SpellingService extends SpellCheckerService {
    @Override
    public Session createSession() {
        return new AndroidSpellCheckerSession();
    }

    private static class AndroidSpellCheckerSession extends Session {
        private String mLocale;
        private Hunspell hunspell;

        @Override
        public void onCreate() {
            String fileName = (new File(
                    Environment.getExternalStorageDirectory(), "en_au.bdic"))
                    .getAbsolutePath();
            Log.d("SpellingTest", "Using dict: " + fileName);
            hunspell = new Hunspell();
            hunspell.init(fileName);
        }

        @Override
        public SuggestionsInfo onGetSuggestions(TextInfo textInfo,
                int suggestionsLimit) {
            final String inputText = textInfo.getText();

            final boolean result = hunspell.check(inputText);

            if (result) {
                return new SuggestionsInfo(
                        SuggestionsInfo.RESULT_ATTR_IN_THE_DICTIONARY,
                        new String[] {});
            }

            String[] suggestions = hunspell.getSuggestions(inputText);

            return new SuggestionsInfo(
                    SuggestionsInfo.RESULT_ATTR_LOOKS_LIKE_TYPO, suggestions);
        }
    }
}
