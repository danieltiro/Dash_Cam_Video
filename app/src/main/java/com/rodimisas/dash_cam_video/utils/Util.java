package com.rodimisas.dash_cam_video.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.rodimisas.dash_cam_video.Objects.CatCamaras;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class Util {
    
    public static String getUserMailPrefs(SharedPreferences preferences) {
        return preferences.getString("user", "");
    }

    public static String getUserPassPrefs(SharedPreferences preferences) {
        return preferences.getString("pass", "");
    }

    public static String getCookiePrefs(SharedPreferences preferences) {
        return preferences.getString("cookie", "");
    }

    public static void removeSharedPreferences(SharedPreferences preferences) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.remove("user");
        editor.remove("pass");
        editor.remove("cookie");
        editor.apply();
    }

    public static List<CatCamaras> getCamspinner() {
        return new ArrayList<CatCamaras>() {{
            add(new CatCamaras("Ninguna", "0"));
            add(new CatCamaras("Frontal", "1"));
            add(new CatCamaras("Interior", "2"));
        }};
    }
}
