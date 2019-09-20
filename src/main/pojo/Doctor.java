package main.pojo;

import java.util.List;
import java.util.Objects;

public class Doctor{

    private String firstName, lastName, pesel;
    private int salary;
    private List<Patient> patientsOfThisDoctor;//tego nie uzywam

    public Doctor(String firstName, String lastName, int salary, String pesel) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.salary = salary;
        this.pesel = pesel;
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

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public String getPesel() {
        return pesel;
    }

    public void setPesel(String pesel) {
        this.pesel = pesel;
    }

    public List<Patient> getPatientsOfThisDoctor() {
        return patientsOfThisDoctor;
    }

    public void setPatientsOfThisDoctor(List<Patient> patientsOfThisDoctor) {
        this.patientsOfThisDoctor = patientsOfThisDoctor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Doctor)) return false;
        Doctor doctor = (Doctor) o;
        return Objects.equals(pesel, doctor.pesel);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pesel);
    }

    @Override
    public String toString() {
        return firstName + " " + lastName + " " + pesel;
//        return String.format("%15.20s %15.20s %8.8s %15.20s", firstName, lastName, salary, pesel);//%15.20s oznacza ze przeznaczone jest 15 miejsc, a napis bedzie wyrownany do prawej. Na napis jest 20 miejsc, wiec jak bedzie dluzszy niz 20 to ucina
    }
}