package com.njp.android.kuweather.ui;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;

import com.njp.android.kuweather.R;
import com.njp.android.kuweather.adapter.SkinsAdapter;
import com.njp.android.kuweather.bean.Background;
import com.njp.android.kuweather.presenter.SettingPresenter;
import com.njp.android.kuweather.service.AutoUpdateService;
import com.njp.android.kuweather.utils.SPUtil;
import com.njp.android.kuweather.view.SettingView;

import java.util.List;

public class SettingActivity extends BaseActivity<SettingPresenter> implements SettingView {

    private static final String TAG = "SettingActivity";

    private ViewGroup mTopBar;
    private ImageView mIvBack;
    private Switch mSwLocation;
    private Switch mSwUpdate;
    private Switch mSwSkin;
    private ViewGroup LlUpdate;
    private ViewGroup LlSkin;
    private TextView mTvUpdateTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

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

        mSwLocation.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SPUtil.putBoolean("auto_location", isChecked);
            }
        });

        mSwUpdate.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SPUtil.putBoolean("auto_update", isChecked);
                Intent intent = new Intent(SettingActivity.this, AutoUpdateService.class);
                if (isChecked) {
                    startService(intent);
                } else {
                    stopService(intent);
                }
            }
        });

        mSwSkin.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SPUtil.putBoolean("auto_change_skin", isChecked);
            }
        });

        LlUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopMenuTime();
            }
        });

        LlSkin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getPresenter().getBackgroundList();
            }
        });

    }

    private void initView() {

        mTopBar = findViewById(R.id.top_bar);
        mIvBack = findViewById(R.id.iv_back);
        mSwLocation = findViewById(R.id.sw_location);
        mSwUpdate = findViewById(R.id.sw_update);
        mSwSkin = findViewById(R.id.sw_skin);
        LlUpdate = findViewById(R.id.ll_update);
        LlSkin = findViewById(R.id.ll_skin);
        mTvUpdateTime = findViewById(R.id.tv_update_time);

        mTopBar.setBackgroundResource(getBackgroundId());

        mSwLocation.setChecked(SPUtil.getBoolean("auto_location", true));
        mSwUpdate.setChecked(SPUtil.getBoolean("auto_update", true));
        mSwSkin.setChecked(SPUtil.getBoolean("auto_change_skin", true));
        mTvUpdateTime.setText(SPUtil.getInt("update_time", 3) + "小时");

    }

    @Override
    public SettingPresenter createPresenter() {
        return new SettingPresenter();
    }

    private void showPopMenuTime() {

        final Dialog dialog = new Dialog(this, R.style.BottomDialog);
        View view = LayoutInflater.from(this).inflate(R.layout.popmenu_time, null);
        ListView listView = view.findViewById(R.id.lv_times);
        final String[] times = {
                "1小时", "2小时", "3小时", "4小时", "5小时", "6小时"
        };
        listView.setAdapter(new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, times));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                SPUtil.putInt("update_time", position + 1);
                mTvUpdateTime.setText(times[position]);
                dialog.dismiss();
            }
        });
        dialog.setContentView(view);
        Window window = dialog.getWindow();
        window.setGravity(Gravity.BOTTOM);
        WindowManager.LayoutParams params = window.getAttributes();
        params.x = 0;
        params.y = 0;
        params.width = getResources().getDisplayMetrics().widthPixels;
        params.alpha = 9f;
        window.setAttributes(params);
        dialog.show();

    }

    private void showPopMenuSkin(List<Background> backgroundList) {

        final Dialog dialog = new Dialog(this, R.style.BottomDialog);
        View view = LayoutInflater.from(this).inflate(R.layout.popmenu_skin, null);
        RecyclerView recyclerView = view.findViewById(R.id.rv_skins);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        SkinsAdapter adapter = new SkinsAdapter(this, backgroundList);
        recyclerView.setAdapter(adapter);
        adapter.setListener(new SkinsAdapter.OnSkinItemClickListener() {
            @Override
            public void onClick(Background background) {
                int backgroundId = background.getResId();
                setBackgroundId(backgroundId);
                mTopBar.setBackgroundResource(backgroundId);
                dialog.dismiss();
            }
        });


        dialog.setContentView(view);
        Window window = dialog.getWindow();
        window.setGravity(Gravity.BOTTOM);
        WindowManager.LayoutParams params = window.getAttributes();
        params.x = 0;
        params.y = 0;
        params.width = getResources().getDisplayMetrics().widthPixels;
        params.alpha = 9f;
        window.setAttributes(params);
        dialog.show();

    }

    @Override
    public void onQuery(List<Background> backgroundList) {
        showPopMenuSkin(backgroundList);
    }
}
