package view.utility;


import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import view.MainView;
import view.utility.constants.PictureAddresses;


public class MenuUtility extends Pane {

     private final ImageView background = new ImageView(Utility.getImage(PictureAddresses.MENU));

     public MenuUtility(String title){
         background.relocate(MainView.WIDTH * 0.1, MainView.HEIGHT * 0.1);
         background.setFitHeight(MainView.HEIGHT * 0.25);
         background.setFitWidth(MainView.WIDTH * 0.5);
         Text text = new Text(title);
         text.setFont(Font.font("SWItalt", 20));
         text.setFill(Color.RED);
         text.relocate(MainView.WIDTH * 0.27, MainView.WIDTH * 0.05);
     }
}
