package view.gameScene;

import controller.AddressConstants;
import controller.MenuController;
import controller.InGameController;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextBoundsType;
import models.buildings.Well;
import models.exceptions.InsufficientResourcesException;
import models.exceptions.IsWorkingException;
import models.interfaces.Time;
import models.objects.Grass;
import models.objects.Point;
import models.objects.animals.Animal;
import view.MainView;
import view.gameScene.truck.TruckView;

public class InGameView extends Scene implements Time
{
    private static InGameView instance = new InGameView();

    public static InGameView getInstance() {
        return instance;
    }

    private Group root;
    private Text money;

    private InGameView() {
        super(new Group(), MainView.WIDTH, MainView.HEIGHT);
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

    public void getMoney(){
        money.setText(Integer.toString(InGameController.getInstance().getMoney()));
    }

    private void wellGraphic(Well well) {
        int XValue = 750, YValue = 130;
        Image wellImage = AddressConstants.getImage(AddressConstants.WELL_PICTURE_ROOT + well.getLevel() + ".png");
        ImageView wellImageView = new ImageView(wellImage);

        Circle circle = new Circle(25);
        circle.setFill(Color.BLUE);
        Text text = new Text("well");
        text.setBoundsType(TextBoundsType.VISUAL);
        StackPane wellGraphic = new StackPane();
        wellGraphic.getChildren().addAll(circle,text);
        wellGraphic.relocate(XValue, YValue);
        wellGraphic.setOnMouseClicked(event -> {
            try {
                MenuController.getInstance().refillWell();
            } catch (IsWorkingException | InsufficientResourcesException e) {
                MainView.getInstance().showExceptions(e, XValue, YValue);
            }
        });

        root.getChildren().addAll(wellGraphic);
    }

    @Override
    public void nextTurn() {
        getMoney();
        MapView.getInstance().nextTurn();
        GameBackground.getInstance().nextTurn();
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
    public void closeTruck() {
        TruckView.getInstance().setVisible(false);
    }
    public void openTruck(){
        TruckView.getInstance().updateInformation();
        TruckView.getInstance().setVisible(true);
    }

    public void closeWarehouse() {
        //root.getChildren().remove(WarehouseScene.getInstance());
        WarehouseScene.getInstance().setVisible(false);
    }

    private void openWarehouse() {
        WarehouseScene.getInstance().UpdateInformation();
        WarehouseScene.getInstance().setVisible(true);

    }


    private void warehouseGraphic() {
        int XValue = MainView.WIDTH/ 2 - 10, YValue = MainView.HEIGHT - 50;
        Circle circle = new Circle(25);
        circle.setFill(Color.BLUE);
        Text text = new Text("warehouse");
        text.setBoundsType(TextBoundsType.VISUAL);
        StackPane warehouseGraphic = new StackPane();
        warehouseGraphic.getChildren().addAll(circle,text);
        warehouseGraphic.relocate(XValue, YValue);
        warehouseGraphic.setOnMouseClicked(event -> openWarehouse());

        root.getChildren().addAll(warehouseGraphic);
    }
}
