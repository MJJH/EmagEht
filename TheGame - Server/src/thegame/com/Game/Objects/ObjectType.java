/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thegame.com.Game.Objects;

import java.io.Serializable;
import javafx.scene.control.Skin;
import javafx.scene.paint.Color;

/**
 *
 * @author Martijn
 */
public abstract class ObjectType implements Serializable {
    private static final long serialVersionUID = 6522685098267700690L;
    
    public final String name;

    public ObjectType(String name) {
        this.name = name;
    }
    
}
