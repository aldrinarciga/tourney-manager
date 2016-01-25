package app.controllers;

import app.models.MainInterface;
import app.models.Match;
import app.models.MatchListMgr;
import app.models.Player;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.util.Callback;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Created by samsung on 1/25/2016.
 */
public class AddPlayersPageController implements Initializable{
    public Text txtTitle;
    public TextField txtFirstName;
    public TextField txtLastName;
    public CheckBox chRated;
    public Button btnAdd;
    public Button btnStartDraw;

    public TableView tblRatedPlayers;
    public TableView tblNonRatedPlayers;

    private MainInterface mainInterface;

    private Match currentMatch;

    private ArrayList<Player> players;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        currentMatch = MatchListMgr.getCurrentMatch();
        players = new ArrayList<>();

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

    private void resetTable(){
        tblRatedPlayers.setItems(getPlayersList(true));
        tblNonRatedPlayers.setItems(getPlayersList(false));
    }

    public void addPlayer(ActionEvent actionEvent) {
        String firstName = txtFirstName.getText();
        String lastName = txtLastName.getText();
        boolean isRated = chRated.isSelected();

        Player player = new Player(firstName, lastName, isRated);
        players.add(player);

        resetTable();

        txtFirstName.setText("");
        txtLastName.setText("");
        chRated.setSelected(false);
    }

    public void startDraw(ActionEvent actionEvent) {

    }

    public ObservableList<Player> getPlayersList(boolean isRated){
        System.out.println(isRated);
        ObservableList<Player> observableList = FXCollections.observableArrayList();
        for(Player player : players){
            if(player.isRated() == isRated) {
                observableList.add(player);
                System.out.println("Added:" + player.getFirstName());
            }
        }
        return observableList;
    }

    public void setMainInterface(MainInterface mainInterface) {
        this.mainInterface = mainInterface;
    }


}
