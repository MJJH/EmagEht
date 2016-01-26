/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thegame;

import javafx.stage.Stage;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import thegame.com.Game.Objects.ObjectType;
import thegame.com.storage.Database;

/**
 *
 * @author robin
 */
public class StartupTest {
    
    public StartupTest() {
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
     * Test of main method, of class Startup.
     */
    @Test
    public void testMain() {
        System.out.println("main");
        String[] args = null;
        Startup.main(args);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of start method, of class Startup.
     */
    @Test
    public void testStart() throws Exception {
        System.out.println("start");
        Stage primaryStage = null;
        Startup instance = new Startup();
        instance.start(primaryStage);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getType method, of class Startup.
     */
    @Test
    public void testGetType() throws Exception {
        System.out.println("getType");
        Database db = null;
        String type = "";
        int id = 0;
        Startup instance = new Startup();
        ObjectType expResult = null;
        ObjectType result = instance.getType(db, type, id);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
