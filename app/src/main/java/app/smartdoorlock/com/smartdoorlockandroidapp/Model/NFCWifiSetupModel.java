package app.smartdoorlock.com.smartdoorlockandroidapp.Model;

import android.os.Parcel;
import android.os.Parcelable;

import app.smartdoorlock.com.smartdoorlockandroidapp.Enums.WifiSignalEnum;

/**
 * Created by shuh on 11/1/2016.
 */



public class NFCWifiSetupModel implements Parcelable {
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

    // Parcelling part
    public NFCWifiSetupModel(Parcel in){
        String[] data = new String[4];

        in.readStringArray(data);
        this.ssid = data[0];
        this.signal = WifiSignalEnum.getSignalFromValue(Integer.parseInt(data[1]));
        this.isEncrypted = Boolean.valueOf(data[2]);
        this.encryptionType = data[3];
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringArray(new String[]
                            {
                                this.ssid,
                                String.valueOf(this.signal.getValue()),
                                String.valueOf(this.isEncrypted),
                                this.encryptionType
                            });
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public NFCWifiSetupModel createFromParcel(Parcel in) {
            return new NFCWifiSetupModel(in);
        }

        public NFCWifiSetupModel[] newArray(int size) {
            return new NFCWifiSetupModel[size];
        }
    };
}
