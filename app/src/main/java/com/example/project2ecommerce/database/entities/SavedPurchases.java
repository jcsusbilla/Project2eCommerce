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
    //constructor

    public SavedPurchases(String plant_name, int quantity, double price) {
        this.plant_name = plant_name;
        this.quantity = quantity;
        this.price = price;
    }

    public String getPlant_name() {
        return plant_name;
    }

    public void setPlant_name(String plant_name) {
        this.plant_name = plant_name;
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

    public void setPrice(double price) {
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SavedPurchases that = (SavedPurchases) o;
        return quantity == that.quantity && Double.compare(that.price, price) == 0 && id == that.id && Objects.equals(plant_name, that.plant_name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(plant_name, quantity, price, id);
    }

    @Override
    public String toString() {
        return "SavedPurchases{" +
                "plant_name='" + plant_name + '\'' +
                ", quantity=" + quantity +
                ", price=" + price +
                ", id=" + id +
                '}';
    }
}
