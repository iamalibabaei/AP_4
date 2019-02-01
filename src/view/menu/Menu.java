package view.menu;

import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import models.account.Account;
import view.MainView;
import view.utility.AddressConstants;
import view.utility.SoundPlayer;
import view.utility.Utility;

public class Menu extends Scene
{
    private static Menu instance = new Menu();
    private Group root;
    private ImageView background;
    private ChoiceBox<String> choiceBox;
    private TextField password;
    private Text wrongPass;
    private Text notChosenAccount;

    public Menu()
    {
        super(new Group(), (double) MainView.WIDTH, (double) MainView.HEIGHT);
        SoundPlayer.getInstance().playBackground(Utility.getSound(AddressConstants.MENU_SOUND));
        root = (Group) getRoot();
        build();
    }

    private StackPane makeMenuButton(int x, int y, String name, EventHandler
                                <? super MouseEvent> value)
    {
        final int width = 200, height = 100;
        ImageView button = new ImageView(Utility.getImage(AddressConstants.MENU_BUTTON));
        button.setFitWidth((double) width);
        button.setFitHeight((double) height);
        StackPane pane = new StackPane();
        Text text = new Text(name);
        text.setFont(Font.font("SWItalt", 15));
        text.setFill(Color.WHITE);
        pane.getChildren().addAll(button, text);
        pane.setOnMouseClicked(value);
        pane.relocate((double) x, (double) y);
        return pane;
    }

    private void build()
    {
        root.getChildren().clear();
        StackPane startGame = makeMenuButton(200, 200, "Start Game", event -> MainView.getInstance().goToMap());
        StackPane setting = makeMenuButton(200, 280, "Settings", event -> MainView.getInstance().goToSetting());
        StackPane addNewProfile = makeMenuButton(200, 360,"Add Profile", event -> MainView.getInstance().newPlayer());
        StackPane exit = makeMenuButton(200, 440, "Exit", event -> MainView.getInstance().close());

        //TODO change these
        choiceBox = new ChoiceBox<>();
        choiceBox.getItems().addAll(Account.getAllAccounts());
        choiceBox.relocate(400, 300);

        password = new TextField();
        password.setPromptText("password");
        password.relocate(400, 350);
        root.getChildren().addAll(MenuBackground.getInstance(), startGame, setting, addNewProfile, exit, choiceBox,
                password);

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

    public static Menu getInstance()
    {
        return instance;
    }

    public void updateChoiceBox()
    {
        choiceBox.getItems().addAll(Account.getAllAccounts());
    }

    public String getAccount()
    {
        return choiceBox.getValue();
    }

    public String getPass()
    {
        String pass = password.getText();
        password.clear();
        return pass;
    }


    public void showNotChosenAccount()
    {
        clearMessages();
        notChosenAccount.setVisible(true);
    }

    public void clearMessages()
    {
        wrongPass.setVisible(false);
        notChosenAccount.setVisible(false);
    }

    public void showWrongPass()
    {
        clearMessages();
        wrongPass.setVisible(true);
    }

}

