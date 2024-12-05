/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMain.java to edit this template
 */
package Documentation;

import InventoryManagement.FXMLDocumentController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import javafx.scene.Scene;

/**
 *
 * @author Khai
 */
public class Documentation extends Application {

    private FXMLDocumentController mainController;

    // Method to set the mainController reference
    public void setMainController(FXMLDocumentController mainController) {
        this.mainController = mainController;
    }

    @Override
    public void start(Stage stage) throws Exception {
        // Load the FXML for AddWindow
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Documentation.fxml"));
        Parent root = loader.load();

        // Access the controller from the loaded FXML
        DocumentationController docWindowController = loader.getController();

        // Pass the mainController to the AddWindowController
        docWindowController.setMainController(mainController);

        // Set up the stage and scene
        Scene scene = new Scene(root);
//        scene.getStylesheets().add(getClass().getResource("Add.css").toExternalForm());
        stage.getIcons().add(new Image(getClass().getResourceAsStream("System.png")));
        stage.setTitle("User Guide");
        stage.setScene(scene);
        stage.show();
    }
//    @Override
//    public void start(Stage stage) throws IOException {
//        Parent root = FXMLLoader.load(getClass().getResource("AddWindow.fxml"));
//
//        Scene scene = new Scene(root);
//
//        scene.getStylesheets().add(getClass().getResource("Add.css").toExternalForm());
//        stage.getIcons().add(new Image(getClass().getResourceAsStream("CRUD.png")));
//        stage.setTitle("Add a User");
//        stage.setResizable(false);
//        stage.setScene(scene);
//        stage.show();
//    }
//
//    /**
//     * @param args the command line arguments
//     */
//    public static void main(String[] args) {
//        launch(args);
//    }

}
