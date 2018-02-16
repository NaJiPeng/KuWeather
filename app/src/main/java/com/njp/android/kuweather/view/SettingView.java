package com.njp.android.kuweather.view;

import com.njp.android.kuweather.bean.Background;

import java.util.List;

/**
 * 设置界面视图层回调接口
 */

public interface SettingView extends BaseView {

    void onQuery(List<Background> backgroundList);

}
