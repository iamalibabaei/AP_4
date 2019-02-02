package view.menu.selectMission;

import controller.MenuController;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import models.account.Account;
import models.misc.Mission;
import models.objects.Item;
import models.objects.animals.Animal;
import view.MainView;
import view.utility.constants.PictureAddresses;
import view.utility.Utility;

import java.io.FileNotFoundException;

public class MissionInfo extends Pane
{
    public static MissionInfo getInstance()
    {
        return instance;
    }

    private static MissionInfo instance = new MissionInfo();
    private Text text;
    private Mission mission;


    private MissionInfo() {
        text = new Text();
        ImageView imageView = new ImageView(Utility.getImage(PictureAddresses.MISSION_SCENE_TAB));
        imageView.setFitHeight(MainView.HEIGHT * 1.2);
        imageView.setFitWidth(MainView.HEIGHT * 1.2);
        imageView.relocate(MainView.HEIGHT * 0.1, 0);
        getChildren().addAll(imageView);
        text.setFont(Font.font("Rage Italic", 25));
        text.relocate(MainView.HEIGHT * 0.45, MainView.HEIGHT * 0.3);
        getChildren().addAll(text);
        addButtons();

        setVisible(false);
    }

    private void addButtons() {
        ImageView start = new ImageView(Utility.getImage(PictureAddresses.MENU_BUTTON));
        start.setFitHeight(MainView.HEIGHT * 0.15);
        start.setFitWidth(MainView.HEIGHT * 0.3);
        StackPane startGame = new StackPane();
        Text startGameText = new Text("start game");
        startGameText.setFont(Font.font("Rage Italic", 25));
        startGame.getChildren().addAll(start, startGameText);
        startGame.setOnMouseClicked(event -> startGame());
        startGame.relocate(MainView.HEIGHT * 0.75, MainView.HEIGHT * 0.3);
        getChildren().addAll(startGame);

        ImageView exitButton = new ImageView(Utility.getImage(PictureAddresses.MENU_BUTTON));
        exitButton.setFitHeight(MainView.HEIGHT * 0.15);
        exitButton.setFitWidth(MainView.HEIGHT * 0.3);
        StackPane exit = new StackPane();
        Text exitText = new Text("back");
        exitText.setFont(Font.font("Rage Italic", 25));
        exit.getChildren().addAll(exitButton, exitText);
        exit.setOnMouseClicked(event -> setVisible(false));
        exit.relocate(MainView.HEIGHT * 0.75, MainView.HEIGHT * 0.45);
        getChildren().addAll(exit);
    }


    public void showMission(String str, Account account) {
        MenuController.getInstance().setCurrentAccount(account);
        Mission mission = null;
        try {
            mission = Mission.loadJson(PictureAddresses.MISSIONS_ROOT + str + ".json");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        this.mission = mission;
        String missionInfo = str +"\nmoney objective: " + mission.getMoneyObjective() +'\n' + "item objective: \n";
        for (Item.Type item : mission.getItemObjective().keySet()) {
            missionInfo = missionInfo + item.name() + "  :  " + mission.getItemObjective().get(item) + '\n';

        }
        missionInfo = missionInfo + "animal objective\n";
        for (Animal.Type animal : mission.getAnimalObjectives().keySet()) {
            missionInfo = missionInfo + animal.name() + " :  " + mission.getAnimalObjectives().get(animal) + '\n';
        }
        if (mission.isDog()) {
            missionInfo = missionInfo + "reach dog\n";
        }
        if(mission.isCat()) {
            missionInfo = missionInfo + "reach cat\n";
        }
        text.setText(missionInfo);
        setVisible(true);

    }

    public void startGame() {
        MenuController.getInstance().startGame(mission);
    }


}
