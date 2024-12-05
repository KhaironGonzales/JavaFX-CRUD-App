package InventoryManagement;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Inventory {

    private final IntegerProperty id;
    private final StringProperty name;
    private final StringProperty category;
    private final IntegerProperty quantity;
    private final DoubleProperty price;
    private final StringProperty supplier;

    public Inventory(int id, String name, String category, int quantity, double price, String supplier) {
        this.id = new SimpleIntegerProperty(id);
        this.name = new SimpleStringProperty(name);
        this.category = new SimpleStringProperty(category);
        this.quantity = new SimpleIntegerProperty(quantity);
        this.price = new SimpleDoubleProperty(price);
        this.supplier = new SimpleStringProperty(supplier);
    }

    // Getters for properties
    public int getId() {
        return id.get();
    }

    public String getName() {
        return name.get();
    }

    public String getCategory() {
        return category.get();
    }

    public int getQuantity() {
        return quantity.get();
    }

    public double getPrice() {
        return price.get();
    }

    public String getSupplier() {
        return supplier.get();
    }

    // Property getters
    public IntegerProperty idProperty() {
        return id;
    }

    public StringProperty nameProperty() {
        return name;
    }

    public StringProperty categoryProperty() {
        return category;
    }

    public IntegerProperty quantityProperty() {
        return quantity;
    }

    public DoubleProperty priceProperty() {
        return price;
    }

    public StringProperty supplierProperty() {
        return supplier;
    }

    // Setter methods for properties
    public void setName(String name) {
        this.name.set(name);
    }

    public void setCategory(String category) {
        this.category.set(category);
    }

    public void setQuantity(int quantity) {
        this.quantity.set(quantity);
    }

    public void setPrice(double price) {
        this.price.set(price);
    }

    public void setSupplier(String supplier) {
        this.supplier.set(supplier);
    }

}
