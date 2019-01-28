package view.gameScene;

import controller.Controller;
import controller.InGameController;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextBoundsType;
import models.exceptions.InsufficientResourcesException;
import models.exceptions.IsWorkingException;
import models.objects.Grass;
import models.objects.Item;
import models.objects.Point;
import models.objects.animals.Animal;
import view.View;

public class GameScene extends Scene
{
    private static GameScene instance = new GameScene();

    public static GameScene getInstance() {
        return instance;
    }

    private Group root;
    private Text money;

    public GameScene() {
        super(new Group(), View.WIDTH, View.HEIGHT);
        root = (Group) getRoot();
        build();
    }

    private void build() {

        root.getChildren().clear();
        root.getChildren().addAll(GameBackground.getInstance(), MapView.getInstance(), WarehouseScene.getInstance());
        wellGraphic();
        warehouseGraphic();
        moneyGraphic();
        truckGraphic();
        root.getChildren().addAll(TruckView.getInstance());
    }

    private void truckGraphic() {
        int XValue = 200, YValue = 600;
        Circle circle = new Circle(25);
        circle.setFill(Color.BLUE);
        Text text = new Text("truck");
        text.setBoundsType(TextBoundsType.VISUAL);
        StackPane truckGraphic = new StackPane();
        truckGraphic.getChildren().addAll(circle,text);
        truckGraphic.relocate(XValue, YValue);
        truckGraphic.setOnMouseClicked(event -> openTruck());

        root.getChildren().addAll(truckGraphic);

    }

    private void moneyGraphic() {
        money = new Text(Integer.toString(InGameController.getInstance().getMoney()));
        money.relocate(1000, 100);
        money.setFill(Color.YELLOW);
        money.setFont(Font.font(30));
        root.getChildren().addAll(money);
    }

    public void updateMoneyInformation(){
        money.setText(Integer.toString(InGameController.getInstance().getMoney()));
    }

    private void wellGraphic() {
        int XValue = 750, YValue = 130;
        Circle circle = new Circle(25);
        circle.setFill(Color.BLUE);
        Text text = new Text("well");
        text.setBoundsType(TextBoundsType.VISUAL);
        StackPane wellGraphic = new StackPane();
        wellGraphic.getChildren().addAll(circle,text);
        wellGraphic.relocate(XValue, YValue);
        wellGraphic.setOnMouseClicked(event -> {
            try {
                Controller.getInstance().refillWell();
            } catch (IsWorkingException | InsufficientResourcesException e) {
                View.getInstance().showExceptions(e, XValue, YValue);
            }
        });

        root.getChildren().addAll(wellGraphic);
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

    private void openWarehouse() {
        WarehouseScene.getInstance().UpdateInformation();
        WarehouseScene.getInstance().setVisible(true);

    }

    public void closeWarehouse() {
        //root.getChildren().remove(WarehouseScene.getInstance());
        WarehouseScene.getInstance().setVisible(false);
    }

    public void addAnimal(Animal animal, Point location) {
        Text text = animal.getText();
        text.relocate((location.getX() + 325) *  MapView.WIDTH_BASE, location.getY() * MapView.HEIGHT_BASE);
        MapView.getInstance().getChildren().addAll(text);
    }

    public void addGrass(Grass entity, Point location) {
        Text text = entity.getText();
        text.relocate((location.getX() + 325) *  MapView.WIDTH_BASE, location.getY() * MapView.HEIGHT_BASE);
        MapView.getInstance().getChildren().addAll(text);
    }

    public void openTruck(){
        TruckView.getInstance().updateInformation();
        TruckView.getInstance().setVisible(true);
    }

    public void closeTruck() {
        TruckView.getInstance().setVisible(false);
    }


    public void addItem(Item entity, Point location) {
        Text text = entity.getText();
        text.relocate((location.getX() + 325) *  MapView.WIDTH_BASE, location.getY() * MapView.HEIGHT_BASE);
        MapView.getInstance().getChildren().addAll(text);
    }
    public void removeAnimal(Animal animal){
        MapView.getInstance().getChildren().remove(animal.getText());
    }
    public void removeItem(Item item){
        MapView.getInstance().getChildren().remove(item);
    }
    public void removeGrass(Grass grass) {
        MapView.getInstance().getChildren().remove(grass);
    }
    public void moveAnimal(Animal animal, Point target) {
        Text text = animal.getText();
        if (animal.getCoordinates().distanceFrom(target) != 0) {
            double differenceX = animal.getCoordinates().getX() - target.getX();
            double differenceY = animal.getCoordinates().getY() - target.getY();
            double factor = Math.max(Math.abs(differenceX), Math.abs(differenceY));
            differenceX = differenceX / factor;
            differenceY = differenceY / factor;
            text.setScaleX(text.getLayoutX() + 10 * differenceX);
            text.setScaleY(text.getLayoutY() + 10 * differenceY);
        }
    }
}
