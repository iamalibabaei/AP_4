package view;


import controller.InGameController;
import controller.MenuController;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;
import models.account.Account;
import models.misc.Mission;
import models.objects.Entity;
import models.objects.Grass;
import models.objects.Item;
import models.objects.animals.Animal;
import view.gameScene.View;
import view.menu.ExitPanel;
import view.menu.selectMission.MissionScene;
import view.multiplayer.Panel;
import view.utility.SoundPlayer;
import view.utility.Utility;
import view.utility.constants.SoundAddresses;

public class MainView extends Application
{
    public static final double SCREEN_WIDTH = Screen.getPrimary().getBounds().getWidth();
    public static final double SCREEN_HEIGHT = Screen.getPrimary().getBounds().getHeight();
    public static final double HEIGHT = SCREEN_HEIGHT;
    public static final double WIDTH = HEIGHT * 4 / 3;
    public static final double OFFSET_X = (SCREEN_WIDTH - WIDTH) / 2;
    public static final double OFFSET_Y = 0;
    private static MainView instance = new MainView();
    private static Stage mainStage;

    public MainView()
    {
    }

    private static MenuController getController()
    {
        return MenuController.getInstance();
    }

    public static MainView getInstance()
    {
        return instance;
    }

    @Override
    public void start(Stage primaryStage)
    {

        System.out.println("start");
        primaryStage.setFullScreenExitHint("");
        mainStage = primaryStage;
        mainStage.setFullScreen(true);
        mainStage.setResizable(true);
        SoundPlayer.getInstance().playBackground(Utility.getSound(SoundAddresses.MENU_MUSIC));
        mainStage.setScene(view.menu.View.getInstance());
        mainStage.show();
        mainStage.setOnCloseRequest(event -> close());
    }

    public void close()
    {
        ExitPanel.getInstance().close();
    }

    public void multiPlayer()
    {
        Panel.getInstance().start();
    }

    public void goToMap()
    {
        mainStage.setScene(MissionScene.getInstance());
        mainStage.setFullScreen(true);
    }

    public void goToMenu()
    {
        if (!SoundPlayer.getInstance().getCurrentPlayingSound().equals(Utility.getSound(SoundAddresses.MENU_MUSIC)))
        {
            SoundPlayer.getInstance().playBackground(Utility.getSound(SoundAddresses.MENU_MUSIC));
        }
        mainStage.setScene(view.menu.View.getInstance());
        mainStage.setFullScreen(true);
    }

    public void startGame(Mission mission)
    {
        mainStage.setScene(View.getInstance());
        mainStage.setFullScreen(true);
    }

}
