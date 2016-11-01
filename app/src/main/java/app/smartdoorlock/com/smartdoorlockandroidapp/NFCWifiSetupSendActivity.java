package app.smartdoorlock.com.smartdoorlockandroidapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;

import app.smartdoorlock.com.smartdoorlockandroidapp.Enums.CommandEnum;
import app.smartdoorlock.com.smartdoorlockandroidapp.Model.NFCWifiSetupModel;
import app.smartdoorlock.com.smartdoorlockandroidapp.Utility.SPHelper;

public class NFCWifiSetupSendActivity extends AppCompatActivity {
    private NFCWifiSetupModel wifiInfo;
    private TextView tvSSID;
    private TextView tvEncryption;
    private EditText etPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nfc_wifi_setup_send);

        Bundle b = getIntent().getExtras();
        wifiInfo = b.getParcelable("WifiInfo");

        tvSSID = (TextView) findViewById(R.id.activity_nfc_wifi_setup_send_ssid);
        tvSSID.setText(wifiInfo.getSSID());
        tvEncryption = (TextView) findViewById(R.id.activity_nfc_wifi_setup_send_encryption);
        String encryption;
        if (wifiInfo.isEncrypted()) {
            encryption = wifiInfo.getEncryptionType();
            tvEncryption.setText(encryption);
        }
        else {
            encryption = "None";
            tvEncryption.setText(encryption);
        }

        etPassword = (EditText) findViewById(R.id.activity_nfc_wifi_setup_send_et_password);
        etPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                SPHelper.putString(NFCWifiSetupSendActivity.this,SPHelper.KEY_WIFI_PASSWORD,s.toString());
            }
        });

        SPHelper.putCommand(this,SPHelper.CURRENT_COMMAND, CommandEnum.WIFI_SETUP);
        SPHelper.putString(this,SPHelper.KEY_WIFI_SSID,wifiInfo.getSSID());
        SPHelper.putString(this,SPHelper.KEY_WIFI_ENCRYPTION,encryption);
    }
}
