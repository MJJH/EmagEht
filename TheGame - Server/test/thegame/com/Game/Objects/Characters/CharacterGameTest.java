/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thegame.com.Game.Objects.Characters;

import java.util.EnumMap;
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
        //instance.equipTool(toolAdd);
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
    }

    /**
     * Test of updateHP method, of class CharacterGame.
     */
    @Test
    public void testUpdateHP()
    {
        /*System.out.println("updateHP");
        int change = 40;
        CharacterGame instance = new CharacterGame("John", 100, null, 1, 1, null, 1, 1);
        int expResult = 40;
        int result = instance.updateHP(change);
        assertEquals(expResult, result);*/
    }

    /**
     * Test of getHP method, of class CharacterGame.
     */
    @Test
    public void testGetHP()
    {
        /*System.out.println("getHP");
        CharacterGame instance = new CharacterGame("John", 100, null, 1, 1, null, 1, 1);
        int expResult = 100;
        int result = instance.getHP();
        assertEquals(expResult, result);*/
    }

    /**
     * Test of getName method, of class CharacterGame.
     */
    @Test
    public void testGetName()
    {
        /*System.out.println("getName");
        CharacterGame instance = new CharacterGame("John", 100, null, 1, 1, null, 1, 1);
        String expResult = "John";
        String result = instance.getName();
        assertEquals(expResult, result);*/
    }

    /**
     * Test of getSkills method, of class CharacterGame.
     */
    @Test
    public void testGetSkills()
    {
        /*System.out.println("getSkills");
        
        CharacterGame instance = new CharacterGame("John", 100, null, 1, 1, null, 1, 1);
        
        Map<SkillType, Integer> expResult = new EnumMap<SkillType, Integer>(SkillType.class);
        Map<SkillType, Integer> result = instance.getSkills();
        
        assertEquals(expResult, result);*/
    }

    /**
     * Test of getSolid method, of class CharacterGame.
     */
    @Test
    public void testGetSolid()
    {
        
        /*System.out.println("getSolid");
        CharacterGame instance = new CharacterGame("John", 100, null, 1, 1, null, 1, 1);
        float expResult = 0.0F;
        float result = instance.getSolid();
        assertEquals(expResult, result, 0.0);*/
    }

    /**
     * Test of getArmor method, of class CharacterGame.
     */
    @Test
    public void testGetArmor()
    {
        /*System.out.println("getArmor");
        CharacterGame instance = new CharacterGame("John", 100, null, 1, 1, null, 1, 1);
        Map<String, Armor> expResult = null;
        Map<String, Armor> result = instance.getArmor();
        assertEquals(expResult, result);*/
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
    }
    
}
