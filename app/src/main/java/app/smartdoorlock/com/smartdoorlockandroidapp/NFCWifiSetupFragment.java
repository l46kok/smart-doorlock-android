package app.smartdoorlock.com.smartdoorlockandroidapp;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Switch;

import java.util.ArrayList;
import java.util.List;

import app.smartdoorlock.com.smartdoorlockandroidapp.Adapters.NFCWifiSetupListAdapter;
import app.smartdoorlock.com.smartdoorlockandroidapp.Enums.WifiSignalEnum;
import app.smartdoorlock.com.smartdoorlockandroidapp.Model.NFCWifiSetupModel;


public class NFCWifiSetupFragment extends Fragment {
    private Button btnRefresh;
    private ListView lvWifi;
    private NFCWifiSetupListAdapter wifiListAdapter;
    private ArrayList<NFCWifiSetupModel> wifiModelList = new ArrayList<>();
    private Switch swWifi;

    private WifiManager wifiManager;
    private static final int PERMISSIONS_REQUEST_CODE_ACCESS_COARSE_LOCATION = 1001;


    public NFCWifiSetupFragment() {
        // Required empty public constructor
    }

    public static NFCWifiSetupFragment newInstance() {
        NFCWifiSetupFragment fragment = new NFCWifiSetupFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_nfc_wifi_setup, container, false);

        btnRefresh = (Button)v.findViewById(R.id.fragment_nfc_wifi_setup_btn_refresh);
        btnRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestPermissions();
                wifiManager.startScan();

            }
        });

        swWifi = (Switch)v.findViewById(R.id.fragment_nfc_wifi_setup_sw_wifi_enable);

        wifiListAdapter = new NFCWifiSetupListAdapter(getActivity(), wifiModelList);
        lvWifi =(ListView)v.findViewById(R.id.fragment_nfc_wifi_setup_lv_wifi);
        lvWifi.setAdapter(wifiListAdapter);

        wifiManager = (WifiManager)getActivity().getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        registerWifiReceiver();

        swWifi.setChecked(wifiManager.isWifiEnabled());

        return v;
    }

    private void requestPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M &&
                getActivity().checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(),new String[]{
                                            Manifest.permission.ACCESS_NETWORK_STATE,
                                            Manifest.permission.ACCESS_WIFI_STATE,
                                            Manifest.permission.CHANGE_WIFI_STATE,
                                            Manifest.permission.ACCESS_COARSE_LOCATION,
                                            Manifest.permission.INTERNET,
                                            Manifest.permission.ACCESS_FINE_LOCATION},
                                            PERMISSIONS_REQUEST_CODE_ACCESS_COARSE_LOCATION );
        }
    }

    private NFCWifiSetupModel getWifiInfoFromSSID(String ssid) {
        for (NFCWifiSetupModel wifiInfo : wifiModelList) {
            if (wifiInfo.getSSID().equals(ssid))
                return wifiInfo;
        }
        return null;
    }

    private void refreshWifiList(List<ScanResult> scanList) {
        wifiModelList.clear();
        for (ScanResult result : scanList) {
            //Check if same SSID has been added
            NFCWifiSetupModel existingWifi = getWifiInfoFromSSID(result.SSID);
            WifiSignalEnum signalStr = WifiSignalEnum.GetSignalFromLevel(result.level);
            if (existingWifi == null) {
                String securityType = result.capabilities.replace("[ESS]","");
                boolean isEncrypted = !TextUtils.isEmpty(securityType);
                NFCWifiSetupModel wifiInfo = new NFCWifiSetupModel(result.SSID,signalStr,isEncrypted,securityType);
                wifiModelList.add(wifiInfo);
            }
            else {
                if (signalStr.isStrongerThan(existingWifi.getSignal())) {
                    existingWifi.setSignal(signalStr);
                }
            }
        }

        wifiListAdapter.notifyDataSetChanged();
    }

    private void registerWifiReceiver(){
        IntentFilter filter = new IntentFilter();
        filter.addAction(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION);
        getActivity().registerReceiver(new BroadcastReceiver(){
            public void onReceive(Context context, Intent intent) {
                StringBuilder sb = new StringBuilder();
                List<ScanResult> scanList = wifiManager.getScanResults();
                sb.append("\n  Number Of Wifi connections :" + " " +scanList.size()+"\n\n");
                refreshWifiList(scanList);
            }

        },filter);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

}
