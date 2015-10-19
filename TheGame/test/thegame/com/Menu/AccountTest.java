/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thegame.com.Menu;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author laure
 */
public class AccountTest {
    
    public AccountTest()
    {
    }
    
    @BeforeClass
    public static void setUpClass()
    {
    }
    
    @AfterClass
    public static void tearDownClass()
    {
    }
    
    @Before
    public void setUp()
    {
    }
    
    @After
    public void tearDown()
    {
    }

    /**
     * Test of changePassword method, of class Account.
     */
    @Test
    public void testChangePassword()
    {
        System.out.println("changePassword");
        Account account = null;
        String oldP = "";
        String newP = "";
        Account instance = null;
        boolean expResult = false;
        boolean result = instance.changePassword(account, oldP, newP);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of sendFriendRequest method, of class Account.
     */
    @Test
    public void testSendFriendRequest()
    {
        System.out.println("sendFriendRequest");
        Account to = null;
        Account instance = null;
        instance.sendFriendRequest(to);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of changeSetting method, of class Account.
     */
    @Test
    public void testChangeSetting()
    {
        System.out.println("changeSetting");
        String setting = "";
        String value = "";
        Account instance = null;
        instance.changeSetting(setting, value);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getPassword method, of class Account.
     */
    @Test
    public void testGetPassword()
    {
        System.out.println("getPassword");
        Account instance = null;
        String expResult = "";
        String result = instance.getPassword();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getUsername method, of class Account.
     */
    @Test
    public void testGetUsername()
    {
        System.out.println("getUsername");
        Account instance = null;
        String expResult = "";
        String result = instance.getUsername();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of acceptFriendRequest method, of class Account.
     */
    @Test
    public void testAcceptFriendRequest()
    {
        System.out.println("acceptFriendRequest");
        int friendRequestID = 0;
        Account instance = null;
        boolean expResult = false;
        boolean result = instance.acceptFriendRequest(friendRequestID);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of declineFriendRequest method, of class Account.
     */
    @Test
    public void testDeclineFriendRequest()
    {
        System.out.println("declineFriendRequest");
        int friendRequestID = 0;
        Account instance = null;
        boolean expResult = false;
        boolean result = instance.declineFriendRequest(friendRequestID);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
