package client.interfaces;

import common.business.IService;
import common.domain.Item;
import common.domain.User;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

public class UpdateListController {

    private IService service;

    private User user;

    @FXML
    private Button closeButton;

    @FXML
    private ListView<Item> myList;

    @FXML
    private Button updateButton;

    void setController(IService service, User user) {
        this.service = service;
        this.user = user;
        try{
            myList.setItems(FXCollections.observableArrayList(service.getAllItems()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void CloseButton(ActionEvent event) {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    void UpdateButton(ActionEvent event) {
        Item item = myList.getSelectionModel().getSelectedItem();
        if (item == null) {
            return;
        }
        try {
            Stage stage = (Stage) updateButton.getScene().getWindow();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/UpdateItem.fxml"));
            Parent root = loader.load();
            UpdateItemController controller = loader.getController();
            controller.setController(service, user, item);

            Scene scene = new Scene(root);
            stage.setTitle("Update item");
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            System.out.println("Error while logging out");
        }
    }

}