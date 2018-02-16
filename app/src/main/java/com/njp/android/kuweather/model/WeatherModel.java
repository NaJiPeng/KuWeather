package com.njp.android.kuweather.model;


import android.util.Log;

import com.baidu.location.BDLocationListener;
import com.google.gson.Gson;
import com.njp.android.kuweather.bean.Weather;
import com.njp.android.kuweather.dao.WeatherDao;
import com.njp.android.kuweather.utils.BDLocationUtil;
import com.njp.android.kuweather.utils.RetrofitUtil;

import java.util.List;

import io.reactivex.Observable;

/**
 * 天气信息数据层
 */

public class WeatherModel {

    public Observable<Weather> getWeatherFromInternet(String location) {
        return RetrofitUtil.getService().getWeather(location, RetrofitUtil.KEY);
    }

    public Weather getWeatherFromDatabase(String location) {
        String weatherInfo = WeatherDao.query(location);
        if (weatherInfo == null) {
            return null;
        }
        return new Gson().fromJson(weatherInfo, Weather.class);
    }

    public void insertWeatherToDatabase(Weather weather) {
        String city = weather.getHeWeather6().get(0)
                .getBasic().getCid();
        String weatherInfo = new Gson().toJson(weather);
        WeatherDao.insert(city, weatherInfo);
    }

    public void updateToDatabase(Weather weather) {
        String city = weather.getHeWeather6().get(0)
                .getBasic().getCid();
        String weatherInfo = new Gson().toJson(weather);
        WeatherDao.upDate(city, weatherInfo);
    }

    public void startLocation() {
        BDLocationUtil.start();
    }

    public void stopLocation() {
        BDLocationUtil.stop();
    }

    public List<String> getPermissionList() {
        return BDLocationUtil.getPermissionList();
    }

    public void setLocationListener(BDLocationListener locationListener){
        BDLocationUtil.setListener(locationListener);
    }

    public void remoreLocationListener(BDLocationListener locationListener){
        BDLocationUtil.removeListener(locationListener);
    }

}
