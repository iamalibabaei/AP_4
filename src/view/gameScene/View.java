package view.gameScene;

import controller.InGameController;
import controller.MenuController;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;
import models.buildings.Warehouse;
import models.buildings.Well;
import models.buildings.Workshop;
import models.transportation.Helicopter;
import models.transportation.Truck;
import view.MainView;
import view.SceneBuilder;
import view.utility.SoundPlayer;
import view.utility.SpriteAnimation;
import view.utility.Utility;
import view.utility.constants.PictureAddresses;
import view.utility.constants.SoundAddresses;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class View extends SceneBuilder {
    private static View instance = new View();
    private Text money;

    private View() {
        super(MainView.WIDTH, MainView.HEIGHT, Color.BLACK);
        getRoot().relocate(MainView.OFFSET_X, MainView.OFFSET_Y);
    }

    public static View getInstance() {
        return instance;
    }

    @Override
    protected void build() {
        childrenList.addAll(Background.getInstance());
        childrenList.addAll(MapView .getInstance());
        wellGraphic();
        warehouseGraphic();
        moneyGraphic();
        childrenList.addAll(truckGraphic());
        buildWorkshopGraphic();
        helicopterGraphic();
        gameMenuButton();
        childrenList.addAll(view.gameScene.truck.View.getInstance(), view.gameScene.helicopter.View.getInstance());
        childrenList.addAll(view.gameScene.workshop.View.getInstance());
        childrenList.addAll(GameMenu.getInstance());
        childrenList.addAll(view.settings.View.getInstance());
        SoundPlayer.getInstance().playBackground(Utility.getSound(SoundAddresses.DEFAULT_INGAME_MUSIC));
    }

    private void gameMenuButton() {
        Utility.makeMenuButton(childrenList, -MainView.HEIGHT * 0.05, MainView.HEIGHT * 0.9,
                MainView.HEIGHT * 0.2, MainView.HEIGHT * 0.1
                , "MENU", event -> {
                    InGameController.getInstance().pauseGame();
                    GameMenu.getInstance().play();
                });

    }

    private void wellGraphic() {
        double XValue = MainView.WIDTH * 0.4;
        double YValue = MainView.HEIGHT / 9;

        ImageView wellImageView =
                Utility.getImageView(PictureAddresses.WELL_PICTURE_ROOT + Well.getInstance().getLevel() + ".png");
        wellImageView.relocate(XValue, YValue);
        wellImageView.setFitHeight(MainView.HEIGHT / 6);
        wellImageView.setFitWidth(MainView.WIDTH / 6);
        childrenList.addAll(wellImageView);

        SpriteAnimation wellSpriteAnimation = new SpriteAnimation(wellImageView, Duration.millis(1250), 16, 4,
                0, 0, (int) (wellImageView.getImage().getWidth() / 4),
                (int) wellImageView.getImage().getHeight() / 4);
        wellSpriteAnimation.stop();
        final boolean[] isWorking = {true};
        wellImageView.setOnMouseClicked(event -> {
            if (isWorking[0]) {
                isWorking[0] = false;
                try {
                    Well.getInstance().issueRefill();
                } catch (Exception e) {
                    childrenList.addAll(Utility.showError(50, 100, "not enough money"));
                    return;
                }
                wellSpriteAnimation.setCycleCount(Well.getInstance().REFILL_TIME[Well.getInstance().getLevel()]);
                wellSpriteAnimation.playFromStart();
                wellSpriteAnimation.setOnFinished(event1 -> {
                    wellSpriteAnimation.stop();
                    isWorking[0] = true;
                    try {
                        MenuController.getInstance().refillWell();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });


            }
        });
    }

    private void warehouseGraphic() {
        Image warehouseImage = null;
        try {
            warehouseImage = new Image(new FileInputStream(
                    PictureAddresses.WAREHOUSE_PICTURE_ROOT + Warehouse.getInstance().getLevel() + ".png"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        ImageView imageView = new ImageView(warehouseImage);
        imageView.setFitHeight(MainView.HEIGHT / 5);
        imageView.setFitWidth(MainView.WIDTH / 5);
        StackPane warehousePane = new StackPane();
        warehousePane.getChildren().addAll(imageView);
        warehousePane.relocate((MainView.WIDTH - warehouseImage.getWidth()) * 0.4,
                MainView.HEIGHT - 1.55 * warehouseImage.getHeight());
        childrenList.addAll(warehousePane);
        warehousePane.setOnMouseClicked(event -> openWarehouse());


    }

    private void moneyGraphic() {
        money = new Text(Integer.toString(InGameController.getInstance().getMoney()));
        money.setFill(Color.GOLD);
        money.setFont(Font.font(50));
        StackPane pane = new StackPane();
        pane.getChildren().addAll(money);
        pane.setAlignment(Pos.CENTER);
        pane.relocate(MainView.WIDTH * 0.68, MainView.HEIGHT / 15);
        ImageView moneyImageview = new ImageView(Utility.getImage(PictureAddresses.GAME_BACKGROUND_ROOT + "money.png"));
        moneyImageview.setFitWidth(moneyImageview.getImage().getWidth() / 2);
        moneyImageview.setFitHeight(moneyImageview.getImage().getHeight() / 2);
        moneyImageview.relocate(MainView.WIDTH * 0.69, MainView.HEIGHT / 45);
        SpriteAnimation moneyAnimation = new SpriteAnimation(moneyImageview, Duration.millis(1200), 16, 4,
                0, 0, (int) (moneyImageview.getImage().getWidth() / 4),
                (int) (moneyImageview.getImage().getHeight() / 4));
        moneyAnimation.setCycleCount(Animation.INDEFINITE);
        moneyAnimation.play();
        childrenList.addAll(pane, moneyImageview);
    }

    public StackPane truckGraphic() {

        ImageView imageView =
                new ImageView(Utility.getImage(PictureAddresses.TRUCK_PICTURE_ROOT + Truck.getInstance().getLevel() + ".png"));
        imageView.setFitWidth(MainView.WIDTH / 5);
        imageView.setFitHeight(MainView.HEIGHT / 5);
        StackPane truckPane = new StackPane();
        truckPane.getChildren().addAll(imageView);
        truckPane.relocate(MainView.WIDTH / 2 - imageView.getImage().getWidth() * 3.7,
                MainView.HEIGHT - imageView.getImage().getHeight() * 1.9);
        truckPane.setOnMouseClicked(event -> {
            view.gameScene.truck.View.getInstance().openTruck();
        });
        return truckPane;

    }

    private void buildWorkshopGraphic() {
        for (int i = 0; i < 6; i++) {
            int place = 0;
            double XValue = MainView.WIDTH, YValue = MainView.HEIGHT;
            switch (i) {
                case 0:
                    place = 1;
                    XValue *= 0.15;
                    YValue *= 0.3;
                    break;
                case 1:
                    place = 2;
                    XValue *= 0.15;
                    YValue *= 0.5;
                    break;
                case 2:
                    place = 3;
                    XValue *= 0.15;
                    YValue *= 0.68;
                    break;
                case 3:
                    place = 4;
                    XValue *= 0.8;
                    YValue *= 0.28;
                    break;
                case 4:
                    place = 5;
                    XValue *= 0.85;
                    YValue *= 0.5;
                    break;
                case 5:
                    place = 6;
                    XValue *= 0.85;
                    YValue *= 0.70;
                    break;

            }
            ImageView imageView = new ImageView(Utility.getImage(PictureAddresses.BUILD_WORKSHOP_ICON));
            imageView.setFitWidth(50);
            imageView.setFitHeight(50);
            imageView.relocate(XValue, YValue);
            int finalPlace = place;
            imageView.setOnMouseClicked(event -> view.gameScene.workshop.View.getInstance().open(finalPlace));
            childrenList.add(finalPlace, imageView);
        }
    }

    private void helicopterGraphic() {

        ImageView imageView =
                new ImageView(Utility.getImage(PictureAddresses.HELICOPTER_PICTURE_ROOT + Helicopter.getInstance().getLevel() + ".png"));
        imageView.setFitWidth(MainView.WIDTH / 4.5);
        imageView.setFitHeight(MainView.HEIGHT / 4.5);
        StackPane helicopterPane = new StackPane();
        helicopterPane.getChildren().addAll(imageView);
        helicopterPane.relocate(MainView.WIDTH / 2 + imageView.getImage().getWidth() * 0.5,
                MainView.HEIGHT - imageView.getImage().getHeight() * 1.6);
        childrenList.addAll(helicopterPane);
        helicopterPane.setOnMouseClicked(event -> openHelicopter());

    }

    private void openWarehouse() {
        view.gameScene.warehouse.View.getInstance().UpdateInformation();
        view.gameScene.warehouse.View.getInstance().setVisible(true);

    }

    private void openHelicopter() {
        view.gameScene.helicopter.View.getInstance().updateInformation();
        view.gameScene.helicopter.View.getInstance().setVisible(true);
    }

    public void drawWorkshop(int place, Workshop workshop) {//string hamun workshop.name hast
        switch (place) {
            case 1: {
                ImageView imageView = new ImageView(Utility.getImage(PictureAddresses.WORKSHOP_PICTURE_ROOT + workshop.name + "/" + workshop.getLevel() + ".png"));
                imageView.relocate(MainView.WIDTH * 0.07, MainView.HEIGHT * 0.18);
                imageView.setFitHeight(MainView.HEIGHT / 5);
                imageView.setFitWidth(MainView.WIDTH / 7);
                imageView.setScaleX(-1);
                if (workshop.name.equals("eggPowderPlant") || workshop.name.equals("sewingFactory"))
                    imageView.setScaleX(1);
                childrenList.remove(place);
                childrenList.add(place, imageView);

                System.out.println(PictureAddresses.WORKSHOP_ROOT + workshop.name + "/" + workshop.getLevel() + ".png");
                SpriteAnimation spriteAnimation = new SpriteAnimation(imageView, Duration.millis(1250), 16, 4,
                        0, 0, (int) (imageView.getImage().getWidth() / 4),
                        (int) imageView.getImage().getHeight() / 4);
                spriteAnimation.stop();

                imageView.setOnMouseClicked(event -> workWorkshop(workshop, spriteAnimation));
                break;
            }
            case 2: {
                ImageView imageView = new ImageView(Utility.getImage(PictureAddresses.WORKSHOP_PICTURE_ROOT + workshop.name + "/" + workshop.getLevel() + ".png"));
                imageView.relocate(MainView.WIDTH * 0.07, MainView.HEIGHT * 0.40);
                imageView.setFitHeight(MainView.HEIGHT / 5);
                imageView.setFitWidth(MainView.WIDTH / 7);
                imageView.setScaleX(-1);
                if (workshop.name.equals("eggPowderPlant") || workshop.name.equals("sewingFactory"))
                    imageView.setScaleX(1);
                childrenList.remove(place);
                childrenList.add(place, imageView);

                System.out.println(PictureAddresses.WORKSHOP_ROOT + workshop.name + "/" + workshop.getLevel() + ".png");
                SpriteAnimation spriteAnimation = new SpriteAnimation(imageView, Duration.millis(1250), 16, 4,
                        0, 0, (int) (imageView.getImage().getWidth() / 4),
                        (int) imageView.getImage().getHeight() / 4);
                spriteAnimation.stop();

                imageView.setOnMouseClicked(event -> {
                    workWorkshop(workshop, spriteAnimation);
                });
                break;
            }
            case 3: {

                ImageView imageView = new ImageView(Utility.getImage(PictureAddresses.WORKSHOP_PICTURE_ROOT + workshop.name + "/" + workshop.getLevel() + ".png"));
                imageView.relocate(MainView.WIDTH * 0.05, MainView.HEIGHT * 0.65);
                imageView.setFitHeight(MainView.HEIGHT / 5);
                imageView.setFitWidth(MainView.WIDTH / 7);
                imageView.setScaleX(-1);
                if (workshop.name.equals("eggPowderPlant") || workshop.name.equals("sewingFactory"))
                    imageView.setScaleX(1);

                childrenList.remove(place);
                childrenList.add(place, imageView);
                System.out.println("before anim");
                System.out.println(PictureAddresses.WORKSHOP_ROOT + workshop.name + "/" + workshop.getLevel() + ".png");
                SpriteAnimation spriteAnimation = new SpriteAnimation(imageView, Duration.millis(1250), 16, 4,
                        0, 0, (int) (imageView.getImage().getWidth() / 4),
                        (int) imageView.getImage().getHeight() / 4);
                spriteAnimation.stop();
                System.out.println("after anim");
                imageView.setOnMouseClicked(event -> {
                    workWorkshop(workshop, spriteAnimation);
                });
                break;
            }
            case 4: {

                ImageView imageView = new ImageView(Utility.getImage(PictureAddresses.WORKSHOP_PICTURE_ROOT + workshop.name + "/" + workshop.getLevel() + ".png"));
                imageView.relocate(MainView.WIDTH * 0.73, MainView.HEIGHT * 0.17);
                imageView.setFitHeight(MainView.HEIGHT / 5);
                imageView.setFitWidth(MainView.WIDTH / 7);
                imageView.setScaleX(1);
                if (workshop.name.equals("eggPowderPlant")) imageView.setScaleX(-1);
                childrenList.remove(place);
                childrenList.add(place, imageView);
                System.out.println("before anim");
                System.out.println(PictureAddresses.WORKSHOP_ROOT + workshop.name + "/" + workshop.getLevel() + ".png");
                SpriteAnimation spriteAnimation = new SpriteAnimation(imageView, Duration.millis(1250), 16, 4,
                        0, 0, (int) (imageView.getImage().getWidth() / 4),
                        (int) imageView.getImage().getHeight() / 4);
                spriteAnimation.stop();
                imageView.setOnMouseClicked(event -> {
                    workWorkshop(workshop, spriteAnimation);

                });
                break;
            }
            case 5: {

                ImageView imageView = new ImageView(Utility.getImage(PictureAddresses.WORKSHOP_PICTURE_ROOT + workshop.name + "/" + workshop.getLevel() + ".png"));
                imageView.relocate(MainView.WIDTH * 0.75, MainView.HEIGHT * 0.37);
                imageView.setFitHeight(MainView.HEIGHT / 5);
                imageView.setFitWidth(MainView.WIDTH / 7);
                imageView.setScaleX(1);
                if (workshop.name.equals("eggPowderPlant")) imageView.setScaleX(-1);
                childrenList.remove(place);
                childrenList.add(place, imageView);
                System.out.println("before anim");
                System.out.println(PictureAddresses.WORKSHOP_ROOT + workshop.name + "/" + workshop.getLevel() + ".png");
                SpriteAnimation spriteAnimation = new SpriteAnimation(imageView, Duration.millis(1250), 16, 4,
                        0, 0, (int) (imageView.getImage().getWidth() / 4),
                        (int) imageView.getImage().getHeight() / 4);
                spriteAnimation.stop();
                imageView.setOnMouseClicked(event -> {
                    workWorkshop(workshop, spriteAnimation);
                });
                break;
            }
            case 6: {

                ImageView imageView = new ImageView(Utility.getImage(PictureAddresses.WORKSHOP_PICTURE_ROOT + workshop.name + "/" + workshop.getLevel() + ".png"));
                imageView.relocate(MainView.WIDTH * 0.75, MainView.HEIGHT * 0.6);
                imageView.setFitHeight(MainView.HEIGHT / 5);
                imageView.setFitWidth(MainView.WIDTH / 7);
                imageView.setScaleX(1);
                if (workshop.name.equals("eggPowderPlant")) imageView.setScaleX(-1);
                childrenList.remove(place);
                childrenList.add(place, imageView);
                System.out.println("before anim");
                System.out.println(PictureAddresses.WORKSHOP_ROOT + workshop.name + "/" + workshop.getLevel() + ".png");
                SpriteAnimation spriteAnimation = new SpriteAnimation(imageView, Duration.millis(1250), 16, 4,
                        0, 0, (int) (imageView.getImage().getWidth() / 4),
                        (int) imageView.getImage().getHeight() / 4);
                spriteAnimation.stop();
                imageView.setOnMouseClicked(event -> {
                    workWorkshop(workshop, spriteAnimation);
                });
                break;
            }
        }
    }


    public void getMoney() {
        money.setText(Integer.toString(InGameController.getInstance().getMoney()));
    }

    public void closehelicopter() {
        view.gameScene.helicopter.View.getInstance().setVisible(false);
    }

    public void closeTruck() {
        view.gameScene.truck.View.getInstance().setVisible(false);
    }

    public void openTruck() {
        view.gameScene.truck.View.getInstance().updateInformation();
        view.gameScene.truck.View.getInstance().setVisible(true);
    }

    public void closeWarehouse() {
        childrenList.remove(View.getInstance());
        view.gameScene.warehouse.View.getInstance().setVisible(false);
    }

    public void showTruckPath() {
        ImageView truckView = new ImageView(Utility.getImage(PictureAddresses.TRUCK_MINI_PICTURE_ROOT + Truck.getInstance().getLevel() + "_mini.png"));
        truckView.setViewport(new Rectangle2D(0, 0, 48, 48));
        truckView.setFitWidth(MainView.WIDTH / 20);
        truckView.setFitHeight(MainView.HEIGHT / 20);
        truckView.relocate(MainView.WIDTH * 0.75, MainView.HEIGHT / 12);
        truckView.setScaleX(-1);
        childrenList.addAll(truckView);
        double end = MainView.WIDTH - MainView.WIDTH * 0.75 - 48;
        KeyValue xForGoing = new KeyValue(truckView.xProperty(), end);
        KeyFrame going = new KeyFrame(Duration.millis(2000), xForGoing);
        Timeline timeLineGoing = new Timeline(going);
        timeLineGoing.getKeyFrames().addAll(going);
        timeLineGoing.play();
        Animation animation = new SpriteAnimation(truckView, Duration.millis(2000), 2, 2, 0, 0,
                (int) (truckView.getImage().getWidth() / 2), (int) (truckView.getImage().getHeight()));
        animation.setCycleCount(1);
        animation.play();
        animation.setOnFinished(event -> {
            childrenList.removeAll(truckView);
            truckView.relocate(MainView.WIDTH - 48, MainView.HEIGHT / 12);
            truckView.setScaleX(1);
            childrenList.addAll(truckView);
            KeyValue xForGoing1 = new KeyValue(truckView.xProperty(), -MainView.WIDTH / 75);

            KeyFrame going1 = new KeyFrame(Duration.millis(2000), xForGoing1);
            Timeline timeLineGoing1 = new Timeline(going1);
            timeLineGoing1.getKeyFrames().addAll(going1);
            timeLineGoing1.play();

            animation.setCycleCount(1);
            animation.play();
            animation.setOnFinished(event1 -> childrenList.removeAll(truckView));
        });

    }

    public void showHelicopterPath() {
        ImageView helicopterView =
                new ImageView(Utility.getImage(PictureAddresses.HELICOPTER_MINI_PICTURE_ROOT + Truck.getInstance().getLevel() + "_mini.png"));
        helicopterView.setViewport(new Rectangle2D(0, 0, 48, 48));
        helicopterView.setFitWidth(MainView.WIDTH / 20);
        helicopterView.setFitHeight(MainView.HEIGHT / 20);
        helicopterView.relocate(MainView.WIDTH * 0.75, MainView.HEIGHT / 150);
        helicopterView.setScaleX(1);
        childrenList.addAll(helicopterView);
        double end = MainView.WIDTH - MainView.WIDTH * 0.75 - 48;
        KeyValue xForGoing = new KeyValue(helicopterView.xProperty(), end);
        KeyFrame going = new KeyFrame(Duration.millis(2000), xForGoing);
        Timeline timeLineGoing = new Timeline(going);
        timeLineGoing.getKeyFrames().addAll(going);
        timeLineGoing.play();
        Animation animation = new SpriteAnimation(helicopterView, Duration.millis(2000), 6, 3, 0, 0,
                (int) (helicopterView.getImage().getWidth() / 3), (int) (helicopterView.getImage().getHeight() / 2));
        animation.setCycleCount(1);
        animation.play();
        animation.setOnFinished(event -> {
            childrenList.removeAll(helicopterView);
            helicopterView.relocate(MainView.WIDTH - 48, MainView.HEIGHT / 150);
            helicopterView.setScaleX(-1);
            childrenList.addAll(helicopterView);
            KeyValue xForGoing1 = new KeyValue(helicopterView.xProperty(), -MainView.WIDTH / 75);

            KeyFrame going1 = new KeyFrame(Duration.millis(2000), xForGoing1);
            Timeline timeLineGoing1 = new Timeline(going1);
            timeLineGoing1.getKeyFrames().addAll(going1);
            timeLineGoing1.play();

            animation.setCycleCount(1);
            animation.play();
            animation.setOnFinished(event1 -> {
                childrenList.removeAll(helicopterView);
            });
        });

    }

    private void workWorkshop(Workshop workshop, SpriteAnimation spriteAnimation) {
        if (!workshop.isWorking()) {
            try {
                InGameController.getInstance().startWorkshop(workshop.name);
            } catch (Exception e) {
                Utility.showError(MainView.WIDTH / 2, MainView.HEIGHT / 2, e.getMessage());
            }
            spriteAnimation.setCycleCount(5);
            spriteAnimation.play();
        }
    }
}
