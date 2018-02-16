package com.njp.android.kuweather.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.njp.android.kuweather.R;
import com.njp.android.kuweather.adapter.LocationsAdapter;
import com.njp.android.kuweather.bean.Location;
import com.njp.android.kuweather.presenter.ChoosePresenter;
import com.njp.android.kuweather.utils.SPUtil;
import com.njp.android.kuweather.view.ChooseView;

import java.util.ArrayList;
import java.util.List;

public class ChooseActivity extends BaseActivity<ChoosePresenter> implements ChooseView {

    private int level;
    private String province;

    private TextView mTvTitle;
    private ViewGroup mTopBar;
    private ImageView mIvBack;
    private RecyclerView mRvLocations;
    private List<Location> mLocationList;
    private LocationsAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose);

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

        mAdapter.setOnItemClickListener(new LocationsAdapter.OnLocationItemClickListener() {
            @Override
            public void onClick(Location location) {
                switch (level) {
                    case 0:
                        String province = location.getLocationName();
                        getPresenter().getCities(province);
                        ChooseActivity.this.province = province;
                        mTvTitle.setText(province);
                        level++;
                        break;
                    case 1:
                        String city = location.getLocationName();
                        getPresenter().getDistricts(city);
                        mTvTitle.setText(city);
                        level++;
                        break;
                    case 2:
                        Intent intent = new Intent();
                        intent.putExtra("location", location.getLocationId());
                        setResult(RESULT_OK, intent);
                        finish();
                        break;
                }
            }
        });

    }

    private void initView() {

        mTvTitle = findViewById(R.id.tv_title);
        mTopBar = findViewById(R.id.top_bar);
        mIvBack = findViewById(R.id.iv_back);
        mRvLocations = findViewById(R.id.rv_locations);

        mRvLocations.setLayoutManager(new LinearLayoutManager(this));
        mLocationList = new ArrayList<>();
        mAdapter = new LocationsAdapter(this, mLocationList, getBackgroundId());
        mRvLocations.setAdapter(mAdapter);


        mTopBar.setBackgroundResource(getBackgroundId());

        getPresenter().getProvinces();

    }


    @Override
    public ChoosePresenter createPresenter() {
        return new ChoosePresenter();
    }


    @Override
    public void onProvinces(List<Location> provinces) {
        mLocationList.clear();
        mLocationList.addAll(provinces);
        mAdapter.notifyDataSetChanged();
        mRvLocations.scrollToPosition(0);
    }

    @Override
    public void onCities(List<Location> cities) {
        mLocationList.clear();
        mLocationList.addAll(cities);
        mAdapter.notifyDataSetChanged();
        mRvLocations.scrollToPosition(0);
    }

    @Override
    public void onDistricts(List<Location> locationList) {
        mLocationList.clear();
        mLocationList.addAll(locationList);
        mAdapter.notifyDataSetChanged();
        mRvLocations.scrollToPosition(0);

    }

    @Override
    public void onBackPressed() {
        switch (level) {
            case 1:
                getPresenter().getProvinces();
                mTvTitle.setText("中国");
                level--;
                return;
            case 2:
                getPresenter().getCities(province);
                mTvTitle.setText(province);
                level--;
                return;
        }
        super.onBackPressed();
    }
}
