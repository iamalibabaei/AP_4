package view;


import controller.InGameController;
import controller.MenuController;
import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
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
import view.menu.Menu;
import view.menu.newPlayer.NewPlayerScene;
import view.menu.selectMission.MissionScene;

import java.io.FileNotFoundException;

public class MainView extends Application {
    private static MainView instance = new MainView();
    private static Stage mainStage;
    public static final int HEIGHT = (int) Screen.getPrimary().getBounds().getHeight();
    public static final int WIDTH = (int) HEIGHT * 4 / 3;
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
        primaryStage.centerOnScreen();
        mainStage = primaryStage;
        mainStage.setResizable(true);
        mainStage.setHeight(WIDTH);
        mainStage.setWidth(HEIGHT);
        setStageScene(Menu.getInstance());
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
        Rectangle2D primScreenBounds = Screen.getPrimary().getBounds();
        primaryStage.setX((primScreenBounds.getWidth() - primaryStage.getWidth()) / 2);
        primaryStage.setY((primScreenBounds.getHeight() - primaryStage.getHeight()) / 2);
        System.out.println("end");
    }


    private void setStageScene(Scene scene) {
        if (mainStage == null) throw new AssertionError();
        mainStage.setX(0);
        mainStage.setY(0);
        mainStage.setScene(scene);
        mainStage.sizeToScene();
    }


    public void goToMap() {
        //todo check password and not null
        Account account = null;
        if (Menu.getInstance().getAccount() == null) {
            Menu.getInstance().showNotChosenAccount();
            return;
        }
        try {
            account = Account.loadJson(Menu.getInstance().getAccount());
        } catch (FileNotFoundException e) {
            System.out.println("null");
            return;
        }

        String password = Menu.getInstance().getPass();
        System.out.println(password);
        System.out.println(account.getPassword());
        if (!password.equals(account.getPassword())) {
            Menu.getInstance().showWrongPass();
            return;
        }
        Menu.getInstance().clearMessages();
        MenuController.getInstance().setCurrentAccount(account);
        MissionScene.getInstance().updateInfo();
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
        setStageScene(InGameView.getInstance());
        InGameController.getInstance().startGame();
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
