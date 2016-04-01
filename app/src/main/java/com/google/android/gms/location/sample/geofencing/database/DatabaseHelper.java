package com.google.android.gms.location.sample.geofencing.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by vivekjha on 30/10/15.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    private final static int DATABASE_VERSION = 1;

    public final static String DATABASE_NAME = "Geofence.db";

    private SQLiteDatabase db;

    public static class IUserGeoFenceDetails {

        public static final String TABLE_NAME = "Geofence";


        public class Columns {

            public static final String ID = "_id";
            public static final String PLACE_NAME = "PlaceName";
            public static final String LAT = "lat";
            public static final String LNG = "lng";
            public static final String TOWER_IDS = "towerId";
            public static final String LAC = "lac";
            public static final String MCC = "mcc";
            public static final String MNC = "mnc";
            public static final String RSSI = "rssi";
            public static final String RADIUS = "radius";
            public static final String OPERATOR_NAME = "operatorName";
            public static final String NETWORK_TYPE = "networkType";
            public static final String LOCATION_PROVIDER = "locationProvider";
            public static final String LOC_UTILITY_CURRENT_LAT = "locUtilityCurrentLat";
            public static final String LOC_UTILITY_CURRENT_LNG = "locUtilityCurrentLng";
            public static final String LOC_UTILITY_ACCURACY = "locUtilityAccuracy";
            public static final String DATETIME = "datetime";
            public static final String CELL_LOC_CHANGE_CID = "cellLocChangeCID";
            public static final String CELL_LOC_CHANGE_LAC = "cellLocChangeLAC";
            //public static final String SET_ALARM = "isAlarmSet";
            //public static final String ALARM_TIMESTAMP = "alarmTimestamp";
           // public static final String SERIAL = "serial";

        }

    }

    public static class INetworkDetails {

        public static final String TABLE_NAME = "INetworkDetails";


        public class Columns {

            public static final String ID = "_id";
            public static final String TOWER_IDS = "neighbourCeltowerId";
            public static final String LAC = "neighbourCelllac";
            public static final String MCC = "neighbourCellmcc";
            public static final String MNC = "neighbourCellmnc";
            public static final String OPERATOR_NAME = "neighbourCeloperatorName";
            public static final String PLACE_NAME = "PlaceName";
            public static final String NETWORK_TYPE = "neighbourCelnetworkType";
            public static final String LOCATION_PROVIDER = "locationProvider";
            public static final String LOC_UTILITY_CURRENT_LAT = "locUtilityCurrentLat";
            public static final String LOC_UTILITY_CURRENT_LNG = "locUtilityCurrentLng";
            public static final String LOC_UTILITY_ACCURACY = "locUtilityAccuracy";
            public static final String DATETIME = "datetime";
            public static final String CELL_LOC_CHANGE_CID = "cellLocChangeCID";
            public static final String CELL_LOC_CHANGE_LAC = "cellLocChangeLAC";
            public static final String RSSI = "rssi";
            public static final String PSC = "psc";
            public static final String ALL_CELL_INFO = "allCellInfo";


        }

    }

    public static class IAllCellDetails {

        public static final String TABLE_NAME = "IAllCellDetails";


        public class Columns {

            public static final String ID = "_id";
            public static final String TOWER_IDS = "allCelltowerId";
            public static final String LAC = "allCelllac";
            public static final String MCC = "allCellmcc";
            public static final String MNC = "allCellmnc";
            public static final String OPERATOR_NAME = "allCelloperatorName";
            public static final String PLACE_NAME = "PlaceName";
            public static final String NETWORK_TYPE = "allCellnetworkType";
            public static final String LOCATION_PROVIDER = "locationProvider";
            public static final String LOC_UTILITY_CURRENT_LAT = "locUtilityCurrentLat";
            public static final String LOC_UTILITY_CURRENT_LNG = "locUtilityCurrentLng";
            public static final String LOC_UTILITY_ACCURACY = "locUtilityAccuracy";
            public static final String DATETIME = "datetime";
            public static final String CELL_LOC_CHANGE_CID = "cellLocChangeCID";
            public static final String CELL_LOC_CHANGE_LAC = "cellLocChangeLAC";
            public static final String RSSI = "rssi";
            public static final String PSC = "psc";
            public static final String ALL_CELL_INFO = "allCellInfo";


        }

    }

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {


        db.execSQL("CREATE TABLE " + IUserGeoFenceDetails.TABLE_NAME + " ("
                + IUserGeoFenceDetails.Columns.ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + IUserGeoFenceDetails.Columns.LAC + " TEXT,"
                + IUserGeoFenceDetails.Columns.MNC + " TEXT,"
                + IUserGeoFenceDetails.Columns.MCC + " TEXT,"
                + IUserGeoFenceDetails.Columns.LAT + " TEXT NOT NULL,"
                + IUserGeoFenceDetails.Columns.LNG + " TEXT NOT NULL,"
                + IUserGeoFenceDetails.Columns.RADIUS + " TEXT NOT NULL,"
                + IUserGeoFenceDetails.Columns.PLACE_NAME + " TEXT NOT NULL,"
                + IUserGeoFenceDetails.Columns.RSSI + " TEXT,"
                + IUserGeoFenceDetails.Columns.OPERATOR_NAME + " TEXT,"
                + IUserGeoFenceDetails.Columns.NETWORK_TYPE + " TEXT,"
                + IUserGeoFenceDetails.Columns.LOCATION_PROVIDER + " TEXT,"
                + IUserGeoFenceDetails.Columns.LOC_UTILITY_CURRENT_LAT + " TEXT,"
                + IUserGeoFenceDetails.Columns.LOC_UTILITY_CURRENT_LNG + " TEXT,"
                + IUserGeoFenceDetails.Columns.LOC_UTILITY_ACCURACY + " TEXT,"
                + IUserGeoFenceDetails.Columns.CELL_LOC_CHANGE_CID + " TEXT,"
                + IUserGeoFenceDetails.Columns.CELL_LOC_CHANGE_LAC + " TEXT,"
                + IUserGeoFenceDetails.Columns.DATETIME + " TEXT NOT NULL,"
                + IUserGeoFenceDetails.Columns.TOWER_IDS + " TEXT)");

        db.execSQL("CREATE TABLE " + INetworkDetails.TABLE_NAME + " ("
                + INetworkDetails.Columns.ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + INetworkDetails.Columns.LAC + " TEXT,"
                + INetworkDetails.Columns.MNC + " TEXT,"
                + INetworkDetails.Columns.MCC + " TEXT,"
                + INetworkDetails.Columns.RSSI + " TEXT,"
                + INetworkDetails.Columns.PSC + " TEXT,"
                + INetworkDetails.Columns.PLACE_NAME + " TEXT,"
                + INetworkDetails.Columns.OPERATOR_NAME + " TEXT,"
                + INetworkDetails.Columns.NETWORK_TYPE + " TEXT,"
                + INetworkDetails.Columns.ALL_CELL_INFO + " TEXT,"
                + INetworkDetails.Columns.LOCATION_PROVIDER + " TEXT,"
                + INetworkDetails.Columns.LOC_UTILITY_CURRENT_LAT + " TEXT,"
                + INetworkDetails.Columns.LOC_UTILITY_CURRENT_LNG + " TEXT,"
                + INetworkDetails.Columns.LOC_UTILITY_ACCURACY + " TEXT,"
                + INetworkDetails.Columns.DATETIME + " TEXT,"
                + INetworkDetails.Columns.CELL_LOC_CHANGE_CID + " TEXT,"
                + INetworkDetails.Columns.CELL_LOC_CHANGE_LAC + " TEXT,"
                + INetworkDetails.Columns.TOWER_IDS + " TEXT)");

        db.execSQL("CREATE TABLE " + IAllCellDetails.TABLE_NAME + " ("
                + IAllCellDetails.Columns.ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + IAllCellDetails.Columns.LAC + " TEXT,"
                + IAllCellDetails.Columns.MNC + " TEXT,"
                + IAllCellDetails.Columns.MCC + " TEXT,"
                + IAllCellDetails.Columns.RSSI + " TEXT,"
                + IAllCellDetails.Columns.PSC + " TEXT,"
                + IAllCellDetails.Columns.PLACE_NAME + " TEXT,"
                + IAllCellDetails.Columns.OPERATOR_NAME + " TEXT,"
                + IAllCellDetails.Columns.NETWORK_TYPE + " TEXT,"
                + IAllCellDetails.Columns.ALL_CELL_INFO + " TEXT,"
                + IAllCellDetails.Columns.LOCATION_PROVIDER + " TEXT,"
                + IAllCellDetails.Columns.LOC_UTILITY_CURRENT_LAT + " TEXT,"
                + IAllCellDetails.Columns.LOC_UTILITY_CURRENT_LNG + " TEXT,"
                + IAllCellDetails.Columns.LOC_UTILITY_ACCURACY + " TEXT,"
                + IAllCellDetails.Columns.DATETIME + " TEXT,"
                + IAllCellDetails.Columns.CELL_LOC_CHANGE_CID + " TEXT,"
                + IAllCellDetails.Columns.CELL_LOC_CHANGE_LAC + " TEXT,"
                + IAllCellDetails.Columns.TOWER_IDS + " TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + IUserGeoFenceDetails.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + INetworkDetails.TABLE_NAME);
        onCreate(db);
    }
}
