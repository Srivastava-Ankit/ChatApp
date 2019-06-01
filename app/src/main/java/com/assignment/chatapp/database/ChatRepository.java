package com.assignment.chatapp.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.os.AsyncTask;

import com.assignment.chatapp.Utils;

import java.util.Date;
import java.util.List;

public class ChatRepository {


    private String DB_NAME = "db_chat";
    private ChatDatabase chatDatabase;

    public ChatRepository(Context context) {
        chatDatabase = Room.databaseBuilder(context, ChatDatabase.class, DB_NAME).build();
    }


    public void insertChat(String message, boolean status, String time){

        Chat chat = new Chat();
        chat.setMessage(message);
        chat.setChatStatus(status);
        chat.setTime(time);
        chat.setCreatedAt(Utils.getCurrentDateTime());
        insertChat(chat);

    }

    public void insertChat(final Chat chat){

        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                chatDatabase.chatDao().insertChat(chat);
                return null;
            }
        }.execute();
    }

    public LiveData<List<Chat>> getChats() {
        return chatDatabase.chatDao().fetchAllChat();
    }
}
