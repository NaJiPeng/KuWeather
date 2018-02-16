package com.njp.android.kuweather.ui;

import android.annotation.SuppressLint;
import android.app.ActivityOptions;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.njp.android.kuweather.R;
import com.njp.android.kuweather.adapter.CitiesAdapter;
import com.njp.android.kuweather.bean.Weather;
import com.njp.android.kuweather.presenter.MenuPresenter;
import com.njp.android.kuweather.utils.SPUtil;
import com.njp.android.kuweather.view.MenuView;

import java.util.ArrayList;
import java.util.List;

public class MenuActivity extends BaseActivity<MenuPresenter> implements MenuView {

    public static final int REQUEST_CODE_CHOOSE = 1001;

    private String location = SPUtil.getString("location", null);

    private ViewGroup mTopBar;
    private ImageView mIvBack;
    private ImageView mIvMore;
    private RecyclerView mRvCities;
    private CitiesAdapter mAdapter;
    private List<Weather> mWeatherList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        initView();
        initEvent();


    }

    private void initEvent() {

        mIvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        mIvMore.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this, ChooseActivity.class);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    startActivityForResult(intent, REQUEST_CODE_CHOOSE,
                            ActivityOptions.makeSceneTransitionAnimation(
                                    MenuActivity.this, mTopBar, "share"
                            ).toBundle());
                } else {
                    startActivityForResult(intent, REQUEST_CODE_CHOOSE);
                }
            }
        });

        mAdapter.setOnItemClickListener(new CitiesAdapter.OnCityItemClickListener() {
            @Override
            public void onClick(Weather weather) {
                String city = weather.getHeWeather6().get(0).getBasic().getCid();
                Intent intent = new Intent();
                intent.putExtra("location", city);
                setResult(RESULT_OK, intent);
                finish();
            }
        });

        mAdapter.setOnItemLongClickListener(new CitiesAdapter.OnCityItemLongClickListener() {
            @Override
            public void onLongClick(final Weather weather) {
                String cityName = weather.getHeWeather6().get(0).getBasic().getLocation();
                new AlertDialog.Builder(MenuActivity.this)
                        .setTitle(cityName)
                        .setPositiveButton("删除", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                getPresenter().deleteWeather(weather);
                            }
                        }).setNegativeButton("取消", null)
                        .show();
            }
        });

    }

    private void initView() {
        mTopBar = findViewById(R.id.top_bar);
        mIvBack = findViewById(R.id.iv_back);
        mIvMore = findViewById(R.id.iv_more);
        mRvCities = findViewById(R.id.rv_cities);

        mTopBar.setBackgroundResource(getBackgroundId());

        mRvCities.setLayoutManager(new LinearLayoutManager(this));
        mWeatherList = new ArrayList<>();
        mAdapter = new CitiesAdapter(this, mWeatherList, getBackgroundId());
        mRvCities.setAdapter(mAdapter);

        getPresenter().getWeathers();

    }


    @Override
    public MenuPresenter createPresenter() {
        return new MenuPresenter();
    }

    @Override
    public void onQuery(List<Weather> weatherList) {
        mWeatherList.clear();
        mWeatherList.addAll(weatherList);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onDelete() {
        if (mWeatherList.size() > 0) {
            location = mWeatherList.get(0).getHeWeather6().get(0).getBasic().getCid();
        } else {
            location = null;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_CODE_CHOOSE:
                if (resultCode == RESULT_OK) {
                    setResult(RESULT_OK, data);
                    finish();
                }
                break;
        }
    }

    @Override
    public void onBackPressed() {
        String preLocation = SPUtil.getString("location", null);
        if (preLocation != null && !preLocation.equals(location)) {
            Intent intent = new Intent();
            intent.putExtra("location", location);
            setResult(RESULT_OK, intent);
            finish();
            return;
        }
        super.onBackPressed();
    }
}
