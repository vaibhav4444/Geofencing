package com.google.android.gms.location.sample.geofencing.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;
import android.util.Log;


import com.google.android.gms.location.sample.geofencing.Constants;
import com.google.android.gms.location.sample.geofencing.GeoFenceApp;
import com.google.android.gms.location.sample.geofencing.model.NetworkDetailModel;
import com.google.android.gms.location.sample.geofencing.utils.UtilityMethods;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vaibhav.singhal on 3/1/2016.
 */
public class DataSource {
    private static final String TAG = DataSource.class.getName();
    private SQLiteDatabase database;
    private DatabaseHelper dbHelper;

    public DataSource(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }
    public long insertGeofenceDetails(String placeName, String lat,String lng, String towerIds, Location location)
    {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.INetworkDetails.Columns.PLACE_NAME, UtilityMethods.getAddress(location.getLatitude(), location.getLongitude()));
        values.put(DatabaseHelper.IUserGeoFenceDetails.Columns.LAT, lat);
        values.put(DatabaseHelper.IUserGeoFenceDetails.Columns.LNG, lng);
        values.put(DatabaseHelper.IUserGeoFenceDetails.Columns.RADIUS, Float.toString(Constants.GEOFENCE_RADIUS_IN_METERS));
        values.put(DatabaseHelper.IUserGeoFenceDetails.Columns.DATETIME, UtilityMethods.getDateTime());
        /*values.put(DatabaseHelper.IUserGeoFenceDetails.Columns.MCC, mcc);
        values.put(DatabaseHelper.IUserGeoFenceDetails.Columns.LAC, lac);
        values.put(DatabaseHelper.IUserGeoFenceDetails.Columns.OPERATOR_NAME, operatorName);

        values.put(DatabaseHelper.IUserGeoFenceDetails.Columns.NETWORK_TYPE, networkType); */
        values.put(DatabaseHelper.IUserGeoFenceDetails.Columns.TOWER_IDS, towerIds);
        if(location != null) {
            if(placeName.equals("default")) {
                values.put(DatabaseHelper.INetworkDetails.Columns.PLACE_NAME, UtilityMethods.getAddress(location.getLatitude(), location.getLongitude()));
            }
            values.put(DatabaseHelper.IUserGeoFenceDetails.Columns.LOCATION_PROVIDER, location.getProvider());
            values.put(DatabaseHelper.IUserGeoFenceDetails.Columns.LOC_UTILITY_CURRENT_LAT, Double.toString(location.getLatitude()));
            values.put(DatabaseHelper.IUserGeoFenceDetails.Columns.LOC_UTILITY_CURRENT_LNG, Double.toString(location.getLongitude()));
            values.put(DatabaseHelper.IUserGeoFenceDetails.Columns.LOC_UTILITY_ACCURACY, Float.toString(location.getAccuracy()));
        }
        return database.insert(DatabaseHelper.IUserGeoFenceDetails.TABLE_NAME,null,values);
    }
    public long insertNetworkDetails(String cid, String lac,String psc, Location location){
        List<NetworkDetailModel> networkDetailModelList = UtilityMethods.getNetworkDetails(GeoFenceApp.getInstance());
        long i=0;
        if(networkDetailModelList != null){
            for(NetworkDetailModel model : networkDetailModelList) {
                i++;
                ContentValues values = new ContentValues();
                values.put(DatabaseHelper.INetworkDetails.Columns.DATETIME, UtilityMethods.getDateTime());
                values.put(DatabaseHelper.INetworkDetails.Columns.CELL_LOC_CHANGE_CID, cid);
                values.put(DatabaseHelper.INetworkDetails.Columns.CELL_LOC_CHANGE_LAC, lac);
                values.put(DatabaseHelper.INetworkDetails.Columns.MCC, model.getMcc());
                values.put(DatabaseHelper.INetworkDetails.Columns.RSSI, model.getRssid());
                values.put(DatabaseHelper.INetworkDetails.Columns.OPERATOR_NAME, model.getOperatorName());
                values.put(DatabaseHelper.INetworkDetails.Columns.NETWORK_TYPE, model.getMnc());
                values.put(DatabaseHelper.INetworkDetails.Columns.MNC, model.getNetworkType());
                values.put(DatabaseHelper.INetworkDetails.Columns.TOWER_IDS, model.getCellID());
                values.put(DatabaseHelper.INetworkDetails.Columns.LAC, model.getLac());
                values.put(DatabaseHelper.INetworkDetails.Columns.PSC, psc);
                if (location != null) {
                    values.put(DatabaseHelper.INetworkDetails.Columns.PLACE_NAME, UtilityMethods.getAddress(location.getLatitude(), location.getLongitude()));
                    values.put(DatabaseHelper.INetworkDetails.Columns.LOCATION_PROVIDER, location.getProvider());
                    values.put(DatabaseHelper.INetworkDetails.Columns.LOC_UTILITY_CURRENT_LAT, Double.toString(location.getLatitude()));
                    values.put(DatabaseHelper.INetworkDetails.Columns.LOC_UTILITY_CURRENT_LNG, Double.toString(location.getLongitude()));
                    values.put(DatabaseHelper.INetworkDetails.Columns.LOC_UTILITY_ACCURACY, Float.toString(location.getAccuracy()));
                }
                i = database.insert(DatabaseHelper.INetworkDetails.TABLE_NAME, null, values);
                Log.i("tag", "i:" + i);
            }
        }
        else{
            ContentValues values = new ContentValues();
            values.put(DatabaseHelper.INetworkDetails.Columns.CELL_LOC_CHANGE_CID, cid);
            values.put(DatabaseHelper.INetworkDetails.Columns.CELL_LOC_CHANGE_LAC, lac);
            values.put(DatabaseHelper.INetworkDetails.Columns.PSC, psc);
            if (location != null) {
                values.put(DatabaseHelper.INetworkDetails.Columns.PLACE_NAME, UtilityMethods.getAddress(location.getLatitude(), location.getLongitude()));
                values.put(DatabaseHelper.INetworkDetails.Columns.LOCATION_PROVIDER, location.getProvider());
                values.put(DatabaseHelper.INetworkDetails.Columns.LOC_UTILITY_CURRENT_LAT, Double.toString(location.getLatitude()));
                values.put(DatabaseHelper.INetworkDetails.Columns.LOC_UTILITY_CURRENT_LNG, Double.toString(location.getLongitude()));
                values.put(DatabaseHelper.INetworkDetails.Columns.LOC_UTILITY_ACCURACY, Float.toString(location.getAccuracy()));
            }
            i = database.insert(DatabaseHelper.INetworkDetails.TABLE_NAME, null, values);

            Log.i("DataSource", "List is null");
            Log.i("DataSource", "List is null");
        }

        return i;

    }
    public long insertAllCellskDetails(String cid, String lac,String psc, Location location){
        List<NetworkDetailModel> networkDetailModelList = UtilityMethods.getAllCellInfo(GeoFenceApp.getInstance());
        long i=0;
        if(networkDetailModelList != null){
            for(NetworkDetailModel model : networkDetailModelList) {
                i++;
                ContentValues values = new ContentValues();
                values.put(DatabaseHelper.IAllCellDetails.Columns.DATETIME, UtilityMethods.getDateTime());
                values.put(DatabaseHelper.IAllCellDetails.Columns.CELL_LOC_CHANGE_CID, cid);
                values.put(DatabaseHelper.IAllCellDetails.Columns.CELL_LOC_CHANGE_LAC, lac);
                values.put(DatabaseHelper.IAllCellDetails.Columns.MCC, model.getMcc());
                values.put(DatabaseHelper.IAllCellDetails.Columns.RSSI, model.getRssid());
                values.put(DatabaseHelper.IAllCellDetails.Columns.OPERATOR_NAME, model.getOperatorName());
                values.put(DatabaseHelper.IAllCellDetails.Columns.NETWORK_TYPE, model.getMnc());
                values.put(DatabaseHelper.IAllCellDetails.Columns.MNC, model.getNetworkType());
                values.put(DatabaseHelper.IAllCellDetails.Columns.TOWER_IDS, model.getCellID());
                values.put(DatabaseHelper.IAllCellDetails.Columns.LAC, model.getLac());
                values.put(DatabaseHelper.IAllCellDetails.Columns.PSC, psc);
                if (location != null) {
                    values.put(DatabaseHelper.IAllCellDetails.Columns.PLACE_NAME, UtilityMethods.getAddress(location.getLatitude(), location.getLongitude()));
                    values.put(DatabaseHelper.IAllCellDetails.Columns.LOCATION_PROVIDER, location.getProvider());
                    values.put(DatabaseHelper.IAllCellDetails.Columns.LOC_UTILITY_CURRENT_LAT, Double.toString(location.getLatitude()));
                    values.put(DatabaseHelper.IAllCellDetails.Columns.LOC_UTILITY_CURRENT_LNG, Double.toString(location.getLongitude()));
                    values.put(DatabaseHelper.IAllCellDetails.Columns.LOC_UTILITY_ACCURACY, Float.toString(location.getAccuracy()));
                }
                i = database.insert(DatabaseHelper.IAllCellDetails.TABLE_NAME, null, values);
                Log.i("tag", "i:" + i);
            }
        }
        else{
            ContentValues values = new ContentValues();
            values.put(DatabaseHelper.IAllCellDetails.Columns.CELL_LOC_CHANGE_CID, cid);
            values.put(DatabaseHelper.IAllCellDetails.Columns.CELL_LOC_CHANGE_LAC, lac);
            values.put(DatabaseHelper.IAllCellDetails.Columns.PSC, psc);
            if (location != null) {
                values.put(DatabaseHelper.IAllCellDetails.Columns.PLACE_NAME, UtilityMethods.getAddress(location.getLatitude(), location.getLongitude()));
                values.put(DatabaseHelper.IAllCellDetails.Columns.LOCATION_PROVIDER, location.getProvider());
                values.put(DatabaseHelper.IAllCellDetails.Columns.LOC_UTILITY_CURRENT_LAT, Double.toString(location.getLatitude()));
                values.put(DatabaseHelper.IAllCellDetails.Columns.LOC_UTILITY_CURRENT_LNG, Double.toString(location.getLongitude()));
                values.put(DatabaseHelper.IAllCellDetails.Columns.LOC_UTILITY_ACCURACY, Float.toString(location.getAccuracy()));
            }
            i = database.insert(DatabaseHelper.IAllCellDetails.TABLE_NAME, null, values);

            Log.i("DataSource", "List is null");
            Log.i("DataSource", "List is null");
        }

        return i;

    }
    /**
     * Get metro details
     * @return list of all the model rows
     */
    public List<NetworkDetailModel> getNetworkInfo(boolean isGeo)
    {
        List<NetworkDetailModel> list_details = new ArrayList<NetworkDetailModel>();
        Cursor cursor = null;
        if(isGeo){
            cursor = database.rawQuery("Select * from "+ DatabaseHelper.IUserGeoFenceDetails.TABLE_NAME, null);
        }
        else{
            cursor = database.rawQuery("Select * from "+ DatabaseHelper.INetworkDetails.TABLE_NAME, null);
        }
        cursor.moveToFirst();
        if(cursor.getCount()!=0) {
            while (!cursor.isAfterLast()) {
                if(isGeo){
                    list_details.add(getGeoNetworkModel(cursor));
                }
                else {
                    list_details.add(getNetworkModel(cursor));
                }
                cursor.moveToNext();
            }
            // make sure to close the cursor
            cursor.close();
        }
        return list_details;
    }
    public List<NetworkDetailModel> getALLCellinfo()
    {
        List<NetworkDetailModel> list_details = new ArrayList<NetworkDetailModel>();
        Cursor cursor = null;

            cursor = database.rawQuery("Select * from "+ DatabaseHelper.IAllCellDetails.TABLE_NAME, null);

        cursor.moveToFirst();
        if(cursor.getCount()!=0) {
            while (!cursor.isAfterLast()) {

                list_details.add(getAllCellNetworkModel(cursor));
                cursor.moveToNext();
            }
            // make sure to close the cursor
            cursor.close();
        }
        return list_details;
    }
    private NetworkDetailModel getNetworkModel(Cursor cursor)
    {
        NetworkDetailModel model = new NetworkDetailModel();
        model.setPlaceName(cursor.getString(cursor.getColumnIndex(DatabaseHelper.INetworkDetails.Columns.PLACE_NAME)));
        model.setNetworkType(cursor.getString(cursor.getColumnIndex(DatabaseHelper.INetworkDetails.Columns.NETWORK_TYPE)));
        model.setLac(cursor.getString(cursor.getColumnIndex(DatabaseHelper.INetworkDetails.Columns.LAC)));
        model.setCellID(cursor.getString(cursor.getColumnIndex(DatabaseHelper.INetworkDetails.Columns.TOWER_IDS)));
        model.setMcc(cursor.getString(cursor.getColumnIndex(DatabaseHelper.INetworkDetails.Columns.MCC)));
        model.setMnc(cursor.getString(cursor.getColumnIndex(DatabaseHelper.INetworkDetails.Columns.MNC)));
        model.setRssid(cursor.getString(cursor.getColumnIndex(DatabaseHelper.INetworkDetails.Columns.RSSI)));
        model.setOperatorName(cursor.getString(cursor.getColumnIndex(DatabaseHelper.INetworkDetails.Columns.OPERATOR_NAME)));
        model.setLocationUtilityLat(cursor.getString(cursor.getColumnIndex(DatabaseHelper.INetworkDetails.Columns.LOC_UTILITY_CURRENT_LAT)));
        model.setLocationUtilityLon(cursor.getString(cursor.getColumnIndex(DatabaseHelper.INetworkDetails.Columns.LOC_UTILITY_CURRENT_LNG)));
        model.setLocationUtilityAccuracy(cursor.getString(cursor.getColumnIndex(DatabaseHelper.INetworkDetails.Columns.LOC_UTILITY_ACCURACY)));
        model.setLocationProvider(cursor.getString(cursor.getColumnIndex(DatabaseHelper.INetworkDetails.Columns.LOCATION_PROVIDER)));
        model.setDateTime(cursor.getString(cursor.getColumnIndex(DatabaseHelper.INetworkDetails.Columns.DATETIME)));
        model.setCellLocationChangeCID(cursor.getString(cursor.getColumnIndex(DatabaseHelper.INetworkDetails.Columns.CELL_LOC_CHANGE_CID)));
        model.setCellLocationChangeLAC(cursor.getString(cursor.getColumnIndex(DatabaseHelper.INetworkDetails.Columns.CELL_LOC_CHANGE_LAC)));
        return model;
    }
    private NetworkDetailModel getGeoNetworkModel(Cursor cursor)
    {
        NetworkDetailModel model = new NetworkDetailModel();
        model.setPlaceName(cursor.getString(cursor.getColumnIndex(DatabaseHelper.IUserGeoFenceDetails.Columns.PLACE_NAME)));
        model.setNetworkType(cursor.getString(cursor.getColumnIndex(DatabaseHelper.IUserGeoFenceDetails.Columns.NETWORK_TYPE)));
        model.setLac(cursor.getString(cursor.getColumnIndex(DatabaseHelper.IUserGeoFenceDetails.Columns.LAC)));
        model.setCellID(cursor.getString(cursor.getColumnIndex(DatabaseHelper.IUserGeoFenceDetails.Columns.TOWER_IDS)));
        model.setMcc(cursor.getString(cursor.getColumnIndex(DatabaseHelper.IUserGeoFenceDetails.Columns.MCC)));
        model.setMnc(cursor.getString(cursor.getColumnIndex(DatabaseHelper.IUserGeoFenceDetails.Columns.MNC)));
        model.setRssid(cursor.getString(cursor.getColumnIndex(DatabaseHelper.IUserGeoFenceDetails.Columns.RSSI)));
        model.setOperatorName(cursor.getString(cursor.getColumnIndex(DatabaseHelper.IUserGeoFenceDetails.Columns.OPERATOR_NAME)));
        model.setLocationUtilityLat(cursor.getString(cursor.getColumnIndex(DatabaseHelper.IUserGeoFenceDetails.Columns.LOC_UTILITY_CURRENT_LAT)));
        model.setLocationUtilityLon(cursor.getString(cursor.getColumnIndex(DatabaseHelper.IUserGeoFenceDetails.Columns.LOC_UTILITY_CURRENT_LNG)));
        model.setLocationUtilityAccuracy(cursor.getString(cursor.getColumnIndex(DatabaseHelper.IUserGeoFenceDetails.Columns.LOC_UTILITY_ACCURACY)));
        model.setLocationProvider(cursor.getString(cursor.getColumnIndex(DatabaseHelper.IUserGeoFenceDetails.Columns.LOCATION_PROVIDER)));
        model.setDateTime(cursor.getString(cursor.getColumnIndex(DatabaseHelper.IUserGeoFenceDetails.Columns.DATETIME)));
        model.setCellLocationChangeCID(cursor.getString(cursor.getColumnIndex(DatabaseHelper.IUserGeoFenceDetails.Columns.CELL_LOC_CHANGE_CID)));
        model.setCellLocationChangeLAC(cursor.getString(cursor.getColumnIndex(DatabaseHelper.IUserGeoFenceDetails.Columns.CELL_LOC_CHANGE_LAC)));
        return model;
    }
    private NetworkDetailModel getAllCellNetworkModel(Cursor cursor)
    {
        NetworkDetailModel model = new NetworkDetailModel();
        model.setPlaceName(cursor.getString(cursor.getColumnIndex(DatabaseHelper.IAllCellDetails.Columns.PLACE_NAME)));
        model.setNetworkType(cursor.getString(cursor.getColumnIndex(DatabaseHelper.IAllCellDetails.Columns.NETWORK_TYPE)));
        model.setLac(cursor.getString(cursor.getColumnIndex(DatabaseHelper.IAllCellDetails.Columns.LAC)));
        model.setCellID(cursor.getString(cursor.getColumnIndex(DatabaseHelper.IAllCellDetails.Columns.TOWER_IDS)));
        model.setMcc(cursor.getString(cursor.getColumnIndex(DatabaseHelper.IAllCellDetails.Columns.MCC)));
        model.setMnc(cursor.getString(cursor.getColumnIndex(DatabaseHelper.IAllCellDetails.Columns.MNC)));
        model.setRssid(cursor.getString(cursor.getColumnIndex(DatabaseHelper.IAllCellDetails.Columns.RSSI)));
        model.setOperatorName(cursor.getString(cursor.getColumnIndex(DatabaseHelper.IAllCellDetails.Columns.OPERATOR_NAME)));
        model.setLocationUtilityLat(cursor.getString(cursor.getColumnIndex(DatabaseHelper.IAllCellDetails.Columns.LOC_UTILITY_CURRENT_LAT)));
        model.setLocationUtilityLon(cursor.getString(cursor.getColumnIndex(DatabaseHelper.IAllCellDetails.Columns.LOC_UTILITY_CURRENT_LNG)));
        model.setLocationUtilityAccuracy(cursor.getString(cursor.getColumnIndex(DatabaseHelper.IAllCellDetails.Columns.LOC_UTILITY_ACCURACY)));
        model.setLocationProvider(cursor.getString(cursor.getColumnIndex(DatabaseHelper.IAllCellDetails.Columns.LOCATION_PROVIDER)));
        model.setDateTime(cursor.getString(cursor.getColumnIndex(DatabaseHelper.IAllCellDetails.Columns.DATETIME)));
        model.setCellLocationChangeCID(cursor.getString(cursor.getColumnIndex(DatabaseHelper.IAllCellDetails.Columns.CELL_LOC_CHANGE_CID)));
        model.setCellLocationChangeLAC(cursor.getString(cursor.getColumnIndex(DatabaseHelper.IAllCellDetails.Columns.CELL_LOC_CHANGE_LAC)));
        return model;
    }



}
