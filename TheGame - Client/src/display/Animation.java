/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package display;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Martijn
 */
public class Animation extends Skin {
    private List<Image> frames;
    private int current;
    
    private int length;
    private int counter;
    
    public Animation(List<Image> frames, int frameLength) {
        this.frames = frames;
        current = 0;
        length = frameLength;
        
        if(!frames.isEmpty()) {
            this.height = frames.get(0).getHeight();
            this.width = frames.get(0).getWidth();
        }
    }
    
    public Animation(int frameLength) {
        this.frames = new ArrayList<>();
        current = 0;
        length = frameLength;
    }
    
    public void removeFrame(int frame) {
        frames.remove(frame);
        current = 0;
    }
    
    public void addFrame(Image frame) {
        frames.add(frame);
        current = 0;
        
        if(!frames.isEmpty()) {
            this.height = frames.get(0).getHeight();
            this.width = frames.get(0).getWidth();
        }
    }
    
    public void addFrame(Image frame, int position) {
        frames.add(position, frame);
        current = 0;
        
        if(!frames.isEmpty()) {
            this.height = frames.get(0).getHeight();
            this.width = frames.get(0).getWidth();
        }
    }
    
    @Override
    public javafx.scene.image.Image show() {
        counter++;
        
        if(counter > length) {
            counter = 0;
            current++;
            if(current > this.frames.size() - 1)
                current = 0;
            }
        return this.frames.get(current).show();
    }
}
