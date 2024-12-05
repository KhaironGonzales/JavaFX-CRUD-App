/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXML.java to edit this template
 */
package LogInWindow;

import InventoryManagement.FXMLDocumentController;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 *
 * @author Khai
 */
public class LogInWindow extends Application {

    private FXMLDocumentController mainController;

    // Method to set the mainController reference
    public void setMainController(FXMLDocumentController mainController) {
        this.mainController = mainController;
    }

    @Override
    public void start(Stage stage) throws Exception {
        // Load the FXML for AddWindow
        FXMLLoader loader = new FXMLLoader(getClass().getResource("LogInwindow.fxml"));
        Parent root = loader.load();

        // Access the controller from the loaded FXML
        LogInWindowController logInWindowController = loader.getController();

        // Pass the mainController to the AddWindowController
        logInWindowController.setMainController(mainController);

        // Set up the stage and scene
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("LogInWindow.css").toExternalForm());
        stage.getIcons().add(new Image(getClass().getResourceAsStream("System.png")));
        stage.setTitle("Invetory Management System");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);

    }
}
