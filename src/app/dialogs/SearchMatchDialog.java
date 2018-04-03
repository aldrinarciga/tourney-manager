package app.dialogs;

import app.dialogs.ErrorDialog;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;

/**
 * Created by samsung on 1/25/2016.
 */
public class SearchMatchDialog {
    static TextField txtQuery;
    static Button btnProceed;
    static Stage stage;
    static String searchQuery;

    public static String display(){

        stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Search Active Match");

        txtQuery = new TextField();
        txtQuery.setPromptText("Enter name, match or board number");
        txtQuery.setFocusTraversable(false);
        txtQuery.setPrefSize(200,10);
        txtQuery.setFont(new Font(16));

        btnProceed = new Button();
        btnProceed.setText("Search");
        btnProceed.setOnAction(createMatch);
        btnProceed.setFont(new Font(16));

        VBox box = new VBox();
        box.setSpacing(10);
        box.getChildren().addAll(txtQuery, btnProceed);
        box.setAlignment(Pos.CENTER);
        Scene scene = new Scene(box, 400, 130);

        stage.setScene(scene);
        stage.setResizable(false);

        double width = 400;
        double height = 130;

        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        stage.setX((screenBounds.getWidth() - width) / 2);
        stage.setY((screenBounds.getHeight() - height) / 2);

        stage.showAndWait();

        return searchQuery;
    }

    static EventHandler<ActionEvent> createMatch = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            try {
                searchQuery = txtQuery.getText();
                stage.close();
            } catch (Exception ex) {
                ErrorDialog.display("Please make sure that the details are correct");
            }
        }
    };

}
