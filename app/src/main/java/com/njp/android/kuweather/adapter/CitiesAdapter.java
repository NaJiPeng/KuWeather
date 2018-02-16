package com.njp.android.kuweather.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.njp.android.kuweather.R;
import com.njp.android.kuweather.bean.Weather;
import com.njp.android.kuweather.utils.IconFactory;

import java.util.List;

/**
 * 城市管理界面的列表适配器
 */

public class CitiesAdapter extends RecyclerView.Adapter<CitiesAdapter.ViewHolder> {

    private Context mContext;
    private List<Weather> mWeatherList;
    private int backgroundId;
    private OnCityItemClickListener mClickListener;
    private OnCityItemLongClickListener mLongClickListener;

    public CitiesAdapter(Context context, List<Weather> weatherList, int backgroundId) {
        mContext = context;
        mWeatherList = weatherList;
        this.backgroundId = backgroundId;
    }

    public void setOnItemClickListener(OnCityItemClickListener listener) {
        this.mClickListener = listener;
    }

    public void setOnItemLongClickListener(OnCityItemLongClickListener listener) {
        this.mLongClickListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext)
                .inflate(R.layout.item_city, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Weather weather = mWeatherList.get(position);
        String city = weather.getHeWeather6().get(0).getBasic().getLocation();
        int weatherId = Integer.parseInt(weather.getHeWeather6().get(0).getNow().getCond_code());

        holder.mItem.setBackgroundResource(backgroundId);
        holder.mTvCity.setText(city);
        holder.mIvWeather.setImageResource(IconFactory.getWhiteIcon(mContext, weatherId));

        if (mClickListener != null) {
            holder.mItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mClickListener.onClick(weather);
                }
            });
        }

        if (mLongClickListener != null) {
            holder.mItem.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    mLongClickListener.onLongClick(weather);
                    return true;
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return mWeatherList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        ViewGroup mItem;
        TextView mTvCity;
        ImageView mIvWeather;

        ViewHolder(View itemView) {
            super(itemView);
            mItem = itemView.findViewById(R.id.rl_item);
            mTvCity = itemView.findViewById(R.id.tv_city);
            mIvWeather = itemView.findViewById(R.id.iv_weather);
        }
    }

    public interface OnCityItemClickListener {
        void onClick(Weather weather);
    }

    public interface OnCityItemLongClickListener {
        void onLongClick(Weather weather);
    }

}
