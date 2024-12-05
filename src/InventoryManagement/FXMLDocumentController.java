package InventoryManagement;

import AddWindow.AddWindow;
import AddWindow.AddWindowController;
import EditWindow.EditWindow;
import EditWindow.EditWindowController;
import Documentation.Documentation;
import LogInWindow.LogInWindow;
import java.sql.PreparedStatement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.animation.PauseTransition;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class FXMLDocumentController {

    private FXMLDocumentController mainController;
    private EditWindowController editController;
    @FXML
    private ImageView EditImg;

    @FXML
    private Button Logout;

    @FXML
    private ImageView LogoutBut;

    @FXML
    private Button addBut;

    @FXML
    private ImageView addImg;

    @FXML
    private AnchorPane canvas;

    @FXML
    private TableColumn<Inventory, String> colCategory;//category

    @FXML
    private TableColumn<Inventory, Integer> colId;//id

    @FXML
    private TableColumn<Inventory, String> colName;//name

    @FXML
    private TableColumn<Inventory, Integer> colPrice;//price

    @FXML
    private TableColumn<Inventory, Integer> colQuantity;//quantity

    @FXML
    private TableColumn<Inventory, String> colSupplier;//supplier

    @FXML
    private TableView<Inventory> dataTable;

    @FXML
    private Button delBut;

    @FXML
    private ImageView delImg;

    @FXML
    private Button editBut;

    @FXML
    private Button searchBut; //search button

    @FXML
    private TextField searchField; //search field

    @FXML
    private Text text;

    @FXML
    void handleAddButtonClick(ActionEvent event) {
        try {
            // Create a new instance of AddWindow
            AddWindow addWindowApp = new AddWindow();

            // Pass the mainController reference to AddWindow
            addWindowApp.setMainController(this);  // 'this' refers to InventoryManagementController
            //By passing 'this', it gives the AddWindow class a way 
            //to access methods and fields of the InventoryManagementController.
            // Create a new stage for the AddWindow
            Stage stage = new Stage();

            // Set modality before starting the application
            stage.initModality(Modality.APPLICATION_MODAL);

            // Start the AddWindow application
            addWindowApp.start(stage);

            // Set stage properties
            stage.setResizable(false);
            stage.setOnCloseRequest(e -> {
                // Refresh the table view when the window is closed
                refreshTableView();
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @FXML
    void handleDeleteButtonClick(ActionEvent event) {
        // Get the selected user from the TableView
        Inventory selectedUser = dataTable.getSelectionModel().getSelectedItem();

        if (selectedUser == null) {
            // No user selected, show an alert or message
            text.setText("Please select item to delete");
            System.out.println("Please select item to delete");

            // Create a PauseTransition to clear the message after 3 seconds
            PauseTransition pause = new PauseTransition(Duration.seconds(3)); // 3 seconds delay
            pause.setOnFinished(e -> text.setText("")); // Clear the message after 3 seconds
            pause.play();  // Start the pause transition

            return;
        }

        // Show a confirmation dialog before deleting
        Alert confirmationAlert = new Alert(AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Confirm Deletion");
        confirmationAlert.setHeaderText("Are you sure you want to delete the selected user?");
        confirmationAlert.setContentText("This action cannot be undone.");

        // Show the dialog and wait for the user's response
        ButtonType result = confirmationAlert.showAndWait().orElse(ButtonType.CANCEL);

        // If the user clicks "OK" (Yes), proceed with deletion
        if (result == ButtonType.OK) {
            // Delete the user from the database
            deleteUserFromDatabase(selectedUser);

            // Refresh the TableView to reflect the deletion
            refreshTableView();
        } else {
            // If the user clicks "Cancel" (No), just print a message (optional)
            System.out.println("User deletion canceled.");
        }
    }

    @FXML
    void handleEditButton(ActionEvent event) {

        Inventory selectedItem = dataTable.getSelectionModel().getSelectedItem();  // Get selected item from TableView

        if (selectedItem != null) {
            try {

                // Create an instance of EditWindow
                EditWindow editWindowApp = new EditWindow();

                // Pass the main controller reference to EditWindow
                editWindowApp.setMainController(this);

                // Pass the selected item to the EditWindowController
                editWindowApp.setUserData(selectedItem);  // This method sets the data in the EditWindow input fields

                // Open the EditWindow
                Stage stage = new Stage();
                editWindowApp.start(stage);
                stage.initModality(Modality.APPLICATION_MODAL);  // Make it modal (blocks interaction with other windows)
                stage.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            // Handle the case when no row is selected (optional)

            text.setText("Please select item to edit");
            System.out.println("Please select item to edit");

            // Create a PauseTransition to clear the message after 3 seconds
            PauseTransition pause = new PauseTransition(Duration.seconds(3)); // 3 seconds delay
            pause.setOnFinished(e -> text.setText("")); // Clear the message after 3 seconds
            pause.play();  // Start the pause transition

            return;
        }

    }

    @FXML
    void handleLogoutButtonClick(ActionEvent event) {

        // Create a confirmation dialog
        Alert confirmationAlert = new Alert(AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Confirm Logout");
        confirmationAlert.setHeaderText("Are you sure you want to log out?");
        confirmationAlert.setContentText("You will be returned to the login screen.");

        // Wait for the user's response
        ButtonType result = confirmationAlert.showAndWait().orElse(ButtonType.CANCEL);

        // If the user confirms (clicks "OK")
        if (result == ButtonType.OK) {
            // Close the current window
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentStage.close();
            try {
                // Create a new instance of AddWindow
                LogInWindow logInWindowApp = new LogInWindow();

                // Pass the mainController reference to AddWindow
                logInWindowApp.setMainController(this);  // 'this' refers to InventoryManagementController

                // Create a new stage for the AddWindow
                Stage stage = new Stage();

                // Set modality before starting the application
                stage.initModality(Modality.APPLICATION_MODAL);

                // Start the AddWindow application
                logInWindowApp.start(stage);

                // Set stage properties
                stage.setResizable(false);
                stage.setOnCloseRequest(e -> {
                    // Refresh the table view when the window is closed
                    refreshTableView();
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    @FXML
    void handleUserGuideButton(ActionEvent event) {

        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        try {
            // Create a new instance of AddWindow
            Documentation documentationWindow = new Documentation();

            // Pass the mainController reference to AddWindow
            documentationWindow.setMainController(this);  // 'this' refers to InventoryManagementController

            // Create a new stage for the AddWindow
            Stage stage = new Stage();

            // Set modality before starting the application
            stage.initModality(Modality.APPLICATION_MODAL);

            // Start the AddWindow application
            documentationWindow.start(stage);

            // Set stage properties
            stage.setResizable(false);
            stage.setOnCloseRequest(e -> {
                // Refresh the table view when the window is closed
                refreshTableView();
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void initialize() {

        dataTable.setOnMouseClicked(event -> {
            // Check if the click is outside any row
            if (dataTable.getSelectionModel().getSelectedItem() != null) {
                // If click occurs on an empty area (i.e., not on any row)
                if (event.getTarget() instanceof TableView) {
                    dataTable.getSelectionModel().clearSelection(); // Deselect the selected item
                }
            }
        });
        // Set up the columns to bind to the User properties
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colCategory.setCellValueFactory(new PropertyValueFactory<>("category"));
        colQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        colSupplier.setCellValueFactory(new PropertyValueFactory<>("supplier"));

        // Fetch and populate data
        ObservableList<Inventory> data = fetchDataFromDatabase();
        dataTable.setItems(data);

        // Load images and handle any missing images
        loadImages();

        // Set cursor style for buttons
        addBut.setStyle("-fx-cursor: hand;");
        delBut.setStyle("-fx-cursor: hand;");
        editBut.setStyle("-fx-cursor: hand;");
        searchBut.setStyle("-fx-cursor: hand;");
        searchField.setStyle("-fx-cursor: text;");

        dataTable.requestFocus();
        addBut.setFocusTraversable(false);
        editBut.setFocusTraversable(false);
        delBut.setFocusTraversable(false);
        dataTable.setFocusTraversable(false);
        Logout.setFocusTraversable(false);

        // Handle mouse click to deselect a user when clicking outside rows
        searchField.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case ENTER:
                    handleSearchButtonClick(null); // Trigger the search button click handler
                    break;
                default:
                    break;
            }
        });

    }

    private void loadImages() {
        try {
            Image add = new Image(getClass().getResourceAsStream("Icons/Add.png"));
            addImg.setImage(add);
        } catch (Exception e) {
            System.out.println("Error loading Add.png: " + e.getMessage());
        }

        try {
            Image edit = new Image(getClass().getResourceAsStream("Icons/Edit.png"));
            EditImg.setImage(edit);
        } catch (Exception e) {
            System.out.println("Error loading Edit.png: " + e.getMessage());
        }

        try {
            Image del = new Image(getClass().getResourceAsStream("Icons/delete.png"));
            delImg.setImage(del);
        } catch (Exception e) {
            System.out.println("Error loading delete.png: " + e.getMessage());
        }
        try {
            Image logout = new Image(getClass().getResourceAsStream("Icons/Logout.png"));
            LogoutBut.setImage(logout);
        } catch (Exception e) {
            System.out.println("Error loading delete.png: " + e.getMessage());
        }
    }

    public void refreshTableView() {
        ObservableList<Inventory> data = fetchDataFromDatabase();
        dataTable.setItems(data);
    }

    private ObservableList<Inventory> fetchDataFromDatabase() {
        ObservableList<Inventory> data = FXCollections.observableArrayList();

        try (Connection conn = MySQLConnection.connect(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery("SELECT id, name, category, quantity, price, supplier  FROM items ")) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String category = rs.getString("category");
                int quantity = rs.getInt("quantity");
                double price = rs.getDouble("price");
                String supplier = rs.getString("supplier");

                data.add(new Inventory(id, name, category, quantity, price, supplier));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return data;
    }

    @FXML
    void handleSearchButtonClick(ActionEvent event) {
        String query = searchField.getText().trim().toLowerCase(); // Get the search input and convert to lowercase
        ObservableList<Inventory> filteredData = FXCollections.observableArrayList();

        if (query.isEmpty()) {
            dataTable.setItems(fetchDataFromDatabase()); // Load all data if the search field is empty
            return;
        }

        // Filter data based on the search query
//        ObservableList<Inventory> filteredData = FXCollections.observableArrayList();
        for (Inventory item : fetchDataFromDatabase()) {
            if (item.getName().toLowerCase().contains(query)
                    || // Search by name
                    item.getCategory().toLowerCase().contains(query)
                    || // Search by category
                    item.getSupplier().toLowerCase().contains(query)
                    || // Search by supplier
                    String.valueOf(item.getId()).contains(query)
                    || // Search by ID
                    String.valueOf(item.getQuantity()).contains(query)
                    || // Search by quantity
                    String.valueOf(item.getPrice()).contains(query)) { // Search by price
                filteredData.add(item);
            }
        }

        // Update the TableView with filtered data
        dataTable.setItems(filteredData);

    }

    private void deleteUserFromDatabase(Inventory item) {
        String deleteQuery = "DELETE FROM items WHERE id = ?";

        try (Connection conn = MySQLConnection.connect(); PreparedStatement pstmt = conn.prepareStatement(deleteQuery)) {
            pstmt.setInt(1, item.getId());
            pstmt.executeUpdate();

            System.out.println("User deleted: " + item.getName());

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
