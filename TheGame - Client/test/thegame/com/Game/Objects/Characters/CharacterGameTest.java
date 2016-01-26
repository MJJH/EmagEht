/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thegame.com.Game.Objects.Characters;

import display.Skin;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import thegame.com.Game.Objects.Armor;
import thegame.com.Game.Objects.ArmorType;
import thegame.com.Game.Objects.MapObject;
import thegame.com.Game.Objects.ObjectType;
import thegame.com.Game.Objects.Tool;

/**
 *
 * @author laure
 */
public class CharacterGameTest {
    
    /**
     *
     */
    public CharacterGameTest()
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
     * Test of walkRight method, of class CharacterGame.
     */
    @Test
    public void testWalkRight() {
        System.out.println("walkRight");
        CharacterGame instance = new CharacterGameImpl();
        instance.walkRight();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of walkLeft method, of class CharacterGame.
     */
    @Test
    public void testWalkLeft() {
        System.out.println("walkLeft");
        CharacterGame instance = new CharacterGameImpl();
        instance.walkLeft();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of jump method, of class CharacterGame.
     */
    @Test
    public void testJump() {
        System.out.println("jump");
        CharacterGame instance = new CharacterGameImpl();
        instance.jump();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of stopJump method, of class CharacterGame.
     */
    @Test
    public void testStopJump() {
        System.out.println("stopJump");
        CharacterGame instance = new CharacterGameImpl();
        instance.stopJump();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of addToBackpack method, of class CharacterGame.
     */
    @Test
    public void testAddToBackpack_MapObject() {
        System.out.println("addToBackpack");
        MapObject object = null;
        CharacterGame instance = new CharacterGameImpl();
        boolean expResult = false;
        boolean result = instance.addToBackpack(object);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of removeFromBackpack method, of class CharacterGame.
     */
    @Test
    public void testRemoveFromBackpack_ObjectType_int() {
        System.out.println("removeFromBackpack");
        ObjectType ot = null;
        int amount = 0;
        CharacterGame instance = new CharacterGameImpl();
        List<MapObject> expResult = null;
        List<MapObject> result = instance.removeFromBackpack(ot, amount);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of removeFromBackpack method, of class CharacterGame.
     */
    @Test
    public void testRemoveFromBackpack_int_int() {
        System.out.println("removeFromBackpack");
        int spot = 0;
        int amount = 0;
        CharacterGame instance = new CharacterGameImpl();
        List<MapObject> expResult = null;
        List<MapObject> result = instance.removeFromBackpack(spot, amount);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of removeFromBackpack method, of class CharacterGame.
     */
    @Test
    public void testRemoveFromBackpack_int() {
        System.out.println("removeFromBackpack");
        int spot = 0;
        CharacterGame instance = new CharacterGameImpl();
        List<MapObject> expResult = null;
        List<MapObject> result = instance.removeFromBackpack(spot);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getMaxHP method, of class CharacterGame.
     */
    @Test
    public void testGetMaxHP() {
        System.out.println("getMaxHP");
        CharacterGame instance = new CharacterGameImpl();
        int expResult = 0;
        int result = instance.getMaxHP();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setArmor method, of class CharacterGame.
     */
    @Test
    public void testSetArmor() {
        System.out.println("setArmor");
        Map<ArmorType.bodyPart, Armor> armor = null;
        CharacterGame instance = new CharacterGameImpl();
        instance.setArmor(armor);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getDirection method, of class CharacterGame.
     */
    @Test
    public void testGetDirection() {
        System.out.println("getDirection");
        CharacterGame instance = new CharacterGameImpl();
        MapObject.sides expResult = null;
        MapObject.sides result = instance.getDirection();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setDirection method, of class CharacterGame.
     */
    @Test
    public void testSetDirection() {
        System.out.println("setDirection");
        MapObject.sides direction = null;
        CharacterGame instance = new CharacterGameImpl();
        instance.setDirection(direction);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of addToBackpack method, of class CharacterGame.
     */
    @Test
    public void testAddToBackpack_MapObject_int() {
        System.out.println("addToBackpack");
        MapObject object = null;
        int spot = 0;
        CharacterGame instance = new CharacterGameImpl();
        boolean expResult = false;
        boolean result = instance.addToBackpack(object, spot);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of addToEmptyBackpack method, of class CharacterGame.
     */
    @Test
    public void testAddToEmptyBackpack() {
        System.out.println("addToEmptyBackpack");
        MapObject object = null;
        CharacterGame instance = new CharacterGameImpl();
        boolean expResult = false;
        boolean result = instance.addToEmptyBackpack(object);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of interactWithBackpack method, of class CharacterGame.
     */
    @Test
    public void testInteractWithBackpack() {
        System.out.println("interactWithBackpack");
        int spot = 0;
        CharacterGame instance = new CharacterGameImpl();
        instance.interactWithBackpack(spot);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getSkin method, of class CharacterGame.
     */
    @Test
    public void testGetSkin() {
        System.out.println("getSkin");
        CharacterGame instance = new CharacterGameImpl();
        Skin expResult = null;
        Skin result = instance.getSkin();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of createSkin method, of class CharacterGame.
     */
    @Test
    public void testCreateSkin() {
        System.out.println("createSkin");
        CharacterGame instance = new CharacterGameImpl();
        instance.createSkin();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setType method, of class CharacterGame.
     */
    @Test
    public void testSetType() {
        System.out.println("setType");
        CharacterGame instance = new CharacterGameImpl();
        instance.setType();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getType method, of class CharacterGame.
     */
    @Test
    public void testGetType() {
        System.out.println("getType");
        CharacterGame instance = new CharacterGameImpl();
        ObjectType expResult = null;
        ObjectType result = instance.getType();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setHolding method, of class CharacterGame.
     */
    @Test
    public void testSetHolding() {
        System.out.println("setHolding");
        List<MapObject> holding = null;
        CharacterGame instance = new CharacterGameImpl();
        instance.setHolding(holding);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    public class CharacterGameImpl extends CharacterGame {

        @Override
        public void update(MapObject update) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
    }
    
}
