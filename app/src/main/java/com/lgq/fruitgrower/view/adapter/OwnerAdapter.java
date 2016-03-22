package com.lgq.fruitgrower.view.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.lgq.fruitgrower.R;
import com.lgq.fruitgrower.model.entity.OwnerSetting;
import com.lgq.fruitgrower.view.utils.ToastUtils;


import java.util.ArrayList;

/**
 * Created by lgq on 16-2-16.
 */
public class OwnerAdapter extends BaseAdapter {
    private Context context;
    private  ArrayList<OwnerSetting> ownerSettings;


    public OwnerAdapter(Context context, ArrayList<OwnerSetting> ownerSettings){
        this.context = context;
        this.ownerSettings = ownerSettings;
    }



    @Override
    public int getCount() {
        return ownerSettings.size();
    }

    @Override
    public OwnerSetting getItem(int i) {
        return ownerSettings.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        final ViewHolder holder;
        if(view == null) {
            holder = new ViewHolder();
            view = View.inflate(context, R.layout.owner_listview_item, null);
            holder.v_divider = view.findViewById(R.id.v_divider);
            holder.ll_content = view.findViewById(R.id.ll_content);
            holder.iv_left = (ImageView) view.findViewById(R.id.iv_left);
            holder.tv_subhead = (TextView) view.findViewById(R.id.tv_subhead);
            holder.tv_caption = (TextView) view.findViewById(R.id.tv_caption);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        // set data
        OwnerSetting item = getItem(i);
        holder.iv_left.setImageResource(item.getLeftImg());
        holder.tv_subhead.setText(item.getSubhead());
        holder.tv_caption.setText(item.getCaption());

        holder.v_divider.setVisibility(item.isShowTopDivider() ?
                View.VISIBLE : View.GONE);


        return view;
    }
    public static class ViewHolder {
        public View v_divider;
        public View ll_content;
        public ImageView iv_left;
        public TextView tv_subhead;
        public TextView tv_caption;
    }
}
