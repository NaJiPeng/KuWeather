package com.njp.android.kuweather.model;

import com.njp.android.kuweather.bean.Location;
import com.njp.android.kuweather.dao.LocationDao;

import java.util.List;

/**
 * 城市列表页数据访问层
 */

public class ChooseModel {

    public List<Location> getProvinces() {
        return LocationDao.queryProvince();
    }

    public List<Location> getCities(String province) {
        return LocationDao.queryCity(province);
    }

    public List<Location> getDistricts(String city) {
        return LocationDao.queryDistrict(city);
    }

}
