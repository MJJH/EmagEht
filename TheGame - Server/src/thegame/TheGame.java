/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thegame;

import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.text.FontWeight;
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
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import thegame.com.Game.Objects.Block;
import thegame.com.Game.Objects.BlockType;
import thegame.com.Game.Objects.Characters.CharacterGame;

/**
 *
 * @author laurens
 */
public class TheGame extends Application {

    private Map play;
    private Player me;
    private Scene scene;
    
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
            }
        } else
        {
            try
            {
                if (event.getCode() == KeyCode.DIGIT1 && event.getEventType() == KeyEvent.KEY_PRESSED )
            {
                play.addObject(new Enemy("Loser", 100, null, play.getSpawnX(), play.getSpawnY(), null, 1, 1, play));
            }
            if (event.getCode() == KeyCode.DIGIT2 && event.getEventType() == KeyEvent.KEY_PRESSED )
            {
                float x = Math.round(me.getX());
                float y = Math.round(me.getY())-1;
                Block block = new Block(BlockType.Dirt, x, y, 1, play);
                play.addObject(block);
            }
            } catch (RemoteException e)
            {
            }
            
        }
    };

    private final EventHandler<MouseEvent> mouseListener = (MouseEvent event) ->
    {
        if (event.getButton().equals(MouseButton.PRIMARY))
        {
            double clickX = (event.getSceneX() + dx) / config.block.val - startX;

            double clickY = (scene.getHeight() - event.getSceneY() + dy) / config.block.val - startY;

            me.useTool((float) clickX, (float) clickY);
        }
    };
    private Stage stages;

    @Override
    public void start(Stage primaryStage)
    {
        Scene scene = new Scene(createContent());
        primaryStage.setTitle("Menu");
        primaryStage.setScene(scene);
        primaryStage.show();
        stages = primaryStage;

    }

    private void draw(GraphicsContext g)
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
        if((pX + pW / 2) * config.block.val > scene.getWidth() / 2 && (pX + pW / 2) * config.block.val < playW * config.block.val - scene.getWidth() / 2) {
            // DX will be the center of the map
            dx = (pX + pW / 2) * config.block.val;
            dx -= (scene.getWidth() / 2);
            
            // If you are no longer on the right side of the map
            if(startX >= 0) {
                dx += (startX) * config.block.val;
            }
        } else if ((pX + pW / 2) * config.block.val >= playW * config.block.val - scene.getWidth() / 2) {
            // Dit is het goede moment. Hier moet iets gebeuren waardoor de aller laatste blok helemaal rechts wordt getekent en niet meer beweegt
            dx = (playW - blockHorizontal + 2) * config.block.val*2;
        }
        
        
        // once the player's center is on the middle
        if((pY - pH / 2) * config.block.val > scene.getHeight() / 2 && 
                (pY - pH / 2) * config.block.val < playH* config.block.val - scene.getHeight() / 2) {
            // DX will be the center of the map
            dy = (pY - pH / 2) * config.block.val;
            dy -= (scene.getHeight() / 2);
            
            // If you are no longer on the right side of the map
            if(startX >= 0) {
                dy += (startY) * config.block.val;
            }
        } else if ((pY - pH / 2) * config.block.val >= playH * config.block.val - scene.getHeight() / 2) {
            // Dit is het goede moment. Hier moet iets gebeuren waardoor de aller laatste blok helemaal rechts wordt getekent en niet meer beweegt
            dy = (playH- blockVertical + 2) * config.block.val*2;
        }
        
        
        for (MapObject draw : view)
        {
            float x;
            float y;
            if(!draw.equals(me))
            {
                x = (draw.getX() + startX) * config.block.val - dx;
                y = ((float) scene.getHeight() - (draw.getY() + startY) * config.block.val) + dy;
            }
            else{
                x = (pX + startX) * config.block.val - dx;
                y = ((float) scene.getHeight() - (pY + startY) * config.block.val) + dy;
            }

            if (draw instanceof Player || draw instanceof Enemy)
            {
                float width = config.block.val * draw.getW();
                float height = config.block.val * draw.getH();

                // CHARACTER
                g.beginPath();
                if (draw instanceof Player)
                {
                    g.setFill(Color.BLACK);
                } else if (draw instanceof Enemy)
                {
                    g.setFill(Color.RED);
                }
                g.rect(x, y, width, height);
                g.fill();

                // ARROW
                double[] xPoints = new double[3];
                double[] yPoints = new double[3];
                CharacterGame enemy = (CharacterGame) draw;
                yPoints[0] = y + (height / 2);
                yPoints[1] = y;
                yPoints[2] = y + height;

                switch (enemy.getDirection())
                {
                    case LEFT:
                        xPoints[0] = x;
                        xPoints[1] = x + width;
                        xPoints[2] = x + width;
                        break;
                    case RIGHT:
                        xPoints[0] = x + width;
                        xPoints[1] = x;
                        xPoints[2] = x;
                        break;
                }

                g.setFill(Color.GREEN);
                g.fillPolygon(xPoints, yPoints, 3);

                g.closePath();
            } else
            {
                g.beginPath();
                g.drawImage(draw.getSkin().show(), x, y);
                g.closePath();
            }
        }
        
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
        return play.getObjects(startX, startY, endX, endY);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        launch(args);
    }

    public void startagame(Stage primaryStage)
    {
        
        try
        {
            // Declare variables
            play = new Map();
        } catch (RemoteException ex)
        {
            Logger.getLogger(TheGame.class.getName()).log(Level.SEVERE, null, ex);
        }
        play.generateMap();
        try
        {
            me = new Player(null, "Dummy", 100, null, null, play.getSpawnX(), play.getSpawnY(), null, 1, 1, play);
        } catch (RemoteException ex)
        {
            Logger.getLogger(TheGame.class.getName()).log(Level.SEVERE, null, ex);
        }
        play.addObject(me);

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

                draw(gc);

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
        
        Timer update = new Timer();
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

        MenuItem startThegame = new MenuItem("SINGLE PLAYER");
        startThegame.setOnMouseClicked(event -> startagame(stages));

        MenuBox menu = new MenuBox(
                startThegame,
                new MenuItem("MULTIPLAYER [soon]"),
                new MenuItem("CHARACTERS [soon]"),
                new MenuItem("OPTIONS [soon]"),
                itemExit);
        menu.setTranslateX(100);
        menu.setTranslateY(300);
        root.getChildren().addAll(title, menu);

        return root;
    }

    private static class MenuItem extends StackPane {

        public MenuItem(String name)
        {
            LinearGradient gradient = new LinearGradient(0, 0, 1, 0, true, CycleMethod.NO_CYCLE, new Stop[]
            {
                new Stop(0, Color.DARKVIOLET),
                new Stop(0.1, Color.BLACK),
                new Stop(0.9, Color.BLACK),
                new Stop(01, Color.DARKVIOLET)
            });

            Rectangle bg = new Rectangle(200, 30);
            bg.setOpacity(0.4);

            Text text = new Text(name);
            text.setFill(Color.DARKGREY);
            text.setFont(Font.font("Tw Cen MT Condensed", FontWeight.SEMI_BOLD, 22));

            setAlignment(Pos.CENTER);
            getChildren().addAll(bg, text);

            setOnMouseEntered(event ->
            {
                bg.setFill(gradient);
                text.setFill(Color.WHITE);
            });

            setOnMouseDragExited(event ->
            {
                bg.setFill(Color.BLACK);
                text.setFill(Color.DARKGREY);
            });

            setOnMousePressed(event ->
            {
                bg.setFill(Color.DARKGREY);
            });

            setOnMouseReleased(event ->
            {
                bg.setFill(gradient);
            });
        }
    }

    private static class MenuBox extends VBox {

        public MenuBox(MenuItem... items)
        {
            getChildren().add(createSeparator());

            for (MenuItem item : items)
            {
                getChildren().addAll(item, createSeparator());
            }
        }

        private Line createSeparator()
        {
            Line sep = new Line();
            sep.setEndX(200);
            sep.setStroke(Color.DARKGREY);
            return sep;
        }

    }

    private static class Title extends StackPane {

        public Title(String name)
        {
            Rectangle bg = new Rectangle(250, 60);
            bg.setStroke(Color.WHITE);
            bg.setStrokeWidth(2);
            bg.setFill(null);

            Text text = new Text(name);
            text.setFill(Color.WHITE);
            text.setFont(Font.font("Tw Cen MT Condensed", FontWeight.SEMI_BOLD, 50));

            setAlignment(Pos.CENTER);
            getChildren().addAll(bg, text);
        }
    }

}
