package main;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainWindowController implements Initializable {

    private Stage stage;

    void setStage(Stage stage){
        this.stage = stage;
    }

    //musze pokazac ze kontroller bedzie korzystac z tych elementow. Nazwy maja byc takie jak w pliku fxml
    @FXML
    Label welcomeLabel;//tu wpisuje id ktore jest w fxml

    @FXML
    Button showDoctorsButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {//ona pozwala przed uruchomieniem okna nadac poczatkowe wartosci, pola
        welcomeLabel.setText("Welcome to our hospital");//domyslny tekst w welcomeLabel

        File doctors = new File("doctors.txt");
        if(!doctors.exists()) {
            try {
                doctors.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        File patients = new File("patients.txt");
        if(!patients.exists()) {
            try {
                patients.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void openWindowShowDoctors() throws IOException{
        welcomeLabel.setText("You have chosen to show all doctors");
        ShowDoctorsController showDoctorsController = new ShowDoctorsController();
        showDoctorsController.openShowDoctorsWindow();
    }

    public void openWindowShowPatients() throws IOException{
        welcomeLabel.setText("You have chosen to show all patients");
        ShowPatientsController showPatientsController = new ShowPatientsController();
        showPatientsController.openShowPatientsWindow();
    }

    public static void closeProgram(){
        Platform.exit();//zamyka okno glowne i potomne
        System.exit(0);//zamyka program
    }
}
