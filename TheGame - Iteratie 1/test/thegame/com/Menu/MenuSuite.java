/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thegame.com.Menu;

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
    thegame.com.Menu.SettingsTest.class, thegame.com.Menu.AccountTest.class, thegame.com.Menu.PartyInviteTest.class, thegame.com.Menu.MessageTest.class, thegame.com.Menu.LobbyTest.class, thegame.com.Menu.PartyTest.class, thegame.com.Menu.FriendRequestTest.class
})
public class MenuSuite {

    /**
     *
     * @throws Exception
     */
    @BeforeClass
    public static void setUpClass() throws Exception
    {
    }

    /**
     *
     * @throws Exception
     */
    @AfterClass
    public static void tearDownClass() throws Exception
    {
    }

    /**
     *
     * @throws Exception
     */
    @Before
    public void setUp() throws Exception
    {
    }

    /**
     *
     * @throws Exception
     */
    @After
    public void tearDown() throws Exception
    {
    }
    
}
