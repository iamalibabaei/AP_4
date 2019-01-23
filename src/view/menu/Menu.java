package view.menu;

import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import models.account.Account;
import view.View;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Menu extends Scene {
    private static Menu instance = new Menu();

    public static Menu getInstance() {
        return instance;
    }

    private Group root;
    private ImageView background;
    private ChoiceBox<String> choiceBox;
    private TextField password;

    public Menu() {
        super(new Group(), View.WIDTH, View.HEIGHT);
        root = (Group) getRoot();
        build();
    }

    private void build() {
        root.getChildren().clear();
        Image menuImage = null;
        try {
            menuImage = new Image(new FileInputStream("Textures\\menuWallpaper.jpg"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        ImageView menuWallpaper = new ImageView(menuImage);
        menuWallpaper.setFitWidth(View.WIDTH);
        menuWallpaper.setFitHeight(View.HEIGHT);
        menuWallpaper.relocate(0, 0);
        //start game button design
        Button startGame = new Button("start Game");
        startGame.relocate(300, 300);
        startGame.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                View.getInstance().goToMap();
            }
        });

        Button setting = new Button("setting");
        setting.relocate(300, 350);
        setting.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                View.getInstance().goToSetting();
            }
        });

        Button exit = new Button("EXIT");
        exit.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                View.getInstance().close();
            }
        });
        exit.relocate(300, 500);

        Button newPlayer = new Button("new player");
        newPlayer.relocate(300, 400);
        newPlayer.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                View.getInstance().newPlayer();
            }
        });

        choiceBox = new ChoiceBox<>();
        choiceBox.getItems().addAll(Account.getAllAccounts());
        choiceBox.relocate(400, 300);

        password = new TextField();
        password.setPromptText("password");
        password.relocate(400, 350);
        root.getChildren().addAll(menuWallpaper, startGame, setting, newPlayer, exit, choiceBox, password);
    }

    public void updateChoiceBox(){
        choiceBox.getItems().addAll(Account.getAllAccounts());
    }

    public String getAccount() {
        return choiceBox.getValue();
    }

}
