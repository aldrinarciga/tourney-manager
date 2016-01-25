package app.controllers;

import app.MatchMakerDialog;
import app.models.MainInterface;
import app.models.Match;
import app.models.MatchListMgr;
import app.models.OtherUtils;
import com.google.gson.Gson;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import java.net.URL;
import java.util.ResourceBundle;

public class WelcomePageController implements Initializable {

    public Button btnClassified;
    public Button btnDoubles;
    public ListView listMatches;

    private MainInterface mainInterface;

    protected ListProperty<String> listProperty = new SimpleListProperty<>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        listMatches.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        resetList();


    }

    public void createNewClassifiedDoubles(ActionEvent actionEvent) {
        createNewMatch(Match.MatchType.CLASSIFIED_DOUBLES);
    }

    public void createNewDoubles(ActionEvent actionEvent) {

    }


    private void resetList(){
        listMatches.itemsProperty().bind(listProperty);
        listProperty.set(FXCollections.observableArrayList(MatchListMgr.getMatchList()));
    }

    private void createNewMatch(Match.MatchType type){
        Match match = MatchMakerDialog.display(type);
        if(match != null){
            try {
                MatchListMgr.getMatches().add(match);
                MatchListMgr.saveMatches();

                MatchListMgr.setCurrentMatch(match);

                if(MatchListMgr.saveCurrentMatch()) {
                    mainInterface.showAddPlayersScene();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void setMainInterface(MainInterface mainInterface) {
        this.mainInterface = mainInterface;
    }
}
