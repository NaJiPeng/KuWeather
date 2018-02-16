package com.njp.android.kuweather.presenter;

import com.njp.android.kuweather.model.SettingModel;
import com.njp.android.kuweather.view.SettingView;

/**
 * 设置界面中间层
 */

public class SettingPresenter extends BasePresenter<SettingView> {

    private SettingModel mModel;

    public SettingPresenter() {
        mModel = new SettingModel();
    }

    public void getBackgroundList() {
        if (getView() != null) {
            getView().onQuery(mModel.getBakgroundList());
        }
    }

}
