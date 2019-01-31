package view.menu;

import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import models.account.Account;
import view.MainView;
import view.utility.AddressConstants;
import view.utility.SoundPlayer;
import view.utility.Utility;

public class Menu extends Scene {
    private static Menu instance = new Menu();

    public static Menu getInstance() {
        return instance;
    }

    private Group root;
    private ImageView background;
    private ChoiceBox<String> choiceBox;
    private TextField password;
    private Text wrongPass;
    private Text notChosenAccount;

    public Menu() {
        super(new Group(), MainView.WIDTH, MainView.HEIGHT);
        SoundPlayer.getInstance().playBackground(Utility.getSound(AddressConstants.MENU_SOUND));
        root = (Group) getRoot();
        build();
    }

    private void build() {
        root.getChildren().clear();

        ImageView startButton = new ImageView(Utility.getImage(AddressConstants.MENU_BUTTON));
        startButton.setFitHeight(100);
        startButton.setFitWidth(200);
        StackPane startGame = new StackPane();
        Text startGameText = new Text("start game");
        startGameText.setFont(Font.font("SWItalt", 15));
        startGame.getChildren().addAll(startButton, startGameText);
        startGame.setOnMouseClicked(event -> MainView.getInstance().goToMap());
        startGame.relocate(200, 200);

        ImageView settingButton = new ImageView(Utility.getImage(AddressConstants.MENU_BUTTON));
        settingButton.setFitHeight(100);
        settingButton.setFitWidth(200);
        StackPane setting = new StackPane();
        Text settingText = new Text("setting");
        settingText.setFont(Font.font("SWItalt", 15));
        setting.getChildren().addAll(settingButton, settingText);
        setting.setOnMouseClicked(event -> MainView.getInstance().goToSetting());
        setting.relocate(200, 280);

        ImageView newPlayerButton = new ImageView(Utility.getImage(AddressConstants.MENU_BUTTON));
        newPlayerButton.setFitHeight(100);
        newPlayerButton.setFitWidth(200);
        StackPane newPlayer = new StackPane();
        Text newPlayerText = new Text("new player");
        newPlayerText.setFont(Font.font("SWItalt", 15));
        newPlayer.getChildren().addAll(newPlayerButton, newPlayerText);
        newPlayer.setOnMouseClicked(event -> MainView.getInstance().newPlayer());
        newPlayer.relocate(200, 360);

        ImageView exitButton = new ImageView(Utility.getImage(AddressConstants.MENU_BUTTON));
        exitButton.setFitHeight(100);
        exitButton.setFitWidth(200);
        StackPane exit = new StackPane();
        Text exitText = new Text("exit");
        exitText.setFont(Font.font("SWItalt", 15));
        exit.getChildren().addAll(exitButton, exitText);
        exit.setOnMouseClicked(event -> MainView.getInstance().close());
        exit.relocate(200, 440);
        //TODO change these
        choiceBox = new ChoiceBox<>();
        choiceBox.getItems().addAll(Account.getAllAccounts());
        choiceBox.relocate(400, 300);

        password = new TextField();
        password.setPromptText("password");
        password.relocate(400, 350);
        root.getChildren().addAll(MenuBackground.getInstance(), startGame, setting, newPlayer, exit, choiceBox, password);

        wrongPass = new Text("WRONG PASSWORD");
        wrongPass.setFill(Color.RED);
        wrongPass.setVisible(false);
        wrongPass.setFont(Font.font("SWItalt", 20));
        wrongPass.relocate(400, 250);
        root.getChildren().addAll(wrongPass);

        notChosenAccount = new Text("CHOOSE A PLAYER");
        notChosenAccount.setFill(Color.RED);
        notChosenAccount.setFont(Font.font("SWItalt", 20));
        notChosenAccount.setVisible(false);
        notChosenAccount.relocate(400, 250);
        root.getChildren().addAll(notChosenAccount);
    }

    public void updateChoiceBox(){
        choiceBox.getItems().addAll(Account.getAllAccounts());
    }

    public String getAccount() {
        return choiceBox.getValue();
    }
    public String getPass(){
        String pass = password.getText();
        password.clear();
        return pass;
    }


    public void showNotChosenAccount() {
        clearMessages();
        notChosenAccount.setVisible(true);
    }

    public void showWrongPass() {
        clearMessages();
        wrongPass.setVisible(true);
    }

    public void clearMessages(){
        wrongPass.setVisible(false);
        notChosenAccount.setVisible(false);
    }
}

