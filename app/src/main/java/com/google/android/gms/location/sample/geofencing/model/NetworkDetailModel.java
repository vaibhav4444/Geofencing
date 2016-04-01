package com.google.android.gms.location.sample.geofencing.model;

/**
 * Created by vaibhav.singhal on 3/2/2016.
 */
public class NetworkDetailModel {
    String lac;

    public String getOperatorName() {
        return operatorName;
    }

    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
    }

    String operatorName;

    public String getNetworkType() {
        return networkType;
    }

    public void setNetworkType(String networkType) {
        this.networkType = networkType;
    }

    String networkType;

    public String getLac() {
        return lac;
    }

    public void setLac(String lac) {
        this.lac = lac;
    }

    public String getCellID() {
        return cellID;
    }

    public void setCellID(String cellID) {
        this.cellID = cellID;
    }

    public String getMcc() {
        return mcc;
    }

    public void setMcc(String mcc) {
        this.mcc = mcc;
    }

    public String getMnc() {
        return mnc;
    }

    public void setMnc(String mnc) {
        this.mnc = mnc;
    }

    public String getRssid() {
        return rssid;
    }

    public void setRssid(String rssid) {
        this.rssid = rssid;
    }

    String cellID;
    String mcc;
    String mnc;
    String rssid;
    String cellLocationChangeCID;

    public String getPsc() {
        return psc;
    }

    public void setPsc(String psc) {
        this.psc = psc;
    }

    String psc;

    public String getCellLocationChangeLAC() {
        return cellLocationChangeLAC;
    }

    public void setCellLocationChangeLAC(String cellLocationChangeLAC) {
        this.cellLocationChangeLAC = cellLocationChangeLAC;
    }

    public String getCellLocationChangeCID() {
        return cellLocationChangeCID;
    }

    public void setCellLocationChangeCID(String cellLocationChangeCID) {
        this.cellLocationChangeCID = cellLocationChangeCID;
    }

    String cellLocationChangeLAC;

    public String getPlaceName() {
        return placeName;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }

    String placeName;
    String locationProvider, locationUtilityLat, locationUtilityLon, locationUtilityAccuracy;

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    String dateTime;

    public String getLocationProvider() {
        return locationProvider;
    }

    public void setLocationProvider(String locationProvider) {
        this.locationProvider = locationProvider;
    }

    public String getLocationUtilityLat() {
        return locationUtilityLat;
    }

    public void setLocationUtilityLat(String locationUtilityLat) {
        this.locationUtilityLat = locationUtilityLat;
    }

    public String getLocationUtilityLon() {
        return locationUtilityLon;
    }

    public void setLocationUtilityLon(String locationUtilityLon) {
        this.locationUtilityLon = locationUtilityLon;
    }

    public String getLocationUtilityAccuracy() {
        return locationUtilityAccuracy;
    }

    public void setLocationUtilityAccuracy(String locationUtilityAccuracy) {
        this.locationUtilityAccuracy = locationUtilityAccuracy;
    }
}
