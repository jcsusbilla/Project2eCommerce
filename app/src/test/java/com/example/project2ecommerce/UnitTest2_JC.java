package com.example.project2ecommerce;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import com.example.project2ecommerce.database.entities.StoreItem;

/**
 *  Author: JC SUSBILLA
 *  Test for StoreItem DAO
 */

//constructor and destructor
public class UnitTest2_JC {
    StoreItem item1;
    StoreItem item2;
    StoreItem item3;

    @Before
    public void setup(){
        StoreItem item1 = new StoreItem("Monstera", "N/A", 15.99, 3);
        StoreItem item2 = new StoreItem("Fern", "N/A", 2.99, 5);
        StoreItem item3 = new StoreItem("Snake Plant", "N/A", 9.99, 5);
    }

    @After
    void tearDown(){
        item1 = null;
        item2 = null;
        item3 = null;
    }

    @Test
    public void nameTest(){
        assertEquals(item1.getName(), "Monstera");
        assertEquals(item2.getName(), "Fern");
        assertNotEquals(item3.getName(), item2.getName());
    }

    @Test
    public void priceTest(){
        assertEquals(item1.getPrice(), 15.99, 0.1);
        assertEquals(item2.getPrice(), 2.99, 0.1);
        assertNotEquals(item3.getPrice(), item2.getPrice());
    }

    @Test
    public void quantityTest(){
        assertEquals(item1.getQuantity(), 3);
        assertEquals(item2.getQuantity(), 5);
        assertNotEquals(item3.getQuantity(), item2.getQuantity());
    }
}
