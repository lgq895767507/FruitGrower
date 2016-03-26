package com.lgq.fruitgrower.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.bumptech.glide.DrawableRequestBuilder;
import com.bumptech.glide.Glide;
import com.lgq.fruitgrower.R;
import com.lgq.fruitgrower.model.beans.Goods;
import com.lgq.fruitgrower.model.beans.Pubilsh;
import com.lgq.fruitgrower.view.utils.ToastUtils;
import com.lgq.fruitgrower.view.widget.WrapHeightGridView;

import java.util.ArrayList;

/**
 * Created by lgq on 16-3-14.
 */
public class StatusAdapter extends RecyclerView.Adapter<StatusAdapter.MyViewHolder> {

    //获取上下文
    private Context context;
    private ArrayList<Pubilsh> datas;

    public StatusAdapter(Context context, ArrayList<Pubilsh> datas) {
        this.datas = datas;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        MyViewHolder holder = new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_card_view, parent,
                false));
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        //设置头像
        if (datas.get(position).getPhoto() != null) {
            Glide.with(context)
                    .load(datas.get(position).getPhoto().getFileUrl(context))
                    .into(holder.iv_avatar);
        }
        //设置名字
        holder.tv_subhead.setText(datas.get(position).getName());
        //设置发布时间
        holder.tv_caption.setText(datas.get(position).getCreatedAt());
        //设置内容
        holder.tv_content.setText(datas.get(position).getContent());
        //设置图片内容
        if (datas.get(position).getPhoto() != null) {
            Glide.with(context)
                    .load(datas.get(position).getPhoto().getFileUrl(context))
                    .into(holder.iv_image);
            holder.include_status_image.setVisibility(View.VISIBLE);
            holder.iv_image.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        public ImageView iv_avatar;
        public TextView tv_subhead;
        public TextView tv_caption;
        public TextView tv_content;

        public ImageView iv_image;
        public WrapHeightGridView gv_images;
        public TextView tv_retweeted_content;

        public LinearLayout ll_share_bottom;
        public LinearLayout ll_comment_bottom;
        public LinearLayout ll_like_bottom;

        public FrameLayout include_status_image;


        public MyViewHolder(View itemView) {
            super(itemView);

            iv_avatar = (ImageView) itemView.findViewById(R.id.iv_avatar);
            tv_subhead = (TextView) itemView.findViewById(R.id.tv_subhead);
            tv_caption = (TextView) itemView.findViewById(R.id.tv_caption);
            tv_content = (TextView) itemView.findViewById(R.id.tv_content);

            iv_image = (ImageView) itemView.findViewById(R.id.iv_image);
            gv_images = (WrapHeightGridView) itemView.findViewById(R.id.gv_images);
            tv_retweeted_content = (TextView) itemView.findViewById(R.id.tv_retweeted_content);

            ll_share_bottom = (LinearLayout) itemView.findViewById(R.id.ll_share_bottom);
            ll_comment_bottom = (LinearLayout) itemView.findViewById(R.id.ll_comment_bottom);
            ll_like_bottom = (LinearLayout) itemView.findViewById(R.id.ll_like_bottom);

            include_status_image = (FrameLayout) itemView.findViewById(R.id.include_status_image);
        }
    }
}
