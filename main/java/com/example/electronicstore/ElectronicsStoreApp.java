package com.example.electronicstore;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.event.*;
import javafx.scene.input.*;
import javafx.collections.ObservableList;


public class ElectronicsStoreApp extends Application {
    ElectronicStore model;
    ElectronicStoreView view;
    int selectedIndex;
    int num = 0;
    int i=0;




    public void start(Stage primaryStage) {
        model = ElectronicStore.createStore();
        view = new ElectronicStoreView(model);

        view.getAddButton().setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent actionEvent){
                handleAdd();
            }
        });

        view.getRemoveButton().setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent actionEvent) {
                handleRemove();

            }
        });
        view.getSaleButton().setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent actionEvent) {
                completeSale();
            }
        });

        view.getResetButton().setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent actionEvent) {
                handleReset();
            }
        });
        view.getStockList().setOnMouseReleased(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent mouseEvent) {
                handleProduct();
            }
        });
        view.getCartList().setOnMouseReleased(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent mouseEvent) {
                handleCart();
            }
        });

        primaryStage.setTitle("Electronic Store Application");
        primaryStage.setResizable(false);
        primaryStage.setScene(new Scene(view, 800, 400));
        primaryStage.show();
    }

    public void handleAdd() {

        selectedIndex = view.getStockList().getSelectionModel().getSelectedIndex();
        view.model2.addProduct(view.model.stock[selectedIndex]);
        model.sellProducts(selectedIndex,1);
        view.Refresh();

    }
    public void handleProduct(){
        view.Refresh();
    }
    public void handleReset(){
        ElectronicStore update = ElectronicStore.createStore();
        model = update;
        view.updateFields(model);
        view.Refresh();
    }
    public void handleCart(){
        view.Refresh();
    }

    public void handleRemove(){
        int selectedIndex2 = view.getCartList().getSelectionModel().getSelectedIndex();
        for(int i=0; i<view.model2.getCurProducts(); i++){
            if (view.model.stock[i] == view.model2.stock[selectedIndex2]){
                view.model.buyUnits(i,1);
            }
        }
        view.model2.deleteProduct(selectedIndex2);
        view.Refresh();
    }
    public void completeSale(){
        num++;
        view.getDollarpersaleField().setText("0.0");
        view.label3.setText("Current Cart");
        view.getSalesField().setText(String.valueOf(num));
        view.getRevenueField().setText(String.valueOf(view.model.revenue));
        view.getDollarpersaleField().setText(String.valueOf(view.model.revenue/num));
        int curProduct = view.model2.getCurProducts();
        view.setMostProduct(view.PopularItem(view.model));
        for(int i = 0; i<curProduct; i++){
            view.model2.deleteProduct(0);
        }
        view.Refresh();
        view.PopularItem(view.model);

    }


    public static void main(String[] args){
        launch(args);
    }
}
