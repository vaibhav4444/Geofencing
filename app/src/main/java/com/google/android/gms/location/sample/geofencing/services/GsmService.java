package com.google.android.gms.location.sample.geofencing.services;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.os.PowerManager;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.telephony.NeighboringCellInfo;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.sample.geofencing.Constants;
import com.google.android.gms.location.sample.geofencing.MainActivity;
import com.google.android.gms.location.sample.geofencing.R;
import com.google.android.gms.location.sample.geofencing.listener.MyPhoneStateListener;
import com.google.android.gms.location.sample.geofencing.utils.UtilityMethods;

import java.util.Arrays;
import java.util.List;

/**
 * Created by vaibhav.singhal on 4/1/2016.
 */
public class GsmService extends Service implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener{
    private PowerManager pPowerManager = null;
    private PowerManager.WakeLock pWakeLock = null;
    public static TelephonyManager p_TelephonyManager = null;
    public static MyPhoneStateListener p_myPhoneStateListener = null;
    private SharedPreferences sharedPreferences;
    private Integer [] home = {9606, 9607, 56152, 39946, 57274, 56153};
    private Integer [] ofc = {10087, 10086, 14716, 53892};
    private GoogleApiClient mGoogleApiClient;
    private Location mLastLocation;
    private LocationRequest mLocationRequest;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    // this method is called everytime when any component call startService
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        sharedPreferences = getSharedPreferences(Constants.MY_PREF, Context.MODE_PRIVATE);
        p_myPhoneStateListener = new MyPhoneStateListener();
        p_TelephonyManager = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
        p_TelephonyManager.listen(p_myPhoneStateListener, PhoneStateListener.LISTEN_CALL_STATE);
        return START_STICKY;
    }
    @Override
    public void onConnected(Bundle bundle) {
        startLocationUpdates();
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }
    // called when activity is created.
    @Override
    public void onCreate() {
        super.onCreate();
        pPowerManager = (PowerManager) getSystemService(Context.POWER_SERVICE);
        if (checkGooglePlayServices()) {
            buildGoogleApiClient();
        }
        createLocationRequest();
        /*final LocationManager locationManager = (LocationManager)
                getSystemService(Context.LOCATION_SERVICE);
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000 * 60 *2  , 400, new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                List<NeighboringCellInfo> list=p_TelephonyManager.getNeighboringCellInfo();
                StringBuffer nInfo = new StringBuffer();
                if(list != null){
                    for (NeighboringCellInfo info: list){
                        nInfo.append("NCide"+info.getCid()+" lac"+info.getLac()+"\n");
                        if(Arrays.asList(home).contains(info.getCid())){
                            sendNotification("Reached home at:"+UtilityMethods.getDateTime());
                        }
                        if(Arrays.asList(ofc).contains(info.getCid())){
                            sendNotification("Reached ofc at:"+UtilityMethods.getDateTime());
                        }
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
        }); */
        if (mGoogleApiClient != null) {
            mGoogleApiClient.connect();
        }

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
    private void sendNotification(String notificationDetails) {
        // Create an explicit content Intent that starts the main Activity.
        Intent notificationIntent = new Intent(getApplicationContext(), MainActivity.class);

        // Construct a task stack.
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);

        // Add the main Activity to the task stack as the parent.
        stackBuilder.addParentStack(MainActivity.class);

        // Push the content Intent onto the stack.
        stackBuilder.addNextIntent(notificationIntent);

        // Get a PendingIntent containing the entire back stack.
        PendingIntent notificationPendingIntent =
                stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

        // Get a notification builder that's compatible with platform versions >= 4
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);

        // Define the notification settings.
        builder.setSmallIcon(R.drawable.ic_launcher)
                // In a real app, you may want to use a library like Volley
                // to decode the Bitmap.
                .setLargeIcon(BitmapFactory.decodeResource(getResources(),
                        R.drawable.ic_launcher))
                .setColor(Color.RED)
                .setContentTitle(notificationDetails)
                .setContentText(notificationDetails)
                .setContentIntent(notificationPendingIntent)
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText(notificationDetails));

        // Dismiss notification once the user touches it.
        builder.setAutoCancel(true);

        // Get an instance of the Notification manager
        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        // Issue the notification
        mNotificationManager.notify(0, builder.build());
    }
    private boolean checkGooglePlayServices(){
        int checkGooglePlayServices = GooglePlayServicesUtil
                .isGooglePlayServicesAvailable(this);
        if (checkGooglePlayServices != ConnectionResult.SUCCESS) {
		/*
		* Google Play Services is missing or update is required
		*  return code could be
		* SUCCESS,
		* SERVICE_MISSING, SERVICE_VERSION_UPDATE_REQUIRED,
		* SERVICE_DISABLED, SERVICE_INVALID.
		*/
           // GooglePlayServicesUtil.getErrorDialog(checkGooglePlayServices,
                    //this, REQUEST_CODE_RECOVER_PLAY_SERVICES).show();

            return false;
        }

        return true;

    }
    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }
    protected void createLocationRequest() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(20000);
        mLocationRequest.setFastestInterval(5000);
        //mLocationRequest.setSmallestDisplacement()
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
    }
    protected void startLocationUpdates() {
        LocationServices.FusedLocationApi.requestLocationUpdates(
                mGoogleApiClient, mLocationRequest, new com.google.android.gms.location.LocationListener() {
                    @Override
                    public void onLocationChanged(Location location) {
                        List<NeighboringCellInfo> list=p_TelephonyManager.getNeighboringCellInfo();
                        StringBuffer nInfo = new StringBuffer();
                        if(list != null){
                            for (NeighboringCellInfo info: list){
                                nInfo.append("NCide"+info.getCid()+" lac"+info.getLac()+"\n");
                                if(Arrays.asList(home).contains(info.getCid())){
                                    sendNotification("Reached home at:"+UtilityMethods.getDateTime());
                                }
                                if(Arrays.asList(ofc).contains(info.getCid())){
                                    sendNotification("Reached ofc at:"+UtilityMethods.getDateTime());
                                }
                            }
                        }
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        String prev = sharedPreferences.getString(Constants.PREF_NAME,"not avl");
                        prev = prev+"\n"+"nInfo:"+nInfo.toString()+"  provider:"+location.getProvider()+" lat: "+location.getLatitude()+" long: "+location.getLongitude()+" speed:"+location.getSpeed()+" time"+ UtilityMethods.getDateTime()+"\n";
                        editor.putString(Constants.PREF_NAME, prev);

                        editor.commit();
                    }
                });
    }

}
