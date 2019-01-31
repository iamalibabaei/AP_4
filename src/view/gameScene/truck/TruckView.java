package view.gameScene.truck;

import controller.InGameController;
import javafx.event.EventHandler;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import models.buildings.Warehouse;
import models.exceptions.InvalidArgumentException;
import models.exceptions.NotEnoughSpaceException;
import models.objects.Item;
import models.transportation.Truck;
import view.MainView;
import view.gameScene.InGameView;
import view.utility.AddressConstants;
import view.utility.Utility;

import java.util.EnumMap;

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
        //setItems();
        setTruckInfo();
    }

    private void setTruckInfo() {
        int XValue = MainView.WIDTH / 2 - 200, YValue = MainView.HEIGHT / 2 ;//todo fix numbers if needed
        EnumMap<Item.Type, Integer> storedItems = Warehouse.getInstance().getStoredItems();
        for (Item.Type item : storedItems.keySet()) {
            ImageView itemImage = new ImageView(Utility.getImage(AddressConstants.ITEM_ROOT + item.name().toLowerCase() + ".png"));
            itemImage.setFitWidth(50);
            itemImage.setFitHeight(50);
            itemImage.relocate(XValue, YValue);

            Text text = new Text(Integer.toString(storedItems.get(item)));
            text.setFont(Font.font("SWItalt", 15));
            text.relocate(XValue + 70, YValue);

            ImageView addToTruckImage = new ImageView(Utility.getImage(AddressConstants.MENU_BUTTON));
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
        } catch (NotEnoughSpaceException e) {
            return;
        } catch (InvalidArgumentException e) {
            System.out.println("invallid argument in truckView");
            return;
        }
        updateInformation();
        setVisible(true);
    }

//    private void setItems() {
//        EnumMap<Item.Type, Integer>  storedItems=  Warehouse.getInstance().getStoredItems();
//        int XValue = 30, YValue = 50;
//        for (Item.Type item : storedItems.keySet()) {
//            String str = item.name() + "  ---->  " + storedItems.get(item);
//            Text text = new Text(str);
//            text.relocate(XValue, YValue);
//            TextField textField = new TextField();
//            text.relocate(XValue + 100, YValue);
//            Button button = new Button("sell");
//            button.setOnMouseClicked(new EventHandler<MouseEvent>() {
//                @Override
//                public void handle(MouseEvent event) {
//                    try {
//                        InGameController.getInstance().addToStash("truck", item.name(), Integer.parseInt(textField.getText()));
//                    } catch (NotEnoughSpaceException e) {
//                        MainView.getInstance().showExceptions(e, 100, 100);
//                    } catch (InvalidArgumentException e) {
//                        MainView.getInstance().showExceptions(e, 100, 100);
//                    }
//                    updateInformation();
//                    setVisible(true);
//
//                }
//            });
//            getChildren().addAll(text, textField, button);
//            YValue += 30;
//        }
//    }

    private void setButtons() {
        ImageView sendTruckImage = new ImageView(Utility.getImage(AddressConstants.MENU_BUTTON));
        sendTruckImage.setFitHeight(100);
        sendTruckImage.setFitWidth(200);

        Text senTruckText = new Text("send truck");
        senTruckText.setFont(Font.font("SWItalt", 15));

        StackPane sendTruck = new StackPane();
        sendTruck.getChildren().addAll(sendTruckImage, senTruckText);
        sendTruck.setOnMouseClicked(event -> InGameController.getInstance().sendTruck());
        sendTruck.relocate(0, 0);
        getChildren().addAll(sendTruck);

        ImageView clearStashImage = new ImageView(Utility.getImage(AddressConstants.MENU_BUTTON));
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
                    InGameController.getInstance().clearStash("truck");
                } catch (InvalidArgumentException e) {
                    System.out.println("invalid Argument in truckView");
                    return;
                }

                updateInformation();
                setVisible(true);

            }
        });
        clearStash.relocate(0, 100);
        getChildren().addAll(clearStash);

        ImageView backImage = new ImageView(Utility.getImage(AddressConstants.MENU_BUTTON));
        backImage.setFitHeight(100);
        backImage.setFitWidth(200);

        Text backText = new Text("back");
        backText.setFont(Font.font("SWItalt", 15));

        StackPane back = new StackPane();
        back.getChildren().addAll(backImage, backText);
        back.setOnMouseClicked(event -> InGameView.getInstance().closehelicopter());
        back.relocate(0, 200);
        getChildren().addAll(back);

    }

    private void wallpaper() {
        ImageView back = new ImageView(Utility.getImage(AddressConstants.TRUCK_INSIDE));
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
        TruckView.getInstance().updateInformation();
        TruckView.getInstance().setVisible(true);
    }
}
