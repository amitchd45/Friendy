package com.solutions.friendy.Models;

public class ChatModelClass {

    String msg, type, time, media;

    public ChatModelClass(){

    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setMedia(String media) {
        this.media = media;
    }

    public ChatModelClass(String msg, String type, String time) {
        this.time = time;
        this.msg = msg;
        this.type = type;

    }

    public String getMedia() {
        return media;
    }

    public String getTime() {
        return time;
    }

    public String getMsg() {
        return msg;
    }

    public String getType() {
        return type;
    }

}
