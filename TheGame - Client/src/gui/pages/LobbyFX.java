/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.pages;

import gui.SplashScreen;
import gui.SplashScreen;
import java.io.IOException;
import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
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
import thegame.com.Game.Map;
import thegame.com.Game.Objects.ArmorType;
import thegame.com.Game.Objects.Characters.Player;
import thegame.com.Menu.Account;
import thegame.config;
import thegame.shared.IGameClientToServer;
import thegame.shared.ILobbyClientToServer;
import thegame.shared.ILobbyServerToClient;

/**
 *
 * @author laure
 */
public class LobbyFX {

    private Stage primaryStage;

    private Account myAccount;
    private Map play;
    private Player me;

    // SERVER
    private Registry server;
    private GameServerToClientListener gameServerToClientListener;
    public IGameClientToServer gameClientToServer;

    private List<KeyCode> keys = new ArrayList<>();
    private SplashScreen splash;

    public LobbyFX(Stage primaryStage, Account account)
    {
        myAccount = account;
        primaryStage.setOnCloseRequest(event ->
        {
            if (gameClientToServer != null && gameServerToClientListener != null)
            {
                try
                {
                    gameClientToServer.leavePlayer(gameServerToClientListener);
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

        this.primaryStage = primaryStage;

        StackPane root = new StackPane();
        Scene scene = new Scene(root, primaryStage.getScene().getWidth(), primaryStage.getScene().getHeight(), Color.BLACK);
        final Canvas canvas = new Canvas(scene.getWidth(), scene.getHeight());
        GraphicsContext gc = canvas.getGraphicsContext2D();
        primaryStage.setTitle("Lobby");
        primaryStage.setScene(scene);
        primaryStage.show();
        scene.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseListener);
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
        connectToGame();
    }

    private void connectToLobby()
    {
        try
        {
            Registry lobbyServer = LocateRegistry.getRegistry(config.ip, config.lobbyServerToClientPort);
            ILobbyClientToServer lobbyClientToServer = (ILobbyClientToServer) server.lookup(config.lobbyClientToServerName);
            LobbyServerToClientListener lobbyServerToClientListener = new LobbyServerToClientListener();
            UnicastRemoteObject.exportObject(lobbyServerToClientListener, config.lobbyServerToClientListenerPort);
        } catch (RemoteException | NotBoundException ex)
        {
            Logger.getLogger(LobbyFX.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void connectToGame()
    {
        loadingScreen(primaryStage);

        Thread updateListenerThread = new Thread(() ->
        {
            try
            {
                server = LocateRegistry.getRegistry(config.ip, config.gameServerToClientPort);
                gameClientToServer = (IGameClientToServer) server.lookup(config.gameClientToServerName);
                splash.countTill(25);
                Thread.sleep(500);
                gameServerToClientListener = new GameServerToClientListener();
                UnicastRemoteObject.exportObject(gameServerToClientListener, config.gameServerToClientListenerPort);
                splash.countTill(50);
                Thread.sleep(500);
                me = gameClientToServer.joinPlayer(myAccount, gameServerToClientListener);
                play = (Map) gameClientToServer.getMap();
                gameServerToClientListener.loadAfterRecieve(myAccount, play, me);
                splash.countTill(75);
                Thread.sleep(500);
                play.loadAfterRecieve(gameClientToServer, myAccount, me, this);
                me.setMap(play);
                splash.countTill(100);
                Thread.sleep(1000);
                Platform.runLater(() ->
                {
                    startagame(primaryStage);
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
        new GameFX(primaryStage, me, myAccount, play, gameClientToServer, gameServerToClientListener);
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
        gameServerToClientListener = null;
        gameClientToServer = null;
        keys = new ArrayList<>();

        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Connectionn loss");
        alert.setHeaderText("Connection to server lost");
        alert.setContentText("You lost the connection to the server. Please try again in a minute.");
        alert.showAndWait();
        new MenuFX(primaryStage, myAccount);
    }
}
