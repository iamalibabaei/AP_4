package view.menu.selectMission;

import controller.MenuController;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import models.account.Account;
import models.misc.Mission;
import view.MainView;
import view.utility.Utility;

import java.io.FileInputStream;
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
        super(new Group(), MainView.WIDTH, MainView.HEIGHT);
        root = (Group) getRoot();
        build();
    }

    private void build() {
        root.getChildren().clear();
        Image menuImage = Utility.getImage()
        try {
            menuImage = new Image(new FileInputStream("res/graphicAssets/menuWallpaper.jpg"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        ImageView menuWallpaper = new ImageView(menuImage);
        menuWallpaper.setFitWidth(MainView.WIDTH);
        menuWallpaper.setFitHeight(MainView.HEIGHT);
        menuWallpaper.relocate(0, 0);

        ArrayList<String> missions = Mission.loadDefaultMissions();
        int YValue = 200;
        for (int i = 0; i < missions.size(); i++) {
            Button button = new Button(missions.get(i));
            button.relocate(300, YValue + i * 50);
            root.getChildren().addAll(button);
            button.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    runGame(button.getText());
                }
            });
        }


    }

    private void runGame(String text) {
        MenuController.getInstance().setCurrentAccount(account);
        try {
            MenuController.getInstance().setMission(Mission.loadJson("res/missions/"+ text + ".json"));
        } catch (FileNotFoundException e) {
            //TODO show alert
            e.printStackTrace();
        }
        MenuController.getInstance().startGame();
    }


    public void setAccount(Account account) {
        this.account = account;
    }
}
