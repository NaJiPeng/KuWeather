package com.njp.android.kuweather.model;

import com.njp.android.kuweather.bean.Background;
import com.njp.android.kuweather.dao.BackgroundDao;

import java.util.List;

/**
 * 设置界面数据访问层
 */

public class SettingModel {

    public List<Background> getBakgroundList() {
        return BackgroundDao.getBackgroundList();
    }

}
