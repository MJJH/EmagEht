/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thegame.com.Game.Objects;

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
public class LiquidTest {
    
    public LiquidTest()
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
     * Test of setVolume method, of class Liquid.
     */
    @Test
    public void testSetVolume()
    {
        System.out.println("setVolume");
        float volume = 0.0F;
        Liquid instance = null;
        instance.setVolume(volume);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of changeVolume method, of class Liquid.
     */
    @Test
    public void testChangeVolume()
    {
        System.out.println("changeVolume");
        float add = 0.0F;
        Liquid instance = null;
        instance.changeVolume(add);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getVolume method, of class Liquid.
     */
    @Test
    public void testGetVolume()
    {
        System.out.println("getVolume");
        Liquid instance = null;
        float expResult = 0.0F;
        float result = instance.getVolume();
        assertEquals(expResult, result, 0.0);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
