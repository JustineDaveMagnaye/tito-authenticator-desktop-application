package com.system.demo.controllers.dashboard;

import com.system.demo.appl.model.randomQuestion.randomQuestion;
import com.system.demo.TwoFactorAuthenticationApplication;
import com.system.demo.appl.facade.authentication.AuthenticationFacade;
import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;


public class RandomChallengeController implements Initializable {

    public DatePicker datePicker;
    public Text error;
    @FXML private Text question;
    @FXML private TextField answer;
    @FXML private Button cancel;
    @FXML private Button enter;
    @FXML private Text tries;

    private String employeeNumber;
    private boolean isTimeIn;
    private int chances = 3;
    private String previousQuestion;
    private randomQuestion currentQuestion;

    private final TwoFactorAuthenticationApplication appl = new TwoFactorAuthenticationApplication();
    private final AuthenticationFacade authenticationFacade = appl.getAuthenticationFacade();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadRandomQuestionWithDelay(0.5);
        updateTriesDisplay();
    }


    public void setData(String data, boolean status) {
        this.employeeNumber = data;
        this.isTimeIn = status;
    }


    @FXML
    protected void handleAnswer(MouseEvent event) {
        String userAnswer = "";

        if (currentQuestion.getQuestionType().equals("When is your Birthdate?")) {
            if (datePicker.getValue() != null) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                userAnswer = datePicker.getValue().format(formatter); // Format the selected date
            }
        } else {
            userAnswer = answer.getText();
        }
        if (userAnswer.toLowerCase().equals(currentQuestion.getQuestionAnswer().toLowerCase())) {
            loadNextScene(event, "/views/Phrase.fxml");
        } else {
            chances--;
            error.setText("Incorrect Answer, please try again.");
            error.setFill(Color.RED);
            updateTriesDisplay();
            if (chances <= 0) {
                loadNewQuestion();
            }
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


    private void loadRandomQuestionWithDelay(double seconds) {
        PauseTransition delay = new PauseTransition(Duration.seconds(seconds));
        delay.setOnFinished(event -> loadNewQuestion());
        delay.play();
    }


    private void loadNewQuestion() {
        do {
            currentQuestion = appl.executeRandomChallenge(employeeNumber);
        } while (currentQuestion.getQuestionType().equals(previousQuestion));

        question.setText(currentQuestion.getQuestionType());
        previousQuestion = currentQuestion.getQuestionType();
        chances = 3;
        updateTriesDisplay();

        // Show the appropriate input field based on the question type
        if (currentQuestion.getQuestionType().equals("When is your Birthdate?")) {
            answer.setVisible(false);
            datePicker.setVisible(true);
            datePicker.setValue(null); // Clear previous value if any
        } else {
            answer.setVisible(true);
            datePicker.setVisible(false);
            answer.setText(""); // Clear previous text
        }
    }



    private void updateTriesDisplay() {
        tries.setText("Tries: " + chances);
    }


    private void loadNextScene(MouseEvent event, String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            PhraseController phraseController = loader.getController();
            phraseController.setData(employeeNumber,isTimeIn);
        } catch (IOException e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Scene Loading Failed", "Unable to load the next screen.");
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
