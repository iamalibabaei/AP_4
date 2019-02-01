package view.menu;

import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import view.MainView;
import view.utility.AddressConstants;
import view.utility.SoundPlayer;
import view.utility.Utility;

public class Menu
{

    private Menu()
    {
    }

    public static void build(Group root)
    {
        StackPane startGame = makeMenuButton(MainView.WIDTH * 0.25, MainView.HEIGHT * 0.3, "Start Game",
                event -> ChooseProfile.getInstance().open());
        StackPane setting = makeMenuButton(MainView.WIDTH * 0.25, MainView.HEIGHT * 0.5, "Settings",
                event -> MainView.getInstance().goToSetting());
        StackPane multiPlayer = makeMenuButton(MainView.WIDTH * 0.25, MainView.HEIGHT * 0.4, "MultiPlayer",
                event -> multiPlayer());
        StackPane exit = makeMenuButton(MainView.WIDTH * 0.25, MainView.HEIGHT * 0.6, "Exit",
                event -> MainView.getInstance().close());
        Pane background = MenuBackground.build();
        root.getChildren().addAll(background, startGame, multiPlayer, setting, exit, ChooseProfile.getInstance());
        SoundPlayer.getInstance().playBackground(Utility.getSound(AddressConstants.MENU_MUSIC));

    }

    private static StackPane makeMenuButton(double x, double y, String name, EventHandler<? super MouseEvent> value)
    {
        final double width = MainView.HEIGHT * 0.3, height = width / 2;
        ImageView button = new ImageView(Utility.getImage(AddressConstants.MENU_BUTTON));
        button.setFitWidth(width);
        button.setFitHeight(height);
        StackPane pane = new StackPane();
        Text text = new Text(name);
        text.setFont(Font.font("SWItalt", 15));
        text.setFill(Color.WHITE);
        pane.getChildren().addAll(button, text);
        pane.setOnMouseClicked(value);
        pane.relocate(x, y);
        return pane;
    }

    private static void multiPlayer()
    {
        //TODO
    }

}
