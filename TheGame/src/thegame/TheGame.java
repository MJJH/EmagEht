/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thegame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javax.swing.Timer;
import thegame.com.Game.Map;
import thegame.com.Game.Objects.Block;
import thegame.com.Game.Objects.Characters.Player;
import thegame.com.Game.Objects.MapObject;

/**
 *
 * @author laurens
 */
public class TheGame extends Application {

    Map play;
    Player me;
    Scene scene;
    
    @Override
    public void start(Stage primaryStage)
    {
        play = new Map();
        me = new Player(null, "Dummy", 100, null, null, 20, 10, null, 1, 1);
        
        StackPane root = new StackPane();
        
        scene = new Scene(root, 800, 400, Color.LIGHTBLUE);
        
        final Canvas canvas = new Canvas(scene.getWidth(), scene.getHeight());
        GraphicsContext gc = canvas.getGraphicsContext2D();
        
        root.getChildren().add(canvas);
        primaryStage.setScene(scene);
        primaryStage.show();
        
        new Thread() {
            @Override
            public void run() {
                // Start repaint timer (max 60fps)
                Timer timer = new Timer(1000/60, new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent e) {
                        draw(gc);
                    }
                });
                timer.start();
            }
        }.start();
        
        new Thread() {
            @Override
             public void run() {
                // Start repaint timer (max 60fps)
                Timer timer = new Timer(1000/30, new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent e) {
                        me.testMove();
                    }
                });
                timer.start();
            }
        }.start();
    }
    
    private void draw(GraphicsContext g) {
        // Clear scene
        clear(g);
        
        // Get viewables
        List<MapObject> view = viewable();
        view.add(me);
        
        for(MapObject draw : view) {
            float x = (draw.getX() - view.get(0).getX()) * config.block.val - (me.getW() / 2) * config.block.val;
            float y = (float) (scene.getHeight() - (draw.getY() - view.get(0).getY() + 1) * config.block.val - (me.getH() / 2) * config.block.val);
            
            if(draw instanceof Player){
                g.setFill(Color.RED);
                g.rect(x, y, config.block.val, config.block.val);
                g.fill();
            } else {
                g.drawImage(draw.getSkin(), x, y);
            }
        }
    }
    
    private void clear(GraphicsContext g) {
        g.clearRect(0, 0, scene.getWidth(), scene.getHeight());
    }
    
    private List<MapObject> viewable() {
        int blockHorizontal = (int) Math.ceil(scene.getWidth() / config.block.val) + 1;
        int blockVertical = (int) Math.ceil(scene.getHeight() / config.block.val) + 1;
        
        int midX = (int) Math.floor(me.getX() + (me.getW() / 2));
        int midY = (int) Math.ceil(me.getY() - (me.getH() / 2));
        
        int startX = (int) (midX - Math.floor((blockHorizontal - 1) / 2));
        int startY = (int) (midY - Math.floor((blockVertical - 1) / 2));
        int endX   = (int) (midX + Math.ceil((blockHorizontal - 1) / 2));
        int endY   = (int) (midY + Math.ceil((blockVertical - 1) / 2));
        
        while(startX < 0) { startX++; endX++; }
        while(startY < 0) { startY++; endY++; }
        while(endX > play.getWidth()) { startX--; endX--; }
        while(endY > play.getWidth()) { startY--; endY--; }
        
        if(startX < 0 && endX > play.getWidth()) {
            startX = 0;
            endX = play.getWidth();
        }
        if(startY < 0 && endY > play.getHeight()) {
            startY = 0;
            endY = play.getHeight();
        }
        
        return play.getObjects(startX, startY, endX, endY);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        launch(args);
    }

}
