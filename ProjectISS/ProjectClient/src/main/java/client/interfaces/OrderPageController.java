package client.interfaces;

import common.business.IService;
import common.business.ProjectException;
import common.domain.Item;
import common.domain.User;
import common.utils.Observable;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class OrderPageController implements Initializable {

    private IService service;

    private User user;

    private ObservableList<Item> cart = FXCollections.observableArrayList();

    @FXML
    private TableView<Item> itemTable;

    @FXML
    private TableColumn<Item, String> nameCol;

    @FXML
    private Button orderButton;

    @FXML
    private TableColumn<Item, Double> priceCol;

    @FXML
    private TableColumn<Item, Integer> quantityColumn;

    @FXML
    private Label totalLabel;

    @FXML
    private Button deleteCartButton;

    public void setController(IService service, User user, List<Item> cart) {
        this.service = service;
        this.user = user;
        this.cart = FXCollections.observableArrayList(cart);
        loadTable();
        totalLabel.setText("Total: " + cart.stream().mapToDouble(Item::getPrice).sum());
    }

    public void loadTable() {
        itemTable.setItems(cart);
    }

    @FXML
    void OrderButton(ActionEvent event){
        try{
            service.orderItems(cart, user);
        } catch (ProjectException e){
            MessageBoxController.show(e.getMessage());
        }

        Stage stage = (Stage) orderButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    void DeleteCartButton(ActionEvent event) {
        Stage stage = (Stage) orderButton.getScene().getWindow();
        stage.close();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        nameCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        priceCol.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getPrice()).asObject());
        quantityColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getQuantity()).asObject());
        itemTable.setItems(cart);
    }
}
