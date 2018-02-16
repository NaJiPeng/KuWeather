package com.njp.android.kuweather.service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import com.google.gson.Gson;
import com.njp.android.kuweather.bean.Weather;
import com.njp.android.kuweather.bean.WeatherData;
import com.njp.android.kuweather.dao.WeatherDao;
import com.njp.android.kuweather.utils.RetrofitUtil;
import com.njp.android.kuweather.utils.SPUtil;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class AutoUpdateService extends Service {

    public AutoUpdateService() {
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        updateWeather();
        AlarmManager manager = (AlarmManager) getSystemService(ALARM_SERVICE);
        int anHour = 60 * 60 * 1000;
        int count = SPUtil.getInt("update_time", 3);
        long triggerAtTime = SystemClock.elapsedRealtime() + count * anHour;
        Intent i = new Intent(this, AutoUpdateService.class);
        PendingIntent pi = PendingIntent.getService(this, 0, i, 0);
        manager.cancel(pi);
        manager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerAtTime, pi);
        return super.onStartCommand(intent, flags, startId);
    }

    private void updateWeather() {

        List<WeatherData> weatherDataList = WeatherDao.queryAll();
        for (WeatherData weatherData : weatherDataList) {
            final String city = weatherData.getCity();
            RetrofitUtil.getService().getWeather(city, RetrofitUtil.KEY)
                    .observeOn(Schedulers.io())
                    .subscribeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<Weather>() {
                        @Override
                        public void onSubscribe(Disposable d) {
                        }

                        @Override
                        public void onNext(Weather weather) {
                            WeatherDao.upDate(city, new Gson().toJson(weather));
                        }

                        @Override
                        public void onError(Throwable e) {
                        }

                        @Override
                        public void onComplete() {
                        }
                    });
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
