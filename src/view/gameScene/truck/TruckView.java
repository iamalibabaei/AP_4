package view.gameScene.truck;

import controller.InGameController;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import models.Map;
import models.buildings.Warehouse;
import models.exceptions.InvalidArgumentException;
import models.exceptions.NotEnoughSpaceException;
import models.objects.Item;
import models.objects.animals.Animal;
import models.objects.animals.DomesticAnimal;
import models.transportation.Truck;
import view.MainView;
import view.gameScene.InGameView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.EnumMap;
import java.util.HashMap;

public class TruckView extends Pane {
    private static TruckView instance = new TruckView();
    public static TruckView getInstance() {
        return instance;
    }

    private TruckView() {
        relocate(0 , 0);
        setHeight(MainView.HEIGHT);
        setWidth(MainView.WIDTH);
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
        HashMap<DomesticAnimal.Type, Integer> animalCurrentState = new HashMap<>();
        HashMap<Item.Type, Integer> itemCurrentState = new HashMap<>();
        Map map = Map.getInstance();
        for (Item item : map.getItems())
        {
            itemCurrentState.put(item.type, itemCurrentState.getOrDefault(item.type, 0) + 1);
        }

        for (Animal animal : map.getAnimals())
        {
            if (animal instanceof DomesticAnimal)
            {
                animalCurrentState.put(((DomesticAnimal) animal).type
                        , animalCurrentState.getOrDefault(((DomesticAnimal) animal).type, 0) + 1);
            }
        }
        Text onMapText = new Text("on the map:");
        onMapText.relocate(XValue, YValue);
        getChildren().addAll(onMapText);
        YValue += 50;
        for(Animal.Type item : animalCurrentState.keySet()) {
            String str = item.name() + "  -------->  " + animalCurrentState.get(item);
            Text text = new Text(str);
            text.relocate(XValue, YValue);
            getChildren().addAll(text);
            YValue += 50;
        }
        for(Item.Type item : itemCurrentState.keySet()) {
            String str = item.name() + "  -------->  " + animalCurrentState.get(item);
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
                        MainView.getInstance().showExceptions(e, 100, 100);
                    } catch (InvalidArgumentException e) {
                        MainView.getInstance().showExceptions(e, 100, 100);
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
        relocate(0, 0);
        exit.relocate(MainView.WIDTH / 2 - 100, MainView.HEIGHT - 50);
        exit.setOnMouseClicked(event -> InGameView.getInstance().closeTruck());

        Button sendTruck = new Button("sendTruck");
//        sendTruck.relocate(30, 10);
        sendTruck.relocate(MainView.WIDTH / 2 + 100, MainView.HEIGHT - 50);
        sendTruck.setOnMouseClicked(event -> InGameController.getInstance().sendTruck());
        getChildren().addAll(exit, sendTruck);
    }

    private void wallpaper() {
        Image background = null;
        try {
            background = new Image(new FileInputStream("res/graphicAssets/truckInSideView.jpg"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        BackgroundSize backgroundSize = new BackgroundSize(MainView.WIDTH, MainView.HEIGHT, false,
                false, false, false);
        BackgroundImage backgroundImage = new BackgroundImage(background, BackgroundRepeat.REPEAT,
                BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, backgroundSize);
        Pane pane = new Pane();
        pane.setMinSize(MainView.WIDTH * 2, MainView.HEIGHT);
        pane.setBackground(new Background(backgroundImage));
        this.getChildren().add(pane);

    }
    public void updateInformation() {
        getChildren().clear();
        build();
    }


}
