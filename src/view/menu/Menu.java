package view.menu;

import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import view.MainView;
import view.utility.AddressConstants;
import view.utility.SoundPlayer;
import view.utility.Utility;

public class Menu extends Scene
{
    private static Menu instance = new Menu();
    private Group root;

    public Menu()
    {
        super(new Group(), (double) MainView.SCREEN_WIDTH, (double) MainView.SCREEN_HEIGHT, Color.BLACK);
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
        pane.relocate((double) x + MainView.OFFSET_X, (double) y + MainView.OFFSET_Y);
        return pane;
    }

    private void build()
    {
        root.getChildren().clear();
        StackPane startGame = makeMenuButton(00, 200, "Start Game", event -> ChooseProfile.getInstance().open());
        StackPane setting = makeMenuButton(200, 280, "Settings", event -> MainView.getInstance().goToSetting());
        StackPane exit = makeMenuButton(200, 440, "Exit", event -> MainView.getInstance().close());
        root.getChildren().addAll(MenuBackground.getInstance(), startGame, setting, exit, ChooseProfile.getInstance());

    }

    public static Menu getInstance()
    {
        return instance;
    }

}

