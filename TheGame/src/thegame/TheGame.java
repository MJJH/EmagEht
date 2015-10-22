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

    private EventHandler<KeyEvent> keyListener = new EventHandler<KeyEvent>() {
        @Override
        public void handle(KeyEvent event)
        {
            if (event.getCode() == KeyCode.UP || event.getCode() == KeyCode.DOWN
                    || event.getCode() == KeyCode.RIGHT || event.getCode() == KeyCode.LEFT)
            {
                switch (event.getCode())
                {
                    case UP:
                        me.moveY(1);
                        System.out.println("UP The player location is: X: " + me.getX() + "Y: " + me.getY());
                        break;
                    case DOWN:
                        me.moveY(-1);
                        System.out.println("DOWN The player location is: X: " + me.getX() + "Y: " + me.getY());
                        break;
                    case LEFT:
                        me.moveX(-1);
                        System.out.println("LEFT The player location is: X: " + me.getX() + "Y: " + me.getY());
                        break;
                    case RIGHT:
                        me.moveX(1);
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
        me = new Player(null, "Dummy", 100, null, null, 20, 10, null, 1, 1);

        StackPane root = new StackPane();

        scene = new Scene(root, 1400, 800, Color.LIGHTBLUE);
        scene.addEventHandler(KeyEvent.ANY, keyListener);

        final Canvas canvas = new Canvas(scene.getWidth(), scene.getHeight());
        GraphicsContext gc = canvas.getGraphicsContext2D();

        root.getChildren().add(canvas);
        primaryStage.setScene(scene);
        primaryStage.show();

        primaryStage.setOnCloseRequest(event ->
        {
            System.exit(0);
        });

        new Thread() {
            @Override
            public void run()
            {
                // Start repaint timer (max 60fps)
                Timer timer = new Timer(1000 / 60, new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent e)
                    {
                        draw(gc);
                    }
                });
                timer.start();
            }
        }.start();

        /*
         new Thread() {
         @Override
         public void run()
         {
         // Start repaint timer (max 60fps)
         Timer timer = new Timer(1000 / 30, new ActionListener() {

         @Override
         public void actionPerformed(ActionEvent e)
         {
         //me.testMove();
         }
         });
         timer.start();
         }
         }.start();
         */
    }

    private void draw(GraphicsContext g)
    {
        // Get viewables
        List<MapObject> view = viewable();
        view.add(me);
        
        // Clear scene
        clear(g);
        
        for (MapObject draw : view)
        {
            // float x = (draw.getX() - view.get(0).getX()) * config.block.val - (me.getW() / 2) * config.block.val;
            // float y = (float) (scene.getHeight() - (draw.getY() - view.get(0).getY() + 1) * config.block.val - (me.getH() / 2) * config.block.val);
            
            float x = (draw.getX() - startX) * config.block.val - (me.getW() / 2) * config.block.val;
            float y = (float) (scene.getHeight() - (draw.getY() - startY + 1) * config.block.val - (me.getH() / 2) * config.block.val);

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
    }

    private void clear(GraphicsContext g)
    {
        g.clearRect(0, 0, scene.getWidth(), scene.getHeight());
    }

    private List<MapObject> viewable()
    {
        int blockHorizontal = (int) Math.ceil(scene.getWidth() / config.block.val) + 1;
        int blockVertical = (int) Math.ceil(scene.getHeight() / config.block.val) + 1;

        int midX = (int) Math.floor(me.getX() + (me.getW() / 2));
        int midY = (int) Math.ceil(me.getY() - (me.getH() / 2));

        startX = (int) (midX - Math.floor((blockHorizontal - 1) / 2));
        startY = (int) (midY - Math.floor((blockVertical - 1) / 2));
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

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        launch(args);
    }
}
