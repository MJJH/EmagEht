/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package display;

import display.iTexture.Part;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Martijn
 */
public class Animation extends Skin {
    private Image image;
    private List<Map<Parts, Integer>> frames;
    
    private int current;
    private int length;
    
    public Animation(Image i) {
        this.image = i;
        this.height = i.getHeight();
        this.width = i.getWidth();
    }
    
    public void addFrame(Map<Parts, Integer> frame) {
        frames.add(frame);
    }
    
    public void addFrameByPart(Map<Part, Integer> frame) {

    }
    
    @Override
    public javafx.scene.image.Image show() {
        return image.show();
    }
}
