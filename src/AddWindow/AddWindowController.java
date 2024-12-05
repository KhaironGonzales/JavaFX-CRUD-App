package AddWindow;

import InventoryManagement.FXMLDocumentController;
import InventoryManagement.MySQLConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class AddWindowController {

    @FXML
    private ImageView img;
    @FXML
    private AnchorPane Anchor;

    @FXML
    private Text Invalid;

    @FXML
    private Button backButton;

    @FXML
    private Button confirmButton;
    @FXML
    private TextField nameField;
    @FXML
    private TextField categoryField;

    @FXML
    private TextField priceField;

    @FXML
    private TextField quantityField;

    @FXML
    private Button clearButton;

    @FXML
    private TextField supplierField;

    private FXMLDocumentController mainController;

    public void setMainController(FXMLDocumentController mainController) {
        this.mainController = mainController;
    }

    @FXML
    void handlClearButtonclick(ActionEvent event) {
        nameField.clear();          // Clears only the text, not the prompt text
        categoryField.clear();      // Clears only the text, not the prompt text
        quantityField.clear();      // Clears only the text, not the prompt text
        priceField.clear();         // Clears only the text, not the prompt text
        supplierField.clear();

    }

    @FXML
    void handleCancelButtonClick(ActionEvent event) {
        Stage stage = (Stage) backButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    void handleConfirmButtonClick(ActionEvent event) {

        String name = nameField.getText();
        String category = categoryField.getText();
        String quantityText = quantityField.getText();
        String priceText = priceField.getText();
        String supplier = supplierField.getText();

        // Clear any previous error message
        Invalid.setText("");

        // Check if any of the fields are empty
        if (name.isEmpty() || category.isEmpty() || quantityText.isEmpty()
                || priceText.isEmpty() || supplier.isEmpty()) {
            Invalid.setText("Please fill in all fields.");
            Invalid.setStyle("-fx-fill: red;");
            System.out.println("Please fill in all fields.");
            return;
        }

        // Validate category input (must be letters and spaces only)
        if (!category.matches("[a-zA-Z\\s]+")) {
            Invalid.setText("Category must only contain letters and spaces.");
            Invalid.setStyle("-fx-fill: red;");
            System.out.println("Category validation failed.");
            return;
        }

        // Validate quantity input (must be a whole number)
        if (!quantityText.matches("\\d+")) {
            Invalid.setText("Quantity must be a valid number.");
            Invalid.setStyle("-fx-fill: red;");
            System.out.println("Quantity validation failed.");
            return;
        }

        // Validate price input (must be a number with up to 2 decimal places)
        if (!priceText.matches("\\d*(\\.\\d{1,2})?")) {
            Invalid.setText("Price must be a valid number.");
            Invalid.setStyle("-fx-fill: red;");
            System.out.println("Price validation failed.");
            return;
        }

        // Show a confirmation dialog before saving the data
        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Confirm Changes");
        confirmationAlert.setHeaderText("Are you sure you want to save these details?");
        // Optional: Provide details in the confirmation dialog
        confirmationAlert.setContentText(
                "Name: " + name + "\n"
                + "Category: " + category + "\n"
                + "Quantity: " + quantityText + "\n"
                + "Price: " + priceText + "\n"
                + "Supplier: " + supplier
        );

        // Show the dialog and wait for the user's response
        ButtonType result = confirmationAlert.showAndWait().orElse(ButtonType.CANCEL);

        // If the user clicks "OK" (Yes), proceed with saving and closing
        if (result == ButtonType.OK) {
            try {
                int quantity = Integer.parseInt(quantityText); // Parse quantity as an integer
                double price = Double.parseDouble(priceText); // Parse price as a double

                saveUserToDatabase(name, category, quantity, price, supplier);
                System.out.println("Product added successfull!");
                System.out.println("Product: " + name );
                System.out.println("Category: " + category );
                System.out.println("Quantity: " + quantity );
                System.out.println("Price: " + price );
                System.out.println("Supplier: " + supplier );
                

                // Refresh the TableView in MainController
                if (mainController != null) {
                    mainController.refreshTableView();
                }

                // Close the window
                Stage stage = (Stage) confirmButton.getScene().getWindow();
                stage.close();

            } catch (NumberFormatException e) {
                // Handle unexpected parsing errors (shouldn't occur due to validation)
                Invalid.setText("An error occurred while parsing the input.");
                Invalid.setStyle("-fx-fill: red;");
                e.printStackTrace();
            }
        } else {
            // If the user clicks "Cancel" (No), just print a message (optional)
            System.out.println("Changes not saved.");
        }
    }
//        String name = nameField.getText();
//        String category = categoryField.getText();
//        String quantityText = quantityField.getText();
//        String priceText = priceField.getText();
//        String supplier = supplierField.getText();
//
//        // Clear any previous error message
//        Invalid.setText("");
//
//        // Check if any of the fields are empty
//        if (name.isEmpty() || category.isEmpty() || quantityText.isEmpty()
//                || priceText.isEmpty() || supplier.isEmpty()) {
//            Invalid.setText("Please fill in all fields.");
//            Invalid.setStyle("-fx-fill: red;");
//            System.out.println("Please fill in all fields.");
//            return;
//        }
//
//        // Show a confirmation dialog before saving the data
//        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
//        confirmationAlert.setTitle("Confirm Changes");
//        confirmationAlert.setHeaderText("Are you sure you want to save these details?");
////        confirmationAlert.setContentText("Name: " + name + "\nEmail: " + email + "\nAge: " + ageText);
//
//        // Show the dialog and wait for the user's response
//        ButtonType result = confirmationAlert.showAndWait().orElse(ButtonType.CANCEL);
//
//        // If the user clicks "OK" (Yes), proceed with saving and closing
//        if (result == ButtonType.OK) {
//            try {
//                int quantity = Integer.parseInt(quantityText);
//                double price = Double.parseDouble(priceText);
//
//                saveUserToDatabase(name, category, quantity, price, supplier);
//                System.out.println("Product: " + name + "Added Successfully.");
//
//                // Refresh the TableView in MainController
//                if (mainController != null) {
//                    mainController.refreshTableView();
//                }
//
//                // Close the window
//                Stage stage = (Stage) confirmButton.getScene().getWindow();
//                stage.close();
//
//            } catch (NumberFormatException e) {
//
//            }
//        } else {
//            // If the user clicks "Cancel" (No), just print a message (optional)
//            System.out.println("Changes not saved.");
//        }
//    }

// Updated email validation method
    private void saveUserToDatabase(String name, String category, int quantity, double price, String supplier) {
        String insertQuery = "INSERT INTO items (name, category, quantity, price, supplier) VALUES (?, ?, ?, ?, ? )";
        try (Connection conn = MySQLConnection.connect(); PreparedStatement pstmt = conn.prepareStatement(insertQuery)) {

            pstmt.setString(1, name);
            pstmt.setString(2, category);
            pstmt.setInt(3, quantity);
            pstmt.setDouble(4, price);
            pstmt.setString(5, supplier);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error saving data to the database.");
        }
    }

//    @FXML
//    public void initialize() {
//        // Restrict categoryField to accept only letters
//        categoryField.textProperty().addListener((observable, oldValue, newValue) -> {
//            if (!newValue.matches("[a-zA-Z ]*")) { // Allows letters and spaces only
//                categoryField.setText(oldValue);
//            }
//        });
//
//        // Restrict quantityField to accept only whole numbers
//        quantityField.textProperty().addListener((observable, oldValue, newValue) -> {
//            if (!newValue.matches("\\d*")) { // Allows digits only
//                quantityField.setText(oldValue);
//            }
//        });
//
//        // Restrict priceField to accept only numbers (including decimals)
//        priceField.textProperty().addListener((observable, oldValue, newValue) -> {
//            if (!newValue.matches("\\d*(\\.\\d{0,2})?")) { // Allows digits and up to 2 decimals
//                priceField.setText(oldValue);
//            }
//        });
//    }
}
