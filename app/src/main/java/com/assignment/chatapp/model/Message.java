package com.assignment.chatapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Message {

    @SerializedName("chatBotName")
    @Expose
    String chatBotName;

    @SerializedName("chatBotID")
    @Expose
    long chatBotId;

    @SerializedName("message")
    @Expose
    String message;

    @SerializedName("emotion")
    @Expose
    String emotion;

    public String getChatBotName() {
        return chatBotName;
    }

    public void setChatBotName(String chatBotName) {
        this.chatBotName = chatBotName;
    }

    public long getChatBotId() {
        return chatBotId;
    }

    public void setChatBotId(long chatBotId) {
        this.chatBotId = chatBotId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getEmotion() {
        return emotion;
    }

    public void setEmotion(String emotion) {
        this.emotion = emotion;
    }
}
