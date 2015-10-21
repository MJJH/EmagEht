/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thegame.com.Game.Objects.Characters;

import java.util.Map;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import thegame.com.Game.Objects.Armor;
import thegame.com.Game.Objects.MapObject;
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
     * Test of addToBackpack method, of class CharacterGame.
     */
    @Test
    public void testAddToBackpack()
    {
        System.out.println("addToBackpack");
        MapObject object = null;
        CharacterGame instance = null;
        boolean expResult = false;
        boolean result = instance.addToBackpack(object);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of dropItem method, of class CharacterGame.
     */
    @Test
    public void testDropItem()
    {
        System.out.println("dropItem");
        MapObject object = null;
        CharacterGame instance = null;
        Map expResult = null;
        Map result = instance.dropItem(object);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of equipArmor method, of class CharacterGame.
     */
    @Test
    public void testEquipArmor()
    {
        System.out.println("equipArmor");
        Armor armorAdd = null;
        CharacterGame instance = null;
        instance.equipArmor(armorAdd);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of unequipArmor method, of class CharacterGame.
     */
    @Test
    public void testUnequipArmor()
    {
        System.out.println("unequipArmor");
        Armor armorDel = null;
        CharacterGame instance = null;
        instance.unequipArmor(armorDel);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of equipTool method, of class CharacterGame.
     */
    @Test
    public void testEquipTool()
    {
        System.out.println("equipTool");
        Tool toolAdd = null;
        CharacterGame instance = null;
        instance.equipTool(toolAdd);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of unequipTool method, of class CharacterGame.
     */
    @Test
    public void testUnequipTool()
    {
        System.out.println("unequipTool");
        CharacterGame instance = null;
        instance.unequipTool();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of updateHP method, of class CharacterGame.
     */
    @Test
    public void testUpdateHP()
    {
        System.out.println("updateHP");
        int change = 0;
        CharacterGame instance = null;
        int expResult = 0;
        int result = instance.updateHP(change);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getHP method, of class CharacterGame.
     */
    @Test
    public void testGetHP()
    {
        System.out.println("getHP");
        CharacterGame instance = null;
        int expResult = 0;
        int result = instance.getHP();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getName method, of class CharacterGame.
     */
    @Test
    public void testGetName()
    {
        System.out.println("getName");
        CharacterGame instance = null;
        String expResult = "";
        String result = instance.getName();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getSkills method, of class CharacterGame.
     */
    @Test
    public void testGetSkills()
    {
        System.out.println("getSkills");
        CharacterGame instance = null;
        Map<SkillType, Integer> expResult = null;
        Map<SkillType, Integer> result = instance.getSkills();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getSolid method, of class CharacterGame.
     */
    @Test
    public void testGetSolid()
    {
        System.out.println("getSolid");
        CharacterGame instance = null;
        float expResult = 0.0F;
        float result = instance.getSolid();
        assertEquals(expResult, result, 0.0);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getArmor method, of class CharacterGame.
     */
    @Test
    public void testGetArmor()
    {
        System.out.println("getArmor");
        CharacterGame instance = null;
        Map<String, Armor> expResult = null;
        Map<String, Armor> result = instance.getArmor();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getHolding method, of class CharacterGame.
     */
    @Test
    public void testGetHolding()
    {
        System.out.println("getHolding");
        CharacterGame instance = null;
        Tool expResult = null;
        Tool result = instance.getHolding();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getBackpackMap method, of class CharacterGame.
     */
    @Test
    public void testGetBackpackMap()
    {
        System.out.println("getBackpackMap");
        CharacterGame instance = null;
        Map<MapObject, Integer> expResult = null;
        Map<MapObject, Integer> result = instance.getBackpackMap();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
