package com.njp.android.kuweather.view;

import com.njp.android.kuweather.bean.Weather;

import java.util.List;

/**
 * 天气管理界面视图层回调接口
 */

public interface MenuView extends BaseView {

    void onQuery(List<Weather> weatherList);

    void onDelete();

}
