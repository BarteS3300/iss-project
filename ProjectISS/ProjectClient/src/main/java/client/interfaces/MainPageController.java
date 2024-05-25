package client.interfaces;

import common.business.IObserver;
import common.business.IService;
import common.business.ProjectException;
import common.domain.User;
import common.utils.Observer;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class MainPageController implements Initializable, IObserver {

    private IService service;

    private User user;

    @FXML
    private Button logOutButton;

    @FXML
    private Label messageLabel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    @Override
    public void update() {
    }

    public void setController(IService service, User user) {
        this.service = service;
        this.user = user;
        messageLabel.setText("Welcome, " + user.toString() + "!");
    }

    @FXML
    void LogOut(ActionEvent event) {
        try {
            service.logOutUser(user, this);
            Stage stage = (Stage) logOutButton.getScene().getWindow();
            stage.close();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/LogIn.fxml"));
            Parent root = loader.load();

            LogInController controller = loader.getController();
            controller.setService(service);

            Scene scene = new Scene(root);
            stage.setTitle("Log In");
            stage.setScene(scene);
            stage.show();
        }catch (ProjectException e) {
            e.printStackTrace();
        } catch (Exception e){
            System.out.println("Error while logging out");
        }
    }
}
