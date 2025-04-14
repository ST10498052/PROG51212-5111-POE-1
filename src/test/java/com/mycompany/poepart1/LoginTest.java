/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package com.mycompany.poepart1;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;

/**
 *
 * @author RC_Student_lab
 */
public class LoginTest {
    
   
    Login instance;
    
    
    @BeforeEach
    public void setUp() {
        // Fix: Provide valid sample inputs to constructor
        instance = new Login("kyl_1", "Ch&&sec@ke99!", "Kyle", "Gelamba", "+270677306577");
    }

    @Test
    public void testGetuserName() {
        assertEquals("kyl_1", instance.getuserName());
    }

    @Test
    public void testSetuserName() {
        instance.setuserName("newUser");
        assertEquals("newUser", instance.getuserName());
    }

    @Test
    public void testGetpassword() {
        assertEquals("Ch&&sec@ke99!", instance.getpassword());
    }

    @Test
    public void testSetpassword() {
        instance.setpassword("NewPass@123");
        assertEquals("NewPass@123", instance.getpassword());
    }

    @Test
    public void testGetfirstName() {
        assertEquals("Kyle", instance.getfirstName());
    }

    @Test
    public void testSetfirstName() {
        instance.setfirstName("John");
        assertEquals("John", instance.getfirstName());
    }

    @Test
    public void testGetlastName() {
        assertEquals("Gelamba", instance.getlastName());
    }

    @Test
    public void testSetlastName() {
        instance.setlastName("Doe");
        assertEquals("Doe", instance.getlastName());
    }

    @Test
    public void testGetnumber() {
        assertEquals("+270677306577", instance.getnumber());
    }

    @Test
    public void testSetnumber() {
        instance.setnumber("+12345678901");
        assertEquals("+12345678901", instance.getnumber());
    }

    @Test
    public void testCheckusername() {
        assertTrue(Login.checkusername("abc_1"));
        assertFalse(Login.checkusername("abcde")); // no underscore
        assertFalse(Login.checkusername("long_name")); // more than 5 characters
    }

    @Test
    public void testCheckPasswordComplexity() {
        assertTrue(Login.checkPasswordComplexity("Apassw@rd9"));
        assertFalse(Login.checkPasswordComplexity("password")); // too weak
    }

    @Test
    public void testCheckCellPhoneNumber() {
        assertTrue(Login.checkCellPhoneNumber("+27012345678"));
        assertFalse(Login.checkCellPhoneNumber("012345678")); // missing country code
    }

    @Test
    public void testRegisterUser() {
        // Since registerUser prints messages and returns an empty string, just call it to make sure no crash
        String result = instance.registerUser("kyl_1", "Ch&&sec@ke99!", "+270677306577");
        assertNotNull(result);
    }

    @Test
    public void testLoginUser() {
        assertTrue(instance.loginUser("kyl_1", "Ch&&sec@ke99!", "Kyle", "Gelamba"));
    assertFalse(instance.loginUser("wrong", "pass", "Kyle", "Gelamba"));

    }

    @Test
    public void testReturnLoginStatus() {
        String result = instance.returnLoginStatus("kyl_1", "Ch&&sec@ke99!", "Kyle", "Gelamba");
        assertNotNull(result);
    }
}