package com.example.project2ecommerce;

import static org.junit.Assert.*;

import com.example.project2ecommerce.database.entities.User;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class UserTest {

    private User user;

    @Before
    public void setUp() throws Exception {
        user = new User("testUser", "testUser");
    }

    @After
    public void tearDown() throws Exception {
        user = null;
    }

    @Test
    public void getId() {
    }

    @Test
    public void getUsername() {
        assertEquals("testUser", user.getUsername());    }
    @Test
    public void setUsername() {
        user.setUsername("newUsername");
        assertEquals("newUsername", user.getUsername());    }

    @Test
    public void isAdmin() {
        assertFalse(user.isAdmin());
    }

    @Test
    public void setAdmin() {
        user.setAdmin(true);
        assertTrue(user.isAdmin());
    }
}