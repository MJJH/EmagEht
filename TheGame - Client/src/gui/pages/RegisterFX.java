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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javax.swing.JOptionPane;
import thegame.com.storage.Database;

/**
 *
 * @author nickbijmoer
 */
public class RegisterFX {
    private Object primaryStage;
    private final Button Register;
    private final Button Back;
    private final TextField username;
    private final PasswordField password;
    private final PasswordField confirmpassword;

    RegisterFX(Stage primaryStage) {
    AnchorPane root = new AnchorPane();
    Scene scene = new Scene(root, primaryStage.getWidth(), primaryStage.getHeight());
    double height = primaryStage.getHeight();
    double width = primaryStage.getWidth();
    primaryStage.setScene(scene);
    primaryStage.show();
    
     Register = new Button("Save Character");
     Back = new Button("Go Back");
     
     username = new TextField();
     password = new PasswordField();
     confirmpassword = new PasswordField();
     
     username.setPrefSize(230, 20);
     password.setPrefSize(230, 20);
     confirmpassword.setPrefSize(230, 20);

     Label usernamelbl = new Label("Username:");
     Label passwordlbl = new Label("Password:");
     Label confirmlbl = new Label("Confirm Password:");
     
      AnchorPane.setTopAnchor(usernamelbl, height * 0.38);
      AnchorPane.setLeftAnchor(usernamelbl, width * 0.39);
      
      AnchorPane.setTopAnchor(passwordlbl, height * 0.42);
      AnchorPane.setLeftAnchor(passwordlbl, width * 0.39);
      
      AnchorPane.setTopAnchor(confirmlbl, height * 0.46);
      AnchorPane.setLeftAnchor(confirmlbl, width * 0.35);
        
      AnchorPane.setTopAnchor(username, height * 0.38);
      AnchorPane.setLeftAnchor(username, width * 0.45);
      
      AnchorPane.setTopAnchor(password, height * 0.42);
      AnchorPane.setLeftAnchor(password, width * 0.45);
      
      AnchorPane.setTopAnchor(confirmpassword, height * 0.46);
      AnchorPane.setLeftAnchor(confirmpassword, width * 0.45);

      AnchorPane.setTopAnchor(Register, height * 0.50);
      AnchorPane.setLeftAnchor(Register, width * 0.45);
      
      AnchorPane.setTopAnchor(Back, height * 0.50);
      AnchorPane.setLeftAnchor(Back, width * 0.54);
      
      root.getChildren().add(username);
      root.getChildren().add(password);
      root.getChildren().add(confirmpassword);
      root.getChildren().add(Register);
      root.getChildren().add(Back);
      root.getChildren().add(passwordlbl);
      root.getChildren().add(confirmlbl);
      root.getChildren().add(usernamelbl);

      Register.setOnMouseClicked(new EventHandler<MouseEvent>()
            {

        @Override
        public void handle(MouseEvent event) 
            {
              
                 String INFOusername= username.getText();
                 String INFOpassword = password.getText();
                 String INFOpasswordConfirm = confirmpassword.getText();
                 
                 if(!INFOpassword.equals(INFOpasswordConfirm))
                 {
                 Alert alert = new Alert(Alert.AlertType.ERROR);
                 alert.setTitle("Registration");
                 alert.setHeaderText("Registration error");
                 alert.setContentText("The password and confirm password are not the same");
                 alert.showAndWait();
                 username.setText("");
                 password.setText("");
                 confirmpassword.setText("");
                 }
                 
                 else
                 {
                 Alert alert = new Alert(Alert.AlertType.INFORMATION);
                 alert.setTitle("Registration");
                 alert.setHeaderText("Registration completed");
                 alert.setContentText("You have created a acount with username: "+ " " + INFOusername);
                 alert.showAndWait();
                 
                 Database database = Database.getDatabase();
                 database.RegisterAccount(INFOusername, INFOpassword);
                 
                new LoginFX(primaryStage);
                 }
            }
            
            });
      Back.setOnMouseClicked(new EventHandler<MouseEvent>()
            {

        @Override
        public void handle(MouseEvent event) 
            {
            new LoginFX(primaryStage);
            }
            
            });
    }
    
    
}
