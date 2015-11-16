/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thegame.com.Game.Objects.Characters;

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
public class CharacterTest {
    
    /**
     *
     */
    public CharacterTest()
    {
        Character character = new Character("Piet", 2000);
        assertEquals(character.getXp(), 2000);
        assertEquals(character.getName(), "Piet");
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
     * Test of levelUp method, of class Character.
     */
    @Test
    public void testLevelUp()
    {
        System.out.println("levelUp");
        String skilltype = "Attack";
        Character instance = new Character("Piet", 2000);
        instance.levelUp(skilltype);
    }

    /**
     * Test of getXp method, of class Character.
     */
    @Test
    public void testGetXp()
    {
        System.out.println("getXp");
        Character instance = new Character("Bob", 10000);
        int expResult = 10000;
        int result = instance.getXp();
        assertEquals(expResult, result);
    }

    /**
     * Test of getName method, of class Character.
     */
    @Test
    public void testGetName() {
        System.out.println("getName");
        Character instance = new Character("Bob", 10000);
        String expResult = "Bob";
        String result = instance.getName();
        assertEquals(expResult, result);
    }
    
}
