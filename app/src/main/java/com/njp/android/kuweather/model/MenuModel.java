package com.njp.android.kuweather.model;

import com.google.gson.Gson;
import com.njp.android.kuweather.bean.Weather;
import com.njp.android.kuweather.bean.WeatherData;
import com.njp.android.kuweather.dao.WeatherDao;

import java.util.ArrayList;
import java.util.List;

/**
 * 城市管理界面的数据访问层
 */

public class MenuModel {

    public List<Weather> queryAll() {
        List<Weather> weathers = new ArrayList<>();
        List<WeatherData> weatherDataList = WeatherDao.queryAll();
        for (WeatherData weatherData : weatherDataList) {
            String weatherInfo = weatherData.getWeatherInfo();
            Weather weather = new Gson().fromJson(weatherInfo, Weather.class);
            weathers.add(weather);
        }
        return weathers;
    }

    public void delete(String city) {
        WeatherDao.delete(city);
    }


}
