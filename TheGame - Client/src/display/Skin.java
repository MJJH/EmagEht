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
public abstract class Skin implements Cloneable {

    protected int height;
    protected int width;
    protected int offsetTop;
    protected int offsetLeft;
    protected int offsetRight;
    protected int offsetBottom;

    public abstract javafx.scene.image.Image show();

    public int getHeight()
    {
        return height;
    }

    public int getWidth()
    {
        return width;
    }

    public void setHeight(int height)
    {
        this.height = height;
    }

    public void setWidth(int width)
    {
        this.width = width;
    }
    
    public int[] getOffset() {
        return new int[] { offsetTop, offsetLeft, offsetBottom, offsetRight };
    }

    @Override
    public Skin clone() throws CloneNotSupportedException
    {
        return (Skin) super.clone();
    }
}
