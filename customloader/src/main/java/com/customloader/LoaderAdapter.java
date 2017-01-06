package com.customloader;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by Mohanraj.S on 04/01/17.
 */

public class LoaderAdapter extends BaseAdapter {
    List<String> data = new ArrayList<>();
    LayoutInflater mLayoutInflater;

    public LoaderAdapter(Context context) {
        mLayoutInflater= LayoutInflater.from(context);
    }

    /*public LoaderAdapter(Collection<String> data) {
        this.data.clear();
        this.data.addAll(data);
        notifyDataSetChanged();

    }*/

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public String getItem(int position) {
        return data.get(position);
    }


    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {

        if(view== null){
            view =mLayoutInflater.inflate(R.layout.list_item_loader,viewGroup,false);
        }
        TextView txtVw_lstItem = (TextView)view.findViewById(R.id.txtVw_lstItem);
        txtVw_lstItem.setText(data.get(position));
        return view;
    }
    public void swapData(Collection<String>data){
        this.data.clear();
        this.data.addAll(data);
        notifyDataSetChanged();

    }
}
