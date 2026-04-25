package ru.itis.dis403.lab2_6.dto;

import java.util.Date;

public class BookingSaveRequest {

    private Long id;
    private Date arrivalDate;
    private Date stayingDate;
    private Date departureDate;
    private Long personId;
    private String personName;
    private String personGender;
    private Date personBirthdate;
    private String room;

    public BookingSaveRequest() {
    }

    // Геттеры
    public Long getId() {
        return id;
    }

    public Date getArrivalDate() {
        return arrivalDate;
    }

    public Date getStayingDate() {
        return stayingDate;
    }

    public Date getDepartureDate() {
        return departureDate;
    }

    public Long getPersonId() {
        return personId;
    }

    public String getPersonName() {
        return personName;
    }

    public String getPersonGender() {
        return personGender;
    }

    public Date getPersonBirthdate() {
        return personBirthdate;
    }

    public String getRoom() {
        return room;
    }

    // Сеттеры
    public void setId(Long id) {
        this.id = id;
    }

    public void setArrivalDate(Date arrivalDate) {
        this.arrivalDate = arrivalDate;
    }

    public void setStayingDate(Date stayingDate) {
        this.stayingDate = stayingDate;
    }

    public void setDepartureDate(Date departureDate) {
        this.departureDate = departureDate;
    }

    public void setPersonId(Long personId) {
        this.personId = personId;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public void setPersonGender(String personGender) {
        this.personGender = personGender;
    }

    public void setPersonBirthdate(Date personBirthdate) {
        this.personBirthdate = personBirthdate;
    }

    public void setRoom(String room) {
        this.room = room;
    }
}