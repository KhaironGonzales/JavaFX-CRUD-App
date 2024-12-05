/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMain.java to edit this template
 */
package EditWindow;

import InventoryManagement.FXMLDocumentController;
import InventoryManagement.Inventory;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 *
 * @author Khai
 */
public class EditWindow extends Application {

    private FXMLDocumentController mainController;
    private Inventory selectedItem;

    // Setter for mainController
    public void setMainController(FXMLDocumentController mainController) {
        this.mainController = mainController;
    }

    // Setter for selectedItem
    public void setUserData(Inventory selectedItem) {
        this.selectedItem = selectedItem;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("EditWindow.fxml"));
        Parent root = loader.load();

        // Pass the data to the EditWindowController
        EditWindowController controller = loader.getController();
        if (mainController != null) {
            controller.setMainController(mainController);
        }
        if (selectedItem != null) {
            controller.setUserData(selectedItem);
        }

        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("Edit.css").toExternalForm()); // Add your CSS file here
        primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("System.png")));
        primaryStage.setTitle("Edit Item");
        primaryStage.setScene(scene);

    }
//    private FXMLDocumentController mainController;
//
//    // Method to set the mainController reference
//    public void setMainController(FXMLDocumentController mainController) {
//        this.mainController = mainController;
//    }
//
//    @Override
//    public void start(Stage stage) throws Exception {
//        // Load the FXML for AddWindow
//        FXMLLoader loader = new FXMLLoader(getClass().getResource("EditWindow.fxml"));
//        Parent root = loader.load();
//
//        // Access the controller from the loaded FXML
//        EditWindowController editWindowController = loader.getController();
//
//        // Pass the mainController to the AddWindowController
//        editWindowController.setMainController(mainController);
//
//        // Set up the stage and scene
//        Scene scene = new Scene(root);
//        scene.getStylesheets().add(getClass().getResource("Edit.css").toExternalForm());
//        stage.getIcons().add(new Image(getClass().getResourceAsStream("CRUD.png")));
//        stage.setScene(scene);
//        stage.show();
//    }
//    @Override
//    public void start(Stage stage) throws IOException {
//        Parent root = FXMLLoader.load(getClass().getResource("EditWindow.fxml"));
//
//        Scene scene = new Scene(root);
//        scene.getStylesheets().add(getClass().getResource("Edit.css").toExternalForm());
//        stage.getIcons().add(new Image(getClass().getResourceAsStream("CRUD.png")));
//        stage.setTitle("Edit User");
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
