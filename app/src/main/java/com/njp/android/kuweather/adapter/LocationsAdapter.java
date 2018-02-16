package com.njp.android.kuweather.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.njp.android.kuweather.R;
import com.njp.android.kuweather.bean.Location;

import java.util.List;

/**
 * 城市列表页的列表适配器
 */

public class LocationsAdapter extends RecyclerView.Adapter<LocationsAdapter.ViewHolder> {

    private Context mContext;
    private List<Location> mLocationList;
    private int backgroundId;
    private OnLocationItemClickListener mListener;

    public LocationsAdapter(Context context, List<Location> locationList, int backgroundId) {
        mContext = context;
        mLocationList = locationList;
        this.backgroundId = backgroundId;
    }

    public void setOnItemClickListener(OnLocationItemClickListener listener) {
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itmView = LayoutInflater.from(mContext)
                .inflate(R.layout.item_location, parent, false);
        return new ViewHolder(itmView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Location location = mLocationList.get(position);
        holder.mCvItem.setBackgroundResource(backgroundId);
        holder.mTvLocation.setText(location.getLocationName());

        if (mListener != null) {
            holder.mCvItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.onClick(location);
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return mLocationList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        ViewGroup mCvItem;
        TextView mTvLocation;

        ViewHolder(View itemView) {
            super(itemView);
            mCvItem = itemView.findViewById(R.id.rl_item);
            mTvLocation = itemView.findViewById(R.id.tv_location);
        }
    }

    public interface OnLocationItemClickListener {
        void onClick(Location location);
    }

}
