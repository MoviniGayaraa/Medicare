package lk.ijse.pharmacy.util;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

import java.util.Optional;

public class AlertController {
    public static void errormessage(String msg){
        Alert alert= new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(msg);

        alert.getDialogPane().setPrefSize(300, 150); // Set the size of the alert dialog pane
//            alert.getDialogPane().setMinSize(300, 150); // Set the minimum size of the alert dialog pane
//            alert.getDialogPane().setMaxSize(300, 150); // Set the maximum size of the alert dialog pane
        alert.getDialogPane().setStyle("-fx-background-color: #ffffff;"); // Set the background color of the alert dialog pane
        alert.getDialogPane().setHeaderText(null); // Remove the header text from the alert dialog pane

        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image("img/wrongicon.png"));
        ButtonType cancelButton = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        alert.getButtonTypes().setAll(cancelButton);

        alert.showAndWait();
    }

    public static void confirmmessage(String msg){
        Alert alert= new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText(null);
        alert.setContentText(msg);

        alert.getDialogPane().setPrefSize(300, 150); // Set the size of the alert dialog pane
//            alert.getDialogPane().setMinSize(300, 150); // Set the minimum size of the alert dialog pane
//            alert.getDialogPane().setMaxSize(300, 150); // Set the maximum size of the alert dialog pane
        alert.getDialogPane().setStyle("-fx-background-color: #ffffff;"); // Set the background color of the alert dialog pane
        alert.getDialogPane().setHeaderText(null); // Remove the header text from the alert dialog pane

        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image("img/tick.gif"));
        ButtonType cancelButton = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        alert.getButtonTypes().setAll(cancelButton);

        alert.showAndWait();
    }

    public static boolean okconfirmmessage(String msg){
        Alert alert= new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText(null);
        alert.setContentText(msg);

        alert.getDialogPane().setPrefSize(300, 150); // Set the size of the alert dialog pane
//            alert.getDialogPane().setMinSize(300, 150); // Set the minimum size of the alert dialog pane
//            alert.getDialogPane().setMaxSize(300, 150); // Set the maximum size of the alert dialog pane
        alert.getDialogPane().setStyle("-fx-background-color: #ffffff;"); // Set the background color of the alert dialog pane
        alert.getDialogPane().setHeaderText(null); // Remove the header text from the alert dialog pane

        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image("img/tick.gif"));
        ButtonType okButton = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
        ButtonType cancelButton = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        alert.getButtonTypes().setAll(okButton,cancelButton);

        Optional<ButtonType> result = alert.showAndWait();
        if(result.orElse(cancelButton) == okButton){
            return true;
        }
        return false;
    }

    public static void animationMesseage(String image, String title, String text) {
        Image img = new Image(String.valueOf(image), 96, 96, false, false);
        Notifications notificationBuilder = Notifications.create()
                .title(title)
                .text(text)
                .graphic(new ImageView(img))
                .hideAfter(Duration.seconds(3));
        notificationBuilder.darkStyle();
        notificationBuilder.show();

    }

}
