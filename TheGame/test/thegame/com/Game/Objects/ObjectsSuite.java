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
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 *
 * @author laure
 */
@RunWith(Suite.class)
@Suite.SuiteClasses(
{
    thegame.com.Game.Objects.ArmorTest.class, thegame.com.Game.Objects.ToolTest.class, thegame.com.Game.Objects.BlockTest.class, thegame.com.Game.Objects.MapObjectTest.class, thegame.com.Game.Objects.ToolTypeTest.class, thegame.com.Game.Objects.ParticleTest.class, thegame.com.Game.Objects.ArmorTypeTest.class, thegame.com.Game.Objects.Characters.CharactersSuite.class, thegame.com.Game.Objects.BlockTypeTest.class, thegame.com.Game.Objects.LiquidTest.class
})
public class ObjectsSuite {

    @BeforeClass
    public static void setUpClass() throws Exception
    {
    }

    @AfterClass
    public static void tearDownClass() throws Exception
    {
    }

    @Before
    public void setUp() throws Exception
    {
    }

    @After
    public void tearDown() throws Exception
    {
    }
    
}
