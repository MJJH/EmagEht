/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thegame;

import display.Skin;
import java.io.IOException;
import javafx.scene.Scene;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import thegame.com.Game.Map;
import thegame.com.Game.Objects.Characters.Enemy;
import thegame.com.Game.Objects.Characters.Player;
import thegame.com.Game.Objects.MapObject;
import javafx.application.Application;
import javafx.beans.value.ObservableValue;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import thegame.com.Game.Objects.Block;
import thegame.com.Game.Objects.BlockType;
import thegame.com.Game.Objects.Characters.CharacterGame;
import thegame.com.Menu.Account;
import thegame.com.Menu.Message;
import thegame.shared.iGameLogic;

/**
 *
 * @author laurens
 */
public class TheGame extends Application {

    private Account myAccount;
    private Map play;
    private Player me;
    private Scene scene;
    private Image img;
    // SERVER
    private Registry server;
    private UpdateListener listener;
    public iGameLogic gameLogic;

    private List<KeyCode> keys = new ArrayList<>();

    // MAP
    private float dx;
    private float dy;
    private int startX;
    private int startY;
    private int offsetBlocks;

    // FPS
    private final long ONE_SECOND = 1000000000;
    private long currentTime = 0;
    private long lastTime = 0;
    private int fps = 0;
    private double delta = 0;
    
    //SJET
    private boolean sjeton = false;

    private final EventHandler<KeyEvent> keyListener = (KeyEvent event) ->
    {
        if (event.getCode() == KeyCode.W || event.getCode() == KeyCode.D || event.getCode() == KeyCode.A)
        {
            if (event.getEventType() == KeyEvent.KEY_PRESSED && !keys.contains(event.getCode()))
            {
                keys.add(event.getCode());

                if (event.getCode() == KeyCode.A && keys.contains(KeyCode.D))
                {
                    keys.remove(KeyCode.D);
                }
                if (event.getCode() == KeyCode.D && keys.contains(KeyCode.A))
                {
                    keys.remove(KeyCode.A);
                }
            } else if (event.getEventType() == KeyEvent.KEY_RELEASED)
            {
                keys.remove(event.getCode());
                if (event.getCode() == KeyCode.W)
                {
                    me.stopJump();
                }
            }
        } else
        {
            try
            {
                if (event.getCode() == KeyCode.DIGIT1 && event.getEventType() == KeyEvent.KEY_PRESSED)
                {
                    gameLogic.addObject(new Enemy("Loser", 100, null, play.getSpawnX() + 5, play.getSpawnY(), null, 1, 1, null));
                }
                if (event.getCode() == KeyCode.DIGIT2 && event.getEventType() == KeyEvent.KEY_PRESSED)
                {
                    float x = Math.round(me.getX());
                    float y = Math.round(me.getY()) - 1;
                    Block block = new Block(BlockType.Dirt, x, y, play);
                    gameLogic.addObject(block);
                }
                if (event.getCode() == KeyCode.ENTER && event.getEventType() == KeyEvent.KEY_RELEASED)
                {
                    Message chatMessage = new Message(myAccount, "test");
                    gameLogic.sendMessage(chatMessage);
                }
                if( event.getCode() == KeyCode.T && event.getEventType() == KeyEvent.KEY_PRESSED)
                {
                    sjeton = !sjeton;
                }
            } catch (RemoteException e)
            {
                System.out.println(e.getMessage());
            }
        }
    };

    private final EventHandler<MouseEvent> mouseListener = (MouseEvent event) ->
    {
        if (event.getButton().equals(MouseButton.PRIMARY))
        {
            double clickX = (event.getSceneX() + dx) / config.block.val - startX;

            double clickY = (scene.getHeight() - event.getSceneY() + dy) / config.block.val - startY;

            me.useTool((float) clickX, (float) clickY, gameLogic);
            play.addToUpdate(me);
        }
    };
    private Stage stages;

   @Override
    public void start(Stage primaryStage) throws IOException
    {
        Scene scene = new Scene(createContent());
        primaryStage.setTitle("Menu");
        primaryStage.setScene(scene);
        primaryStage.show();
        stages = primaryStage;
        
        InputStream hartje = Files.newInputStream(Paths.get("src/resources//Hearts.png"));
        
        img = new Image(hartje);     

    }

    private void draw(GraphicsContext g) throws IOException
    {
       
        float pX = me.getX();
        float pY = me.getY();
        float pW = me.getW();
        float pH = me.getH();

        int playH = play.getHeight();
        int playW = play.getWidth();

        // Get viewables
        List<MapObject> view = viewable();

        // Clear scene
        clear(g);

        // Get amount of blocks that fit on screen
        int blockHorizontal = (int) Math.ceil(scene.getWidth() / config.block.val) + offsetBlocks;
        int blockVertical = (int) Math.ceil(scene.getHeight() / config.block.val) + offsetBlocks;

        // preset Delta values
        dx = 0;
        dy = 0;

         // once the player's center is on the middle
        if ((pX + pW / 2) * config.block.val > scene.getWidth() / 2 && (pX + pW / 2) * config.block.val < playW * config.block.val - scene.getWidth() / 2)
        {
            // DX will be the center of the map
            dx = (pX + pW / 2) * config.block.val;
            dx -= (scene.getWidth() / 2);

            // If you are no longer on the right side of the map
            if (startX >= 0)
            {
                dx += (startX) * config.block.val;
            }
        } else if ((pX + pW / 2) * config.block.val >= playW * config.block.val - scene.getWidth() / 2)
        {
            // Dit is het goede moment. Hier moet iets gebeuren waardoor de aller laatste blok helemaal rechts wordt getekent en niet meer beweegt
            dx = (playW - blockHorizontal + 2) * config.block.val * 2;
        }

        // once the player's center is on the middle
        if ((pY - pH / 2) * config.block.val > scene.getHeight() / 2
                && (pY - pH / 2) * config.block.val < playH * config.block.val - scene.getHeight() / 2)
        {
            // DX will be the center of the map
            dy = (pY - pH / 2) * config.block.val;
            dy -= (scene.getHeight() / 2);

            // If you are no longer on the right side of the map
            if (startX >= 0)
            {
                dy += (startY) * config.block.val;
            }
        } else if ((pY - pH / 2) * config.block.val >= playH * config.block.val - scene.getHeight() / 2)
        {
            // Dit is het goede moment. Hier moet iets gebeuren waardoor de aller laatste blok helemaal rechts wordt getekent en niet meer beweegt
            dy = (playH - blockVertical + 2) * config.block.val * 2;
        }
        
        
        
        for (MapObject draw : view)
        {
            float x;
            float y;
            if (!draw.equals(me))
            {
                 
                x = (draw.getX() + startX) * config.block.val - dx;
                y = ((float) scene.getHeight() - (draw.getY() + startY) * config.block.val) + dy;
            } else
            {
                
                x = (pX + startX) * config.block.val - dx;
                y = ((float) scene.getHeight() - (pY + startY) * config.block.val) + dy;
            }
       
           
           if(draw instanceof CharacterGame && draw != me)
           {
           CharacterGame character = (CharacterGame) draw;
           g.strokeRect(x - 3, y - 10, 24.0f, 5.0f);
           g.setFill(Color.RED);
           g.fillRect(x -2 , y - 9, 22.0f , 3.0f);
           g.setFill(Color.GREEN);
           int hp = character.getHP(); 
           int maxhp= character.getMaxHP();
           double breedte = (double) hp/maxhp;
           breedte = breedte * 22;
           g.fillRect(x -2 , y - 9, breedte , 3.0f);

           }
     

            g.beginPath();

            if (draw.getSkin() == null)
            {
                draw.createSkin();
            }

            Skin s = draw.getSkin();

            if (s == null)
            {
                g.setFill(Color.RED);
                g.fillRect(x, y, draw.getW() * config.block.val, draw.getH() * config.block.val);
            } else
            {
                float divX = ((draw.getW() * config.block.val) - s.getWidth()) / 2;
                float divY = ((draw.getH() * config.block.val) - s.getHeight());
                /*g.setFill(Color.GREEN);
                 g.fillRect(x, y, draw.getW() * config.block.val, draw.getH() * config.block.val);*/

                g.drawImage(s.show(), x + divX, y + divY, s.getWidth(), s.getHeight());
            }
        
        
        g.closePath();
       
        }
        
            //draw black background
            Color c=new Color(0f,0f,0f,.1f );

            g.setFill(c);
            g.fillRect(scene.getWidth() - 123.5, scene.getY() -4  , 108f, 60.0f);

             //draw hearth
            int teamlevens = 4;
            for (int i = 0; i < teamlevens; i++) 
            {
            //g.drawImage(img, ((scene.getWidth() - 135) +(25 * i)), 32 ,87,92);
            g.drawImage(img, ((scene.getWidth() - 134) +(25 * i)), scene.getY() + 15, 87,92); 


            } 
             
             
             g.setFill(Color.WHITE);
             g.fillText("Team Lifes", scene.getWidth() - 105, scene.getY() + 10);
            
            g.beginPath();
            //g.strokeRect(scene.getWidth() - 120, scene.getY() - 6, 102.0f, 13.0f);
            g.strokeRect(scene.getWidth() - 120, scene.getY() + 39, 102.0f, 13.0f);

            g.setFill(Color.RED);
            //g.fillRect(scene.getWidth() - 119 , scene.getY() - 5, 100.0f , 11.0f);
            g.fillRect(scene.getWidth() - 119 , scene.getY() + 40, 100.0f , 11.0f);

            g.setFill(Color.GREEN);
            int hp = me.getHP(); 
            int maxhp= me.getMaxHP();
            double breedte = (double) hp/maxhp;
            breedte = breedte * 100;
           // g.fillRect(scene.getWidth() - 119 , scene.getY() - 5, breedte , 11.0f);
            g.fillRect(scene.getWidth() - 119 , scene.getY() + 40, breedte , 11.0f);

            g.closePath(); 
            

        

        /*
         // Calibration lines
         g.setLineWidth(1);
         g.setStroke(Color.rgb(0, 0, 0, 0.2));
         g.strokeLine(scene.getWidth() / 2, 0, scene.getWidth() / 2, scene.getHeight());
         g.strokeLine(0, scene.getHeight()/ 2, scene.getWidth(), scene.getHeight() / 2);
         */
    }

    private void clear(GraphicsContext g)
    {
        g.clearRect(0, 0, scene.getWidth(), scene.getHeight());
    }

    private List<MapObject> viewable()
    {
        // Get amount of blocks that need te be loaded 
        int blockHorizontal = (int) Math.ceil(scene.getWidth() / config.block.val) + offsetBlocks;
        int blockVertical = (int) Math.ceil(scene.getHeight() / config.block.val) + offsetBlocks;

        // Calculate the mid position of the player
        int midX = (int) Math.floor(me.getX() + (me.getW() / 2));
        int midY = (int) Math.ceil(me.getY() - (me.getH() / 2));

        // Calculate at what block we should start drawing (the player object should be centered)
        startX = (int) Math.ceil(midX - blockHorizontal / 2);
        startY = (int) Math.ceil(midY - blockVertical / 2);
        // And what will the ending blocks be
        int endX = startX + blockHorizontal;
        int endY = startY + blockVertical;

        // If we are on the left side on the map, draw the map more to the right
        while (startX < 0)
        {
            startX++;
            endX++;
        }
        // If we are to the top side of the map, draw the map more to the bottom
        while (startY < 0)
        {
            startY++;
            endY++;
        }
        // If we are on the right side of the map, draw the map more to the left
        while (endX > play.getWidth())
        {
            startX--;
            endX--;
        }
        // If we are to the bottom side of the map, draw the map more to the top
        while (endY > play.getWidth())
        {
            startY--;
            endY--;
        }
        // If there are less blocks than could be displayed, just display less
        if (startX < 0 && endX > play.getWidth())
        {
            startX = 0;
            endX = play.getWidth();
        }
        // Same for height
        if (startY < 0 && endY > play.getHeight())
        {
            startY = 0;
            endY = play.getHeight();
        }

        // Ask the map for the blocks and objects that should be drawn in this area.
        return play.getBlocksAndObjects(startX, startY, endX, endY);
    }

    public void startagame(Stage primaryStage)
    {
        try
        {
            server = LocateRegistry.getRegistry(config.ip, config.port);
            gameLogic = (iGameLogic) server.lookup(config.bindName);
            Random rand = new Random();
            myAccount = new Account(Integer.toString(rand.nextInt(1000)));
            me = gameLogic.joinPlayer(myAccount);
            play = (Map) gameLogic.getMap();
            play.loadAfterRecieve(gameLogic, myAccount, me);
            me.setMap(play);
            play.addToUpdate(me);
            //gameLogic.addListener(listener, "ServerUpdate", me);
            listener = new UpdateListener(play, myAccount, me);
            gameLogic.addListener(listener, "ServerUpdate", me);
        } catch (RemoteException | NotBoundException ex)
        {
            Logger.getLogger(TheGame.class.getName()).log(Level.SEVERE, null, ex);
        }

        offsetBlocks = 4;

        StackPane root = new StackPane();

        scene = new Scene(root, 1400, 800, Color.LIGHTBLUE);
        scene.addEventHandler(KeyEvent.ANY, keyListener);
        scene.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseListener);

        final Canvas canvas = new Canvas(scene.getWidth(), scene.getHeight());
        GraphicsContext gc = canvas.getGraphicsContext2D();

        // Action Listeners
        scene.widthProperty().addListener((ObservableValue<? extends Number> observableValue, Number oldSceneWidth, Number newSceneWidth) ->
        {
            canvas.setWidth((double) newSceneWidth);
        });
        scene.heightProperty().addListener((ObservableValue<? extends Number> observableValue, Number oldSceneHeight, Number newSceneHeight) ->
        {
            canvas.setHeight((double) newSceneHeight);
        });

        // Set stage visiable
        root.getChildren().add(canvas);
        primaryStage.setScene(scene);
        primaryStage.show();

        primaryStage.setOnCloseRequest(event ->
        {
            System.exit(0);
        });

        // Update screen when animationtimer says it is possible
        lastTime = System.nanoTime();
        AnimationTimer loop = new AnimationTimer() {

            @Override
            public void handle(long now)
            {
                // Calculate fps
                currentTime = now;
                fps++;
                delta += currentTime - lastTime;

                try
                {
                    draw(gc);
                } catch (IOException ex)
                {
                    Logger.getLogger(TheGame.class.getName()).log(Level.SEVERE, null, ex);
                }

                if (delta > ONE_SECOND)
                {
                    primaryStage.setTitle("FPS : " + fps);
                    delta -= ONE_SECOND;
                    fps = 0;
                }

                lastTime = currentTime;
            }
        };
        loop.start();

        Timer update = new Timer("update");
        update.schedule(new TimerTask() {

            @Override
            public void run()
            {
                if (keys.contains(KeyCode.A))
                {
                    me.walkLeft();
                } else if (keys.contains(KeyCode.D))
                {
                    me.walkRight();
                }
                if (keys.contains(KeyCode.W))
                {
                    me.jump();
                }
                play.update();
            }
        }, 0, 1000 / 60);
    }

    private Parent createContent()
    {
        Pane root = new Pane();

        root.setPrefSize(860, 600);

        try (InputStream is = Files.newInputStream(Paths.get("src/resources//menu.jpg")))
        {
            ImageView img = new ImageView(new Image(is));
            img.setFitWidth(860);
            img.setFitHeight(600);
            root.getChildren().add(img);

        } catch (Exception e)
        {
            System.out.println("Couldnt load image");
        }

        Title title = new Title("The Game");
        title.setTranslateX(75);
        title.setTranslateY(200);

        MenuItem itemExit = new MenuItem("EXIT");
        itemExit.setOnMouseClicked(event -> System.exit((0)));

        MenuItem startMultiPlayer = new MenuItem("MULTIPLAYER");
        startMultiPlayer.setOnMouseClicked(event -> startagame(stages));

        MenuBox menu = new MenuBox(
                new MenuItem("SINGLE PLAYER [soon]"),
                startMultiPlayer,
                new MenuItem("CHARACTERS [soon]"),
                new MenuItem("OPTIONS [soon]"),
                itemExit);
        menu.setTranslateX(100);
        menu.setTranslateY(300);
        root.getChildren().addAll(title, menu);

        return root;
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        launch(args);
    }
}
