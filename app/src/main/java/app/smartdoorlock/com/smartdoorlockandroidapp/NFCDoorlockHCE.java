package app.smartdoorlock.com.smartdoorlockandroidapp;

import android.content.Intent;
import android.nfc.cardemulation.HostApduService;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

import app.smartdoorlock.com.smartdoorlockandroidapp.Enums.CommandEnum;
import app.smartdoorlock.com.smartdoorlockandroidapp.Utility.SPHelper;

import static app.smartdoorlock.com.smartdoorlockandroidapp.Enums.CommandEnum.DOORLOCK_CONTROL;
import static app.smartdoorlock.com.smartdoorlockandroidapp.Enums.CommandEnum.DOORLOCK_REGISTRATION;
import static app.smartdoorlock.com.smartdoorlockandroidapp.Enums.CommandEnum.WIFI_SETUP;
import static app.smartdoorlock.com.smartdoorlockandroidapp.Utility.SPHelper.KEY_REGISTRATION_DATE;

/**
 * Created by shuh on 10/22/2016.
 */

public class NFCDoorlockHCE extends HostApduService {
    private static final String TAG = "CardService";
    // AID for smart doorlock
    private static final String SMART_DOORLOCK_AID = "D2760000850101";
    // ISO-DEP command HEADER for selecting an AID.
    // Format: [Class | Instruction | Parameter 1 | Parameter 2]
    private static final String SELECT_APDU_HEADER = "00A40400";
    // "OK" status word sent in response to SELECT AID command (0x9000)
    private static final byte[] SELECT_OK_SW = HexStringToByteArray("9000");
    // "UNKNOWN" status word sent in response to invalid APDU command (0x0000)
    private static final byte[] UNKNOWN_CMD_SW = HexStringToByteArray("0000");
    private static final byte[] SELECT_APDU = BuildSelectApdu(SMART_DOORLOCK_AID);

    public static final String BR_REFRESH_FILTER = "NFC_REG_REFRESH";

    /**
     * Called if the connection to the NFC card is lost, in order to let the application know the
     * cause for the disconnection (either a lost link, or another AID being selected by the
     * reader).
     *
     * @param reason Either DEACTIVATION_LINK_LOSS or DEACTIVATION_DESELECTED
     */
    @Override
    public void onDeactivated(int reason) { }

    /**
     * Processes APDU commands from TRF7970A
     *
     * Handles SELECT AID command.
     *
     * Response is appended to R_APDU OK status (90 00) + Payload
     */
    // BEGIN_INCLUDE(processCommandApdu)
    @Override
    public byte[] processCommandApdu(byte[] commandApdu, Bundle extras) {
        Log.i(TAG, "Received APDU: " + ByteArrayToHexString(commandApdu));
        if (!Arrays.equals(SELECT_APDU, commandApdu)) {
            return UNKNOWN_CMD_SW;
        }
        CommandEnum enumVal = SPHelper.getCommand(NFCDoorlockHCE.this,SPHelper.CURRENT_COMMAND);
        // If the APDU matches the SELECT AID command for this service,
        // send the loyalty card account number, followed by a SELECT_OK status trailer (0x9000).
        String payload;
        switch (enumVal) {
            case DOORLOCK_CONTROL:
                String phoneId = SPHelper.getString(NFCDoorlockHCE.this,SPHelper.KEY_PHONE_ID);
                payload = DOORLOCK_CONTROL.toString() + "|" + phoneId;
                return getAckPayload(payload);
            case DOORLOCK_REGISTRATION:
                String newId = SPHelper.getString(NFCDoorlockHCE.this,SPHelper.KEY_PHONE_ID);
                if (!TextUtils.isEmpty(newId)) {
                    SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
                    payload = DOORLOCK_REGISTRATION.toString() + "|" + newId + "|" + sdf.format(new Date());
                    Toast.makeText(NFCDoorlockHCE.this,"Successfully registered phone",Toast.LENGTH_LONG).show();
                    sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
                    SPHelper.putString(NFCDoorlockHCE.this,KEY_REGISTRATION_DATE,sdf.format(new Date()));

                    //Broadcast message to refresh fragment
                    Intent intent = new Intent();
                    intent.setAction(BR_REFRESH_FILTER);
                    intent.putExtra(BR_REFRESH_FILTER, 1);
                    sendBroadcast(intent);

                    return getAckPayload(payload);
                }
                break;
            case WIFI_SETUP:
                String ssid = SPHelper.getString(NFCDoorlockHCE.this,SPHelper.KEY_WIFI_SSID);
                String password = SPHelper.getString(NFCDoorlockHCE.this,SPHelper.KEY_WIFI_PASSWORD);
                String encryption = SPHelper.getString(NFCDoorlockHCE.this,SPHelper.KEY_WIFI_ENCRYPTION);
                payload = WIFI_SETUP.toString() + "|" + ssid + "|" + password + "|" + encryption;
                return getAckPayload(payload);
            default:
                break;
        }
        return null;
    }

    private byte[] getAckPayload(String content) {
        byte[] payload = content.getBytes();
        Log.i(TAG, "NFC Responding with: " + content);
        Log.i(TAG, "Byte response: " + ByteArrayToHexString(payload));
        return ConcatArrays(SELECT_OK_SW, payload);
    }
    // END_INCLUDE(processCommandApdu)

    /**
     * Build APDU for SELECT AID command. This command indicates which service a reader is
     * interested in communicating with. See ISO 7816-4.
     *
     * @param aid Application ID (AID) to select
     * @return APDU for SELECT AID command
     */
    public static byte[] BuildSelectApdu(String aid) {
        // Format: [CLASS | INSTRUCTION | PARAMETER 1 | PARAMETER 2 | LENGTH | DATA | LE]
        return HexStringToByteArray(SELECT_APDU_HEADER + String.format("%02X",
                aid.length() / 2) + aid + "00");
    }

    /**
     * Utility method to convert a byte array to a hexadecimal string.
     *
     * @param bytes Bytes to convert
     * @return String, containing hexadecimal representation.
     */
    public static String ByteArrayToHexString(byte[] bytes) {
        final char[] hexArray = {'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};
        char[] hexChars = new char[bytes.length * 2]; // Each byte has two hex characters (nibbles)
        int v;
        for (int j = 0; j < bytes.length; j++) {
            v = bytes[j] & 0xFF; // Cast bytes[j] to int, treating as unsigned value
            hexChars[j * 2] = hexArray[v >>> 4]; // Select hex character from upper nibble
            hexChars[j * 2 + 1] = hexArray[v & 0x0F]; // Select hex character from lower nibble
        }
        return new String(hexChars);
    }

    /**
     * Utility method to convert a hexadecimal string to a byte string.
     *
     * <p>Behavior with input strings containing non-hexadecimal characters is undefined.
     *
     * @param s String containing hexadecimal characters to convert
     * @return Byte array generated from input
     * @throws java.lang.IllegalArgumentException if input length is incorrect
     */
    public static byte[] HexStringToByteArray(String s) throws IllegalArgumentException {
        int len = s.length();
        if (len % 2 == 1) {
            throw new IllegalArgumentException("Hex string must have even number of characters");
        }
        byte[] data = new byte[len / 2]; // Allocate 1 byte per 2 hex characters
        for (int i = 0; i < len; i += 2) {
            // Convert each character into a integer (base-16), then bit-shift into place
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                    + Character.digit(s.charAt(i+1), 16));
        }
        return data;
    }

    /**
     * Utility method to concatenate two byte arrays.
     * @param first First array
     * @param rest Any remaining arrays
     * @return Concatenated copy of input arrays
     */
    public static byte[] ConcatArrays(byte[] first, byte[]... rest) {
        int totalLength = first.length;
        for (byte[] array : rest) {
            totalLength += array.length;
        }
        byte[] result = Arrays.copyOf(first, totalLength);
        int offset = first.length;
        for (byte[] array : rest) {
            System.arraycopy(array, 0, result, offset, array.length);
            offset += array.length;
        }
        return result;
    }
}
