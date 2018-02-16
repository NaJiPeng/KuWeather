package com.njp.android.kuweather.presenter;

import com.njp.android.kuweather.bean.Location;
import com.njp.android.kuweather.model.ChooseModel;
import com.njp.android.kuweather.view.ChooseView;

import java.util.List;

/**
 * 城市列表页中间层
 */

public class ChoosePresenter extends BasePresenter<ChooseView> {

    private ChooseModel mModel;

    public ChoosePresenter() {
        mModel = new ChooseModel();
    }

    public void getProvinces() {
        List<Location> provinces = mModel.getProvinces();
        if (getView() != null) {
            getView().onProvinces(provinces);
        }
    }

    public void getCities(String province) {
        List<Location> cities = mModel.getCities(province);
        if (getView() != null) {
            getView().onCities(cities);
        }
    }

    public void getDistricts(String city) {
        List<Location> locationList = mModel.getDistricts(city);
        if (getView() != null) {
            getView().onDistricts(locationList);
        }
    }

}
