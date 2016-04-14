package com.lgq.fruitgrower.view.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.lgq.fruitgrower.R;
import com.lgq.fruitgrower.model.beans.Chat;
import com.lgq.fruitgrower.view.utils.DateUtils;
import com.lgq.fruitgrower.view.utils.ToastUtils;

import java.util.ArrayList;

/**
 * Created by lgq on 16-2-16.
 */
public class MessageAdapter extends BaseAdapter implements View.OnClickListener{
    private Context context;
    private  ArrayList<Chat> chats;
    //获取i
    private  int getI = 0;

    PositionClick positionClick;

    public MessageAdapter(Context context, ArrayList<Chat> chats,PositionClick positionClick){
        this.positionClick = positionClick;
        this.context = context;
        this.chats = chats;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.ll_userinfo){
            Log.i("lgq","iiiiii:::"+getI);
            positionClick.positionClick(getI);
        }
    }

    public interface PositionClick{
        void positionClick(int position);
    }

    @Override
    public int getCount() {
        return chats.size();
    }

    @Override
    public Chat getItem(int i) {
        return chats.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        final ViewHolder holder;
        if(view == null) {
            holder = new ViewHolder();
            view = View.inflate(context, R.layout.message_listview_item, null);
            holder.ll_userinfo = view.findViewById(R.id.ll_userinfo);
            holder.iv_avatar = (ImageView) view.findViewById(R.id.iv_avatar);
            holder.tv_subhead = (TextView) view.findViewById(R.id.tv_subhead);
            holder.tv_caption = (TextView) view.findViewById(R.id.tv_caption);
            holder.tv_data = (TextView) view.findViewById(R.id.tv_data);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        // set data
        Chat item = getItem(i);

        getI = i;

        Glide.with(context)
                .load(item.getOther_img().getFileUrl(context))
                .into(holder.iv_avatar);
        holder.tv_subhead.setText(item.getOther_name());
        holder.tv_caption.setText(item.getOther_content());
        holder.tv_data.setText(DateUtils.getShortTime(item.getTime().getDate()));

        holder.ll_userinfo.setOnClickListener(this);

        return view;
    }
    public static class ViewHolder {
        public View ll_userinfo;
        public ImageView iv_avatar;
        public TextView tv_subhead;
        public TextView tv_caption;
        public TextView tv_data;
    }
}
