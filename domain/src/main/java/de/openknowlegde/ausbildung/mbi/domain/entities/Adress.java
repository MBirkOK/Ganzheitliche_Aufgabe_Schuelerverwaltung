package de.openknowlegde.ausbildung.mbi.domain.entities;

import java.security.SecureRandom;

import de.openknowlegde.ausbildung.mbi.domain.data.City;
import de.openknowlegde.ausbildung.mbi.domain.data.Street;

/**
 * This class is used as a Value Object to map the address in the Human class. Each address has a street, a house number, a postal code and
 * a city.
 */
public class Adress {
    private static final int HUNDRED = 100;

    private static final int UPPER = 9999;

    private static final SecureRandom RANDOM = new SecureRandom();

    private String street;

    private String houseNumber;

    private int zipcode;

    private String city;

    /**
     * Used as a default constructor to create an object of type address. The given parameters street, house number, zip code and city are
     * used to store the unique address of the person.
     *
     * @param street
     *   the street of the place, where the person lifes
     * @param houseNumber
     *   house number of the place
     * @param zipcode
     *   postal code where the place is
     * @param city
     *   city name to the postal code
     */
    public Adress(String street, String houseNumber, int zipcode, String city) {
        this.street = street;
        this.houseNumber = houseNumber;
        this.zipcode = zipcode;
        this.city = city;
    }

    /**
     * Empty constructor. It creates an empty Adress to the person.
     */
    public Adress() {
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
    }

    public int getZipcode() {
        return zipcode;
    }

    public void setZipcode(int zipcode) {
        this.zipcode = zipcode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    /**
     * A random address is created. Random values are created using enumerations from the data package. By the Street Enumeration and with
     * the help of the Secure Random class a random street is determined, by the SecureRandom class a random number is determined and the
     * value at the place of the number from the Enumeration is selected. For the other values it works similarly, with the corresponding
     * enumeration. The only exception is the house number and the postal code.
     *
     * @return a new Adress filled with all random data
     */
    public Adress randomAdress() {
        return new Adress(Street.values()[RANDOM.nextInt(Street.values().length)].name(), String.valueOf(RANDOM.nextInt(HUNDRED)),
          RANDOM.nextInt(UPPER), City.values()[RANDOM.nextInt(City.values().length)].name());
    }
}
