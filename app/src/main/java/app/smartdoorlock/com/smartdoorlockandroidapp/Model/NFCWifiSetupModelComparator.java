package app.smartdoorlock.com.smartdoorlockandroidapp.Model;

import java.util.Comparator;

/**
 * Created by shuh on 11/1/2016.
 */

public class NFCWifiSetupModelComparator implements Comparator<NFCWifiSetupModel> {
    @Override
    public int compare(NFCWifiSetupModel o1, NFCWifiSetupModel o2) {
        return o1.getSignal().isStrongerThan(o2.getSignal()) ? -1 : 1;
    }
}

