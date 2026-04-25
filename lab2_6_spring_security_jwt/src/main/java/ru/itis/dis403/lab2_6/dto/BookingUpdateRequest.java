package ru.itis.dis403.lab2_6.dto;

import java.util.Date;

public class BookingUpdateRequest {

    private Date arrivalDate;
    private Date stayingDate;
    private Date departureDate;
    private String personName;  // ← добавили

    public BookingUpdateRequest() {
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

    public String getPersonName() {        // ← добавили
        return personName;
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

    public void setPersonName(String personName) {   // ← добавили
        this.personName = personName;
    }
}