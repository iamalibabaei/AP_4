package view.menu;

import controller.AddressConstants;
import controller.SoundPlayer;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.PathTransition;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;
import view.View;

public class MenuBackground extends Pane {
    private static MenuBackground instance = new MenuBackground();

    public static MenuBackground getInstance() {
        return instance;
    }

    private MenuBackground() {
        setHeight(View.HEIGHT);
        setWidth(View.WIDTH);
        relocate(0, 0);
        build();

    }

    private void build() {
        ImageView cloud = new ImageView(AddressConstants.getImage(AddressConstants.MENU_CLOUD));
        cloud.setFitWidth(View.WIDTH);
        cloud.setFitHeight(View.HEIGHT);
        cloud.relocate(0, - View.HEIGHT * 0.5);
        getChildren().addAll(cloud);

        ImageView grass1 = new ImageView(AddressConstants.getImage(AddressConstants.MENU_GRASS1));
        grass1.setFitHeight(View.HEIGHT / 2);
        grass1.setFitWidth(View.WIDTH);
        relocate(0, View.HEIGHT * 0.5);
        getChildren().addAll(grass1);

        ImageView sun = new ImageView(AddressConstants.getImage(AddressConstants.MENU_SUN));
        sun.setFitHeight(250);
        sun.setFitWidth(250);
        sun.relocate(1000, -400);
        getChildren().addAll(sun);

        //Timeline
        KeyValue kvRotateSun = new KeyValue(sun.rotateProperty(), 180);
        KeyFrame keyFrameSun = new KeyFrame(Duration.millis(2000), kvRotateSun);
        Timeline timelineSun = new Timeline(keyFrameSun);
        timelineSun.getKeyFrames().addAll(keyFrameSun);
        timelineSun.setAutoReverse(true);
        timelineSun.setCycleCount(4);
        timelineSun.setCycleCount(Timeline.INDEFINITE);
        timelineSun.play();
        //sky and birds
        ImageView rainBow = new ImageView(AddressConstants.getImage(AddressConstants.MENU_RAINBOW));
        rainBow.setFitHeight(200);
        rainBow.setFitWidth(400);
        getChildren().addAll(rainBow);

        //PathTransition
        Path path = new Path(new MoveTo(400, -200), new LineTo(1000, -200));
        path.setVisible(false);
        getChildren().add(path);

        PathTransition pathTransition = new PathTransition(Duration.millis(5000), path, rainBow);
        pathTransition.setAutoReverse(true);
        pathTransition.setCycleCount(Timeline.INDEFINITE);
        pathTransition.play();

        ImageView birds = new ImageView(AddressConstants.getImage(AddressConstants.MENU_BIRDS));
        birds.setFitWidth(200);
        birds.setFitHeight(200);
        getChildren().addAll(birds);

        //PathTransition
        Path path1 = new Path(new MoveTo(1000, -100), new LineTo(350, 0));
        path1.setVisible(false);
        getChildren().add(path1);

        PathTransition pathTransition1 = new PathTransition(Duration.millis(15000), path1, birds);
        pathTransition1.setAutoReverse(true);
        pathTransition1.setCycleCount(Timeline.INDEFINITE);

        pathTransition1.play();

        ImageView billboard = new ImageView(AddressConstants.getImage(AddressConstants.MENU_BILLBOARD));
        billboard.setFitHeight(500);
        billboard.setFitWidth(500);
        billboard.relocate(900, -135);
        getChildren().addAll(billboard);

        //

        Text text = new Text(
                "FARM FRENZY\n" +
                        "BY : AP_4\n"
        );
        text.setFont(Font.font("Courier New", 30));
        text.relocate(980, 90);
        getChildren().addAll(text);



        ImageView tree = new ImageView(AddressConstants.getImage(AddressConstants.MENU_Tree));
        tree.setFitHeight(700);
        tree.setFitWidth(700);
        tree.relocate(0, -350);
        getChildren().addAll(tree);


        //add animals
        for (int i = 0; i < 4; i++) {
            int XValueStart, YValueStart,XValueEnd,YValueEnd, rotate, height,width, time;
            Image animalImage;
            var musicSoundAnonymousObject = new Object() {
                String musicSound = null;
            };
            switch (i) {
                case 0:
                    animalImage = AddressConstants.getImage(AddressConstants.MENU_PIG);
                    height = 75;
                    width = 75;
                    rotate = 5;
                    XValueEnd = -150;
                    XValueStart = 830;
                    YValueEnd = -20;
                    YValueStart = 150;
                    time = 20000;
                    break;

                case 1:
                    musicSoundAnonymousObject.musicSound = AddressConstants.getSound(AddressConstants.MENU_ROOSTER_SOUND);
                    animalImage = AddressConstants.getImage(AddressConstants.MENU_ROOSTER);
                    height = 200;
                    width = 200;
                    rotate = 5;
                    XValueEnd = 0;
                    XValueStart = 1000;
                    YValueEnd = 0;
                    YValueStart = -105;
                    time = 2000;
                    break;
                case 2:
                    musicSoundAnonymousObject.musicSound = AddressConstants.getSound(AddressConstants.MENU_COW_SOUND);
                    animalImage = AddressConstants.getImage(AddressConstants.MENU_COW);
                    height = 200;
                    width = 400;
                    rotate = 20;
                    XValueEnd = 900;
                    XValueStart = 200;
                    YValueEnd = 0;
                    YValueStart = 150;
                    time = 15000;


                    break;
                default:
                    height = 50;
                    width = 50;
                    rotate = 10;
                    XValueEnd = 0;
                    XValueStart = 140;
                    YValueEnd = 0;
                    YValueStart = -90;
                    time = 1000;
                    animalImage = AddressConstants.getImage(AddressConstants.MENU_BIRD);

            }
            ImageView animalView = new ImageView(animalImage);
            animalView.setRotate(- rotate);
            animalView.setFitWidth(width);
            animalView.setFitHeight(height);
            animalView.relocate(XValueStart, YValueStart);
            if (musicSoundAnonymousObject.musicSound != null) {
                animalView.setOnMouseClicked(event -> SoundPlayer.getInstance().play(musicSoundAnonymousObject.musicSound));
            }
            getChildren().addAll(animalView);

            KeyValue xvalue = new KeyValue(animalView.xProperty(), XValueEnd);
            KeyValue yvalue = new KeyValue(animalView.yProperty(), YValueEnd);
            KeyValue kvRotate = new KeyValue(animalView.rotateProperty(), rotate);
            KeyFrame keyFrame = new KeyFrame(Duration.millis(time), xvalue, yvalue, kvRotate);
            Timeline timeline = new Timeline(keyFrame);
            timeline.getKeyFrames().addAll(keyFrame);
            timeline.setAutoReverse(true);
            timeline.setCycleCount(Timeline.INDEFINITE);
            timeline.play();

        }

        ImageView flower = new ImageView(AddressConstants.getImage(AddressConstants.MENU_FLOWER));
        flower.setFitWidth(View.WIDTH);
        flower.setFitHeight(500);
        flower.relocate(0, -130);
        getChildren().addAll(flower);

    }

}
