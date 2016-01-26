/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.pages;

import java.awt.Panel;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import thegame.com.Menu.Account;
import thegame.com.storage.Database;

/**
 *
 * @author laure
 */
public class LoginFX {

    Stage primaryStage;
    private TextField login;
    private PasswordField wachtwoord;
    private Button confirm;
    private boolean confirmDisabled;

    public LoginFX(Stage primaryStage)
    {
        this.primaryStage = primaryStage;
        primaryStage.setOnCloseRequest(event ->
        {
            System.exit(0);
        });

        primaryStage.setTitle("Login");
        primaryStage.setScene(createLogin());
        primaryStage.show();
    }

    public Scene createLogin()
    {
        double width = primaryStage.getScene().getWidth();
        double height = primaryStage.getScene().getHeight();

        AnchorPane root = new AnchorPane();
        login = new TextField();
        wachtwoord = new PasswordField();
        confirm = new Button();
        confirm.setText("Confirm");
        confirmDisabled = false;

        AnchorPane.setTopAnchor(login, height * 0.38);
        AnchorPane.setLeftAnchor(login, width * 0.45);
        AnchorPane.setTopAnchor(wachtwoord, (height * 0.44));
        AnchorPane.setLeftAnchor(wachtwoord, width * 0.45);
        AnchorPane.setTopAnchor(confirm, (height * 0.5));
        AnchorPane.setLeftAnchor(confirm, (width * 0.45));

        root.getChildren().add(login);
        root.getChildren().add(wachtwoord);
        root.getChildren().add(confirm);
        
        Label usernamelbl = new Label("Username:");
        Label passwordlbl = new Label("Password:");

        
        Button Registreren = new Button("Registreren");
        
        AnchorPane.setTopAnchor(Registreren, height * 0.5);
        AnchorPane.setLeftAnchor(Registreren, width * 0.51);
        root.getChildren().add(Registreren);
        
         AnchorPane.setTopAnchor(usernamelbl, height * 0.38);
         AnchorPane.setLeftAnchor(usernamelbl, width * 0.39);
         root.getChildren().add(usernamelbl);
     
         AnchorPane.setTopAnchor(passwordlbl, height * 0.44);
         AnchorPane.setLeftAnchor(passwordlbl, width * 0.39);
         root.getChildren().add(passwordlbl);
     
     
        confirm.setOnMouseClicked((MouseEvent t) ->
        {
            if (!confirmDisabled)
            {
                confirmDisabled = true;
                Thread loginThread = new Thread(this::login, "loginThread");
                loginThread.start();
            }
        });
        
        Registreren.setOnMouseClicked((MouseEvent t) ->
        {
            new RegisterFX(primaryStage);
        });
        
        

        return new Scene(root, primaryStage.getScene().getWidth(), primaryStage.getScene().getHeight(), Color.BLACK);
    }

    private void login()
    {
        String username = login.getText();
        String password = wachtwoord.getText();
        if (username == null || username.isEmpty())
        {
            System.out.println("Username is leeg");
            confirmDisabled = false;
            return;
        }
        if (password == null || password.isEmpty())
        {
            System.out.println("Password is leeg");
            confirmDisabled = false;
            return;
        }
        Account account = Database.getDatabase().checkCredentials(username, password);
        if (account != null)
        {
            Platform.runLater(() ->
            {
                new MenuFX(primaryStage, account, false);
            });
        } else
        {
            confirmDisabled = false;
        }
    }
}
