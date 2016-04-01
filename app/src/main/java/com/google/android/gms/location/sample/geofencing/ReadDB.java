package com.google.android.gms.location.sample.geofencing;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.location.sample.geofencing.database.DataSource;
import com.google.android.gms.location.sample.geofencing.database.DatabaseHelper;
import com.google.android.gms.location.sample.geofencing.model.NetworkDetailModel;

import java.util.List;

/**
 * Created by vaibhav.singhal on 3/8/2016.
 */
public class ReadDB extends Activity {
    DataSource dataSource;
    TextView db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.read_db);
        db = (TextView) findViewById(R.id.txtData);
        if(dataSource == null){
            dataSource = GeoFenceApp.getInstance().getDataSource();
        }

        Log.i("tag", "tag");
    }
    private void readNetworkDetails(int id){
        boolean isGeoDetails = false;
        List<NetworkDetailModel> list;
        if(id == R.id.readGeo){
            isGeoDetails = true;
            list=dataSource.getNetworkInfo(isGeoDetails);
        }
        else if (id == R.id.readNetwork){
            list=dataSource.getNetworkInfo(isGeoDetails);
        }
        else{
            list=dataSource.getALLCellinfo();
        }
        //List<NetworkDetailModel> list=dataSource.getNetworkInfo(isGeoDetails);
        StringBuffer buffer = new StringBuffer();

        if(id == R.id.readAllCell) {
            for (NetworkDetailModel model : list) {
                buffer.append(DatabaseHelper.IAllCellDetails.Columns.DATETIME + ":" + model.getDateTime());
                buffer.append("\n");
                buffer.append(DatabaseHelper.IAllCellDetails.Columns.PLACE_NAME + ":" + model.getPlaceName());
                buffer.append("\n");
                if (!isGeoDetails) {
                    buffer.append(DatabaseHelper.IAllCellDetails.Columns.LAC + ":" + model.getLac());
                    buffer.append("\n");
                    buffer.append(DatabaseHelper.IAllCellDetails.Columns.RSSI + ":" + model.getRssid());
                    buffer.append("\n");
                    buffer.append(DatabaseHelper.IAllCellDetails.Columns.CELL_LOC_CHANGE_CID + ":" + model.getCellLocationChangeCID());
                    buffer.append("\n");
                    buffer.append(DatabaseHelper.IAllCellDetails.Columns.CELL_LOC_CHANGE_LAC + ":" + model.getCellLocationChangeLAC());
                    buffer.append("\n");
                    buffer.append(DatabaseHelper.IAllCellDetails.Columns.PSC + ":" + model.getPsc());
                    buffer.append("\n");
                }
                buffer.append(DatabaseHelper.IAllCellDetails.Columns.TOWER_IDS + ":" + model.getCellID());
                buffer.append("\n");
                buffer.append(DatabaseHelper.IAllCellDetails.Columns.LOC_UTILITY_CURRENT_LAT + ":" + model.getLocationUtilityLat());
                buffer.append("\n");
                buffer.append(DatabaseHelper.IAllCellDetails.Columns.LOC_UTILITY_CURRENT_LNG + ":" + model.getLocationUtilityLon());
                buffer.append("\n");
                buffer.append(DatabaseHelper.IAllCellDetails.Columns.LOCATION_PROVIDER + ":" + model.getLocationProvider());
                buffer.append("\n");
                buffer.append(DatabaseHelper.IAllCellDetails.Columns.LOC_UTILITY_ACCURACY + ":" + model.getLocationUtilityAccuracy());
                buffer.append("\n");
                if (!isGeoDetails) {
                    buffer.append(DatabaseHelper.IAllCellDetails.Columns.MCC + ":" + model.getMcc());
                    buffer.append("\n");
                    buffer.append(DatabaseHelper.IAllCellDetails.Columns.MNC + ":" + model.getMnc());
                    buffer.append("\n");
                }
                buffer.append("*************************************************************************");
                buffer.append("\n");

            }
        }
        else{
            for (NetworkDetailModel model: list){
                buffer.append(DatabaseHelper.INetworkDetails.Columns.DATETIME+":"+model.getDateTime());
                buffer.append("\n");
                buffer.append(DatabaseHelper.INetworkDetails.Columns.PLACE_NAME+":"+model.getPlaceName());
                buffer.append("\n");
                if(!isGeoDetails) {
                    buffer.append(DatabaseHelper.INetworkDetails.Columns.LAC + ":" + model.getLac());
                    buffer.append("\n");
                    buffer.append(DatabaseHelper.INetworkDetails.Columns.RSSI+":"+model.getRssid());
                    buffer.append("\n");
                    buffer.append(DatabaseHelper.INetworkDetails.Columns.CELL_LOC_CHANGE_CID + ":" + model.getCellLocationChangeCID());
                    buffer.append("\n");
                    buffer.append(DatabaseHelper.INetworkDetails.Columns.CELL_LOC_CHANGE_LAC+":"+model.getCellLocationChangeLAC());
                    buffer.append("\n");
                    buffer.append(DatabaseHelper.INetworkDetails.Columns.PSC+":"+model.getPsc());
                    buffer.append("\n");
                }
                buffer.append(DatabaseHelper.INetworkDetails.Columns.TOWER_IDS+":"+model.getCellID());
                buffer.append("\n");
                buffer.append(DatabaseHelper.INetworkDetails.Columns.LOC_UTILITY_CURRENT_LAT+":"+model.getLocationUtilityLat());
                buffer.append("\n");
                buffer.append(DatabaseHelper.INetworkDetails.Columns.LOC_UTILITY_CURRENT_LNG+":"+model.getLocationUtilityLon());
                buffer.append("\n");
                buffer.append(DatabaseHelper.INetworkDetails.Columns.LOCATION_PROVIDER+":"+model.getLocationProvider());
                buffer.append("\n");
                buffer.append(DatabaseHelper.INetworkDetails.Columns.LOC_UTILITY_ACCURACY+":"+model.getLocationUtilityAccuracy());
                buffer.append("\n");
                if(!isGeoDetails) {
                    buffer.append(DatabaseHelper.INetworkDetails.Columns.MCC + ":" + model.getMcc());
                    buffer.append("\n");
                    buffer.append(DatabaseHelper.INetworkDetails.Columns.MNC + ":" + model.getMnc());
                    buffer.append("\n");
                }
                buffer.append("*************************************************************************");
                buffer.append("\n");

            }
        }
        db.setText(buffer.toString());
    }
    public void onClick(View v){
        db.setText("");
        int id = v.getId();
        if(id == R.id.readPref){
            db.setText("");
            SharedPreferences prefs = getSharedPreferences(Constants.MY_PREF, MODE_PRIVATE);
            db.setText(prefs.getString(Constants.PREF_NAME, "not avl")+" listener result: "+prefs.getString(Constants.PREF_CALLEd,"not called"));
            return;
        }

            readNetworkDetails(id);
    }
}
