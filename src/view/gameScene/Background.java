package view.gameScene;

import controller.InGameController;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import models.exceptions.InsufficientResourcesException;
import models.exceptions.InvalidArgumentException;
import models.interfaces.Time;
import models.objects.animals.Animal;
import view.MainView;
import view.utility.constants.PictureAddresses;
import view.utility.Utility;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class Background extends Pane implements Time {
    private static Background instance = new Background();


    public static Background getInstance() {
        return instance;
    }

    private Background() {
        this.setHeight(MainView.HEIGHT);
        this.setWidth(MainView.WIDTH);
        System.out.println("hieghtttt = " + this.getWidth());
        build();
    }

    private void build() {
        setBackgroundStuff();
        setBuyAnimalButton();
        setUnderBar();
        setUpperBar();
    }

    private void setUpperBar() {
        Image upperBarImage = null;
        try {
            upperBarImage = new Image(new FileInputStream(PictureAddresses.GAME_BACKGROUND_ROOT + "upperBar.png"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        ImageView imageView = new ImageView(upperBarImage);
        imageView.setFitHeight(90);

        StackPane upperBarPane = new StackPane();
        upperBarPane.getChildren().addAll(imageView);
        upperBarPane.relocate(MainView.WIDTH - upperBarImage.getWidth(), 0);
        this.getChildren().addAll(upperBarPane);

    }

    private void setBackgroundStuff() {
        Image background = null;
        background = Utility.getImage(PictureAddresses.GAME_BACKGROUND_ROOT + "background.png");

        BackgroundSize backgroundSize = new BackgroundSize(MainView.WIDTH, MainView.HEIGHT, false,
                false, false, false);
        BackgroundImage backgroundImage = new BackgroundImage(background, BackgroundRepeat.REPEAT,
                BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, backgroundSize);
        Pane pane = new Pane();
        pane.setMinSize(MainView.WIDTH * 2, MainView.HEIGHT);
        pane.setBackground(new javafx.scene.layout.Background(backgroundImage));
        this.getChildren().addAll(pane);
    }

    private void setUnderBar(){
        Image underBarImage = null;
        try {
            underBarImage = new Image(new FileInputStream(PictureAddresses.GAME_BACKGROUND_ROOT + "underBar.png"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        ImageView imageView = new ImageView(underBarImage);
        imageView.setFitWidth(MainView.WIDTH);
        imageView.setFitHeight(MainView.HEIGHT / 7.7);

        StackPane underBarPane = new StackPane();
        underBarPane.getChildren().addAll(imageView);
        underBarPane.relocate(0, MainView.HEIGHT - imageView.getImage().getHeight() * 0.6);
        this.getChildren().addAll(underBarPane);

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

            if (type.BUY_COST < InGameController.getInstance().getMoney()) {
                System.out.println(type.BUY_COST);
                System.out.println(InGameController.getInstance().getMoney());
                try {
                    backImage = new Image(new FileInputStream(
                            PictureAddresses.ANIMAL_ICONS_ROOT + type.toString().toLowerCase() + "Icon.png"));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
            else {
                System.out.println(type.BUY_COST);
                System.out.println(InGameController.getInstance().getMoney());
                try {
                    backImage = new Image(new FileInputStream(
                            PictureAddresses.ANIMAL_ICONS_ROOT + type.toString().toLowerCase() + "IconGray.png"));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
            ImageView imageView = new ImageView(backImage);
            imageView.setFitHeight(MainView.HEIGHT / 14);
            imageView.setFitWidth(MainView.WIDTH / 14);

            StackPane addAnimal = new StackPane();            addAnimal.getChildren().addAll(imageView);
            addAnimal.relocate(20 + animalButton.indexOf(animalName) * MainView.WIDTH / 14, 20);
            addAnimal.setOnMouseClicked(event -> {
                try {
                    InGameController.getInstance().buyAnimal(animalName.toLowerCase());
                } catch (InsufficientResourcesException e) {
                    MainView.getInstance().showExceptions(e, 30, 30);
                } catch (InvalidArgumentException e) {
                    e.printStackTrace();
                }
            });
            this.getChildren().addAll(addAnimal);
        }



    }

    @Override
    public void nextTurn() {
        // if not enough money color of buttons becomes brown
        for (Node node : getChildren()) {
            if (node instanceof Text) {
                try {
                    if (Integer.parseInt(((Text) node).getText()) > InGameController.getInstance().getMoney()) {
                        ((Text) node).setFill(Color.BROWN);
                    } else {
                        ((Text) node).setFill(Color.YELLOW);
                    }
                } catch (Exception e) {
                    //nothing really
                }
            }
        }
    }
}
