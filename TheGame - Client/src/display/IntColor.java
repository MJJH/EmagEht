/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package display;

import javafx.scene.paint.Color;

/**
 *
 * @author Martijn
 */
public class IntColor {
    
    public static Color rgb(int red, int green, int blue) {
        float r = red/255.00f;
        float g = green/255.00f;
        float b = blue/255.00f; 
        
        return new Color(r, g, b, 1);
    }
    
    public static Color rgba(int red, int green, int blue, double opacity) {
        float r = red/255.00f;
        float g = green/255.00f;
        float b = blue/255.00f; 
        
        return new Color(r, g, b, opacity);
    }
}
