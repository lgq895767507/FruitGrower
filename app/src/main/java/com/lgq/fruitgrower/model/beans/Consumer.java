package com.lgq.fruitgrower.model.beans;

import java.io.Serializable;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by lgq on 16-1-29.
 */
public class Consumer extends BmobObject implements Serializable {
    private String name;
    private String signature;
    private String phone;
    private String address;
    private BmobFile img;
    private String password;
    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        if (name == null) {
            return "";
        }
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSignature() {
        if (signature == null) {
            return "";
        }
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getAddress() {
        if (address == null) {
            return "设置您的地址";
        }
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        if (phone == null) {
            return null;
        }
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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
}
