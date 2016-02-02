package app;

import app.models.ControllerInterface;
import app.models.MainInterface;
import app.models.MatchListMgr;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class Main extends Application implements MainInterface {

    Stage window;

    @Override
    public void start(Stage primaryStage) throws Exception{
        window = primaryStage;
        resetScene("fxmls/welcome_page.fxml", "Tourney Manager", 750, 500);
        //window.setResizable(false);

        double width = 750;
        double height = 500;

        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        window.setX((screenBounds.getWidth() - width) / 2);
        window.setY((screenBounds.getHeight() - height) / 2);

        window.show();
    }


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void showAddPlayersScene() throws Exception{
        resetScene("fxmls/addplayers_page.fxml", MatchListMgr.getCurrentMatch().getTitle(), 750, 700);
    }

    @Override
    public void showDrawScene() throws Exception{
        resetScene("fxmls/draw_page.fxml", MatchListMgr.getCurrentMatch().getTitle(), 750, 900);
    }

    private void resetScene(String resource, String title, double width, double height)throws Exception{
        FXMLLoader loader = new FXMLLoader(getClass().getResource(resource));
        Parent root = loader.load();

        Initializable controller = loader.getController();
        if(controller instanceof ControllerInterface){
            ((ControllerInterface)controller).setInterface(this);
        }

        window.setTitle(title);
        Scene scene = new Scene(root, width, height);
        scene.getStylesheets().addAll(this.getClass().getResource("style.css").toExternalForm());
        window.setScene(scene);

        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        window.setX((screenBounds.getWidth() - window.getWidth()) / 2);
        window.setY((screenBounds.getHeight() - window.getHeight()) / 2);
    }
}
