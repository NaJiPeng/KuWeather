package com.njp.android.kuweather.bean;

/**
 * 城市名称及ID实体类
 */

public class Location {

    private String locationName;

    private String locationId;

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public String getLocationId() {
        return locationId;
    }

    public void setLocationId(String locationId) {
        this.locationId = locationId;
    }
}
