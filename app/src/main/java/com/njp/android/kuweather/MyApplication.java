package com.njp.android.kuweather;


import android.app.Application;
import android.content.Intent;

import com.njp.android.kuweather.dao.BackgroundDao;
import com.njp.android.kuweather.dao.LocationDao;
import com.njp.android.kuweather.dao.WeatherDao;
import com.njp.android.kuweather.service.AutoUpdateService;
import com.njp.android.kuweather.utils.BDLocationUtil;
import com.njp.android.kuweather.utils.CopyFileUtil;
import com.njp.android.kuweather.utils.SPUtil;
import com.njp.android.kuweather.utils.ToastUtil;

/**
 * 用于初始化全局设置
 */

public class MyApplication extends Application {


    @Override
    public void onCreate() {
        super.onCreate();
        SPUtil.init(this);
        ToastUtil.init(this);
        //如果是第一次启动需要复制数据库文件
        if (SPUtil.getBoolean("boot", true)) {
            CopyFileUtil.copy(this, "locations.db");
            CopyFileUtil.copy(this, "backgrounds.db");
            SPUtil.putBoolean("boot", false);
        }
        LocationDao.init(this);
        WeatherDao.init(this);
        BackgroundDao.init(this);
        BDLocationUtil.init(this);

        if (SPUtil.getBoolean("auto_update", true)) {
            Intent intent = new Intent(this, AutoUpdateService.class);
            startService(intent);
        }

    }


}
