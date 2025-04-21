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
        assertFalse("New user should NOT be admin by default", user.isAdmin());
    }

    @Test
    public void testAdminPrivilege() {
        assertFalse("User is not admin by default", user.isAdmin());
        user.setAdmin(true);
        assertTrue("Admin privileges are set", user.isAdmin());
        user.setAdmin(false);
        assertFalse("User is no longer admin", user.isAdmin());
    }
}
