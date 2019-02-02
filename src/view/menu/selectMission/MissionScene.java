package view.menu.selectMission;

import controller.MenuController;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;
import models.account.Account;
import models.misc.Mission;
import view.MainView;
import view.utility.Utility;
import view.utility.constants.PictureAddresses;

import java.util.ArrayList;

public class MissionScene extends Scene
{
    public static MissionScene getInstance()
    {
        return instance;
    }

    private static MissionScene instance = new MissionScene();
    private Group root;

    private static Account account;

    private MissionScene()
    {
        super(new Group(), MainView.WIDTH, MainView.HEIGHT);
        root = (Group) getRoot();
        build();

    }

    public void updateInfo(Account account)
    {
        MissionScene.account = account;
        root.getChildren().clear();
        build();
    }

    private void build()
    {
        account = MenuController.getInstance().getCurrentAccount();
        int missionsPassed = account.getMissionsPassed();
        buildBackground();
        ArrayList<String> missions = Mission.loadDefaultMissions();
        missions.sort(String::compareTo);
        double XCenter = MainView.WIDTH / 2 - 100; // todo fix
        double YCenter = MainView.HEIGHT / 2 - 100; // todo fix
        double radius = 300; // todo fix
        double teta = 2 * Math.PI / missions.size();
        for (String mission : missions)
        {
            int i = Integer.parseInt(mission.substring(mission.indexOf('n') + 1));
            Color color = Color.RED;
            if (i <= missionsPassed + 1)
            {
                color = Color.GREEN;
            }
            createButton(XCenter + radius * StrictMath.cos(teta * i), YCenter + radius * StrictMath.sin(teta * i),
                    color, mission);
        }
        buildMenuButton(XCenter, YCenter);
        showProfileInfo();
    }

    private void buildBackground()
    {
        ImageView cloud = Utility.getImageView(PictureAddresses.MENU_CLOUD);
        cloud.setFitWidth(MainView.WIDTH);
        cloud.setFitHeight(MainView.HEIGHT);
        cloud.relocate(0, 0);
        root.getChildren().addAll(cloud);

        ImageView missionWallpaper = Utility.getImageView(PictureAddresses.MISSION_SCENE_BACKGROUND);
        missionWallpaper.setFitWidth(MainView.WIDTH);
        missionWallpaper.setFitHeight(MainView.HEIGHT);
        missionWallpaper.relocate(0, 0);
        root.getChildren().addAll(missionWallpaper);
    }

    private void createButton(double x, double y, Color color, String mission)
    {
        StackPane stackPane = new StackPane();
        ImageView imageView = new ImageView(Utility.getImage(PictureAddresses.MISSION_SCENE_ICON));
        imageView.setFitWidth(150); // todo fix
        imageView.setFitHeight(150); // todo fix
        Text text = new Text(mission);
        text.setFill(color);
        stackPane.getChildren().addAll(imageView, text);
        if (color == Color.GREEN)
        {
            stackPane.setOnMouseClicked(event -> MissionInfo.getInstance().showMission(mission, account));
        }
        stackPane.relocate(x, y);
        root.getChildren().addAll(stackPane);
    }

    private void buildMenuButton(double XCenter, double YCenter)
    {
        ImageView sun = new ImageView(Utility.getImage(PictureAddresses.MENU_SUN));
        sun.setFitHeight(250); // todo fix
        sun.setFitWidth(250); // todo fix
        sun.relocate(XCenter - 50, YCenter - 50); // todo fix
        sun.setOnMouseClicked(event -> MainView.getInstance().goToMenu());
        root.getChildren().addAll(sun);
        //Timeline
        KeyValue kvRotateSun = new KeyValue(sun.rotateProperty(), 180);
        KeyFrame keyFrameSun = new KeyFrame(Duration.millis(2000), kvRotateSun);
        Timeline timelineSun = new Timeline(keyFrameSun);
        timelineSun.getKeyFrames().addAll(keyFrameSun);
        timelineSun.setAutoReverse(true);
        timelineSun.setCycleCount(4);
        timelineSun.setCycleCount(Animation.INDEFINITE);
        timelineSun.play();
    }

    private void showProfileInfo()
    {
        ImageView billboard = Utility.getImageView(PictureAddresses.MENU_BILLBOARD);
        billboard.setFitHeight(500); // todo fix
        billboard.setFitWidth(500); // todo fix
        billboard.relocate(900, 300); // todo fix

        Text text = new Text(
                account.getName() + '\n' +
                        "Your Level: " +
                        account.getMissionsPassed()
        );
        text.setFont(Font.font("Courier New", 25));
        text.relocate(960, 530); // todo fix
        root.getChildren().addAll(billboard, text, MissionInfo.getInstance());
    }

    private void runGame(String text)
    {
        MissionInfo.getInstance().showMission(text, account);
    }

}
