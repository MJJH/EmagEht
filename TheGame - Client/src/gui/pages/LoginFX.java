/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.pages;

import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javax.swing.event.HyperlinkEvent;
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

    public LoginFX(Stage primaryStage)
    {
        this.primaryStage = primaryStage;
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
        Button confirm = new Button();
        confirm.setText("Confirm");

        AnchorPane.setTopAnchor(login, height / 2);
        AnchorPane.setLeftAnchor(login, width / 2);
        AnchorPane.setTopAnchor(wachtwoord, (height / 2) + 75);
        AnchorPane.setLeftAnchor(wachtwoord, width / 2);
        AnchorPane.setTopAnchor(confirm, (height / 2) + 100);
        AnchorPane.setLeftAnchor(confirm, width / 2);

        root.getChildren().add(login);
        root.getChildren().add(wachtwoord);
        root.getChildren().add(confirm);

        confirm.setOnMouseClicked((MouseEvent t) ->
        {
            login();
        });

        return new Scene(root, primaryStage.getScene().getWidth(), primaryStage.getScene().getHeight(), Color.BLACK);
    }

    private void login()
    {
        String username = login.getText();
        String password = wachtwoord.getText();
        if(username == null || username.isEmpty())
        {
            System.out.println("Username is leeg");
            return;
        }
        if(password == null || password.isEmpty())
        {
            System.out.println("Password is leeg");
            return;
        }
        Account account = Database.getDatabase().checkCredentials(username, password);
        if(account != null)
        {
            new MenuFX(primaryStage, account);
        }
    }
}
