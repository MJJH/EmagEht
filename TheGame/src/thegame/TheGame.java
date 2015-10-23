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
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
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

    private Map play;
    private Player me;
    private Scene scene;
    private int startX;
    private int startY;
    private MapObject obj;
    private boolean repainting;

    private EventHandler<KeyEvent> keyListener = new EventHandler<KeyEvent>() {
        @Override
        public void handle(KeyEvent event)
        {
            float speed = .2f;
            
            if(event.isShiftDown())
                speed = 1;
            
            if (event.getCode() == KeyCode.UP || event.getCode() == KeyCode.DOWN
                    || event.getCode() == KeyCode.RIGHT || event.getCode() == KeyCode.LEFT)
            {
                switch (event.getCode())
                {
                    case UP:
                        me.moveY(speed);
                        System.out.println("UP The player location is: X: " + me.getX() + "Y: " + me.getY());
                        break;
                    case DOWN:
                        me.moveY(-speed);
                        System.out.println("DOWN The player location is: X: " + me.getX() + "Y: " + me.getY());
                        break;
                    case LEFT:
                        me.moveX(-speed);
                        System.out.println("LEFT The player location is: X: " + me.getX() + "Y: " + me.getY());
                        break;
                    case RIGHT:
                        me.moveX(speed);
                        System.out.println("RIGHT The player location is: X: " + me.getX() + "Y: " + me.getY());
                        break;
                }
                event.consume();
            } else if (event.getCode() == KeyCode.SPACE)
            {
                //spring
                event.consume();
            }
        }
    };

    @Override
    public void start(Stage primaryStage)
    {
        play = new Map();
        me = new Player(null, "Dummy", 100, null, null, play.getSpawnX(), play.getSpawnY(), null, 1, 1);

        
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

        repainting = false;
        
        new Thread() {
            @Override
            public void run()
            {
                // Start repaint timer (max 60fps)
                Timer timer = new Timer(1000 / 30, new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent e)
                    {
                        if(!repainting)
                            draw(gc);
                    }
                });
                timer.start();
            }
        }.start();
       
    }

    private void draw(GraphicsContext g)
    {
        repainting = true;
        
        // Get viewables
        List<MapObject> view = viewable();
        view.add(me);
        
        // Clear scene
        clear(g);
        
        int blockHorizontal = (int) Math.ceil(scene.getWidth() / config.block.val) + 3;
        int blockVertical = (int) Math.ceil(scene.getHeight() / config.block.val) + 3;
        
        float dx = 0;
        float dy = 0;
        /*if((startX > 0 || (startX == 0 && me.getX() > blockHorizontal/2 - 0.5))) {
            if(me.getX()%1 >= .5f)
                dx+=config.block.val;
            dx -= (config.block.val * (me.getX() % 1));

        } else {
           dx += config.block.val / 2; 
        }*/
        
        if(startX > 0 && startX < play.getWidth() - blockHorizontal + 1) {
            if(me.getX()%1 >= .5f)
                dx+=config.block.val;
            dx -= config.block.val * (me.getX() % 1);
        } else if(startX == 0 && me.getX() > blockHorizontal / 2 - 0.5) {
            if(me.getX()%1 >= .5f)
                dx+=config.block.val;
            dx -= config.block.val * (me.getX() % 1);
        } else if(startX == play.getWidth() - blockHorizontal + 1 && me.getX() < play.getWidth() - (blockHorizontal + 1) / 2 + 2) {
            if((me.getX() - play.getWidth() - (blockHorizontal + 1) / 2 + 2) % 1 < .5f)
                dx-=config.block.val;
            dx -= config.block.val * ((me.getX() - play.getWidth() - (blockHorizontal + 1) / 2 + 2) % 2);
        } else if(startX == play.getWidth() - blockHorizontal + 1) {
            dx -= config.block.val * 1.5;
        } else if(startX == 0) {
            dx += config.block.val / 2;
        }

        /*if((startY > 0 || (startY == 0 && me.getY() > blockVertical/2 - 0.5))) {
            if(me.getY()%1 >= .5f)
                dy-=config.block.val;
            dy += (config.block.val * (me.getY() % 1));
        } else {
            dy -= config.block.val / 2;
        }*/
        
        if(startY > 0 && startY < play.getHeight()- blockVertical + 1) {
            if(me.getY()%1 >= .5f)
                dy-=config.block.val;
            dy += config.block.val * (me.getY() % 1);
        } else if(startY == 0 && me.getY() > blockVertical / 2 - 0.5) {
            if(me.getY()%1 >= .5f)
                dy-=config.block.val;
            dy += config.block.val * (me.getY() % 1);
        } else if(startY == play.getHeight() - blockVertical + 1 && me.getY() < play.getHeight() - (blockVertical + 1) / 2 + 2) {
            if((me.getY() - play.getHeight() - (blockVertical + 1) / 2 + 2) % 1 < .5f)
                dy+=config.block.val;
            dy += config.block.val * ((me.getY() - play.getHeight() - (blockVertical + 1) / 2 + 2) % 2);
        } else if(startY == play.getHeight() - blockVertical + 1) {
            dy += config.block.val * 1.5;
        } else if(startY == 0) {
            dy -= config.block.val / 2;
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
                g.setFill(Color.RED);
                g.rect(x, y, config.block.val, config.block.val);
                g.fill();
                g.closePath();
            } else
            {
                g.beginPath();
                g.drawImage(draw.getSkin(), x, y);
                g.closePath();
            }
        }
        
        repainting = false;
    }

    private void clear(GraphicsContext g)
    {
        g.clearRect(0, 0, scene.getWidth(), scene.getHeight());
    }

    private List<MapObject> viewable()
    {
        int blockHorizontal = (int) Math.ceil(scene.getWidth() / config.block.val) + 3;
        int blockVertical = (int) Math.ceil(scene.getHeight() / config.block.val) + 3;

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
