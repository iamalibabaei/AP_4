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
import view.gameScene.GameScene;
import view.gameScene.MissionScene;
import view.menu.Menu;
import view.newPlayer.NewPlayerScene;

import java.io.FileNotFoundException;

public class View extends Application {
    private static View view = new View();
    private Stage mainStage;
    public static final int WIDTH = 1300, HEIGHT = 720;

    public Stage getMainStage() {
        return mainStage;
    }


    private static MenuController getController(){
        return MenuController.getInstance();
    }

    public static View getInstance() {
        return view;
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
        setStageScene(GameScene.getInstance());
    }

    public void showExceptions(Exception e, double x, double y) {
        if (e instanceof InsufficientResourcesException) {

        } else if (e instanceof IsWorkingException) {

        }
    }
    public void addEntityToMap(Entity entity) {
        if (entity instanceof Animal) {
            GameScene.getInstance().addAnimal((Animal) entity, entity.getCoordinates());
        } else if (entity instanceof Grass) {
            GameScene.getInstance().addGrass((Grass) entity, entity.getCoordinates());
        } else if (entity instanceof Item) {
            //GameScene.getInstance().addItem((Item) entity, entity.getCoordinates());
        }
    }
}
