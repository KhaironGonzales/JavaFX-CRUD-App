package EditWindow;

import InventoryManagement.Inventory;

import InventoryManagement.FXMLDocumentController;
import InventoryManagement.Inventory;
import InventoryManagement.MySQLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class EditWindowController {

    @FXML
    private AnchorPane Anchor;

    @FXML
    private Text Invalid;

    @FXML
    private Button backButton;

    @FXML
    private TextField categoryField;

    @FXML
    private Button confirmButton;

    @FXML
    private TextField idField;

    @FXML
    private TextField nameField;

    @FXML
    private TextField priceField;

    @FXML
    private Button clearButton;
    @FXML
    private TextField quantityField;

    @FXML
    private TextField supplierField;

    private FXMLDocumentController editController;

    public void setMainController(FXMLDocumentController mainController) {
        this.editController = mainController;
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
        // Close the Edit window without saving
        ((Stage) backButton.getScene().getWindow()).close();
    }

    @FXML
    void handleConfirmButtonClick(ActionEvent event) {
        // Clear any previous error message
        Invalid.setText("");

        // Get input values from the fields
        String name = nameField.getText();
        String category = categoryField.getText();
        String quantityText = quantityField.getText();
        String priceText = priceField.getText();
        String supplier = supplierField.getText();

        // Validate the inputs
        if (name.isEmpty() || category.isEmpty() || quantityText.isEmpty()
                || priceText.isEmpty() || supplier.isEmpty()) {
            Invalid.setText("Please fill in all fields.");
            Invalid.setStyle("-fx-fill: red;");
            return;
        }

        if (!category.matches("[a-zA-Z\\s]+")) {
            Invalid.setText("Category must only contain letters and spaces.");
            Invalid.setStyle("-fx-fill: red;");
            return;
        }

        if (!quantityText.matches("\\d+")) {
            Invalid.setText("Quantity must be a valid number.");
            Invalid.setStyle("-fx-fill: red;");
            return;
        }

        if (!priceText.matches("\\d*(\\.\\d{1,2})?")) {
            Invalid.setText("Price must be a valid number (e.g., 10.99).");
            Invalid.setStyle("-fx-fill: red;");
            return;
        }

        // Show a confirmation dialog with input details
        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Confirm Changes");
        confirmationAlert.setHeaderText("Are you sure you want to save these details?");
        confirmationAlert.setContentText(
                "Name: " + name + "\n"
                + "Category: " + category + "\n"
                + "Quantity: " + quantityText + "\n"
                + "Price: " + priceText + "\n"
                + "Supplier: " + supplier
        );

        // Show the dialog and wait for the user's response
        ButtonType result = confirmationAlert.showAndWait().orElse(ButtonType.CANCEL);

        if (result == ButtonType.OK) {
            try {
                int quantity = Integer.parseInt(quantityText);
                double price = Double.parseDouble(priceText);

                // Update the item data
                item.setName(name);
                item.setCategory(category);
                item.setQuantity(quantity);
                item.setPrice(price);
                item.setSupplier(supplier);

                // Update the database
                updateUserInDatabase(item);

                System.out.println("Product added successfull!");
                System.out.println("Product: " + name);
                System.out.println("Category: " + category);
                System.out.println("Quantity: " + quantity);
                System.out.println("Price: " + price);
                System.out.println("Supplier: " + supplier);

                // Notify the main controller to refresh the TableView
                if (editController != null) {
                    editController.refreshTableView();
                }

                // Close the Edit window
                Stage stage = (Stage) confirmButton.getScene().getWindow();
                stage.close();

            } catch (Exception e) {
                e.printStackTrace();
                Invalid.setText("An error occurred while saving. Please try again.");
                Invalid.setStyle("-fx-fill: red;");
            }
        } else {
            System.out.println("Changes not saved.");
        }
    }

    private Inventory item;

    public void setUserData(Inventory item) {
        this.item = item;
        idField.setText(String.valueOf(item.getId()));  // Display ID (non-editable)
        nameField.setText(item.getName());
        categoryField.setText(item.getCategory());
        quantityField.setText(String.valueOf(item.getQuantity()));
        priceField.setText(String.valueOf(item.getPrice()));
        supplierField.setText(item.getSupplier());

        idField.setEditable(false);
        idField.setDisable(true);
    }

    private void updateUserInDatabase(Inventory item) {
        String updateQuery = "UPDATE items SET name = ?, category = ?, quantity = ?, price = ?, supplier = ? WHERE id = ?";

        try (Connection conn = MySQLConnection.connect(); PreparedStatement pstmt = conn.prepareStatement(updateQuery)) {
            pstmt.setString(1, item.getName());
            pstmt.setString(2, item.getCategory());
            pstmt.setInt(3, item.getQuantity());
            pstmt.setDouble(4, item.getPrice());
            pstmt.setString(5, item.getSupplier());
            pstmt.setInt(6, item.getId());
            pstmt.executeUpdate();

            System.out.println("Product updated: " + item.getName());

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
