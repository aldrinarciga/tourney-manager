package app.dialogs;

import app.models.Board;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;
import org.controlsfx.control.CheckComboBox;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * Created by samsung on 1/25/2016.
 */
public class PrintSelectionDialog {
    static CheckComboBox<String> checkComboBox;
    static Button btnProceed;
    static Button selectAll;
    static Button unselectAll;
    static Stage stage;
    static List<Integer> boardIds;
    static ObservableList<String> strings;

    public static List<Integer> display(List<Board> activeBoards) {
        boardIds = new ArrayList<>();
        stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Print multiple matches");

        strings = FXCollections.observableArrayList();
        for(Board board : activeBoards) {
            strings.add("Board " + board.getBoardNumber() + " / Match" + board.getMatchNumber());
        }

        checkComboBox = new CheckComboBox<>(strings);
        checkComboBox.setPrefWidth(200);
        checkComboBox.getCheckModel().getCheckedItems();


        btnProceed = new Button();
        btnProceed.setText("Print");
        btnProceed.setOnAction(createMatch);
        btnProceed.setFont(new Font(16));

        int btnTextSize = 12;
        selectAll = new Button();
        selectAll.setText("Check All");
        selectAll.setOnAction(getSelectionClick(true));
        selectAll.setFont(new Font(btnTextSize));

        unselectAll = new Button();
        unselectAll.setText("Uncheck All");
        unselectAll.setOnAction(getSelectionClick(false));
        unselectAll.setFont(new Font(btnTextSize));

        HBox hBox = new HBox();
        hBox.setSpacing(5);
        hBox.setAlignment(Pos.CENTER);
        hBox.getChildren().addAll(selectAll, unselectAll);

        VBox box = new VBox();
        box.setSpacing(10);
        box.getChildren().addAll(hBox, checkComboBox, btnProceed);
        box.setAlignment(Pos.CENTER);
        Scene scene = new Scene(box, 200, 130);

        stage.setScene(scene);
        stage.setResizable(false);

        double width = 200;
        double height = 130;

        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        stage.setX((screenBounds.getWidth() - width) / 2);
        stage.setY((screenBounds.getHeight() - height) / 2);

        stage.showAndWait();

        return boardIds;
    }

    static EventHandler<ActionEvent> createMatch = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            try {
                if(checkComboBox.getCheckModel().getCheckedItems().size() > 8) {
                    ErrorDialog.display("Maximum of 8 selections only");
                    return;
                }
                checkComboBox.getCheckModel().getCheckedItems().forEach(s -> {
                    String boardIdString = s.split("/")[0].trim();
                    boardIdString = boardIdString.split(" ")[1];
                    boardIds.add(Integer.parseInt(boardIdString));
                });
                stage.close();
            } catch (Exception ex) {
                ErrorDialog.display("Please make sure that the details are correct");
            }
        }
    };

    static EventHandler<ActionEvent> getSelectionClick(boolean isChecked) {
        return event -> {
            if(isChecked) {
                checkComboBox.getCheckModel().checkAll();
            } else {
                checkComboBox.getCheckModel().clearChecks();
            }
        };
    }

}
