package com.lgq.fruitgrower.view.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.lgq.fruitgrower.model.constance.Constance;

/**
 * Created by lgq on 16-3-26.
 */
public class SharePreUtils {

    /**
     * 获取本地sharepre文件中的键值
     * @param context  上下文
     * @param tagName  key
     * @param def   默认值
     * @return
     */
    public static String getEmailPre(Context context,String tagName,String def) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(Constance.password, context.MODE_WORLD_READABLE);
        return sharedPreferences.getString(tagName,def);
    }
    /**
     * 专门为登陆做的一个获取登陆的email
     * @param context  上下文
     * @return
     */
    public static String getEmailPre(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(Constance.password, context.MODE_WORLD_READABLE);
        return sharedPreferences.getString(Constance.LOGINEMAIL, "Unviald");
    }

    /**
     *
     * @param context 上下文
     * @param tagName key
     * @param text values
     */
    public static void setSharePre(Context context,String tagName,String text){
        SharedPreferences sharedPreferences = context.getSharedPreferences(Constance.password, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor  = sharedPreferences.edit();
        editor.putString(tagName, text);
        editor.commit();
    }

    /**
     * 获取本地sharepre文件中的键值
     * @param context  上下文
     * @param tagName  key
     * @param def   默认值
     * @return
     */
    public static boolean getEmailPre(Context context,String tagName,boolean def) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(Constance.password, context.MODE_WORLD_READABLE);
        return sharedPreferences.getBoolean(tagName, def);
    }

    /**
     *
     * @param context 上下文
     * @param tagName key
     * @param flag values
     */
    public static void setSharePre(Context context,String tagName,boolean flag){
        SharedPreferences sharedPreferences = context.getSharedPreferences(Constance.password, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor  = sharedPreferences.edit();
        editor.putBoolean(tagName, flag);
        editor.commit();
    }

    /**
     * 初始化或者清除退出登陆的一些sharepre记录
     * @param context
     */
    public static void clear(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(Constance.password, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(Constance.LOGINVERIFIED, false);
        editor.putString(Constance.LOGINEMAIL, "");
        editor.putString(Constance.imgHeadPath,"");
        editor.putString(Constance.nickname,"");
        editor.putString(Constance.signature,"");
        editor.putString(Constance.phone,"");
        editor.putString(Constance.address,"");
        editor.putBoolean(Constance.ONFLAG,true);
        editor.commit();
    }
}
