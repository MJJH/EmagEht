/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thegame.com.Game;

import gui.pages.GameFX;
import gui.pages.LobbyFX;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import thegame.com.Game.Objects.Block;
import thegame.com.Game.Objects.Characters.Enemy;
import thegame.com.Game.Objects.Characters.Player;
import thegame.com.Game.Objects.MapObject;
import thegame.com.Menu.Account;
import thegame.com.Menu.Lobby;
import thegame.com.Menu.Message;
import thegame.shared.IGameClientToServer;

/**
 *
 * @author robin
 */
public class MapTest {
    
    public MapTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of loadAfterRecieve method, of class Map.
     */
    @Test
    public void testLoadAfterRecieve() {
        System.out.println("loadAfterRecieve");
        IGameClientToServer gameClientToServer = null;
        Account myAccount = null;
        Player me = null;
        LobbyFX lobbyFX = null;
        Map instance = new Map();
        instance.loadAfterRecieve(gameClientToServer, myAccount, me, lobbyFX);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getBlock method, of class Map.
     */
    @Test
    public void testGetBlock() {
        System.out.println("getBlock");
        int y = 0;
        int x = 0;
        Map instance = new Map();
        Block expResult = null;
        Block result = instance.getBlock(y, x);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getPlayers method, of class Map.
     */
    @Test
    public void testGetPlayers() {
        System.out.println("getPlayers");
        Map instance = new Map();
        List<Player> expResult = null;
        List<Player> result = instance.getPlayers();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getEnemies method, of class Map.
     */
    @Test
    public void testGetEnemies() {
        System.out.println("getEnemies");
        Map instance = new Map();
        List<Enemy> expResult = null;
        List<Enemy> result = instance.getEnemies();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of NextLevel method, of class Map.
     */
    @Test
    public void testNextLevel() {
        System.out.println("NextLevel");
        Map instance = new Map();
        instance.NextLevel();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getSpawnX method, of class Map.
     */
    @Test
    public void testGetSpawnX() {
        System.out.println("getSpawnX");
        Map instance = new Map();
        float expResult = 0.0F;
        float result = instance.getSpawnX();
        assertEquals(expResult, result, 0.0);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getSpawnY method, of class Map.
     */
    @Test
    public void testGetSpawnY() {
        System.out.println("getSpawnY");
        Map instance = new Map();
        float expResult = 0.0F;
        float result = instance.getSpawnY();
        assertEquals(expResult, result, 0.0);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getWidth method, of class Map.
     */
    @Test
    public void testGetWidth() {
        System.out.println("getWidth");
        Map instance = new Map();
        int expResult = 0;
        int result = instance.getWidth();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getHeight method, of class Map.
     */
    @Test
    public void testGetHeight() {
        System.out.println("getHeight");
        Map instance = new Map();
        int expResult = 0;
        int result = instance.getHeight();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getAccount method, of class Map.
     */
    @Test
    public void testGetAccount() {
        System.out.println("getAccount");
        Map instance = new Map();
        Account expResult = null;
        Account result = instance.getAccount();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getMe method, of class Map.
     */
    @Test
    public void testGetMe() {
        System.out.println("getMe");
        Map instance = new Map();
        Player expResult = null;
        Player result = instance.getMe();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of addMapObject method, of class Map.
     */
    @Test
    public void testAddMapObject() {
        System.out.println("addMapObject");
        MapObject mo = null;
        Map instance = new Map();
        instance.addMapObject(mo);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of removeMapObject method, of class Map.
     */
    @Test
    public void testRemoveMapObject() {
        System.out.println("removeMapObject");
        int type = 0;
        int id = 0;
        int x = 0;
        int y = 0;
        Map instance = new Map();
        instance.removeMapObject(type, id, x, y);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of updateMapObject method, of class Map.
     */
    @Test
    public void testUpdateMapObject() {
        System.out.println("updateMapObject");
        MapObject update = null;
        Map instance = new Map();
        instance.updateMapObject(update);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getBlocksAndObjects method, of class Map.
     */
    @Test
    public void testGetBlocksAndObjects() {
        System.out.println("getBlocksAndObjects");
        int startX = 0;
        int startY = 0;
        int endX = 0;
        int endY = 0;
        Map instance = new Map();
        List<MapObject> expResult = null;
        List<MapObject> result = instance.getBlocksAndObjects(startX, startY, endX, endY);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of update method, of class Map.
     */
    @Test
    public void testUpdate() {
        System.out.println("update");
        Map instance = new Map();
        instance.update();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getLifes method, of class Map.
     */
    @Test
    public void testGetLifes() {
        System.out.println("getLifes");
        Map instance = new Map();
        int expResult = 0;
        int result = instance.getLifes();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of decreaseLife method, of class Map.
     */
    @Test
    public void testDecreaseLife() {
        System.out.println("decreaseLife");
        Map instance = new Map();
        instance.decreaseLife();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of increaseLife method, of class Map.
     */
    @Test
    public void testIncreaseLife() {
        System.out.println("increaseLife");
        Map instance = new Map();
        instance.increaseLife();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of addChatMessage method, of class Map.
     */
    @Test
    public void testAddChatMessage() {
        System.out.println("addChatMessage");
        Message message = null;
        Map instance = new Map();
        instance.addChatMessage(message);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getChatMessages method, of class Map.
     */
    @Test
    public void testGetChatMessages() {
        System.out.println("getChatMessages");
        Map instance = new Map();
        List expResult = null;
        List result = instance.getChatMessages();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getGameClientToServer method, of class Map.
     */
    @Test
    public void testGetGameClientToServer() {
        System.out.println("getGameClientToServer");
        Map instance = new Map();
        IGameClientToServer expResult = null;
        IGameClientToServer result = instance.getGameClientToServer();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setLifes method, of class Map.
     */
    @Test
    public void testSetLifes() {
        System.out.println("setLifes");
        int lifes = 0;
        Map instance = new Map();
        instance.setLifes(lifes);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getGravity method, of class Map.
     */
    @Test
    public void testGetGravity() {
        System.out.println("getGravity");
        Map instance = new Map();
        float expResult = 0.0F;
        float result = instance.getGravity();
        assertEquals(expResult, result, 0.0);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getLobbyFX method, of class Map.
     */
    @Test
    public void testGetLobbyFX() {
        System.out.println("getLobbyFX");
        Map instance = new Map();
        LobbyFX expResult = null;
        LobbyFX result = instance.getLobbyFX();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setGameFX method, of class Map.
     */
    @Test
    public void testSetGameFX() {
        System.out.println("setGameFX");
        GameFX gameFX = null;
        Map instance = new Map();
        instance.setGameFX(gameFX);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getGameFX method, of class Map.
     */
    @Test
    public void testGetGameFX() {
        System.out.println("getGameFX");
        Map instance = new Map();
        GameFX expResult = null;
        GameFX result = instance.getGameFX();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getLobby method, of class Map.
     */
    @Test
    public void testGetLobby() {
        System.out.println("getLobby");
        Map instance = new Map();
        Lobby expResult = null;
        Lobby result = instance.getLobby();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of GetTile method, of class Map.
     */
    @Test
    public void testGetTile() {
        System.out.println("GetTile");
        float x = 0.0F;
        float y = 0.0F;
        MapObject self = null;
        Map instance = new Map();
        MapObject expResult = null;
        MapObject result = instance.GetTile(x, y, self);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
