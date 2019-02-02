package view.menu;

import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import view.MainView;
import view.menu.profiles.ChooseProfile;
import view.utility.constants.PictureAddresses;
import view.utility.SoundPlayer;
import view.utility.Utility;
import view.utility.constants.SoundAddresses;

public class View extends Scene
{
    private Group root;
    public static View getInstance()
    {
        return instance;
    }

    private static View instance = new View();

    private View()
    {
        super(new Group(), MainView.WIDTH, MainView.HEIGHT);
        root = (Group) getRoot();
        build();
    }

    private void build()
    {
        StackPane startGame = makeMenuButton(MainView.WIDTH * 0.25, MainView.HEIGHT * 0.3, "Start Game",
                event -> ChooseProfile.getInstance().toggleChooseProfilePane());
        StackPane setting = makeMenuButton(MainView.WIDTH * 0.25, MainView.HEIGHT * 0.5, "Settings",
                event -> MainView.getInstance().goToSetting());
        StackPane multiPlayer = makeMenuButton(MainView.WIDTH * 0.25, MainView.HEIGHT * 0.4, "Multiplayer",
                event -> multiPlayer());
        StackPane exit = makeMenuButton(MainView.WIDTH * 0.25, MainView.HEIGHT * 0.6, "Exit",
                event -> MainView.getInstance().close());
        root.getChildren().addAll(Background.getInstance(), startGame, multiPlayer, setting, exit, ChooseProfile.getInstance(), ExitPanel.getInstance());
        SoundPlayer.getInstance().playBackground(Utility.getSound(SoundAddresses.MENU_MUSIC));

    }

    public static StackPane makeMenuButton(double x, double y, String name, EventHandler<? super MouseEvent> value)
    {
        StackPane pane = new StackPane();
        Text text = new Text(name);
        text.setFont(Font.font("SWItalt", 15));
        text.setFill(Color.WHITE);
        final double width = MainView.HEIGHT * 0.3, height = width / 2;
        ImageView button = new ImageView(Utility.getImage(PictureAddresses.MENU_BUTTON));
        ImageView buttonMouseOver = new ImageView(Utility.getImage(PictureAddresses.MENU_BUTTON_BRIGHT));
        buttonMouseOver.setFitWidth(width);
        buttonMouseOver.setFitHeight(height);
        button.setFitWidth(width);
        button.setFitHeight(height);
        pane.getChildren().addAll(button, text);
        pane.setOnMouseEntered(event -> {
            pane.getChildren().removeAll(button, text);
            pane.getChildren().addAll(buttonMouseOver, text);
        });
        pane.setOnMouseExited(event -> {
            pane.getChildren().removeAll(buttonMouseOver, text);
            pane.getChildren().addAll(button, text);
        });
        MediaPlayer sound = Utility.getPlayer(SoundAddresses.BUTTON_CLICK_SOUND);
        sound.setOnEndOfMedia(sound::stop);
        pane.setOnMouseClicked(event -> {
            sound.play();
            value.handle(event);
        });
        pane.relocate(x, y);
        return pane;
    }

    private void multiPlayer()
    {
        //TODO
    }

}
