package view.gameScene;

import controller.Controller;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.scene.text.TextBoundsType;
import models.buildings.Warehouse;
import models.exceptions.InsufficientResourcesException;
import models.exceptions.IsWorkingException;
import view.View;

public class GameScene extends Scene
{
    private static GameScene instance = new GameScene();

    public static GameScene getInstance() {
        return instance;
    }

    private Group root;
    private ImageView background;

    public GameScene() {
        super(new Group(), View.WIDTH, View.HEIGHT);
        root = (Group) getRoot();
        build();
    }

    private void build() {

        root.getChildren().clear();
        root.getChildren().addAll(GameBackground.getInstance());
        root.getChildren().addAll(MapView.getInstance());
        wellGraphic();
        warehouseGraphic();
    }

    private void warehouseGraphic() {
        int XValue = View.WIDTH/ 2 - 10, YValue = View.HEIGHT - 50;
        Circle circle = new Circle(25);
        circle.setFill(Color.BLUE);
        Text text = new Text("warehouse");
        text.setBoundsType(TextBoundsType.VISUAL);
        StackPane warehouseGraphic = new StackPane();
        warehouseGraphic.getChildren().addAll(circle,text);
        warehouseGraphic.relocate(XValue, YValue);
        warehouseGraphic.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                openWarehouse();
            }
        });

        root.getChildren().addAll(warehouseGraphic);



    }

    private void wellGraphic() {
        int XValue = 200, YValue = 200;
        Circle circle = new Circle(25);
        circle.setFill(Color.BLUE);
        Text text = new Text("well");
        text.setBoundsType(TextBoundsType.VISUAL);
        StackPane wellGraphic = new StackPane();
        wellGraphic.getChildren().addAll(circle,text);
        wellGraphic.relocate(XValue, YValue);
        wellGraphic.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                try {
                    Controller.getInstance().refillWell();
                } catch (IsWorkingException e) {
                    View.getInstance().showExceptions(e, XValue, YValue);
                } catch (InsufficientResourcesException e) {
                    View.getInstance().showExceptions(e, XValue, YValue);
                }
            }
        });

        root.getChildren().addAll(wellGraphic);
    }

    private void openWarehouse() {
        WarehouseScene.getInstance().UpdateInformation();
        root.getChildren().add(WarehouseScene.getInstance());

    }

    public void closeWarehouse() {
        root.getChildren().remove(WarehouseScene.getInstance());
    }
}
