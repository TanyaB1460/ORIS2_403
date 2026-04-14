package ru.itis.dis403.lab2_2.orm.model;

import ru.itis.dis403.lab2_2.orm.annotation.*;

@Entity
public class Street {
    @Id
    private Long id;

    @Column
    private String name;

    @ManyToOne
    private City city;

    public Street() {
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public City getCity() { return city; }
    public void setCity(City city) { this.city = city; }

    @Override
    public String toString() {
        return "Street{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", city=" + city +
                '}';
    }
}
