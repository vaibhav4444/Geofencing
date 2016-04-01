package com.google.android.gms.location.sample.geofencing;

import android.content.Context;
import android.telephony.NeighboringCellInfo;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.google.android.gms.location.sample.geofencing.model.NetworkDetailModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vaibhav.singhal on 3/2/2016.
 */
public class UtilityMethods {
    public static List<NetworkDetailModel> getNetworkDetails(Context context){
        List<NetworkDetailModel> listNetworkDetailModel = null;
        List<NeighboringCellInfo> neighCell = null;
        TelephonyManager telManager = ( TelephonyManager )context.getSystemService(Context.TELEPHONY_SERVICE);
        neighCell = telManager.getNeighboringCellInfo();
        String networkOperator = telManager.getNetworkOperator();
        String mcc = Constants.NOT_AVAILABLE, mnc = Constants.NOT_AVAILABLE;
        String simOperatorName = telManager.getSimOperatorName();
        if(neighCell != null && neighCell.size() > 0) {
            if (networkOperator != null) {
                mcc = networkOperator.substring(0, 3);
                mnc = networkOperator.substring(3);
            }
            listNetworkDetailModel = new ArrayList<NetworkDetailModel>();
            for (int i = 0; i < neighCell.size(); i++) {
                try {
                    NetworkDetailModel networkDetailModel = new NetworkDetailModel();
                    NeighboringCellInfo thisCell = neighCell.get(i);
                    int thisNeighCID = thisCell.getCid();
                    int thisNeighRSSI = thisCell.getRssi();
                    networkDetailModel.setCellID(Integer.toString(thisNeighCID));
                    networkDetailModel.setRssid(Integer.toString(thisNeighRSSI));
                    networkDetailModel.setLac(Integer.toString(thisCell.getLac()));
                    networkDetailModel.setNetworkType(Integer.toString(thisCell.getNetworkType()));
                    networkDetailModel.setMcc(mcc);
                    networkDetailModel.setMnc(mnc);
                    networkDetailModel.setOperatorName(simOperatorName);
                    listNetworkDetailModel.add(networkDetailModel);
                    Log.i("Info:", " cid:" + thisNeighCID + " - " + thisNeighRSSI);
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                    NeighboringCellInfo thisCell = neighCell.get(i);
                    Log.i("exception", neighCell.toString());
                }
            }

            Log.i("", "mcc:" + mcc);
            Log.i("", "mnc:" + mnc);
        }
        return listNetworkDetailModel;
    }
}
