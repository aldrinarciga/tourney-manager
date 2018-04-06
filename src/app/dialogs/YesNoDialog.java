package app.dialogs;

import app.models.Match;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;

/**
 * Created by samsung on 1/26/2016.
 */
public class YesNoDialog {
    static boolean result;
    static Text txtContent;
    static Button btnYes;
    static Button btnNo;
    static Stage stage;

    public static boolean display(String content){
        return display(content, "Yes", "No");
    }

    public static boolean display(String content, String firstButton, String seconButton) {
        result = false;


        stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Confirm Action");

        txtContent = new Text();
        txtContent.setText(content);
        txtContent.setFont(new Font(16));

        btnYes = new Button();
        btnYes.setText(firstButton);
        btnYes.setFont(new Font(16));
        btnYes.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                returnResult(true);
            }
        });

        btnNo = new Button();
        btnNo.setText(seconButton);
        btnNo.setFont(new Font(16));
        btnNo.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                returnResult(false);
            }
        });

        HBox hbox = new HBox();
        hbox.setSpacing(10);
        hbox.getChildren().addAll(btnYes, btnNo);
        hbox.setAlignment(Pos.CENTER);

        VBox box = new VBox();
        box.setSpacing(10);
        box.getChildren().addAll(txtContent, hbox);
        box.setAlignment(Pos.CENTER);

        double width = 350;
        double height = 110;

        Scene scene = new Scene(box, width, height);

        stage.setScene(scene);
        stage.setResizable(false);



        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        stage.setX((screenBounds.getWidth() - width) / 2);
        stage.setY((screenBounds.getHeight() - height) / 2);

        stage.showAndWait();

        return result;
    }

    private static void returnResult(boolean res){
        result = res;
        stage.close();
    }
}
