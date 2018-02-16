package com.njp.android.kuweather.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.njp.android.kuweather.R;
import com.njp.android.kuweather.bean.Background;

import java.util.List;

/**
 * 皮肤列表适配器
 */

public class SkinsAdapter extends RecyclerView.Adapter<SkinsAdapter.ViewHolder> {

    private Context mContext;
    private List<Background> mBackgroundList;
    private OnSkinItemClickListener mListener;

    public SkinsAdapter(Context context, List<Background> backgroundList) {
        mContext = context;
        mBackgroundList = backgroundList;
    }

    public void setListener(OnSkinItemClickListener listener) {
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext)
                .inflate(R.layout.item_skin, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Background background = mBackgroundList.get(position);
        holder.name.setText(background.getName());
        holder.item.setBackgroundResource(background.getResId());
        if (mListener != null) {
            holder.item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.onClick(background);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mBackgroundList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ViewGroup item;
        TextView name;

        public ViewHolder(View itemView) {
            super(itemView);
            item = itemView.findViewById(R.id.rl_item);
            name = itemView.findViewById(R.id.tv_name);
        }
    }

    public interface OnSkinItemClickListener {
        void onClick(Background background);
    }

}
