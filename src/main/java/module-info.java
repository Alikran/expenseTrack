module expensetracker.expensetrack {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;

    opens expensetracker.expensetrack to javafx.fxml;
    exports expensetracker.expensetrack;
}