package view;


import controller.MenuController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
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
    private static Stage mainStage;
    public static final int HEIGHT = (int) Screen.getPrimary().getBounds().getHeight();
    public static final int WIDTH = (int) Screen.getPrimary().getBounds().getWidth();
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
        System.out.println("start");
        primaryStage.setFullScreen(true);
        mainStage = primaryStage;
        mainStage.setResizable(true);
        mainStage.setHeight(WIDTH);
        mainStage.setWidth(HEIGHT);
        setStageScene(Menu.getInstance());
        System.out.println("after setStage");
        if (mainStage == null) throw new AssertionError();
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
        System.out.println("end");
    }


    private void setStageScene(Scene scene) {
        if (mainStage == null) throw new AssertionError();
        scene.setFill(Color.BLACK);
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
