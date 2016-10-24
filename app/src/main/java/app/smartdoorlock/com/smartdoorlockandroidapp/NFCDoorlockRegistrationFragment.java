package app.smartdoorlock.com.smartdoorlockandroidapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.math.BigInteger;
import java.security.SecureRandom;

import app.smartdoorlock.com.smartdoorlockandroidapp.Enums.CommandEnum;
import app.smartdoorlock.com.smartdoorlockandroidapp.Utility.SPHelper;


public class NFCDoorlockRegistrationFragment extends Fragment {
    private TextView tvPhoneId;
    private TextView tvRegDate;
    private Button btnGenId;

    private SecureRandom random = new SecureRandom();

    private RegisterReceiver regEventReceiver;

    public NFCDoorlockRegistrationFragment() {
        // Required empty public constructor
    }

    public static NFCDoorlockRegistrationFragment newInstance() {
        NFCDoorlockRegistrationFragment fragment = new NFCDoorlockRegistrationFragment();
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

        View v = inflater.inflate(R.layout.fragment_nfc_doorlock_registration, container, false);
        tvPhoneId = (TextView) v.findViewById(R.id.fragment_nfc_doorlock_reg_tv_smartphone_id);
        tvRegDate = (TextView) v.findViewById(R.id.fragment_nfc_doorlock_reg_tv_reg_date);

        SPHelper.putCommand(getActivity(),SPHelper.CURRENT_COMMAND, CommandEnum.DOORLOCK_REGISTRATION);
        String phoneId = SPHelper.getString(getActivity(),SPHelper.KEY_PHONE_ID);
        if (!TextUtils.isEmpty(phoneId)) {
            tvPhoneId.setText(phoneId);
        }

        tvRegDate.setText(SPHelper.getString(getActivity(),SPHelper.KEY_REGISTRATION_DATE));

        btnGenId = (Button) v.findViewById(R.id.fragment_nfc_doorlock_btn_gen_id);
        btnGenId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String randId = new BigInteger(130, random).toString(32);
                tvPhoneId.setText(randId);
                SPHelper.putString(getActivity(),SPHelper.KEY_PHONE_ID,randId);
            }
        });
        return v;
    }

    @Override
    public void onAttach(Context context) {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(NFCDoorlockHCE.BR_REFRESH_FILTER);

        regEventReceiver = new RegisterReceiver();
        getActivity().registerReceiver(regEventReceiver, intentFilter);

        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        getActivity().unregisterReceiver(regEventReceiver);
        regEventReceiver = null;
        super.onDetach();
    }

    private class RegisterReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context arg0, Intent arg1) {
            int refreshScreen = arg1.getIntExtra(NFCDoorlockHCE.BR_REFRESH_FILTER, 0);
            if (refreshScreen == 1) {
                tvRegDate.setText(SPHelper.getString(getActivity(),SPHelper.KEY_REGISTRATION_DATE));
            }
        }
    }

}
