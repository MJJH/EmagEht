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
    private TextField wachtwoord;
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
        wachtwoord = new TextField();
        confirm = new Button();
        confirm.setText("Confirm");
        confirmDisabled = false;

        AnchorPane.setTopAnchor(login, height / 2.5);
        AnchorPane.setLeftAnchor(login, width / 2.5);
        AnchorPane.setTopAnchor(wachtwoord, (height / 2.5) + 50);
        AnchorPane.setLeftAnchor(wachtwoord, width / 2.5);
        AnchorPane.setTopAnchor(confirm, (height / 2.5) + 100);
        AnchorPane.setLeftAnchor(confirm, (width / 2.5) + 50);

        root.getChildren().add(login);
        root.getChildren().add(wachtwoord);
        root.getChildren().add(confirm);

        confirm.setOnMouseClicked((MouseEvent t) ->
        {
            if (!confirmDisabled)
            {
                confirmDisabled = true;
                Thread loginThread = new Thread(this::login, "loginThread");
                loginThread.start();
            }
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
