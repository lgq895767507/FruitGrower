package com.lgq.fruitgrower.view.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.bumptech.glide.DrawableRequestBuilder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.lgq.fruitgrower.R;
import com.lgq.fruitgrower.model.beans.Goods;
import com.lgq.fruitgrower.model.beans.Pubilsh;
import com.lgq.fruitgrower.model.constance.Constance;
import com.lgq.fruitgrower.view.utils.ToastUtils;
import com.lgq.fruitgrower.view.widget.WrapHeightGridView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

/**
 * Created by lgq on 16-3-14.
 */
public class StatusAdapter extends RecyclerView.Adapter<StatusAdapter.MyViewHolder> {

    //获取上下文
    private Context context;
    private ArrayList<Pubilsh> datas;

    //hashMap 保存
    private static HashMap<Integer,Boolean> hashChange = new HashMap<>();

    MyViewHolder.ItemClick itemClick;

    public ArrayList<Pubilsh> getDatas() {
        return datas;
    }

    public StatusAdapter(MyViewHolder.ItemClick itemClick) {
        this.itemClick = itemClick;
        datas = new ArrayList<>();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        MyViewHolder holder = new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_card_view, parent,
                false), itemClick);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        //设置头像
        if (datas.get(position).getImg() != null) {
            Log.i("lgq", "position" + position);
            Glide.with(context)
                    .load(datas.get(position).getPhoto().getFileUrl(context))
                    .into(holder.iv_avatar);
        } else {
            Bitmap defaultImg = BitmapFactory.decodeResource(this.context.getResources(), R.mipmap.logo);
            holder.iv_avatar.setImageBitmap(defaultImg);
        }
        //设置名字
        if (datas.get(position).getName() != null){
            holder.tv_subhead.setText(datas.get(position).getName());
        }else{
            holder.tv_subhead.setText(datas.get(position).getEmail());
        }
        //设置发布时间
        holder.tv_caption.setText(datas.get(position).getCreatedAt());
        //设置内容
        holder.tv_content.setText(datas.get(position).getContent());
        //设置图片内容,并缓存图片和缩略图片，增强应用的性能增强。
        if (datas.get(position).getPhoto() != null) {
            Log.i("lgq", "position" + position);
            Glide.with(context)
                    .load(datas.get(position).getPhoto().getFileUrl(context))
                    .thumbnail(Constance.SizeHalf)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(holder.iv_image);
            holder.include_status_image.setVisibility(View.VISIBLE);
            holder.iv_image.setVisibility(View.VISIBLE);
        } else {
            holder.include_status_image.setVisibility(View.GONE);
            holder.iv_image.setVisibility(View.GONE);
        }

        Log.i("lgq","hashMap:"+hashChange.get(position));
        if (hashChange.get(position) == null) {
            holder.iv_like_bottom.setImageDrawable(context.getResources().getDrawable(R.mipmap.timeline_icon_like_disable));
        } else if (hashChange.get(position)) {
            holder.iv_like_bottom.setImageDrawable(context.getResources().getDrawable(R.mipmap.timeline_icon_like));
        }else{
            holder.iv_like_bottom.setImageDrawable(context.getResources().getDrawable(R.mipmap.timeline_icon_like_disable));
        }

    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder implements AdapterView.OnClickListener {

        public ImageView iv_avatar;
        public TextView tv_subhead;
        public TextView tv_caption;
        public TextView tv_content;
        public ImageView iv_like_bottom;

        public ImageView iv_image;
        public WrapHeightGridView gv_images;
        public TextView tv_retweeted_content;

        public LinearLayout ll_share_bottom;
        public LinearLayout ll_comment_bottom;
        public LinearLayout ll_like_bottom;

        public FrameLayout include_status_image;

        public LinearLayout ll_card_content;



        //设置点赞的改变值
        private boolean isChanged = false;
        //region 回调接口
        ItemClick itemClick;

        public interface ItemClick {
            void onBtnCommentClick(int position);

            void onBtnShareClick(int position);

            void onRootViewClick(int position);

            void onBtnLikeClick(int position);

            void onBtnUnLikeClick(int position);
        }
        //endregion

        public MyViewHolder(View itemView, ItemClick itemClick) {
            super(itemView);

            this.itemClick = itemClick;
            iv_avatar = (ImageView) itemView.findViewById(R.id.iv_avatar);
            tv_subhead = (TextView) itemView.findViewById(R.id.tv_subhead);
            tv_caption = (TextView) itemView.findViewById(R.id.tv_caption);
            tv_content = (TextView) itemView.findViewById(R.id.tv_content);
            iv_like_bottom = (ImageView) itemView.findViewById(R.id.iv_like_bottom);

            iv_image = (ImageView) itemView.findViewById(R.id.iv_image);
            gv_images = (WrapHeightGridView) itemView.findViewById(R.id.gv_images);
            tv_retweeted_content = (TextView) itemView.findViewById(R.id.tv_retweeted_content);

            ll_share_bottom = (LinearLayout) itemView.findViewById(R.id.ll_share_bottom);
            ll_comment_bottom = (LinearLayout) itemView.findViewById(R.id.ll_comment_bottom);
            ll_like_bottom = (LinearLayout) itemView.findViewById(R.id.ll_like_bottom);

            include_status_image = (FrameLayout) itemView.findViewById(R.id.include_status_image);
            ll_card_content = (LinearLayout) itemView.findViewById(R.id.ll_card_content);

            ll_card_content.setOnClickListener(this);
            ll_comment_bottom.setOnClickListener(this);
            ll_share_bottom.setOnClickListener(this);
            ll_like_bottom.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            switch (v.getId()) {
                case R.id.ll_card_content:
                    if (itemClick != null) {
                        itemClick.onRootViewClick(getAdapterPosition());
                    }
                    break;
                case R.id.ll_share_bottom:
                    if (itemClick != null) {
                        itemClick.onBtnShareClick(getAdapterPosition());
                    }
                    break;
                case R.id.ll_comment_bottom:
                    if (itemClick != null) {
                        itemClick.onBtnCommentClick(getAdapterPosition());
                    }
                    break;
                case R.id.ll_like_bottom:
                    if (itemClick != null) {
                        if (isChanged) {
                            //设置点击背景
                            Drawable drawableLiled = v.getResources().getDrawable(R.mipmap.timeline_icon_like_disable);
                            iv_like_bottom.setImageDrawable(drawableLiled);
                            itemClick.onBtnUnLikeClick(getAdapterPosition());
                        }else {
                            Drawable drawableLiled = v.getResources().getDrawable(R.mipmap.timeline_icon_like);
                            iv_like_bottom.setImageDrawable(drawableLiled);
                            itemClick.onBtnLikeClick(getAdapterPosition());
                        }
                        isChanged = !isChanged;
                        hashChange.put(getAdapterPosition(),isChanged);
                    }
                    break;
            }
        }
    }
}
