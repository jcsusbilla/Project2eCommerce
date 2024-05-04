package com.example.project2ecommerce.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.project2ecommerce.database.eCommerceDatabase;
import com.example.project2ecommerce.database.entities.StoreItem;
import com.example.project2ecommerce.database.entities.User;

import java.util.List;

//STORE IN itemtable
@Dao
public interface StoreItemDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(StoreItem... storeItem); //... var arg, takes 0 or made data objects

    @Delete
    void delete(StoreItem... storeItem);

    @Query("SELECT * FROM " + eCommerceDatabase.ITEM_TABLE + " ORDER BY name")
    LiveData<List<StoreItem>> getAllItems();

    @Query("DELETE from " + eCommerceDatabase.ITEM_TABLE)
    void deleteAll();

}
