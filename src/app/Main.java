package app;

import app.models.ControllerInterface;
import app.models.MainInterface;
import app.models.MatchListMgr;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application implements MainInterface {

    Stage window;

    @Override
    public void start(Stage primaryStage) throws Exception{
        window = primaryStage;
        resetScene("fxmls/welcome_page.fxml", "Tourney Manager", 750, 500);
        window.setResizable(false);
        window.show();
    }


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void showAddPlayersScene() throws Exception{
        resetScene("fxmls/addplayers_page.fxml", MatchListMgr.getCurrentMatch().getTitle(), 750, 700);
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
    }
}
