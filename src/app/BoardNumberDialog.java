package app;

import app.models.ErrorDialog;
import app.models.Match;
import app.models.OtherUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.input.InputMethodRequests;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;

/**
 * Created by samsung on 1/25/2016.
 */
public class BoardNumberDialog {
    static TextField boardNumberField;
    static Button btnProceed;
    static Stage stage;
    static int numberOfBoards = 0;

    public static int display(){

        stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Enter number of boards");

        boardNumberField = new TextField();
        boardNumberField.setPromptText("Number of boards");
        boardNumberField.setFocusTraversable(false);
        boardNumberField.setPrefSize(200,10);
        boardNumberField.setFont(new Font(16));

        btnProceed = new Button();
        btnProceed.setText("Proceed");
        btnProceed.setOnAction(createMatch);
        btnProceed.setFont(new Font(16));

        VBox box = new VBox();
        box.setSpacing(10);
        box.getChildren().addAll(boardNumberField, btnProceed);
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

        return numberOfBoards;
    }

    static EventHandler<ActionEvent> createMatch = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            try {
                numberOfBoards = Integer.parseInt(boardNumberField.getText());
                stage.close();
            } catch (Exception ex) {
                ErrorDialog.display("Please make sure that the details are correct");
            }
        }
    };

}
