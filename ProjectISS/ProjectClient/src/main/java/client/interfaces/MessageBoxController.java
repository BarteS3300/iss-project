package client.interfaces;

import common.domain.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class MessageBoxController {

    @FXML
    private Label messageLabel;

    public void setMessage(String message){
        messageLabel.setText(message);
    }

    public static void show(String message){
        try {
            Stage stage = new Stage();

            FXMLLoader loader = new FXMLLoader(MessageBoxController.class.getResource("/MessageBox.fxml"));
            Parent root = loader.load();

            MessageBoxController controller = loader.getController();

            stage.close();
            controller.setMessage(message);

            Scene scene =new Scene(root, root.prefWidth(1), root.prefHeight(1));
            stage.setTitle("Message!");
            stage.setScene(scene);
            stage.show();


        } catch (Exception e){
            System.out.println("Error while logging in");
            e.printStackTrace();
        }
    }
}
