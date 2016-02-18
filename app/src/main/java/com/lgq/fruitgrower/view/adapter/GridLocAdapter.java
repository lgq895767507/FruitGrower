package com.lgq.fruitgrower.view.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lgq.fruitgrower.R;
import com.lgq.fruitgrower.view.widget.WrapHeightGridView;

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
        final ViewHolder holder;
        if (view==null){
            holder = new ViewHolder();
            view = View.inflate(context, R.layout.grid_view_item,null);
            holder.locNameTv = (TextView) view.findViewById(R.id.locNameTv);
            view.setTag(holder);
        }else{
            holder = (ViewHolder) view.getTag();
        }

        WrapHeightGridView gv = (WrapHeightGridView) viewGroup;

        int horizontalSpacing = gv.getHorizontalSpacing();
        int numColumns = gv.getNumColumns();
        int itemWidth = (gv.getWidth() - (numColumns-1) * horizontalSpacing
                - gv.getPaddingLeft() - gv.getPaddingRight()) / numColumns;

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(itemWidth, itemWidth);
        holder.locNameTv.setLayoutParams(params);
        holder.locNameTv.setText(locName.get(i));

        return view;
    }
    public static class ViewHolder {
        public TextView locNameTv;
    }
}
