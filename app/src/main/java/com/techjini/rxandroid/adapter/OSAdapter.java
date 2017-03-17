package com.techjini.rxandroid.adapter;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.techjini.rxandroid.activity.MainActivity;
import com.techjini.rxandroid.model.OSModel;
import com.techjini.rxandroid.R;
import com.techjini.rxandroid.databinding.RowBinding;
import com.techjini.rxandroid.utils.CommonUtils;

import java.util.ArrayList;

/**
 * Created by Ashif on 17/3/17,March,2017
 * TechJini Solutions
 * Banglore,India
 */

public class OSAdapter extends RecyclerView.Adapter {

    private ArrayList<OSModel> OSList;

    public OSAdapter(ArrayList<OSModel> OSList) {
        this.OSList = OSList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RowBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.row,parent,false);
        return new OSViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((OSViewHolder) holder).binding.setOs(OSList.get(position));
    }

    @Override
    public int getItemCount() {
        return OSList.size();
    }

    private class OSViewHolder extends RecyclerView.ViewHolder {
        private RowBinding binding;
        public OSViewHolder(RowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

            binding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    CommonUtils.showToast(MainActivity.getHomeContext(),OSList.get(getAdapterPosition()).getName());
                }
            });
        }
    }
}
