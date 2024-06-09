package client.interfaces;

import common.business.IObserver;
import common.business.IService;
import common.business.ProjectException;
import common.domain.Item;
import common.domain.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AddItemController implements IObserver {

    private IService service;

    private User user;

    @FXML
    private Button addButton;

    @FXML
    private TextField nameField;

    @FXML
    private TextField priceField;

    @FXML
    private TextField quantityField;

    public void setController(IService service, User user) {
        this.service = service;
        this.user = user;
    }

    @FXML
    void AddItem(ActionEvent event) {
        if (nameField.getText().isEmpty() || priceField.getText().isEmpty() || quantityField.getText().isEmpty()) {
            return;
        }
        String name = nameField.getText();
        double price = Double.parseDouble(priceField.getText());
        int quantity = Integer.parseInt(quantityField.getText());
        try {
            Item item = new Item(name, quantity, price);
            service.addItem(item);
            Stage stage = (Stage) addButton.getScene().getWindow();
            stage.close();


        } catch (ProjectException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update() {
        }
}
