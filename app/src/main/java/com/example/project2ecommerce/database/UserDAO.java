package com.example.project2ecommerce.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.project2ecommerce.database.entities.User;

import java.util.List;

@Dao //<-- dont forget this when making new db's *reminder*
public interface UserDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(User... user);

    @Delete
    void delete(User user);

    @Query("SELECT * FROM " + eCommerceDatabase.USER_TABLE + " ORDER BY username")
    LiveData<List<User>> getAllUsers();

    @Query("DELETE from " + eCommerceDatabase.USER_TABLE)
    void deleteAll();

    @Query("SELECT * FROM " + eCommerceDatabase.USER_TABLE + " WHERE username == :username")
    LiveData<User> getUserByUserName(String username);

    //Found that Query was looking for username (WHERE id == :userId) and changed it to look for id - Miguel
    @Query("SELECT * FROM " + eCommerceDatabase.USER_TABLE + " WHERE id == :userId")
    LiveData<User> getUserByUserId(int userId);

    @Query("UPDATE " + eCommerceDatabase.USER_TABLE + " SET password = :newPassword WHERE id = :userId")
    void  updateUserPassword(int userId, String newPassword);
}
