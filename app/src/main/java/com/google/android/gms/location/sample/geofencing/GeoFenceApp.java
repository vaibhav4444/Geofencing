package com.google.android.gms.location.sample.geofencing;

import android.app.Application;

import com.google.android.gms.location.sample.geofencing.database.DataSource;
import com.google.android.gms.location.sample.geofencing.utils.LocationUtility;
import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseUser;

/**
 * Created by vaibhav.singhal on 2/26/2016.
 */
public class GeoFenceApp extends Application {
    private DataSource mDataSource;
    private static GeoFenceApp mInstance;
    private static LocationUtility mLocationUtility;
    @Override
    public void onCreate() {
        super.onCreate();

        // Enable Local Datastore.
        //Parse.enableLocalDatastore(this);

        // Add your initialization code here
        //Parse.initialize(this);

        //ParseUser.enableAutomaticUser();
        //ParseACL defaultACL = new ParseACL();
        // Optionally enable public read access.
        // defaultACL.setPublicReadAccess(true);
        //ParseACL.setDefaultACL(defaultACL, true);
        mInstance = this;

    }
    public DataSource getDataSource() {
        if(mDataSource == null){
            mDataSource = new DataSource(this);
            mDataSource.open();
        }
        return mDataSource;
    }
    public static GeoFenceApp getInstance() {
        return mInstance;
    }
    public static LocationUtility getLocationUtilityInstance(){
        if(mLocationUtility == null){
            mLocationUtility = new LocationUtility();
        }
        return mLocationUtility;
    }

}
