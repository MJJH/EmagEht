/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thegame;

import display.CombineParts;
import display.IntColor;
import display.Parts;
import display.Sets;
import display.iTexture;
import gui.pages.LoginFX;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
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

        try
        {
            loadDatabase();
        } catch (SQLException ex)
        {
            System.err.println(ex.getMessage());
            System.exit(0);
        }

        Pane root = new Pane();
        Scene scene = new Scene(root, 1280, 720);
        primaryStage.setScene(scene);

        new LoginFX(primaryStage);

        primaryStage.setOnCloseRequest(event ->
        {
            System.exit(0);
        });
    }

    private void loadDatabase() throws SQLException, IOException
    {
        Database db = Database.getDatabase();
        ResultSet rs;
        
        // Parts
        String partsQuery = "SELECT * FROM Image";
        rs = db.executeQuery(partsQuery);
        while(rs.next())
        {
            new Parts(rs.getString("Name"), iTexture.Part.valueOf(rs.getString("Part")), rs.getBoolean("Body"), rs.getInt("X"), rs.getInt("Y"), rs.getInt("Width"), rs.getInt("Height"), rs.getInt("ConnectX"), rs.getInt("ConnectY"));
        }
        
        // Sets
        String setQuery = "SELECT * FROM image_set";
        rs = db.executeQuery(setQuery);
        ResultSet rsI;
        while(rs.next())
        {
            List<CombineParts> cb = new ArrayList<>();
            String setpartQuery = "SELECT * FROM set_piece WHERE `set` = '"+rs.getString("Name")+"'";
            rsI = db.executeQuery(setpartQuery);
            while(rsI.next())
            {
                cb.add(new CombineParts(Parts.parts.get(rsI.getString("image")), rsI.getInt("place"), rsI.getInt("X"), rsI.getInt("Y")));
            }
            new Sets(rs.getString("Name"), cb);
        }
        
        // Tool
        String toolQuery = "SELECT * FROM Tool";
        rs = db.executeQuery(toolQuery);
        while (rs.next())
        {
            new ToolType(rs.getString("Name"), rs.getInt("Strength"), rs.getInt("Speed"), rs.getInt("Radius"), rs.getInt("reqLvl"), ToolType.toolType.valueOf(rs.getString("Type")), (float) rs.getDouble("KnockBack"), Sets.sets.get(rs.getString("image")), IntColor.fromDB(rs.getString("color_set")));
        }
        
        // Blocks
        String blockQuery = "SELECT * FROM Resource";
        rs = db.executeQuery(blockQuery);
        while (rs.next())
        {
            new BlockType(rs.getString("Name"), rs.getInt("Strength"), rs.getInt("ToolLevel"), ToolType.toolType.valueOf(rs.getString("ToolType")), (float) rs.getDouble("Solid"), Sets.sets.get(rs.getString("image")), IntColor.fromDB(rs.getString("color_set")));
        }

        // Armor
        String armorQuery = "SELECT * FROM Armor";
        rs = db.executeQuery(armorQuery);
        while (rs.next())
        {
            new ArmorType(rs.getString("Name"), rs.getInt("DIA"), rs.getInt("RequiredLvl"), ArmorType.bodyPart.valueOf(rs.getString("ArmorType")), Sets.sets.get(rs.getString("image")), IntColor.fromDB(rs.getString("color_set")));
        }

        // Item
        String itemQuery = "SELECT * FROM Item";
        rs = db.executeQuery(itemQuery);
        while (rs.next())
        {
            new ItemType(rs.getString("Name"), rs.getInt("Width"), rs.getInt("Height"), Sets.sets.get(rs.getString("image")), IntColor.fromDB(rs.getString("color_set")));
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
