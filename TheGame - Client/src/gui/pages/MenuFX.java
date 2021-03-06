/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.pages;

import gui.JavaFXColorPicker;
import gui.MenuBox;
import gui.MenuItem;
import gui.Title;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import thegame.LobbyServerToClientListener;
import thegame.Startup;
import thegame.com.Menu.Account;
import thegame.com.Menu.Lobby;
import thegame.config;
import thegame.shared.ILobbyClientToServer;

/**
 *
 * @author laure
 */
public class MenuFX {

    Stage primaryStage;
    private Account account;
    private Registry lobbyServer;
    private ILobbyClientToServer lobbyClientToServer;
    private LobbyServerToClientListener lobbyServerToClientListener;
    private boolean signedIn;

    public MenuFX(Stage primaryStage, Account account, boolean signedIn)
    {
        this.account = account;
        this.signedIn = signedIn;
        this.primaryStage = primaryStage;

        primaryStage.setOnCloseRequest(event ->
        {
            if (lobbyServer != null && lobbyClientToServer != null)
            {
                try
                {
                    lobbyClientToServer.signOut(account);
                } catch (RemoteException ex)
                {
                    System.out.println("Could not reach the server. (Exception: " + ex.getMessage() + ")");
                }
            }
            System.exit(0);
        });

        Thread connectThread = new Thread(() ->
        {
            try
            {
                connectToLobbyServer();
                if (this.signedIn)
                {
                    Platform.runLater(() ->
                    {
                        primaryStage.setTitle("Menu");
                        primaryStage.setScene(createMenu());
                        primaryStage.show();
                    });
                }
            } catch (RemoteException | NotBoundException ex)
            {
                Platform.runLater(() ->
                {
                    new LoginFX(primaryStage);
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setHeaderText("Server can't be found");
                    alert.setContentText("You can't connect to the server. Please try again in a minute.");
                    alert.showAndWait();
                });
                return;
            }
        });
        connectThread.start();
    }

    private void connectToLobbyServer() throws RemoteException, NotBoundException
    {
        lobbyServer = LocateRegistry.getRegistry(config.ip, config.lobbyServerToClientPort);
        lobbyClientToServer = (ILobbyClientToServer) lobbyServer.lookup(config.lobbyClientToServerName);
        lobbyServerToClientListener = new LobbyServerToClientListener();
        UnicastRemoteObject.exportObject(lobbyServerToClientListener, config.lobbyServerToClientListenerPort);
        if (!signedIn && !lobbyClientToServer.signIn(account, lobbyServerToClientListener))
        {
            Platform.runLater(() ->
            {
                new LoginFX(primaryStage);
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("Already logged in");
                alert.setContentText("You can't login twice.");
                alert.showAndWait();
            });
            return;
        }
        signedIn = true;
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

        MenuItem startMultiPlayerFind = new MenuItem("MULTIPLAYER - QUICK");
        startMultiPlayerFind.setOnMouseClicked(event ->
        {
            try
            {
                Lobby lobby = lobbyClientToServer.findLobby(account);
                if (lobby != null)
                {
                    new LobbyFX(primaryStage, lobby, account, lobbyServer, lobbyClientToServer, lobbyServerToClientListener);
                }
            } catch (RemoteException ex)
            {
                Logger.getLogger(MenuFX.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        MenuItem startMultiPlayerNew = new MenuItem("MULTIPLAYER - NEW");
        startMultiPlayerNew.setOnMouseClicked(event ->
        {
            try
            {
                Lobby lobby = lobbyClientToServer.findNewLobby(account);
                new LobbyFX(primaryStage, lobby, account, lobbyServer, lobbyClientToServer, lobbyServerToClientListener);
            } catch (RemoteException ex)
            {
                Logger.getLogger(MenuFX.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        MenuItem startTut = new MenuItem("TUTORIAL [WIP]");
        startTut.setOnMouseClicked(event ->
        {
            new LoadingFX(primaryStage);
        }
        );

        MenuItem CustomizeCharacter = new MenuItem("CUSTOMIZE CHARACTER [WIP]");
        CustomizeCharacter.setOnMouseClicked(event ->
        {
            JavaFXColorPicker p = new JavaFXColorPicker(account);
            try
            {
                p.start(primaryStage);
            } catch (IOException ex)
            {
                Logger.getLogger(Startup.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        );

        MenuItem logOut = new MenuItem("LOG OUT");
        logOut.setOnMouseClicked(event ->
        {
            if (lobbyServer != null && lobbyClientToServer != null)
            {
                try
                {
                    lobbyClientToServer.signOut(account);
                    UnicastRemoteObject.unexportObject(lobbyServerToClientListener, false);
                } catch (RemoteException ex)
                {
                    System.out.println("Could not reach the server. (Exception: " + ex.getMessage() + ")");
                }
            }
            new LoginFX(primaryStage);
        }
        );

        MenuItem itemExit = new MenuItem("EXIT");
        itemExit.setOnMouseClicked(event -> System.exit((0)));

        MenuBox menu = new MenuBox(
                startMultiPlayerFind,
                startMultiPlayerNew,
                startTut,
                CustomizeCharacter,
                new MenuItem("OPTIONS [soon]"),
                logOut,
                itemExit);
        menu.setTranslateX(100);
        menu.setTranslateY(300);
        root.getChildren().addAll(title, menu);
        return new Scene(root);
    }

    public void show()
    {
        primaryStage.setOnCloseRequest(event ->
        {
            if (lobbyServer != null && lobbyClientToServer != null)
            {
                try
                {
                    lobbyClientToServer.signOut(account);
                } catch (RemoteException ex)
                {
                    System.out.println("Could not reach the server. (Exception: " + ex.getMessage() + ")");
                }
            }
            System.exit(0);
        });

        if (this.signedIn)
        {
            primaryStage.setTitle("Menu");
            primaryStage.setScene(createMenu());
            primaryStage.show();
        }
    }
}
