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


    public Chat insertChat(String message, boolean status, boolean sentstatus, String time){

        Chat chat = new Chat();
        chat.setMessage(message);
        chat.setChatStatus(status);
        chat.setTime(time);
        chat.setSentStatus(sentstatus);
        chat.setCreatedAt(Utils.getCurrentDateTime());
        chat.setModifiedAt(Utils.getCurrentDateTime());
        insertChat(chat);
        return chat;

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


    public void updateChat(final Chat chat) {
        chat.setModifiedAt(Utils.getCurrentDateTime());
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                chatDatabase.chatDao().updateChat(chat);
                return null;
            }
        }.execute();
    }


    public LiveData<List<Chat>> getPendingChats(){
        return chatDatabase.chatDao().fetchPendingChats(false, true);
    }



    public void deleteChat(final int id) {
        final LiveData<Chat> chat = getChat(id);
        if(chat != null) {
            new AsyncTask<Void, Void, Void>() {
                @Override
                protected Void doInBackground(Void... voids) {
                    chatDatabase.chatDao().deleteChat(chat.getValue());
                    return null;
                }
            }.execute();
        }
    }

    public void deleteChat(final Chat chat) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                chatDatabase.chatDao().deleteChat(chat);
                return null;
            }
        }.execute();
    }

    public LiveData<Chat> getChat(int id) {
        return chatDatabase.chatDao().getChat(id);
    }
}
