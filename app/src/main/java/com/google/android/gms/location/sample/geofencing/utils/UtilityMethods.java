package com.google.android.gms.location.sample.geofencing.utils;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.os.Build;
import android.telephony.CellIdentityCdma;
import android.telephony.CellIdentityGsm;
import android.telephony.CellInfo;
import android.telephony.CellInfoCdma;
import android.telephony.CellInfoGsm;
import android.telephony.NeighboringCellInfo;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.google.android.gms.location.sample.geofencing.Constants;
import com.google.android.gms.location.sample.geofencing.GeoFenceApp;
import com.google.android.gms.location.sample.geofencing.model.NetworkDetailModel;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

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
        List<CellInfo> cellInfoList=cellInfoList = telManager.getAllCellInfo();
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
    public static List<NetworkDetailModel> getAllCellInfo(Context context){
        List<CellInfo> cellInfoList = null;
        List<NetworkDetailModel> listNetworkDetailModel = null;
        TelephonyManager telManager = ( TelephonyManager )context.getSystemService(Context.TELEPHONY_SERVICE);
        String networkOperator = telManager.getNetworkOperator();
        String sinOPerator = telManager.getSimOperatorName();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            cellInfoList = telManager.getAllCellInfo();
        }
        if(cellInfoList != null && cellInfoList.size() > 0) {
            listNetworkDetailModel = new ArrayList<NetworkDetailModel>();
            for(CellInfo cellInfo : cellInfoList){
                if(cellInfo instanceof CellInfoCdma){
                    CellInfoCdma cellInfoCdma = (CellInfoCdma) cellInfo;
                    CellIdentityCdma cellIdentityCdma = null;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                        cellIdentityCdma = cellInfoCdma.getCellIdentity();
                    }

                }
                else if(cellInfo instanceof CellInfoGsm){
                    CellInfoGsm cellInfoGsm = (CellInfoGsm) cellInfo;
                    CellIdentityGsm thisCell = null;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                        thisCell = cellInfoGsm.getCellIdentity();
                        NetworkDetailModel networkDetailModel = new NetworkDetailModel();
                        int thisNeighCID = thisCell.getCid();
                        int thisNeighRSSI = -7;
                        networkDetailModel.setCellID(Integer.toString(thisNeighCID));
                        networkDetailModel.setRssid(Integer.toString(thisNeighRSSI));
                        networkDetailModel.setLac(Integer.toString(thisCell.getLac()));
                        networkDetailModel.setNetworkType("GSM");
                        networkDetailModel.setMcc(Integer.toString(thisCell.getMcc()));
                        networkDetailModel.setMnc(Integer.toString(thisCell.getMnc()));
                        networkDetailModel.setOperatorName(sinOPerator);
                        listNetworkDetailModel.add(networkDetailModel);

                    }
                }
            }
        }
        return listNetworkDetailModel;
    }
    public static String getDateTime(){
        Calendar cal = Calendar.getInstance();
        DateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        return format.format(cal.getTime());
    }
    public static String getAddress(double latitude, double longitude){
        String adr = "not avaialble";
        Geocoder geocoder;
        List<Address> addresses = null;
        geocoder = new Geocoder(GeoFenceApp.getInstance(), Locale.getDefault());

        try {
            addresses = geocoder.getFromLocation(latitude, longitude, 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(addresses != null && !addresses.isEmpty()){
            adr = addresses.get(0).getAddressLine(0)+" "+addresses.get(0).getSubLocality() + " "+addresses.get(0).getLocality()+" "+addresses.get(0).getPostalCode();;
        }
        return  adr;
    }
}
