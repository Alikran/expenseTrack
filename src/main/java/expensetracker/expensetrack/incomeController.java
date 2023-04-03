package expensetracker.expensetrack;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashMap;

public class incomeController extends databaseHandler {
    private String username;
    private String month;
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
    @FXML
    public Button addIncomeBtn;
    @FXML
    public TextField incomeAmount;
    @FXML
    public TextField categoryTxt;
    @FXML
    public Label addResultTxt;
    @FXML
    public Button homeBtn;
    @FXML
    public PieChart incomePieChart;

    public boolean populated = false;


    @FXML void loadChart( databaseHandler myDbHandler){
        HashMap<String, Double> incomeData = myDbHandler.getCurrentUserSourcesByMonth(username,month);
        //use HashMap to create table
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
        // Add the data to the pie chart
        for (String source : incomeData.keySet()) {
            Double amount = incomeData.get(source);
            pieChartData.add(new PieChart.Data(source, amount));
        }
        // set the pie chart data
        incomePieChart.setData(pieChartData);
    }
    @FXML
    public void validLoad( databaseHandler myDbHandler){
        String addAmount = incomeAmount.getText();
        double amount = Double.parseDouble(addAmount);
        String addSource = categoryTxt.getText();
        addIncomeBtn.setOnAction(event -> {
            boolean result = myDbHandler.addIncome(username, amount, addSource, month);
            if (result) {
                System.out.println("Income added successfully!");
                loadChart(myDbHandler);
            }
        });
    }

    public void setUsername(String username) {
        this.username = username;
        System.out.println("Username set to: " + username);
    }

    @FXML
    public void initialize() {
        // Set the values of the ComboBox and MenuBar
        monthSelectBox.setItems(FXCollections.observableArrayList("January","February","March","April","May","June","July","August","September","October","November","December"));
        incomeMenu.setText("Income");
        homeMenuItem.setText("Home");
        incomeMenuItem.setText("Income");
        exitMenuItem.setText("Exit");
        databaseHandler dbHandler = new databaseHandler();

        homeMenuItem.setOnAction((ActionEvent event)-> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("mainpageView.fxml"));
                Parent root = loader.load();
                mainpageController controller = loader.getController();
                controller.setUsername(username);
                Scene scene = new Scene(root);
                Stage stage = (Stage) mainPane.getScene().getWindow();
                stage.setScene(scene);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        homeBtn.setOnAction(event -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("mainpageView.fxml"));
                Parent root = loader.load();
                mainpageController controller = loader.getController();
                controller.setUsername(username);
                Scene scene = new Scene(root);
                Stage stage = (Stage) mainPane.getScene().getWindow();
                stage.setScene(scene);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        //make sure user cant add anything unless month is specified;
        monthSelectBox.valueProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue == null) {
                addResultTxt.setText("Select month");
            } else {
                month = newValue;
                System.out.println("Selected item: " + newValue);
                addResultTxt.setText("");
                loadChart(dbHandler);
                populated = true;
            }

            if(populated == true){
                addIncomeBtn.setOnAction(event -> {
                    validLoad(dbHandler);
                });
            }
        });

    }
}
