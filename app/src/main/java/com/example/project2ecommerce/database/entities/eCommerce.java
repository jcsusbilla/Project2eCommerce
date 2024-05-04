package com.example.project2ecommerce.database.entities;
import androidx.lifecycle.LiveData;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.project2ecommerce.database.eCommerceDatabase;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

//represents eCommerce entity
//a POJO
@Entity(tableName = eCommerceDatabase.eCommerceTable)            //denote that it will be stored in a database
public class eCommerce {
    @PrimaryKey(autoGenerate = true)        //unique identifier for piece of data
    private int product_id;
    private String product_name;
    private String product_desc;
    private Double product_price;
    private boolean in_stock;
    private LocalDateTime date;
    private int userId;
    private int itemId; //db

    //constructor
    public eCommerce(String product_name, String product_desc, Double product_price, boolean in_stock, int userId, int itemId) {        //adjust if need to later
        this.product_name = product_name;
        this.product_desc = product_desc;
        this.product_price = product_price;
        this.in_stock = in_stock;
        this.userId = userId;
        date = LocalDateTime.now();                                             //current time stamp
        this.itemId = itemId;
    }

    //getters & setters

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getProduct_desc() {
        return product_desc;
    }

    public void setProduct_desc(String product_desc) {
        this.product_desc = product_desc;
    }

    public Double getProduct_price() {
        return product_price;
    }

    public void setProduct_price(Double product_price) {
        this.product_price = product_price;
    }

    public boolean isIn_stock() {
        return in_stock;
    }

    public void setIn_stock(boolean in_stock) {
        this.in_stock = in_stock;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    //hashcode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        eCommerce eCommerce = (eCommerce) o;
        return product_id == eCommerce.product_id && in_stock == eCommerce.in_stock && userId == eCommerce.userId && itemId == eCommerce.itemId && Objects.equals(product_name, eCommerce.product_name) && Objects.equals(product_desc, eCommerce.product_desc) && Objects.equals(product_price, eCommerce.product_price) && Objects.equals(date, eCommerce.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(product_id, product_name, product_desc, product_price, in_stock, date, userId, itemId);
    }
}
