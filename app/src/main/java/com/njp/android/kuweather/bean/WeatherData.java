package com.njp.android.kuweather.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Unique;
import org.greenrobot.greendao.annotation.Generated;

/**
 * 天气数据类
 */
@Entity(nameInDb = "tb_weather")
public class WeatherData {

    @Unique @Id
    private String city;

    @Property
    private String weatherInfo;

    @Generated(hash = 1818152046)
    public WeatherData(String city, String weatherInfo) {
        this.city = city;
        this.weatherInfo = weatherInfo;
    }

    @Generated(hash = 1331017575)
    public WeatherData() {
    }

    public String getCity() {
        return this.city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getWeatherInfo() {
        return this.weatherInfo;
    }

    public void setWeatherInfo(String weatherInfo) {
        this.weatherInfo = weatherInfo;
    }



}
