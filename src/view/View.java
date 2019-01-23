package view;


import controller.Controller;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import models.account.Account;
import models.exceptions.NotEnoughSpaceException;
import models.interfaces.Time;
import models.misc.Mission;
import view.gameScene.GameScene;
import view.gameScene.MissionScene;
import view.menu.Menu;
import view.newPlayer.NewPlayerScene;

import java.io.FileNotFoundException;

public class View extends Application implements Time {
    private static View view = new View();
    private static Stage mainStage;
    public static final int WIDTH = 1300, HEIGHT = 720;

    public static Stage getMainStage() {
        return mainStage;
    }

    private static Controller getController(){
        return Controller.getInstance();
    }

    public static View getInstance() {
        return view;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        mainStage = primaryStage;
        getMainStage().setResizable(false);
        setStageScene(Menu.getInstance());

        getMainStage().setOnCloseRequest(event -> {
            /*
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Save Game");
            alert.setHeaderText(null);
            alert.setContentText("Save the game before closing?");
            Optional<ButtonType> action = alert.showAndWait();
            if (action.get() == ButtonType.OK) {
                getController().saveGame();
            }
            ConnectionManager.getInstance().disconnect();
            Platform.exit();
            System.exit(0);*/
        });
        getMainStage().show();

    }


    public static void setStageScene(Scene scene) {
        view.getMainStage().setX(0);
        view.getMainStage().setY(0);
        view.getMainStage().setScene(scene);
        getMainStage().sizeToScene();
    }


    public void goToMap() {
        //todo check password and not null
        Account account = null;
        try {
            account = Account.loadJason(Menu.getInstance().getAccount());
        } catch (FileNotFoundException e) {
            //TODO show message
            return;
        }
        Controller.getInstance().setAccount(account);
        setStageScene(MissionScene.getInstance());
    }

    public void goToSetting() {
    }

    public void close() {

    }

    public void newPlayer() {
        setStageScene(new NewPlayerScene());
    }

    public void goToMenue() {
        setStageScene(Menu.getInstance());
    }

    public void startGame() {
        setStageScene(GameScene.getInstance());
    }

    @Override
    public void nextTurn() {
        System.out.println("nextTurn");
    }
}
