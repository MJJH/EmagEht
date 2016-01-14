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
import javafx.application.Application;
import javafx.stage.Stage;
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
            lobbyServerToClientHandler.registerComponents(lobbyClientToServerHandler, gameServerToClientHandler, gameClientToServerHandler);
            lobbyClientToServerHandler.registerComponents(lobbyServerToClientHandler, gameServerToClientHandler, gameClientToServerHandler);
            gameServerToClientHandler.registerComponents(lobbyServerToClientHandler, lobbyClientToServerHandler, gameClientToServerHandler);
            gameClientToServerHandler.registerComponents(lobbyServerToClientHandler, lobbyClientToServerHandler, gameServerToClientHandler);
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
        primaryStage.setWidth(100);
        primaryStage.setHeight(100);
        primaryStage.show();

        System.setProperty("java.rmi.server.hostname", config.ip);

        loadDatabase();
        startServer();
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
            new BlockType(rs.getString("Name"), rs.getInt("Strength"), rs.getInt("ToolLevel"), ToolType.toolType.valueOf(rs.getString("ToolType")), (float) rs.getDouble("Solid"));
        }

        // Armor
        String armorQuery = "SELECT * FROM Armor";
        rs = db.executeQuery(armorQuery);
        while (rs.next())
        {
            new ArmorType(rs.getString("Name"), rs.getInt("DIA"), rs.getInt("RequiredLvl"), ArmorType.bodyPart.valueOf(rs.getString("ArmorType")));
        }

        // Tool
        String toolQuery = "SELECT * FROM Tool";
        rs = db.executeQuery(toolQuery);
        while (rs.next())
        {
            new ToolType(rs.getString("Name"), rs.getInt("Strength"), rs.getInt("Speed"), rs.getInt("Radius"), rs.getInt("ToolLevel"), ToolType.toolType.valueOf(rs.getString("Type")), (float) rs.getDouble("KnockBack"));
        }

        // Item
        String itemQuery = "SELECT * FROM item";
        rs = db.executeQuery(itemQuery);
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
