package com.example.project2ecommerce;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import com.example.project2ecommerce.database.entities.User;

/**
 *  Author: JC SUSBILLA
 *  Test for User DAO
 */

//constructor and destructor
public class UnitTest1_JC {
    User u1;
    User u2;
    User u3;

    @Before
    public void setup(){
        u1 = new User("StephenCurry","3pointer");
        u2 = new User("Skyrim", "dovah");
        u3 = new User("DrC", "iscool");
    }

    @After
    void tearDown(){
        u1 = null;
        u2 = null;
        u3 = null;
    }

    @Test
    public void usernameTest(){
        assertEquals(u1.getUsername(), "StephenCurry");
        assertNotEquals(u3.getUsername(), "DrGross");
        assertNotEquals(u1.getUsername(), u2.getUsername());
    }

    @Test
    public void passwordTest(){
        assertEquals(u1.getPassword(), "3pointer");
        assertNotEquals(u2.getPassword(), "stormcloaks");
        assertNotEquals(u1.getPassword(), u3.getPassword());
    }
}
