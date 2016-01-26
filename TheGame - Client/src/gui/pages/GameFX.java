/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.pages;

import gui.GameUtilities;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import static thegame.Startup.music;
import thegame.com.Game.Map;
import thegame.com.Game.Objects.ArmorType;
import thegame.com.Game.Objects.Characters.CharacterGame;
import thegame.com.Game.Objects.Characters.Player;
import thegame.com.Menu.Account;
import thegame.com.Menu.Message;
import thegame.config;
import thegame.shared.IGameClientToServer;
import thegame.shared.IGameServerToClientListener;

/**
 *
 * @author Martijn
 */
public class GameFX {

    private Account myAccount;
    private Map play;
    private Player me;
    private Scene scene;
    private GameUtilities ui;

    // server
    private Registry server;
    private IGameServerToClientListener gameServerToClientListener;
    public IGameClientToServer gameClientToServer;

    private List<KeyCode> keys = new ArrayList<>();

    // fps 
    private final long ONE_SECOND = 1000000000;
    private long currentTime = 0;
    private long lastTime = 0;
    private int fps = 0;
    private double delta = 0;

    //Chat
    private boolean typing = false;
    private String chatline = "";

    private AnimationTimer draw;
    private Timer movement;
    private Timer update;

    private Stage stages;

    public GameFX(Stage primaryStage, Player me, Account a, Map play, IGameClientToServer gameClientToServer, IGameServerToClientListener gameServerToClientListener)
    {
        primaryStage.setOnCloseRequest(event ->
        {
            if (gameClientToServer != null && gameServerToClientListener != null)
            {
                try
                {
                    gameClientToServer.quitGame(gameServerToClientListener);
                } catch (RemoteException ex)
                {
                    System.out.println("Could not reach the server. (Exception: " + ex.getMessage() + ")");
                    Platform.runLater(() ->
                    {
                        connectionLoss();
                    });
                }
            }
            System.exit(0);
        });

        primaryStage.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> ov, Boolean t, Boolean t1)
            {
                keys.clear();
            }
        });

        myAccount = a;
        stages = primaryStage;
        this.me = me;
        this.play = play;
        this.gameClientToServer = gameClientToServer;
        this.gameServerToClientListener = gameServerToClientListener;

        stages.setFullScreenExitKeyCombination(new KeyCodeCombination(KeyCode.F11, KeyCombination.SHORTCUT_DOWN));
        stages.setFullScreenExitHint("");
        StackPane root = new StackPane();
        scene = new Scene(root, primaryStage.getScene().getWidth(), primaryStage.getScene().getHeight(), Color.LIGHTBLUE);
        final Canvas canvas = new Canvas(scene.getWidth(), scene.getHeight());
        GraphicsContext gc = canvas.getGraphicsContext2D();

        bindHandlers(canvas);

        root.getChildren().add(canvas);
        primaryStage.setScene(scene);
        primaryStage.show();

        // Start timers
        startTimers();

        stages.setTitle("The Game");
        ui = new GameUtilities(me, play, canvas.getGraphicsContext2D(), scene);
        music.loop();
    }

    private void startTimers()
    {
        lastTime = System.nanoTime();
        draw = new AnimationTimer() {

            @Override
            public void handle(long now)
            {
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
        if (event.getEventType() == KeyEvent.KEY_PRESSED && event.getCode() == KeyCode.F11)
        {
            stages.setFullScreen(!stages.isFullScreen());
        }
        if (event.getEventType() == KeyEvent.KEY_PRESSED && event.getCode() == KeyCode.ESCAPE)
        {
            if (typing)
            {
                ui.toggleChat();
                typing = false;
            }
            ui.toggleMenu();
        }
        if (!typing && !ui.isMenu())
        {
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
            if (event.getCode() == KeyCode.ENTER && event.getEventType() == KeyEvent.KEY_PRESSED)
            {
                typing = true;
                chatline = "";
                ui.setChat(chatline);
            }
            if (event.getCode() == KeyCode.E && event.getEventType() == KeyEvent.KEY_PRESSED)
            {
                ui.toggleInventory();
            }
        } else if (typing && !ui.isMenu())
        {
            if (event.getCode() == KeyCode.ENTER && event.getEventType() == KeyEvent.KEY_PRESSED)
            {
                if (!chatline.isEmpty())
                {
                    Message chatMessage = new Message(myAccount, chatline);
                    try
                    {
                        gameClientToServer.sendGameChatMessage(chatMessage);
                    } catch (RemoteException ex)
                    {
                        System.out.println("Could not reach the server. (Exception: " + ex.getMessage() + ")");
                        Platform.runLater(() ->
                        {
                            connectionLoss();
                        });
                    }
                    ui.setChat("~");
                } else
                {
                    ui.closeChat();
                }
                typing = false;

                return;
            }
            if (event.getCode() == KeyCode.BACK_SPACE && event.getEventType() == KeyEvent.KEY_PRESSED)
            {
                if (chatline.length() > 0)
                {
                    chatline = chatline.substring(0, chatline.length() - 1);
                    ui.setChat(chatline);
                }
            }
            if (event.getCode() == KeyCode.LEFT && event.getEventType() == KeyEvent.KEY_PRESSED)
            {

            }
            if (event.getEventType() == KeyEvent.KEY_PRESSED && chatline.length() < 44)
            {
                if (event.isShiftDown())
                {
                    String tempString = event.getCode().getName();
                    if (!tempString.equals("Shift"))
                    {
                        tempString = event.getCode().getName();
                        if (tempString.equalsIgnoreCase("Slash"))
                        {
                            chatline += "?";
                            ui.setChat(chatline);
                        } else
                        {
                            chatline += event.getCode().getName();
                            ui.setChat(chatline);
                        }
                    }
                } else
                {
                    chatline += event.getText();
                    ui.setChat(chatline);
                }
            }
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
        if (ui.isMenu())
        {
            stopTimers();
            try
            {
                gameClientToServer.leaveGame(gameServerToClientListener);
            } catch (RemoteException ex)
            {
                Logger.getLogger(GameFX.class.getName()).log(Level.SEVERE, null, ex);
            }
            music.stop();
            new MenuFX(stages, myAccount, true);
            return;
        }
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
        me.useTool((float) mapX, (float) mapY, gameClientToServer);
    }

    private void draw()
    {
        ui.drawMap();
    }

    public void connectionLoss()
    {
        stopTimers();
        music.stop();

        new LoginFX(stages);

        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Connection lost");
        alert.setHeaderText("Connection to server lost");
        alert.setContentText("You lost the connection to the server. Please try again in a minute.");
        alert.showAndWait();
    }

    public void stopTimers()
    {
        draw.stop();
        movement.cancel();
        update.cancel();
    }
}
