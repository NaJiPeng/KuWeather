package com.njp.android.kuweather.presenter;

import com.njp.android.kuweather.view.BaseView;

/**
 * Presenter的抽象父类
 */

public abstract class BasePresenter<View extends BaseView> {

    private View mView;

    public View getView() {
        return mView;
    }

    public void attachView(View view) {
        this.mView = view;
    }

    public void detachView() {
        this.mView = null;
    }

}
