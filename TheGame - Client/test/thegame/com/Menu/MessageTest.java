/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thegame.com.Menu;

import java.util.Date;
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
public class MessageTest {
    
    /**
     *
     */
    public MessageTest()
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
     * Test of getText method, of class Message.
     */
    @Test
    public void testGetText()
    {
        System.out.println("getText");
        Account account = new Account("test");
        Message instance = new Message(account, "Test Message");
        String expResult = "Test Message";
        String result = instance.getText();
        assertEquals("The text doesn't match.", expResult, result);
    }

    /**
     * Test of getDate method, of class Message.
     */
    @Test
    public void testGetDate()
    {
        System.out.println("getDate");
        Account account = new Account("test");
        Message instance = new Message(account, "Test Message");
        Date expResult = new Date();
        Date result = instance.getDate();
        assertEquals("The date doesn't match.", expResult, result);
    }

    /**
     * Test of getSender method, of class Message.
     */
    @Test
    public void testGetSender()
    {
        System.out.println("getSender");
        Account account = new Account("test");
        Message instance = new Message(account, "Test Message");
        String expResult = account.getUsername();
        String result = instance.getSender().getUsername();
        assertEquals(expResult, result);
    }
    
}
