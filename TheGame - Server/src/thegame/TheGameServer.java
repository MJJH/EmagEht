/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thegame;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;
import javafx.application.Application;
import javafx.stage.Stage;
import thegame.com.Game.GameLogic;

/**
 *
 * @author Gebruiker
 */
public class TheGameServer extends Application{

    // References to registry and student administration
    private Registry registry = null;
    private GameLogic gameLogic = null;

    public TheGameServer()
    {
        try
        {
            gameLogic = new GameLogic();
        } catch (RemoteException ex)
        {
            gameLogic = null;
            System.out.println(ex.getMessage());
        }

        // Create registry at port number
        try
        {
            System.out.println("Selected port is " + config.port);
            registry = LocateRegistry.createRegistry(config.port);
        } catch (RemoteException ex)
        {
            registry = null;
            System.out.println(ex.getMessage());
        }

        // Bind effectenbeurs using registry
        try
        {
            registry.rebind(config.bindName, gameLogic);
        } catch (RemoteException ex)
        {
            System.out.println(ex.getMessage());
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        TheGameServer server = new TheGameServer();
        System.out.println("Server gestart");
        Scanner s = new Scanner(System.in);
        System.out.println("Type stop to stop.");

        while (!s.nextLine().equals("stop"))
        {
            System.out.println("Type stop to stop.");
        }
        System.exit(0);
    }

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
