package view.gameScene.truck;

import controller.InGameController;
import javafx.event.EventHandler;
import javafx.scene.Group;
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
        setTruckInfo();
    }

    private void setTruckInfo() {
        double XValue = MainView.WIDTH / 2 - 200;//todo fix numbers if needed
        double YValue = MainView.HEIGHT / 2 ;
        EnumMap<Item.Type, Integer> storedItems = Warehouse.getInstance().getStoredItems();
        for (Item.Type item : storedItems.keySet()) {
            ImageView itemImage = new ImageView(Utility.getImage(PictureAddresses.ITEM_ROOT + item.name().toLowerCase() + ".png"));
            itemImage.setFitWidth(50);
            itemImage.setFitHeight(50);
            itemImage.relocate(XValue, YValue);

            Text text = new Text(Integer.toString(storedItems.get(item)));
            text.setFont(Font.font("SWItalt", 15));
            text.relocate(XValue + 70, YValue);

            ImageView addToTruckImage = new ImageView(Utility.getImage(PictureAddresses.MENU_BUTTON));
            addToTruckImage.setFitHeight(100);
            addToTruckImage.setFitWidth(200);
            Text addToTruckText = new Text("add to truck");
            addToTruckText.setFont(Font.font("SWItalt", 15));
            TextField amount = new TextField();
            amount.setPromptText("amount");
            amount.relocate(XValue + 200, YValue);


            StackPane addToTruck = new StackPane();
            addToTruck.getChildren().addAll(addToTruckImage, addToTruckText);
            addToTruck.setOnMouseClicked(event -> addToTruck(item, amount.getText()));
            addToTruck.relocate(XValue + 150, YValue);

            getChildren().addAll(itemImage, text, addToTruck, amount);
            YValue += 100;


        }

    }

    private void addToTruck(Item.Type item, String amountStr) {
        int amount = 0;
        try {
            amount = Integer.parseInt(amountStr);
        } catch (Exception e) {
            return;
        }
        try {
            InGameController.getInstance().addToStash("truck", item.name(),amount );
        } catch (InvalidArgumentException e)
        {
            e.printStackTrace();
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        updateInformation();
        setVisible(true);
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
                    } catch (Exception e) {
                        Utility.showError(100, 100, e.getMessage());
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

        Text senTruckText = new Text("send truck");
        senTruckText.setFont(Font.font("SWItalt", 15));

        StackPane sendTruck = new StackPane();
        sendTruck.getChildren().addAll(sendTruckImage, senTruckText);
        sendTruck.setOnMouseClicked(event -> {
            InGameController.getInstance().sendTruck();
            view.gameScene.View.getInstance().closeTruck();
            view.gameScene.View.getInstance().showTruckPath();
            childrenList.remove(view.gameScene.View.getInstance().truckGraphic());
//            view.gameScene.View.getInstance().truckGraphic();
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
        clearStash.setOnMouseClicked(event -> {
            try {
                InGameController.getInstance().clearStash("truck");
            } catch (InvalidArgumentException e) {
                System.out.println("invalid Argument in truckView");
                return;
            }

            updateInformation();
            setVisible(true);

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
        back.setOnMouseClicked(event -> view.gameScene.View.getInstance().closeTruck());
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

    public void openTruck(){
        if (Truck.getInstance().isWorking()) {
            return;
        }
        view.gameScene.truck.View.getInstance().updateInformation();
        view.gameScene.truck.View.getInstance().setVisible(true);
    }
}
