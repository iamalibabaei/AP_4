package view.gameScene;

import view.utility.AddressConstants;
import controller.InGameController;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import models.buildings.Warehouse;
import models.exceptions.InsufficientResourcesException;
import models.exceptions.InvalidArgumentException;
import models.interfaces.Time;
import models.objects.animals.Animal;
import view.utility.Utility;
import view.View;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class GameBackground extends Pane implements Time {
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
        setWarehouse();
        setTruck();
        setWell();
        setUnderBar();
    }

    private void setWell() {
//        Image wellImage = null;
//        try {
//            wellImage = new Image(new FileInputStream(".png"));
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
//
//        ImageView imageView = new ImageView(wellImage);
//        StackPane wellPane = new StackPane();
//        wellPane.getChildren().addAll(imageView);
//        wellPane.relocate(View.WIDTH / 2, View.HEIGHT );
//        this.getChildren().addAll(wellPane);
    }

    private void setTruck() {
        Image truckImage = null;
        try {
            truckImage = new Image(new FileInputStream(
                    AddressConstants.TRUCK_PICTURE_ROOT + Warehouse.getInstance().getLevel() + ".png"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        ImageView imageView = new ImageView(truckImage);
        StackPane truckPane = new StackPane();
        truckPane.getChildren().addAll(imageView);
        truckPane.relocate(View.WIDTH / 2 - truckImage.getWidth() * 3.5, View.HEIGHT - truckImage.getHeight());
        this.getChildren().addAll(truckPane);

    }

    private void setBackgroundStuff() {
        Image background = null;
        background = Utility.getImage(AddressConstants.GAME_BACKGROUND_ROOT + "background.png");

        BackgroundSize backgroundSize = new BackgroundSize(View.WIDTH, View.HEIGHT, false,
                false, false, false);
        BackgroundImage backgroundImage = new BackgroundImage(background, BackgroundRepeat.REPEAT,
                BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, backgroundSize);
        Pane pane = new Pane();
        pane.setMinSize(View.WIDTH * 2, View.HEIGHT);
        pane.setBackground(new Background(backgroundImage));
        this.getChildren().addAll(pane);
    }

    private void setUnderBar(){
        Image underBarImage = null;
        try {
            underBarImage = new Image(new FileInputStream(AddressConstants.GAME_BACKGROUND_ROOT + "underBar.png"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        ImageView imageView = new ImageView(underBarImage);
        imageView.setFitWidth(View.WIDTH);

        StackPane underBarPane = new StackPane();
        underBarPane.getChildren().addAll(imageView);
        underBarPane.relocate(0, View.HEIGHT - underBarImage.getHeight());
        this.getChildren().addAll(underBarPane);

    }

    private void setWarehouse() {
        Image warehouseImage = null;
        try {
            warehouseImage = new Image(new FileInputStream(
                    AddressConstants.WAREHOUSE_PICTURE_ROOT + Warehouse.getInstance().getLevel() + ".png"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        ImageView imageView = new ImageView(warehouseImage);

        StackPane warehpusePane = new StackPane();
        warehpusePane.getChildren().addAll(imageView);
        warehpusePane.relocate((View.WIDTH - warehouseImage.getWidth()) / 2, View.HEIGHT - warehouseImage.getHeight());
        this.getChildren().addAll(warehpusePane);

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
                            AddressConstants.ANIMAL_ICONS_ROOT + type.toString().toLowerCase() + "Icon.png"));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
            else {
                System.out.println(type.BUY_COST);
                System.out.println(InGameController.getInstance().getMoney());
                try {
                    backImage = new Image(new FileInputStream(
                            AddressConstants.ANIMAL_ICONS_ROOT + type.toString().toLowerCase() + "IconGray.png"));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
            ImageView imageView = new ImageView(backImage);
            imageView.setFitHeight(100);
            imageView.setFitWidth(100);

            StackPane addAnimal = new StackPane();
//            Text text = new Text(animalName);
//            text.setFill(Color.YELLOW);
            addAnimal.getChildren().addAll(imageView);
            addAnimal.relocate(20 + animalButton.indexOf(animalName) * 100, 20);
            addAnimal.setOnMouseClicked(event -> {
                try {
                    InGameController.getInstance().buyAnimal(animalName.toLowerCase());
                } catch (InsufficientResourcesException e) {
                    View.getInstance().showExceptions(e, 30, 30);
                } catch (InvalidArgumentException e) {
                    e.printStackTrace();
                }
            });
//            Text buyCost = new Text(Integer.toString(type.BUY_COST));
//            buyCost.setFill(Color.YELLOW);
//            buyCost.relocate(60 + animalButton.indexOf(animalName) * 100, 100);
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
