/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thegame;

import display.Animation;
import sound.Sound;
import gui.Title;
import gui.SplashScreen;
import gui.MenuItem;
import gui.MenuBox;
import display.Skin;
import gui.JavaFXColorPicker;
import java.io.IOException;
import javafx.scene.Scene;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import thegame.com.Game.Map;
import thegame.com.Game.Objects.Characters.Player;
import thegame.com.Game.Objects.MapObject;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javax.swing.JFrame;
import thegame.com.Game.Objects.ArmorType;
import thegame.com.Game.Objects.Characters.CharacterGame;
import thegame.com.Menu.Account;
import thegame.com.Menu.Message;
import thegame.shared.IGameClientToServer;
import thegame.tutorial.TutPlayer;
import thegame.tutorial.Tutorial;

/**
 *
 * @author laurens
 */
public class Startup extends Application {

    private Account myAccount;
    private Map play;
    private Player me;
    private Scene scene;
    private Image img;
    // SERVER
    private Registry server;
    private GameServerToClientListener listener;
    public IGameClientToServer gameClientToServer;

    private List<KeyCode> keys = new ArrayList<>();
    private SplashScreen splash;

    private Sound sound;

    // FPS
    private final long ONE_SECOND = 1000000000;
    private long currentTime = 0;
    private long lastTime = 0;
    private int fps = 0;
    private double delta = 0;


    private boolean inventory = false;
    private AnimationTimer draw;
    private Timer movement;

    private Stage stages;
    public boolean LoadingDone;


    @Override
    public void start(Stage primaryStage) throws IOException
    {
        primaryStage.setOnCloseRequest(event ->
        {
            if (gameClientToServer != null && listener != null)
            {
                try
                {
                    gameClientToServer.leavePlayer(listener);
                } catch (RemoteException ex)
                {
                    System.out.println("Could not reach the server. (Exception: " + ex.getMessage() + ")");
                    Platform.runLater(() ->
                    {
                        connectionLoss();
                    });
                }
            }
            System.exit(0);
        });
        Scene scene = new Scene(createContent());
        primaryStage.setTitle("Menu");
        primaryStage.setScene(scene);
        primaryStage.show();
        stages = primaryStage;
    }

    private void connectToServer(Stage primaryStage) throws InterruptedException
    {
        loadingScreen(primaryStage);

        Thread updateListenerThread = new Thread(() ->
        {
            try
            {
                server = LocateRegistry.getRegistry(config.ip, config.reachGameLogicPort);
                gameClientToServer = (IGameClientToServer) server.lookup(config.bindName);
                Random rand = new Random();
                splash.countTill(25);
                Thread.sleep(500);
                myAccount = new Account(Integer.toString(rand.nextInt(1000)));
                listener = new GameServerToClientListener();
                UnicastRemoteObject.exportObject(listener, config.updateListenerPort);
                splash.countTill(50);
                Thread.sleep(500);
                me = gameClientToServer.joinPlayer(myAccount, listener);
                play = (Map) gameClientToServer.getMap();
                listener.loadAfterRecieve(myAccount, play, me);
                splash.countTill(75);
                Thread.sleep(500);
                play.loadAfterRecieve(gameClientToServer, myAccount, me, this);
                me.setMap(play);
                splash.countTill(100);
                Thread.sleep(1000);
                Platform.runLater(() ->
                {
                    startagame(stages);
                });
            } catch (NotBoundException | InterruptedException ex)
            {
                Logger.getLogger(Startup.class.getName()).log(Level.SEVERE, null, ex);
            } catch (RemoteException ex)
            {
                System.out.println("Could not reach the server. (Exception: " + ex.getMessage() + ")");
                Platform.runLater(() ->
                {
                    connectionLoss();
                });
            }
        }, "updateListenerThread");
        updateListenerThread.start();
    }

    public void startagame(Stage primaryStage)
    {
        new Game(primaryStage, me, myAccount, play, gameClientToServer);
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
            try
            {
               connectToServer(stages);
            } catch (InterruptedException ex)
            {
                Logger.getLogger(Startup.class.getName()).log(Level.SEVERE, null, ex);
            }
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

    

    private void loadingScreen(Stage primaryStage)
    {
        
        // Start tutorial
        StackPane root = new StackPane();
        scene = new Scene(root, primaryStage.getScene().getWidth(), primaryStage.getScene().getHeight(), Color.BLACK);
        
        stages.setTitle("Loading Screen");
        stages.setScene(scene);
        stages.show();

        try
        {
            splash = new SplashScreen();
            splash.giveSplash(splash);
            splash.SplashScreen();

        } catch (Exception e)
        {
        }
    }

    public void connectionLoss()
    {
        myAccount = null;
        play = null;
        me = null;
        server = null;
        listener = null;
        gameClientToServer = null;
        keys = new ArrayList<>();
        draw.stop();
        movement.cancel();
        sound.stop();
        sound = null;

        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Connectionn loss");
        alert.setHeaderText("Connection to server lost");
        alert.setContentText("You lost the connection to the server. Please try again in a minute.");
        alert.showAndWait();
        
        try
        {
            start(stages);
        } catch (IOException ex)
        {
            Logger.getLogger(Startup.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
