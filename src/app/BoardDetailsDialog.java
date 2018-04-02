package app;

import app.models.Board;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;

/**
 * Created by samsung on 1/25/2016.
 */
public class BoardDetailsDialog {

    static Label lblPlayerOne;
    static Label lblPlayerTwo;
    static Label lblMatchInfo;
    static Button btnFinish;
    static Button btnPrint;
    static Button btnClose;
    static Stage stage;
    static BoardResult result;

    public enum BoardAction {
        NONE, CLOSE, PRINT
    }

    public static class BoardResult {
        public int boardNumber;
        public BoardAction boardAction;

        public BoardResult(int boardNumber, BoardAction boardAction) {
            this.boardNumber = boardNumber;
            this.boardAction = boardAction;
        }
    }

    public static BoardResult display(Board board, String playerOne, String playerTwo){
        result = new BoardResult(board.getBoardNumber(), BoardAction.NONE);

        stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Board Details");

        lblMatchInfo = new Label();
        lblMatchInfo.setText("Board " + board.getBoardNumber() + " - Match " + board.getMatchNumber());
        lblMatchInfo.setTextAlignment(TextAlignment.CENTER);
        lblMatchInfo.setFont(new Font(24));

        lblPlayerOne = new Label();
        lblPlayerOne.setText(playerOne);
        lblPlayerOne.setTextAlignment(TextAlignment.CENTER);
        lblPlayerOne.setFont(new Font(16));

        lblPlayerTwo = new Label();
        lblPlayerTwo.setText(playerTwo);
        lblPlayerTwo.setTextAlignment(TextAlignment.CENTER);
        lblPlayerTwo.setFont(new Font(16));

        Label lblVS = new Label();
        lblVS.setText("VS.");
        lblVS.setTextAlignment(TextAlignment.CENTER);
        lblVS.setFont(new Font(26));

        btnFinish = new Button();
        btnFinish.setText("Finish Match");
        btnFinish.setOnAction(getBoardAction(BoardAction.CLOSE));
        btnFinish.setFont(new Font(16));

        btnPrint = new Button();
        btnPrint.setText("Print Match");
        btnPrint.setOnAction(getBoardAction(BoardAction.PRINT));
        btnPrint.setFont(new Font(16));

        btnClose = new Button();
        btnClose.setText("Done");
        btnClose.setOnAction(getBoardAction(BoardAction.NONE));
        btnClose.setFont(new Font(16));

        HBox hBox = new HBox();
        hBox.setSpacing(10);
        hBox.setAlignment(Pos.CENTER);
        hBox.getChildren().addAll(btnPrint, btnFinish, btnClose);

        VBox box = new VBox();
        box.setSpacing(10);
        box.getChildren().addAll(lblMatchInfo, lblPlayerOne, lblVS, lblPlayerTwo, hBox);
        box.setAlignment(Pos.CENTER);
        double width = 400;
        double height = 200;
        Scene scene = new Scene(box, width, height);

        stage.setScene(scene);
        stage.setResizable(false);



        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        stage.setX((screenBounds.getWidth() - width) / 2);
        stage.setY((screenBounds.getHeight() - height) / 2);

        stage.showAndWait();

        return result;
    }

    static EventHandler<ActionEvent> getBoardAction(BoardAction boardAction) {
        return event -> {
            result.boardAction = boardAction;
            stage.close();
        };
    }

}
