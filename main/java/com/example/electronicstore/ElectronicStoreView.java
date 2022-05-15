package com.example.electronicstore;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.beans.binding.Binding;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.Parent;
import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;



public class ElectronicStoreView extends Pane {
    ElectronicStore model = new ElectronicStore("");
    ElectronicStore model2 = new ElectronicStore("");
    private Button resetButton, removeButton, addButton, saleButton;
    private ListView<String> stockList, cartList, itemList;
    private TextField salesField, revenueField, dollarpersaleField;
    Label label1, label2, label3, label4, label5, label6, label7;
    private String[] mostProduct, Astock, curstock;

    public ElectronicStoreView(ElectronicStore initmodel) {
        model = initmodel;

        // all labels
        label1 = new Label("Store Summary:");
        label1.relocate(30, 20);
        label2 = new Label("Store Stock:");
        label2.relocate(350, 20);
        label3 = new Label("Current Cart:");
        label3.relocate(620, 20);
        label4 = new Label("# Sales:");
        label4.relocate(30, 50);
        label5 = new Label("Revenue:");
        label5.relocate(30, 80);
        label6 = new Label("$/Sale:");
        label6.relocate(30, 110);
        label7 = new Label("Most Polular Items:");
        label7.relocate(80, 140);

        // all textfields
        salesField = new TextField("0");
        salesField.setEditable(true);
        salesField.relocate(85, 50);
        revenueField = new TextField("0.0");
        revenueField.setEditable(false);
        revenueField.relocate(85, 80);
        dollarpersaleField = new TextField("N/A");
        dollarpersaleField.setEditable(false);
        dollarpersaleField.relocate(85, 110);
        cartList = new ListView<String>();
        cartList.setPrefSize(270, 270);
        cartList.relocate(520, 50);
        stockList = new ListView<String>();
        stockList.setPrefSize(270, 270);
        stockList.relocate(240, 50);
        itemList = new ListView<String>();
        itemList.setPrefSize(200, 160);
        itemList.relocate(30, 160);

        // all buttons
        resetButton = new Button("Reset Store");
        resetButton.setPrefSize(100, 50);
        resetButton.relocate(75, 330);
        addButton = new Button("Add to Cart");
        addButton.setPrefSize(150, 50);
        addButton.relocate(300, 330);
        //addButton.setDisable(true);
        removeButton = new Button("Remove from Cart");
        removeButton.setPrefSize(140, 50);
        removeButton.relocate(535, 330);
        ////removeButton.setDisable(true);
        saleButton = new Button("Complete Sale");
        saleButton.setPrefSize(100, 50);
        saleButton.relocate(690, 330);
        saleButton.setDisable(true);
        mostProduct = new String[3];
        mostProduct = PopularItem(model);
        Refresh();


        getChildren().addAll(label1, label2, label3, label4, label5, label6, label7, salesField, revenueField,
                dollarpersaleField, cartList, stockList, itemList, resetButton, addButton, removeButton, saleButton);


    }

    public void setMostProduct(String[] swap) {
        for (int k = 0; k < 3; k++) {
            mostProduct[k] = swap[k];
        }
    }

    public Button getAddButton() {
        return addButton;
    }

    public ListView getCartList() {
        return cartList;
    }

    public ListView getItemList() {
        return itemList;
    }

    public ListView getStockList() {
        return stockList;
    }

    public Button getRemoveButton() {
        return removeButton;
    }

    public Button getResetButton() {
        return resetButton;
    }

    public Button getSaleButton() {
        return saleButton;

    }

    public TextField getSalesField() {
        return salesField;
    }

    public TextField getRevenueField() {
        return revenueField;
    }

    public TextField getDollarpersaleField() {
        return dollarpersaleField;
    }

    public String[] PopularItem(ElectronicStore model3) {
        int j = 0;
        int check = 0;
        Product[] temp = new Product[model3.stock.length];
        for (int m = 0; m < model3.stock.length; m++) {
            temp[m] = model3.stock[m];
        }
        String[] newProduct = new String[3];
        while (j < 3) {
            for (int i = 0; i < model3.getCurProducts(); i++) {
                if (temp[check].getSoldQuantity() < temp[i].getSoldQuantity()) {
                    check = i;
                }
            }
            newProduct[j] = temp[check].toString();
            if (check == 0) {
                temp[check] = temp[check + 1];

            } else {
                temp[check] = temp[check - 1];
            }
            j++;
        }
        return newProduct;
    }

    public void updateFields(ElectronicStore update) {
        salesField.setText("0");
        revenueField.setText("0.00");
        dollarpersaleField.setText("N/A");
        Astock = null;
        mostProduct = null;
        model = update;

    }

    public void Refresh() {
        int i = 0;
        int k = 0;
        Astock = new String[model.getCurProducts()];
        for (i = 0; i < model.getCurProducts(); i++) {
            if (model.stock[i].getStockQuantity() > 0) {
                Astock[i] = model.stock[i].toString();
            } else {
                Astock[i] = "";
                i++;
            }
        }
        stockList.setItems(FXCollections.observableArrayList(Astock));

        curstock = new String[model2.getCurProducts()];
        for (i = 0; i < model2.getCurProducts(); i++) {
            curstock[i] = model2.stock[i].toString();
        }
        cartList.setItems(FXCollections.observableArrayList(curstock));
        int index = stockList.getSelectionModel().getSelectedIndex();
        if (index < 0) {
            addButton.setDisable(true);
        } else {
            addButton.setDisable(false);
        }
        index = cartList.getSelectionModel().getSelectedIndex();
        if (index < 0) {
            removeButton.setDisable(true);
        } else {
            removeButton.setDisable(false);

        }
        if (curstock.length == 0) {
            saleButton.setDisable(true);
        } else {
            saleButton.setDisable(false);
        }

        int total = 0;
        for (i = 0; i < model2.getCurProducts(); i++) {
            total += model2.stock[i].getPrice();
        }
        label3.setText("Current Cart:($" + String.valueOf(total) + ")");
        itemList.setItems((FXCollections.observableArrayList(mostProduct)));
    }
}

