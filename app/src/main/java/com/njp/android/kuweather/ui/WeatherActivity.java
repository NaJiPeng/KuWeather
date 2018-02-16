package com.njp.android.kuweather.ui;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.ActivityOptions;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.nightonke.boommenu.BoomButtons.ButtonPlaceEnum;
import com.nightonke.boommenu.BoomButtons.HamButton;
import com.nightonke.boommenu.BoomMenuButton;
import com.nightonke.boommenu.Piece.PiecePlaceEnum;
import com.njp.android.kuweather.R;
import com.njp.android.kuweather.bean.Lifestyle;
import com.njp.android.kuweather.bean.Weather;
import com.njp.android.kuweather.presenter.WeatherPresenter;
import com.njp.android.kuweather.utils.BMBUtil;
import com.njp.android.kuweather.utils.IconFactory;
import com.njp.android.kuweather.utils.SPUtil;
import com.njp.android.kuweather.view.WeatherView;

import java.util.ArrayList;
import java.util.List;


public class WeatherActivity extends BaseActivity<WeatherPresenter> implements WeatherView {

    private static final int REQUEST_CODE_MENU = 1001;
    private static final int REQUEST_CODE_LOCATION = 2001;

    private SwipeRefreshLayout mSRL;
    private ImageView mIvBackground;
    private ImageView mIvMenu;
    private ImageView mIvSettings;
    private TextView mTvCity;
    private ImageView mIvWeather;
    private TextView mTvTmp;
    private TextView mTvWeather;
    private TextView mTvWindDir;
    private TextView mTvWindSc;
    private TextView mTvUpdateTime;
    private TextView mTvDate1;
    private TextView mTvDate2;
    private TextView mTvDate3;
    private TextView mTvTmp1;
    private TextView mTvTmp2;
    private TextView mTvTmp3;
    private ImageView mIvWeather1;
    private ImageView mIvWeather2;
    private ImageView mIvWeather3;
    private BoomMenuButton mBMB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        initView();
        initEvent();

        mSRL.setRefreshing(true);
        if (SPUtil.getBoolean("auto_location", true)) {
            getPresenter().requestLocationPermissions();
        } else {
            getPresenter().getWeather();
        }
    }

    /**
     * 初始化事件
     */
    protected void initEvent() {

        mSRL.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getPresenter().refreshWeather();
            }
        });

        mIvMenu.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("RestrictedApi")
            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WeatherActivity.this, MenuActivity.class);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    startActivityForResult(intent, REQUEST_CODE_MENU,
                            ActivityOptions.makeSceneTransitionAnimation(
                                    WeatherActivity.this,
                                    mIvBackground,
                                    "share"
                            ).toBundle());
                } else {
                    startActivityForResult(intent, REQUEST_CODE_MENU);
                }
            }
        });

        mIvSettings.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WeatherActivity.this, SettingActivity.class);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(
                            WeatherActivity.this,
                            mIvBackground,
                            "share"
                    ).toBundle());
                } else {
                    startActivity(intent);
                }
            }
        });

    }

    /**
     * 初始化视图
     */
    protected void initView() {

        mSRL = findViewById(R.id.srl);
        mIvBackground = findViewById(R.id.iv_background);
        mIvMenu = findViewById(R.id.iv_menu);
        mIvSettings = findViewById(R.id.iv_settings);
        mTvCity = findViewById(R.id.tv_city);
        mIvWeather = findViewById(R.id.iv_weather);
        mTvTmp = findViewById(R.id.tv_tmp);
        mTvWeather = findViewById(R.id.tv_weather);
        mTvWindDir = findViewById(R.id.tv_wind_dir);
        mTvWindSc = findViewById(R.id.tv_wind_sc);
        mTvUpdateTime = findViewById(R.id.tv_update_time);
        mBMB = findViewById(R.id.bmb);
        mTvDate1 = findViewById(R.id.tv_date_1);
        mTvDate2 = findViewById(R.id.tv_date_2);
        mTvDate3 = findViewById(R.id.tv_date_3);
        mTvTmp1 = findViewById(R.id.tv_tmp_1);
        mTvTmp2 = findViewById(R.id.tv_tmp_2);
        mTvTmp3 = findViewById(R.id.tv_tmp_3);
        mIvWeather1 = findViewById(R.id.iv_weather_1);
        mIvWeather2 = findViewById(R.id.iv_weather_2);
        mIvWeather3 = findViewById(R.id.iv_weather_3);

    }

    @Override
    protected void onStart() {
        super.onStart();
        mIvBackground.setImageResource(getBackgroundId());
    }

    @Override
    public WeatherPresenter createPresenter() {
        return new WeatherPresenter();
    }


    @Override
    public void onSuccess(Weather weather) {
        Weather.HeWeather6Bean heWeather6Bean = weather.getHeWeather6().get(0);
        Weather.HeWeather6Bean.BasicBean basic = heWeather6Bean.getBasic();
        Weather.HeWeather6Bean.UpdateBean update = heWeather6Bean.getUpdate();
        Weather.HeWeather6Bean.NowBean now = heWeather6Bean.getNow();
        List<Weather.HeWeather6Bean.DailyForecastBean> daily_forecast =
                heWeather6Bean.getDaily_forecast();
        Weather.HeWeather6Bean.DailyForecastBean dailyForecastBean1 = daily_forecast.get(0);
        Weather.HeWeather6Bean.DailyForecastBean dailyForecastBean2 = daily_forecast.get(1);
        Weather.HeWeather6Bean.DailyForecastBean dailyForecastBean3 = daily_forecast.get(2);
        List<Weather.HeWeather6Bean.LifestyleBean> lifestyles = heWeather6Bean.getLifestyle();
        List<Lifestyle> lifestyleList = new ArrayList<>();
        for (Weather.HeWeather6Bean.LifestyleBean lifestyleBean : lifestyles) {

            Lifestyle lifestyle = new Lifestyle();
            String type = lifestyleBean.getType();
            switch (type) {
                case "drsg":
                    lifestyle.setIcon(R.drawable.clothes);
                    lifestyle.setTitle("穿衣指数：" + lifestyleBean.getBrf());
                    lifestyle.setContent(lifestyleBean.getTxt());
                    lifestyleList.add(lifestyle);
                    break;
                case "flu":
                    lifestyle.setIcon(R.drawable.cold);
                    lifestyle.setTitle("感冒指数：" + lifestyleBean.getBrf());
                    lifestyle.setContent(lifestyleBean.getTxt());
                    lifestyleList.add(lifestyle);
                    break;
                case "sport":
                    lifestyle.setIcon(R.drawable.sports);
                    lifestyle.setTitle("运动指数：" + lifestyleBean.getBrf());
                    lifestyle.setContent(lifestyleBean.getTxt());
                    lifestyleList.add(lifestyle);
                    break;
                case "uv":
                    lifestyle.setIcon(R.drawable.sunshine);
                    lifestyle.setTitle("防晒指数：" + lifestyleBean.getBrf());
                    lifestyle.setContent(lifestyleBean.getTxt());
                    lifestyleList.add(lifestyle);
                    break;
                case "cw":
                    lifestyle.setIcon(R.drawable.car);
                    lifestyle.setTitle("洗车指数：" + lifestyleBean.getBrf());
                    lifestyle.setContent(lifestyleBean.getTxt());
                    lifestyleList.add(lifestyle);
                    break;
                case "air":
                    lifestyle.setIcon(R.drawable.pollution);
                    lifestyle.setTitle("污染指数：" + lifestyleBean.getBrf());
                    lifestyle.setContent(lifestyleBean.getTxt());
                    lifestyleList.add(lifestyle);
                    break;
            }

        }

        String city = basic.getLocation();
        String updateTime = update.getLoc();
        String tmp = now.getTmp() + "°";
        String weatherNow = now.getCond_txt();
        int weatherCode = Integer.parseInt(now.getCond_code());
        String windDir = now.getWind_dir();
        String windSc = now.getWind_sc();
        String[] strings1 = dailyForecastBean1.getDate().split("-");
        String date1 = strings1[1] + "-" + strings1[2];
        String[] strings2 = dailyForecastBean2.getDate().split("-");
        String date2 = strings2[1] + "-" + strings2[2];
        String[] strings3 = dailyForecastBean3.getDate().split("-");
        String date3 = strings3[1] + "-" + strings3[2];
        String tmp1 = dailyForecastBean1.getTmp_max() + "°/" + dailyForecastBean1.getTmp_min() + "°";
        String tmp2 = dailyForecastBean2.getTmp_max() + "°/" + dailyForecastBean2.getTmp_min() + "°";
        String tmp3 = dailyForecastBean3.getTmp_max() + "°/" + dailyForecastBean3.getTmp_min() + "°";
        int weatherCode1 = Integer.parseInt(dailyForecastBean1.getCond_code_d());
        int weatherCode2 = Integer.parseInt(dailyForecastBean2.getCond_code_d());
        int weatherCode3 = Integer.parseInt(dailyForecastBean3.getCond_code_d());

        mTvCity.setText(city);
        mTvUpdateTime.setText(updateTime);
        mTvTmp.setText(tmp);
        mTvWeather.setText(weatherNow);
        mIvWeather.setImageResource(IconFactory.getLargeIcon(weatherCode));
        mTvWindDir.setText(windDir);
        mTvWindSc.setText(windSc);
        mTvDate1.setText(date1);
        mTvDate2.setText(date2);
        mTvDate3.setText(date3);
        mTvTmp1.setText(tmp1);
        mTvTmp2.setText(tmp2);
        mTvTmp3.setText(tmp3);
        mIvWeather1.setImageResource(IconFactory.getBlackIcon(this, weatherCode1));
        mIvWeather2.setImageResource(IconFactory.getBlackIcon(this, weatherCode2));
        mIvWeather3.setImageResource(IconFactory.getBlackIcon(this, weatherCode3));

        int count = lifestyleList.size();
        PiecePlaceEnum ppe = BMBUtil.getPiecePlaceEnum(count);
        ButtonPlaceEnum bpe = BMBUtil.getButtonPlaceEnum(count);
        if (ppe != null) {
            mBMB.setPiecePlaceEnum(ppe);
            mBMB.setButtonPlaceEnum(bpe);
            mBMB.clearBuilders();
            for (Lifestyle l : lifestyleList) {
                mBMB.addBuilder(
                        new HamButton.Builder()
                                .normalImageRes(l.getIcon())
                                .normalText(l.getTitle())
                                .subNormalText(l.getContent())
                );
            }
            mBMB.setVisibility(View.VISIBLE);
        }

        if (SPUtil.getBoolean("auto_change_skin", true)) {
            int backgroundId = IconFactory.getBackground(this);
            setBackgroundId(backgroundId);
            mIvBackground.setImageResource(backgroundId);
        }
        mSRL.setRefreshing(false);
    }

    @Override
    public void onError(String e) {
        Snackbar.make(mBMB, "获取数据失败：" + e, Snackbar.LENGTH_SHORT).show();
        mSRL.setRefreshing(false);
    }

    @Override
    public void onRequestPermission(List<String> permissionList) {
        for (int i = 0; i < permissionList.size(); i++) {
            if (ContextCompat.checkSelfPermission(
                    WeatherActivity.this, permissionList.get(i))
                    == PackageManager.PERMISSION_GRANTED) {
                permissionList.remove(i);
            }
        }
        if (permissionList.size() == 0) {
            getPresenter().startLocation();
            return;
        }
        String[] permissions = permissionList.toArray(new String[permissionList.size()]);
        ActivityCompat.requestPermissions(WeatherActivity.this,
                permissions, REQUEST_CODE_LOCATION);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_CODE_MENU:
                if (resultCode == RESULT_OK) {
                    String location = data.getStringExtra("location");
                    getPresenter().changeLocation(location);
                }
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_LOCATION:
                if (grantResults.length > 0) {
                    for (int result : grantResults) {
                        if (result != PackageManager.PERMISSION_GRANTED) {
                            Snackbar.make(mBMB, "获取位置失败", Snackbar.LENGTH_SHORT).show();
                            getPresenter().getWeather();
                            return;
                        }
                    }
                    getPresenter().getLocation();
                }
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            moveTaskToBack(false);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        getPresenter().stopLocation();
    }

}
