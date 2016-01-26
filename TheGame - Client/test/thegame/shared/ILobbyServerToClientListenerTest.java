/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thegame.shared;

import java.rmi.RemoteException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author robin
 */
public class ILobbyServerToClientListenerTest {
    
    public ILobbyServerToClientListenerTest() {
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
     * Test of requestConnectToGame method, of class ILobbyServerToClientListener.
     */
    @Test
    public void testRequestConnectToGame() throws Exception {
        System.out.println("requestConnectToGame");
        ILobbyServerToClientListener instance = new ILobbyServerToClientListenerImpl();
        instance.requestConnectToGame();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    public class ILobbyServerToClientListenerImpl implements ILobbyServerToClientListener {

        public void requestConnectToGame() throws RemoteException {
        }
    }
    
}
