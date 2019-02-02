package view.settings;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Slider;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import view.MainView;
import view.utility.SoundPlayer;
import view.utility.Utility;
import view.utility.constants.PictureAddresses;


public class View extends Pane {

    private static View instance = new View();
    private ObservableList<Node> list;

    private View(){
        relocate(MainView.WIDTH * 0.375, MainView.HEIGHT * 0.25);
        setVisible(false);
        list = getChildren();
        build();
    }

    public static View getInstance() {
        return instance;
    }

    private void build(){
        buildMenuTemplate();
        StackPane backButton = Utility.makeMenuButton(this.getChildren(),MainView.WIDTH * 0.3,MainView.HEIGHT * 0.35, MainView.WIDTH * 0.1,
                MainView.HEIGHT * 0.125,"Back",
                event -> {
                    setVisible(false);
                });
        list.addAll(backButton);

    }

    private void buildMenuTemplate() {
        ImageView imageView = new ImageView(Utility.getImage(PictureAddresses.GAME_MENU));
        imageView.relocate(0, 0);
        imageView.setFitHeight(MainView.HEIGHT * 0.5);
        imageView.setFitWidth(MainView.WIDTH * 0.5);
        list.addAll(imageView);
        Text text = new Text("music");
        text.relocate(MainView.HEIGHT * 0.1, MainView.HEIGHT * 0.15);
        text.setFont(Font.font(20));
        list.addAll(text);


        Slider music = new Slider();
        music.valueProperty().addListener(
                new ChangeListener<Number>() {

                    public void changed(ObservableValue<? extends Number >
                                                observable, Number oldValue, Number newValue)
                    {

                        SoundPlayer.getInstance().setBackgroundSoundVolume(music.getValue() / music.getMax());
                    }
        });
        music.setValue(music.getMax());

        music.relocate(MainView.HEIGHT * 0.25, MainView.HEIGHT * 0.15);
        list.addAll(music);

        Slider sound = new Slider();
        sound.setValue(sound.getMax());
        sound.valueProperty().addListener(
                new ChangeListener<Number>() {

                    public void changed(ObservableValue<? extends Number >
                                                observable, Number oldValue, Number newValue)
                    {

                        SoundPlayer.getInstance().setMediaVolume(sound.getValue() / sound.getMax());
                    }
                });

        sound.relocate(MainView.HEIGHT * 0.25, MainView.HEIGHT * 0.3);
        list.addAll(sound);
        Text soundText = new Text("sound");
        soundText.relocate(MainView.HEIGHT * 0.1, MainView.HEIGHT * 0.3);
        soundText.setFont(Font.font(20));
        list.addAll(soundText);


    }
    public void start()
    {
        setVisible(true);
    }
}

