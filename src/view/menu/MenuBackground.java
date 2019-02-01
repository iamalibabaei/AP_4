package view.menu;

import javafx.animation.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;
import view.MainView;
import view.utility.AddressConstants;
import view.utility.SoundPlayer;
import view.utility.Utility;

public class MenuBackground extends Pane
{
    private static MenuBackground instance = new MenuBackground();

    private MenuBackground()
    {
        setWidth((double) MainView.WIDTH);
        setHeight((double) MainView.HEIGHT);
        relocate((double) MainView.OFFSET_X, (double) MainView.OFFSET_Y);
        build();
    }

    private void build()
    {
        buildWhiteBack();
        buildCloud();
        buildGrass();
        buildSun();
        buildRainbow();
        buildBirds();
        buildBillBoard();
        buildTree();
        addPig();
        addRooster();
        addCow();
        addBird();
        buildFlower();
    }

    private void buildWhiteBack() {
        ImageView whiteImage = new ImageView(Utility.getImage(AddressConstants.WHITE_IMAGE));
        whiteImage.setFitWidth((double) MainView.SCREEN_WIDTH * 4/3);
        whiteImage.setFitHeight((double) MainView.SCREEN_HEIGHT);
        whiteImage.relocate(- MainView.WIDTH / 3, 0);
        getChildren().add(whiteImage);
    }

    private void buildCloud()
    {
        ImageView cloud = new ImageView(Utility.getImage(AddressConstants.MENU_CLOUD));
        cloud.setFitWidth((double) MainView.SCREEN_WIDTH * 4/3);
        cloud.setFitHeight((double) MainView.SCREEN_HEIGHT);
        cloud.relocate(- MainView.WIDTH / 3, 0);
        getChildren().addAll(cloud);
    }

    private void buildGrass()
    {
        ImageView grass = new ImageView(Utility.getImage(AddressConstants.MENU_GRASS1));
        grass.setFitWidth((double) MainView.SCREEN_WIDTH * 4/3);
        grass.setFitHeight((double) MainView.SCREEN_HEIGHT * 0.5);
        grass.relocate(- MainView.WIDTH / 3, MainView.HEIGHT * 0.5);
        getChildren().addAll(grass);
    }

    private void buildSun()
    {
        ImageView sun = new ImageView(Utility.getImage(AddressConstants.MENU_SUN));
        final double width = (double) (MainView.HEIGHT / 3);
        final double height = (double) (MainView.HEIGHT / 3);
        sun.setFitHeight(width);
        sun.setFitWidth(height);
        sun.relocate((double) MainView.WIDTH - width, 0);
        //Timeline
        KeyValue kvRotateSun = new KeyValue(sun.rotateProperty(), 180);
        KeyFrame keyFrameSun = new KeyFrame(Duration.millis(2000), kvRotateSun);
        Timeline timelineSun = new Timeline(keyFrameSun);
        timelineSun.getKeyFrames().addAll(keyFrameSun);
        timelineSun.setAutoReverse(true);
        timelineSun.setCycleCount(Animation.INDEFINITE);
        timelineSun.play();
        getChildren().addAll(sun);
    }

    private void buildRainbow()
    {
        ImageView rainBow = new ImageView(Utility.getImage(AddressConstants.MENU_RAINBOW));
        rainBow.setFitHeight(MainView.HEIGHT / 4);
        rainBow.setFitWidth(MainView.HEIGHT / 2);
        //PathTransition
        final double YValue = MainView.HEIGHT / 4, XValueStart = MainView.WIDTH /4, XValueEnd = MainView.WIDTH  * 0.8;
        Path path = new Path(new MoveTo(XValueStart, YValue),
                new LineTo(XValueEnd, YValue));
        path.setVisible(false);
        getChildren().add(path);
        PathTransition pathTransition = new PathTransition(Duration.millis(5000), path, rainBow);
        pathTransition.setAutoReverse(true);
        pathTransition.setCycleCount(Animation.INDEFINITE);
        pathTransition.play();
        getChildren().addAll(rainBow);
    }

    private void buildBirds()
    {
        ImageView birds = new ImageView(Utility.getImage(AddressConstants.MENU_BIRDS));
        birds.setFitWidth(MainView.HEIGHT / 4);
        birds.setFitHeight(MainView.HEIGHT / 4);
        //PathTransition
        Path path1 = new Path(new MoveTo(MainView.WIDTH, MainView.HEIGHT * 0.3),
                new LineTo(MainView.WIDTH * 0.2, MainView.HEIGHT * 0.5));
        path1.setVisible(false);
        getChildren().add(path1);
        PathTransition pathTransition1 = new PathTransition(Duration.millis(15000), path1, birds);
        pathTransition1.setAutoReverse(true);
        pathTransition1.setCycleCount(Animation.INDEFINITE);
        pathTransition1.play();
        getChildren().addAll(birds);
    }

    private void buildBillBoard()
    {
        ImageView billboard = new ImageView(Utility.getImage(AddressConstants.MENU_BILLBOARD));
        final double width = MainView.WIDTH / 2;
        billboard.setFitHeight(width);
        billboard.setFitWidth(width);
        billboard.relocate(MainView.WIDTH * 0.75, MainView.HEIGHT - width);
        Text text = new Text(
                "FARM FRENZY\n" +
                        "BY : AP_4\n"
        );
        text.setFont(Font.font("Courier New", 30));
        text.relocate(MainView.WIDTH * 0.8, MainView.HEIGHT * 0.65);
        getChildren().addAll(billboard);
        getChildren().addAll(text);
    }

    private void buildTree()
    {
        ImageView tree = new ImageView(Utility.getImage(AddressConstants.MENU_Tree));
        tree.setFitHeight(MainView.HEIGHT * 0.8);
        tree.setFitWidth(MainView.HEIGHT * 0.8);
        tree.relocate(0, MainView.HEIGHT * 0.2);
        getChildren().addAll(tree);
    }

    private void addPig()
    {
        double XValueStart, YValueStart, XValueEnd, YValueEnd, rotate, height, width, time;
        Image animalImage;
        String musicSound = null;
        animalImage = Utility.getImage(AddressConstants.MENU_PIG);
        height = MainView.HEIGHT * 0.075;
        width = MainView.HEIGHT * 0.075;
        rotate = 5;

        XValueStart = MainView.WIDTH * 0.65;
        YValueStart = MainView.WIDTH * 0.55;

        XValueEnd = - MainView.WIDTH * 0.1;
        YValueEnd = - MainView.WIDTH * 0.05;
        time = 20000;
        makeAnimation(XValueStart, YValueStart, XValueEnd, YValueEnd, rotate, height, width, time, animalImage,
                musicSound);

    }

    private void addRooster()
    {
        double XValueStart, YValueStart, XValueEnd, YValueEnd, rotate, height, width, time;
        Image animalImage;
        String musicSound = Utility.getSound(AddressConstants.MENU_ROOSTER_SOUND);
        animalImage = Utility.getImage(AddressConstants.MENU_ROOSTER);
        height = MainView.HEIGHT / 3;
        width = MainView.HEIGHT / 3;
        rotate = 5;
        XValueEnd = 0;
        XValueStart = MainView.WIDTH * 0.8;
        YValueEnd = 0;
        YValueStart = MainView.HEIGHT * 0.31;
        time = 2000;
        makeAnimation(XValueStart, YValueStart, XValueEnd, YValueEnd, rotate, height, width, time, animalImage,
                musicSound);
    }

    private void addCow()
    {
        double XValueStart, YValueStart, XValueEnd, YValueEnd, rotate, height, width, time;
        Image animalImage;
        String musicSound = Utility.getSound(AddressConstants.MENU_COW_SOUND);
        animalImage = Utility.getImage(AddressConstants.MENU_COW);
        height = MainView.HEIGHT / 4;
        width = 2 * MainView.HEIGHT / 4;
        rotate = 20;
        XValueEnd = MainView.WIDTH * 0.6;
        XValueStart = 0;
        YValueEnd = 0;
        YValueStart = MainView.HEIGHT * 0.7;
        time = 15000;
        makeAnimation(XValueStart, YValueStart, XValueEnd, YValueEnd, rotate, height, width, time, animalImage,
                musicSound);
    }

    private void addBird()
    {
        double XValueStart, YValueStart, XValueEnd, YValueEnd, rotate, height, width, time;
        Image animalImage;
        String musicSound = null;
        height = 50;
        width = 50;
        rotate = 10;
        XValueEnd = 0;
        XValueStart = MainView.WIDTH * 0.15;
        YValueEnd = 0;
        YValueStart = MainView.HEIGHT * 0.5;
        time = 1000;
        animalImage = Utility.getImage(AddressConstants.MENU_BIRD);

        makeAnimation(XValueStart, YValueStart, XValueEnd, YValueEnd, rotate, height, width, time, animalImage,
                musicSound);

    }

    private void buildFlower()
    {
        ImageView flower = new ImageView(Utility.getImage(AddressConstants.MENU_FLOWER));
        flower.setFitWidth((double) MainView.WIDTH * 1.5);
        flower.setFitHeight(MainView.HEIGHT / 3);
//        flower.relocate(0, (double) MainView.HEIGHT);
        flower.relocate(- MainView.WIDTH / 3, (double) (MainView.HEIGHT * 2 / 3));
        getChildren().addAll(flower);
    }

    private void makeAnimation(double XValueStart, double YValueStart, double XValueEnd, double YValueEnd, double rotate
            , double height, double width, double time, Image animalImage, String musicSound)
    {
        ImageView animalView = new ImageView(animalImage);
        animalView.setRotate(-rotate);
        animalView.setFitWidth(width);
        animalView.setFitHeight(height);
        animalView.relocate(XValueStart, YValueStart);
        if (musicSound != null)
        {
            animalView.setOnMouseClicked(event -> SoundPlayer.getInstance().play(musicSound));
        }
        getChildren().addAll(animalView);
        KeyValue xvalue = new KeyValue(animalView.xProperty(), XValueEnd );
        KeyValue yvalue = new KeyValue(animalView.yProperty(), YValueEnd );
        KeyValue kvRotate = new KeyValue(animalView.rotateProperty(), rotate);
        KeyFrame keyFrame = new KeyFrame(Duration.millis(time), xvalue, yvalue, kvRotate);
        Timeline timeline = new Timeline(keyFrame);
        timeline.getKeyFrames().addAll(keyFrame);
        timeline.setAutoReverse(true);
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();

    }

    public static MenuBackground getInstance()
    {
        return instance;
    }

}
