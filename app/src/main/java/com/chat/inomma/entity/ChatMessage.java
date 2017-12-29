package com.chat.inomma.entity;

import com.chat.inomma.AppConstants;

import java.util.HashMap;

/**
 * Created by Vahe on 12/26/2017.
 */

//todo need to use for local database with Room library
public class ChatMessage {
    private String uId;
    private String text;
    private String time;

    public ChatMessage() {
    }

    public ChatMessage(HashMap<String,String > messageData) {
        this.text = messageData.get(AppConstants.MESSAGE_TEXT_TABLE);
        this.time = messageData.get(AppConstants.MESSAGE_TIME_TABLE);
    }

    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
