package com.google.android.gms.location.sample.geofencing;

import android.os.Environment;
import android.telephony.CellLocation;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.telephony.cdma.CdmaCellLocation;
import android.telephony.gsm.GsmCellLocation;
import android.util.Log;
import android.widget.Toast;

import com.parse.ParseObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by vaibhav.singhal on 2/26/2016.
 */
public class MyPhoneStateListener extends PhoneStateListener {

    public static Boolean phoneRinging = false;

    public void onCallStateChanged(int state, String incomingNumber) {
        /*ParseObject obj = new ParseObject("State");
        obj.put("state", "state");
        obj.saveInBackground(); */
        generateNoteOnSD("myFile","call number:"+incomingNumber);
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

        super.onCellLocationChanged(location); //UtilityMethods.getNetworkDetails(this);
        int cid = 0;
        int lac = 0;

        if (location != null) {
            if (location instanceof GsmCellLocation) {
                cid = ((GsmCellLocation) location).getCid();
                lac = ((GsmCellLocation) location).getLac();
            }
            else if (location instanceof CdmaCellLocation) {
                cid = ((CdmaCellLocation) location).getBaseStationId();
                lac = ((CdmaCellLocation) location).getSystemId();
            }
        }
        ParseObject obj = new ParseObject("CellInfo");
        obj.put("cid", cid);
        obj.put("lac", lac);
        obj.saveInBackground();
    }
}
