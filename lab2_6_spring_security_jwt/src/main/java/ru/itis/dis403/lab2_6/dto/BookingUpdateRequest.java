package ru.itis.dis403.lab2_6.dto;

public class BookingUpdateRequest {
    private Long id;
    private String arrivalDate;
    private String stayingDate;
    private String departureDate;
    private String fromCity;

    public BookingUpdateRequest() {
    }

    public BookingUpdateRequest(Long id, String arrivalDate, String stayingDate,
                                String departureDate, String fromCity) {
        this.id = id;
        this.arrivalDate = arrivalDate;
        this.stayingDate = stayingDate;
        this.departureDate = departureDate;
        this.fromCity = fromCity;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getArrivalDate() {
        return arrivalDate;
    }

    public void setArrivalDate(String arrivalDate) {
        this.arrivalDate = arrivalDate;
    }

    public String getStayingDate() {
        return stayingDate;
    }

    public void setStayingDate(String stayingDate) {
        this.stayingDate = stayingDate;
    }

    public String getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(String departureDate) {
        this.departureDate = departureDate;
    }

    public String getFromCity() {
        return fromCity;
    }

    public void setFromCity(String fromCity) {
        this.fromCity = fromCity;
    }
}