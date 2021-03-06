package com.lgq.fruitgrower.model.beans;

import java.io.Serializable;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobDate;
import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by lgq on 16-3-1.
 */
public class Pubilsh extends BmobObject implements Serializable {

    private String content;
    private BmobFile photo;
    private String coment;
    private Boolean isFarmer;
    private String name;
    private String comtent_name;
    private String email;
    private BmobFile img;
    private Integer liked;

    public Integer getLiked() {
        if (liked == null){
            return 0;
        }
        return liked;
    }

    public void setLiked(Integer liked) {
        this.liked = liked;
    }

    public BmobFile getImg() {
        if (img == null) {
            return null;
        }
        return img;
    }

    public void setImg(BmobFile img) {
        this.img = img;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public BmobFile getPhoto() {
        if (photo == null) {
            return null;
        }
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
