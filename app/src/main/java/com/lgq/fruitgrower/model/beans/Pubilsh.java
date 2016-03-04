package com.lgq.fruitgrower.model.beans;

import java.io.Serializable;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobDate;
import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by lgq on 16-3-1.
 */
public class Pubilsh extends BmobObject implements Serializable {

    String content;
    BmobFile photo;
    String coment;
    Boolean isFarmer;
    BmobDate time;
    String name;
    String comtent_name;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public BmobFile getPhoto() {
        return photo;
    }

    public void setPhoto(BmobFile photo) {
        this.photo = photo;
    }

    public String getComent() {
        return coment;
    }

    public void setComent(String coment) {
        this.coment = coment;
    }

    public Boolean getIsFarmer() {
        return isFarmer;
    }

    public void setIsFarmer(Boolean isFarmer) {
        this.isFarmer = isFarmer;
    }

    public BmobDate getTime() {
        return time;
    }

    public void setTime(BmobDate time) {
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getComtent_name() {
        return comtent_name;
    }

    public void setComtent_name(String comtent_name) {
        this.comtent_name = comtent_name;
    }
}
