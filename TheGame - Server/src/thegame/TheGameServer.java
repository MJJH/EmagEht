/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thegame;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import javafx.application.Application;
import javafx.stage.Stage;
import thegame.com.Game.GameLogic;

/**
 *
 * @author Gebruiker
 */
public class TheGameServer extends Application {

    private Registry registry = null;
    private GameLogic gameLogic = null;

    public void startServer()
    {
        try
        {
            gameLogic = new GameLogic();
            System.out.println("Selected port is " + config.port);
            registry = LocateRegistry.createRegistry(config.port);
            registry.rebind(config.bindName, gameLogic);
            System.out.println("Server gestart");
            UnicastRemoteObject.exportObject(gameLogic, 1100);
        } catch (RemoteException ex)
        {
            gameLogic = null;
            registry = null;
            System.out.println(ex.getMessage());
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
        
        startServer();
        primaryStage.setOnCloseRequest(event ->
        {
            System.exit(0);
        });
    }
}
