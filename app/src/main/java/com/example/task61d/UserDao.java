package com.example.task61d;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface UserDao {
    @Insert
    void insert(User user);

    @Query("SELECT * FROM user WHERE username = :username AND password = :password LIMIT 1")
    User login(String username, String password);

    @Query("UPDATE user SET interest = :interest WHERE id = :id")
    void updateInterest(int id, String interest);

    @Query("SELECT * FROM user WHERE id=:id")
    User getUserById(int id);
}
