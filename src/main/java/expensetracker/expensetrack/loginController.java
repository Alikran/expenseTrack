package expensetracker.expensetrack;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class loginController extends databaseHandler {

    public String username = "";
    @FXML
    private TextField usernameText;
    @FXML
    private PasswordField passwordTxt;
    @FXML
    public Button loginBtn;
    @FXML
    public Button signupBtn;
    @FXML
    public Label errorLabel;

    @FXML
    public void initialize() {
        loginBtn.setOnAction(event -> {
            // Get the username and password entered by the user
            username = usernameText.getText();
            String password = passwordTxt.getText();
            //System.out.println(username);
            //System.out.println(password);

            // Check the username and password against the database
            databaseHandler dbHandler = new databaseHandler();
            boolean isValidUser = dbHandler.validateUser(username, password);
            dbHandler.closeConnection();

            // Display an error message if the user is not valid, otherwise proceed to the main screen
            if (!isValidUser) {
                errorLabel.setVisible(true);
            } else {
                try {
                    System.out.println(username);
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("mainpageView.fxml"));
                    Parent root = loader.load();
                    mainpageController controller = loader.getController();
                    controller.setUsername(username);
                    Scene scene = new Scene(root, 869, 795);
                    Stage stage = (Stage) loginBtn.getScene().getWindow();
                    stage.setResizable(false);
                    stage.setScene(scene);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        signupBtn.setOnAction(event -> {
            try {
                Parent root = FXMLLoader.load(getClass().getResource("signupView.fxml"));
                Scene scene = new Scene(root, 600, 400);
                Stage stage = (Stage) signupBtn.getScene().getWindow();
                stage.setResizable(false);
                stage.setScene(scene);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}
