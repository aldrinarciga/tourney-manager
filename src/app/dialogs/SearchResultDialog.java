package app.dialogs;

import app.models.Board;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by samsung on 1/26/2016.
 */
public class SearchResultDialog {
    static ComboBox<String> comboBox;
    static Button btnOk;
    static Stage stage;
    static int boardId;
    static List<Board> mResults;

    public static int display(List<Board> results, HashMap<Integer, String> playersMap){
        mResults = new ArrayList<>(results);
        boardId = 0;
        stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Multiple Results");

        ObservableList<String> resultStrings = FXCollections.observableArrayList();
        for(Board board : results) {
            StringBuilder builder = new StringBuilder("");
            builder.append("- B" + board.getBoardNumber() + "/M" + board.getMatchNumber() + ": ");
            builder.append(playersMap.get(board.getPlayerOne()) + " vs. " + playersMap.get(board.getPlayerTwo()));
            resultStrings.add(builder.toString());
        }

        comboBox = new ComboBox<>(resultStrings);

        btnOk = new Button();
        btnOk.setText("View");
        btnOk.setFont(new Font(16));
        btnOk.setOnAction(event -> returnResult());

        VBox box = new VBox();
        box.setSpacing(10);
        box.setPadding(new Insets(10, 10, 10 ,10));
        box.getChildren().addAll(comboBox, btnOk);
        box.setAlignment(Pos.CENTER);

        double width = 400;
        double height = 150;

        Scene scene = new Scene(box, width, height);

        stage.setScene(scene);
        stage.setResizable(false);

        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        stage.setX((screenBounds.getWidth() - width) / 2);
        stage.setY((screenBounds.getHeight() - height) / 2);

        stage.showAndWait();
        return boardId;
    }

    private static void returnResult(){
        int idx = comboBox.getSelectionModel().getSelectedIndex();
        if(idx >= 0 && idx < mResults.size()) {
            Board board = mResults.get(idx);
            if (board != null) {
                boardId = board.getBoardNumber();
            }
        }
        stage.close();
    }
}
