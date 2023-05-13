package it.polimi.ingsw.GUI;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class GUI extends Application {
    List<Sprite> sprites = new ArrayList<>();
    Group root = new Group();
    Timer timer;
   private Coord coord = new Coord();

    @Override
    public void start(Stage primaryStage) {
        Timer timer = startTimer();
        primaryStage.setTitle("Drawing Operations Test");
        Canvas canvas = new Canvas(1280, 720);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        root.getChildren().add(canvas);

        drawCards(gc);

        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    private void drawCards(GraphicsContext gc) {
        Image img = new Image( "Personal_Goals.png" );
        //gc.drawImage( img, 10, 10, 100, 100 );
        ImageView imageView = new ImageView(img);

        //Setting the position of the image
        imageView.setX(440);
        imageView.setY(100);
        imageView.setPreserveRatio(true);
        //imageView.setFitHeight(400);
        imageView.setFitWidth(400);
        root.getChildren().add(imageView);

    }


    private Timer startTimer(){
        Timer timer = new Timer("Timer");
        TimerTask task = new TimerTask() {
            public void run() {
                //coord.moveX(12);
                //System.out.println("HELLO " + coord.getX());
                Platform.runLater(() -> {
                    //moveImage();
                });
            }
        };
        long delay = 0;
        long period = 1000L;
        timer.schedule(task, delay, period);
        return timer;
    }


    public static void main(String[] args) {

        Application.launch(args);
    }

}
