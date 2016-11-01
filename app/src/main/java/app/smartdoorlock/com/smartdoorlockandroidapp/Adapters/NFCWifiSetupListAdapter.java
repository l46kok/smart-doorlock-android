package app.smartdoorlock.com.smartdoorlockandroidapp.Adapters;

/**
 * Created by shuh on 11/1/2016.
 */


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import app.smartdoorlock.com.smartdoorlockandroidapp.Model.NFCWifiSetupModel;
import app.smartdoorlock.com.smartdoorlockandroidapp.R;

public class NFCWifiSetupListAdapter extends ArrayAdapter<NFCWifiSetupModel> {
    private Context mCtx;
    private ArrayList<NFCWifiSetupModel> mWifiList;
    private ViewHolder mViewHolder;

    private static class ViewHolder {
        private TextView tvSSID;
        private ImageView ivWifiSignal;
        private ImageView ivWifiEncrypted;
    }

    public NFCWifiSetupListAdapter(Context context, ArrayList<NFCWifiSetupModel> wifiList) {
        super(context, R.layout.listview_nfc_wifi_setup, wifiList);
        mWifiList = wifiList;
        mCtx = context;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(this.getContext())
                    .inflate(R.layout.listview_nfc_wifi_setup, parent, false);

            mViewHolder = new ViewHolder();
            mViewHolder.tvSSID = (TextView) convertView.findViewById(R.id.lv_nfc_wifi_setup_tv_SSID_NAME);
            mViewHolder.ivWifiSignal = (ImageView) convertView.findViewById(R.id.lv_nfc_wifi_setup_iv_wifi_signal);
            mViewHolder.ivWifiEncrypted= (ImageView) convertView.findViewById(R.id.lv_nfc_wifi_setup_iv_wifi_encrypted);
            convertView.setTag(mViewHolder);
        } else {
            mViewHolder = (ViewHolder) convertView.getTag();
        }

        NFCWifiSetupModel wifiInfo = getItem(position);
        if (wifiInfo!= null) {
            mViewHolder.tvSSID.setText(wifiInfo.getSSID());
        }

        return convertView;
    }
}
