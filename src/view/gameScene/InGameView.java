package view.gameScene;

import controller.InGameController;
import controller.MenuController;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;
import models.buildings.Warehouse;
import models.buildings.Well;
import models.exceptions.InsufficientResourcesException;
import models.exceptions.IsWorkingException;
import models.interfaces.Time;
import models.objects.Grass;
import models.objects.Point;
import models.objects.animals.Animal;
import models.transportation.Helicopter;
import models.transportation.Truck;
import view.MainView;
import view.gameScene.helicopter.HelicopterView;
import view.gameScene.truck.TruckView;
import view.utility.AddressConstants;
import view.utility.SpriteAnimation;
import view.utility.Utility;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class InGameView extends Scene implements Time
{
    private static InGameView instance = new InGameView();

    public static InGameView getInstance() {
        return instance;
    }

    private Group root;
    private Text money;

    private InGameView() {
        super(new Group(), MainView.WIDTH, MainView.HEIGHT, Color.BLACK);
        root = (Group) getRoot();
        build();
    }

    private void build() {
        root.getChildren().clear();
        root.getChildren().addAll(GameBackground.getInstance(), MapView.getInstance(), WarehouseScene.getInstance());
//        emptyWorkshopGraphic();
        wellGraphic();
        warehouseGraphic();
        moneyGraphic();
        truckGraphic();
        buildWorkshopGraphic();
        helicopterGraphic();
        root.getChildren().addAll(TruckView.getInstance());
        root.getChildren().addAll(WorkshopView.getInstance());
    }

    private void buildWorkshopGraphic() {
        for (int i = 0; i < 6; i++) {
            var placeRef = new Object() {
                int place = 0;
            };
            double XValue = MainView.WIDTH, YValue = MainView.HEIGHT;
            switch (i) {
                case 0:
                    placeRef.place = 1;
                    XValue = XValue * 0.2;
                    YValue = YValue * 0.3;
                    break;
                case 1 :
                    placeRef.place = 2;
                    XValue = XValue * 0.2;
                    YValue = YValue * 0.5;
                    break;
                case 2 :
                    placeRef.place = 3;
                    XValue = XValue * 0.2;
                    YValue = YValue * 0.65;
                    break;
                case 3 :
                    placeRef.place = 4;
                    XValue = XValue * 0.85;
                    YValue = YValue * 0.3;
                    break;
                case 4 :
                    placeRef.place = 5;
                    XValue = XValue * 0.85;
                    YValue = YValue * 0.5;
                    break;
                case 5 :
                    placeRef.place = 6;
                    XValue = XValue * 0.85;
                    YValue = YValue * 0.65;
                    break;

            }
            ImageView imageView = new ImageView(Utility.getImage(AddressConstants.BUILD_WORKSHOP_ICON));
            imageView.setFitWidth(50);
            imageView.setFitHeight(50);
            imageView.relocate(XValue, YValue);
            imageView.setOnMouseClicked(event -> WorkshopView.getInstance().open(placeRef.place));
            root.getChildren().addAll(imageView);


        }
    }

    private void emptyWorkshopGraphic() {
        ImageView place1 = new ImageView(Utility.getImage(AddressConstants.PLACES_ROOT + "place1.png"));
        place1.setFitWidth(MainView.WIDTH / 3.2375);
        place1.setFitHeight(MainView.HEIGHT / 5);
        StackPane place1Pane = new StackPane();
        place1Pane.getChildren().addAll(place1);
        place1Pane.relocate(MainView.WIDTH / 18, MainView.HEIGHT / 4.6);
        root.getChildren().addAll(place1Pane);
        place1Pane.setOnMouseClicked(event -> openWorkshopChoices(1));

        ImageView place2 = new ImageView(Utility.getImage(AddressConstants.PLACES_ROOT + "place2.png"));
        place2.setFitWidth(MainView.WIDTH / 4.2);
        place2.setFitHeight(MainView.HEIGHT / 4.7);
        StackPane place2Pane = new StackPane();
        place2Pane.getChildren().addAll(place2);
        place2Pane.relocate(0, MainView.HEIGHT / 2.4);
        root.getChildren().addAll(place2Pane);
        place2Pane.setOnMouseClicked(event -> openWorkshopChoices(2));

        ImageView place3 = new ImageView(Utility.getImage(AddressConstants.PLACES_ROOT + "place3.png"));
        place3.setFitWidth(MainView.WIDTH / 3.1);
        place3.setFitHeight(MainView.HEIGHT / 4.5);
        StackPane place3Pane = new StackPane();
        place3Pane.getChildren().addAll(place3);
        place3Pane.relocate(0, MainView.HEIGHT / 1.6);
        root.getChildren().addAll(place3Pane);
        place3Pane.setOnMouseClicked(event -> openWorkshopChoices(3));

        ImageView place4 = new ImageView(Utility.getImage(AddressConstants.PLACES_ROOT + "place4.png"));
        place4.setFitWidth(MainView.WIDTH / 3.3);
        place4.setFitHeight(MainView.HEIGHT / 4.5);
        StackPane place4Pane = new StackPane();
        place4Pane.getChildren().addAll(place4);
        double x = MainView.WIDTH - (MainView.WIDTH - place4.getImage().getWidth() * 2 + MainView.WIDTH / 3.3);
        place4Pane.relocate(MainView.WIDTH - place4.getImage().getWidth() * 2 + x, MainView.HEIGHT / 4.8);
        root.getChildren().addAll(place4Pane);
        place4Pane.setOnMouseClicked(event -> openWorkshopChoices(4));

        ImageView place5 = new ImageView(Utility.getImage(AddressConstants.PLACES_ROOT + "place5.png"));
        place5.setFitWidth(MainView.WIDTH / 3.4);
        place5.setFitHeight(MainView.HEIGHT / 4.7);
        StackPane place5Pane = new StackPane();
        place5Pane.getChildren().addAll(place5);
        x = MainView.WIDTH - (MainView.WIDTH - place5.getImage().getWidth() * 2 + MainView.WIDTH / 3.4);
        place5Pane.relocate(MainView.WIDTH - place5.getImage().getWidth() * 2 + x, MainView.HEIGHT / 2.4);
        root.getChildren().addAll(place5Pane);
        place5Pane.setOnMouseClicked(event -> openWorkshopChoices(5));

        ImageView place6 = new ImageView(Utility.getImage(AddressConstants.PLACES_ROOT + "place6.png"));
        place6.setFitWidth(MainView.WIDTH / 3.4);
        place6.setFitHeight(MainView.HEIGHT / 4.5);
        StackPane place6Pane = new StackPane();
        place6Pane.getChildren().addAll(place6);
        x = MainView.WIDTH - (MainView.WIDTH - place6.getImage().getWidth() * 2 + MainView.WIDTH / 3.4);
        place6Pane.relocate(MainView.WIDTH - place6.getImage().getWidth() * 2 + x
                , MainView.HEIGHT / 1.6);
        System.out.println("xxx" + (MainView.WIDTH - place6.getImage().getWidth() * 2 + MainView.WIDTH / 3.4));
        root.getChildren().addAll(place6Pane);
        place6Pane.setOnMouseClicked(event -> openWorkshopChoices(6));

    }

    private void openWorkshopChoices(int place) {

    }

    public void drawWorkshop(int place, String workshop) {//string hamun workshop.name hast
        switch (place){
            case 1:{

                break;
            }
            case 2:{

                break;
            }
            case 3:{

                break;
            }
            case 4:{

                break;
            }
            case 5:{

                break;
            }
            case 6:{

                break;
            }
        }
    }


    private void helicopterGraphic() {

        ImageView imageView = new ImageView(Utility.getImage(AddressConstants.HELICOPTER_PICTURE_ROOT + Helicopter.getInstance().getLevel() + ".png"));
        imageView.setFitWidth(MainView.WIDTH / 4.5);
        imageView.setFitHeight(MainView.HEIGHT / 4.5);
        StackPane helicopterPane = new StackPane();
        helicopterPane.getChildren().addAll(imageView);
        helicopterPane.relocate(MainView.WIDTH / 2 + imageView.getImage().getWidth() * 0.5, MainView.HEIGHT - imageView.getImage().getHeight() * 1.6);
        root.getChildren().addAll(helicopterPane);
        helicopterPane.setOnMouseClicked(event -> openHelicopter());
        root.getChildren().addAll(HelicopterView.getInstance());

    }

    private void openHelicopter() {
        HelicopterView.getInstance().updateInformation();
        HelicopterView.getInstance().setVisible(true);
    }

    private void truckGraphic() {

        ImageView imageView = new ImageView(Utility.getImage(AddressConstants.TRUCK_PICTURE_ROOT + Truck.getInstance().getLevel() + ".png"));
        imageView.setFitWidth(MainView.WIDTH / 5);
        imageView.setFitHeight(MainView.HEIGHT / 5);
        StackPane truckPane = new StackPane();
        truckPane.getChildren().addAll(imageView);
        truckPane.relocate(MainView.WIDTH / 2 - imageView.getImage().getWidth() * 3.7, MainView.HEIGHT - imageView.getImage().getHeight() * 1.9);
        root.getChildren().addAll(truckPane);
        truckPane.setOnMouseClicked(event -> TruckView.getInstance().openTruck());


    }

    public void getMoney(){
        money.setText(Integer.toString(InGameController.getInstance().getMoney()));
    }

    private void wellGraphic() {
        int XValue = (int) (MainView.WIDTH * 0.4), YValue = (int) (MainView.HEIGHT / 9);

        ImageView wellImageView = new ImageView(Utility.getImage(AddressConstants.WELL_PICTURE_ROOT +
                Well.getInstance().getLevel() + ".png"));
        wellImageView.relocate(XValue, YValue);
        wellImageView.setFitHeight(MainView.HEIGHT / 6);
        wellImageView.setFitWidth(MainView.WIDTH / 6);
        root.getChildren().addAll(wellImageView);

        SpriteAnimation wellSpriteAnimation = new SpriteAnimation(wellImageView, Duration.millis(1250), 16, 4,
                0, 0, (int) (wellImageView.getImage().getWidth() / 4),
                (int) wellImageView.getImage().getHeight() / 4);
        wellSpriteAnimation.stop();
        final boolean[] isWorking = {true};
        wellImageView.setOnMouseClicked(event -> {
            if (isWorking[0]) {
                isWorking[0] = false;
                try {
                    MenuController.getInstance().refillWell();
                } catch (IsWorkingException | InsufficientResourcesException e) {
                    MainView.getInstance().showExceptions(e, XValue, YValue);
                }
                wellSpriteAnimation.setCycleCount(Well.getInstance().REFILL_TIME[Well.getInstance().getLevel()]);
                wellSpriteAnimation.playFromStart();
                wellSpriteAnimation.setOnFinished(event1 -> {
                    wellSpriteAnimation.stop();
                    isWorking[0] = true;
                });
            }
        });
    }

    private void moneyGraphic() {
        money = new Text(Integer.toString(InGameController.getInstance().getMoney()));
        money.setFill(Color.YELLOW);
        money.setFont(Font.font(30));
        StackPane pane = new StackPane();
        pane.getChildren().addAll(money);
        pane.setAlignment(Pos.CENTER);
        pane.relocate(MainView.WIDTH * 0.7, MainView.HEIGHT / 32);
        root.getChildren().addAll(pane);
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
    public void closehelicopter() {
        HelicopterView.getInstance().setVisible(false);
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
        Image warehouseImage = null;
        try {
            warehouseImage = new Image(new FileInputStream(
                    AddressConstants.WAREHOUSE_PICTURE_ROOT + Warehouse.getInstance().getLevel() + ".png"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        ImageView imageView = new ImageView(warehouseImage);
        imageView.setFitHeight(MainView.HEIGHT / 5);
        imageView.setFitWidth(MainView.WIDTH / 5);
        StackPane warehousePane = new StackPane();
        warehousePane.getChildren().addAll(imageView);
        warehousePane.relocate((MainView.WIDTH - warehouseImage.getWidth()) * 0.4, MainView.HEIGHT - 1.55 * warehouseImage.getHeight());
        root.getChildren().addAll(warehousePane);
        warehousePane.setOnMouseClicked(event -> openWarehouse());


    }

    public void showTruckPath(){
        ImageView truckView = new ImageView(Utility.getImage(AddressConstants.TRUCK_MINI_PICTURE_ROOT + Truck.getInstance().getLevel() + "_mini.png"));
        truckView.setViewport(new Rectangle2D(0, 0, 48, 48));
        truckView.setFitWidth(MainView.WIDTH / 20);
        truckView.setFitHeight(MainView.HEIGHT / 20);
        truckView.relocate(MainView.WIDTH * 0.75, MainView.HEIGHT / 35);
        truckView.setScaleX(-1);
        root.getChildren().addAll(truckView);
        double end = MainView.WIDTH - MainView.WIDTH * 0.75 - 48;
        KeyValue xForGoing = new KeyValue(truckView.xProperty(), end);
        KeyFrame going = new KeyFrame(Duration.millis(2000), xForGoing);
        Timeline timeLineGoing = new Timeline(going);
        timeLineGoing.getKeyFrames().addAll(going);
//        VehicleTimeLine(truck, turnToMoveObjectToCityAndComeBack, truckView, timeLineGoing);
        timeLineGoing.play();

//        truckView.setViewport(new Rectangle2D(0, 0, 48, 48));

        Animation animation = new SpriteAnimation(truckView, Duration.millis(2000), 2, 2, 0, 0,
                (int) (truckView.getImage().getWidth() /  2), (int) (truckView.getImage().getHeight()));
        animation.setCycleCount(1);
        animation.play();
        animation.setOnFinished(event -> {
            root.getChildren().removeAll(truckView);
            truckView.relocate(MainView.WIDTH - 48, MainView.HEIGHT / 35);
            truckView.setScaleX(1);
            root.getChildren().addAll(truckView);
            KeyValue xForGoing1 = new KeyValue(truckView.xProperty(), -MainView.WIDTH / 75);

            KeyFrame going1 = new KeyFrame(Duration.millis(2000), xForGoing1);
            Timeline timeLineGoing1 = new Timeline(going1);
            timeLineGoing1.getKeyFrames().addAll(going1);
            timeLineGoing1.play();

            animation.setCycleCount(1);
            animation.play();
            animation.setOnFinished(event1 -> {
                root.getChildren().removeAll(truckView);
            });
        });

    }

}
