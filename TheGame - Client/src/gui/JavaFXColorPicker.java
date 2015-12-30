package gui;
 
import display.Image;
import java.awt.Button;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ColorPicker;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.stage.Stage;
 
/**
 *
 * @web http://java-buddy.blogspot.com/
 */
public class JavaFXColorPicker extends Application {
    private Image head;
    private Image body;
    private Image frontarm;
    private Image backarm;
    private Image frontleg;
    private Image backleg;
    

    private Color[] BodyColorArray;
    private Color[] HeadColorArray;
    private Color[] LegColorArray;

    private Image heart;
        ColorPicker HeadColor = new ColorPicker();
        ColorPicker BodyColor = new ColorPicker();
        ColorPicker LegColor = new ColorPicker();
        
    @Override
    public void start(Stage primaryStage) throws IOException {
        
         Color[] temp = new Color[]
            {
                new Color(0, 0, 0, 0.3), new Color(0.2, 0.2, 0.2, 0.3), new Color(0.4, 0.4, 0.4, 0.3), new Color(0.6, 0.6, 0.6, 0.3), new Color(0.8, 0.8, 0.8, 0.3), new Color(1, 1, 1, 0.3)
            };
          HeadColorArray = temp;
          BodyColorArray = temp;
          LegColorArray = temp;
          
          head = new display.Image(display.Parts.playerHead);
          body = new display.Image(display.Parts.playerTorso);
          frontarm = new display.Image(display.Parts.playerFrontArm);
          backarm = new display.Image(display.Parts.playerBackArm);
          frontleg = new display.Image(display.Parts.playerFrontLeg);
          backleg = new display.Image(display.Parts.playerBackLeg);

        GridPane root = new GridPane();
        
        final Canvas canvas = new Canvas(500,400);
        canvas.setOpacity(10);
         Scene scene = new Scene(root, 500, 400, Color.BLACK); 
         GraphicsContext gc = canvas.getGraphicsContext2D();
         root.setHgap(10);
         root.setVgap(10);
         root.setPadding(new Insets(10, 30, 30, 30));

         ColorChanged(gc);
           
       
        
         
        HeadColor.setOnAction(new EventHandler(){
 
            @Override
            public void handle(Event event) {
                Color HeadPaint = HeadColor.getValue();
                HeadColorArray = new Color[]
            {
                new Color(0 , 0, 0, 1),
                HeadPaint,
                HeadPaint,
                HeadPaint,
                HeadPaint,
                new Color(1, 1, 1, 1) // de ogen
            };
                ColorChanged(gc);
                
              
              
         
          
            }
        
        });
        
        BodyColor.setOnAction(new EventHandler(){
 
            @Override
            public void handle(Event event) {
                Color BodyPaint = BodyColor.getValue();
                BodyColorArray = new Color[]
            {
                new Color(0 , 0, 0, 1),
                BodyPaint,
                BodyPaint,
                BodyPaint,
                BodyPaint,
                new Color(1, 1, 1, 1) // de ogen
            };
                ColorChanged(gc);
              
          
            }
        
        });
        
        
         LegColor.setOnAction(new EventHandler(){
 
            @Override
            public void handle(Event event) {
                Color LegPaint = LegColor.getValue();
                LegColorArray = new Color[]
            {
                new Color(0 , 0, 0, 1),
                LegPaint,
                LegPaint,
                LegPaint,
                LegPaint,
                new Color(1, 1, 1, 1) // de ogen
            };
                ColorChanged(gc);
            }  
         });
          
                
                
          
          
        
         
                   
        

        root.getChildren().add(canvas);
        root.add(HeadColor, 0, 1); 
        root.add(BodyColor, 0, 2); 
        root.add(LegColor,0,3);


          
        
      
        
        
        primaryStage.setTitle("Customize Character!");
        primaryStage.setScene(scene);
        primaryStage.show();
        
          
      

    }
 
    public static void main(String[] args) {
        launch(args);
        
       
    }
    
    public void ColorChanged(GraphicsContext gc) {
      
        
         
                backleg.recolour(LegColorArray);
                Image i = backleg;
                gc.drawImage(i.show(), 304, 228);    
                
                backarm.recolour(BodyColorArray);
                i = backarm;
                gc.drawImage(i.show(), 312, 212);    
                
                   
                body.recolour(BodyColorArray);
                i = body;
                gc.drawImage(i.show(), 300, 212);  
                
                frontleg.recolour(LegColorArray);
                i = frontleg;
                gc.drawImage(i.show(), 299, 228);  
                
                frontarm.recolour(BodyColorArray);
                i = frontarm;
                gc.drawImage(i.show(), 296, 212);  
                    
              
                head.recolour(HeadColorArray);
                 i = head;
                gc.drawImage(i.show(), 300, 200);
}
     
         }
                 