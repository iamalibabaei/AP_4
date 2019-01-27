package view.gameScene;

import controller.InGameController;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import models.exceptions.InsufficientResourcesException;
import models.exceptions.InvalidArgumentException;
import models.objects.animals.Animal;
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
        setBackgroundStuff();
        setBuyAnimalButton();
    }

    private void setBackgroundStuff() {
        Image background = null;
        try {
            background = new Image(new FileInputStream("Textures\\back.png"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        BackgroundSize backgroundSize = new BackgroundSize(View.WIDTH, View.HEIGHT, false,
                false, false, false);
        BackgroundImage backgroundImage = new BackgroundImage(background, BackgroundRepeat.REPEAT,
                BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, backgroundSize);
        Pane pane = new Pane();
        pane.setMinSize(View.WIDTH * 2, View.HEIGHT);
        pane.setBackground(new Background(backgroundImage));
        this.getChildren().add(pane);
    }

    private void setBuyAnimalButton() {//TODO change later
        ArrayList<String> animalButton = new ArrayList<>();
        animalButton.add("HEN");
        animalButton.add("SHEEP");
        animalButton.add("COW");
        animalButton.add("CAT");
        animalButton.add("DOG");
        for (String animalName : animalButton) {
            Animal.Type type = Animal.Type.valueOf(animalName);
            Image backImage = null;

            try {
                backImage = new Image(new FileInputStream("Textures\\menuItemButton.png"));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            ImageView imageView = new ImageView(backImage);
            imageView.setFitHeight(100);
            imageView.setFitWidth(100);

            StackPane addAnimal = new StackPane();
            Text text = new Text(animalName);
            text.setFill(Color.YELLOW);
            addAnimal.getChildren().addAll(imageView, text);
            addAnimal.relocate(20 + animalButton.indexOf(animalName) * 100, 20);
            addAnimal.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    try {
                        InGameController.getInstance().buyAnimal(animalName.toLowerCase());
                    } catch (InsufficientResourcesException e) {
                        View.getInstance().showExceptions(e, 30, 30);
                    } catch (InvalidArgumentException e) {
                        e.printStackTrace();
                    }
                }
            });
            Text buyCost = new Text(Integer.toString(type.BUY_COST));
            buyCost.setFill(Color.YELLOW);
            buyCost.relocate(60 + animalButton.indexOf(animalName) * 100, 100);

            this.getChildren().addAll(addAnimal, buyCost);
        }



    }

}
