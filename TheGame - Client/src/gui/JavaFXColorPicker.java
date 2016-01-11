    package gui;

    import display.Image;
import display.Parts;
import display.Sets;
    import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
    import javafx.application.Application;
    import javafx.event.Event;
    import javafx.event.EventHandler;
    import javafx.geometry.Insets;
    import javafx.scene.Scene;
    import javafx.scene.canvas.Canvas;
    import javafx.scene.canvas.GraphicsContext;
    import javafx.scene.control.ColorPicker;
    import javafx.scene.paint.Color;
    import javafx.stage.Stage;
    import javafx.scene.control.TextField;
    import javafx.scene.control.Label;
    import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;



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
        
        double width;
        double height;
        
        private TextField name;
        private Button Save;
        private Button Reset;
        private Scene scene;

        private Color[] BodyColorArray;
        private Color[] HeadColorArray;
        private Color[] ShirtColorArray;
        private Color[] LegColorArray;
        private Color[] temp;      
        private Color[] tempclear;


        private Image heart;
            ColorPicker HeadColor = new ColorPicker();
            ColorPicker BodyColor = new ColorPicker();
            ColorPicker LegColor = new ColorPicker();
    private Image Tshirt;
    private Image TshirtBody;
    private Image TShirtBack;
    private Image TShirtFront;
    private Image shortTop;
    private Image shortBack;
    private Image shortFront;
    private Color[] shortColorArray;
    
    

        @Override
        public void start(Stage primaryStage) throws IOException {

                temp = new Color[]
                {
                    new Color(0, 0, 0, 0.3), new Color(0.2, 0.2, 0.2, 0.3), new Color(0.4, 0.4, 0.4, 0.3), new Color(0.6, 0.6, 0.6, 0.3), new Color(0.8, 0.8, 0.8, 0.3), new Color(1, 1, 1, 0.3)
                };
                tempclear = new Color[]
                {
                    new Color(0, 0, 0, 1), new Color(0.2, 0.2, 0.2, 1), new Color(0.4, 0.4, 0.4, 1), new Color(0.6, 0.6, 0.6, 1), new Color(0.8, 0.8, 0.8, 1), new Color(1, 1, 1, 1)
                };
                ShirtColorArray = new Color[]
                {
                    new Color(0, 0, 0, 0.3), new Color(0.2, 0.2, 0.2, 0.3), new Color(0.4, 0.4, 0.4, 0.3), new Color(0.6, 0.6, 0.6, 0.3), new Color(0.8, 0.8, 0.8, 0.3), new Color(1, 1, 1, 0.3)
                };
                shortColorArray = new Color[]
                {
                    new Color(0, 0, 0, 0.3), new Color(0.2, 0.2, 0.2, 0.3), new Color(0.4, 0.4, 0.4, 0.3), new Color(0.6, 0.6, 0.6, 0.3), new Color(0.8, 0.8, 0.8, 0.3), new Color(1, 1, 1, 0.3)
                };
              HeadColorArray = temp;
              BodyColorArray = temp;
              LegColorArray = temp;
              

             
              body = new display.Image(display.Parts.playerTorso);
              body.Scale(3);
              TshirtBody = new display.Image(Parts.tShirtBody);
              TshirtBody.Scale(3);
              frontarm = new display.Image(display.Parts.playerFrontArm);
              frontarm.Scale(3);
              backarm = new display.Image(display.Parts.playerBackArm);
              backarm.Scale(3);
              TShirtBack = new display.Image(Parts.tShirtBack);
              TShirtBack.Scale(3);
              TShirtFront = new display.Image(Parts.tShirtFront);
              TShirtFront.Scale(3);
              frontleg = new display.Image(display.Parts.playerFrontLeg);
              frontleg.Scale(3);
              backleg = new display.Image(display.Parts.playerBackLeg);
              backleg.Scale(3);
              head = new display.Image(display.Parts.playerHead);
              head.Scale(3);
              shortTop = new display.Image(display.Parts.shortTop);
              shortTop.Scale(3);
              shortBack = new display.Image(display.Parts.shortBack);
              shortBack.Scale(3);
              shortFront = new display.Image(display.Parts.shortFront);
              shortFront.Scale(3);
              
              

            final Canvas canvas = new Canvas(primaryStage.getScene().getWidth(),primaryStage.getScene().getHeight());

             GraphicsContext gc = canvas.getGraphicsContext2D();
             //BorderPane root = new BorderPane();
             AnchorPane root = new AnchorPane();
             
             width = primaryStage.getScene().getWidth();
             height = primaryStage.getScene().getHeight();
             
             scene = new Scene(root, primaryStage.getScene().getWidth(), primaryStage.getScene().getHeight(), Color.BLUE); 
             primaryStage.setResizable(false);
             
            root.setStyle("-fx-background-color: #add8e6");
             root.setPadding(new Insets(10, 30, 30, 5));

            /* gc.strokeText("Color Head :", 5, 40);
             gc.strokeText("Color Body :", 5, 95);
             gc.strokeText("Color Legs :", 5, 145); */
              
             

             ColorChanged(gc);

            root.getChildren().add(canvas);
            Save = new Button("Save Character");
            Reset = new Button("Reset Character");

            AnchorPane.setTopAnchor(BodyColor, height * 0.6);
            AnchorPane.setLeftAnchor(BodyColor, width * 0.45);
            root.getChildren().add(BodyColor);

 
            AnchorPane.setTopAnchor(Save, height * 0.6);
            AnchorPane.setTopAnchor(Reset, height * 0.6);
            AnchorPane.setLeftAnchor(Reset, width * 0.35); 
            AnchorPane.setLeftAnchor(Save, width * 0.565);

            root.getChildren().add(Save);
            root.getChildren().add(Reset);
            
             Label label1 = new Label("Character Name:");
             name = new TextField();
             name.maxWidth(30);
             
            AnchorPane.setTopAnchor(label1, height * 0.2);
            AnchorPane.setTopAnchor(name, height * 0.2);
            AnchorPane.setLeftAnchor(label1, width * 0.40); 
            AnchorPane.setLeftAnchor(name, width * 0.50);
             
             root.getChildren().add(label1);
             root.getChildren().add(name);
             
            
             
             gc.setFill(Color.RED);
            
            double[] pointTopLeft = new double[]{(width * 0.4) , (height * 0.33) , (width * 0.4) , (height * 0.378), (width * 0.36) , (height * 0.354)};
            Polygon TriangleTopLeft = new Polygon(pointTopLeft);
            
            double[] pointMiddleLeft = new double[]{(width * 0.4) , (height * 0.42) , (width * 0.4) , (height * 0.468), (width * 0.36) , (height * 0.444)};
            Polygon TriangleMiddleLeft = new Polygon(pointMiddleLeft);
            
            double[] pointBottomLeft = new double[]{(width * 0.4) , (height * 0.51) , (width * 0.4) , (height * 0.558), (width * 0.36) , (height * 0.534)};
            Polygon TriangleBottomLeft = new Polygon(pointBottomLeft);
            
            double[] pointTopRight = new double[]{(width * 0.6) , (height * 0.33) , (width * 0.6) , (height * 0.378), (width * 0.64) , (height * 0.354)};
            Polygon TriangleTopRight = new Polygon(pointTopRight);
            
            double[] pointMiddleRight = new double[]{(width * 0.6) , (height * 0.42) , (width * 0.6) , (height * 0.468), (width * 0.64) , (height * 0.444)};
            Polygon TriangleMiddleRight = new Polygon(pointMiddleRight);
            
            double[] pointBottomRight = new double[]{(width * 0.6) , (height * 0.51) , (width * 0.6) , (height * 0.558), (width * 0.64) , (height * 0.534)};
            Polygon TriangleBottomRight = new Polygon(pointBottomRight);
            
            
            root.getChildren().add(TriangleBottomLeft);
            root.getChildren().add(TriangleMiddleLeft);
            root.getChildren().add(TriangleTopLeft);
            root.getChildren().add(TriangleBottomRight);
            root.getChildren().add(TriangleMiddleRight);
            root.getChildren().add(TriangleTopRight);
            

            //     root.getChildren().add(addButton());

            /*
            root.setRight(addTextbox());
            root.setBottom(addButton());
            root.setLeft(addVBox());



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
             */
            
             TriangleMiddleLeft.setOnMouseClicked(new EventHandler<MouseEvent>()
            {
            @Override
            public void handle(MouseEvent t) {
                Random rand = new Random();

                float r = rand.nextFloat();
                float g = rand.nextFloat();
                float b = rand.nextFloat();
                Color shirtPaint = new Color(r, g, b, 1);

                ShirtColorArray = new Color[]
                {
                    null,
                    shirtPaint.darker().darker(),
                    shirtPaint.darker(),
                    shirtPaint,
                    shirtPaint.brighter(),
                    shirtPaint.brighter().brighter(),
                };
                    ColorChanged(gc);
            }
            });
             
              TriangleBottomLeft.setOnMouseClicked(new EventHandler<MouseEvent>()
            {
            @Override
            public void handle(MouseEvent t) {
                Random rand = new Random();

                float r = rand.nextFloat();
                float g = rand.nextFloat();
                float b = rand.nextFloat();
                Color ShortsPaint = new Color(r, g, b, 1);

                shortColorArray = new Color[]
                {
                    null,
                    ShortsPaint.darker().darker(),
                    ShortsPaint.darker(),
                    ShortsPaint,
                    ShortsPaint.brighter(),
                    ShortsPaint.brighter().brighter(),
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

               /*
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
             
            
             
             Save.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
             Stage stage = (Stage) Save.getScene().getWindow();
             stage.close();           
            }
                 });
             
             Reset.addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>() {
             @Override
             public void handle(ActionEvent actionEvent) {
             HeadColor.setValue(Color.WHITE);
             BodyColor.setValue(Color.WHITE);
             LegColor.setValue(Color.WHITE);
             BodyColorArray = tempclear;
             HeadColorArray = tempclear;
             LegColorArray = tempclear;
             name.setText("");
             ColorChanged(gc);
          
             
             }
             });
             
    

             
           




             */



            
         
           







            primaryStage.setTitle("Customize Character!");
            primaryStage.setScene(scene);
            primaryStage.show();




        }

        public static void main(String[] args) {
            launch(args);


        }
        
        
        /*
        public HBox addTextbox() {
        HBox hbox = new HBox();
        hbox.setPadding(new Insets(0,0,0,0));
        hbox.setSpacing(25);
        
        Label label1 = new Label("Character Name:");
        name = new TextField();
        name.maxWidth(30);
        
        hbox.getChildren().add(label1);
        hbox.getChildren().add(name);

        
        return hbox;

        } */
        
      /*  public HBox addButton() {
        HBox hbox = new HBox();
        hbox.setPadding(new Insets(0,0,0,175));
        hbox.setSpacing(10);
        
        Save = new Button("Save Character");
        Reset = new Button("Reset Character");

        hbox.getChildren().add(Save);
        hbox.getChildren().add(Reset);
 
        return hbox;

        } */
        
        
        
       /* 
        public VBox addVBox() {
         VBox vbox = new VBox();
         vbox.setPadding(new Insets(40,10,10,0));
         vbox.setSpacing(25);
        
             
          vbox.getChildren().add(HeadColor); 
          vbox.getChildren().add(BodyColor);
          vbox.getChildren().add(LegColor);

         return vbox;
} */

        public void ColorChanged(GraphicsContext gc) {

                    
                    
                    backleg.recolour(BodyColorArray);
                    Image i = backleg;
                    gc.drawImage(i.show(), 632, 348);    

                 


                    body.recolour(BodyColorArray);
                    i = body;
                    gc.drawImage(i.show(), 620, 300);  

                    frontleg.recolour(BodyColorArray);
                    i = frontleg;
                    gc.drawImage(i.show(), 617, 348);
                    
                    shortBack.recolour(shortColorArray);
                    i = shortBack;
                    gc.drawImage(i.show(), 641, 354);   
                    
                    shortFront.recolour(shortColorArray);
                    i = shortFront;
                    gc.drawImage(i.show(), 620, 354);   
                    
                    shortTop.recolour(shortColorArray);
                    i = shortTop;
                    gc.drawImage(i.show(), 620, 348);   

                     TshirtBody.recolour(ShirtColorArray);
                     i = TshirtBody;
                     gc.drawImage(i.show(), 614, 300); 
                     
                    frontarm.recolour(BodyColorArray);
                    i = frontarm;
                    gc.drawImage(i.show(), 602, 300);  
                    
                    backarm.recolour(BodyColorArray);
                    i = backarm;
                    gc.drawImage(i.show(), 656, 300);   
                    
                     TShirtBack.recolour(ShirtColorArray);
                     i = TShirtBack;
                     gc.drawImage(i.show(), 656, 301);
                     
                     TShirtFront.recolour(ShirtColorArray);
                     i = TShirtFront;
                     gc.drawImage(i.show(), 602, 300); 
                     
                     head.recolour(BodyColorArray);
                     i = head;
                     gc.drawImage(i.show(), 620, 264);
                    
                  
                     
                      
            
        
    }
        
        

             }