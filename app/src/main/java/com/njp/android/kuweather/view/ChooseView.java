package com.njp.android.kuweather.view;

import com.njp.android.kuweather.bean.Location;

import java.util.List;

/**
 * 城市列表页面视图层回调接口
 */

public interface ChooseView extends BaseView {

    void onProvinces(List<Location> provinces);

    void onCities(List<Location> cities);

    void onDistricts(List<Location> locationList);

}
