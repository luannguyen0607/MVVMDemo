package com.luannt.demomvvm.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.luannt.demomvvm.model.User;

import java.util.List;

public interface UserDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(User user);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void bulkInsert(List<User> users);

    @Query("SELECT * FROM user")
    LiveData<List<User>>getAllUsers();

    @Query("SELECT * FROM user WHERE id = :id")
    User getUserById(int id);

    @Query("DELETE FROM user WHERE id = :id")
    void deleteUserById(int id);

    @Query("DELETE FROM user")
    void deleteAllUsers();

    @Query("SELECT * FROM user WHERE name LIKE :name")
    LiveData<List<User>> getUserByName(String name);
}
