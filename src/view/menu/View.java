package view.menu;

import javafx.scene.layout.StackPane;
import view.SceneBuilder;
import view.MainView;
import view.menu.profiles.ChooseProfile;
import view.multiplayer.Panel;
import view.utility.SoundPlayer;
import view.utility.Utility;
import view.utility.constants.SoundAddresses;

public class View extends SceneBuilder
{
    private static View instance = new View();

    private View()
    {
        super(MainView.WIDTH, MainView.HEIGHT);
        // todo its better to be screen width and screen height as it is!
    }

    public static View getInstance()
    {
        return instance;
    }

    protected void build()
    {
        childrenList.addAll(Background.getInstance());
        Utility.makeMenuButton(childrenList,MainView.WIDTH * 0.25, MainView.HEIGHT * 0.3,MainView.HEIGHT * 0.3,
                MainView.HEIGHT * 0.3 / 2 ,"Start Game", event -> ChooseProfile.getInstance().toggleChooseProfilePane());
        Utility.makeMenuButton(childrenList,MainView.WIDTH * 0.25, MainView.HEIGHT * 0.5,MainView.HEIGHT * 0.3,
                MainView.HEIGHT * 0.3 / 2,"Settings", event -> view.settings.View.getInstance().start());
        Utility.makeMenuButton(childrenList,MainView.WIDTH * 0.25, MainView.HEIGHT * 0.4,MainView.HEIGHT * 0.3,
                MainView.HEIGHT * 0.3 / 2,"Multiplayer",
                event -> MainView.getInstance().multiPlayer());
        Utility.makeMenuButton(childrenList,MainView.WIDTH * 0.25, MainView.HEIGHT * 0.6,MainView.HEIGHT * 0.3,
                MainView.HEIGHT * 0.3 / 2,"Exit",
                event -> {
                    MainView.getInstance().close();
                    SoundPlayer.getInstance().play(Utility.getSound(SoundAddresses.MENU_COW_SOUND));
                });
        SoundPlayer.getInstance().playBackground(Utility.getSound(SoundAddresses.MENU_MUSIC));
        childrenList.addAll(ChooseProfile.getInstance(),
                ExitPanel.getInstance(), Panel.getInstance(), view.settings.View.getInstance());

    }


}
