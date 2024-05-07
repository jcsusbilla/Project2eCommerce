package com.example.project2ecommerce.database;
import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.project2ecommerce.database.entities.StoreItem;
import com.example.project2ecommerce.database.entities.eCommerce;
import java.util.List;

//represents queries run in database
@Dao
public interface eCommerceDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(eCommerce... ecommerce);
    @Query("SELECT * FROM " + eCommerceDatabase.eCommerceTable + " ORDER BY date DESC")
    List<eCommerce> getAllRecords();

    @Query("SELECT * FROM " + eCommerceDatabase.eCommerceTable)
    LiveData<List<eCommerce>> getAllItemsInCart();

    //@Query("DELETE FROM " + eCommerceDatabase.eCommerceTable + " WHERE userId = :userId")
//    @Delete
//    void delete(int userId);

//    @Query("Select * from " + eCommerceDatabase.ITEM_TABLE + " where id == :id")
//    LiveData<eCommerce> getUserItemById(int id);


    @Query("SELECT * FROM " + eCommerceDatabase.eCommerceTable + " WHERE userId = :userId")
    //@Query("SELECT * FROM " + eCommerceDatabase.eCommerceTable + " WHERE userId = :userId ORDER BY date DESC")
    LiveData<List<eCommerce>> getAllCartsByUserId(int userId);

//    @Query("SELECT * FROM " + eCommerceDatabase.eCommerceTable + " WHERE userId = :userId")
//    LiveData<List<eCommerce>> getUsersItems(int userId);
}
