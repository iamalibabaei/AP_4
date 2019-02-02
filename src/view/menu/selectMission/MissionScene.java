package view.menu.selectMission;

import controller.MenuController;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;
import models.account.Account;
import models.misc.Mission;
import view.SceneBuilder;
import view.MainView;
import view.utility.Utility;
import view.utility.constants.PictureAddresses;

import java.util.ArrayList;

public class MissionScene extends SceneBuilder
{
    private static MissionScene instance = new MissionScene();
    private static Account account;
    private MissionScene()
    {
        super(MainView.WIDTH, MainView.HEIGHT);
    }

    public static MissionScene getInstance()
    {
        return instance;
    }

    public void updateInfo(Account account)
    {
        MissionScene.account = account;
        childrenList.clear();
        build();
    }

    @Override
    protected void build()
    {
        account = MenuController.getInstance().getCurrentAccount();
        int missionsPassed = account.getMissionsPassed();
        buildBackground();
        ArrayList<String> missions = Mission.loadDefaultMissions();
        missions.sort(String::compareTo);
        double XCenter = MainView.WIDTH / 2 - MainView.WIDTH * 0.1;
        double YCenter = MainView.HEIGHT / 2 - MainView.HEIGHT * 0.1;
        double radius = MainView.WIDTH * 0.3;
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
        childrenList.addAll(cloud);

        ImageView missionWallpaper = Utility.getImageView(PictureAddresses.MISSION_SCENE_BACKGROUND);
        missionWallpaper.setFitWidth(MainView.WIDTH);
        missionWallpaper.setFitHeight(MainView.HEIGHT);
        missionWallpaper.relocate(0, 0);
        childrenList.addAll(missionWallpaper);
    }

    private void createButton(double x, double y, Color color, String mission)
    {
        StackPane stackPane = new StackPane();
        ImageView imageView = new ImageView(Utility.getImage(PictureAddresses.MISSION_SCENE_ICON));
        final double length = MainView.HEIGHT * 0.2;
        imageView.setFitWidth(length);
        imageView.setFitHeight(length);
        Text text = new Text(mission);
        text.setFill(color);
        stackPane.getChildren().addAll(imageView, text);
        if (color == Color.GREEN)
        {
            stackPane.setOnMouseClicked(event -> MissionInfo.getInstance().showMission(mission, account));
        }
        stackPane.relocate(x, y);
        childrenList.addAll(stackPane);
    }

    private void buildMenuButton(double XCenter, double YCenter)
    {
        ImageView sun = new ImageView(Utility.getImage(PictureAddresses.MENU_SUN));
        sun.setFitHeight(MainView.HEIGHT * 0.3);
        sun.setFitWidth(MainView.HEIGHT * 0.3);
        sun.relocate(XCenter - MainView.WIDTH * 0.05, YCenter - MainView.WIDTH * 0.05);
        sun.setOnMouseClicked(event -> MainView.getInstance().goToMenu());
        childrenList.addAll(sun);
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
        billboard.setFitHeight(MainView.HEIGHT * 0.5);
        billboard.setFitWidth(MainView.HEIGHT * 0.5);
        billboard.relocate(MainView.HEIGHT, MainView.HEIGHT * 0.5);

        Text text = new Text(
                account.getName() + '\n' +
                        "Your Level: " +
                        account.getMissionsPassed()
        );
        text.setFont(Font.font("Courier New", 23));
        text.relocate(MainView.HEIGHT * 1.05, MainView.HEIGHT * 0.715);
        childrenList.addAll(billboard, text, MissionInfo.getInstance());
    }

    private void runGame(String text)
    {
        MissionInfo.getInstance().showMission(text, account);
    }

}
