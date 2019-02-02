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
        imageView.setFitHeight(1000); // todo fix
        imageView.setFitWidth(1000); // todo fix
        imageView.relocate(200, 0); // todo fix
        getChildren().addAll(imageView);
        text.setFont(Font.font("Rage Italic", 25));
        text.relocate(500, 250); // todo fix
        getChildren().addAll(text);
        addButtons();

        setVisible(false);
    }

    private void addButtons() {
        ImageView start = new ImageView(Utility.getImage(PictureAddresses.MENU_BUTTON));
        start.setFitHeight(100);
        start.setFitWidth(200);
        StackPane startGame = new StackPane();
        Text startGameText = new Text("start game");
        startGameText.setFont(Font.font("Rage Italic", 25));
        startGame.getChildren().addAll(start, startGameText);
        startGame.setOnMouseClicked(event -> startGame());
        startGame.relocate(750, 265);
        getChildren().addAll(startGame);

        ImageView exitButton = new ImageView(Utility.getImage(PictureAddresses.MENU_BUTTON));
        exitButton.setFitHeight(100);
        exitButton.setFitWidth(200);
        StackPane exit = new StackPane();
        Text exitText = new Text("back");
        exitText.setFont(Font.font("Rage Italic", 25));
        exit.getChildren().addAll(exitButton, exitText);
        exit.setOnMouseClicked(event -> setVisible(false));
        exit.relocate(750, 320);
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
