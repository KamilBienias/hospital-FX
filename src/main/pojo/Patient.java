package main.pojo;

import java.util.Objects;

public class Patient {

    private String firstName, lastName, pesel, hisDoctorFirstName, hisDoctorLastName, hisDoctorPesel;

    public Patient(String firstName, String lastName, String pesel, String hisDoctorFirstName, String hisDoctorLastName, String hisDoctorPesel) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.pesel = pesel;
        this.hisDoctorFirstName = hisDoctorFirstName;
        this.hisDoctorLastName = hisDoctorLastName;
        this.hisDoctorPesel = hisDoctorPesel;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPesel() {
        return pesel;
    }

    public void setPesel(String pesel) {
        this.pesel = pesel;
    }

    public String getHisDoctorFirstName() {
        return hisDoctorFirstName;
    }

    public void setHisDoctorFirstName(String hisDoctorFirstName) {
        this.hisDoctorFirstName = hisDoctorFirstName;
    }

    public String getHisDoctorLastName() {
        return hisDoctorLastName;
    }

    public void setHisDoctorLastName(String hisDoctorLastName) {
        this.hisDoctorLastName = hisDoctorLastName;
    }

    public String getHisDoctorPesel() {
        return hisDoctorPesel;
    }

    public void setHisDoctorPesel(String hisDoctorPesel) {
        this.hisDoctorPesel = hisDoctorPesel;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Patient)) return false;
        Patient patient = (Patient) o;
        return Objects.equals(pesel, patient.pesel);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pesel);
    }

    @Override
    public String toString() {
        return "Patient{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", pesel='" + pesel + '\'' +
                ", hisDoctorFirstName='" + hisDoctorFirstName + '\'' +
                ", hisDoctorLastName='" + hisDoctorLastName + '\'' +
                ", hisDoctorPesel='" + hisDoctorPesel + '\'' +
                '}';
    }
}

