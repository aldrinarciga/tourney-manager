package app.dialogs;

import app.utils.OtherUtils;
import app.models.Player;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by samsung on 1/25/2016.
 */
public class BulkAddDialog {

    static List<Player> players;

    static TextArea txtBulkAdd;
    static Button btnProceed;
    static Stage stage;

    public static List<Player> display(){
        players = new ArrayList<>();

        stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Enter Board Details");

        txtBulkAdd = new TextArea();
        txtBulkAdd.setPromptText("Players");
        txtBulkAdd.setFocusTraversable(false);
        txtBulkAdd.setPrefSize(400,450);
        txtBulkAdd.setFont(new Font(16));

        btnProceed = new Button();
        btnProceed.setText("Add players");
        btnProceed.setOnAction(createBoard);
        btnProceed.setFont(new Font(16));

        VBox box = new VBox();
        box.setSpacing(10);
        box.getChildren().addAll(txtBulkAdd, btnProceed);
        box.setAlignment(Pos.CENTER);
        double width = 420;
        double height = 500;
        Scene scene = new Scene(box, width, height);

        stage.setScene(scene);
        stage.setResizable(false);

        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        stage.setX((screenBounds.getWidth() - width) / 2);
        stage.setY((screenBounds.getHeight() - height) / 2);

        stage.showAndWait();

        return players;
    }

    static EventHandler<ActionEvent> createBoard = event -> {
        String bulk = txtBulkAdd.getText();
        String[] toAdd = bulk.split("\n");
        for(String name: toAdd) {
            Player player = OtherUtils.parseNameToPlayer(name);
            if(player != null) {
                players.add(player);
            }
        }

        stage.close();
    };

}
