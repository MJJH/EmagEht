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
public class CombineParts implements Comparable<CombineParts> {

    public final Parts part;
    public final int x;
    public final int y;
    public final int order;

    public CombineParts(Parts p, int index, int x, int y, int order)
    {
        part = p;
        this.x = x;
        this.y = y;
        this.order = order;
    }

    @Override
    public int compareTo(CombineParts o) {
        return this.order - o.order;
    }
}
