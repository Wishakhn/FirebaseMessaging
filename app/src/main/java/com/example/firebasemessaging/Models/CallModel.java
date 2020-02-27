package com.example.firebasemessaging.Models;

public class CallModel {
    private String ImageUrl;
  private   int Id;
  int imgId;
    private String calltype;
    int callicon;

    public CallModel(String imageUrl, int id, String calltype) {
        ImageUrl = imageUrl;
        Id = id;
        this.calltype = calltype;
    }

    public CallModel(int id, int imgId,int callicon, String calltype) {
        Id = id;
        this.imgId = imgId;
        this.callicon = callicon;
        this.calltype = calltype;
    }

    public int getCallicon() {
        return callicon;
    }

    public void setCallicon(int callicon) {
        this.callicon = callicon;
    }

    public int getImgId() {
        return imgId;
    }

    public void setImgId(int imgId) {
        this.imgId = imgId;
    }

    public String getImageUrl() {
        return ImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        ImageUrl = imageUrl;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getCalltype() {
        return calltype;
    }

    public void setCalltype(String calltype) {
        this.calltype = calltype;
    }
}
