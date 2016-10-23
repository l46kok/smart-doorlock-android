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
import android.widget.Toast;

import app.smartdoorlock.com.smartdoorlockandroidapp.Enums.CommandEnum;
import app.smartdoorlock.com.smartdoorlockandroidapp.Utility.SPHelper;

import static android.nfc.NdefRecord.createMime;


public class NFCDoorlockControlFragment extends Fragment {

    private IFragmentInteractionListener mListener;

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

        return v;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction("TEST");
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof IFragmentInteractionListener) {
            mListener = (IFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
        String phoneId = SPHelper.getString(getActivity(),SPHelper.KEY_PHONE_ID);
        if (TextUtils.isEmpty(phoneId)) {
            SPHelper.putCommand(getActivity(),SPHelper.CURRENT_COMMAND, CommandEnum.NONE);
        }
        else {
            SPHelper.putCommand(getActivity(),SPHelper.CURRENT_COMMAND, CommandEnum.DOORLOCK_CONTROL);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
}
