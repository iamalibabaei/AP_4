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

    private void buildCloud()
    {
        ImageView cloud = new ImageView(Utility.getImage(AddressConstants.MENU_CLOUD));
        cloud.setFitWidth((double) MainView.WIDTH);
        cloud.setFitHeight((double) MainView.HEIGHT);
        cloud.relocate(0, 0);
        getChildren().addAll(cloud);
    }

    private void buildGrass()
    {
        ImageView grass1 = new ImageView(Utility.getImage(AddressConstants.MENU_GRASS1));
        grass1.setFitWidth((double) MainView.WIDTH);
        grass1.setFitHeight((double) (MainView.HEIGHT / 2));
        grass1.relocate(0, (double) (MainView.HEIGHT / 2));
        getChildren().addAll(grass1);
    }

    private void buildSun()
    {
        ImageView sun = new ImageView(Utility.getImage(AddressConstants.MENU_SUN));
        final double width = (double) (MainView.HEIGHT / 5);
        final double height = (double) (MainView.HEIGHT / 5);
        sun.setFitHeight(width);
        sun.setFitWidth(height);
        sun.relocate((double) MainView.WIDTH - width, 0);
        //Timeline
        KeyValue kvRotateSun = new KeyValue(sun.rotateProperty(), 180);
        KeyFrame keyFrameSun = new KeyFrame(Duration.millis(2000), kvRotateSun);
        Timeline timelineSun = new Timeline(keyFrameSun);
        timelineSun.getKeyFrames().addAll(keyFrameSun);
        timelineSun.setAutoReverse(true);
        timelineSun.setCycleCount(4);
        timelineSun.setCycleCount(Animation.INDEFINITE);
        timelineSun.play();
        getChildren().addAll(sun);
    }

    private void buildRainbow()
    {
        ImageView rainBow = new ImageView(Utility.getImage(AddressConstants.MENU_RAINBOW));
        rainBow.setFitHeight(200);
        rainBow.setFitWidth(400);
        //PathTransition
        Path path = new Path(new MoveTo(400, -200),
                new LineTo(1000, -200));
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
        birds.setFitWidth(200);
        birds.setFitHeight(200);
        //PathTransition
        Path path1 = new Path(new MoveTo(1000, -100),
                new LineTo(350, 0));
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
        billboard.setFitHeight(500);
        billboard.setFitWidth(500);
        billboard.relocate(900, -135);
        Text text = new Text(
                "FARM FRENZY\n" +
                        "BY : AP_4\n"
        );
        text.setFont(Font.font("Courier New", 30));
        text.relocate(980, 90);
        getChildren().addAll(text);
        getChildren().addAll(billboard);
    }

    private void buildTree()
    {
        ImageView tree = new ImageView(Utility.getImage(AddressConstants.MENU_Tree));
        tree.setFitHeight(700);
        tree.setFitWidth(700);
        tree.relocate(0, -350);
        getChildren().addAll(tree);
    }

    private void addPig()
    {
        int XValueStart, YValueStart, XValueEnd, YValueEnd, rotate, height, width, time;
        Image animalImage;
        String musicSound = null;
        animalImage = Utility.getImage(AddressConstants.MENU_PIG);
        height = 75;
        width = 75;
        rotate = 5;
        XValueEnd = -150;
        XValueStart = 830;
        YValueEnd = -20;
        YValueStart = 150;
        time = 20000;
        makeAnimation(XValueStart, YValueStart, XValueEnd, YValueEnd, rotate, height, width, time, animalImage,
                musicSound);
    }

    private void addRooster()
    {
        int XValueStart, YValueStart, XValueEnd, YValueEnd, rotate, height, width, time;
        Image animalImage;
        String musicSound = Utility.getSound(AddressConstants.MENU_ROOSTER_SOUND);
        animalImage = Utility.getImage(AddressConstants.MENU_ROOSTER);
        height = 200;
        width = 200;
        rotate = 5;
        XValueEnd = 0;
        XValueStart = 1000;
        YValueEnd = 0;
        YValueStart = -105;
        time = 2000;
        makeAnimation(XValueStart, YValueStart, XValueEnd, YValueEnd, rotate, height, width, time, animalImage,
                musicSound);
    }

    private void addCow()
    {
        int XValueStart, YValueStart, XValueEnd, YValueEnd, rotate, height, width, time;
        Image animalImage;
        String musicSound = Utility.getSound(AddressConstants.MENU_COW_SOUND);
        animalImage = Utility.getImage(AddressConstants.MENU_COW);
        height = 200;
        width = 400;
        rotate = 20;
        XValueEnd = 900;
        XValueStart = 200;
        YValueEnd = 0;
        YValueStart = 150;
        time = 15000;
        makeAnimation(XValueStart, YValueStart, XValueEnd, YValueEnd, rotate, height, width, time, animalImage,
                musicSound);
    }

    private void addBird()
    {
        int XValueStart, YValueStart, XValueEnd, YValueEnd, rotate, height, width, time;
        Image animalImage;
        String musicSound = null;
        height = 50;
        width = 50;
        rotate = 10;
        XValueEnd = 0;
        XValueStart = 140;
        YValueEnd = 0;
        YValueStart = -90;
        time = 1000;
        animalImage = Utility.getImage(AddressConstants.MENU_BIRD);

        makeAnimation(XValueStart, YValueStart, XValueEnd, YValueEnd, rotate, height, width, time, animalImage,
                musicSound);

    }

    private void buildFlower()
    {
        ImageView flower = new ImageView(Utility.getImage(AddressConstants.MENU_FLOWER));
        flower.setFitWidth((double) MainView.WIDTH);
        flower.setFitHeight(MainView.HEIGHT / 3);
//        flower.relocate(0, (double) MainView.HEIGHT);
        flower.relocate(0, (double) (MainView.HEIGHT * 2 / 3));
        getChildren().addAll(flower);
    }

    private void makeAnimation(int XValueStart, int YValueStart, int XValueEnd, int YValueEnd, int rotate, int height
            , int width, int time, Image animalImage, String musicSound)
    {
        ImageView animalView = new ImageView(animalImage);
        animalView.setRotate((double) -rotate);
        animalView.setFitWidth((double) width);
        animalView.setFitHeight((double) height);
        animalView.relocate((double) XValueStart, (double) YValueStart);
        if (musicSound != null)
        {
            animalView.setOnMouseClicked(event -> SoundPlayer.getInstance().play(musicSound));
        }
        KeyValue xvalue = new KeyValue(animalView.xProperty(), XValueEnd + MainView.OFFSET_X);
        KeyValue yvalue = new KeyValue(animalView.yProperty(), YValueEnd + MainView.OFFSET_Y);
        KeyValue kvRotate = new KeyValue(animalView.rotateProperty(), rotate);
        KeyFrame keyFrame = new KeyFrame(Duration.millis((double) time), xvalue, yvalue, kvRotate);
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
