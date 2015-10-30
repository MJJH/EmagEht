/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thegame;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import thegame.com.Game.Map;
import thegame.com.Game.Objects.Characters.Player;
import thegame.com.Game.Objects.MapObject;

/**
 *
 * @author laurens
 */
public class TheGame extends Application {

    private Map play;
    private Player me;
    private Scene scene;
    private int startX;
    private int startY;
    private List<KeyCode> keys = new ArrayList<>();

    private EventHandler<KeyEvent> keyListener = new EventHandler<KeyEvent>() {
        @Override
        public void handle(KeyEvent event)
        {
            if (event.getCode() == KeyCode.UP || event.getCode() == KeyCode.RIGHT || event.getCode() == KeyCode.LEFT)
            {
                if(event.getEventType() == KeyEvent.KEY_PRESSED && !keys.contains(event.getCode())){
                    keys.add(event.getCode());
                    
                    
                    if(event.getCode() == KeyCode.LEFT && keys.contains(KeyCode.RIGHT))
                        keys.remove(KeyCode.RIGHT);
                    if(event.getCode() == KeyCode.RIGHT && keys.contains(KeyCode.LEFT))
                        keys.remove(KeyCode.LEFT);
                } else
                    keys.remove(event.getCode());
            } 
            
        }
    };
    

    @Override
    public void start(Stage primaryStage)
    {
        play = new Map();
        play.generateMap();
        me = new Player(null, "Dummy", 100, null, null, play.getSpawnX(), play.getSpawnY(), null, 1, 1, play);
        play.addObject(me);

        
        StackPane root = new StackPane();

        scene = new Scene(root, 1400, 800, Color.LIGHTBLUE);
        scene.addEventHandler(KeyEvent.ANY, keyListener);

        final Canvas canvas = new Canvas(scene.getWidth(), scene.getHeight()); 
        GraphicsContext gc = canvas.getGraphicsContext2D();
        
        scene.widthProperty().addListener(new ChangeListener<Number>() {
            @Override 
            public void changed(ObservableValue<? extends Number> observableValue, Number oldSceneWidth, Number newSceneWidth) {
                canvas.setWidth((double) newSceneWidth);
            }
        });
        scene.heightProperty().addListener(new ChangeListener<Number>() {
            @Override 
            public void changed(ObservableValue<? extends Number> observableValue, Number oldSceneHeight, Number newSceneHeight) {
                canvas.setHeight((double) newSceneHeight);
            }
        });

        root.getChildren().add(canvas);
        primaryStage.setScene(scene);
        primaryStage.show();

        primaryStage.setOnCloseRequest(event ->
        {
            System.exit(0);
        });

        
        AnimationTimer loop = new AnimationTimer() {

            @Override
            public void handle(long now) {
                draw(gc);
            }
        };
        loop.start();
        
        Timer update = new Timer();
        update.schedule(new TimerTask() {

            @Override
            public void run() {
                if(keys.contains(KeyCode.LEFT))
                    me.walkLeft();
                else if(keys.contains(KeyCode.RIGHT))
                    me.walkRight();
                
                if(keys.contains(KeyCode.UP))
                    me.Jump();
                
                me.update();
            }
        }, 0, 1000/60);
       
    }

    private void draw(GraphicsContext g)
    {
        // Get viewables
        List<MapObject> view = viewable();
        
        // Clear scene
        clear(g);
        
        int blockHorizontal = (int) Math.ceil(scene.getWidth() / config.block.val) + 3;
        int blockVertical = (int) Math.ceil(scene.getHeight() / config.block.val) + 3;
        
        float dx = 0;
        float dy = 0;
        
        
        
        dx += config.block.val * (me.getW() / 2);
        if(me.getX() + me.getW() / 2 >= Math.floor(startX + (blockHorizontal)/2)-me.getW()/2 && me.getX() + me.getW() / 2 <= Math.ceil(startX + (blockHorizontal)/2)) {
            if(me.getX() % 1 >= me.getW() / 2)
                dx += config.block.val;
            dx -= config.block.val * (me.getX() % 1);
        } else if(me.getX() + me.getW() / 2 >= Math.floor(startX + (blockHorizontal)/2) && me.getX() + me.getW() / 2 <= Math.ceil(startX + (blockHorizontal)/2)+1) {
            if(me.getX() % 1 >= me.getW() / 2)
                dx += config.block.val;
            dx -= config.block.val * (me.getX() % 1);
            dx -= config.block.val;
        } else if(me.getX() + me.getW() / 2 > Math.floor(startX + (blockHorizontal)/2) + 1) {
            dx -= config.block.val*2;
        }
        
        dy -= config.block.val * (me.getH() / 2);
        if(me.getY() + me.getH() / 2 >= Math.floor(startY + (blockVertical)/2)-me.getH()/2 && me.getY() + me.getH() / 2 <= Math.ceil(startY + (blockVertical)/2)) {
            if(me.getY() % 1 >= me.getH() / 2)
                dy -= config.block.val;
            dy += config.block.val * (me.getY() % 1);
        } else if(me.getY() + me.getH() / 2 >= Math.floor(startY + (blockVertical)/2) && me.getY() + me.getH() / 2 <= Math.ceil(startY + (blockVertical)/2)+1) {
            if(me.getY() % 1 >= me.getH() / 2)
                dy -= config.block.val;
            dy += config.block.val * (me.getY() % 1);
            dy += config.block.val;
        } else if(me.getY() + me.getH() / 2 > Math.floor(startY + (blockVertical)/2) + 1) {
            dy += config.block.val*2;
        }

        
        
        for (MapObject draw : view)
        {
            float x = (draw.getX() - startX - me.getW()/2) * config.block.val;
            float y = ((float)scene.getHeight() - (draw.getY() - startY + 1 - me.getH()/2) * config.block.val);
            
            x += dx;
            y += dy;
            

            if (draw instanceof Player)
            {
                g.beginPath();
                g.setFill(Color.BLACK);
                g.rect(x, y, config.block.val * ((Player) draw).getW(), config.block.val * ((Player) draw).getH());
                g.fill();
                g.closePath();
            } else
            {
                g.beginPath();
                g.drawImage(draw.getSkin(), x, y);
                g.closePath();
            }
        }
    }

    private void clear(GraphicsContext g)
    {
        g.clearRect(0, 0, scene.getWidth(), scene.getHeight());
    }

    private List<MapObject> viewable()
    {
        int blockHorizontal = (int) Math.ceil(scene.getWidth() / config.block.val) + 4;
        int blockVertical = (int) Math.ceil(scene.getHeight() / config.block.val) + 4;

        int midX = (int) Math.floor(me.getX() + (me.getW() / 2));
        int midY = (int) Math.ceil(me.getY() - (me.getH() / 2));

        startX = (int) Math.round(midX - Math.floor((blockHorizontal - 1) / 2));
        startY = (int) Math.round(midY - Math.floor((blockVertical - 1) / 2));
        int endX = (int) (midX + Math.ceil((blockHorizontal - 1) / 2));
        int endY = (int) (midY + Math.ceil((blockVertical - 1) / 2));

        while (startX < 0)
        {
            startX++;
            endX++;
        }
        while (startY < 0)
        {
            startY++;
            endY++;
        }
        while (endX > play.getWidth())
        {
            startX--;
            endX--;
        }
        while (endY > play.getWidth())
        {
            startY--;
            endY--;
        }

        if (startX < 0 && endX > play.getWidth())
        {
            startX = 0;
            endX = play.getWidth();
        }
        if (startY < 0 && endY > play.getHeight())
        {
            startY = 0;
            endY = play.getHeight();
        }

        return play.getObjects(startX, startY, endX, endY);
    }
    
    public Player getPlayer()
    {
        return me;
    }
    
    public Map getmap()
    {
        return play;
    }
    
    public TheGame getgame()
    {
        return this;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        launch(args);
    }
}
