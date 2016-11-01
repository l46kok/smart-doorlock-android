package app.smartdoorlock.com.smartdoorlockandroidapp.Enums;

/**
 * Created by shuh on 11/1/2016.
 */

public enum WifiSignalEnum {

    FIVE(5),FOUR(4),THREE(3),TWO(2),ONE(1),NONE(0);

    private Integer strength;

    WifiSignalEnum(int strength) {
        this.strength = strength;
    }

    public int getValue() {
        return strength;
    }

    public static WifiSignalEnum getSignalFromValue(int val) {
        switch (val) {
            case 5:
                return FIVE;
            case 4:
                return FOUR;
            case 3:
                return THREE;
            case 2:
                return TWO;
            case 1:
                return ONE;
            case 0:
                return NONE;
        }
        return null;
    }

    public boolean isStrongerThan(WifiSignalEnum other) {
        return this.strength > other.strength;
    }

    public static WifiSignalEnum GetSignalFromLevel(int level) {
        if (level >= -50) {
            return FIVE;
        }
        else if (level >= -65) {
            return FOUR;
        }
        else if (level >= -75) {
            return THREE;
        }
        else if (level >= -85) {
            return TWO;
        }
        else if (level >= -95) {
            return ONE;
        }
        else {
            return NONE;
        }
    }
}
