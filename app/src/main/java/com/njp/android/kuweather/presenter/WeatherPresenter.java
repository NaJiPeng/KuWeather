package com.njp.android.kuweather.presenter;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.njp.android.kuweather.bean.Weather;
import com.njp.android.kuweather.model.WeatherModel;
import com.njp.android.kuweather.utils.SPUtil;
import com.njp.android.kuweather.view.WeatherView;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * 天气信息媒介层
 */

public class WeatherPresenter extends BasePresenter<WeatherView> implements BDLocationListener {

    private WeatherModel mModel;

    public WeatherPresenter() {
        mModel = new WeatherModel();
    }

    //改变当前城市
    public void changeLocation(String location) {
        SPUtil.putString("location", location);
        getWeather();
    }

    public void refreshWeather() {
        String location = SPUtil.getString("location", null);

        if (location == null && getView() != null) {
            getView().onError("请选择城市");
            return;
        }

        getWeatherFromInternetById(location);

    }

    public void getWeatherFromInternetById(final String location) {

        if (location == null && getView() != null) {
            getView().onError("请选择城市");
            return;
        }

        mModel.getWeatherFromInternet(location)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Weather>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(Weather weather) {
                        if (getView() != null) {
                            if ("ok".equals(weather.getHeWeather6().get(0).getStatus())) {
                                getView().onSuccess(weather);
                                //如果数据库中存在数据则更新数据，否则添加数据
                                if (mModel.getWeatherFromDatabase(location) != null) {
                                    mModel.updateToDatabase(weather);
                                } else {
                                    mModel.insertWeatherToDatabase(weather);
                                }
                            } else {
                                getView().onError(weather.getHeWeather6().get(0).getStatus());
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (getView() != null) {
                            getView().onError(e.getMessage());
                        }
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }

    public void getWeatherFromInternetByLocation(String strLocation) {

        if (strLocation == null && getView() != null) {
            getView().onError("请选择城市");
            return;
        }

        mModel.getWeatherFromInternet(strLocation)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Weather>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(Weather weather) {
                        if (getView() != null) {
                            if ("ok".equals(weather.getHeWeather6().get(0).getStatus())) {
                                getView().onSuccess(weather);
                                //检查是否更换了城市
                                String preLocation = SPUtil.getString("location",null);
                                String location = weather.getHeWeather6().get(0).getBasic().getCid();
                                if (!location.equals(preLocation)) {
                                    SPUtil.putString("location", location);
                                }
                                //如果数据库中存在数据则更新数据，否则添加数据
                                if (mModel.getWeatherFromDatabase(location) != null) {
                                    mModel.updateToDatabase(weather);
                                } else {
                                    mModel.insertWeatherToDatabase(weather);
                                }
                            } else {
                                getWeather();
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        getWeather();
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }

    public void getWeather() {

        String location = SPUtil.getString("location", null);

        if (location == null && getView() != null) {
            getView().onError("请选择城市");
            return;
        }

        //如果数据库中存在数据直接返回
        Weather weather = mModel.getWeatherFromDatabase(location);
        if (weather != null && getView() != null) {
            getView().onSuccess(weather);
            return;
        }
        //从网络获取数据
        getWeatherFromInternetById(location);
    }

    public void startLocation(){
        requestLocationPermissions();
    }

    public void requestLocationPermissions() {
        if (getView() != null) {
            getView().onRequestPermission(mModel.getPermissionList());
        }
    }

    public void getLocation() {
        mModel.setLocationListener(this);
        mModel.startLocation();
    }

    public void stopLocation() {
        mModel.remoreLocationListener(this);
        mModel.stopLocation();
    }


    @Override
    public void onReceiveLocation(BDLocation bdLocation) {
        String location = bdLocation.getLongitude() + "," + bdLocation.getLatitude();
        getWeatherFromInternetByLocation(location);
    }
}
