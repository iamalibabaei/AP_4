package view.utility;

import javafx.animation.FadeTransition;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;
import view.MainView;
import view.utility.constants.PictureAddresses;
import view.utility.constants.SoundAddresses;

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


    public static void fade(Duration duration, Node node){
        FadeTransition fadeTransition = new FadeTransition(duration, node);
        fadeTransition.setFromValue(1);
        fadeTransition.setToValue(0);
        fadeTransition.play();
        fadeTransition.setAutoReverse(true);
    }
    public static StackPane makeMenuButton(ObservableList<Node> list, double x, double y, final double width, final double height, String name, EventHandler<? super MouseEvent> value)
    {

        list.addAll();
        StackPane pane = new StackPane();
        Text text = new Text(name);
        text.setFont(Font.font("SWItalt", 15));
        text.setFill(Color.WHITE);
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

}
