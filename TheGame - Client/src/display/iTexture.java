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
public interface iTexture {

    public int getWidth();

    public int getHeight();

    public final String path = "src/resources//mapping.png";

    public enum Part {

        HEAD, TORSO, BACKARM, FRONTARM, BACKLEG, FRONTLEG
    }

    public enum Type {

        BODY, ARMOR, TOOL, BLOCK
    }
}
