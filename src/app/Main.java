package app;

import app.controllers.AddPlayersPageController;
import app.controllers.WelcomePageController;
import app.models.MainInterface;
import app.models.MatchListMgr;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application implements MainInterface {

    Stage window;

    @Override
    public void start(Stage primaryStage) throws Exception{
        window = primaryStage;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("fxmls/welcome_page.fxml"));
        Parent root = loader.load();

        WelcomePageController controller = loader.getController();
        controller.setMainInterface(this);

        window.setTitle("Tourney Manager");
        Scene scene = new Scene(root, 750, 500);
        scene.getStylesheets().addAll(this.getClass().getResource("style.css").toExternalForm());
        window.setScene(scene);

        window.show();
    }


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void showAddPlayersScene() throws Exception{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("fxmls/addplayers_page.fxml"));
        Parent root = loader.load();

        AddPlayersPageController controller = loader.getController();
        controller.setMainInterface(this);

        window.setTitle(MatchListMgr.getCurrentMatch().getTitle());
        Scene scene = new Scene(root, 750, 700);
        scene.getStylesheets().addAll(this.getClass().getResource("style.css").toExternalForm());
        window.setScene(scene);
    }
}
