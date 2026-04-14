package ru.itis.dis403.lab2_2.orm;

import ru.itis.dis403.lab2_2.orm.model.City;
import ru.itis.dis403.lab2_2.orm.model.Country;
import ru.itis.dis403.lab2_2.orm.model.Street;

public class OrmCrudMain {
    public static void main(String[] args) throws Exception {
        EntityManagerFactory emf = new EntityManagerFactory();
        EntityManager em = emf.getEntityManager();

        Country country = new Country();
        country.setName("Russia");
        em.save(country);
        System.out.println("saved country id = " + country.getId());

        City city = new City();
        city.setName("Kazan");
        city.setCountry(country);
        em.save(city);
        System.out.println("saved city id = " + city.getId());

        Street street = new Street();
        street.setName("Baumana");
        street.setCity(city);
        em.save(street);
        System.out.println("saved street id = " + street.getId());

        emf.close();
    }
}