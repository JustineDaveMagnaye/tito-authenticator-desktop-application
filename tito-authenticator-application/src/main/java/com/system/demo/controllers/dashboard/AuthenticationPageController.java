package com.system.demo.controllers.dashboard;


import com.system.demo.TwoFactorAuthenticationApplication;
import com.system.demo.appl.facade.authentication.AuthenticationFacade;
import com.system.demo.appl.facade.timerecord.TimeRecordFacade;
import com.system.demo.appl.facade.timerecord.impl.TimeRecordFacadeImpl;
import com.system.demo.appl.model.timeRecord.TimeRecord;
import com.system.demo.data.timerecord.TimeRecordDao;
import com.system.demo.data.timerecord.impl.TimeRecordDaoImpl;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.util.ResourceBundle;

public class AuthenticationPageController implements Initializable{

    public TextField authenticationCode;
    public boolean isTimeIn;
    public String employeeNumber;
    public Button cancel;
    public Button enter;
    public Text error;

    TimeRecordDao timeRecordDao = new TimeRecordDaoImpl();

    TimeRecordFacade timeRecordFacade = new TimeRecordFacadeImpl(timeRecordDao);

    public void setData(String data, boolean status) {
        employeeNumber = data;
        isTimeIn = status;
    }

    @FXML
    protected void handleAuthentication(MouseEvent event) {
        try {
            TwoFactorAuthenticationApplication appl = new TwoFactorAuthenticationApplication();
            AuthenticationFacade authenticationFacade = appl.getAuthenticationFacade();

            if(authenticationFacade.validateAuthenticatorCode(employeeNumber,authenticationCode.getText()) && isTimeIn){
                TimeRecord newRecord = new TimeRecord();
                newRecord.setEmployeeNumber(employeeNumber);
                newRecord.setTimeIn(new Timestamp(System.currentTimeMillis()));
                timeRecordFacade.addTimeInRecord(newRecord);
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/TimeIn.fxml"));
                Parent root = loader.load();
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(new Scene(root));
            } else if(authenticationFacade.validateAuthenticatorCode(employeeNumber,authenticationCode.getText()) && !isTimeIn) {
                TimeRecord newRecords = new TimeRecord();
                newRecords.setEmployeeNumber(employeeNumber);
                newRecords.setTimeOut(new Timestamp(System.currentTimeMillis()));
                timeRecordFacade.addTimeOutRecord(newRecords);
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/TimeOut.fxml"));
                Parent root = loader.load();
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(new Scene(root));
            } else {
                error.setText("Incorrect Authentication Code!");
                error.setFill(Color.RED);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    protected void handleCancel(MouseEvent event) {
        try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/MainView.fxml"));
                Parent root = loader.load();
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(new Scene(root));
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
