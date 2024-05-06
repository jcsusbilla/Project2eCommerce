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

@Dao
public interface StoreItemDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(StoreItem... storeItem); //... var arg, takes 0 or made data objects

    @Delete
    void delete(StoreItem... storeItem);

    @Query("SELECT * FROM " + eCommerceDatabase.ITEM_TABLE)
    LiveData<List<StoreItem>> getAllItems();

    @Query("DELETE from " + eCommerceDatabase.ITEM_TABLE)
    void deleteAll();

    @Query("Select * from " + eCommerceDatabase.ITEM_TABLE + " where id == :id")
    LiveData<StoreItem> getItemById(int id);

    @Query("Select * from " + eCommerceDatabase.ITEM_TABLE + " where id == :name")
    LiveData<StoreItem> getIdByName(String name);

    @Query("Select * from " + eCommerceDatabase.ITEM_TABLE + " where price == :name")
    LiveData<StoreItem> getPriceByName(String name);

    @Query("Select * from " + eCommerceDatabase.ITEM_TABLE + " where inStock == :name")
    LiveData<StoreItem> getStockByName(String name);

    @Query("Select * from " + eCommerceDatabase.ITEM_TABLE + " where name == :name")
    LiveData<StoreItem> getItemByName(String name);

}
