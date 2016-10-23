package app.smartdoorlock.com.smartdoorlockandroidapp;

import android.content.Context;
import android.net.Uri;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.NfcEvent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import app.smartdoorlock.com.smartdoorlockandroidapp.Enums.CommandEnum;
import app.smartdoorlock.com.smartdoorlockandroidapp.Utility.SPHelper;

import static android.nfc.NdefRecord.createMime;


public class NFCDoorlockControlFragment extends Fragment {
    private TextView tvInfo;

    public NFCDoorlockControlFragment() {
        // Required empty public constructor
    }

    public static NFCDoorlockControlFragment newInstance() {
        NFCDoorlockControlFragment fragment = new NFCDoorlockControlFragment();
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
        View v = inflater.inflate(R.layout.fragment_nfc_doorlock_control, container, false);
        tvInfo = (TextView) v.findViewById(R.id.fragment_nfc_doorlock_ctl_tv_info);

        String phoneId = SPHelper.getString(getActivity(),SPHelper.KEY_PHONE_ID);
        if (TextUtils.isEmpty(phoneId)) {
            tvInfo.setText("Phone ID could not be found.\n\nPlease register phone to Smart Doorlock first");
            SPHelper.putCommand(getActivity(),SPHelper.CURRENT_COMMAND, CommandEnum.NONE);
        }
        else {
            SPHelper.putCommand(getActivity(),SPHelper.CURRENT_COMMAND, CommandEnum.DOORLOCK_CONTROL);
        }

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
