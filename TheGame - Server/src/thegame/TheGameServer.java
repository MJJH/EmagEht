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
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.stage.Stage;

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
            System.out.println("Server is starting up");

            // Lobby
            LobbyServerToClientHandler lobbyServerToClientHandler = new LobbyServerToClientHandler();
            lobbyClientToServerHandler = new LobbyClientToServerHandler(lobbyServerToClientHandler);
            lobbyClientToServerRegistry = LocateRegistry.createRegistry(config.lobbyServerToClientPort, RMISocketFactory.getSocketFactory(), RMISocketFactory.getSocketFactory());
            lobbyClientToServerRegistry.rebind(config.lobbyClientToServerName, lobbyClientToServerHandler);
            UnicastRemoteObject.exportObject(lobbyClientToServerHandler, config.lobbyClientToServerPort);

            // Game
            GameServerToClientHandler gameServerToClientHandler = new GameServerToClientHandler();
            gameClientToServerHandler = new GameClientToServerHandler(gameServerToClientHandler);
            gameClientToServerRegistry = LocateRegistry.createRegistry(config.gameServerToClientPort, RMISocketFactory.getSocketFactory(), RMISocketFactory.getSocketFactory());
            gameClientToServerRegistry.rebind(config.gameClientToServerName, gameClientToServerHandler);
            UnicastRemoteObject.exportObject(gameClientToServerHandler, config.gameClientToServerPort);

            System.out.println("Server is up and running");
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
        startServer();
        primaryStage.setOnCloseRequest(event ->
        {
            System.exit(0);
        });
    }
}
