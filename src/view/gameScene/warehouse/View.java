package view.gameScene.warehouse;

import view.MainView;
import view.utility.constants.PictureAddresses;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import models.buildings.Warehouse;
import models.objects.Item;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class View extends Pane {
    private static View instance = new View();
    private static final int height = 500, width = 500;
    public static View getInstance() {
        return instance;
    }

    private View(){
        relocate(MainView.WIDTH/2 - width / 2, MainView.HEIGHT / 2 - height / 2);
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
        exit.setOnMouseClicked(event -> view.gameScene.View.getInstance().closeWarehouse());
        getChildren().addAll(exit);
        //////////////
        Button upgrade = new Button("upgradeWarehouse");
        upgrade.relocate(50, 20);
        upgrade.setOnMouseClicked(event -> {
            try {
                Warehouse.getInstance().upgrade();
            } catch (IOException e)
            {
                e.printStackTrace();
            }
        });
        getChildren().addAll(upgrade);
        int XValue = 50, YValue = 50;
        for (Item.Type item : Warehouse.getInstance().getStoredItems().keySet()) {
            Text text = new Text(item.name() + "---->" + Warehouse.getInstance().getStoredItems().get(item));
            text.relocate(XValue, YValue);
            YValue += 50;
            getChildren().addAll(text);
        }
    }

    private void setBackgroundStuff() {
        Image background = null;
        try {
            background = new Image(new FileInputStream(PictureAddresses.WAREHOUSE_PICTURE_ROOT + Warehouse.getInstance().getLevel() + ".png"));
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
