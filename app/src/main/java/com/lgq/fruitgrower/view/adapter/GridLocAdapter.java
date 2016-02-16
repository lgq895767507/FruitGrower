package com.lgq.fruitgrower.view.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

import com.lgq.fruitgrower.R;

import java.util.ArrayList;

/**
 * Created by lgq on 16-2-16.
 */
public class GridLocAdapter extends BaseAdapter {
    private Context context;
    private  ArrayList<String> locName;
    TextView locNameTv;

    public GridLocAdapter(Context context, ArrayList<String> locName){
        this.context = context;
        this.locName = locName;
    }



    @Override
    public int getCount() {
        return locName.size();
    }

    @Override
    public String getItem(int i) {
        return locName.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        GridView gv = (GridView) viewGroup;

        view = View.inflate(context, R.layout.grid_view_item,null);
        locNameTv = (TextView) view.findViewById(R.id.locNameTv);

        locNameTv.setText(locName.get(i));

        return view;
    }
}
