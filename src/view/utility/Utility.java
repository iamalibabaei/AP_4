package view.utility;

import javafx.animation.FadeTransition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;
import view.MainView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Utility
{
    public static final double ERROR_MESSAGE_MENU_X = MainView.WIDTH * 0.01;
    public static final double ERROR_MESSAGE_MENU_Y = -MainView.HEIGHT * 0.23;

    public static ImageView getImageView(String path)
    {
        return new ImageView(Utility.getImage(path));
    }

    public static Image getImage(String path)
    {
        Image image = null;
        try
        {
            image = new Image(new FileInputStream(path));
        } catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        return image;
    }

    public static MediaPlayer getPlayer(String path)
    {
        return new MediaPlayer(new Media(getSound(path)));
    }

    public static String getSound(String path)
    {

        String media = new File(path).toURI().toString();
        return media;
    }

    public static Text showError(double x, double y, String message)
    {
        Text error = new Text(message);
        error.setFill(Color.RED);
        error.setFont(Font.font(30));
        error.relocate(x, y);
        FadeTransition ft = new FadeTransition(Duration.millis(3000), error);
        ft.setFromValue(2.0);
        ft.setToValue(0.0);
        new Thread(() -> {
            try
            {
                Thread.sleep(2000);
            } catch (InterruptedException e)
            {
                e.printStackTrace();
            }
            ft.play();
        }).start();
        return error;
    }

}
