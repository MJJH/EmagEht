    package gui;

    import display.Image;
import display.Parts;
import display.Sets;
    import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
    import javafx.application.Application;
import javafx.event.ActionEvent;
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
import sun.security.pkcs11.wrapper.PKCS11Constants;
import thegame.com.Menu.Account;
import thegame.com.storage.Database;



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
    private Image player;
    private Image tshirt;
    private Image shorts;
    private Color[] shortColorArray;
    private Account account;
    
    

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
              

              
              player = new display.Image(display.Sets.sets.get("player"));
              player.Scale(3);
              
              shorts = new display.Image(display.Sets.sets.get("shorts"));
              shorts.Scale(3);
              
              tshirt = new display.Image(display.Sets.sets.get("tShirt"));
              tshirt.Scale(3);
            
              
              

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
            
           // double[] pointTopLeft = new double[]{(width * 0.4) , (height * 0.33) , (width * 0.4) , (height * 0.378), (width * 0.36) , (height * 0.354)};
            //Polygon TriangleTopLeft = new Polygon(pointTopLeft);
            
            double[] pointMiddleLeft = new double[]{(width * 0.4) , (height * 0.40) , (width * 0.4) , (height * 0.448), (width * 0.36) , (height * 0.424)};
            Polygon TriangleMiddleLeft = new Polygon(pointMiddleLeft);
            
            double[] pointBottomLeft = new double[]{(width * 0.4) , (height * 0.49) , (width * 0.4) , (height * 0.538), (width * 0.36) , (height * 0.514)};
            Polygon TriangleBottomLeft = new Polygon(pointBottomLeft);
            
         //   double[] pointTopRight = new double[]{(width * 0.6) , (height * 0.33) , (width * 0.6) , (height * 0.378), (width * 0.64) , (height * 0.354)};
         //   Polygon TriangleTopRight = new Polygon(pointTopRight);
            
            double[] pointMiddleRight = new double[]{(width * 0.6) , (height * 0.40) , (width * 0.6) , (height * 0.448), (width * 0.64) , (height * 0.424)};
            Polygon TriangleMiddleRight = new Polygon(pointMiddleRight);
            
            double[] pointBottomRight = new double[]{(width * 0.6) , (height * 0.49) , (width * 0.6) , (height * 0.538), (width * 0.64) , (height * 0.514)};
            Polygon TriangleBottomRight = new Polygon(pointBottomRight);
            
            
            root.getChildren().add(TriangleBottomLeft);
            root.getChildren().add(TriangleMiddleLeft);
            //root.getChildren().add(TriangleTopLeft);
            root.getChildren().add(TriangleBottomRight);
            root.getChildren().add(TriangleMiddleRight);
           // root.getChildren().add(TriangleTopRight);
            

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
             
             TriangleMiddleRight.setOnMouseClicked(new EventHandler<MouseEvent>()
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
              
             TriangleBottomRight.setOnMouseClicked(new EventHandler<MouseEvent>()
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
            Save.setOnAction(new EventHandler() {

                    @Override
                    public void handle(Event event) {
                    
                    
                    java.awt.Color bodycolorone = new java.awt.Color((float)BodyColorArray[0].getRed(),(float)BodyColorArray[0].getGreen(),(float) BodyColorArray[0].getBlue(), (float)BodyColorArray[0].getOpacity());
                    java.awt.Color bodycolortwo = new java.awt.Color((float)BodyColorArray[1].getRed(),(float)BodyColorArray[1].getGreen(),(float) BodyColorArray[1].getBlue(), (float)BodyColorArray[1].getOpacity());
                    java.awt.Color bodycolorthree = new java.awt.Color((float)BodyColorArray[2].getRed(),(float)BodyColorArray[2].getGreen(),(float) BodyColorArray[2].getBlue(), (float)BodyColorArray[2].getOpacity());
                    java.awt.Color bodycolorfour = new java.awt.Color((float)BodyColorArray[3].getRed(),(float)BodyColorArray[3].getGreen(),(float) BodyColorArray[3].getBlue(), (float)BodyColorArray[3].getOpacity());
                    java.awt.Color bodycolorfive = new java.awt.Color((float)BodyColorArray[4].getRed(),(float)BodyColorArray[4].getGreen(),(float) BodyColorArray[4].getBlue(), (float)BodyColorArray[4].getOpacity());
                    java.awt.Color bodycolorsix = new java.awt.Color((float)BodyColorArray[5].getRed(),(float)BodyColorArray[5].getGreen(),(float) BodyColorArray[5].getBlue(), (float)BodyColorArray[5].getOpacity());
                    
                    java.awt.Color shirtcolorone = new java.awt.Color(0,0,0,0);
                    java.awt.Color shirtcolortwo = new java.awt.Color((float)ShirtColorArray[1].getRed(),(float)ShirtColorArray[1].getGreen(),(float) ShirtColorArray[1].getBlue(), (float)ShirtColorArray[1].getOpacity());
                    java.awt.Color shirtcolorthree = new java.awt.Color((float)ShirtColorArray[2].getRed(),(float)ShirtColorArray[2].getGreen(),(float) ShirtColorArray[2].getBlue(), (float)ShirtColorArray[2].getOpacity());
                    java.awt.Color shirtcolorfour = new java.awt.Color((float)ShirtColorArray[3].getRed(),(float)ShirtColorArray[3].getGreen(),(float) ShirtColorArray[3].getBlue(), (float)ShirtColorArray[3].getOpacity());
                    java.awt.Color shirtcolorfive = new java.awt.Color((float)ShirtColorArray[4].getRed(),(float)ShirtColorArray[4].getGreen(),(float) ShirtColorArray[4].getBlue(), (float)ShirtColorArray[4].getOpacity());
                    java.awt.Color shirtcolorsix = new java.awt.Color((float)ShirtColorArray[5].getRed(),(float)ShirtColorArray[5].getGreen(),(float) ShirtColorArray[5].getBlue(), (float)ShirtColorArray[5].getOpacity());
                    
                    java.awt.Color shortscolorone = new java.awt.Color(0,0,0,0);
                    java.awt.Color shortscolortwo = new java.awt.Color((float)shortColorArray[1].getRed(),(float)shortColorArray[1].getGreen(),(float) shortColorArray[1].getBlue(), (float)shortColorArray[1].getOpacity());
                    java.awt.Color shortscolorthree = new java.awt.Color((float)shortColorArray[2].getRed(),(float)shortColorArray[2].getGreen(),(float) shortColorArray[2].getBlue(), (float)shortColorArray[2].getOpacity());
                    java.awt.Color shortscolorfour = new java.awt.Color((float)shortColorArray[3].getRed(),(float)shortColorArray[3].getGreen(),(float) shortColorArray[3].getBlue(), (float)shortColorArray[3].getOpacity());
                    java.awt.Color shortscolorfive   = new java.awt.Color((float)shortColorArray[4].getRed(),(float)shortColorArray[4].getGreen(),(float) shortColorArray[4].getBlue(), (float)shortColorArray[4].getOpacity());
                    java.awt.Color shortscolorsix = new java.awt.Color((float)shortColorArray[5].getRed(),(float)shortColorArray[5].getGreen(),(float) shortColorArray[5].getBlue(), (float)shortColorArray[5].getOpacity());
                    
                    
                    
                    
                    
                    int accountid = 1;
                    Database s = Database.getDatabase();
                 
                    s.insertColors(account.id, name.getText(), "body" , bodycolorone, bodycolortwo, bodycolorthree, bodycolorfour, bodycolorfive, bodycolorsix);
                    s.insertColors(account.id, name.getText(), "shorts" , shortscolorone, shortscolortwo, shortscolorthree, shortscolorfour, shortscolorfive, shortscolorsix);
                    s.insertColors(account.id, name.getText(), "shirt" , shirtcolorone, shirtcolortwo, shirtcolorthree, shirtcolorfour, shirtcolorfive, shirtcolorsix);
                    
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
        public JavaFXColorPicker(Account account)
        {
            this.account = account;
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

                    
             
            

                    player.recolour(BodyColorArray);
                    Image i = player;
                    gc.drawImage(i.show(), 605, 250);  
                    
                    shorts.recolour(shortColorArray);
                    i = shorts;
                    gc.drawImage(i.show(), 620, 330);    
            
                     tshirt.recolour(ShirtColorArray);
                     i = tshirt;
                     gc.drawImage(i.show(), 602, 287); 
                     
                    
                    
                  
                     
                      
            
        
    }
        
        

             }
