package com.askjeffreyliu.teslaapi.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.askjeffreyliu.teslaapi.R;
import com.askjeffreyliu.teslaapi.model.Vehicle;
import com.askjeffreyliu.teslaapi.utils.VehicleDiffCallback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

public class MainScreenRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<Vehicle> mList;
    private OnItemClickListener mItemClickListener;

    public MainScreenRecyclerAdapter() {
    }

    private class CellViewHolder extends RecyclerView.ViewHolder {

        private TextView displayName;
        private TextView batteryPercent;
        private TextView batteryDistance;
        private ImageView imageView;
        private Button flashButton;
        private Button honkButton;
        private Button openTrunkButton;

        public CellViewHolder(View itemView) {
            super(itemView);
            displayName = itemView.findViewById(R.id.displayName);
            batteryPercent = itemView.findViewById(R.id.batteryPercent);
            batteryDistance = itemView.findViewById(R.id.batteryDistance);
            imageView = itemView.findViewById(R.id.options);
            flashButton = itemView.findViewById(R.id.flashButton);
            honkButton = itemView.findViewById(R.id.honkButton);
            openTrunkButton = itemView.findViewById(R.id.openTrunkButton);
//            itemView.setOnClickListener(this);
//            itemView.setOnLongClickListener(this);

            flashButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mItemClickListener != null) {
                        mItemClickListener.onFlashClicked(view, getAdapterPosition());
                    }
                }
            });

            honkButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mItemClickListener != null) {
                        mItemClickListener.onHonkClicked(view, getAdapterPosition());
                    }
                }
            });

            openTrunkButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mItemClickListener != null) {
                        mItemClickListener.onOpenTrunkClicked(view, getAdapterPosition());
                    }
                }
            });
        }

//        @Override
//        public void onClick(View view) {
//
//        }

//        @Override
//        public boolean onLongClick(View view) {
//            if (mItemClickListener != null) {
//                mItemClickListener.onItemLongClick(view, getLayoutPosition());
//                return true;
//            }
//            return false;
//        }
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
                Picasso.get().load("https://www.tesla.com/configurator/compositor/?model=mx&view=STUD_3QTR&size=1920&bkba_opt=1&file_type=jpg&options=" + mList.get(position).getOption_codes()).into(cellViewHolder.imageView);
                cellViewHolder.displayName.setText("" + mList.get(position).getDisplay_name());
                cellViewHolder.batteryPercent.setText("" + (mList.get(position).getChargeStateResponseObj() == null ? "null" : mList.get(position).getChargeStateResponseObj().getBattery_level()));
                cellViewHolder.batteryDistance.setText("" + (mList.get(position).getChargeStateResponseObj() == null ? "null" : mList.get(position).getChargeStateResponseObj().getBattery_range()));
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
        void onFlashClicked(View view, int position);

        void onHonkClicked(View view, int position);

        void onOpenTrunkClicked(View view, int position);
    }

    // for both short and long click
    public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }
}