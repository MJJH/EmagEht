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

/**
 *
 * @author laure
 */
public class Startup {
    
    /**
     *
     */
    public Startup()
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
     * Test of start method, of class Startup.
     */
    @Test
    public void testStart()
    {
        System.out.println("start");
        Stage primaryStage = null;
        Startup instance = new Startup();
        instance.start(primaryStage);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of main method, of class Startup.
     */
    @Test
    public void testMain()
    {
        System.out.println("main");
        String[] args = null;
        Startup.main(args);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
