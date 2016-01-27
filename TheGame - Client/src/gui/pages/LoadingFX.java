/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.pages;

import gui.GameUtilities;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import javafx.animation.AnimationTimer;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import sound.Sound;
import thegame.Startup;
import thegame.com.Game.Objects.ArmorType;
import thegame.com.Game.Objects.Characters.CharacterGame;
import thegame.com.Game.Objects.Characters.Player;
import thegame.com.Menu.Account;
import thegame.config;
import thegame.tutorial.TutPlayer;
import thegame.tutorial.Tutorial;

/**
 *
 * @author Martijn
 */
public class LoadingFX {
    
    private Account myAccount;
    private Tutorial play;
    private Player me;
    private Scene scene;
    private GameUtilities ui;

    private List<KeyCode> keys = new ArrayList<>();

    private Sound sound;
    
    // fps 
    private final long ONE_SECOND = 1000000000;
    private long currentTime = 0;
    private long lastTime = 0;
    private int fps = 0;
    private double delta = 0;
    
    private AnimationTimer draw;
    private Timer movement;
    private Timer update;

    private Stage stages;
    public LoadingFX(Stage ps) {
        stages = ps;
        play = new Tutorial(10, 10);
        me = new TutPlayer(play.getSpawnX(), play.getSpawnY(), (Tutorial) play);
        play.setMe(me);
        
        stages.setFullScreenExitKeyCombination(new KeyCodeCombination(KeyCode.F11, KeyCombination.SHORTCUT_DOWN));
        stages.setFullScreenExitHint("");
        StackPane root = new StackPane();
        
        ps.focusedProperty().addListener(new ChangeListener<Boolean>()
        {
            @Override
            public void changed(ObservableValue<? extends Boolean> ov, Boolean t, Boolean t1)
            {
                keys.clear();
            }
        });
        
        scene = new Scene(root, ps.getScene().getWidth(), ps.getScene().getHeight(), Color.LIGHTBLUE);
        final Canvas canvas = new Canvas(scene.getWidth(), scene.getHeight());
        GraphicsContext gc = canvas.getGraphicsContext2D();
        
        bindHandlers(canvas);
        
        root.getChildren().add(canvas);
        ps.setScene(scene);
        ps.show();
        
        // Start timers
        startTimers();
        
        stages.setTitle("The Game");
        ui = new GameUtilities(me, play, canvas.getGraphicsContext2D(), scene);
        Startup.music.loop();
    }
    
    private void startTimers()
    {
        lastTime = System.nanoTime();
        draw = new AnimationTimer() {

            @Override
            public void handle(long now) {
                currentTime = now;
                fps++;
                delta += currentTime - lastTime;
                
                draw();
                
                if (delta > ONE_SECOND)
                {
                    stages.setTitle("The Game - FPS : " + fps);
                    delta -= ONE_SECOND;
                    fps = 0;
                }

                lastTime = currentTime;
            }
        };
        draw.start();
        
        update = new Timer("update");
        update.schedule(new TimerTask() {

            @Override
            public void run()
            {
                play.update();
            }
        }, 0, 1000 / 60);
        
        movement = new Timer("movement");
        movement.schedule(new TimerTask() {

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
            }
        }, 0, 1000 / 60);
    }
    
    private void bindHandlers(Canvas canvas)
    {
        // Action Listeners
        scene.widthProperty().addListener((ObservableValue<? extends Number> observableValue, Number oldSceneWidth, Number newSceneWidth) ->
        {
            canvas.setWidth((double) newSceneWidth);
        });
        scene.heightProperty().addListener((ObservableValue<? extends Number> observableValue, Number oldSceneHeight, Number newSceneHeight) ->
        {
            canvas.setHeight((double) newSceneHeight);
        });
        scene.addEventHandler(KeyEvent.ANY, keyListener);
        scene.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseListener);
        
    }
    
    private final EventHandler<KeyEvent> keyListener = (KeyEvent event) ->
    {
        if(event.getEventType() == KeyEvent.KEY_PRESSED && event.getCode() == KeyCode.F11) {
            stages.setFullScreen(!stages.isFullScreen());
        }
            if (event.getEventType() == KeyEvent.KEY_PRESSED && (event.getCode() == KeyCode.W || event.getCode() == KeyCode.D || event.getCode() == KeyCode.A) && !keys.contains(event.getCode()))
            {
                if (event.getCode() == KeyCode.W || event.getCode() == KeyCode.D || event.getCode() == KeyCode.A)
                {
                    keys.add(event.getCode());
                }
                if (event.getCode() == KeyCode.A && keys.contains(KeyCode.D))
                {
                    keys.remove(KeyCode.D);
                }
                if (event.getCode() == KeyCode.D && keys.contains(KeyCode.A))
                {
                    keys.remove(KeyCode.A);
                }
            }
            if (event.getEventType() == KeyEvent.KEY_RELEASED && (event.getCode() == KeyCode.W || event.getCode() == KeyCode.D || event.getCode() == KeyCode.A))
            {
                keys.remove(event.getCode());
                if (event.getCode() == KeyCode.W)
                {
                    me.stopJump();
                }
            }
            if (event.getCode() == KeyCode.E && event.getEventType() == KeyEvent.KEY_PRESSED)
            {
                ui.toggleInventory();
            }
    };
    
    private final EventHandler<MouseEvent> mouseListener = (MouseEvent event) ->
    {
        if (event.getButton().equals(MouseButton.PRIMARY) || event.getButton().equals(MouseButton.SECONDARY) || event.getButton().equals(MouseButton.MIDDLE))
        {
            clickHandler(event.getSceneX(), event.getSceneY(), event.getButton());
        }
    };

    private void clickHandler(double clickX, double clickY, MouseButton button)
    {
        if (ui.isInventory())
        {
            if (clickX <= 500 && clickY <= 150 && clickX > 10 && clickY > 10)
            {
                double horizontalX = clickX / 50;
                int horizontalSlot = Math.round((int) horizontalX);
                if ((horizontalX - Math.floor(horizontalX)) < 0.2)
                {
                    horizontalSlot = -1;
                }

                double verticalY = clickY / 50;
                int verticalSlot = Math.round((int) verticalY);
                if ((verticalY - Math.floor(verticalY)) < 0.2)
                {
                    verticalSlot = -1;
                }

                if (horizontalSlot != -1 && verticalSlot != -1)
                {
                    if(button == MouseButton.PRIMARY)
                    {
                        me.interactWithBackpack(verticalSlot * 10 + horizontalSlot, CharacterGame.action.CLICK);
                        return;
                    }
                }
            }
        }
        if (ui.isChat())
        {
            if (clickX >= 410 && clickY >= 540)
            {
                return;
            }
        }
        //Tool
        if (clickX >= scene.getWidth() - 50 && clickY >= scene.getHeight() - 50 && clickX < scene.getWidth() - 10 && scene.getHeight() - 10 > clickY)
        {
            me.unequipTool();
            return;
        }

        //Armor
        if (clickX >= scene.getWidth() - 50 && clickX < scene.getWidth() - 10 && clickY >= scene.getHeight() - 5 * 50 && clickY < scene.getHeight() - (10 + 50))
        {
            double vertical = (scene.getHeight() - clickY) / 50;
            int verticalSlot = Math.round((int) vertical);
            if ((vertical - Math.floor(vertical)) < 0.2)
            {
                verticalSlot = -1;
            }
            if (verticalSlot != -1)
            {
                switch (verticalSlot)
                {
                    case 1:
                        me.unequipArmor(ArmorType.bodyPart.SHIELD);
                        return;
                    case 2:
                        me.unequipArmor(ArmorType.bodyPart.GREAVES);
                        return;
                    case 3:
                        me.unequipArmor(ArmorType.bodyPart.CHESTPLATE);
                        return;
                    case 4:
                        me.unequipArmor(ArmorType.bodyPart.HELMET);
                        return;
                }
            }
        }

        int[] start = ui.getStartXstartY();
        float[] dxdy = ui.calculateDXDY(play.getWidth(), play.getHeight(), me.getX(), me.getY(), me.getW(), me.getH());
        double mapX = (clickX + dxdy[0]) / config.block.val - start[0];
        double mapY = (scene.getHeight() - clickY + dxdy[1]) / config.block.val - start[1];
        ((TutPlayer)me).useTool((float) mapX, (float) mapY);
    }
    
    private void draw()
    {
        ui.drawMap();
    }
}
