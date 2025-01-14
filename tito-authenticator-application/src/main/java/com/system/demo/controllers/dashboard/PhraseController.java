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
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.util.ResourceBundle;

public class PhraseController implements Initializable {

    public boolean isTimeIn;
    public String employeeNumber;

    @FXML
    private ComboBox<String> firstDropdown;

    @FXML
    private ComboBox<String> secondDropdown;

    @FXML
    private TextField yearInput;

    @FXML
    private Text error;

    TimeRecordDao timeRecordDao = new TimeRecordDaoImpl();
    TimeRecordFacade timeRecordFacade = new TimeRecordFacadeImpl(timeRecordDao);

    // Define the list of words
    private static final String[] WORDS = {
            "Chronicles", "Deuteronomy", "Ecclesiastes", "Esther", "Exodus", "Ezra",
            "Genesis", "Isaiah", "Jeremiah", "Job", "Judith", "Judges", "Joshua",
            "Kings", "Lamentation", "Leviticus", "Maccabees", "Nehemiah", "Numbers",
            "Psalm", "Proverbs", "Ruth", "Sirach", "Song of Songs", "Wisdom", "John",
            "Tobit"
    };

    public void setData(String data, boolean status) {
        employeeNumber = data;
        isTimeIn = status;
    }

    @FXML
    protected void handleEnter(MouseEvent event) {
        try {
            TwoFactorAuthenticationApplication appl = new TwoFactorAuthenticationApplication();
            AuthenticationFacade authenticationFacade = appl.getAuthenticationFacade();

            String phrase = firstDropdown.getValue() + "-" + secondDropdown.getValue() + "-" + yearInput.getText();
            System.out.println(phrase);

            if (authenticationFacade.validateSecretPhrase(employeeNumber, phrase) && isTimeIn && !phrase.equals("null-null-")) {
                phrase = "";
                TimeRecord newRecord = new TimeRecord();
                newRecord.setEmployeeNumber(employeeNumber);
                newRecord.setTimeIn(new Timestamp(System.currentTimeMillis()));
                timeRecordFacade.addTimeInRecord(newRecord);

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/TimeIn.fxml"));
                Parent root = loader.load();
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(new Scene(root));
            } else if (authenticationFacade.validateSecretPhrase(employeeNumber, phrase) && !isTimeIn) {
                phrase = "";
                TimeRecord newRecords = new TimeRecord();
                newRecords.setEmployeeNumber(employeeNumber);
                newRecords.setTimeOut(new Timestamp(System.currentTimeMillis()));
                timeRecordFacade.addTimeOutRecord(newRecords);
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/TimeOut.fxml"));
                Parent root = loader.load();
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(new Scene(root));
            } else {
                error.setText("Incorrect Secret Phrase!");
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
        // Populate both ComboBoxes with the WORDS array values
        firstDropdown.getItems().addAll(WORDS);
        secondDropdown.getItems().addAll(WORDS);
    }
}
