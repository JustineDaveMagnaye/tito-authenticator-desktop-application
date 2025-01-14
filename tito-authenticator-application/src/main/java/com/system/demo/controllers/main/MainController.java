package com.system.demo.controllers.main;

import com.system.demo.appl.facade.timerecord.TimeRecordFacade;
import com.system.demo.appl.facade.timerecord.impl.TimeRecordFacadeImpl;
import com.system.demo.appl.model.employee.Employee;
import com.system.demo.appl.model.timeRecord.TimeRecord;
import com.system.demo.controllers.dashboard.AuthenticationPageController;
import com.system.demo.controllers.dashboard.RandomChallengeController;
import com.system.demo.data.employee.dao.EmployeeDao;
import com.system.demo.data.employee.dao.impl.EmployeeDaoImpl;
import com.system.demo.data.timerecord.TimeRecordDao;
import com.system.demo.data.timerecord.impl.TimeRecordDaoImpl;
import com.system.demo.controllers.dashboard.DeviceController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainController {


    public Text error;
    @FXML
    private TextField employeeNumberField;


    EmployeeDao employeeFacade = new EmployeeDaoImpl();

    TimeRecordDao timeRecordDao = new TimeRecordDaoImpl();

    TimeRecordFacade timeRecordFacade = new TimeRecordFacadeImpl(timeRecordDao);


    @FXML
    protected void timeInButtonAction(ActionEvent event) {
        String employeeNumber = employeeNumberField.getText();
        try {
            Employee currentEmployee = employeeFacade.getEmployeeById(employeeNumber);
            if(currentEmployee == null){
                error.setText("Employee Number does not exist!");
                error.setFill(Color.RED);
            } else {
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                Date currentDate = new Date();
                String currentDateString = format.format(currentDate);
                TimeRecord newRecord = new TimeRecord();
                newRecord.setEmployeeNumber(employeeNumber);
                newRecord.setCreatedAt(currentDateString);
                TimeRecord check = timeRecordDao.timeRecordExists(newRecord);
                if(check != null && check.getCreatedAt().equals(currentDateString)){
                    error.setText("You have already timed in today.");
                    error.setFill(Color.RED);
                } else {
                    openTimeInDashboardWindow(event, currentEmployee);
                }
            }
        } catch (Exception ex) {
            showAlert("Error", "An error occurred during login: " + ex.getMessage(), Alert.AlertType.ERROR);
            ex.printStackTrace();
        }
    }
    @FXML
    protected void timeOutButtonAction(ActionEvent event) {
        String employeeNumber = employeeNumberField.getText();
        try {
            Employee currentEmployee = employeeFacade.getEmployeeById(employeeNumber);
            if(currentEmployee == null){
                error.setText("Employee Number does not exist!");
                error.setFill(Color.RED);
            } else {
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                Date currentDate = new Date();
                String currentDateString = format.format(currentDate);
                TimeRecord newRecord = new TimeRecord();
                newRecord.setEmployeeNumber(employeeNumber);
                newRecord.setCreatedAt(currentDateString);
                TimeRecord check = timeRecordDao.timeRecordExists(newRecord);
                if(check != null && check.getTimeOut() != null){
                    error.setText("You have already timed out today.");
                    error.setFill(Color.RED);
                } else if(check == null){
                    error.setText("You haven't timed in today.");
                    error.setFill(Color.RED);
                } else {
                    openTimeOutDashboardWindow(event, currentEmployee);
                }

            }
        } catch (Exception ex) {
            showAlert("Error", "An error occurred during login: " + ex.getMessage(), Alert.AlertType.ERROR);
            ex.printStackTrace();
        }
    }
    private void showAlert(String title, String content, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void openTimeInDashboardWindow(ActionEvent event, Employee currentEmployee) {
        try {
            Stage previousStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            previousStage.close();

            Stage dashboardStage = new Stage();
            FXMLLoader loader = new FXMLLoader();
            if(currentEmployee.getLoginId() == null){
                loader.setLocation(getClass().getResource("/views/Challenge.fxml"));
                Parent root = loader.load();
                Scene scene = new Scene(root);
                dashboardStage.setScene(scene);
                RandomChallengeController randomChallengeController = loader.getController();

                randomChallengeController.setData(employeeNumberField.getText(), true);
            } else {
                loader.setLocation(getClass().getResource("/views/AuthenticationPage.fxml"));
                Parent root = loader.load();
                Scene scene = new Scene(root);
                dashboardStage.setScene(scene);
                AuthenticationPageController authenticationPageController = loader.getController();

                authenticationPageController.setData(employeeNumberField.getText(), true);
            }

            dashboardStage.initStyle(StageStyle.UNDECORATED);

            dashboardStage.show();
        } catch (IOException e) {
            System.err.println("Error opening dashboard window: " + e.getMessage());
            e.printStackTrace();
        }
    }
    private void openTimeOutDashboardWindow(ActionEvent event, Employee currentEmployee) {
        try {
            Stage previousStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            previousStage.close();
            Stage dashboardStage = new Stage();
            FXMLLoader loader = new FXMLLoader();
            if(currentEmployee.getLoginId() == null){
                loader.setLocation(getClass().getResource("/views/Challenge.fxml"));
                Parent root = loader.load();
                Scene scene = new Scene(root);
                dashboardStage.setScene(scene);
                RandomChallengeController randomChallengeController = loader.getController();

                randomChallengeController.setData(employeeNumberField.getText(), false);
            } else {
                loader.setLocation(getClass().getResource("/views/AuthenticationPage.fxml"));
                Parent root = loader.load();
                Scene scene = new Scene(root);
                dashboardStage.setScene(scene);
                AuthenticationPageController authenticationPageController = loader.getController();

                authenticationPageController.setData(employeeNumberField.getText(), false);
            }
            dashboardStage.initStyle(StageStyle.UNDECORATED);

            dashboardStage.show();
        } catch (IOException e) {
            System.err.println("Error opening dashboard window: " + e.getMessage());
            e.printStackTrace();
        }
    }



    @FXML
    protected void quitApp(MouseEvent event) {
        try {
            Stage previousStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            previousStage.close();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void handleForgotPsw(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/ForgotPsw.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
