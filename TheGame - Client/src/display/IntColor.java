/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package display;

import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.scene.paint.Color;

/**
 *
 * @author Martijn
 */
public class IntColor {

    public static Color rgb(int red, int green, int blue)
    {
        float r = red / 255.00f;
        float g = green / 255.00f;
        float b = blue / 255.00f;

        return new Color(r, g, b, 1);
    }

    public static Color rgba(int red, int green, int blue, double opacity)
    {
        float r = red / 255.00f;
        float g = green / 255.00f;
        float b = blue / 255.00f;

        return new Color(r, g, b, opacity);
    }

    public static Color[] fromDB(String color, thegame.com.storage.Database db) throws SQLException 
    {
        // Get color ints
        String colorQuery = "SELECT Index_0, Index_1, Index_2, Index_3, Index_4, Index_5, Index_6, Index_7 FROM color_set WHERE Name = '"+color+"'";
        ResultSet rs = db.executeUnsafeQuery(colorQuery);

        rs.first();
        
        Color[] c = new Color[7];
        for(int i=1; i <= 7; i++)
        {
            int d = rs.getInt(i);
            if(!rs.wasNull()) {
                java.awt.Color col = new java.awt.Color(rs.getInt(i));
                c[i] = IntColor.rgba(col.getRed(), col.getGreen(), col.getBlue(), col.getAlpha() / 255.00f);
            }
        }
        return c;
    }
}
