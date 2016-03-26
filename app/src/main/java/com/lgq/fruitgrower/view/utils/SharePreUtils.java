package com.lgq.fruitgrower.view.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.lgq.fruitgrower.model.constance.Constance;

/**
 * Created by lgq on 16-3-26.
 */
public class SharePreUtils {

    public static String getEmailPre(Context context,String tagName,String def) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(Constance.password, Activity.MODE_WORLD_READABLE);
        return sharedPreferences.getString(tagName,def);
    }
    public static String getEmailPre(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(Constance.password, Activity.MODE_WORLD_READABLE);
        return sharedPreferences.getString(Constance.LOGINEMAIL, "Unviald");
    }

    public static void setSharePre(Context context,String tagName,String text){
        SharedPreferences sharedPreferences = context.getSharedPreferences(Constance.password, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor  = sharedPreferences.edit();
        editor.putString(tagName, text);
        editor.commit();
    }

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
        editor.commit();
    }
}
