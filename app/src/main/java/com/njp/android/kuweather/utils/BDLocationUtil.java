package com.njp.android.kuweather.utils;

import android.Manifest;
import android.content.Context;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import java.util.ArrayList;
import java.util.List;

/**
 * 百度定位工具类
 */

public class BDLocationUtil {

    private static LocationClient sLocationClient;

    public static void init(Context context) {
        sLocationClient = new LocationClient(context);
        LocationClientOption option = new LocationClientOption();
        option.setIsNeedAddress(true);
        sLocationClient.setLocOption(option);
    }

    public static void setListener(BDLocationListener locationListener) {
        sLocationClient.registerLocationListener(locationListener);
    }

    public static void removeListener(BDLocationListener locationListener) {
        sLocationClient.unRegisterLocationListener(locationListener);
    }

    public static void start() {
        sLocationClient.start();
    }

    public static void stop() {
        sLocationClient.stop();
    }


    public static List<String> getPermissionList() {
        List<String> permissionList = new ArrayList<>();
        permissionList.add(Manifest.permission.ACCESS_FINE_LOCATION);
        permissionList.add(Manifest.permission.READ_PHONE_STATE);
        permissionList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        return permissionList;
    }


}
