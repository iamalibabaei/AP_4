package view.gameScene;

import controller.Controller;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.scene.text.TextBoundsType;
import models.exceptions.InsufficientResourcesException;
import models.exceptions.InvalidArgumentException;
import view.View;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class GameBackground extends Pane {
    private static GameBackground instance = new GameBackground();


    public static GameBackground getInstance() {
        return instance;
    }

    private GameBackground() {
        build();
    }

    private void build() {
        Image background = null;
        try {
            background = new Image(new FileInputStream("Textures\\back.png"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        BackgroundSize backgroundSize = new BackgroundSize(View.WIDTH, View.HEIGHT, false, false, false, false);
        BackgroundImage backgroundImage = new BackgroundImage(background, BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, backgroundSize);
        Pane pane = new Pane();
        pane.setMinSize(View.WIDTH * 2, View.HEIGHT);
        pane.setBackground(new Background(backgroundImage));
        this.getChildren().add(pane);
        setScene();


    }

    private void setScene() {//TODO change later
        ArrayList<String> animalButton = new ArrayList<>();
        animalButton.add("HEN");
        animalButton.add("SHEEP");
        animalButton.add("COW");
        animalButton.add("CAT");
        animalButton.add("DOG");
        for (String animalName : animalButton) {
            Circle circle = new Circle(25);
            circle.setFill(Color.BLUE);
            Text text = new Text(animalName);
            text.setBoundsType(TextBoundsType.VISUAL);
            StackPane addHen = new StackPane();
            addHen.getChildren().addAll(circle,text);
            addHen.relocate(40 + animalButton.indexOf(animalName) * 45, 40);
            addHen.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    try {
                        Controller.getInstance().buy(animalName.toLowerCase());
                    } catch (InsufficientResourcesException e) {
                        View.getInstance().showExceptions(e, 30, 30);
                    } catch (InvalidArgumentException e) {
                        e.printStackTrace();
                    }
                }
            });
            this.getChildren().addAll(addHen);
        }



    }


}
