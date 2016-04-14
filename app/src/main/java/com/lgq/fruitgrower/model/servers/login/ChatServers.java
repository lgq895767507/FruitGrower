package com.lgq.fruitgrower.model.servers.login;

import android.content.Context;
import android.util.Log;

import com.lgq.fruitgrower.model.beans.Chat;
import com.lgq.fruitgrower.view.act.ChatActivity;

import java.util.List;
import java.util.logging.Logger;

import cn.bmob.newim.BmobIM;
import cn.bmob.newim.bean.BmobIMConversation;
import cn.bmob.newim.bean.BmobIMUserInfo;
import cn.bmob.newim.event.MessageEvent;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by lgq on 16-4-12.
 */
public class ChatServers {

    private static ChatServers chatServers = new ChatServers();

    public static ChatServers getInstance(){
        return chatServers;
    }
    private ChatServers(){}

    public Context getContext(){
        return ChatActivity.getInstance();
    }

    /**查询用户信息
     * @param objectId
     * @param listener
     */
    public void queryUserInfo(String objectId, final QueryUserListener listener){
        BmobQuery<Chat> query = new BmobQuery<>();
        query.addWhereEqualTo("objectId", objectId);
        query.findObjects(getContext(), new FindListener<Chat>() {
            @Override
            public void onSuccess(List<Chat> list) {
                if(list!=null && list.size()>0){
                    listener.internalDone(list.get(0), null);
                }else{
                    listener.internalDone(new BmobException(000, "查无此人"));
                }
            }

            @Override
            public void onError(int i, String s) {
                listener.internalDone(new BmobException(i, s));
            }
        });
    }

    /**更新用户资料和会话资料
    * @param event
    * @param listener
    */
    public void updateUserInfo(MessageEvent event,final UpdateCacheListener listener){
        final BmobIMConversation conversation=event.getConversation();
        final BmobIMUserInfo info =event.getFromUserInfo();
        String username =info.getName();
        String title =conversation.getConversationTitle();
        //sdk内部，将新会话的会话标题用objectId表示，因此需要比对用户名和会话标题--单聊，后续会根据会话类型进行判断
        if(!username.equals(title)) {
            ChatServers.getInstance().queryUserInfo(info.getUserId(), new QueryUserListener() {
                @Override
                public void done(Chat s, BmobException e) {
                    if(e==null){
                        String name =s.getEmail();
                   //     String avatar = s.getAvatar();
                    //    conversation.setConversationIcon(avatar);
                        conversation.setConversationTitle(name);
                        info.setName(name);
                   //     info.setAvatar(avatar);
                        //更新用户资料
                        BmobIM.getInstance().updateUserInfo(info);
                        //更新会话资料
                        BmobIM.getInstance().updateConversation(conversation);
                    }else{
                        Log.i("lgq","error:"+e);
                    }
                    listener.done(null);
                }
            });
        }else{
            listener.internalDone(null);
        }
    }
}
