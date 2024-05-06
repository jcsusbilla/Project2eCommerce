package com.example.project2ecommerce.database.entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.project2ecommerce.database.eCommerceDatabase;
import java.util.Objects;

@Entity(tableName = eCommerceDatabase.SAVED_TABLE)
public class SavedPurchases {
    @PrimaryKey(autoGenerate = true)
    //attributes
    private int quantity;
    private double price;
    private int id;
    private String plant_name;
    private int userId;
    //constructor

    public SavedPurchases(String plant_name, int quantity, double price, int userId) {
        this.plant_name = plant_name;
        this.quantity = quantity;
        this.price = price;
        this.userId = userId;
    }

    public String getPlant_name() {
        return plant_name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setPlant_name(String plant_name) {
        this.plant_name = plant_name;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SavedPurchases that = (SavedPurchases) o;
        return quantity == that.quantity && Double.compare(that.price, price) == 0 && id == that.id && userId == that.userId && Objects.equals(plant_name, that.plant_name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(quantity, price, id, plant_name, userId);
    }

    @Override
    public String toString() {
        return "SavedPurchases{" +
                "quantity=" + quantity +
                ", price=" + price +
                ", id=" + id +
                ", plant_name='" + plant_name + '\'' +
                ", userId=" + userId +
                '}';
    }
}
