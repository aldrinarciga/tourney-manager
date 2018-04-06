package app.dialogs;

import app.models.ChallongeParticipant;
import app.models.GetParticipantsCallback;
import app.models.Match;
import app.models.PlayerAndTeamList;
import app.networking.NetworkingUtil;
import app.utils.OtherUtils;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;

/**
 * Created by samsung on 1/25/2016.
 */
public class ChallongeMatchDialog {
    static PlayerAndTeamList playerAndTeamList;
    static TextField txtChallongeId;
    static TextField txtSplitter;
    static Label lblProcess;
    static Button btnProceed;
    static Stage stage;
    static boolean isDoubles;

    public static PlayerAndTeamList display(boolean isDoubles){
        playerAndTeamList = null;
        ChallongeMatchDialog.isDoubles = isDoubles;

        stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Enter Challonge ID");

        txtChallongeId = new TextField();
        txtChallongeId.setPromptText("Challonge tournament id");
        txtChallongeId.setFocusTraversable(false);
        txtChallongeId.setPrefSize(200,10);
        txtChallongeId.setFont(new Font(16));

        txtSplitter = new TextField();
        txtSplitter.setPromptText("Team Splitter");
        txtSplitter.setFocusTraversable(false);
        txtSplitter.setPrefSize(200,10);
        txtSplitter.setFont(new Font(16));
        txtSplitter.setText("/");
        txtSplitter.setVisible(isDoubles);

        btnProceed = new Button();
        btnProceed.setText("Process");
        btnProceed.setOnAction(createMatch);
        btnProceed.setFont(new Font(16));

        lblProcess = new Label();
        lblProcess.setFont(new Font(10));
        lblProcess.setVisible(false);

        VBox box = new VBox();
        box.setSpacing(10);
        box.getChildren().addAll(txtChallongeId, txtSplitter, lblProcess, btnProceed);
        box.setAlignment(Pos.CENTER);
        Scene scene = new Scene(box, 400, 160);

        stage.setScene(scene);
        stage.setResizable(false);

        double width = 400;
        double height = 160;

        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        stage.setX((screenBounds.getWidth() - width) / 2);
        stage.setY((screenBounds.getHeight() - height) / 2);

        stage.showAndWait();

        return playerAndTeamList;
    }

    static EventHandler<ActionEvent> createMatch = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            String challongeId = txtChallongeId.getText();
            showMessage("Processing tournament...");
            NetworkingUtil.getPlayerAndTeamList(challongeId, isDoubles, txtSplitter.getText(), new GetParticipantsCallback() {
                @Override
                public void onSuccess(PlayerAndTeamList playerAndTeamList) {
                    Platform.runLater(() -> {
                        ChallongeMatchDialog.playerAndTeamList = playerAndTeamList;
                        stage.close();
                    });
                }

                @Override
                public void onFailure(String err) {
                    showMessage(err);
                }
            });
        }
    };

    private static void showMessage(String err) {
        Platform.runLater(() -> {
            lblProcess.setText(err);
            lblProcess.setVisible(true);
        });

    }

}
