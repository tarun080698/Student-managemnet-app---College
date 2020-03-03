package com.example.project;

import com.google.firebase.database.Exclude;

public class Upload {
    private String uName;
    private String organ;
    private String postT;
    private String postD;
    private String imageUrl;
    private String mKey;

    public Upload() {
//        empty constructor
    }
//    ---------------------------------------------------------Try  remove this. from getters and setters---------------------------

    public Upload(String name, String organi, String pT, String pD) {
        uName = name;
        organ = organi;
        postT = pT;
        postD = pD;
    }

    public Upload(String name, String organi, String pT, String pD, String image) {
        uName = name;
        organ = organi;
        postT = pT;
        postD = pD;
        imageUrl = image;
    }

    public String getuName() {
        return uName;
    }

    public void setuName(String uName) {
        this.uName = uName;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getPostD() {
        return postD;
    }

    public void setPostD(String postD) {
        this.postD = postD;
    }

    public String getPostT() {
        return postT;
    }

    public void setPostT(String postT) {
        this.postT = postT;
    }

    public String getOrgan() {
        return organ;
    }

    public void setOrgan(String organ) {
        this.organ = organ;
    }

    @Exclude
    public String getKey() {
        return mKey;
    }

    @Exclude
    public void setKey(String key) {
        this.mKey = key;
    }
}
