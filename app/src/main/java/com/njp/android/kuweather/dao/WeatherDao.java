package com.njp.android.kuweather.dao;

import android.content.Context;

import com.njp.android.kuweather.bean.DaoMaster;
import com.njp.android.kuweather.bean.DaoSession;
import com.njp.android.kuweather.bean.WeatherData;
import com.njp.android.kuweather.bean.WeatherDataDao;

import java.util.List;

/**
 * 天气信息数据库访问对象
 */

public class WeatherDao {

    private static DaoSession sDaoSession;

    public static void init(Context context) {
        DaoMaster.DevOpenHelper devOpenHelper =
                new DaoMaster.DevOpenHelper(context, "weather.db", null);
        DaoMaster daoMaster = new DaoMaster(devOpenHelper.getWritableDb());
        sDaoSession = daoMaster.newSession();
    }

    public static void insert(String city, String weatherInfo) {
        WeatherDataDao dao = sDaoSession.getWeatherDataDao();
        WeatherData weatherData = new WeatherData();
        weatherData.setCity(city);
        weatherData.setWeatherInfo(weatherInfo);
        dao.insert(weatherData);
    }

    public static void delete(String city) {
        WeatherDataDao dao = sDaoSession.getWeatherDataDao();
        dao.deleteByKey(city);
    }

    public static void upDate(String city, String weatherInfo) {
        WeatherDataDao dao = sDaoSession.getWeatherDataDao();
        List<WeatherData> list = dao.queryBuilder().
                where(WeatherDataDao.Properties.City.eq(city)).
                build().list();
        if (list.size() > 0) {
            WeatherData weatherData = list.get(0);
            weatherData.setWeatherInfo(weatherInfo);
            dao.update(weatherData);
        }
    }

    public static String query(String city) {
        WeatherDataDao dao = sDaoSession.getWeatherDataDao();
        List<WeatherData> list = dao.queryBuilder().
                where(WeatherDataDao.Properties.City.eq(city)).
                build().list();
        if (list.size() > 0) {
            WeatherData weatherData = list.get(0);
            return weatherData.getWeatherInfo();
        }
        return null;
    }

    public static List<WeatherData> queryAll() {
        WeatherDataDao dao = sDaoSession.getWeatherDataDao();
        return dao.queryBuilder().build().list();
    }


}
