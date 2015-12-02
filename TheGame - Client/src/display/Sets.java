/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package display;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Martijn
 */
public enum Sets implements iTexture {
    // Body
    player(new ArrayList<CombineParts>() { 
        { 
            add(new CombineParts(Parts.playerBackLeg, 8, 28));
            add(new CombineParts(Parts.playerBackArm, 16, 12));
            add(new CombineParts(Parts.playerTorso, 4, 12));
            add(new CombineParts(Parts.playerFrontLeg, 3, 28));
            add(new CombineParts(Parts.playerFrontArm, 0, 12));
            add(new CombineParts(Parts.playerHead, 4, 0)); 
        } 
    }),
    
    // Clothes
    featherHelmet(new ArrayList<CombineParts>() {
        {
            add(new CombineParts(Parts.helmetEyes, 11, 8));
            add(new CombineParts(Parts.helmetFeather, 0, 0));
        } 
    }),
    
    SpikeHelmet(new ArrayList<CombineParts>() {
        {
            add(new CombineParts(Parts.helmetOpen, 0, 10));
            add(new CombineParts(Parts.helmetHorn, 6, 0));
        } 
    }),
    
    tShirt(new ArrayList<CombineParts>() {
        {
            add(new CombineParts(Parts.tShirtBack, 18, 1));
            add(new CombineParts(Parts.tShirtBody, 4, 0));
            add(new CombineParts(Parts.tShirtFront, 0, 0));
        } 
    }),
    
    bodyArmor(new ArrayList<CombineParts>() {
        {
            add(new CombineParts(Parts.armorBody, 6, 2));
            add(new CombineParts(Parts.armorShoulderBack, 17, 1));
            add(new CombineParts(Parts.armorShoulderFront, 0, 0));
            add(new CombineParts(Parts.armorWristBack, 20, 9));
            add(new CombineParts(Parts.armorWristFront, 1, 11));
        }
    }),
    
    shorts(new ArrayList<CombineParts>() {
        {
            add(new CombineParts(Parts.shortTop, 0, 0));
            add(new CombineParts(Parts.shortBack, 6, 2));
            add(new CombineParts(Parts.shortFront, 0, 2));
        }
    }),
    
    legArmor(new ArrayList<CombineParts>() {
        {
            add(new CombineParts(Parts.armorTop, 1, 0));
            add(new CombineParts(Parts.armorBack, 9, 2));
            add(new CombineParts(Parts.armorFront, 1, 2));
            add(new CombineParts(Parts.armorShoeBack, 6, 8));
            add(new CombineParts(Parts.armorShoeFront, 0, 8));
        }
    });
    
    private final int height;
    private final int width;
    public final List<CombineParts> parts;
    
    
    Sets(List<CombineParts> parts) {
        this.parts = parts;
        int h = 0;
        int w = 0;
        for(CombineParts cp : parts) {
            if(cp.x + cp.part.getWidth() > w)
                w = cp.x + cp.part.getWidth();
            if(cp.y + cp.part.getHeight() > h)
                h = cp.y + cp.part.getHeight();
        }
        this.height = h;
        this.width = w;
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }
}
