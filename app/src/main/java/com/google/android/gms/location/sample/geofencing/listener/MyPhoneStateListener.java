package com.google.android.gms.location.sample.geofencing.listener;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Environment;
import android.telephony.CellLocation;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.telephony.cdma.CdmaCellLocation;
import android.telephony.gsm.GsmCellLocation;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.location.sample.geofencing.Constants;
import com.google.android.gms.location.sample.geofencing.GeoFenceApp;
import com.google.android.gms.location.sample.geofencing.database.DataSource;
import com.google.android.gms.location.sample.geofencing.utils.LocationUtility;
import com.google.android.gms.location.sample.geofencing.utils.UtilityMethods;
import com.parse.ParseObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**

 * Created by vaibhav.singhal on 2/26/2016.
 */

public class MyPhoneStateListener extends PhoneStateListener {

    public static Boolean phoneRinging = false;
    private DataSource dataSource;

    public void onCallStateChanged(int state, String incomingNumber) {
        /*ParseObject obj = new ParseObject("State");
        obj.put("state", "state");
        obj.saveInBackground(); */
        generateNoteOnSD("myFile","call number:"+incomingNumber);
        if(dataSource == null){
            dataSource = GeoFenceApp.getInstance().getDataSource();
        }
        //long i= dataSource.insertNetworkDetails();
        switch (state) {
            case TelephonyManager.CALL_STATE_IDLE:
                Log.d("DEBUG", "IDLE");
                phoneRinging = false;
                break;
            case TelephonyManager.CALL_STATE_OFFHOOK:
                Log.d("DEBUG", "OFFHOOK");
                phoneRinging = false;
                break;
            case TelephonyManager.CALL_STATE_RINGING:
                Log.d("DEBUG", "RINGING");
                phoneRinging = true;

                break;
        }
    }
    public void generateNoteOnSD(String sFileName, String sBody){
        try
        {
            File root = new File(Environment.getExternalStorageDirectory(), "Notes");
            if (!root.exists()) {
                root.mkdirs();
            }

                File gpxfile = new File(root, sFileName);
                FileWriter writer = new FileWriter(gpxfile);
                writer.append(sBody);
                writer.flush();
                writer.close();

            //Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show();
        }
        catch(IOException e)
        {
            e.printStackTrace();
            //importError = e.getMessage();
            //iError();
        }
    }

    @Override
    public void onCellLocationChanged(CellLocation location) {
        if(dataSource == null){
            dataSource = GeoFenceApp.getInstance().getDataSource();
        }
        LocationUtility locationUtility = GeoFenceApp.getLocationUtilityInstance();
        if (locationUtility != null){
            if(locationUtility.getmGoogleApiClient() == null){
                locationUtility.initialize(GeoFenceApp.getInstance());
            }
            if(!locationUtility.getmGoogleApiClient().isConnected()){
                locationUtility.getmGoogleApiClient().connect();
            }
        }

        super.onCellLocationChanged(location); //UtilityMethods.getNetworkDetails(this);
        int cid = 0;
        int lac = 0;
        int psc= 0 ;
        SharedPreferences sharedPreferences = GeoFenceApp.getInstance().getSharedPreferences(Constants.MY_PREF, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(Constants.PREF_CALLEd, "Called at:"+UtilityMethods.getDateTime());
        editor.commit();
        if (location != null) {
            if (location instanceof GsmCellLocation) {
                cid = ((GsmCellLocation) location).getCid();
                lac = ((GsmCellLocation) location).getLac();
                psc = ((GsmCellLocation) location).getPsc();
            }
            else if (location instanceof CdmaCellLocation) {
                cid = ((CdmaCellLocation) location).getBaseStationId();
                lac = ((CdmaCellLocation) location).getSystemId();
            }
            dataSource.insertNetworkDetails(Integer.toString(cid), Integer.toString(lac),Integer.toString(psc),  GeoFenceApp.getLocationUtilityInstance().getLocation());
            dataSource.insertAllCellskDetails(Integer.toString(cid), Integer.toString(lac),Integer.toString(psc),  GeoFenceApp.getLocationUtilityInstance().getLocation());
        }
        locationUtility.getmGoogleApiClient().disconnect();
        //UtilityMethods.getNetworkDetails(GeoFenceApp.getInstance());

    }
}
