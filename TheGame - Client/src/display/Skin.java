/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package display;

/**
 *
 * @author Martijn
 */
public abstract class Skin{
    protected int height;
    protected int width;
    
    public abstract javafx.scene.image.Image show();
    
    public int getHeight() { 
        return height;
    }
    
    public int getWidth() {
        return width;
    }
}
