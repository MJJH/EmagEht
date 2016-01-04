    package gui;

    import display.Image;
    import java.io.IOException;
import java.util.ArrayList;
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
        private Color[] LegColorArray;
        private Color[] temp;      
        private Color[] tempclear;


        private Image heart;
            ColorPicker HeadColor = new ColorPicker();
            ColorPicker BodyColor = new ColorPicker();
            ColorPicker LegColor = new ColorPicker();

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
              HeadColorArray = temp;
              BodyColorArray = temp;
              LegColorArray = temp;

              head = new display.Image(display.Parts.playerHead);
              body = new display.Image(display.Parts.playerTorso);
              frontarm = new display.Image(display.Parts.playerFrontArm);
              backarm = new display.Image(display.Parts.playerBackArm);
              frontleg = new display.Image(display.Parts.playerFrontLeg);
              backleg = new display.Image(display.Parts.playerBackLeg);


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
            
            
            double[] points = new double[]{25 , 5 , 45 , 45, 5 , 45};
            Polygon p = new Polygon(points);
            root.getChildren().add(p);
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
            
             p.setOnMouseClicked(new EventHandler<MouseEvent>()
            {
            @Override
            public void handle(MouseEvent t) {
                p.setFill(Color.RED);
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
                    gc.drawImage(i.show(), 304, 128);    

                    backarm.recolour(BodyColorArray);
                    i = backarm;
                    gc.drawImage(i.show(), 312, 112);    


                    body.recolour(BodyColorArray);
                    i = body;
                    gc.drawImage(i.show(), 300, 112);  

                    frontleg.recolour(BodyColorArray);
                    i = frontleg;
                    gc.drawImage(i.show(), 299, 128);  

                    frontarm.recolour(BodyColorArray);
                    i = frontarm;
                    gc.drawImage(i.show(), 296, 112);  


                    head.recolour(BodyColorArray);
                     i = head;
                    gc.drawImage(i.show(), 300, 100);
    }
        
        

             }
