package view;


import controller.MenuController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import models.account.Account;
import models.exceptions.InsufficientResourcesException;
import models.exceptions.IsWorkingException;
import models.objects.Entity;
import models.objects.Grass;
import models.objects.Item;
import models.objects.animals.Animal;
import view.gameScene.InGameView;
import view.menu.selectMission.MissionScene;
import view.menu.Menu;
import view.menu.newPlayer.NewPlayerScene;

import java.io.FileNotFoundException;

public class MainView extends Application {
    private static MainView instance = new MainView();
    private Stage mainStage;
    public static final int WIDTH = 1300, HEIGHT = 720;

    public Stage getMainStage() {
        return mainStage;
    }


    private static MenuController getController(){
        return MenuController.getInstance();
    }

    public static MainView getInstance() {
        return instance;
    }

    @Override
    public void start(Stage primaryStage) {
        mainStage = primaryStage;
        mainStage.setResizable(false);
        setStageScene(Menu.getInstance());

        mainStage.setOnCloseRequest(event -> {
            /*
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Save InGameController");
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
        mainStage.show();

    }


    private void setStageScene(Scene scene) {
        mainStage.setX(0);
        mainStage.setY(0);
        mainStage.setScene(scene);
        mainStage.sizeToScene();
    }


    public void goToMap() {
        //todo check password and not null
        Account account = null;
        try {
            account = Account.loadJson(Menu.getInstance().getAccount());
        } catch (FileNotFoundException e) {
            //TODO show message
            return;
        }
        System.out.println(1);
        MenuController.getInstance().setCurrentAccount(account);
        System.out.println(2);
        setStageScene(MissionScene.getInstance());
        System.out.println(3);
    }

    public void goToSetting() {
    }

    public void close() {

    }

    public void newPlayer() {
        setStageScene(new NewPlayerScene());
    }

    public void goToMenu() {
        setStageScene(Menu.getInstance());
    }

    public void startGame() {
        setStageScene(InGameView.getInstance());
    }

    public void showExceptions(Exception e, double x, double y) {
        if (e instanceof InsufficientResourcesException) {

        } else if (e instanceof IsWorkingException) {

        }
    }
    public void addEntityToMap(Entity entity) {
        if (entity instanceof Animal) {
            InGameView.getInstance().addAnimal((Animal) entity, entity.getCoordinates());
        } else if (entity instanceof Grass) {
            InGameView.getInstance().addGrass((Grass) entity, entity.getCoordinates());
        } else if (entity instanceof Item) {
            //InGameView.getInstance().addItem((Item) entity, entity.getCoordinates());
        }
    }
}
