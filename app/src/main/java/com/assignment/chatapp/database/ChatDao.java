package com.assignment.chatapp.database;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface ChatDao{


    @Insert
    Long insertChat(Chat chatDb);

    @Query("SELECT * FROM Chat ORDER BY created_at asc")
    LiveData<List<Chat>> fetchAllChat();

    @Update
    void updateChat(Chat chat);

    @Query("SELECT * FROM Chat WHERE sentStatus LIKE :status AND chatStatus LIKE :chatstatus ORDER BY modified_at asc")
    LiveData<List<Chat>> fetchPendingChats(boolean status, boolean chatstatus);


    @Delete
    void deleteChat(Chat chat);


    @Query("SELECT * FROM Chat WHERE chatId =:chatId")
    LiveData<Chat> getChat(int chatId);
}
