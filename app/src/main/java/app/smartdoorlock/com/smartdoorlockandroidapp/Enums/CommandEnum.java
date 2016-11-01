package app.smartdoorlock.com.smartdoorlockandroidapp.Enums;

/**
 * Created by shuh on 10/23/2016.
 */

public enum CommandEnum {
    NONE, DOORLOCK_CONTROL, DOORLOCK_REGISTRATION, WIFI_SETUP;

    public static CommandEnum toCommandEnum (String enumString) {
        try {
            return valueOf(enumString);
        } catch (Exception ex) {
            // For error cases
            return NONE;
        }
    }
}

