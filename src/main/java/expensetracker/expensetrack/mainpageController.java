package expensetracker.expensetrack;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;

public class mainpageController {
    private String username;
    @FXML
    public Menu incomeMenu;
    @FXML
    public MenuItem homeMenuItem;
    @FXML
    public MenuItem incomeMenuItem;
    @FXML
    public MenuItem exitMenuItem;
    @FXML
    public ComboBox<String> monthSelectBox;
    @FXML
    public AnchorPane mainPane;

    public void setUsername(String username) {
        this.username = username;
        System.out.println("Username set to: " + username);
    }

    @FXML
    public void initialize() {
        monthSelectBox.setItems(FXCollections.observableArrayList("January","February","March","April","May","June","July","August","September","October","November","December"));
        monthSelectBox.valueProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue == null) {
                System.out.println("No item selected");
            } else {
                System.out.println("Selected item: " + newValue);
            }
        });

        incomeMenuItem.setOnAction((ActionEvent event) -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("incomeView.fxml"));
                Parent root = loader.load();
                incomeController controller = loader.getController();
                controller.setUsername(username);
                Scene scene = new Scene(root);
                Stage stage = (Stage) mainPane.getScene().getWindow();
                stage.setScene(scene);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}
