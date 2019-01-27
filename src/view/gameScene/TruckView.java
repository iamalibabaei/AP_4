package view.gameScene;

import controller.Controller;
import controller.InGameController;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import models.buildings.Warehouse;
import models.exceptions.InvalidArgumentException;
import models.exceptions.NotEnoughSpaceException;
import models.objects.Item;
import models.transportation.Truck;
import view.View;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.EnumMap;

public class TruckView extends Pane {
    private static TruckView instance = new TruckView();
    public static TruckView getInstance() {
        return instance;
    }

    private TruckView() {
        relocate(0 , 0);
        setHeight(View.HEIGHT);
        setWidth(View.WIDTH);
        setVisible(false);
        build();
    }

    private void build() {
        wallpaper();
        setButtons();
        setItems();
        setTruckInfo();
    }

    private void setTruckInfo() {
        EnumMap<Item.Type, Integer> list = Truck.getInstance().getList();
        int XValue = 500, YValue = 50;
        for(Item.Type item : list.keySet()) {
            String str = item.name() + "  -------->  " + list.get(item);
            Text text = new Text(str);
            text.relocate(XValue, YValue);
            getChildren().addAll(text);


            YValue += 50;
        }
    }

    private void setItems() {
        EnumMap<Item.Type, Integer>  storedItems=  Warehouse.getInstance().getStoredItems();
        int XValue = 30, YValue = 50;
        for (Item.Type item : storedItems.keySet()) {
            String str = item.name() + "  ---->  " + storedItems.get(item);
            Text text = new Text(str);
            text.relocate(XValue, YValue);
            TextField textField = new TextField();
            text.relocate(XValue + 100, YValue);
            Button button = new Button("sell");
            button.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    try {
                        InGameController.getInstance().addToStash("truck", item.name(), Integer.parseInt(textField.getText()));
                    } catch (NotEnoughSpaceException e) {
                        View.getInstance().showExceptions(e, 100, 100);
                    } catch (InvalidArgumentException e) {
                        View.getInstance().showExceptions(e, 100, 100);
                    }
                    updateInformation();
                    setVisible(true);

                }
            });
            getChildren().addAll(text, textField, button);
            YValue += 30;
        }
    }

    private void setButtons() {
        Button exit = new Button("exit");
        relocate(10, 10);
        exit.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                GameScene.getInstance().closeTruck();
            }
        });

        Button sendTruck = new Button("sendTruck");
        sendTruck.relocate(30, 10);
        sendTruck.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                InGameController.getInstance().sendTruck();
            }
        });
        getChildren().addAll(exit, sendTruck);
    }

    private void wallpaper() {
        Image background = null;
        try {
            background = new Image(new FileInputStream("res/Textures/truck.jpg"));
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
    public void updateInformation() {
        getChildren().clear();
        build();
    }


}
