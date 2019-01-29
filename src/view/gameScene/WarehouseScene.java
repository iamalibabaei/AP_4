package view.gameScene;

import view.AddressConstants;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import models.buildings.Warehouse;
import models.exceptions.AlreadyAtMaxLevelException;
import models.objects.Item;
import view.View;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class WarehouseScene extends Pane {
    private static WarehouseScene instance = new WarehouseScene();
    private static final int height = 500, width = 500;
    public static WarehouseScene getInstance() {
        return instance;
    }

    private WarehouseScene(){
        relocate(View.WIDTH/2 - width / 2, View.HEIGHT / 2 - height / 2);
        setHeight(height);
        setWidth(width);
        build();
    }

    private void build() {
        setBackgroundStuff();
        setButtons();
        setVisible(false);

    }

    private void setButtons() {
        Button exit = new Button("exit");
        exit.relocate(20, 20);
        exit.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                GameScene.getInstance().closeWarehouse();
            }
        });
        getChildren().addAll(exit);
        //////////////
        Button upgrade = new Button("upgradeWarehouse");
        upgrade.relocate(50, 20);
        upgrade.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                try {
                    Warehouse.getInstance().upgrade();
                } catch (AlreadyAtMaxLevelException e) {
                    View.getInstance().showExceptions(e, View.WIDTH/2 - width / 2, View.HEIGHT / 2 - height / 2);
                }
            }
        });
        getChildren().addAll(upgrade);
        int XValue = 50, YValue = 50;
        for (Item.Type item : Warehouse.getInstance().getStoredItems().keySet()) {
            Text text = new Text(item.name() + "---->" + Integer.toString(Warehouse.getInstance().getStoredItems().get(item)));
            text.relocate(XValue, YValue);
            YValue += 50;
            getChildren().addAll(text);
        }
    }

    private void setBackgroundStuff() {
        Image background = null;
        try {
            background = new Image(new FileInputStream(AddressConstants.WAREHOUSE_PICTURE_ROOT + Warehouse.getInstance().getLevel() + ".png"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        BackgroundSize backgroundSize = new BackgroundSize(width, height, false, false, false, false);
        BackgroundImage backgroundImage = new BackgroundImage(background, BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, backgroundSize);
        Pane pane = new Pane();
        pane.setMinSize(width, height);
        pane.setBackground(new Background(backgroundImage));
        getChildren().add(pane);
    }

    public void UpdateInformation(){
        getChildren().clear();
        build();
    }


}
