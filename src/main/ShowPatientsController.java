package main;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import main.pojo.Doctor;
import main.pojo.Patient;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

public class ShowPatientsController implements Initializable {

    Stage showPaitentsStage;

    //nowa baza danych, ktora jest polem
    Database database = new Database();

    public static int numberOfRemovedPatient;

    public static int numberOfHisDoctor;

    boolean ascendingFirstNames = false;

    boolean ascendingLastNames = false;

    public static String sortOfAlert = "EMPTY";

    @FXML
    Label displayFirstNamesLabel, displayLastNamesLabel, displayPeselsLabel, displayDoctorsFirstNamesLabel, displayDoctorsLastNamesLabel, displayDoctorsPeselsLabel, numberRemovedLabel;

    @FXML
    TextField firstNameInputText, lastNameInputText, peselInputText, numberOfHisDoctorInputText, numberRemovedInputText;

    @FXML
    Button addPatientButton, removePatientButton, sortByFirstNameButton, sortByLastNameButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        //zapis z pliku txt do bazy danych ArrayList
        saveFromTxtFileToDatabase();

        //wyswietla obiekty w tym oknie, korzystajac z bazy danych ArrayList
        displayTableInWindow();
    }

    //wyswietla obiekty w tym oknie, korzystajac z bazy danych ArrayList
    public void displayTableInWindow(){

        try {
            String patientsFirstNames = "";
            String patientsLastNames = "";
            String patientsPesels = "";
            String patientsDoctorFirstName = "";
            String patientsDoctorLastName = "";
            String patientsDoctorPesel = "";

            for (Patient pat : database.getPatients()) {
                patientsFirstNames = patientsFirstNames + pat.getFirstName() + "\n";
                patientsLastNames = patientsLastNames + pat.getLastName() + "\n";
                patientsPesels = patientsPesels + pat.getPesel() + "\n";
                patientsDoctorFirstName = patientsDoctorFirstName + pat.getHisDoctorFirstName() + "\n";
                patientsDoctorLastName = patientsDoctorLastName + pat.getHisDoctorLastName() + "\n";
                patientsDoctorPesel = patientsDoctorPesel + pat.getHisDoctorPesel() + "\n";
            }

            displayFirstNamesLabel.setText(patientsFirstNames);
            displayLastNamesLabel.setText(patientsLastNames);
            displayPeselsLabel.setText(patientsPesels);
//            displayDoctorsFirstNamesLabel.setText(patientsDoctorFirstName);
//            displayDoctorsLastNamesLabel.setText(patientsDoctorLastName);
            displayDoctorsPeselsLabel.setText(patientsDoctorPesel);

        }catch (Exception e){
            System.out.println("Lack of patients");
        }
    }

    public void openShowPatientsWindow() throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("fxmlfiles/showPatientsWindow.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        showPaitentsStage = new Stage();
        showPaitentsStage.setTitle("Show patients");
        showPaitentsStage.initModality(Modality.APPLICATION_MODAL);//ta linijka robi okno modalne
        showPaitentsStage.setResizable(false);//nie pozwalam na zmiane rozmiaru
        showPaitentsStage.setScene(scene);
        showPaitentsStage.show();
    }

    //dodaje nowy wiersz do bazy danych ArrayList i do pliku txt oraz zrestartuj okno
    public void addRowToDatabaseAndTxtFileAndRestartWindow() throws IOException {

        System.out.println("1");

        try {
            System.out.println("2");
            if (peselInputText.getText().length() != 11) {

                throw new IllegalArgumentException();
            }
            System.out.println("3");
            Long.parseLong(peselInputText.getText());
            System.out.println("4");
            numberOfHisDoctor = Integer.parseInt(numberOfHisDoctorInputText.getText());
            System.out.println("5");

            //pobieram liste lekarzy jako ArrayList i znajduje jej dlugosc
            ShowDoctorsController showDoctorsController = new ShowDoctorsController();
            List<Doctor> listOfDoctors = showDoctorsController.saveFromTxtFileToDatabase();
            System.out.println(listOfDoctors);
            int ilosc = listOfDoctors.size();
            System.out.println("Ilosc lekarzy w ArrayList: " + ilosc);

            if (numberOfHisDoctor >= 1 && ilosc <= ilosc) {
                System.out.println("6");
//                System.out.println(database.getDoctors().get(numberOfHisDoctor - 1));
//
//                //tworze nowego pacjenta korzystajac z danych wprowadzonych do pol tekstowych TextField
//                Patient newPatient = new Patient(firstNameInputText.getText(), lastNameInputText.getText(), peselInputText.getText(), database.getDoctors().get(numberOfHisDoctor - 1));

//                //tworze nowego pacjenta korzystajac z danych wprowadzonych do pol tekstowych TextField
//                Patient newPatient = new Patient(firstNameInputText.getText(), lastNameInputText.getText(), peselInputText.getText(), database.getDoctors().get(numberOfHisDoctor - 1));


                try(Scanner scanner = new Scanner(new FileInputStream("doctors.txt"))){
                    System.out.println("7");
                    List<String> doctorsFirstNamesTxtFile = new ArrayList<>();
                    List<String> doctorsLastNamesTxtFile = new ArrayList<>();
                    List<String> doctorsPeselsTxtFile = new ArrayList<>();

                    System.out.println("8");

                    while (scanner.hasNext()){

                        String doctorFirstName = scanner.next();
                        doctorsFirstNamesTxtFile.add(doctorFirstName);

                        String doctorLastName = scanner.next();
                        doctorsLastNamesTxtFile.add(doctorLastName);

                        String doctorSalary = scanner.next();//pomijam pensje przy dodawaniu do listy

                        String doctorPesel = scanner.next();
                        doctorsPeselsTxtFile.add(doctorPesel);
                    }

                    System.out.println("9");
                    System.out.println(doctorsFirstNamesTxtFile);
                    System.out.println(doctorsLastNamesTxtFile);
                    System.out.println(doctorsPeselsTxtFile);

//                    System.out.println("Doctor \n" + rowsTxtFile.get(numberOfHisDoctor - 1) + "\nsuccessfully added to this patient");//dzialalo wczesniej

                    System.out.println("10");
                //tworze nowego pacjenta korzystajac z danych wprowadzonych do pol tekstowych TextField
                Patient newPatient = new Patient(firstNameInputText.getText(), lastNameInputText.getText(), peselInputText.getText(), doctorsFirstNamesTxtFile.get(numberOfHisDoctor - 1), doctorsLastNamesTxtFile.get(numberOfHisDoctor - 1), doctorsPeselsTxtFile.get(numberOfHisDoctor - 1));

                    System.out.println("11");

                //czyszcze kazdego pola po dodaniu elementu do tabeli
                firstNameInputText.clear();
                lastNameInputText.clear();
                peselInputText.clear();
                numberOfHisDoctorInputText.clear();

                    System.out.println("12");

                //dodaje pacjenta do bazy danych
                database.getPatients().add(newPatient);

                    System.out.println("13");
                //zapis do pliku txt
                saveFromDatabaseToTxtFile();

                    System.out.println("14");
                //zamyka to okno i ponownie je otwiera
                closeAndOpenThisWindow();//zawieszalo sie jak po dodaniu nowego pacjenta chcialem wyswietlic tabele poprzez displayTableInWindow();

                }//try Scanner
                catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }//if
        }//try
        catch (NumberFormatException e){

            sortOfAlert = "WRONG_PESEL_ADDED";

            //wyskakuje okienko z alertem
            AlertController alertController = new AlertController();
            alertController.openAlertWindow();

            //zamyka to okno
            showPaitentsStage = (Stage) addPatientButton.getScene().getWindow();//pobierz mi okno, w ktorym osadzona jest scena
            showPaitentsStage.close();

        }
        catch (IllegalArgumentException e) {

            sortOfAlert = "WRONG_PESEL_ADDED";

            //wyskakuje okienko z alertem
            AlertController alertController = new AlertController();
            alertController.openAlertWindow();

            //zamyka to okno
            showPaitentsStage = (Stage) addPatientButton.getScene().getWindow();//pobierz mi okno, w ktorym osadzona jest scena
            showPaitentsStage.close();
        }
//        catch (IndexOutOfBoundsException e){
//
//            sortOfAlert = "WRONG_INTEGER_DOCTOR_ADD_TO_PATIENT";
//
//            //wyskakuje okienko z alertem
//            AlertController alertController = new AlertController();
//            alertController.openAlertWindow();
//
//            //zamyka to okno
//            showPaitentsStage = (Stage) addPatientButton.getScene().getWindow();//pobierz mi okno, w ktorym osadzona jest scena
//            showPaitentsStage.close();
//
//        }
    }

    //usuwam wiersz z bazy danych ArrayList i z pliku txt oraz restartuje okno
    public void removeRowFromDatabaseAndTxtFileAndRestartWindow() throws IOException {

        try {
            numberOfRemovedPatient = Integer.parseInt(numberRemovedInputText.getText());

            if (numberOfRemovedPatient >= 1 && numberOfRemovedPatient <= database.getPatients().size()) {

                sortOfAlert = "SUCCESS_PATIENT_REMOVED";

                //wyskakuje okienko z alertem
                AlertController alertController = new AlertController();
                alertController.openAlertWindow();

                database.getPatients().remove(numberOfRemovedPatient - 1);

                //zapis z bazy danych ArrayList do pliku txt
                saveFromDatabaseToTxtFile();

                //zamyka to okno
                showPaitentsStage = (Stage) addPatientButton.getScene().getWindow();//pobierz mi okno, w ktorym osadzona jest scena
                showPaitentsStage.close();

            } else {

                throw new NumberFormatException();
            }
        }

        catch (NumberFormatException e) {

            sortOfAlert = "WRONG_INTEGER_PATIENT_REMOVED";

            //wyskakuje okienko z alertem
            AlertController alertController = new AlertController();
            alertController.openAlertWindow();

            //zamyka to okno
            showPaitentsStage = (Stage) addPatientButton.getScene().getWindow();//pobierz mi okno, w ktorym osadzona jest scena
            showPaitentsStage.close();
        }

        catch (Exception e){

            sortOfAlert = "ANOTHER_PROBLEM_REMOVED";

            //wyskakuje okienko z informacja o tym, ze jest inny blad
            AlertController alertController = new AlertController();
            alertController.openAlertWindow();

            //zamyka to okno
            showPaitentsStage = (Stage) addPatientButton.getScene().getWindow();//pobierz mi okno, w ktorym osadzona jest scena
            showPaitentsStage.close();
        }
    }

    //zapis z pliku txt do bazy danych ArrayList
    public List<Patient> saveFromTxtFileToDatabase(){

        //odczyt wszystkich slow z pliku txt
        try(Scanner scanner = new Scanner(new FileInputStream("patients.txt"))){

            while (scanner.hasNext()) {
                String firstName = scanner.next();
                String lastName = scanner.next();
                String pesel = scanner.next();
                String hisDoctorFirstName  = scanner.next();
                String hisDoctorLastName  = scanner.next();
                String hisDoctorPesel  = scanner.next();

/////////// ZROBIC OBSLUGE WYJATKU NA NUMER DOKTORA ////////////////////////////////////////

                //zapis obiektu do bazy danych ArrayList
                database.getPatients().add(new Patient(firstName, lastName, pesel, hisDoctorFirstName, hisDoctorLastName, hisDoctorPesel));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return database.getPatients();
    }

    //zapisa z bazy danych ArrayList do pliku txt
    public void saveFromDatabaseToTxtFile(){
        try(FileWriter fileWriter = new FileWriter("patients.txt")) {// proba otwarcia pliku do zapisywania. Jesli nie istrnieje, to go utworzy, a jesli istnieje to go nadpisze. Chyba, ze plik bedzie tylko do odczytu, to rzuci wyjatek NullPointerException ze nie mozna nadpisac

            for (Patient patient : database.getPatients()) {
                fileWriter.write(patient.getFirstName() + " ");
                fileWriter.write(patient.getLastName() + " ");
                fileWriter.write(patient.getPesel() + " ");
                fileWriter.write(patient.getHisDoctorFirstName() + " ");
                fileWriter.write(patient.getHisDoctorLastName() + " ");
                fileWriter.write(patient.getHisDoctorPesel() + "\n");
            }
        }catch (IOException ex){
            System.out.println("File access problem");// to sie wyswietli gdy plik bedzie tylko do odczytu
        }
    }

    //sortuje baze danych ArrayList, plik txt i tabelkie po imieniu
    public void sortByFirstName(){

        if(ascendingFirstNames == false) {

            //sortuje baze danych ArrayList po nazwiskach i zapisuje do nowej listy
            List<Patient> patientsSortedByFirstName = database.getPatients().stream()
                    .sorted(Comparator.comparing(Patient::getFirstName))//mozna .sorted((o1, o2) -> o1.getFirstName().compareTo(o2.getFirstName()))
                    .collect(Collectors.toList());

            //czysci stara nieposortowana baze danych
            database.getPatients().clear();

            //dodaje posotrowana liste do bazy danych
            database.getPatients().addAll(patientsSortedByFirstName);

            //zapis do pliku txt
            saveFromDatabaseToTxtFile();

            //wyswietla obiekty w tym oknie, korzystajac z bazy danych ArrayList
            displayTableInWindow();

            ascendingFirstNames = true;
        }

        else if (ascendingFirstNames == true){
            //sortuje baze danych ArrayList po nazwiskach i zapisuje do nowej listy
            List<Patient> patientsSortedByFirstName = database.getPatients().stream()
                    .sorted(Comparator.comparing(Patient::getFirstName))//mozna .sorted((o1, o2) -> o1.getFirstName().compareTo(o2.getFirstName()))
                    .collect(Collectors.toList());

            //odwraca kolejnosc sortowania zeby bylo malejaco
            Collections.reverse(patientsSortedByFirstName);

            //czysci stara nieposortowana baze danych
            database.getPatients().clear();

            //dodaje posotrowana liste do bazy danych
            database.getPatients().addAll(patientsSortedByFirstName);

            //zapis do pliku txt
            saveFromDatabaseToTxtFile();

            //wyswietla obiekty w tym oknie, korzystajac z bazy danych ArrayList
            displayTableInWindow();

            ascendingFirstNames = false;
        }
    }

    //sortuje baze danych ArrayList, plik txt i tabelkie po nazwisku
    public void sortByLastName(){

        if(ascendingLastNames == false) {

            //sortuje baze danych ArrayList po nazwiskach i zapisuje do nowej listy
            List<Patient> patientsSortedByLastName = database.getPatients().stream()
                    .sorted(Comparator.comparing(Patient::getLastName))//mozna .sorted((o1, o2) -> o1.getLastName().compareTo(o2.getLastName()))
                    .collect(Collectors.toList());

            //czysci stara nieposortowana baze danych
            database.getPatients().clear();

            //dodaje posotrowana liste do bazy danych
            database.getPatients().addAll(patientsSortedByLastName);

            //zapis do pliku txt
            saveFromDatabaseToTxtFile();

            //wyswietla obiekty w tym oknie, korzystajac z bazy danych ArrayList
            displayTableInWindow();

            ascendingLastNames = true;
        }

        else if (ascendingLastNames == true){
            //sortuje baze danych ArrayList po nazwiskach i zapisuje do nowej listy
            List<Patient> patientsSortedByLastName = database.getPatients().stream()
                    .sorted(Comparator.comparing(Patient::getLastName))//mozna .sorted((o1, o2) -> o1.getLastName().compareTo(o2.getLastName()))
                    .collect(Collectors.toList());

            //odwraca kolejnosc sortowania zeby bylo malejaco
            Collections.reverse(patientsSortedByLastName);

            //czysci stara nieposortowana baze danych
            database.getPatients().clear();

            //dodaje posotrowana liste do bazy danych
            database.getPatients().addAll(patientsSortedByLastName);

            //zapis do pliku txt
            saveFromDatabaseToTxtFile();

            //wyswietla obiekty w tym oknie, korzystajac z bazy danych ArrayList
            displayTableInWindow();

            ascendingLastNames = false;
        }
    }

    //zamyka to okno i ponownie je otwiera
    public void closeAndOpenThisWindow() throws IOException {

        //zamyka to onko
        showPaitentsStage = (Stage) addPatientButton.getScene().getWindow();//pobierz mi okno, w ktorym osadzona jest scena
        showPaitentsStage.close();

        //po czym ponownie je otwiera i ono ma wczytana juz nowa tebelke po wprowadzonych zmianach
        ShowPatientsController showPatientsController = new ShowPatientsController();
        showPatientsController.openShowPatientsWindow();
    }
}
