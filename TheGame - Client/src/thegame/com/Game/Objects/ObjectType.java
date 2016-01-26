/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thegame.com.Game.Objects;

import display.Image;
import display.Sets;
import display.Skin;
import java.io.IOException;
import java.io.Serializable;
import javafx.scene.paint.Color;

/**
 *
 * @author Martijn
 */
public abstract class ObjectType implements Serializable {

    private static final long serialVersionUID = 6522685098267700690L;

    public String name;
    public Sets texture;
    public Color[] colorset;
    public transient Skin skin;

    public ObjectType(String name, Sets t, Color[] c) throws IOException
    {
        texture = t;
        colorset = c;
        this.name = name;
        this.skin = new Image(t) {
            {
                recolour(c);
            }
        };
    }

    public String getName()
    {
        return name;
    }

    protected Skin getSkin() throws IOException
    {
        return skin;
    }

}
