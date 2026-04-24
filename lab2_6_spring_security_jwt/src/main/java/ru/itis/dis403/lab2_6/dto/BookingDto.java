package ru.itis.dis403.lab2_6.dto;

public class BookingDto {
    private Long id;
    private String hotelName;
    private String personGender;
    private String birthDate;
    private String fromCity;
    private String arrivalDate;
    private String stayingDate;
    private String departureDate;

    public BookingDto() {
    }

    public BookingDto(Long id, String hotelName, String personGender, String birthDate,
                      String fromCity, String arrivalDate, String stayingDate, String departureDate) {
        this.id = id;
        this.hotelName = hotelName;
        this.personGender = personGender;
        this.birthDate = birthDate;
        this.fromCity = fromCity;
        this.arrivalDate = arrivalDate;
        this.stayingDate = stayingDate;
        this.departureDate = departureDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getHotelName() {
        return hotelName;
    }

    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }

    public String getPersonGender() {
        return personGender;
    }

    public void setPersonGender(String personGender) {
        this.personGender = personGender;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getFromCity() {
        return fromCity;
    }

    public void setFromCity(String fromCity) {
        this.fromCity = fromCity;
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
}