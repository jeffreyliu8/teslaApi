package com.askjeffreyliu.teslaapi.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.askjeffreyliu.teslaapi.R;
import com.askjeffreyliu.teslaapi.model.Vehicle;
import com.askjeffreyliu.teslaapi.utils.VehicleDiffCallback;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

public class MainScreenRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<Vehicle> mList;
    private OnItemClickListener mItemClickListener;

    public MainScreenRecyclerAdapter() {
    }

    private class CellViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        private TextView textView;
        private TextView textView1;
        private TextView textView2;

        public CellViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.id);
            textView1 = itemView.findViewById(R.id.access);
            textView2 = itemView.findViewById(R.id.charge);

            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mItemClickListener != null) {
                mItemClickListener.onItemClick(view, getLayoutPosition());
            }
        }

        @Override
        public boolean onLongClick(View view) {
            if (mItemClickListener != null) {
                mItemClickListener.onItemLongClick(view, getLayoutPosition());
                return true;
            }
            return false;
        }
    }

    public void setList(List<Vehicle> list) {
        final VehicleDiffCallback diffCallback = new VehicleDiffCallback(mList, list);
        final DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(diffCallback);

        if (mList == null) {
            mList = new ArrayList<>();
        } else {
            mList.clear();
        }
        if (list != null) {
            for (int i = 0; i < list.size(); i++) {
                mList.add((Vehicle) list.get(i).clone());
            }
        }
        diffResult.dispatchUpdatesTo(this);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(final ViewGroup viewGroup, int viewType) {

        switch (viewType) {
            default: {
                View v1 = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.detail_list_item_type_title, viewGroup, false);
                return new CellViewHolder(v1);
            }
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        switch (viewHolder.getItemViewType()) {
            default: {
                CellViewHolder cellViewHolder = (CellViewHolder) viewHolder;
                cellViewHolder.textView.setText("" + mList.get(position).getId());
                cellViewHolder.textView1.setText("" + (mList.get(position).getMobileAccessEnabled() == null ? "null" : mList.get(position).getMobileAccessEnabled()));
                cellViewHolder.textView2.setText("" + (mList.get(position).getChargeStateResponseObj() == null ? "null" : mList.get(position).getChargeStateResponseObj().getCharging_state()));
                break;
            }
        }
    }

    @Override
    public int getItemCount() {
        if (mList == null)
            return 0;
        return mList.size();
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);

        void onItemLongClick(View view, int position);
    }

    // for both short and long click
    public void SetOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }
}