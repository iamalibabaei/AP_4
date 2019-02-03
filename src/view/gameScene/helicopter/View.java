package view.gameScene.helicopter;

import controller.InGameController;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import models.buildings.Warehouse;
import models.exceptions.InvalidArgumentException;
import models.objects.Item;
import models.transportation.Truck;
import view.MainView;
import view.PaneBuilder;
import view.utility.constants.PictureAddresses;
import view.utility.Utility;

import java.io.IOException;
import java.util.ArrayList;
import java.util.EnumMap;

public class View extends PaneBuilder {
    private static View instance = new View();
    public static View getInstance() {
        return instance;
    }

    private View() {
        super(0, 0, MainView.WIDTH, MainView.HEIGHT);
        setVisible(false);
        build();
    }


    protected void build() {
        wallpaper();
        setButtons();
        setItems();
        setHelicopterInfo();
    }

    private void setHelicopterInfo() {
        double XValue = MainView.WIDTH / 2 - 200;//todo fix numbers if needed
        double YValue = MainView.HEIGHT / 2 ;
        ArrayList<Item.Type> items = new ArrayList<>();
        items.add( Item.Type.PLUME);
        items.add(Item.Type.FLOUR);
        for (Item.Type item : items) {
            ImageView itemImage = new ImageView(Utility.getImage(PictureAddresses.ITEM_ROOT + item.name().toLowerCase() + ".png"));
            itemImage.setFitWidth(50);
            itemImage.setFitHeight(50);
            itemImage.relocate(XValue, YValue);

            ImageView addToHelicopterImage = new ImageView(Utility.getImage(PictureAddresses.MENU_BUTTON));
            addToHelicopterImage.setFitHeight(100);
            addToHelicopterImage.setFitWidth(200);
            Text addToTruckText = new Text("add helicopter");
            addToTruckText.setFont(Font.font("SWItalt", 15));
            TextField amount = new TextField();
            amount.setPromptText("amount");
            amount.relocate(XValue + 200, YValue);

            StackPane addToTruck = new StackPane();
            addToTruck.getChildren().addAll(addToHelicopterImage, addToTruckText);
            addToTruck.setOnMouseClicked(event -> addTohelicopter(item, amount.getText()));
            addToTruck.relocate(XValue + 150, YValue);

            getChildren().addAll(itemImage, addToTruck, amount);
            YValue += 100;
        }

    }

    private void addTohelicopter(Item.Type item, String amountStr) {
        int amount = 0;
        try {
            amount = Integer.parseInt(amountStr);
        } catch (Exception e) {
            return;
        }
        try {
            InGameController.getInstance().addToStash("helicopter", item.name(),amount );
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        updateInformation();
        setVisible(true);
    }

    private void setItems() {
        EnumMap<Item.Type, Integer> storedItems=  Warehouse.getInstance().getStoredItems();
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
                    } catch (Exception e) {
                        Utility.showError( 100, 100, e.getMessage());
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
        ImageView sendTruckImage = new ImageView(Utility.getImage(PictureAddresses.MENU_BUTTON));
        sendTruckImage.setFitHeight(100);
        sendTruckImage.setFitWidth(200);

        Text senTruckText = new Text("send");
        senTruckText.setFont(Font.font("SWItalt", 15));

        StackPane sendTruck = new StackPane();
        sendTruck.getChildren().addAll(sendTruckImage, senTruckText);
        sendTruck.setOnMouseClicked(event -> {
            try {
                InGameController.getInstance().sendHelicopter();
                view.gameScene.View.getInstance().closehelicopter();
                view.gameScene.View.getInstance().showHelicopterPath();
            } catch (IOException e)
            {
                e.printStackTrace();
            }
            //TODO

        });
        sendTruck.relocate(0, 0);
        getChildren().addAll(sendTruck);

        ImageView clearStashImage = new ImageView(Utility.getImage(PictureAddresses.MENU_BUTTON));
        clearStashImage.setFitHeight(100);
        clearStashImage.setFitWidth(200);

        Text clearText = new Text("clearStash");
        clearText.setFont(Font.font("SWItalt", 15));

        StackPane clearStash = new StackPane();
        clearStash.getChildren().addAll(clearStashImage, clearText);
        clearStash.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                try {
                    InGameController.getInstance().clearStash("helicopter");
                } catch (InvalidArgumentException e) {
                    System.out.println("invalid Argument in helicopterView");
                    return;
                }

                updateInformation();
                setVisible(true);
            }

        });
        clearStash.relocate(0, 100);
        getChildren().addAll(clearStash);

        ImageView backImage = new ImageView(Utility.getImage(PictureAddresses.MENU_BUTTON));
        backImage.setFitHeight(100);
        backImage.setFitWidth(200);

        Text backText = new Text("back");
        backText.setFont(Font.font("SWItalt", 15));

        StackPane back = new StackPane();
        back.getChildren().addAll(backImage, backText);
        back.setOnMouseClicked(event -> view.gameScene.View.getInstance().closehelicopter());
        back.relocate(0, 200);
        getChildren().addAll(back);

    }

    private void wallpaper() {
        ImageView back = new ImageView(Utility.getImage(PictureAddresses.TRUCK_INSIDE));
        back.setFitWidth(350);
        back.setFitHeight(600);
        back.relocate(MainView.WIDTH * 0.3 ,MainView.HEIGHT * 0.2);
        getChildren().addAll(back);
    }

    public void updateInformation() {
        getChildren().clear();
        build();
    }

    public void openHelicopter(){
        if (Truck.getInstance().isWorking()) {
            return;
        }
        view.gameScene.helicopter.View.getInstance().updateInformation();
        view.gameScene.helicopter.View.getInstance().setVisible(true);
    }
}
