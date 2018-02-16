package com.njp.android.kuweather.view;

import com.baidu.location.BDLocationListener;
import com.njp.android.kuweather.bean.Weather;

import java.util.List;

/**
 * 天气信息视图层回调接口
 */

public interface WeatherView extends BaseView {

    void onSuccess(Weather weather);

    void onError(String e);

    void onRequestPermission(List<String> permissionList);

}
