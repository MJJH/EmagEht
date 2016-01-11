/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thegame;

import gui.pages.MenuFX;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.application.Application;
import javafx.stage.Stage;
import thegame.com.Game.Objects.ArmorType;
import thegame.com.Game.Objects.BlockType;
import thegame.com.Game.Objects.ItemType;
import thegame.com.Game.Objects.ToolType;
import thegame.com.storage.Database;

/**
 *
 * @author laurens
 */
public class Startup extends Application {

    private Stage primaryStage;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException
    {
        this.primaryStage = primaryStage;
        /*
         try {
         loadDatabase();
         } catch (SQLException | ClassNotFoundException ex) {
         System.exit(0);
         }
         */
        
        new MenuFX(primaryStage);
        
        primaryStage.setOnCloseRequest(event ->
        {
            System.exit(0);
        });
    }

    private void loadDatabase() throws SQLException, ClassNotFoundException
    {
        Database db = Database.getDatabase();
        ResultSet rs;
        // Blocks
        String blockQuery = "SELECT * FROM Resource";
        rs = db.executeQuery(blockQuery);
        while (rs.next())
        {
            new BlockType(rs.getString("Name"), rs.getInt("Strength"), rs.getInt("ToolLevel"), ToolType.toolType.valueOf(rs.getString("ToolType")), (float) rs.getDouble("Solid"), null, null);
        }

        // Armor
        String armorQuery = "SELECT * FROM Armor";
        rs = db.executeQuery(armorQuery);
        while (rs.next())
        {
            new ArmorType(rs.getString("Name"), rs.getInt("DIA"), rs.getInt("RequiredLvl"), ArmorType.bodyPart.valueOf(rs.getString("ArmorTypeID")));
        }

        // Tool
        String toolQuery = "SELECT * FROM Tool";
        rs = db.executeQuery(toolQuery);
        while (rs.next())
        {
            new ToolType(rs.getString("Name"), rs.getInt("Strength"), rs.getInt("Speed"), rs.getInt("Radius"), rs.getInt("reqLvl"), ToolType.toolType.valueOf(rs.getString("Type")), rs.getDouble("KnockBack"));
        }

        // Item
        String itemQuery = "SELECT * FROM Item";
        rs = db.executeQuery(toolQuery);
        while (rs.next())
        {
            new ItemType(rs.getString("Name"), rs.getInt("Width"), rs.getInt("Height"));
        }

        // Crafting
        /*String craftQuery = "SELECT * FROM Craft";
         rs = db.executeQuery(toolQuery);
         while(rs.next()) 
         {
         int id = rs.getInt("ID");
         ObjectType ot;
         int Level = rs.getInt("Level");
            
         switch(rs.getString("Type")) 
         {
         case "Item":
         ot = 
         }
         }*/
    }
}
