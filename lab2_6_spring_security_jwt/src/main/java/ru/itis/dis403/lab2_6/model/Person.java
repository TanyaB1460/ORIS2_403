package ru.itis.dis403.lab2_6.model;

import jakarta.persistence.*;

import java.util.Date;
import java.util.Objects;

@Entity
public class Person {
    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private String gender;

    @Temporal(TemporalType.DATE)
    private Date birthdate;

    private String fromCity;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Date getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }

    public String getFromCity() {
        return fromCity;
    }

    public void setFromCity(String fromCity) {
        this.fromCity = fromCity;
    }

    @Override
    public String toString() {
        return "Person{" +
                "gender='" + gender + '\'' +
                ", birthdate=" + birthdate +
                ", fromCity='" + fromCity + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return Objects.equals(gender, person.gender) &&
                Objects.equals(birthdate, person.birthdate) &&
                Objects.equals(fromCity, person.fromCity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(gender, birthdate, fromCity);
    }
}