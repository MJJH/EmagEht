/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thegame.com.Game.Objects;

import javafx.scene.image.Image;
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
public class MapObjectTest {
    
    public MapObjectTest()
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
     * Test of update method, of class MapObject.
     */
    @Test
    public void testUpdate()
    {
        System.out.println("update");
        MapObject instance = null;
        instance.update();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getX method, of class MapObject.
     */
    @Test
    public void testGetX()
    {
        System.out.println("getX");
        MapObject instance = null;
        float expResult = 0.0F;
        float result = instance.getX();
        assertEquals(expResult, result, 0.0);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getY method, of class MapObject.
     */
    @Test
    public void testGetY()
    {
        System.out.println("getY");
        MapObject instance = null;
        float expResult = 0.0F;
        float result = instance.getY();
        assertEquals(expResult, result, 0.0);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getH method, of class MapObject.
     */
    @Test
    public void testGetH()
    {
        System.out.println("getH");
        MapObject instance = null;
        float expResult = 0.0F;
        float result = instance.getH();
        assertEquals(expResult, result, 0.0);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getW method, of class MapObject.
     */
    @Test
    public void testGetW()
    {
        System.out.println("getW");
        MapObject instance = null;
        float expResult = 0.0F;
        float result = instance.getW();
        assertEquals(expResult, result, 0.0);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getS method, of class MapObject.
     */
    @Test
    public void testGetS()
    {
        System.out.println("getS");
        MapObject instance = null;
        float expResult = 0.0F;
        float result = instance.getS();
        assertEquals(expResult, result, 0.0);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getSX method, of class MapObject.
     */
    @Test
    public void testGetSX()
    {
        System.out.println("getSX");
        MapObject instance = null;
        float expResult = 0.0F;
        float result = instance.getSX();
        assertEquals(expResult, result, 0.0);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getSY method, of class MapObject.
     */
    @Test
    public void testGetSY()
    {
        System.out.println("getSY");
        MapObject instance = null;
        float expResult = 0.0F;
        float result = instance.getSY();
        assertEquals(expResult, result, 0.0);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getSkin method, of class MapObject.
     */
    @Test
    public void testGetSkin()
    {
        System.out.println("getSkin");
        MapObject instance = null;
        Image expResult = null;
        Image result = instance.getSkin();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    public class MapObjectImpl extends MapObject {

        public MapObjectImpl()
        {
            super(null, 0.0F, 0.0F);
        }
    }

    public class MapObjectImpl extends MapObject {

        public MapObjectImpl()
        {
            super(null, 0.0F, 0.0F);
        }
    }

    public class MapObjectImpl extends MapObject {

        public MapObjectImpl()
        {
            super(null, 0.0F, 0.0F);
        }
    }
    
}
