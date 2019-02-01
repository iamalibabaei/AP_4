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
        StackPane startGame = makeMenuButton(200, 200, "Start Game", event -> GoToMap.getInstance().open());
        StackPane setting = makeMenuButton(200, 280, "Settings", event -> MainView.getInstance().goToSetting());
        StackPane exit = makeMenuButton(200, 440, "Exit", event -> MainView.getInstance().close());

        root.getChildren().addAll(MenuBackground.getInstance(), startGame, setting, exit, GoToMap.getInstance());

    }

    public static Menu getInstance()
    {
        return instance;
    }

}

