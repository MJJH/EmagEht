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
public class CombineParts {

    public final Parts part;
    public final int x;
    public final int y;

    public CombineParts(Parts p, int x, int y)
    {
        part = p;
        this.x = x;
        this.y = y;
    }
}
