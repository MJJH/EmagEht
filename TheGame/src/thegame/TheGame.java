/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thegame;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.input.KeyCode;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
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
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.*;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;



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
    
    private float dx;
    private float dy;
    
    // FPS
    private final long ONE_SECOND = 1000000000;
    private long currentTime = 0;
    private long lastTime = 0;
    private int fps = 0;
    private double delta = 0;

    private EventHandler<KeyEvent> keyListener = new EventHandler<KeyEvent>() {
        @Override
        public void handle(KeyEvent event)
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
                } else if(event.getEventType() == KeyEvent.KEY_RELEASED)
                {
                    keys.remove(event.getCode());
                }
            }

        }
    };
    
    private EventHandler<MouseEvent> mouseListener = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {
            if(event.getButton().equals(MouseButton.PRIMARY)){
                double clickX = (event.getSceneX()+dx);
                clickX = clickX / config.block.val;
                clickX += startX;
                
                int blockHorizontal = (int) Math.ceil(scene.getWidth() / config.block.val) + 3;
                int blockVertical = (int) Math.ceil(scene.getHeight() / config.block.val) + 3;
                
                if(startX == 0)
                    clickX -= me.getW() / 2;
                else if(startX > 0)
                    clickX += me.getW() / 2 + 1;
                
                double clickY = (scene.getHeight() - event.getSceneY() + dy) / config.block.val + startY - me.getH() / 2;

                
                System.err.println(clickY + " / " + clickX);
                System.out.println(me.getY() + " / " + me.getX());
                
                me.useTool((float) clickX, (float) clickY);
            }
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
        // Get viewables
        List<MapObject> view = viewable();

        // Clear scene
        clear(g);

        int blockHorizontal = (int) Math.ceil(scene.getWidth() / config.block.val) + 3;
        int blockVertical = (int) Math.ceil(scene.getHeight() / config.block.val) + 3;

        dx = 0;
        dy = 0;

        dx += config.block.val * (me.getW() / 2);
        if (me.getX() + me.getW() / 2 >= Math.floor(startX + (blockHorizontal) / 2) - me.getW() / 2 && me.getX() + me.getW() / 2 <= Math.ceil(startX + (blockHorizontal) / 2))
        {
            if (me.getX() % 1 >= me.getW() / 2)
            {
                dx += config.block.val;
            }
            dx -= config.block.val * (me.getX() % 1);
        } else if (me.getX() + me.getW() / 2 >= Math.floor(startX + (blockHorizontal) / 2) && me.getX() + me.getW() / 2 <= Math.ceil(startX + (blockHorizontal) / 2) + 1)
        {
            if (me.getX() % 1 >= me.getW() / 2)
            {
                dx += config.block.val;
            }
            dx -= config.block.val * (me.getX() % 1);
            dx -= config.block.val;
        } else if (me.getX() + me.getW() / 2 > Math.floor(startX + (blockHorizontal) / 2) + 1)
        {
            dx -= config.block.val * 2;
        }

        dy -= config.block.val * (me.getH() / 2);
        if (me.getY() + me.getH() / 2 >= Math.floor(startY + (blockVertical) / 2) - me.getH() / 2 && me.getY() + me.getH() / 2 <= Math.ceil(startY + (blockVertical) / 2))
        {
            if (me.getY() % 1 >= me.getH() / 2)
            {
                dy -= config.block.val;
            }
            dy += config.block.val * (me.getY() % 1);
        } else if (me.getY() + me.getH() / 2 >= Math.floor(startY + (blockVertical) / 2) && me.getY() + me.getH() / 2 <= Math.ceil(startY + (blockVertical) / 2) + 1)
        {
            if (me.getY() % 1 >= me.getH() / 2)
            {
                dy -= config.block.val;
            }
            dy += config.block.val * (me.getY() % 1);
            dy += config.block.val;
        } else if (me.getY() + me.getH() / 2 > Math.floor(startY + (blockVertical) / 2) + 1)
        {
            dy += config.block.val * 2;
        }

        for (MapObject draw : view)
        {
            float x = (draw.getX() - startX - me.getW() / 2) * config.block.val;
            float y = ((float) scene.getHeight() - (draw.getY() - startY + 1 - me.getH() / 2) * config.block.val);

            x += dx;
            y += dy;

            if (draw instanceof Player)
            {
                g.beginPath();
                g.setFill(Color.BLACK);
                g.rect(x, y, config.block.val * ((Player) draw).getW(), config.block.val * ((Player) draw).getH());
                g.fill();
                g.closePath();
            } 
            else if (draw instanceof Enemy)
            {
                g.beginPath();
                g.setFill(Color.RED);
                g.rect(x, y, config.block.val * ((Enemy) draw).getW(), config.block.val * ((Enemy) draw).getH());
                g.fill();
                g.closePath();
            }
            else
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

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        launch(args);
    }
     public void startagame(Stage primaryStage){
        play = new Map();
        play.generateMap();
        me = new Player(null, "Dummy", 100, null, null, play.getSpawnX(), play.getSpawnY(), null, 1, 1, play);
        play.addObject(me);
        play.addPlayer(me);

        StackPane root = new StackPane();

        scene = new Scene(root, 1400, 800, Color.LIGHTBLUE);
        scene.addEventHandler(KeyEvent.ANY, keyListener);
        scene.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseListener);

        final Canvas canvas = new Canvas(scene.getWidth(), scene.getHeight());
        GraphicsContext gc = canvas.getGraphicsContext2D();

        scene.widthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number oldSceneWidth, Number newSceneWidth)
            {
                canvas.setWidth((double) newSceneWidth);
            }
        });
        scene.heightProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number oldSceneHeight, Number newSceneHeight)
            {
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
        
        lastTime = System.nanoTime();
        
        AnimationTimer loop = new AnimationTimer() {

            @Override
            public void handle(long now)
            {
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
                    me.Jump();
                }

                me.update();
                
                play.updateEnemy();
            }
        }, 0, 1000 / 60);
     }
     
      private Parent createContent() {
        Pane root = new Pane();
        
        root.setPrefSize(860, 600);
        
        try(InputStream is = Files.newInputStream(Paths.get("src/resources//menu.jpg"))) {
            ImageView img = new ImageView(new Image(is));
            img.setFitWidth(860);
            img.setFitHeight(600);
            root.getChildren().add(img);
        
            
        } catch (Exception e) {
            System.out.println("Couldnt load image");
        }
        
        Title title = new Title ("The Game");
        title.setTranslateX(75);
        title.setTranslateY(200);
        
        MenuItem itemExit = new MenuItem("EXIT");
        itemExit.setOnMouseClicked(event -> System.exit((0)));
        
        MenuItem startThegame = new MenuItem("Start a game");
        startThegame.setOnMouseClicked(event -> startagame(stages));

        
        MenuBox menu = new MenuBox(
                startThegame,
                new MenuItem("TO DO"),
                new MenuItem("TO DO"),
                new MenuItem(" TO DO HIGH SCORE"),
                itemExit);
        menu.setTranslateX(100);
        menu.setTranslateY(300);
        root.getChildren().addAll(title,menu);
        
        return root;
    }
    
    private static class MenuItem extends StackPane{
        public MenuItem(String name){
            LinearGradient gradient = new LinearGradient(0, 0, 1, 0, true, CycleMethod.NO_CYCLE, new Stop[]{
                new Stop(0, Color.DARKVIOLET),
                new Stop(0.1, Color.BLACK),
                new Stop(0.9, Color.BLACK),
                new Stop(01, Color.DARKVIOLET)
            });
        
            Rectangle bg = new Rectangle(200,30);
            bg.setOpacity(0.4);
            
            Text text = new Text(name);
            text.setFill(Color.DARKGREY);
            text.setFont(Font.font("Tw Cen MT Condensed", FontWeight.SEMI_BOLD, 22));
            
            setAlignment(Pos.CENTER);
            getChildren().addAll(bg, text);
            
            setOnMouseEntered(event -> {
                bg.setFill(gradient);
                text.setFill(Color.WHITE);
            });
                
            setOnMouseDragExited(event -> {
            bg.setFill(Color.BLACK);
            text.setFill(Color.DARKGREY);
            });
                    
            setOnMousePressed(event -> {
           bg.setFill(Color.DARKGREY);
            });
            
            setOnMouseReleased(event -> {
               bg.setFill(gradient);
            });
    }
        }
    private static class MenuBox extends VBox {
        public  MenuBox(MenuItem... items){
            getChildren().add(createSeparator());
            
            for(MenuItem item : items){
                getChildren().addAll(item, createSeparator());
            }
    }
        private Line createSeparator(){
            Line sep = new Line();
            sep.setEndX(200);
            sep.setStroke(Color.DARKGREY);
            return sep;
        }
                     
            }
                private static class Title extends StackPane{
                    public Title(String name){
                        Rectangle bg = new Rectangle(250,60);
                      bg.setStroke(Color.WHITE);
                      bg.setStrokeWidth(2);
                      bg.setFill(null);
                        
                        Text text = new Text(name);
                        text.setFill(Color.WHITE);
                        text.setFont(Font.font("Tw Cen MT Condensed",FontWeight.SEMI_BOLD,50));
                        
                        setAlignment(Pos.CENTER);
                        getChildren().addAll(bg,text);
                    }
                }

}
