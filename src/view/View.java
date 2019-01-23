package view;

import controller.Controller;
import javafx.application.Application;

import javafx.stage.Stage;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class View extends Application {
    private static View view = new View();
    private Controller controller;
    public static final int WIDTH = 1024, HEIGHT = 720;

    private View() {
        this.controller = Controller.getInstance();
    }

    public static View getInstance() {
        return view;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("FarmFrenzy");
        Menu menu = new Menu();
        primaryStage.setScene(menu);
        primaryStage.show();

    }

}
