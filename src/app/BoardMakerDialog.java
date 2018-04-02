package app;

import app.models.Board;
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
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;

/**
 * Created by samsung on 1/25/2016.
 */
public class BoardMakerDialog {
    static Board board;

    static TextField txtMatchNumber;
    static TextField txtPlayerOne;
    static TextField txtPlayerTwo;
    static Button btnProceed;
    static Stage stage;

    public static Board display(int boardNumber){
        board = new Board(boardNumber);

        stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Enter Board Details");

        txtMatchNumber = new TextField();
        txtMatchNumber.setPromptText("Match Number");
        txtMatchNumber.setFocusTraversable(false);
        txtMatchNumber.setPrefSize(50,10);
        txtMatchNumber.setFont(new Font(16));

        txtPlayerOne = new TextField();
        txtPlayerOne.setPromptText("Player 1 Seed");
        txtPlayerOne.setFocusTraversable(false);
        txtPlayerOne.setPrefSize(50,10);
        txtPlayerOne.setFont(new Font(16));

        txtPlayerTwo = new TextField();
        txtPlayerTwo.setPromptText("Player 2 Seed");
        txtPlayerTwo.setFocusTraversable(false);
        txtPlayerTwo.setPrefSize(50,10);
        txtPlayerTwo.setFont(new Font(16));

        btnProceed = new Button();
        btnProceed.setText("Create Match");
        btnProceed.setOnAction(createBoard);
        btnProceed.setFont(new Font(16));

        VBox box = new VBox();
        box.setSpacing(10);
        box.getChildren().addAll(txtMatchNumber, txtPlayerOne, txtPlayerTwo, btnProceed);
        box.setAlignment(Pos.CENTER);
        double width = 220;
        double height = 180;
        Scene scene = new Scene(box, width, height);

        stage.setScene(scene);
        stage.setResizable(false);



        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        stage.setX((screenBounds.getWidth() - width) / 2);
        stage.setY((screenBounds.getHeight() - height) / 2);

        stage.showAndWait();

        return board;
    }

    static EventHandler<ActionEvent> createBoard = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            try {
                int matchNumber = Integer.parseInt(txtMatchNumber.getText());
                int player1 = Integer.parseInt(txtMatchNumber.getText());
                int player2 = Integer.parseInt(txtMatchNumber.getText());
                board.setMatchNumber(matchNumber);
                board.setPlayerOne(player1);
                board.setPlayerTwo(player2);
                stage.close();
            } catch (Exception ex) {
                ErrorDialog.display("Please make sure that the details are correct");
            }
        }
    };

}
