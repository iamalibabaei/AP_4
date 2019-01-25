package view.gameScene;

import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import models.Game;
import models.buildings.Warehouse;
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
        Image background = null;
        try {
            background = new Image(new FileInputStream("Textures\\Service\\Depot\\03.png"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        BackgroundSize backgroundSize = new BackgroundSize(width, height, false, false, false, false);
        BackgroundImage backgroundImage = new BackgroundImage(background, BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, backgroundSize);
        Pane pane = new Pane();
        pane.setMinSize(width, height);
        pane.setBackground(new Background(backgroundImage));
        getChildren().add(pane);
        Button exit = new Button("exit");
        exit.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                GameScene.getInstance().closeWarehouse();
            }
        });
        getChildren().addAll(exit);
    }

    public void UpdateInformation(){
        //TODO
    }


}
