package client.interfaces;

import common.business.IObserver;
import common.business.IService;
import common.business.ProjectException;
import common.domain.Item;
import common.domain.Role;
import common.domain.User;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class AgentMainPageController implements Initializable, IObserver {

    private IService service;

    private User user;

    private List<Item> cart = new ArrayList<>();

    @FXML
    private Button cartButton;

    @FXML
    private Button logOutButton;

    @FXML
    private Label messageLabel;

    @FXML
    private FlowPane myFlowPanel;

    @FXML
    private Label usernameLabel;

    @FXML
    private Button adminButton;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    private VBox cardLayout(Item item) {
        VBox box = new VBox();
        box.setSpacing(10);
        box.setPadding(new javafx.geometry.Insets(10, 10, 10, 10));
        box.setPrefWidth(150);
        box.setPrefHeight(150);
        javafx.scene.layout.FlowPane.setMargin(box, new javafx.geometry.Insets(10, 10, 10, 10));
        box.setStyle("-fx-background-color: #;");
//        box.setBorder(new javafx.scene.layout.Border(new javafx.scene.layout.BorderStroke(javafx.scene.paint.Color.BLACK,
//                javafx.scene.layout.BorderStrokeStyle.SOLID, new javafx.scene.layout.CornerRadii(5), new javafx.scene.layout.BorderWidths(2))));

        Label nameLabel = new Label("Name: " + item.getName());
        Label quantityLabel = new Label("Quantity: " + item.getQuantity());
        Label priceLabel = new Label("Price: " + item.getPrice());
        nameLabel.setFont(new javafx.scene.text.Font(16));
        quantityLabel.setFont(new javafx.scene.text.Font(16));
        priceLabel.setFont(new javafx.scene.text.Font(16));


        Button addButton = getAddButton(item);


        box.getChildren().addAll(nameLabel, quantityLabel, priceLabel, addButton);

        return box;
    }

    private Button getAddButton(Item item) {
        Button addButton = new Button("Add to cart");
        addButton.setStyle("-fx-background-color: #75C661");
        addButton.setOnAction(event -> {
            Item oneItem = item;
            boolean exist = false;
            for(Item i: cart){
                if(i.getId().equals(oneItem.getId())){
                    i.setQuantity(i.getQuantity() + 1);
                    exist = true;
                    break;
                }
            }
            if(!exist){
                oneItem.setQuantity(1);
                cart.add(oneItem);
            }
            messageLabel.setText("Item added to cart");
            int nrOfItems = 0;
            for(Item i: cart){
                nrOfItems += i.getQuantity();
            }
            cartButton.setText("Cart  "+ nrOfItems);
        });
        return addButton;
    }


    @Override
    public void update() {
        Platform.runLater(this::laod);
    }

    public void laod() {
        try {
            myFlowPanel.getChildren().clear();
            for (Item item : service.getAllItems()) {
                if(item.getQuantity() == 0)
                    continue;
                myFlowPanel.getChildren().add(cardLayout(item));
            }
        } catch (ProjectException e) {
            e.printStackTrace();
        }
    }

    public void setController(IService service, User user) {
        this.service = service;
        this.user = user;
        if(user.getRole() == Role.ADMIN)
            adminButton.setVisible(true);
        usernameLabel.setText("Welcome, " + user.getUsername() + "!");
        laod();
    }

    @FXML
    void LogOut(ActionEvent event) {
        try {
            service.logOutUser(user, this);
            Stage stage = (Stage) logOutButton.getScene().getWindow();
            stage.close();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/LogIn2.fxml"));
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

    @FXML
    void OpenCart(ActionEvent event){
        try {
            Stage stage = new Stage();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/OrderPage.fxml"));
            Parent root = loader.load();
            OrderPageController controller = loader.getController();
            controller.setController(service, user, cart);
            cart = new ArrayList<>();
            cartButton.setText("Cart 0");
            Scene scene = new Scene(root);
            stage.setTitle("Cart!");
            stage.setScene(scene);
            stage.show();
        } catch (Exception e){
            messageLabel.setText("Error while opening cart");
        }
    }

    @FXML
    void AdminCommand(ActionEvent event) {
        try {
            Stage stage = new Stage();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AdminMainPage.fxml"));
            Parent root = loader.load();
            AdminMainPageController controller = loader.getController();
            controller.setController(service, user);
            cart = new ArrayList<>();
            cartButton.setText("Cart 0");
            Scene scene = new Scene(root);
            stage.setTitle("Cart!");
            stage.setScene(scene);
            stage.show();
        } catch (Exception e){
            messageLabel.setText("Error while opening cart");
        }
    }

}
