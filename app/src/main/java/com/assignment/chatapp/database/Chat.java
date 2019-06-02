package com.assignment.chatapp.database;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;

import com.assignment.chatapp.TimestampConverter;

import java.io.Serializable;
import java.util.Date;

@Entity
public class Chat implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private int chatId;

    String message;
    Boolean chatStatus;
    Boolean sentStatus;

    String time;

    public Boolean getSentStatus() {
        return sentStatus;
    }

    public void setSentStatus(Boolean sentStatus) {
        this.sentStatus = sentStatus;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @ColumnInfo(name = "created_at")
    @TypeConverters({TimestampConverter.class})
    private Date createdAt;

    @ColumnInfo(name = "modified_at")
    @TypeConverters({TimestampConverter.class})
    private Date modifiedAt;

    public Date getModifiedAt() {
        return modifiedAt;
    }

    public void setModifiedAt(Date modifiedAt) {
        this.modifiedAt = modifiedAt;
    }

    public int getChatId() {
        return chatId;
    }

    public void setChatId(int chatId) {
        this.chatId = chatId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Boolean getChatStatus() {
        return chatStatus;
    }

    public void setChatStatus(Boolean chatStatus) {
        this.chatStatus = chatStatus;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}
