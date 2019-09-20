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

import java.io.*;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

public class ShowDoctorsController implements Initializable {

    Stage showDoctorsStage;

    //nowa baza danych, ktora jest polem
    Database database = new Database();

    public static int numberOfRemovedDoctor;

    public static String sortOfAlert = "EMPTY";

    boolean ascendingFirstNames = false;

    boolean ascendingLastNames = false;

    boolean ascendingSalaries = false;

    @FXML
    Label displayFirstNamesLabel, displayLastNamesLabel, displaySalariesLabel, displayPeselsLabel, numberRemovedLabel;

    @FXML
    TextField firstNameInputText, lastNameInputText, salaryInputText, peselInputText, numberRemovedInputText;

    @FXML
    Button addDoctorButton, removeDoctorButton, sortByFirstNameButton, sortByLastNameButton, sortBySalaryButton;

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
            String doctorsFirstNames = "";
            String doctorsLastNames = "";
            String doctorsSalaries = "";
            String doctorsPesels = "";

            for (Doctor doc : database.getDoctors()) {
                doctorsFirstNames = doctorsFirstNames + doc.getFirstName() + "\n";
                doctorsLastNames = doctorsLastNames + doc.getLastName() + "\n";
                doctorsSalaries = doctorsSalaries + doc.getSalary() + "\n";
                doctorsPesels = doctorsPesels + doc.getPesel() + "\n";
            }

            displayFirstNamesLabel.setText(doctorsFirstNames);
            displayLastNamesLabel.setText(doctorsLastNames);
            displaySalariesLabel.setText(doctorsSalaries);
            displayPeselsLabel.setText(doctorsPesels);

        }catch (Exception e){
            System.out.println("Lack of doctors");
        }
    }

    public void openShowDoctorsWindow() throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("fxmlfiles/showDoctorsWindow.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        showDoctorsStage = new Stage();
        showDoctorsStage.setTitle("Show doctors");
        showDoctorsStage.initModality(Modality.APPLICATION_MODAL);//ta linijka robi okno modalne
        showDoctorsStage.setResizable(false);//nie pozwalam na zmiane rozmiaru
        showDoctorsStage.setScene(scene);
        showDoctorsStage.show();
    }

    //dodaje nowy wiersz do bazy danych ArrayList i do pliku txt oraz zrestartuj okno
    public void addRowToDatabaseAndTxtFileAndRestartWindow() throws IOException {

        try {

            Integer.parseInt(salaryInputText.getText());

            if (peselInputText.getText().length() != 11) {
                throw new IllegalArgumentException();
            }

            Long.parseLong(peselInputText.getText());

        //tworze nowego doktora korzystajac z danych wprowadzonych do pol tekstowych TextField
        Doctor newDoctor = new Doctor(firstNameInputText.getText(), lastNameInputText.getText(), Integer.parseInt(salaryInputText.getText()), peselInputText.getText());

        //czyszcze kazdego pola po dodaniu elementu do tabeli
        firstNameInputText.clear();
        lastNameInputText.clear();
        salaryInputText.clear();
        peselInputText.clear();

        //dodaje doktora do bazy danych
        database.getDoctors().add(newDoctor);

        //zapis do pliku txt
        saveFromDatabaseToTxtFile();

        //zamyka to okno i ponownie je otwiera
        closeAndOpenThisWindow();//zawieszalo sie jak po dodaniu nowego doktora chcialem wyswietlic tabele poprzez displayTableInWindow();

        }
        catch (NumberFormatException e){

            sortOfAlert = "WRONG_SALARY_ADDED";

            //wyskakuje okienko z alertem
            AlertController alertController = new AlertController();
            alertController.openAlertWindow();

            //zamyka to okno
            showDoctorsStage = (Stage) addDoctorButton.getScene().getWindow();//pobierz mi okno, w ktorym osadzona jest scena
            showDoctorsStage.close();

        }
        catch (IllegalArgumentException e) {

            sortOfAlert = "WRONG_PESEL_ADDED";

            //wyskakuje okienko z alertem
            AlertController alertController = new AlertController();
            alertController.openAlertWindow();

            //zamyka to okno
            showDoctorsStage = (Stage) addDoctorButton.getScene().getWindow();//pobierz mi okno, w ktorym osadzona jest scena
            showDoctorsStage.close();
        }
    }

    //usuwam wiersz z bazy danych ArrayList i z pliku txt oraz restartuje okno
    public void removeRowFromDatabaseAndTxtFileAndRestartWindow() throws IOException {

        try {
            numberOfRemovedDoctor = Integer.parseInt(numberRemovedInputText.getText());

            if (numberOfRemovedDoctor >= 1 && numberOfRemovedDoctor <= database.getDoctors().size()) {

                sortOfAlert = "SUCCESS_DOCTOR_REMOVED";

                //wyskakuje okienko z alertem
                AlertController alertController = new AlertController();
                alertController.openAlertWindow();

                //pobieram liste pacjentow jako ArrayList i znajduje jej dlugosc
                ShowPatientsController showPatientsController = new ShowPatientsController();
                List<Patient> listOfPatients = showPatientsController.saveFromTxtFileToDatabase();
                System.out.println(listOfPatients);
                int ilosc = listOfPatients.size();
                System.out.println("Ilosc pacjentow w ArrayList: " + ilosc);

                //usuwam z listy pacjentow ArrayList pola doczace usunietego doktora
                for (Patient patient : listOfPatients) {
                    //jesli maja te same pesele, to dla danego pacjenta zastap pola ktore dotycza tego doktora poprzez removed
                    if(patient.getHisDoctorPesel().equals(database.getDoctors().get(numberOfRemovedDoctor - 1).getPesel())) {
                        patient.setHisDoctorFirstName("removed");
                        patient.setHisDoctorLastName("removed");
                        patient.setHisDoctorPesel("removed");
                    }
                }

                //zapisuje liste pacjentow z ArrayList do patients.txt
                showPatientsController.saveFromDatabaseToTxtFile();

                database.getDoctors().remove(numberOfRemovedDoctor - 1);

                //zapis z bazy danych ArrayList do pliku doctors.txt
                saveFromDatabaseToTxtFile();

                //zamyka to okno
                showDoctorsStage = (Stage) addDoctorButton.getScene().getWindow();//pobierz mi okno, w ktorym osadzona jest scena
                showDoctorsStage.close();
            }else {
                throw new NumberFormatException();//rzuci go jesli jest numer, ale spoza tabeli
            }
        }
        catch (NumberFormatException e) {

            sortOfAlert = "WRONG_INTEGER_DOCTOR_REMOVED";

            //wyskakuje okienko z alertem
            AlertController alertController = new AlertController();
            alertController.openAlertWindow();

            //zamyka to okno
            showDoctorsStage = (Stage) addDoctorButton.getScene().getWindow();//pobierz mi okno, w ktorym osadzona jest scena
            showDoctorsStage.close();

        }

        catch (Exception e){

            sortOfAlert = "ANOTHER_PROBLEM_REMOVED";

            //wyskakuje okienko z informacja o tym, ze jest inny blad
            AlertController alertController = new AlertController();
            alertController.openAlertWindow();

            //zamyka to okno
            showDoctorsStage = (Stage) addDoctorButton.getScene().getWindow();//pobierz mi okno, w ktorym osadzona jest scena
            showDoctorsStage.close();

        }
    }

    //zapis z pliku txt do bazy danych ArrayList
    public List<Doctor> saveFromTxtFileToDatabase(){

        //odczyt wszystkich slow z pliku txt
        try(Scanner scanner = new Scanner(new FileInputStream("doctors.txt"))){

            while (scanner.hasNext()) {
                String firstName = scanner.next();
                String lastName = scanner.next();
                String salary = scanner.next();
                String pesel = scanner.next();

                //zapis obiektu do bazy danych ArrayList
                database.getDoctors().add(new Doctor(firstName, lastName, Integer.parseInt(salary), pesel));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return database.getDoctors();
    }

    //zapisa z bazy danych ArrayList do pliku txt
    public void saveFromDatabaseToTxtFile(){
        try(FileWriter fileWriter = new FileWriter("doctors.txt")) {// proba otwarcia pliku do zapisywania. Jesli nie istrnieje, to go utworzy, a jesli istnieje to go nadpisze. Chyba, ze plik bedzie tylko do odczytu, to rzuci wyjatek NullPointerException ze nie mozna nadpisac

            for (Doctor doctor : database.getDoctors()) {
                fileWriter.write(doctor.getFirstName() + " ");
                fileWriter.write(doctor.getLastName() + " ");
                fileWriter.write(doctor.getSalary() + " ");
                fileWriter.write(doctor.getPesel() + "\n");
            }
        }catch (IOException ex){
            System.out.println("File access problem");// to sie wyswietli gdy plik bedzie tylko do odczytu
        }
    }

    //sortuje baze danych ArrayList, plik txt i tabelkie po imieniu
    public void sortByFirstName(){

        if (ascendingFirstNames == false) {
            //sortuje rosnaco baze danych ArrayList po nazwiskach i zapisauje do nowej listy
            List<Doctor> doctorsSortedByFirstName = database.getDoctors().stream()
                    .sorted(Comparator.comparing(Doctor::getFirstName))//mozna .sorted((o1, o2) -> o1.getFirstName().compareTo(o2.getFirstName()))
                    .collect(Collectors.toList());

            //czysci stara nieposortowana baze danych
            database.getDoctors().clear();

            //dodaje posotrowana liste do bazy danych
            database.getDoctors().addAll(doctorsSortedByFirstName);

            //zapis do pliku txt
            saveFromDatabaseToTxtFile();

            //wyswietla obiekty w tym oknie, korzystajac z bazy danych ArrayList
            displayTableInWindow();

            ascendingFirstNames = true;
        }

        else if (ascendingFirstNames == true) {
            //sortuje rosnaco baze danych ArrayList po nazwiskach i zapisuje do nowej listy
            List<Doctor> doctorsSortedByFirstName = database.getDoctors().stream()
                    .sorted(Comparator.comparing(Doctor::getFirstName))//mozna .sorted((o1, o2) -> o1.getFirstName().compareTo(o2.getFirstName()))
                    .collect(Collectors.toList());

            //odwraca kolejnosc sortowania zeby bylo malejaco
            Collections.reverse(doctorsSortedByFirstName);

            //czysci stara nieposortowana baze danych
            database.getDoctors().clear();

            //dodaje posotrowana liste do bazy danych
            database.getDoctors().addAll(doctorsSortedByFirstName);

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
            List<Doctor> doctorsSortedByLastName = database.getDoctors().stream()
                    .sorted(Comparator.comparing(Doctor::getLastName))//mozna .sorted((o1, o2) -> o1.getLastName().compareTo(o2.getLastName()))
                    .collect(Collectors.toList());

            //czysci stara nieposortowana baze danych
            database.getDoctors().clear();

            //dodaje posotrowana liste do bazy danych
            database.getDoctors().addAll(doctorsSortedByLastName);

            //zapis do pliku txt
            saveFromDatabaseToTxtFile();

            //wyswietla obiekty w tym oknie, korzystajac z bazy danych ArrayList
            displayTableInWindow();

            ascendingLastNames = true;
        }

        else if (ascendingLastNames == true){

            //sortuje baze danych ArrayList po nazwiskach i zapisuje do nowej listy
            List<Doctor> doctorsSortedByLastName = database.getDoctors().stream()
                    .sorted(Comparator.comparing(Doctor::getLastName))//mozna .sorted((o1, o2) -> o1.getLastName().compareTo(o2.getLastName()))
                    .collect(Collectors.toList());

            //odwraca kolejnosc sortowania zeby bylo malejaco
            Collections.reverse(doctorsSortedByLastName);

            //czysci stara nieposortowana baze danych
            database.getDoctors().clear();

            //dodaje posotrowana liste do bazy danych
            database.getDoctors().addAll(doctorsSortedByLastName);

            //zapis do pliku txt
            saveFromDatabaseToTxtFile();

            //wyswietla obiekty w tym oknie, korzystajac z bazy danych ArrayList
            displayTableInWindow();

            ascendingLastNames = false;
        }
    }

    //sortuje baze danych ArrayList, plik txt i tabelkie po pensji
    public void sortBySalary(){

        if(ascendingSalaries == false) {
            //sortuje baze danych ArrayList po pensjach i zapisuje do nowej listy
            List<Doctor> doctorsSortedBySalary = database.getDoctors().stream()
                    .sorted(Comparator.comparing(Doctor::getSalary))//mozna .sorted((o1, o2) -> o1.getSalary().compareTo(o2.getSalary()))
                    .collect(Collectors.toList());

            //czysci stara nieposortowana baze danych
            database.getDoctors().clear();

            //dodaje posotrowana liste do bazy danych
            database.getDoctors().addAll(doctorsSortedBySalary);

            //zapis do pliku txt
            saveFromDatabaseToTxtFile();

            //wyswietla obiekty w tym oknie, korzystajac z bazy danych ArrayList
            displayTableInWindow();

            ascendingSalaries = true;
        }

        else if(ascendingSalaries == true){
            //sortuje baze danych ArrayList po pensjach i zapisuje do nowej listy
            List<Doctor> doctorsSortedBySalary = database.getDoctors().stream()
                    .sorted(Comparator.comparing(Doctor::getSalary))//mozna .sorted((o1, o2) -> o1.getSalary().compareTo(o2.getSalary()))
                    .collect(Collectors.toList());

            //odwraca kolejnosc sortowania zeby bylo malejaco
            Collections.reverse(doctorsSortedBySalary);

            //czysci stara nieposortowana baze danych
            database.getDoctors().clear();

            //dodaje posotrowana liste do bazy danych
            database.getDoctors().addAll(doctorsSortedBySalary);

            //zapis do pliku txt
            saveFromDatabaseToTxtFile();

            //wyswietla obiekty w tym oknie, korzystajac z bazy danych ArrayList
            displayTableInWindow();

            ascendingSalaries = false;
        }
    }

    //zamyka to okno i ponownie je otwiera
    public void closeAndOpenThisWindow() throws IOException {

        //zamyka to onko
        showDoctorsStage = (Stage) addDoctorButton.getScene().getWindow();//pobierz mi okno, w ktorym osadzona jest scena
        showDoctorsStage.close();

        //po czym ponownie je otwiera i ono ma wczytana juz nowa tebelke po wprowadzonych zmianach
        ShowDoctorsController showDoctorsController = new ShowDoctorsController();
        showDoctorsController.openShowDoctorsWindow();
    }
}
