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
import view.multiplayer.Panel;
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
        StackPane startGame = Utility.makeMenuButton(MainView.WIDTH * 0.25, MainView.HEIGHT * 0.3,MainView.HEIGHT * 0.3,
                MainView.HEIGHT * 0.3 / 2 ,"Start Game", event -> ChooseProfile.getInstance().toggleChooseProfilePane());
        StackPane setting = Utility.makeMenuButton(MainView.WIDTH * 0.25, MainView.HEIGHT * 0.5,MainView.HEIGHT * 0.3,
                MainView.HEIGHT * 0.3 / 2,"Settings", event -> view.settings.View.getInstance().start());
        StackPane multiPlayer = Utility.makeMenuButton(MainView.WIDTH * 0.25, MainView.HEIGHT * 0.4,MainView.HEIGHT * 0.3,
                MainView.HEIGHT * 0.3 / 2,"Multiplayer",
                event -> MainView.getInstance().multiPlayer());
        StackPane exit = Utility.makeMenuButton(MainView.WIDTH * 0.25, MainView.HEIGHT * 0.6,MainView.HEIGHT * 0.3,
                MainView.HEIGHT * 0.3 / 2,"Exit",
                event -> {
                    MainView.getInstance().close();
                    SoundPlayer.getInstance().play(Utility.getSound(SoundAddresses.MENU_COW_SOUND));
                });
        root.getChildren().addAll(Background.getInstance(), startGame, multiPlayer, setting, exit, ChooseProfile.getInstance(),
                ExitPanel.getInstance(), Panel.getInstance(), view.settings.View.getInstance());

    }


}
