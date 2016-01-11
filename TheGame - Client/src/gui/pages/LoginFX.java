/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.pages;

import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author laure
 */
public class LoginFX {

    Stage primaryStage;

    public LoginFX(Stage primaryStage)
    {
        this.primaryStage = primaryStage;
        primaryStage.setTitle("Menu");
        primaryStage.setScene(createLogin());
        primaryStage.show();
    }
    
    public Scene createLogin()
    {
        return null;
    }
}
