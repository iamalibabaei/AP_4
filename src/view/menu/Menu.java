package view.menu;

import controller.AddressConstants;
import controller.SoundPlayer;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
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
        SoundPlayer.getInstance().playBackground(AddressConstants.getSound(AddressConstants.MENU_SOUND));
        root = (Group) getRoot();
        build();
    }

    private void build() {
        root.getChildren().clear();

        //todo change font of texes
        ImageView startButton = new ImageView(AddressConstants.getImage(AddressConstants.MENU_BUTTON));
        startButton.setFitHeight(100);
        startButton.setFitWidth(200);
        StackPane startGame = new StackPane();
        Text startGameText = new Text("start game");
        startGame.getChildren().addAll(startButton, startGameText);
        startButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                View.getInstance().goToMap();
            }
        });
        startGame.relocate(200, 200);

        ImageView settingButton = new ImageView(AddressConstants.getImage(AddressConstants.MENU_BUTTON));
        settingButton.setFitHeight(100);
        settingButton.setFitWidth(200);
        StackPane setting = new StackPane();
        Text settingText = new Text("setting");
        setting.getChildren().addAll(settingButton, settingText);
        setting.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                View.getInstance().goToSetting();
            }
        });
        setting.relocate(200, 280);

        ImageView newPlayerButton = new ImageView(AddressConstants.getImage(AddressConstants.MENU_BUTTON));
        newPlayerButton.setFitHeight(100);
        newPlayerButton.setFitWidth(200);
        StackPane newPlayer = new StackPane();
        Text newPlayerText = new Text("new player");
        newPlayer.getChildren().addAll(newPlayerButton, newPlayerText);
        newPlayer.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                View.getInstance().newPlayer();
            }
        });
        newPlayer.relocate(200, 360);

        ImageView exitButton = new ImageView(AddressConstants.getImage(AddressConstants.MENU_BUTTON));
        exitButton.setFitHeight(100);
        exitButton.setFitWidth(200);
        StackPane exit = new StackPane();
        Text exitText = new Text("exit");
        exit.getChildren().addAll(exitButton, exitText);
        exit.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                View.getInstance().close();
            }
        });
        exit.relocate(200, 440);
        //TODO change these
        choiceBox = new ChoiceBox<>();
        choiceBox.getItems().addAll(Account.getAllAccounts());
        choiceBox.relocate(400, 300);

        password = new TextField();
        password.setPromptText("password");
        password.relocate(400, 350);
        root.getChildren().addAll(MenuBackground.getInstance(), startGame, setting, newPlayer, exit, choiceBox, password);

        //root.getChildren().addAll(MenuBackground.getInstance());


    }

    public void updateChoiceBox(){
        choiceBox.getItems().addAll(Account.getAllAccounts());
    }

    public String getAccount() {
        return choiceBox.getValue();
    }

    private void createButtton(String textOnButton, int x, int y, EventHandler eventHandler) {
        Pane pane = new Pane();
        //todo change font of texes
        ImageView button = new ImageView(AddressConstants.getImage(AddressConstants.MENU_BUTTON));
        button.setFitHeight(100);
        button.setFitWidth(200);
        Text text = new Text(textOnButton);
        pane.getChildren().addAll(button, text);
        button.setOnMouseClicked(eventHandler);
        pane.relocate(x, y);
        root.getChildren().addAll(pane);
    }


}

