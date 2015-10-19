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
import thegame.com.Game.Map;

/**
 *
 * @author laure
 */
public class LobbyTest {
    
    public LobbyTest()
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
     * Test of sendMessage method, of class Lobby.
     */
    @Test
    public void testSendMessage()
    {
        System.out.println("sendMessage");
        Account sender = null;
        String message = "";
        Lobby instance = new Lobby();
        boolean expResult = false;
        boolean result = instance.sendMessage(sender, message);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of joinLobby method, of class Lobby.
     */
    @Test
    public void testJoinLobby()
    {
        System.out.println("joinLobby");
        Account account = null;
        Lobby instance = new Lobby();
        boolean expResult = false;
        boolean result = instance.joinLobby(account);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of generateMap method, of class Lobby.
     */
    @Test
    public void testGenerateMap()
    {
        System.out.println("generateMap");
        Lobby instance = new Lobby();
        instance.generateMap();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getChat method, of class Lobby.
     */
    @Test
    public void testGetChat()
    {
        System.out.println("getChat");
        Lobby instance = new Lobby();
        ArrayList<Message> expResult = null;
        ArrayList<Message> result = instance.getChat();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getAccounts method, of class Lobby.
     */
    @Test
    public void testGetAccounts()
    {
        System.out.println("getAccounts");
        Lobby instance = new Lobby();
        ArrayList<Account> expResult = null;
        ArrayList<Account> result = instance.getAccounts();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getMap method, of class Lobby.
     */
    @Test
    public void testGetMap()
    {
        System.out.println("getMap");
        Lobby instance = new Lobby();
        Map expResult = null;
        Map result = instance.getMap();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getID method, of class Lobby.
     */
    @Test
    public void testGetID()
    {
        System.out.println("getID");
        Lobby instance = new Lobby();
        int expResult = 0;
        int result = instance.getID();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
