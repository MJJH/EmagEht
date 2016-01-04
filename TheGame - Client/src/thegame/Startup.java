/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thegame;

import gui.Title;
import gui.MenuItem;
import gui.MenuBox;
import gui.JavaFXColorPicker;
import java.io.IOException;
import javafx.scene.Scene;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.Parent;
import javafx.scene.layout.Pane;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
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
    private Scene scene;
    private Stage stages;

    @Override
    public void start(Stage primaryStage) throws IOException
    {
        /*try {
            loadDatabase();
        } catch (SQLException | ClassNotFoundException ex) {
            System.exit(0);
        }*/
        
        primaryStage.setOnCloseRequest(event ->
        {
            System.exit(0);
        });
        Scene scene = new Scene(createContent());
        primaryStage.setTitle("Menu");
        primaryStage.setScene(scene);
        primaryStage.show();
        stages = primaryStage;
    }
    
    private Parent createContent()
    {
        Pane root = new Pane();
        root.setPrefSize(1280, 720);
        root.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));

        try (InputStream is = Files.newInputStream(Paths.get("src/resources//menu.jpg")))
        {
            ImageView img = new ImageView(new Image(is));
            img.setFitWidth(1280);
            img.setFitHeight(720);
            root.getChildren().add(img);
        } catch (Exception e)
        {
            System.out.println("Couldnt load image");
        }

        Title title = new Title("The Game");
        title.setTranslateX(75);
        title.setTranslateY(200);

        MenuItem itemExit = new MenuItem("EXIT");
        itemExit.setOnMouseClicked(event -> System.exit((0)));


        MenuItem startMultiPlayer = new MenuItem("MULTIPLAYER");
        startMultiPlayer.setOnMouseClicked(event ->
        {
            new LobbyFX(stages, this);
        });
        


        MenuItem startTut = new MenuItem("TUTORIAL [WIP]");
        startTut.setOnMouseClicked(event ->
        {
                new Loading(stages);
        }
        );
        
        MenuItem CustomizeCharacter = new MenuItem("Customize Character [WIP]");
        CustomizeCharacter.setOnMouseClicked(event ->
        {
            JavaFXColorPicker p = new JavaFXColorPicker();
            try {
                p.start(stages);
            } catch (IOException ex) {
                Logger.getLogger(Startup.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        );

        MenuBox menu = new MenuBox(
                startMultiPlayer,
                startTut,
                CustomizeCharacter,
                new MenuItem("OPTIONS [soon]"),
                itemExit);
        menu.setTranslateX(100);
        menu.setTranslateY(300);
        root.getChildren().addAll(title, menu);

        return root;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        launch(args);
    }
    
    private void loadDatabase() throws SQLException, ClassNotFoundException
    {
        Database db = new Database();
        ResultSet rs;
        // Blocks
        String blockQuery = "SELECT * FROM Resource";
        rs = db.executeQuery(blockQuery);
        while(rs.next()) 
        {
            new BlockType(rs.getString("Name"), rs.getInt("Strength"), rs.getInt("ToolLevel"), ToolType.toolType.valueOf(rs.getString("ToolType")), (float) rs.getDouble("Solid"), null, null);
        }
        
        // Armor
        String armorQuery = "SELECT * FROM Armor";
        rs = db.executeQuery(armorQuery);
        while(rs.next()) 
        {
            new ArmorType(rs.getString("Name"), rs.getInt("DIA"), rs.getInt("RequiredLvl"), ArmorType.bodyPart.valueOf(rs.getString("ArmorTypeID")));
        }
        
        // Tool
        String toolQuery = "SELECT * FROM Tool";
        rs = db.executeQuery(toolQuery);
        while(rs.next()) 
        {
            new ToolType(rs.getString("Name"), rs.getInt("Strength"), rs.getInt("Speed"), rs.getInt("Radius"), rs.getInt("reqLvl"), ToolType.toolType.valueOf(rs.getString("Type")), rs.getDouble("KnockBack"));
        }
        
        // Item
        String itemQuery = "SELECT * FROM Item";
        rs = db.executeQuery(toolQuery);
        while(rs.next()) 
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
