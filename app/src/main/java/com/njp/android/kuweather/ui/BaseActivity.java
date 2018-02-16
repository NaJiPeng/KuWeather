package com.njp.android.kuweather.ui;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.ViewConfiguration;
import android.view.WindowManager;

import com.njp.android.kuweather.R;
import com.njp.android.kuweather.presenter.BasePresenter;
import com.njp.android.kuweather.utils.SPUtil;
import com.njp.android.kuweather.view.BaseView;

/**
 * Activity抽象父类
 */

public abstract class BaseActivity<Presenter extends BasePresenter> extends AppCompatActivity implements BaseView {


    private static final String TAG = "BaseActivity";

    private Presenter mPresenter;


    public int getBackgroundId() {
        return SPUtil.getInt("background", R.drawable.happy_fisher);
    }

    public void setBackgroundId(int backgroundId) {
        SPUtil.putInt("background", backgroundId);
    }

    public Presenter getPresenter() {
        return mPresenter;
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(
                android.support.v7.appcompat.R.anim.abc_grow_fade_in_from_bottom,
                android.support.v7.appcompat.R.anim.abc_shrink_fade_out_from_bottom
        );
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        overridePendingTransition(
                android.support.v7.appcompat.R.anim.abc_grow_fade_in_from_bottom,
                android.support.v7.appcompat.R.anim.abc_shrink_fade_out_from_bottom
        );

        if (mPresenter == null) {
            mPresenter = createPresenter();
        }
        if (mPresenter != null) {
            mPresenter.attachView(this);
        }

        //如果没有虚拟按键采取完全沉浸式，否则采取半透明样式
        if (!hasNavigationBar()) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                getWindow().setStatusBarColor(getResources().getColor(R.color.transparent));
            } else {
                getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            }
        } else {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                getWindow().setStatusBarColor(getResources().getColor(R.color.transparent));
            }
        }

    }

    private boolean hasNavigationBar() {
        boolean hasMenuKey = ViewConfiguration.get(this).hasPermanentMenuKey();
        boolean hasBackKey = KeyCharacterMap.deviceHasKey(KeyEvent.KEYCODE_BACK);
        if (!hasMenuKey && !hasBackKey) {
            return true;
        }
        return false;
    }

    public abstract Presenter createPresenter();


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.detachView();
        }
    }
}
