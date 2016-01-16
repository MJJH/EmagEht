/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thegame;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.RMISocketFactory;
import java.rmi.server.UnicastRemoteObject;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.util.Timer;
import java.util.TimerTask;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import thegame.com.Game.Objects.ArmorType;
import thegame.com.Game.Objects.BlockType;
import thegame.com.Game.Objects.ItemType;
import thegame.com.Game.Objects.ToolType;
import thegame.com.storage.Database;

/**
 *
 * @author Gebruiker
 */
public class TheGameServer extends Application {

    private Registry gameClientToServerRegistry = null;
    private Registry lobbyClientToServerRegistry = null;
    private GameClientToServerHandler gameClientToServerHandler = null;
    private LobbyClientToServerHandler lobbyClientToServerHandler = null;

    private Label lbConnectedPlayersN;
    private Label lbLobbiesN;
    private Label lbGamesN;
    private Label lbFreeMemoryN;
    private Label lbAllocatedMemoryN;
    private Label lbMaxMemoryN;
    private Label lbTotalFreeMemoryN;

    public void startServer()
    {
        try
        {
            /*
             RMISocketFactory.setSocketFactory(new RMISocketFactory() {
             @Override
             public Socket createSocket(String host, int port)
             throws IOException
             {
             Socket socket = new Socket();
             socket.setSoTimeout(config.timeOutTime);
             socket.setSoLinger(false, 0);
             socket.connect(new InetSocketAddress(host, port), config.timeOutTime);
             return socket;
             }

             @Override
             public ServerSocket createServerSocket(int port)
             throws IOException
             {
             return new ServerSocket(port);
             }
             });
             */

            // Lobby
            LobbyServerToClientHandler lobbyServerToClientHandler = new LobbyServerToClientHandler();
            lobbyClientToServerHandler = new LobbyClientToServerHandler();
            lobbyClientToServerRegistry = LocateRegistry.createRegistry(config.lobbyServerToClientPort, RMISocketFactory.getSocketFactory(), RMISocketFactory.getSocketFactory());
            lobbyClientToServerRegistry.rebind(config.lobbyClientToServerName, lobbyClientToServerHandler);
            UnicastRemoteObject.exportObject(lobbyClientToServerHandler, config.lobbyClientToServerPort);
            System.out.println("The lobby component is running");

            // Game
            GameServerToClientHandler gameServerToClientHandler = new GameServerToClientHandler();
            gameClientToServerHandler = new GameClientToServerHandler();
            gameClientToServerRegistry = LocateRegistry.createRegistry(config.gameServerToClientPort, RMISocketFactory.getSocketFactory(), RMISocketFactory.getSocketFactory());
            gameClientToServerRegistry.rebind(config.gameClientToServerName, gameClientToServerHandler);
            UnicastRemoteObject.exportObject(gameClientToServerHandler, config.gameClientToServerPort);
            System.out.println("The game component is running");

            // Register components
            lobbyServerToClientHandler.registerComponents(lobbyClientToServerHandler, gameServerToClientHandler, gameClientToServerHandler, this);
            lobbyClientToServerHandler.registerComponents(lobbyServerToClientHandler, gameServerToClientHandler, gameClientToServerHandler, this);
            gameServerToClientHandler.registerComponents(lobbyServerToClientHandler, lobbyClientToServerHandler, gameClientToServerHandler, this);
            gameClientToServerHandler.registerComponents(lobbyServerToClientHandler, lobbyClientToServerHandler, gameServerToClientHandler, this);
            System.out.println("Components registered and running");

        } catch (RemoteException ex)
        {
            lobbyClientToServerHandler = null;
            lobbyClientToServerRegistry = null;
            gameClientToServerHandler = null;
            gameClientToServerRegistry = null;
            System.out.println("Server could not be started (" + ex.getMessage() + ")");
            System.out.println("Shutting down now");
            System.exit(0);
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        primaryStage.setOnCloseRequest(event ->
        {
            System.exit(0);
        });
        primaryStage.setWidth(200);
        primaryStage.setHeight(210);
        primaryStage.setTitle("The Game - Server");
        primaryStage.initStyle(StageStyle.UTILITY);
        primaryStage.setAlwaysOnTop(true);

        System.setProperty("java.rmi.server.hostname", config.ip);
        loadDatabase();
        startServer();

        AnchorPane root = new AnchorPane();

        Label lbConnectedPlayers = new Label("Connected players:");
        AnchorPane.setTopAnchor(lbConnectedPlayers, 10.0);
        AnchorPane.setLeftAnchor(lbConnectedPlayers, 10.0);
        root.getChildren().add(lbConnectedPlayers);

        Label lbLobbies = new Label("Lobbies:");
        AnchorPane.setTopAnchor(lbLobbies, 30.0);
        AnchorPane.setLeftAnchor(lbLobbies, 10.0);
        root.getChildren().add(lbLobbies);

        Label lbGames = new Label("Games:");
        AnchorPane.setTopAnchor(lbGames, 50.0);
        AnchorPane.setLeftAnchor(lbGames, 10.0);
        root.getChildren().add(lbGames);

        Label lbFreeMemory = new Label("Free memory:");
        AnchorPane.setTopAnchor(lbFreeMemory, 90.0);
        AnchorPane.setLeftAnchor(lbFreeMemory, 10.0);
        root.getChildren().add(lbFreeMemory);

        Label lbAllocatedMemory = new Label("Allocated memory:");
        AnchorPane.setTopAnchor(lbAllocatedMemory, 110.0);
        AnchorPane.setLeftAnchor(lbAllocatedMemory, 10.0);
        root.getChildren().add(lbAllocatedMemory);

        Label lbMaxMemory = new Label("Max memory:");
        AnchorPane.setTopAnchor(lbMaxMemory, 130.0);
        AnchorPane.setLeftAnchor(lbMaxMemory, 10.0);
        root.getChildren().add(lbMaxMemory);

        Label lbTotalFreeMemory = new Label("Total free memory:");
        AnchorPane.setTopAnchor(lbTotalFreeMemory, 150.0);
        AnchorPane.setLeftAnchor(lbTotalFreeMemory, 10.0);
        root.getChildren().add(lbTotalFreeMemory);

        lbConnectedPlayersN = new Label("0");
        AnchorPane.setTopAnchor(lbConnectedPlayersN, 10.0);
        AnchorPane.setLeftAnchor(lbConnectedPlayersN, 120.0);
        root.getChildren().add(lbConnectedPlayersN);

        lbLobbiesN = new Label("0");
        AnchorPane.setTopAnchor(lbLobbiesN, 30.0);
        AnchorPane.setLeftAnchor(lbLobbiesN, 120.0);
        root.getChildren().add(lbLobbiesN);

        lbGamesN = new Label("0");
        AnchorPane.setTopAnchor(lbGamesN, 50.0);
        AnchorPane.setLeftAnchor(lbGamesN, 120.0);
        root.getChildren().add(lbGamesN);

        lbFreeMemoryN = new Label("0");
        AnchorPane.setTopAnchor(lbFreeMemoryN, 90.0);
        AnchorPane.setLeftAnchor(lbFreeMemoryN, 120.0);
        root.getChildren().add(lbFreeMemoryN);

        lbAllocatedMemoryN = new Label("0");
        AnchorPane.setTopAnchor(lbAllocatedMemoryN, 110.0);
        AnchorPane.setLeftAnchor(lbAllocatedMemoryN, 120.0);
        root.getChildren().add(lbAllocatedMemoryN);

        lbMaxMemoryN = new Label("0");
        AnchorPane.setTopAnchor(lbMaxMemoryN, 130.0);
        AnchorPane.setLeftAnchor(lbMaxMemoryN, 120.0);
        root.getChildren().add(lbMaxMemoryN);

        lbTotalFreeMemoryN = new Label("0");
        AnchorPane.setTopAnchor(lbTotalFreeMemoryN, 150.0);
        AnchorPane.setLeftAnchor(lbTotalFreeMemoryN, 120.0);
        root.getChildren().add(lbTotalFreeMemoryN);

        Timer displayMemory = new Timer();
        displayMemory.schedule(new TimerTask() {

            @Override
            public void run()
            {
                displayMemory();
            }
        }, 0, 1000);

        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    private void loadDatabase() throws SQLException, ClassNotFoundException
    {
        Database db = Database.getDatabase();
        ResultSet rs;
        // Blocks
        String blockQuery = "SELECT * FROM Resource";
        rs = db.executeUnsafeQuery(blockQuery);
        while (rs.next())
        {
            new BlockType(rs.getString("Name"), rs.getInt("Strength"), rs.getInt("ToolLevel"), ToolType.toolType.valueOf(rs.getString("ToolType")), (float) rs.getDouble("Solid"));
        }

        // Armor
        String armorQuery = "SELECT * FROM Armor";
        rs = db.executeUnsafeQuery(armorQuery);
        while (rs.next())
        {
            new ArmorType(rs.getString("Name"), rs.getInt("DIA"), rs.getInt("RequiredLvl"), ArmorType.bodyPart.valueOf(rs.getString("ArmorType")));
        }

        // Tool
        String toolQuery = "SELECT * FROM Tool";
        rs = db.executeUnsafeQuery(toolQuery);
        while (rs.next())
        {
            new ToolType(rs.getString("Name"), rs.getInt("Strength"), rs.getInt("Speed"), rs.getInt("Radius"), rs.getInt("ToolLevel"), ToolType.toolType.valueOf(rs.getString("Type")), (float) rs.getDouble("KnockBack"));
        }

        // Item
        String itemQuery = "SELECT * FROM item";
        rs = db.executeUnsafeQuery(itemQuery);
        while (rs.next())
        {
            new ItemType(rs.getString("Name"), rs.getInt("Width"), rs.getInt("Height"));
        }
        db.closeConnection();

        // Crafting
        /*String craftQuery = "SELECT * FROM Craft";
         rs = db.executeUnsafeQuery(toolQuery);
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

    private void displayMemory()
    {
        Runtime runtime = Runtime.getRuntime();
        NumberFormat format = NumberFormat.getInstance();

        long maxMemory = runtime.maxMemory();
        long allocatedMemory = runtime.totalMemory();
        long freeMemory = runtime.freeMemory();

        Platform.runLater(() ->
        {
            lbFreeMemoryN.setText(format.format(freeMemory / 1024));
            lbAllocatedMemoryN.setText(format.format(allocatedMemory / 1024));
            lbMaxMemoryN.setText(format.format(maxMemory / 1024));
            lbFreeMemoryN.setText(format.format((freeMemory + (maxMemory - allocatedMemory)) / 1024));
        });
    }
    
    public void changeConnectedPlayer(int change)
    {
        Platform.runLater(() ->
        {
            int connectedPlayers = Integer.parseInt(lbConnectedPlayersN.getText());
            connectedPlayers += change;
            lbConnectedPlayersN.setText(Integer.toString(connectedPlayers));
        });
    }
    
    public void changeLobbies(int change)
    {
        Platform.runLater(() ->
        {
            int lobbies = Integer.parseInt(lbLobbiesN.getText());
            lobbies += change;
            lbLobbiesN.setText(Integer.toString(lobbies));
        });
    }
    
    public void changeGames(int change)
    {
        Platform.runLater(() ->
        {
            int games = Integer.parseInt(lbGamesN.getText());
            games += change;
            lbGamesN.setText(Integer.toString(games));
        });
    }
}
