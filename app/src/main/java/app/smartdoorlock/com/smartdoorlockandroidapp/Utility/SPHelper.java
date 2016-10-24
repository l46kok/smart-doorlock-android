package app.smartdoorlock.com.smartdoorlockandroidapp.Utility;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import app.smartdoorlock.com.smartdoorlockandroidapp.Enums.CommandEnum;

/**
 * Created by shuh on 10/23/2016.
 */

public class SPHelper {
    private SPHelper() {}

    public static final String CURRENT_COMMAND = "CURRENT_COMMAND";
    public static final String SAVED_COMMAND = "SAVED_COMMAND";
    public static final String KEY_PHONE_ID = "PHONE_ID";
    public static final String KEY_REGISTRATION_DATE = "REG_DATE";
    public static final String KEY_REGISTRATION = "REGISTRATION";
    public static final String KEY_CONFIG_WIFI = "WIFI";

    public static void putCommand(Context ctx, String command, CommandEnum val) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(ctx);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(command, val.toString());
        editor.apply();
    }

    public static CommandEnum getCommand(Context ctx, String command) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(ctx);
        String enumStr = sp.getString(command, CommandEnum.NONE.toString());
        return CommandEnum.toCommandEnum(enumStr);
    }

    public static void putString(Context ctx, String key, String content) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(ctx);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key,content);
        editor.apply();
    }

    public static String getString(Context ctx, String key) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(ctx);
        return preferences.getString(key, "");
    }
}
