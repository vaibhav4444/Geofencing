package com.google.android.gms.location.sample.geofencing.services;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.os.PowerManager;
import android.support.annotation.Nullable;
import android.telephony.NeighboringCellInfo;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;

import com.google.android.gms.location.sample.geofencing.Constants;
import com.google.android.gms.location.sample.geofencing.listener.MyPhoneStateListener;
import com.google.android.gms.location.sample.geofencing.utils.UtilityMethods;

import java.util.List;

/**
 * Created by vaibhav.singhal on 4/1/2016.
 */
public class GsmService extends Service {
    private PowerManager pPowerManager = null;
    private PowerManager.WakeLock pWakeLock = null;
    public static TelephonyManager p_TelephonyManager = null;
    public static MyPhoneStateListener p_myPhoneStateListener = null;
    private SharedPreferences sharedPreferences;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        sharedPreferences = getSharedPreferences(Constants.MY_PREF, Context.MODE_PRIVATE);
        p_myPhoneStateListener = new MyPhoneStateListener();
        p_TelephonyManager = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
        p_TelephonyManager.listen(p_myPhoneStateListener, PhoneStateListener.LISTEN_CALL_STATE);
        return START_STICKY;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        pPowerManager = (PowerManager) getSystemService(Context.POWER_SERVICE);
        final LocationManager locationManager = (LocationManager)
                getSystemService(Context.LOCATION_SERVICE);
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000 * 10, 0, new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                List<NeighboringCellInfo> list=p_TelephonyManager.getNeighboringCellInfo();
                StringBuffer nInfo = new StringBuffer();
                if(list != null){
                    for (NeighboringCellInfo info: list){
                        nInfo.append("NCide"+info.getCid()+" lac"+info.getLac()+"\n");
                    }
                }
                SharedPreferences.Editor editor = sharedPreferences.edit();
                String prev = sharedPreferences.getString(Constants.PREF_NAME,"not avl");
                prev = prev+"\n"+"nInfo:"+nInfo.toString()+"  provider:"+location.getProvider()+" lat: "+location.getLatitude()+" long: "+location.getLongitude()+" speed:"+location.getSpeed()+" time"+ UtilityMethods.getDateTime()+"\n";
                editor.putString(Constants.PREF_NAME, prev);

                editor.commit();
            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {

            }

            @Override
            public void onProviderDisabled(String s) {

            }
        });
        pWakeLock = pPowerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "No sleep");
        pWakeLock.acquire();
    }
    @Override
    public void onDestroy()
    {
        if (pWakeLock != null)
        {
            pWakeLock.release();
            pWakeLock = null;
        }
    }
}
