package com.google.android.gms.location.sample.geofencing;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;

import com.google.android.gms.location.sample.geofencing.listener.MyPhoneStateListener;

/**
 * Created by vaibhav.singhal on 2/26/2016.
 */
public class PhoneStateReceiver extends BroadcastReceiver {
    private TelephonyManager telephony;
    private MyPhoneStateListener myPhoneStateListener;

    public void onReceive(Context context, Intent intent) {
        if(myPhoneStateListener == null){
            myPhoneStateListener = new MyPhoneStateListener();
        }
        if(telephony == null) {
            telephony = (TelephonyManager) context
                    .getSystemService(Context.TELEPHONY_SERVICE);
        }
        telephony.listen(myPhoneStateListener, PhoneStateListener.LISTEN_CELL_LOCATION);
        //telephony.listen(myPhoneStateListener, MyPhoneStateListener.LISTEN_CALL_STATE);
    }
}
