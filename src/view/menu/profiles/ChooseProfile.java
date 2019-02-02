package view.menu.profiles;

import controller.MenuController;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import models.account.Account;
import view.MainView;
import view.menu.View;
import view.utility.Utility;
import view.utility.constants.PictureAddresses;

import java.io.IOException;

public class ChooseProfile
{
    private static Pane pane;
    private static ObservableList<Node> list;

    static
    {
        pane = new Pane();
        list = pane.getChildren();
    }

    private ChooseProfile()
    {
    }

    public static Pane build()
    {
        pane.relocate(MainView.WIDTH * 0.4, MainView.HEIGHT * 0.3);
        pane.setVisible(false);
        buildMenuTemplate();
        buildGetAccounts();
        buildAddNewPlayer();
        StackPane backButton = View.makeMenuButton(MainView.WIDTH * 0.08, MainView.HEIGHT * 0.4, "Back",
                event -> pane.setVisible(false));
        list.addAll(backButton);

        return pane;

    }

    private static void buildMenuTemplate()
    {
        ImageView imageView = new ImageView(Utility.getImage(PictureAddresses.GAME_MENU));
        imageView.relocate(0, -MainView.HEIGHT * 0.1);
        imageView.setFitHeight(2 * MainView.HEIGHT / 3);
        imageView.setFitWidth(MainView.WIDTH * 0.4);
        list.add(imageView);
    }

    private static void buildGetAccounts()
    {
        ChoiceBox<String> choiceBox;
        Text text = new Text("Choose Your Account");
        text.setFont(Font.font("Rage Italic", 25));
        text.relocate(MainView.WIDTH * 0.1, 0);
        list.addAll(text);
        choiceBox = new ChoiceBox<>();
        choiceBox.setOnMouseClicked(event -> {
            choiceBox.getItems().clear();
            choiceBox.getItems().addAll(Account.getAllAccounts());
        });
        choiceBox.relocate(MainView.WIDTH * 0.1, MainView.HEIGHT * 0.1);
        choiceBox.setVisible(true);
        StackPane startGameButton = View.makeMenuButton(MainView.WIDTH * 0.125, 20, "Go",
                event -> goToMissionView(choiceBox.getValue()));
        list.addAll(choiceBox, startGameButton);
    }

    private static void buildAddNewPlayer()
    {
        Text newPlayerText = new Text("Or create a new account");
        newPlayerText.setFont(Font.font("SWItalt", 15));
        newPlayerText.relocate(MainView.WIDTH * 0.1, MainView.HEIGHT * 0.15);
        TextField name = new TextField();
        name.setPromptText("Name");
        name.relocate(MainView.WIDTH * 0.1, MainView.HEIGHT * 0.2);
        PasswordField password = new PasswordField();
        password.setPromptText("Password");
        password.relocate(MainView.WIDTH * 0.1, MainView.HEIGHT * 0.25);
        StackPane submitButton = View.makeMenuButton(MainView.WIDTH * 0.08, MainView.HEIGHT * 0.3, "SUBMIT",
                event -> addNewPlayer(name, password));
        list.addAll(newPlayerText, name, password, submitButton);
    }

    private static void goToMissionView(String name)
    {
        try
        {
            MenuController.getInstance().setCurrentAccount(name);
        } catch (IOException e)
        {
            list.add(Utility.showError(Utility.ERROR_MESSAGE_MENU_X, Utility.ERROR_MESSAGE_MENU_Y, e.getMessage()));
        }
        MainView.getInstance().goToMap();

    }

    private static void addNewPlayer(TextField name, PasswordField password)
    {
        try
        {
            Account.addAccount(name.getText(), password.getText());
            // todo show message
        } catch (IOException e)
        {
            list.add(Utility.showError(Utility.ERROR_MESSAGE_MENU_X, Utility.ERROR_MESSAGE_MENU_Y, e.getMessage()));
        } finally
        {
            name.clear();
            password.clear();
        }
    }

    public static void toggleChooseProfilePane()
    {
        pane.setVisible(!pane.isVisible());
    }

}
