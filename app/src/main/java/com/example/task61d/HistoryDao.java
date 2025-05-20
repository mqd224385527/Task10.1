package com.example.task61d;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface HistoryDao {
    @Insert
    void insert(History history);

    @Query("SELECT * FROM history WHERE user_id=:userId")
    List<History> getAllHistory(int userId);
}
