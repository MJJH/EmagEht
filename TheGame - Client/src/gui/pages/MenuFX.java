/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.pages;

import gui.JavaFXColorPicker;
import gui.JavaFXColorPicker;
import gui.MenuBox;
import gui.MenuBox;
import gui.MenuItem;
import gui.MenuItem;
import gui.Title;
import gui.Title;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import thegame.Startup;

/**
 *
 * @author laure
 */
public class MenuFX {

    Stage primaryStage;

    public MenuFX(Stage primaryStage)
    {
        this.primaryStage = primaryStage;
        primaryStage.setTitle("Menu");
        primaryStage.setScene(createMenu());
        primaryStage.show();
    }

    private Scene createMenu()
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
            new LobbyFX(primaryStage);
        });

        MenuItem startTut = new MenuItem("TUTORIAL [WIP]");
        startTut.setOnMouseClicked(event ->
        {
            new LoadingFX(primaryStage);
        }
        );

        MenuItem CustomizeCharacter = new MenuItem("Customize Character [WIP]");
        CustomizeCharacter.setOnMouseClicked(event ->
        {
            JavaFXColorPicker p = new JavaFXColorPicker();
            try
            {
                p.start(primaryStage);
            } catch (IOException ex)
            {
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
        return new Scene(root);
    }
}
