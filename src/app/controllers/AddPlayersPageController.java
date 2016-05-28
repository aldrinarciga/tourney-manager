package app.controllers;

import app.YesNoDialog;
import app.models.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Created by samsung on 1/25/2016.
 */
public class AddPlayersPageController implements Initializable, ControllerInterface{
    public Text txtTitle;
    public TextField txtFirstName;
    public TextField txtLastName;
    public CheckBox chRated;
    public Button btnAdd;
    public Button btnStartDraw;

    public TableView tblRatedPlayers;
    public TableView tblNonRatedPlayers;
    public Text txtStatus;

    private MainInterface mainInterface;

    private Match currentMatch;

    private ArrayList<Player> players;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        players = new ArrayList<>();

        currentMatch = MatchListMgr.getCurrentMatch();
        if(currentMatch.getPlayers() != null){
            players = currentMatch.getPlayers();
        }

        setupTable();
    }

    private void setupTable() {

        TableColumn<Player, String> firstNameCol = new TableColumn<>("First Name");
        firstNameCol.setMinWidth(200);
        firstNameCol.setCellValueFactory(new PropertyValueFactory<Player, String>("firstName"));
        firstNameCol.getStyleClass().add("italic");

        TableColumn<Player, String> lastNameCol = new TableColumn<>("Last Name");
        lastNameCol.setMinWidth(200);
        lastNameCol.setCellValueFactory(new PropertyValueFactory<Player, String>("lastName"));
        lastNameCol.getStyleClass().add("italic");

        tblRatedPlayers.getColumns().addAll(firstNameCol, lastNameCol);

        //NON RATED

        TableColumn<Player, String> firstNameCol2 = new TableColumn<>("First Name");
        firstNameCol2.setMinWidth(200);
        firstNameCol2.setCellValueFactory(new PropertyValueFactory<Player, String>("firstName"));
        firstNameCol2.getStyleClass().add("bold");

        TableColumn<Player, String> lastNameCol2 = new TableColumn<>("Last Name");
        lastNameCol2.setMinWidth(200);
        lastNameCol2.setCellValueFactory(new PropertyValueFactory<Player, String>("lastName"));
        lastNameCol2.getStyleClass().add("bold");

        tblNonRatedPlayers.getColumns().addAll(firstNameCol2, lastNameCol2);

        resetTable();
    }

    public void addPlayer(ActionEvent actionEvent) {
        String firstName = txtFirstName.getText();
        String lastName = txtLastName.getText();
        boolean isRated = chRated.isSelected();

        Player player = new Player(firstName, lastName, isRated);
        players.add(player);

        if(isRated){
            tblRatedPlayers.getItems().add(player);
        }else{
            tblNonRatedPlayers.getItems().add(player);
        }

        savePlayers();

        //resetTable();

        txtFirstName.setText("");
        txtLastName.setText("");
        chRated.setSelected(false);
        txtFirstName.requestFocus();
        txtStatus.setText("");
    }

    public void deleteFromRated(Event event) {
        removePlayer(true);
    }

    public void deleteFromNonRated(Event event) {
        removePlayer(false);
    }

    public void startDraw(ActionEvent actionEvent) throws Exception {
        if(YesNoDialog.display("Do you want to draw this match?")) {
            currentMatch = MatchListMgr.getCurrentMatch();
            boolean cont = false;
            switch (currentMatch.getMatchType()) {
                case OPEN_DOUBLES:
                case CLASSIFIED_DOUBLES:
                    if (currentMatch.getPlayers() == null || currentMatch.getPlayers().size() % 2 == 1 || currentMatch.getPlayers().size() < 4) {
                        txtStatus.setText("Insufficient Players/Players not even");
                    } else {
                        cont = true;
                    }
                    break;
                case SINGLES:
                    if (currentMatch.getPlayers() == null || currentMatch.getPlayers().size() < 2) {
                        txtStatus.setText("Insufficient Players/Players not even");
                    } else {
                        cont = true;
                    }
                    break;
                default:
                    break;
            }

            if (cont) {
                mainInterface.showDrawScene();
            }
        }
    }

    private void savePlayers() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                MatchListMgr.getCurrentMatch().setPlayers(players);
                MatchListMgr.saveCurrentMatch();
            }
        });
        thread.start();
    }

    private void resetTable(){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                tblRatedPlayers.setItems(getPlayersList(true));
                tblNonRatedPlayers.setItems(getPlayersList(false));
            }
        });
        thread.start();
    }

    private void removePlayer(boolean isRated){
        Player player = isRated ? (Player) tblRatedPlayers.getSelectionModel().getSelectedItem() :
                (Player) tblNonRatedPlayers.getSelectionModel().getSelectedItem();
        if(YesNoDialog.display("Do you want to delete " + player.getFirstName() + " " + player.getLastName() + "?")) {
            players.remove(player);
            /*resetTable();*/
            if(isRated){
                tblRatedPlayers.getItems().remove(player);
            }else{
                tblNonRatedPlayers.getItems().remove(player);
            }
            btnAdd.requestFocus();
            savePlayers();
        }
    }

    public ObservableList<Player> getPlayersList(boolean isRated){
        //System.out.println(isRated);
        ObservableList<Player> observableList = FXCollections.observableArrayList();
        for(Player player : players){
            if(player.isRated() == isRated) {
                observableList.add(player);
                //System.out.println("Added:" + player.getFirstName());
            }
        }
        return observableList;
    }

    @Override
    public void setInterface(MainInterface mainInterface) {
        this.mainInterface = mainInterface;
    }


    public void generate(ActionEvent actionEvent) {
        for(int x = 1; x <= 80; x++){
            txtFirstName.setText(x + "");
            chRated.setSelected(x <= 20);
            addPlayer(null);
        }
    }
}
