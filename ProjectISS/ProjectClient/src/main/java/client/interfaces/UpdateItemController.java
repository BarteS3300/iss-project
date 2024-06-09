package client.interfaces;

import common.business.IService;
import common.business.ProjectException;
import common.domain.Item;
import common.domain.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class UpdateItemController {

    private IService service;

    private User user;

    private Item item;

    @FXML
    private Button cancelButton;

    @FXML
    private Button confirmButton;

    @FXML
    private Label itemText;

    @FXML
    private TextField nameText;

    @FXML
    private TextField priceText;

    @FXML
    private TextField quantityText;

    void setController(IService service, User user, Item item){
        this.service = service;
        this.user = user;
        this.item = item;
        itemText.setText("Update item: " + item.getId());
        nameText.setText(item.getName());
        priceText.setText(String.valueOf(item.getPrice()));
        quantityText.setText(String.valueOf(item.getQuantity()));
    }

    @FXML
    void CancelButton(ActionEvent event) {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    void ConfirmButton(ActionEvent event) throws ProjectException {
        if (nameText.getText().isEmpty() || priceText.getText().isEmpty() || quantityText.getText().isEmpty()) {
            return;
        }
        Item newItem = new Item(item.getId(), nameText.getText(), Integer.parseInt(quantityText.getText()), Double.parseDouble(priceText.getText()));
        service.updateItem(newItem);
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

}
