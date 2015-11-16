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
    
    /**
     *
     */
    public LobbyTest()
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
     * Test of sendMessage method, of class Lobby.
     * Normal
     */
    @Test
    public void testSendMessage1()
    {
        System.out.println("sendMessage");
        Account sender = new Account("test");
        String message = "Test";
        Lobby instance = new Lobby();
        boolean expResult = true;
        boolean result = instance.sendMessage(sender, message);
        assertEquals("The message wasn't added.", expResult, result);
    }
    
    /**
     * Test of sendMessage method, of class Lobby.
     * Empty
     */
    @Test
    public void testSendMessage2()
    {
        System.out.println("sendMessage");
        Account sender = new Account("test");
        String message = "";
        Lobby instance = new Lobby();
        boolean expResult = true;
        boolean result = instance.sendMessage(sender, message);
        assertEquals("The message wasn't added.", expResult, result);
    }

    /**
     * Test of joinLobby method, of class Lobby.
     * 0 players joined.
     */
    @Test
    public void testJoinLobby1()
    {
        System.out.println("joinLobby");
        Account account = new Account("test");
        Lobby instance = new Lobby();
        boolean expResult = true;
        boolean result = instance.joinLobby(account);
        assertEquals("The first player can't join.", expResult, result);
    }
    
    /**
     * Test of joinLobby method, of class Lobby.
     * 2 players joined.
     */
    @Test
    public void testJoinLobby2()
    {
        System.out.println("joinLobby");
        Account account1 = new Account("test1");
        Account account2 = new Account("test2");
        Account account3 = new Account("test3");
        Lobby instance = new Lobby();
        instance.joinLobby(account1); 
        instance.joinLobby(account2);
        boolean expResult = true;
        boolean result = instance.joinLobby(account3);
        assertEquals("The player could not join a lobby that is half full.", expResult, result);
    }
    
    /**
     * Test of joinLobby method, of class Lobby.
     * 4 players joined.
     */
    @Test
    public void testJoinLobby3()
    {
        System.out.println("joinLobby");
        Account account1 = new Account("test1");
        Account account2 = new Account("test2");
        Account account3 = new Account("test3");
        Account account4 = new Account("test4");
        Account account5 = new Account("test5");
        Lobby instance = new Lobby();
        instance.joinLobby(account1);
        instance.joinLobby(account2);
        instance.joinLobby(account3);
        instance.joinLobby(account4);
        boolean expResult = false;
        boolean result = instance.joinLobby(account5);
        assertEquals("The player could join a full lobby.", expResult, result);
    }
    
    /**
     * Test of joinLobby method, of class Lobby.
     * Already joined.
     */
    @Test
    public void testJoinLobby4()
    {
        System.out.println("joinLobby");
        Account account1 = new Account("test1");
        Lobby instance = new Lobby();
        instance.joinLobby(account1); 
        boolean expResult = false;
        boolean result = instance.joinLobby(account1);
        assertEquals("The player could join a lobby that he already joined.", expResult, result);
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
    }

    /**
     * Test of getChat method, of class Lobby.
     */
    @Test
    public void testGetChat()
    {
        System.out.println("getChat");
        Lobby instance = new Lobby();
        Account henk = new Account("Henk");
        instance.sendMessage(henk, "Test");
        ArrayList<Message> expResult = new ArrayList<>();
        expResult.add(new Message(henk, "Test"));
        ArrayList<Message> result = instance.getChat();
        Message message1 = expResult.get(0);
        Message message2 = result.get(0);
        assertEquals("The chat wasn't acquired.", message1.getText(), message2.getText());
    }

    /**
     * Test of getAccounts method, of class Lobby.
     */
    @Test
    public void testGetAccounts()
    {
        System.out.println("getAccounts");
        Account account1 = new Account("test1");
        Lobby instance = new Lobby();
        instance.joinLobby(account1);
        ArrayList<Account> expResult = new ArrayList<>();
        expResult.add(account1);
        ArrayList<Account> result = instance.getAccounts();
        assertEquals("The accounts weren't acquired.", expResult.get(0).getUsername(), result.get(0).getUsername());
    }

    /**
     * Test of getMap method, of class Lobby.
     */
    @Test
    public void testGetMap()
    {
        System.out.println("getMap");
        Lobby instance = new Lobby();
        instance.generateMap();
        Class expResult = new Map().getClass();
        Class result = instance.getMap().getClass();
        assertEquals("A map wasn't generated.", expResult, result);
    }

    /**
     * Test of getID method, of class Lobby.
     * NOTE: This test has no immediate function.
     */
    @Test
    public void testGetID()
    {
        System.out.println("getID");
        Lobby instance = new Lobby();
        int expResult = 0;
        int result = instance.getID();
        assertEquals("The ID isn't correct.", expResult, result);
    }
    
}
