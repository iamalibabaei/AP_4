package view;


import controller.Controller;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import models.account.Account;
import models.exceptions.InsufficientResourcesException;
import models.exceptions.IsWorkingException;
import models.objects.Entity;
import models.objects.Grass;
import models.objects.Item;
import models.objects.Point;
import models.objects.animals.Animal;
import view.gameScene.GameScene;
import view.gameScene.MissionScene;
import view.menu.Menu;
import view.newPlayer.NewPlayerScene;

import java.io.FileNotFoundException;

public class View extends Application {
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
