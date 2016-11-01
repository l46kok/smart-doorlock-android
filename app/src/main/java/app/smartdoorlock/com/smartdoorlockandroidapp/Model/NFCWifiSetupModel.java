package app.smartdoorlock.com.smartdoorlockandroidapp.Model;

import app.smartdoorlock.com.smartdoorlockandroidapp.Enums.WifiSignalEnum;

/**
 * Created by shuh on 11/1/2016.
 */

public class NFCWifiSetupModel {
    private String ssid;
    private WifiSignalEnum signal;
    private boolean isEncrypted;
    private String encryptionType;

    public NFCWifiSetupModel(String ssid, WifiSignalEnum signal, boolean isEncrypted, String encryptionType) {
        this.ssid = ssid;
        this.signal = signal;
        this.isEncrypted = isEncrypted;
        this.encryptionType = encryptionType;
    }

    public String getSSID() {
        return ssid;
    }

    public void setSsid(String ssid) {
        this.ssid = ssid;
    }

    public WifiSignalEnum getSignal() {
        return signal;
    }

    public void setSignal(WifiSignalEnum signal) {
        this.signal = signal;
    }

    public String getEncryptionType() {
        return encryptionType;
    }

    public void setEncryptionType(String encryptionType) {
        this.encryptionType = encryptionType;
    }

    public boolean isEncrypted() {
        return isEncrypted;
    }

    public void setEncrypted(boolean encrypted) {
        isEncrypted = encrypted;
    }
}
