package com.example.project2ecommerce.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.project2ecommerce.database.entities.SavedPurchases;
import com.example.project2ecommerce.database.entities.StoreItem;
import com.example.project2ecommerce.database.entities.eCommerce;

import java.util.List;

@Dao
public interface SavedPurchasesDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertSaved(SavedPurchases... savedPurchases);

    @Delete
    void delete (SavedPurchases savedPurchases);

    @Query("SELECT * FROM " + eCommerceDatabase.SAVED_TABLE)
    LiveData<List<SavedPurchases>> getAllSavedPurchases();
}
