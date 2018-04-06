package app.dialogs;

import app.models.Match;
import app.models.PlayerAndTeamList;
import app.utils.OtherUtils;
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
public class MatchMakerDialog {
    static Match newMatch;
    static TextField titleField;
    static Button btnProceed;
    static CheckBox chIsDoubleElimination;
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

        chIsDoubleElimination = new CheckBox();
        chIsDoubleElimination.setText("Double Elimination");
        chIsDoubleElimination.setFont(new Font(16));

        VBox box = new VBox();
        box.setSpacing(10);
        box.getChildren().addAll(titleField, chIsDoubleElimination, btnProceed);
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

        return newMatch;
    }

    static EventHandler<ActionEvent> createMatch = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            String title = titleField.getText();
            String fileName = OtherUtils.getJsonFileName(title);

            boolean isChallongeMatch = YesNoDialog.display("Do you have an existing Challonge ID?");
            PlayerAndTeamList playerAndTeamList = null;
            if(isChallongeMatch) {
                playerAndTeamList = ChallongeMatchDialog.display(type == Match.MatchType.OPEN_DOUBLES || type == Match.MatchType.CLASSIFIED_DOUBLES);
            }

            newMatch = new Match(title, fileName, type, Match.Status.CREATED, chIsDoubleElimination.isSelected());
            if(playerAndTeamList != null) {
                newMatch.setStatus(Match.Status.STARTED);
                newMatch.setPlayers(playerAndTeamList.getPlayers());
                newMatch.setTeams(playerAndTeamList.getTeams());
            }
            stage.close();
        }
    };

}
