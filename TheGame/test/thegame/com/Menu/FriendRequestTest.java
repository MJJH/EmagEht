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
public class FriendRequestTest {
    
    public FriendRequestTest()
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
     * Test of getAccountSource method, of class FriendRequest.
     */
    @Test
    public void testGetAccountSource()
    {
        System.out.println("getAccountSource");
        FriendRequest instance = null;
        Account expResult = null;
        Account result = instance.getAccountSource();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getAccountDestination method, of class FriendRequest.
     */
    @Test
    public void testGetAccountDestination()
    {
        System.out.println("getAccountDestination");
        FriendRequest instance = null;
        Account expResult = null;
        Account result = instance.getAccountDestination();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
