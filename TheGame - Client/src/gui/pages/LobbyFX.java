/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.pages;

import gui.SplashScreen;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import thegame.GameServerToClientListener;
import thegame.LobbyServerToClientListener;
import thegame.Startup;
import static thegame.Startup.music;
import thegame.com.Game.Map;
import thegame.com.Game.Objects.Characters.Player;
import thegame.com.Menu.Account;
import thegame.com.Menu.Lobby;
import thegame.config;
import thegame.shared.IGameClientToServer;
import thegame.shared.ILobbyClientToServer;

/**
 *
 * @author laure
 */
public class LobbyFX {

    private Stage primaryStage;
    private boolean started;

    // LOBBY
    private Registry lobbyServer;
    private ILobbyClientToServer lobbyClientToServer;
    private LobbyServerToClientListener lobbyServerToClientListener;
    private Lobby lobby;
    private Account myAccount;

    // GAME
    private Registry server;
    private GameServerToClientListener gameServerToClientListener;
    public IGameClientToServer gameClientToServer;
    private Map play;
    private Player me;

    private List<KeyCode> keys = new ArrayList<>();
    private SplashScreen splash;
    private double width;
    private double height;
    private CheckBox cbchar1;
    private CheckBox cbchar2;
    private CheckBox cbchar3;
    private CheckBox cbchar4;
    private Button back;

    public LobbyFX(Stage primaryStage, Lobby lobby, Account account, Registry lobbyServer, ILobbyClientToServer lobbyClientToServer, LobbyServerToClientListener lobbyServerToClientListener)
    {
        this.lobbyServer = lobbyServer;
        this.lobbyClientToServer = lobbyClientToServer;
        this.lobbyServerToClientListener = lobbyServerToClientListener;
        this.lobby = lobby;
        myAccount = account;
        this.primaryStage = primaryStage;

        drawLobby();
    }
    
    private void drawLobby()
    {
        primaryStage.setOnCloseRequest(event ->
        {
            if (lobbyClientToServer != null && lobbyServerToClientListener != null)
            {
                try
                {
                    lobbyClientToServer.signOut(myAccount);
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

        AnchorPane root = new AnchorPane();
        Scene scene = new Scene(root, primaryStage.getScene().getWidth(), primaryStage.getScene().getHeight(), Color.BLACK);
        final Canvas canvas = new Canvas(scene.getWidth(), scene.getHeight());
        GraphicsContext gc = canvas.getGraphicsContext2D();
        height = primaryStage.getHeight();
        width = primaryStage.getWidth();
        primaryStage.setTitle("Lobby");
        primaryStage.setScene(scene);
        primaryStage.show();
        scene.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseListener);
        Label character1 = new Label("Character1");
        Label character2 = new Label("Character2");
        Label character3 = new Label("Character3");
        Label character4 = new Label("Character4");
        
         cbchar1 = new CheckBox();
         cbchar2 = new CheckBox();
         cbchar3 = new CheckBox();
         cbchar4 = new CheckBox();
        
        AnchorPane.setLeftAnchor(cbchar1, width * 0.1);        
        AnchorPane.setTopAnchor(cbchar1, height * 0.3);
        root.getChildren().add(cbchar1);
        AnchorPane.setTopAnchor(character1, height * 0.3);
        AnchorPane.setLeftAnchor(character1, width * 0.15);
        root.getChildren().add(character1);
 
        AnchorPane.setLeftAnchor(cbchar2, width * 0.1);        
        AnchorPane.setTopAnchor(cbchar2, height * 0.35);
        root.getChildren().add(cbchar2);
        AnchorPane.setTopAnchor(character2, height * 0.35);
        AnchorPane.setLeftAnchor(character2, width * 0.15);
        root.getChildren().add(character2);

        AnchorPane.setLeftAnchor(cbchar3, width * 0.1);        
        AnchorPane.setTopAnchor(cbchar3, height * 0.4);
        root.getChildren().add(cbchar3);
        AnchorPane.setTopAnchor(character3, height * 0.4);
        AnchorPane.setLeftAnchor(character3, width * 0.15);
        root.getChildren().add(character3);
  
        AnchorPane.setLeftAnchor(cbchar4, width * 0.1);        
        AnchorPane.setTopAnchor(cbchar4, height * 0.45);
        root.getChildren().add(cbchar4);
        AnchorPane.setTopAnchor(character4, height * 0.45);
        AnchorPane.setLeftAnchor(character4, width * 0.15);
        root.getChildren().add(character4);
        
        cbchar1.setDisable(true);
        cbchar2.setDisable(true);
        cbchar3.setDisable(true);
        cbchar4.setDisable(true);
        
        back = new Button("Back");

        AnchorPane.setLeftAnchor(back,width * 0.95);
        root.getChildren().add(back);
        

        
    }

    private final EventHandler<MouseEvent> mouseListener = (MouseEvent event) ->
    {
        if (event.getButton().equals(MouseButton.PRIMARY))
        {
            clickHandler(event.getSceneX(), event.getSceneY());
        }
    };

    private void clickHandler(double clickX, double clickY)
    {
        //GUI CLICK
        cbchar1.setSelected(checkReady());
    }

    private boolean checkReady()
    {
        try
        {
            // if character selected etc. else return false
            lobbyServerToClientListener.setLobbyFX(this);
            boolean returnValue = lobbyClientToServer.checkReady(myAccount);
            return returnValue;
        } catch (RemoteException ex)
        {
            Logger.getLogger(LobbyFX.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public void connectToGame()
    {
        if (started)
        {
            return;
        }
        loadingScreen(primaryStage);

        Thread updateListenerThread = new Thread(() ->
        {
            try
            {
                server = LocateRegistry.getRegistry(config.ip, config.gameServerToClientPort);
                gameClientToServer = (IGameClientToServer) server.lookup(config.gameClientToServerName);
                splash.countTill(25);
                gameServerToClientListener = new GameServerToClientListener();
                UnicastRemoteObject.exportObject(gameServerToClientListener, config.gameServerToClientListenerPort);
                splash.countTill(50);
                me = gameClientToServer.getMe(gameServerToClientListener, myAccount);
                play = (Map) gameClientToServer.getMap(lobby);
                gameServerToClientListener.loadAfterRecieve(myAccount, play, me);
                splash.countTill(75);
                play.loadAfterRecieve(gameClientToServer, myAccount, me, this);
                me.loadAfterRecieve(play);
                splash.countTill(100);
                Platform.runLater(() ->
                {
                    
                    play.setGameFX(new GameFX(primaryStage, me, myAccount, play, gameClientToServer, gameServerToClientListener));
                });
            } catch (NotBoundException ex)
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

    private void loadingScreen(Stage primaryStage)
    {

        // Start tutorial
        StackPane root = new StackPane();
        Scene scene = new Scene(root, primaryStage.getScene().getWidth(), primaryStage.getScene().getHeight(), Color.BLACK);
        primaryStage.setTitle("Loading Screen");
        primaryStage.setScene(scene);
        primaryStage.show();

        try
        {
            splash = new SplashScreen();
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
        gameServerToClientListener = null;
        gameClientToServer = null;
        keys = new ArrayList<>();

        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Connectionn loss");
        alert.setHeaderText("Connection to server lost");
        alert.setContentText("You lost the connection to the server. Please try again in a minute.");
        alert.showAndWait();
        new LoginFX(primaryStage);
    }

    public void stopGame()
    {
        music.stop();
        play.getGameFX().stopTimers();
        play = null;
        me = null;
        server = null;
        gameClientToServer = null;
        gameServerToClientListener = null;
        drawLobby();
    }
}
