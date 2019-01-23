package view;


import controller.Controller;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import view.menu.Menu;

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

    }

    public void goToSetting() {
    }

    public void close() {
    }

    public void newPlayer() {
    }
}
