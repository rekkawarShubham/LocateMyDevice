package com.simplifiers.locatemydevice;

public class Device {
    String IMEI,latitude,longitude;

    public Device(String IMEI, String latitude, String longitude) {
        this.IMEI = IMEI;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getIMEI() {
        return IMEI;
    }

    public void setIMEI(String IMEI) {
        this.IMEI = IMEI;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }
}
