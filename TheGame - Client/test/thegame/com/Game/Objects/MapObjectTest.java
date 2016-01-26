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
import thegame.com.Game.Map;

/**
 *
 * @author laure
 */
public class MapObjectTest {
    
    /**
     *
     */
    public MapObjectTest()
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
        /*System.out.println("getSkin");
        MapObject instance = null;
        Image expResult = null;
        Image result = instance.getSkin();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");*/
    }

    /**
     * Test of setX method, of class MapObject.
     */
    @Test
    public void testSetX() {
        System.out.println("setX");
        float x = 0.0F;
        MapObject instance = new MapObjectImpl();
        instance.setX(x);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setY method, of class MapObject.
     */
    @Test
    public void testSetY() {
        System.out.println("setY");
        float y = 0.0F;
        MapObject instance = new MapObjectImpl();
        instance.setY(y);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setH method, of class MapObject.
     */
    @Test
    public void testSetH() {
        System.out.println("setH");
        float h = 0.0F;
        MapObject instance = new MapObjectImpl();
        instance.setH(h);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setW method, of class MapObject.
     */
    @Test
    public void testSetW() {
        System.out.println("setW");
        float w = 0.0F;
        MapObject instance = new MapObjectImpl();
        instance.setW(w);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setS method, of class MapObject.
     */
    @Test
    public void testSetS() {
        System.out.println("setS");
        float s = 0.0F;
        MapObject instance = new MapObjectImpl();
        instance.setS(s);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getSXDecay method, of class MapObject.
     */
    @Test
    public void testGetSXDecay() {
        System.out.println("getSXDecay");
        MapObject instance = new MapObjectImpl();
        float expResult = 0.0F;
        float result = instance.getSXDecay();
        assertEquals(expResult, result, 0.0);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getSXMax method, of class MapObject.
     */
    @Test
    public void testGetSXMax() {
        System.out.println("getSXMax");
        MapObject instance = new MapObjectImpl();
        float expResult = 0.0F;
        float result = instance.getSXMax();
        assertEquals(expResult, result, 0.0);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getSYMax method, of class MapObject.
     */
    @Test
    public void testGetSYMax() {
        System.out.println("getSYMax");
        MapObject instance = new MapObjectImpl();
        float expResult = 0.0F;
        float result = instance.getSYMax();
        assertEquals(expResult, result, 0.0);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getSXIncrease method, of class MapObject.
     */
    @Test
    public void testGetSXIncrease() {
        System.out.println("getSXIncrease");
        MapObject instance = new MapObjectImpl();
        float expResult = 0.0F;
        float result = instance.getSXIncrease();
        assertEquals(expResult, result, 0.0);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getSYIncrease method, of class MapObject.
     */
    @Test
    public void testGetSYIncrease() {
        System.out.println("getSYIncrease");
        MapObject instance = new MapObjectImpl();
        float expResult = 0.0F;
        float result = instance.getSYIncrease();
        assertEquals(expResult, result, 0.0);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getID method, of class MapObject.
     */
    @Test
    public void testGetID() {
        System.out.println("getID");
        MapObject instance = new MapObjectImpl();
        int expResult = 0;
        int result = instance.getID();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of equals method, of class MapObject.
     */
    @Test
    public void testEquals() {
        System.out.println("equals");
        Object o = null;
        MapObject instance = new MapObjectImpl();
        boolean expResult = false;
        boolean result = instance.equals(o);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of hashCode method, of class MapObject.
     */
    @Test
    public void testHashCode() {
        System.out.println("hashCode");
        MapObject instance = new MapObjectImpl();
        int expResult = 0;
        int result = instance.hashCode();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setMap method, of class MapObject.
     */
    @Test
    public void testSetMap() {
        System.out.println("setMap");
        Map set = null;
        MapObject instance = new MapObjectImpl();
        instance.setMap(set);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getMap method, of class MapObject.
     */
    @Test
    public void testGetMap() {
        System.out.println("getMap");
        MapObject instance = new MapObjectImpl();
        Map expResult = null;
        Map result = instance.getMap();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setSX method, of class MapObject.
     */
    @Test
    public void testSetSX() {
        System.out.println("setSX");
        float newSpeed = 0.0F;
        MapObject instance = new MapObjectImpl();
        instance.setSX(newSpeed);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setSY method, of class MapObject.
     */
    @Test
    public void testSetSY() {
        System.out.println("setSY");
        float newSpeed = 0.0F;
        MapObject instance = new MapObjectImpl();
        instance.setSY(newSpeed);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of createSkin method, of class MapObject.
     */
    @Test
    public void testCreateSkin() {
        System.out.println("createSkin");
        MapObject instance = new MapObjectImpl();
        instance.createSkin();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setType method, of class MapObject.
     */
    @Test
    public void testSetType() {
        System.out.println("setType");
        MapObject instance = new MapObjectImpl();
        instance.setType();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getType method, of class MapObject.
     */
    @Test
    public void testGetType() {
        System.out.println("getType");
        MapObject instance = new MapObjectImpl();
        ObjectType expResult = null;
        ObjectType result = instance.getType();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    public class MapObjectImpl extends MapObject {

        public void createSkin() {
        }

        public void update(MapObject update) {
        }

        public void setType() {
        }

        public ObjectType getType() {
            return null;
        }
    }

    /**
     *
     *
    public class MapObjectImpl extends MapObject {

        /**
         *
         *
        public MapObjectImpl()
        {
            super(null, 0.0F, 0.0F);
        }
    }*/
}
