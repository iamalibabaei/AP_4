package view.menu;

import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;
import view.MainView;
import view.menu.profiles.ChooseProfile;
import view.utility.constants.PictureAddresses;
import view.utility.SoundPlayer;
import view.utility.Utility;
import view.utility.constants.SoundAddresses;

public class View
{

    private View()
    {
    }

    public static void build(Group root)
    {
        StackPane startGame = makeMenuButton(MainView.WIDTH * 0.25, MainView.HEIGHT * 0.3, "Start Game",
                event -> ChooseProfile.open());
        StackPane setting = makeMenuButton(MainView.WIDTH * 0.25, MainView.HEIGHT * 0.5, "Settings",
                event -> MainView.getInstance().goToSetting());
        StackPane multiPlayer = makeMenuButton(MainView.WIDTH * 0.25, MainView.HEIGHT * 0.4, "Multiplayer",
                event -> multiPlayer());
        StackPane exit = makeMenuButton(MainView.WIDTH * 0.25, MainView.HEIGHT * 0.6, "Exit",
                event -> MainView.getInstance().close());
        Pane background = Background.build();
        Pane chooseProfile = ChooseProfile.build();
        root.getChildren().addAll(background, startGame, multiPlayer, setting, exit, chooseProfile);
        SoundPlayer.getInstance().playBackground(Utility.getSound(SoundAddresses.MENU_MUSIC));

    }

    public static StackPane makeMenuButton(double x, double y, String name, EventHandler<? super MouseEvent> value)
    {
        final double width = MainView.HEIGHT * 0.3, height = width / 2;
        ImageView button = new ImageView(Utility.getImage(PictureAddresses.MENU_BUTTON));
        MediaPlayer sound = Utility.getPlayer(SoundAddresses.BUTTON_CLICK_SOUND);
        sound.setOnEndOfMedia(sound::stop);
        button.setFitWidth(width);
        button.setFitHeight(height);
        StackPane pane = new StackPane();
        Text text = new Text(name);
        text.setFont(Font.font("SWItalt", 15));
        text.setFill(Color.WHITE);
        pane.getChildren().addAll(button, text);
        pane.setOnMouseClicked(event -> {
            sound.play();
            value.handle(event);
        });
        pane.relocate(x, y);
        return pane;
    }

    private static void multiPlayer()
    {
        //TODO
    }

}
