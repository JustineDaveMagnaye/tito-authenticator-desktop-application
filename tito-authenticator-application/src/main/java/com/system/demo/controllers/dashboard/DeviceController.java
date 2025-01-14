package com.system.demo.controllers.dashboard;


import com.system.demo.TimeInTimeOutMgtApplication;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class DeviceController implements Initializable{
    public Button deviceNo;
    public Button deviceYes;

    public String employeeNumber;
    public boolean isTimeIn;
    //for sidebar uses
    @FXML
    private Button burgerButton;

    @FXML
    private ImageView burgerIcon;

    @FXML
    private AnchorPane sidebarPane;

    private boolean sidebarVisible = false;

    //for search
    @FXML
    private TextField searchField;

    //for table id
    @FXML
    TableView table;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        TimeInTimeOutMgtApplication app = new TimeInTimeOutMgtApplication();

    }

    public void setData(String data, boolean status) {
        employeeNumber = data;
        isTimeIn = status;
    }
    @FXML
    protected void handleYes(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/AuthenticationPage.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            AuthenticationPageController authenticationPageController = loader.getController();
            authenticationPageController.setData(employeeNumber, isTimeIn);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void handleNo(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/Challenge.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            RandomChallengeController randomChallengeController = loader.getController();
            randomChallengeController.setData(employeeNumber, isTimeIn);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void showAlert(Alert.AlertType alertType, String title, String headerText, String contentText) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        alert.showAndWait();
    }
}
