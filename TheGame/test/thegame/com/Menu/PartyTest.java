/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thegame.com.Menu;

import java.util.ArrayList;
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
public class PartyTest {

    public PartyTest()
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
     * Test of sendInvite method, of class Party.
     */
    @Test
    public void testSendInvite()
    {
        System.out.println("sendInvite");
        Account invite = null;
        Account sender = null;
        Party instance = null;
        instance.sendInvite(invite, sender);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of sendMessage method, of class Party.
     */
    @Test
    public void testSendMessage()
    {
        System.out.println("sendMessage");
        Account sender = null;
        String message = "";
        Party instance = null;
        boolean expResult = false;
        boolean result = instance.sendMessage(sender, message);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of leaveParty method, of class Party.
     */
    @Test
    public void testLeaveParty()
    {
        System.out.println("leaveParty");
        Account leaveAccount = null;
        Party instance = null;
        Account expResult = null;
        Account result = instance.leaveParty(leaveAccount);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getMembers method, of class Party.
     */
    @Test
    public void testGetMembers()
    {
        System.out.println("getMembers");
        Party instance = null;
        ArrayList<Account> expResult = null;
        ArrayList<Account> result = instance.getMembers();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getOwner method, of class Party.
     */
    @Test
    public void testGetOwner()
    {
        System.out.println("getOwner");
        Party instance = null;
        Account expResult = null;
        Account result = instance.getOwner();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getChat method, of class Party.
     */
    @Test
    public void testGetChat()
    {
        System.out.println("getChat");
        Party instance = null;
        ArrayList<Message> expResult = null;
        ArrayList<Message> result = instance.getChat();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

}
