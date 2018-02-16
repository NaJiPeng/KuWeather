package com.njp.android.kuweather.presenter;

import com.njp.android.kuweather.bean.Weather;
import com.njp.android.kuweather.model.MenuModel;
import com.njp.android.kuweather.view.MenuView;

import java.util.List;

/**
 * 城市管理界面中间层
 */

public class MenuPresenter extends BasePresenter<MenuView> {

    private MenuModel mMenuModel;

    public MenuPresenter() {
        mMenuModel = new MenuModel();
    }

    public void getWeathers() {
        List<Weather> weatherList = mMenuModel.queryAll();
        if (getView() != null) {
            getView().onQuery(weatherList);
        }
    }

    public void deleteWeather(Weather weather) {
        String city = weather.getHeWeather6().get(0).getBasic().getCid();
        mMenuModel.delete(city);
        getWeathers();
        if (getView()!=null){
            getView().onDelete();
        }
    }

}
