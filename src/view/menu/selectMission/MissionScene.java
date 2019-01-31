package view.menu.selectMission;

import controller.MenuController;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;
import models.account.Account;
import models.misc.Mission;
import view.MainView;
import view.utility.AddressConstants;
import view.utility.Utility;

import java.io.FileNotFoundException;
import java.util.ArrayList;

public class MissionScene extends Scene {
    private static MissionScene instance = new MissionScene();
    private Account account;
    public static MissionScene getInstance() {
        return instance;
    }

    private Group root;
    private ImageView background;
    public MissionScene() {
        super(new Group(), MainView.WIDTH, MainView.HEIGHT, Color.BLACK);
        root = (Group) getRoot();
        build();
    }

    private void build() {
        root.getChildren().clear();
        setAccount(MenuController.getInstance().getCurrentAccount());

        ImageView cloud = new ImageView(Utility.getImage(AddressConstants.MENU_CLOUD));
        cloud.setFitWidth(MainView.WIDTH);
        cloud.setFitHeight(MainView.HEIGHT);
        cloud.relocate(0,0);
        root.getChildren().addAll(cloud);

        Image wallpaper = Utility.getImage(AddressConstants.MISSION_SCENE_BACKGROUND);
        ImageView missionWallpaper = new ImageView(wallpaper);
        missionWallpaper.setFitWidth(MainView.WIDTH);
        missionWallpaper.setFitHeight(MainView.HEIGHT);
        missionWallpaper.relocate(0, 0);
        root.getChildren().addAll(missionWallpaper);

        ArrayList<String> missions = Mission.loadDefaultMissions();
        missions.sort(String::compareTo);
        int XCenter= MainView.WIDTH / 2 - 100, YCenter = MainView.HEIGHT / 2 - 100, radios = 300;
        double teta = 2 * 3.1415 / missions.size();
        for (String mission : missions) {
            int i = Integer.parseInt(mission.substring(mission.indexOf('n') + 1));
            Color color = Color.RED;
            if (i <= account.getMissionPassed() + 1) {
                color = Color.GREEN;
            }

            creatButton(XCenter + radios * Math.cos(teta * i), YCenter + radios * Math.sin(teta * i), color, mission);
        }

        ImageView sun = new ImageView(Utility.getImage(AddressConstants.MENU_SUN));
        sun.setFitHeight(250);
        sun.setFitWidth(250);
        sun.relocate(XCenter - 50, YCenter - 50);
        sun.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                MainView.getInstance().goToMenu();
            }
        });
        root.getChildren().addAll(sun);
        //Timeline
        KeyValue kvRotateSun = new KeyValue(sun.rotateProperty(), 180);
        KeyFrame keyFrameSun = new KeyFrame(Duration.millis(2000), kvRotateSun);
        Timeline timelineSun = new Timeline(keyFrameSun);
        timelineSun.getKeyFrames().addAll(keyFrameSun);
        timelineSun.setAutoReverse(true);
        timelineSun.setCycleCount(4);
        timelineSun.setCycleCount(Timeline.INDEFINITE);
        timelineSun.play();


        ImageView billboard = new ImageView(Utility.getImage(AddressConstants.MENU_BILLBOARD));
        billboard.setFitHeight(500);
        billboard.setFitWidth(500);
        billboard.relocate(900, 300);
        root.getChildren().addAll(billboard);

        //

        Text text = new Text(
                account.getName() + '\n'+
                        "your level : "+
                        account.getMissionPassed()
        );
        text.setFont(Font.font("Courier New", 25));
        text.relocate(960,530);
        root.getChildren().addAll(text);


        root.getChildren().addAll(MissionInfo.getInstance());




    }

    private void creatButton(double x, double y, Color color, String mission) {
        StackPane stackPane = new StackPane();
        ImageView imageView = new ImageView(Utility.getImage(AddressConstants.MISSION_SCENE_ICON));
        imageView.setFitWidth(150);
        imageView.setFitHeight(150);
        Text text = new Text(mission);
        text.setFill(color);
        stackPane.getChildren().addAll(imageView, text);
        if (color == Color.GREEN) {
            stackPane.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    MissionInfo.getInstance().showMission(mission, account);
                }
            });
        }
        stackPane.relocate(x, y);
        root.getChildren().addAll(stackPane);
    }

    private void runGame(String text) {
        MissionInfo.getInstance().showMission(text, account);
    }


    public void setAccount(Account account) {
        this.account = account;
    }

    public void updateInfo() {
        root.getChildren().clear();
        build();
    }
}
