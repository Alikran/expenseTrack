package expensetracker.expensetrack;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;

public class signupController extends databaseHandler {
    @FXML
    private TextField usernameText;
    @FXML
    private PasswordField passwordTxt;
    @FXML
    public Button createAccountBtn;
    @FXML
    public Button backBtn;

    @FXML
    public Label errorOrCreateLbl;

    @FXML
    public void initialize() {
        createAccountBtn.setOnAction(event -> {
            // Get the username and password entered by the user
            String username = usernameText.getText();
            String password = passwordTxt.getText();
            System.out.println(username);
            System.out.println(password);

            // adds the user into database, return false if user already exists
            databaseHandler dbHandler = new databaseHandler();
            boolean addUser = dbHandler.createUser(username, password);
            dbHandler.closeConnection();

            // Display an error message if the user already exists other let user know account was created
            if (!addUser) {
                errorOrCreateLbl.setText("Account already exists/invalid");
                errorOrCreateLbl.setTextFill(Color.RED);
            }
            else {
                errorOrCreateLbl.setText("Success creating account");
                errorOrCreateLbl.setTextFill(Color.GREEN);
            }
        });

        backBtn.setOnAction(event -> {
            try {
                Parent root = FXMLLoader.load(getClass().getResource("loginView.fxml"));
                Scene scene = new Scene(root, 600, 400);
                Stage stage = (Stage) backBtn.getScene().getWindow();
                stage.setResizable(false);
                stage.setScene(scene);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}
