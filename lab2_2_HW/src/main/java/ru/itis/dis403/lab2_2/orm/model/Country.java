package ru.itis.dis403.lab2_2.orm.model;

import ru.itis.dis403.lab2_2.orm.annotation.Column;
import ru.itis.dis403.lab2_2.orm.annotation.Entity;
import ru.itis.dis403.lab2_2.orm.annotation.Id;

@Entity
public class Country {
    @Id
    private Long id;

    @Column
    private String name;

    public Country() {
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    @Override
    public String toString() {
        return "Country{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
