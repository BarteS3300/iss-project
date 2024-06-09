package client.interfaces;

import common.business.IObserver;
import common.business.IService;
import common.business.ProjectException;
import common.domain.Item;
import common.domain.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class AdminMainPageController{

    private IService service;

    private User user;

    @FXML
    private Button addButton;

    @FXML
    private Button deleteButton;

    @FXML
    private Button logOutButton;

    @FXML
    private Button updateButton;

    @FXML
    private Label usernameLabel;

    public void setController(IService service, User user) {
        this.service = service;
        this.user = user;
        usernameLabel.setText("Welcome, " + user.getUsername() + "!");
    }

    @FXML
    void AddButton(ActionEvent event) {
        try {
            Stage stage = new Stage();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AddItem.fxml"));
            Parent root = loader.load();
            AddItemController controller = loader.getController();
            controller.setController(service, user);

            Scene scene = new Scene(root);
            stage.setTitle("Add item");
            stage.setScene(scene);
            stage.show();
        } catch (Exception e){
            System.out.println("Error while logging out");
        }
    }

    @FXML
    void DeleteButton(ActionEvent event) {
        try {
            Stage stage = new Stage();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/DeleteItem.fxml"));
            Parent root = loader.load();
            DeleteItemController controller = loader.getController();
            controller.setController(service, user);

            Scene scene = new Scene(root);
            stage.setTitle("Add item");
            stage.setScene(scene);
            stage.show();
        } catch (Exception e){
            System.out.println("Error while logging out");
        }
    }

    @FXML
    void LogOut(ActionEvent event) {
        try {
            Stage stage = (Stage) logOutButton.getScene().getWindow();
            stage.close();
        } catch (Exception e){
            System.out.println("Error while logging out");
        }
    }

    @FXML
    void UpdateButton(ActionEvent event) {
        try {
            Stage stage = new Stage();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/UpdateList.fxml"));
            Parent root = loader.load();
            UpdateListController controller = loader.getController();
            controller.setController(service, user);

            Scene scene = new Scene(root);
            stage.setTitle("Add item");
            stage.setScene(scene);
            stage.show();
        } catch (Exception e){
            System.out.println("Error while logging out");
        }
    }

}
