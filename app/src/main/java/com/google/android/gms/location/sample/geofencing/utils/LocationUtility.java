package com.google.android.gms.location.sample.geofencing.utils;

import android.content.Context;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.sample.geofencing.GeoFenceApp;
import com.google.android.gms.location.sample.geofencing.database.DataSource;
import com.google.android.gms.location.sample.geofencing.listener.ILocationUpdates;


/**
 * Created by Vaibhav.
 */

public class LocationUtility implements LocationListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener{

    /*
        setInterval() - This method sets the rate in milliseconds at which your app prefers to receive location updates.
        Note that the location updates may be faster than this rate if another app is receiving updates at a faster rate,
        or slower than this rate, or there may be no updates at all (if the device has no connectivity, for example).
     */
    private static final long INTERVAL = 1000 * 30;
    /*
        setFastestInterval() - This method sets the fastest rate in milliseconds at which your app can handle location updates.
        You need to set this rate because other apps also affect the rate at which updates are sent.
        The Google Play services location APIs send out updates at the fastest rate that any app has requested with setInterval().
        If this rate is faster than your app can handle, you may encounter problems with UI flicker or data overflow.
         To prevent this, call setFastestInterval() to set an upper limit to the update rate.
     */
    private static final long FASTEST_INTERVAL = 1000 * 30;

    private LocationRequest mLocationRequest;
    private GoogleApiClient mGoogleApiClient;
    private Context mContext;
    private Location mCurrentLocation;
    private Location mLastLocation =null;
    private DataSource dataSource;

    //> make object of interface ILocationUpdates
    private ILocationUpdates mILocationUpdatesListener=null;

    public void LocationUtility(){

    }
    public void initialize(Context mContext){
        this.mContext=mContext;
        createLocationRequest();
        // Create a GoogleApiClient instance
        if(mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(GeoFenceApp.getInstance())
                    .addApi(LocationServices.API)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .build();
        }
    mGoogleApiClient.connect();
        //> attach listener to the context
        try {
            if(mContext instanceof  ILocationUpdates){
                mILocationUpdatesListener = (ILocationUpdates)mContext;
            }

        }
        catch (ClassCastException e) {
            throw new ClassCastException(mContext.getClass().toString()
                    + " must implement ILocationUpdates");
        }
    }

    protected void createLocationRequest() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(INTERVAL);
        mLocationRequest.setFastestInterval(FASTEST_INTERVAL);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    @Override
    public void onConnected(Bundle bundle) {
        // Connected to Google Play services!
        //> get last location separately
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                mGoogleApiClient);

        startLocationUpdates();

    }

    public void startLocationUpdates() {
        PendingResult<Status> pendingResult = LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest,this);
    }

    public void googleClientConnection()
    {
        mGoogleApiClient.connect();
    }

    public void googleClientDisconnection()
    {
        mGoogleApiClient.disconnect();
    }


    @Override
    public void onConnectionSuspended(int i) {
        // The connection has been interrupted.
        // Disable any UI components that depend on Google APIs
        // until onConnected() is called.


    }

    @Override
    public void onLocationChanged(Location location) {
        mCurrentLocation = location;
        if(dataSource == null){
            dataSource = GeoFenceApp.getInstance().getDataSource();
        }
        //long index = dataSource.insertNetworkDetails("throug loc utility", "throug loc utility",  GeoFenceApp.getLocationUtilityInstance().getLocation());
        Log.e("LocationUtils", "Lat : " + mCurrentLocation.getLatitude() + " Lon : " + mCurrentLocation.getLongitude());

        //> set data in listener
        if(mILocationUpdatesListener!=null) {
            mILocationUpdatesListener.notifyLocationChange(mCurrentLocation);
        }


    }

    public Location getLocation(){
        return mCurrentLocation;
    }

    public double getLatitude(){
        if(mCurrentLocation==null)
            return 0;
        else
            return mCurrentLocation.getLatitude();
    }

    public double getLongitude(){
        if(mCurrentLocation==null)
            return 0;
        else
            return mCurrentLocation.getLongitude();
    }


    public void stopLocationUpdates() {
        LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
    }


    public float distanceBetween(double startLatitude, double startLongitude, double endLatitude, double endLongitude, float[] results){

        Location.distanceBetween(startLatitude, startLongitude, endLatitude, endLongitude, results);

        return results[0];
    }

    public float distanceBetween(Location currentLocation,Location endLocation){

      return currentLocation.distanceTo(endLocation);

    }

    public boolean isGoogleApiConnected(){
        return mGoogleApiClient.isConnected();
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        // This callback is important for handling errors that
        // may occur while attempting to connect with Google.

    }

    // return true when google play services are available
    public boolean isGooglePlayServicesAvailable() {
        int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(mContext);
        if (ConnectionResult.SUCCESS == status) {
            return true;
        } else {

            return false;
        }
    }


    public Location getmLastLocation() {
        return mLastLocation;
    }


    public LocationRequest getLocationRequest() {
        return mLocationRequest;
    }

    public GoogleApiClient getmGoogleApiClient(){
        //commented bec if it is null call initialze function, need to see how we can do better

        /*if(mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(GeoFenceApp.getInstance())
                    .addApi(LocationServices.API)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .build();
        } */
        return mGoogleApiClient;
    }

}
