package view.utility;


import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import view.MainView;
import view.utility.constants.PictureAddresses;


public class MenuUtility extends Pane {

     public static ImageView background = new ImageView(Utility.getImage(PictureAddresses.GRAPHICS_ROOT + "game_menu_1.png"));


    public MenuUtility(String title){
//         relocate(MainView.WIDTH * 0.17, MainView.HEIGHT * 0.15);
        background.relocate(MainView.WIDTH * 0.1, MainView.HEIGHT * 0.1);
        background.setFitHeight(MainView.HEIGHT * 0.25);
        background.setFitWidth(MainView.WIDTH * 0.5);
        Text text = new Text(title);
        text.setFont(Font.font("SWItalt", 20));
        text.setFill(Color.RED);
        text.relocate(MainView.WIDTH * 0.27, MainView.WIDTH * 0.05);
        this.getChildren().add(0, background);
        this.getChildren().addAll(text);
    }
    public MenuUtility(String title, int i){
//         relocate(MainView.WIDTH * 0.17, MainView.HEIGHT * 0.15);
        background.relocate(MainView.WIDTH * 0.1, MainView.HEIGHT * 0.1);
        background.setFitHeight(MainView.HEIGHT * 0.8);
        background.setFitWidth(MainView.WIDTH * 0.5);
        Text text = new Text(title);
        text.setFont(Font.font("SWItalt", 20));
        text.setFill(Color.RED);
        text.relocate(MainView.WIDTH * 0.27, MainView.WIDTH * 0.05);
        this.getChildren().addAll(background, text);
    }
}
