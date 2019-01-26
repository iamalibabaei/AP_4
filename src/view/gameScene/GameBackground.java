package view.gameScene;

import controller.Controller;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextBoundsType;
import models.exceptions.InsufficientResourcesException;
import models.exceptions.InvalidArgumentException;
import models.objects.animal.Animal;
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
        setBuyAnimalButton();


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
            Text animalNameText = new Text(animalName);
            animalNameText.setFill(Color.YELLOW);
            addAnimal.getChildren().addAll(imageView, animalNameText);
            addAnimal.relocate(20 + animalButton.indexOf(animalName) * 80, 20);
            addAnimal.setOnMouseClicked(new EventHandler<MouseEvent>() {
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
            Text text = new Text(Integer.toString(type.BUY_COST));
            text.setFill(Color.YELLOW);
            text.setFont(Font.font ("Verdana", 15));

            text.setBoundsType(TextBoundsType.VISUAL);
            text.relocate(50 + animalButton.indexOf(animalName) * 80, 130);
            this.getChildren().addAll(addAnimal, text);
        }



    }


}
