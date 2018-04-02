package app;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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
public class ErrorDialog {
    static Text txtContent;
    static Button btnOk;
    static Stage stage;

    public static void display(String content){


        stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Notice");

        txtContent = new Text();
        txtContent.setText(content);
        txtContent.setFont(new Font(16));

        btnOk = new Button();
        btnOk.setText("Okay");
        btnOk.setFont(new Font(16));
        btnOk.setOnAction(event -> returnResult());

        VBox box = new VBox();
        box.setSpacing(10);
        box.getChildren().addAll(txtContent, btnOk);
        box.setAlignment(Pos.CENTER);

        double width = 350;
        double height = 110;

        Scene scene = new Scene(box, width, height);

        stage.setScene(scene);
        stage.setResizable(false);

        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        stage.setX((screenBounds.getWidth() - width) / 2);
        stage.setY((screenBounds.getHeight() - height) / 2);

        stage.show();
    }

    private static void returnResult(){
        stage.close();
    }
}
