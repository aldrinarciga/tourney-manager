package app;

import app.models.Match;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Created by samsung on 1/25/2016.
 */
public class MatchMakerDialog {
    static Match newMatch;
    static TextField titleField;
    static Button btnProceed;
    static Match.MatchType type;
    static Stage stage;

    public static Match display(Match.MatchType matchType){
        newMatch = null;

        type = matchType;

        stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Enter Match Name");

        titleField = new TextField();
        titleField.setPromptText("Match Name");
        titleField.setFocusTraversable(false);
        titleField.setPrefSize(200,10);
        titleField.setFont(new Font(16));

        btnProceed = new Button();
        btnProceed.setText("Create Match");
        btnProceed.setOnAction(createMatch);
        btnProceed.setFont(new Font(16));

        VBox box = new VBox();
        box.setSpacing(10);
        box.getChildren().addAll(titleField, btnProceed);
        box.setAlignment(Pos.CENTER);
        Scene scene = new Scene(box, 400, 100);

        stage.setScene(scene);
        stage.showAndWait();

        return newMatch;
    }

    static EventHandler<ActionEvent> createMatch = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            String title = titleField.getText();
            String fileName = title.toLowerCase().replaceAll(" ", "-").concat(".json");

            newMatch = new Match(title, fileName, type, Match.Status.CREATED);
            stage.close();
        }
    };

}
