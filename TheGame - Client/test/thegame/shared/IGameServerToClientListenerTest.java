/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thegame.shared;

import java.rmi.RemoteException;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import thegame.com.Game.Objects.MapObject;
import thegame.com.Menu.Message;

/**
 *
 * @author robin
 */
public class IGameServerToClientListenerTest {
    
    public IGameServerToClientListenerTest() {
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
     * Test of sendGameChatMessage method, of class IGameServerToClientListener.
     */
    @Test
    public void testSendGameChatMessage() throws Exception {
        System.out.println("sendGameChatMessage");
        Message message = null;
        IGameServerToClientListener instance = new IGameServerToClientListenerImpl();
        instance.sendGameChatMessage(message);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of knockBackPlayer method, of class IGameServerToClientListener.
     */
    @Test
    public void testKnockBackPlayer() throws Exception {
        System.out.println("knockBackPlayer");
        float hSpeed = 0.0F;
        float vSpeed = 0.0F;
        IGameServerToClientListener instance = new IGameServerToClientListenerImpl();
        instance.knockBackPlayer(hSpeed, vSpeed);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of removeMapObject method, of class IGameServerToClientListener.
     */
    @Test
    public void testRemoveMapObject() throws Exception {
        System.out.println("removeMapObject");
        int id = 0;
        int type = 0;
        float x = 0.0F;
        float y = 0.0F;
        IGameServerToClientListener instance = new IGameServerToClientListenerImpl();
        instance.removeMapObject(id, type, x, y);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of addMapObject method, of class IGameServerToClientListener.
     */
    @Test
    public void testAddMapObject() throws Exception {
        System.out.println("addMapObject");
        MapObject mo = null;
        IGameServerToClientListener instance = new IGameServerToClientListenerImpl();
        instance.addMapObject(mo);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of updateObjects method, of class IGameServerToClientListener.
     */
    @Test
    public void testUpdateObjects() throws Exception {
        System.out.println("updateObjects");
        List<MapObject> toSend = null;
        IGameServerToClientListener instance = new IGameServerToClientListenerImpl();
        instance.updateObjects(toSend);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of addToBackpack method, of class IGameServerToClientListener.
     */
    @Test
    public void testAddToBackpack() throws Exception {
        System.out.println("addToBackpack");
        MapObject object = null;
        int spot = 0;
        IGameServerToClientListener instance = new IGameServerToClientListenerImpl();
        instance.addToBackpack(object, spot);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of addToEmptyBackpack method, of class IGameServerToClientListener.
     */
    @Test
    public void testAddToEmptyBackpack() throws Exception {
        System.out.println("addToEmptyBackpack");
        MapObject object = null;
        IGameServerToClientListener instance = new IGameServerToClientListenerImpl();
        instance.addToEmptyBackpack(object);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setTeamLifes method, of class IGameServerToClientListener.
     */
    @Test
    public void testSetTeamLifes() throws Exception {
        System.out.println("setTeamLifes");
        int lifes = 0;
        IGameServerToClientListener instance = new IGameServerToClientListenerImpl();
        instance.setTeamLifes(lifes);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of respawnMe method, of class IGameServerToClientListener.
     */
    @Test
    public void testRespawnMe() throws Exception {
        System.out.println("respawnMe");
        IGameServerToClientListener instance = new IGameServerToClientListenerImpl();
        instance.respawnMe();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of stopGame method, of class IGameServerToClientListener.
     */
    @Test
    public void testStopGame() throws Exception {
        System.out.println("stopGame");
        IGameServerToClientListener instance = new IGameServerToClientListenerImpl();
        instance.stopGame();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    public class IGameServerToClientListenerImpl implements IGameServerToClientListener {

        public void sendGameChatMessage(Message message) throws RemoteException {
        }

        public void knockBackPlayer(float hSpeed, float vSpeed) throws RemoteException {
        }

        public void removeMapObject(int id, int type, float x, float y) throws RemoteException {
        }

        public void addMapObject(MapObject mo) throws RemoteException {
        }

        public void updateObjects(List<MapObject> toSend) throws RemoteException {
        }

        public void addToBackpack(MapObject object, int spot) throws RemoteException {
        }

        public void addToEmptyBackpack(MapObject object) throws RemoteException {
        }

        public void setTeamLifes(int lifes) throws RemoteException {
        }

        public void respawnMe() throws RemoteException {
        }

        public void stopGame() throws RemoteException {
        }
    }
    
}
