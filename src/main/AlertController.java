package main;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Scanner;

public class AlertController implements Initializable {

    Stage alertStage;

    Database database = new Database();

    @FXML
    Label successAlertLabel, failureAlertLabel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        switch (ShowDoctorsController.sortOfAlert) {

            case ("SUCCESS_DOCTOR_REMOVED"):
                successDoctorRemoved();
                break;

//            case ("SUCCESS_PATIENT_REMOVED"):
//                successPatientRemoved();
//                break;

            case ("WRONG_INTEGER_DOCTOR_REMOVED"):
                wrongIntegerDoctorRemoved();
                break;

//            case ("WRONG_INTEGER_PATIENT_REMOVED"):
//                wrongIntegerPatientRemoved();
//                break;

            case ("WRONG_SALARY_ADDED"):
                wrongSalaryAdded();
                break;

            case ("WRONG_PESEL_ADDED"):
                wrongPeselAdded();
                break;

            case ("ANOTHER_PROBLEM_REMOVED"):
                anotherProblemRemoved();
                break;

            case ("WRONG_INTEGER_DOCTOR_ADD_TO_PATIENT"):
                wrongIntegerDoctorAddToPatient();
                break;

                case("EMPTY"):
                    System.out.println("Empty switch (ShowDoctorsController.sortOfAlert). Do nothing");
                    break;
        }

        switch (ShowPatientsController.sortOfAlert) {

//            case ("SUCCESS_DOCTOR_REMOVED"):
//                successDoctorRemoved();
//                break;

            case ("SUCCESS_PATIENT_REMOVED"):
                successPatientRemoved();
                break;

//            case ("WRONG_INTEGER_DOCTOR_REMOVED"):
//                wrongIntegerDoctorRemoved();
//                break;

            case ("WRONG_INTEGER_PATIENT_REMOVED"):
                wrongIntegerPatientRemoved();
                break;

//            case ("WRONG_SALARY_ADDED"):
//                wrongSalaryAdded();
//                break;

            case ("WRONG_PESEL_ADDED"):
                wrongPeselAdded();
                break;

            case ("ANOTHER_PROBLEM_REMOVED"):
                anotherProblemRemoved();
                break;

            case ("WRONG_INTEGER_DOCTOR_ADD_TO_PATIENT"):
                wrongIntegerDoctorAddToPatient();
                break;

            case("EMPTY"):
                System.out.println("Empty switch (ShowPatientsController.sortOfAlert). Do nothing");
                break;
        }
    }

    public void openAlertWindow() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("fxmlfiles/alertWindow.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        alertStage = new Stage();
        alertStage.setTitle("Alert");
        alertStage.initModality(Modality.APPLICATION_MODAL);//ta linijka robi okno modalne
        alertStage.setResizable(false);//nie pozwalam na zmiane rozmiaru
        alertStage.setScene(scene);
        alertStage.show();
    }

    private void successDoctorRemoved(){

        int index = ShowDoctorsController.numberOfRemovedDoctor;//pobieram numer doktora wybrany w oknie ShowDoctorsController

        try(Scanner scanner = new Scanner(new FileInputStream("doctors.txt"))){

            List<String> rowsTxtFile = new ArrayList<>();

            while (scanner.hasNextLine()){
                String row = scanner.nextLine();
                rowsTxtFile.add(row);
            }

            successAlertLabel.setText("Doctor \n" + rowsTxtFile.get(index - 1) + "\nsuccessfully removed");//dzialalo wczesniej

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void successPatientRemoved(){

        int index = ShowPatientsController.numberOfRemovedPatient;//pobieram numer pacjenta wybrany w oknie ShowPatientsController

        try(Scanner scanner = new Scanner(new FileInputStream("patients.txt"))){

            List<String> rowsTxtFile = new ArrayList<>();

            while (scanner.hasNextLine()){
                String row = scanner.nextLine();
                rowsTxtFile.add(row);
            }

            successAlertLabel.setText("Patient \n" + rowsTxtFile.get(index - 1) + "\nsuccessfully removed");//dzialalo wczesniej

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void wrongIntegerDoctorRemoved() {
        failureAlertLabel.setText("This data is outside the doctors table");
    }

    private void wrongIntegerPatientRemoved() {
        failureAlertLabel.setText("This data is outside the patients table");
    }

    private void wrongSalaryAdded() {
        failureAlertLabel.setText("Salary should be an integer\n (or pesel has 11 elements, but not all are numbers)" );
    }

    private void wrongPeselAdded() {
        failureAlertLabel.setText("Pesel should have 11 digits");
    }

    private void anotherProblemRemoved() {
        failureAlertLabel.setText("Another problem with removing the doctor");
    }

    private void wrongIntegerDoctorAddToPatient() {
        int index = ShowPatientsController.numberOfHisDoctor;//pobieram numer pacjenta wybrany w oknie ShowPatientsController
        failureAlertLabel.setText(index + " is outside the doctors table");
    }
}
