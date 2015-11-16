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
        Account sender = new Account("Henk");
        Account recepient = new Account("Frank");
        Party party = new Party(sender);
        PartyInvite instance = new PartyInvite(recepient, party, sender);
        Date expResult = new Date();
        Date result = instance.getDate();
        assertEquals("The datetime is not correct.", expResult, result);
    }

    /**
     * Test of getParty method, of class PartyInvite.
     */
    @Test
    public void testGetParty()
    {
        System.out.println("getParty");
        Account sender = new Account("Henk");
        Account recepient = new Account("Frank");
        Party party = new Party(sender);
        PartyInvite instance = new PartyInvite(recepient, party, sender);
        Party expResult = party;
        Party result = instance.getParty();
        assertEquals("The party was not saved properly in the invite.", expResult, result);
    }

    /**
     * Test of getAccount method, of class PartyInvite.
     */
    @Test
    public void testGetAccount()
    {
        System.out.println("getAccount");
        Account sender = new Account("Henk");
        Account recepient = new Account("Frank");
        Party party = new Party(sender);
        PartyInvite instance = new PartyInvite(recepient, party, sender);
        Account expResult = recepient;
        Account result = instance.getAccount();
        assertEquals("The recepient account was not saved properly in the invite.", expResult, result);
    }

    /**
     * Test of getSender method, of class PartyInvite.
     */
    @Test
    public void testGetSender()
    {
        System.out.println("getSender");
        Account sender = new Account("Henk");
        Account recepient = new Account("Frank");
        Party party = new Party(sender);
        PartyInvite instance = new PartyInvite(recepient, party, sender);
        Account expResult = null;
        Account result = instance.getSender();
        assertEquals("The ender account was not saved properly in the invite.", expResult, result);
    }
    
}
