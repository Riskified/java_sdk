package com.ry.secure;

import java.io.Serializable;

public class AesConfigBean implements Serializable {
    private static final long serialVersionUID = -3139888533298384987L;
    private String first;
    private String workKey;
    private String second;
    private String salt;
    private String third;
    private String hmacWorkKey;

    public AesConfigBean() {
    }

    public void setWorkKey(String workKey) {
        this.workKey = workKey;
    }

    public String getWorkKey() {
        return this.workKey;
    }

    public String getFirst() {
        return this.first;
    }

    public void setFirst(String first) {
        this.first = first;
    }

    public void setThird(String third) {
        this.third = third;
    }

    public String getThird() {
        return this.third;
    }

    public void setSecond(String second) {
        this.second = second;
    }

    public String getSecond() {
        return this.second;
    }

    public String getHmacWorkKey() {
        return this.hmacWorkKey;
    }

    public void setHmacWorkKey(String hmacWorkKey) {
        this.hmacWorkKey = hmacWorkKey;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getSalt() {
        return this.salt;
    }
}
