/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package display;

import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

/**
 *
 * @author Martijn
 */
public class PartImage {

    public WritableImage image;
    public Color[] recolors;
    public int x;
    public int y;

    public boolean hFlip = false;
    public boolean vFlip = false;

    public PartImage(WritableImage i, int x, int y)
    {
        this.image = i;
        this.x = x;
        this.y = y;
        this.recolors = new Color[7];
    }
}
