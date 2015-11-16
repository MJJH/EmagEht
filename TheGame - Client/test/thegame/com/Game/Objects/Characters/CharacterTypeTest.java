/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thegame.com.Game.Objects.Characters;

import java.util.List;
import java.util.Map;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import thegame.com.Game.Objects.MapObject;

/**
 *
 * @author laure
 */
public class CharacterTypeTest {
    
    /**
     *
     */
    public CharacterTypeTest()
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
     *
     */
    @Test
    public void testSomeMethod()
    {
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getName method, of class CharacterType.
     */
    @Test
    public void testGetName() {
        System.out.println("getName");
        CharacterType instance = new CharacterType("Zombie", null, null);
        String expResult = "Zombie";
        String result = instance.getName();
        assertEquals(expResult, result);
    }

    /**
     * Test of getStartItems method, of class CharacterType.
     */
    @Test
    public void testGetStartItems() {
        System.out.println("getStartItems");
        CharacterType instance = null;
        List<MapObject> expResult = null;
        List<MapObject> result = instance.getStartItems();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getStartSkills method, of class CharacterType.
     */
    @Test
    public void testGetStartSkills() {
        System.out.println("getStartSkills");
        CharacterType instance = null;
        Map<SkillType, Integer> expResult = null;
        Map<SkillType, Integer> result = instance.getStartSkills();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
