/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thegame.com.storage;

import java.sql.ResultSet;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import thegame.com.Menu.Account;

/**
 *
 * @author robin
 */
public class DatabaseTest {
    
    public DatabaseTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of getDatabase method, of class Database.
     */
    @Test
    public void testGetDatabase() {
        System.out.println("getDatabase");
        Database expResult = null;
        Database result = Database.getDatabase();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of openConnection method, of class Database.
     */
    @Test
    public void testOpenConnection() {
        System.out.println("openConnection");
        Database instance = new Database();
        instance.openConnection();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of executeUnsafeQuery method, of class Database.
     */
    @Test
    public void testExecuteUnsafeQuery() throws Exception {
        System.out.println("executeUnsafeQuery");
        String sql = "";
        Database instance = new Database();
        ResultSet expResult = null;
        ResultSet result = instance.executeUnsafeQuery(sql);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of checkCredentials method, of class Database.
     */
    @Test
    public void testCheckCredentials() {
        System.out.println("checkCredentials");
        String username = "";
        String password = "";
        Database instance = new Database();
        Account expResult = null;
        Account result = instance.checkCredentials(username, password);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of closeConnection method, of class Database.
     */
    @Test
    public void testCloseConnection() {
        System.out.println("closeConnection");
        Database instance = new Database();
        instance.closeConnection();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
