package view.menu.selectMission;

import controller.InGameController;
import controller.MenuController;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import models.account.Account;
import models.misc.Mission;
import models.objects.Item;
import models.objects.animals.Animal;
import view.MainView;
import view.utility.constants.JsonAddresses;
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
        Utility.makeMenuButton(getChildren(), MainView.WIDTH * 0.6, MainView.HEIGHT * 0.3, MainView.WIDTH * 0.2,
                MainView.HEIGHT * 0.15, "Start Game", event -> startGame());
        Utility.makeMenuButton(getChildren(), MainView.WIDTH * 0.6, MainView.HEIGHT * 0.4, MainView.WIDTH * 0.2,
                MainView.HEIGHT * 0.15, "LoadGame", event -> {
            if (!InGameController.loadGame(mission, MissionScene.getAccount())) {
                String errorMessage = "you don't have a saved game for this mission";
                getChildren().addAll(Utility.showError(MainView.HEIGHT * 0.4, MainView.HEIGHT * 0.3,errorMessage));
            } else {
                setVisible(false);
            }

                });
        Utility.makeMenuButton(getChildren(), MainView.WIDTH * 0.6, MainView.HEIGHT * 0.5, MainView.WIDTH * 0.2,
                MainView.HEIGHT * 0.15, "Back", event -> setVisible(false));
    }


    public void showMission(String str, Account account) {
        MenuController.getInstance().setCurrentAccount(account);
        InGameController.getInstance();
        InGameController.getInstance().setAccount(account);
        Mission mission = null;
        try {
            mission = Mission.loadJson(JsonAddresses.MISSIONS_ROOT + str + ".json");
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
        setVisible(false);
        MenuController.getInstance().startGame(mission);
    }


}
