package view.utility;

import javafx.scene.image.Image;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Utility
{
    public static Image getImage(String path) {
        Image image = null;
        try {
            image = new Image(new FileInputStream(path));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return image;
    }

    public static String getSound(String path) {

        String media = new File(path).toURI().toString();
        return media;
    }

}
