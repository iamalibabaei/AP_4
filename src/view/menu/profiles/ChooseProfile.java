package view.menu.profiles;

import controller.MenuController;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import models.account.Account;
import view.MainView;
import view.PaneBuilder;
import view.utility.Utility;
import view.utility.constants.PictureAddresses;

import java.io.IOException;

public class ChooseProfile extends PaneBuilder
{
    private static ChooseProfile instance = new ChooseProfile();
    ChoiceBox<String> choiceBox;

    private ChooseProfile()
    {
        super(MainView.WIDTH * 0.4, MainView.HEIGHT * 0.3);
        setVisible(false);
    }

    public static ChooseProfile getInstance()
    {
        return instance;
    }

    @Override
    protected void build()
    {
        buildMenuTemplate();
        buildGetAccounts();
        buildAddNewPlayer();
        StackPane backButton = Utility.makeMenuButton(childrenList, MainView.WIDTH * 0.08, MainView.HEIGHT * 0.4, MainView.HEIGHT * 0.3,
                MainView.HEIGHT * 0.3 / 2,"Back",
                event -> setVisible(false));
        childrenList.addAll(backButton);
    }

    private void buildMenuTemplate()
    {
        ImageView imageView = new ImageView(Utility.getImage(PictureAddresses.GAME_MENU));
        imageView.relocate(0, -MainView.HEIGHT * 0.1);
        imageView.setFitHeight(2 * MainView.HEIGHT / 3);
        imageView.setFitWidth(MainView.WIDTH * 0.4);
        childrenList.add(imageView);
    }

    private void buildGetAccounts()
    {
        Text text = new Text("Choose Your Account");
        text.setFont(Font.font("Rage Italic", 25));
        text.relocate(MainView.WIDTH * 0.1, 0);
        childrenList.addAll(text);
        choiceBox = new ChoiceBox<>();
        choiceBox.relocate(MainView.WIDTH * 0.1, MainView.HEIGHT * 0.1);
        choiceBox.setVisible(true);
        StackPane startGameButton = Utility.makeMenuButton(childrenList,MainView.WIDTH * 0.125, 20, MainView.HEIGHT * 0.3,
                MainView.HEIGHT * 0.3 / 2,"Go",
                event -> goToMissionView());
        childrenList.addAll(choiceBox, startGameButton);
    }

    private void buildAddNewPlayer()
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
        StackPane submitButton = Utility.makeMenuButton(childrenList, MainView.WIDTH * 0.08, MainView.HEIGHT * 0.3, MainView.HEIGHT * 0.3,
                MainView.HEIGHT * 0.3 / 2,"SUBMIT",
                event -> addNewPlayer(name, password));
        childrenList.addAll(newPlayerText, name, password, submitButton);
    }

    private void goToMissionView()
    {
        String name = choiceBox.getValue();
        try
        {
            System.out.println(name);
            MenuController.getInstance().setCurrentAccount(name);
        } catch (IOException e)
        {
            childrenList.add(Utility.showError(Utility.ERROR_MESSAGE_MENU_X, Utility.ERROR_MESSAGE_MENU_Y,
                    e.getMessage()));
            return;

        }
        setVisible(false);
        MainView.getInstance().goToMap();

    }

    private void addNewPlayer(TextField name, PasswordField password)
    {
        try
        {
            Account.addAccount(name.getText(), password.getText());
            choiceBox.getItems().clear();
            choiceBox.getItems().addAll(Account.getAllAccounts());
        } catch (IOException e)
        {
            childrenList.add(Utility.showError(Utility.ERROR_MESSAGE_MENU_X, Utility.ERROR_MESSAGE_MENU_Y,
                    e.getMessage()));
        } finally
        {
            name.clear();
            password.clear();
        }
    }

    public void toggleChooseProfilePane()
    {
        choiceBox.getItems().clear();
        choiceBox.getItems().addAll(Account.getAllAccounts());
        setVisible(!isVisible());

    }

}
