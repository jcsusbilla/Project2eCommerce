package com.example.project2ecommerce.database.entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.project2ecommerce.database.eCommerceDatabase;
import java.util.Objects;

@Entity(tableName = eCommerceDatabase.PRODUCT_TABLE)
public class StoreItem {
    @PrimaryKey(autoGenerate = true)
    //attributes
    private int id;
    private String name;
    private String desc;
    private double price;
    private int quantity;
    private boolean inStock;

    //constructor
    public StoreItem(int id, String name, String desc, double price, int quantity) {        //maybe add inStock not sure yet
        this.id = id;
        this.name = name;
        this.desc = desc;
        this.price = price;
        this.quantity = quantity;
        inStock = true;                                                                     //default value until admin changes
    }

    //equals & hash
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StoreItem storeItem = (StoreItem) o;
        return id == storeItem.id && Double.compare(storeItem.price, price) == 0 && quantity == storeItem.quantity && inStock == storeItem.inStock && Objects.equals(name, storeItem.name) && Objects.equals(desc, storeItem.desc);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, desc, price, quantity, inStock);
    }

    //getters & setters
    //      **************REMOVE WHAT ISN'T BEING USED LATER**************
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public boolean isInStock() {
        return inStock;
    }

    public void setInStock(boolean inStock) {
        this.inStock = inStock;
    }

    //toString

    @Override
    public String toString() {
        return "StoreItem{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", desc='" + desc + '\'' +
                ", price=" + price +
                ", quantity=" + quantity +
                ", inStock=" + inStock +
                '}';
    }
}
