package com.lgq.fruitgrower.model.beans;

import java.io.Serializable;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobDate;
import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by lgq on 16-2-18.
 */
public class Chat   extends BmobObject implements Serializable {

    private String own_content;
    private String other_name;
    private BmobDate time;
    private String other_content;
    private BmobFile owe_img;
    private BmobFile other_img;


    public String getOwn_content() {
        return own_content;
    }

    public void setOwn_content(String own_content) {
        this.own_content = own_content;
    }

    public String getOther_name() {
        return other_name;
    }

    public void setOther_name(String other_name) {
        this.other_name = other_name;
    }

    public BmobDate getTime() {
        return time;
    }

    public void setTime(BmobDate time) {
        this.time = time;
    }

    public String getOther_content() {
        return other_content;
    }

    public void setOther_content(String other_content) {
        this.other_content = other_content;
    }

    public BmobFile getOwe_img() {
        return owe_img;
    }

    public void setOwe_img(BmobFile owe_img) {
        this.owe_img = owe_img;
    }

    public BmobFile getOther_img() {
        return other_img;
    }

    public void setOther_img(BmobFile other_img) {
        this.other_img = other_img;
    }
}
