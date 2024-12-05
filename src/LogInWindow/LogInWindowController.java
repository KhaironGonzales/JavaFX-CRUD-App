package LogInWindow;

import InventoryManagement.FXMLDocumentController;
import InventoryManagement.InventoryManagement;
import InventoryManagement.MySQLConnection;
import java.io.IOException;
import java.sql.Connection;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

public class LogInWindowController {

    @FXML
    private AnchorPane AnchorPane;

    @FXML
    private Button LoginButton;

    @FXML
    private TextField Username;

    @FXML
    private ImageView WowImage;

    @FXML
    private Text text;

    @FXML
    private Button showPass;

    @FXML
    private PasswordField Password;

    private FXMLDocumentController mainController;

    public void setMainController(FXMLDocumentController mainController) {
        this.mainController = mainController;
    }

    @FXML
    public void initialize() {

        // Style login button
        LoginButton.setStyle("-fx-cursor: hand;");

        // Listen for Enter key press on the password field to trigger login
        Password.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                handleLogin(new ActionEvent());  // Call handleLogin as if the login button was pressed
            }
        });

    }

    private int failedAttempts = 0; // Counter for failed login attempts
    private boolean isLocked = false; // Flag to indicate login lockout

    @FXML
    void handleLogin(ActionEvent event) {
        if (isLocked) {
            text.setText("Wait 30 seconds to log in again");
            text.setStyle("-fx-fill: red;");
            return;
        }

        String username = Username.getText();
        String password = Password.getText();

        if (isValidCredentials(username, password)) {
            failedAttempts = 0; // Reset failed attempts on successful login
            try {
                // Create an instance of InventoryManagement
                InventoryManagement inventoryManagementApp = new InventoryManagement();

                // Launch the InventoryManagement window
                inventoryManagementApp.start((javafx.stage.Stage) LoginButton.getScene().getWindow());

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            failedAttempts++;
            if (failedAttempts >= 5) {
                isLocked = true;
                text.setText("Wait 30 seconds to log in again");
                text.setStyle("-fx-fill: red;");

                // Lockout period of 30 seconds
                PauseTransition lockoutTimer = new PauseTransition(Duration.seconds(30));
                lockoutTimer.setOnFinished(e -> {
                    isLocked = false; // Unlock after 30 seconds
                    failedAttempts = 0; // Reset failed attempts
                    text.setText(""); // Clear the message
                });
                lockoutTimer.play();

                return;
            }

            // Display invalid credentials message
            text.setText("Invalid username or password");
            text.setStyle("-fx-fill: red;");

            // Clear the message after 3 seconds
            PauseTransition pause = new PauseTransition(Duration.seconds(3));
            pause.setOnFinished(e -> text.setText(""));
            pause.play();
        }
    }

    private boolean isValidCredentials(String username, String password) {
        String query = "SELECT * FROM accounts WHERE BINARY username = ? AND password = ?";
        try (Connection conn = MySQLConnection.connect(); PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, username);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();
            return rs.next(); // Return true if a matching record is found
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false; // Return false if an exception occurs or no match is found
    }
}
