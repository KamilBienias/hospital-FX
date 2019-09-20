package main;

import main.pojo.Doctor;
import main.pojo.Patient;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement
public class Database {

    private List<Doctor> doctors;
    private List<Patient> patients;

    //a non-argument constructor is needed to create an xml file
    public Database(){
        doctors = new ArrayList<>();
        patients = new ArrayList<>();
    }

    @XmlElement(name = "listOfDoctors")
    public List<Doctor> getDoctors() {
        return doctors;
    }

    public void setDoctors(List<Doctor> doctors) {
        this.doctors = doctors;
    }

    @XmlElement(name = "listOfPatients")
    public List<Patient> getPatients() {
        return patients;
    }

    public void setPatients(List<Patient> patients) {
        this.patients = patients;
    }

    @Override
    public String toString() {
        return "Database{" +
                "doctors=" + doctors +
                ", patients=" + patients +
                '}';
    }
}
