package com.assignment.chatapp.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface ChatDao{


    @Insert
    Long insertChat(Chat chatDb);

    @Query("SELECT * FROM Chat ORDER BY created_at asc")
    LiveData<List<Chat>> fetchAllChat();
}
