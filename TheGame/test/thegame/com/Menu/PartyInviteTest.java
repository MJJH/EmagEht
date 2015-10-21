/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thegame.com.Menu;

import java.util.Date;
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
public class PartyInviteTest {
    
    /**
     *
     */
    public PartyInviteTest()
    {
    }
    
    /**
     *
     */
    @BeforeClass
    public static void setUpClass()
    {
    }
    
    /**
     *
     */
    @AfterClass
    public static void tearDownClass()
    {
    }
    
    /**
     *
     */
    @Before
    public void setUp()
    {
    }
    
    /**
     *
     */
    @After
    public void tearDown()
    {
    }

    /**
     * Test of getDate method, of class PartyInvite.
     */
    @Test
    public void testGetDate()
    {
        System.out.println("getDate");
        PartyInvite instance = null;
        Date expResult = null;
        Date result = instance.getDate();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getParty method, of class PartyInvite.
     */
    @Test
    public void testGetParty()
    {
        System.out.println("getParty");
        PartyInvite instance = null;
        Party expResult = null;
        Party result = instance.getParty();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getAccount method, of class PartyInvite.
     */
    @Test
    public void testGetAccount()
    {
        System.out.println("getAccount");
        PartyInvite instance = null;
        Account expResult = null;
        Account result = instance.getAccount();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getSender method, of class PartyInvite.
     */
    @Test
    public void testGetSender()
    {
        System.out.println("getSender");
        PartyInvite instance = null;
        Account expResult = null;
        Account result = instance.getSender();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
