package client.interfaces;

import common.business.IService;
import common.business.ProjectException;
import common.domain.Item;
import common.domain.User;
import javafx.concurrent.Service;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

public class DeleteItemController {

    private IService service;

    private User user;

    @FXML
    private Button closeButton;

    @FXML
    private Button deleteButton;

    @FXML
    private ListView<Item> myList;

    public void setController(IService service, User user) throws ProjectException {
        this.service = service;
        this.user = user;
        myList.getItems().addAll(service.getAllItems());
    }

    @FXML
    void CloseButton(ActionEvent event) {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    void DeleteButton(ActionEvent event) {
        Item item = myList.getSelectionModel().getSelectedItem();
        if (item == null) {
            return;
        }
        try {
            service.deleteItem(item.getId());
            myList.getItems().remove(item);
            Stage stage = (Stage) closeButton.getScene().getWindow();
            stage.close();
        } catch (ProjectException e) {
            e.printStackTrace();
        }

    }
}
