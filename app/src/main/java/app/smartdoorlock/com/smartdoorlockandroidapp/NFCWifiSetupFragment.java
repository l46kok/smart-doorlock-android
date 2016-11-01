package app.smartdoorlock.com.smartdoorlockandroidapp;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

import app.smartdoorlock.com.smartdoorlockandroidapp.Adapters.NFCWifiSetupListAdapter;
import app.smartdoorlock.com.smartdoorlockandroidapp.Enums.WifiSignalEnum;
import app.smartdoorlock.com.smartdoorlockandroidapp.Model.NFCWifiSetupModel;


public class NFCWifiSetupFragment extends Fragment {
    private Button btnRefresh;
    private ListView lvWifi;
    private NFCWifiSetupListAdapter wifiListAdapter;
    private ArrayList<NFCWifiSetupModel> wifiModelList = new ArrayList<>();

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
                wifiModelList.add(new NFCWifiSetupModel("TEST3","", WifiSignalEnum.FOUR,true,"IDK"));
                wifiListAdapter.notifyDataSetChanged();
            }
        });

        wifiModelList.add(new NFCWifiSetupModel("TEST1","", WifiSignalEnum.FIVE,true,"IDK"));
        wifiModelList.add(new NFCWifiSetupModel("TEST2","", WifiSignalEnum.FOUR,true,"IDK"));

        wifiListAdapter = new NFCWifiSetupListAdapter(getActivity(), wifiModelList);
        lvWifi =(ListView)v.findViewById(R.id.fragment_nfc_wifi_setup_lv_wifi);
        lvWifi.setAdapter(wifiListAdapter);

        return v;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

}
