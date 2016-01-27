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
import gui.pages.LoginFX;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import sound.Sound;
import thegame.com.Game.Crafting;
import thegame.com.Game.Objects.ArmorType;
import thegame.com.Game.Objects.BlockType;
import thegame.com.Game.Objects.ItemType;
import thegame.com.Game.Objects.ObjectType;
import thegame.com.Game.Objects.ToolType;
import thegame.com.storage.Database;

/**
 *
 * @author laurens
 */
public class Startup extends Application {

    private Stage primaryStage;
    public static Sound music;
    public static Sound hit;

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
        loadSound();

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
        rs = db.executeUnsafeQuery(partsQuery);
        while (rs.next())
        {
            new Parts(rs.getString("Name"), display.iTexture.Part.valueOf(rs.getString("Part")), rs.getBoolean("Body"), rs.getInt("X"), rs.getInt("Y"), rs.getInt("Width"), rs.getInt("Height"), rs.getInt("ConnectX"), rs.getInt("ConnectY"));
        }

        // Sets
        String setQuery = "SELECT * FROM image_set";
        rs = db.executeUnsafeQuery(setQuery);
        ResultSet rsI;
        while (rs.next())
        {
            List<CombineParts> cb = new ArrayList<>();
            String setpartQuery = "SELECT * FROM set_piece WHERE `set` = '" + rs.getString("Name") + "'";
            rsI = db.executeUnsafeQuery(setpartQuery);
            while (rsI.next())
            {
                cb.add(new CombineParts(Parts.parts.get(rsI.getString("image")), rsI.getInt("place"), rsI.getInt("X"), rsI.getInt("Y"), rsI.getInt("place")));
            }
            new Sets(rs.getString("Name"), cb);
        }

        // Tool
        String toolQuery = "SELECT * FROM Tool";
        rs = db.executeUnsafeQuery(toolQuery);
        while (rs.next())
        {
            new ToolType(rs.getString("Name"), rs.getInt("Strength"), rs.getInt("Speed"), rs.getInt("Radius"), rs.getInt("reqLvl"), ToolType.toolType.valueOf(rs.getString("Type")), (float) rs.getDouble("KnockBack"), Sets.sets.get(rs.getString("image")), IntColor.fromDB(rs.getString("color_set"), db));
        }

        // Blocks
        String blockQuery = "SELECT * FROM Resource";
        rs = db.executeUnsafeQuery(blockQuery);
        while (rs.next())
        {
            new BlockType(rs.getString("Name"), rs.getInt("Strength"), rs.getInt("ToolLevel"), ToolType.toolType.valueOf(rs.getString("ToolType")), (float) rs.getDouble("Solid"), Sets.sets.get(rs.getString("image")), IntColor.fromDB(rs.getString("color_set"), db));
        }

        // Armor
        String armorQuery = "SELECT * FROM Armor";
        rs = db.executeUnsafeQuery(armorQuery);
        while (rs.next())
        {
            new ArmorType(rs.getString("Name"), rs.getInt("DIA"), rs.getInt("RequiredLvl"), ArmorType.bodyPart.valueOf(rs.getString("ArmorType")), Sets.sets.get(rs.getString("image")), IntColor.fromDB(rs.getString("color_set"), db));
        }

        // Item
        String itemQuery = "SELECT * FROM Item";
        rs = db.executeUnsafeQuery(itemQuery);
        while (rs.next())
        {
            new ItemType(rs.getString("Name"), rs.getInt("Width"), rs.getInt("Height"), Sets.sets.get(rs.getString("image")), IntColor.fromDB(rs.getString("color_set"), db));
        }
        db.closeConnection();
        
        // Crafting
        String craftQuery = "SELECT * FROM Craft Order by Level";
         rs = db.executeUnsafeQuery(craftQuery);
         while(rs.next()) 
         {
             ObjectType to_craft = getType(db, rs.getString("type"), rs.getInt("ObjectID"));
             int level = rs.getInt("Level");
             String needed = "SELECT * FROM need WHERE CraftID = "+rs.getInt("ID");
             ResultSet rs2 = db.executeUnsafeQuery(needed);
             HashMap<ObjectType, Integer> n = new HashMap<>(); 
             while(rs2.next()) {
                 n.put(getType(db, rs2.getString("Type"), rs2.getInt("ObjectID")), rs2.getInt("Amount"));
             }
             new Crafting(to_craft, n, level, null);
         }
    }
         
    public ObjectType getType(Database db, String type, int id) throws SQLException {
        String query = "SELECT Name FROM "+type+" WHERE ID ="+id;
        ResultSet rs = db.executeUnsafeQuery(query);

        
        rs.first();
        switch(type){
            case "Item":
                return ItemType.itemtypes.get(rs.getString("Name"));
            case "Resource":
                return BlockType.blocktypes.get(rs.getString("Name"));
            case "Tool":
                return ToolType.tooltypes.get(rs.getString("Name"));
            case "Armor":
                return ArmorType.armortypes.get(rs.getString("Name"));
        }
        return null;
    }

    private void loadSound()
    {
        music = new Sound("GameSound.wav", -10);
        hit = new Sound("Hit.wav", 0);
    }
}
