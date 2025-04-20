package com.example.project02;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import com.example.project02.database.entities.User;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class UserUnitTest {

    private User user;

    @Before
    public void setup(){
        user = new User("testUser", "testPass");
    }

    @After
    public void teardown() {
        user = null;
    }

    @Test
    public void testUserCreation() {
        assertEquals("testUser", user.getUsername());
        assertEquals("testPass", user.getPassword());
        user.setPassword("pizza123");
        assertFalse("New user should NOT be admin by default", user.isAdmin());
    }

    @Test
    public void testAdminPrivilege() {
        user.setAdmin(true);
        assertTrue("Admin privilege should be set", user.isAdmin());
        user.setAdmin(false);
        assertFalse("User is no longer admin", user.isAdmin());
    }
}
