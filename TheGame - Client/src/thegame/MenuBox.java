/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thegame;

import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

/**
 *
 * @author robin
 */
    public class MenuBox extends VBox {

        public MenuBox(MenuItem... items)
        {
            getChildren().add(createSeparator());

            for (MenuItem item : items)
            {
                getChildren().addAll(item, createSeparator());
            }
        }

        private Line createSeparator()
        {
            Line sep = new Line();
            sep.setEndX(200);
            sep.setStroke(Color.DARKGREY);
            return sep;
        }
    }
