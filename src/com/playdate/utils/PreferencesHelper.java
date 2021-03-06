package com.playdate.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.util.HashMap;
/**
 * Provides support functions for getting preferences as well as a
 * central place for storing default preferences.
 */
public final class PreferencesHelper {
    public static class Preferences {
    	private static final String PREF_NAME = "PlayDateUserPref";
        public static final String USER_ID = "user_id";
    }

   /** This allows us to define our default preferences all in one place */
    private static HashMap<String, Object> sDefaultPreferences =
        new HashMap<String, Object>(){{
        put(Preferences.PREF_NAME, "value");
    }};

    public static SharedPreferences getDefaultPreferences(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static String getString(SharedPreferences prefs, String key) {
        return prefs.getString(key, (String) sDefaultPreferences.get(key));
    }

    /* Convenience method (use when getting only one preference) */
    public static String getString(Context context, String scanner) {
        SharedPreferences prefs = getDefaultPreferences(context);
        return getString(prefs, scanner);
    }
}