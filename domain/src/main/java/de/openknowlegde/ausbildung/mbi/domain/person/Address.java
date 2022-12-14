package de.openknowlegde.ausbildung.mbi.domain.person;

import java.security.SecureRandom;
import java.util.Objects;

import de.openknowlegde.ausbildung.mbi.domain.adressing.City;
import de.openknowlegde.ausbildung.mbi.domain.adressing.CityName;
import de.openknowlegde.ausbildung.mbi.domain.adressing.HouseNumber;
import de.openknowlegde.ausbildung.mbi.domain.adressing.Street;
import de.openknowlegde.ausbildung.mbi.domain.adressing.Zip;
import de.openknowlegde.ausbildung.mbi.domain.testdata.CityList;
import de.openknowlegde.ausbildung.mbi.domain.testdata.StreetList;


/**
 * This class is used as a Value Object to map the address in the Human class. Each address has a street, a house number, a postal code and
 * a city.
 */
public class Address {
    //Random house number from 1 to 100
    private static final int HUNDRED = 100;

    //Random zip code from 1 to 9999
    private static final int UPPER = 9999;

    private static final SecureRandom RANDOM = new SecureRandom();

    private Street street;

    private HouseNumber houseNumber;

    private Zip zipcode;

    private City city;

    /**
     * Used as a default constructor to create an object of type address. The given parameters street, house number, zip code and city are
     * used to store the unique address of the person.
     *
     * @param street      the street of the place, where the person lifes
     * @param houseNumber house number of the place
     * @param zipcode     postal code where the place is
     * @param city        city name to the postal code
     */
    public Address(Street street, HouseNumber houseNumber, Zip zipcode, City city) {
        this.street = street;
        this.houseNumber = houseNumber;
        this.zipcode = zipcode;
        this.city = city;
    }

    /**
     * Empty constructor. It creates an empty Adress to the person.
     */
    public Address() {
    }

    public Street getStreet() {
        return street;
    }

    public Street changeStreet(Street updatedStreet) {
        this.street = new Street(updatedStreet.getStreet());
        return this.street;
    }

    public HouseNumber getHouseNumber() {
        return houseNumber;
    }

    public HouseNumber changeHouseNumber(HouseNumber updatedHouseNumber) {
        this.houseNumber = new HouseNumber(updatedHouseNumber.getNumber());
        return this.houseNumber;
    }

    public Zip getZipcode() {
        return zipcode;
    }

    public Zip changeZipcode(Zip updatedZip) {
        this.zipcode = new Zip(updatedZip.getNumber());
        return this.zipcode;
    }

    public City getCity() {
        return city;
    }

    public City changeCity(City updatedCity) {
        this.city = new City(updatedCity.getName());
        return this.city;
    }

    /**
     * A random address is created. Random values are created using enumerations from the data package. By the Street Enumeration and with
     * the help of the Secure Random class a random street is determined, by the SecureRandom class a random number is determined and the
     * value at the place of the number from the Enumeration is selected. For the other values it works similarly, with the corresponding
     * enumeration. The only exception is the house number and the postal code.
     *
     * @return a new Adress filled with all random data
     */
    public Address randomAdress() {
        return new Address(new Street(new FirstName(StreetList.values()[RANDOM.nextInt(StreetList.values().length)].name())),
            new HouseNumber(RANDOM.nextInt(HUNDRED)), new Zip(RANDOM.nextInt(UPPER)),
            new City(new CityName(CityList.values()[RANDOM.nextInt(CityList.values().length)].name())));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Address address = (Address)o;
        return Objects.equals(street, address.street) && Objects.equals(houseNumber, address.houseNumber)
            && Objects.equals(zipcode, address.zipcode) && Objects.equals(city, address.city);
    }

    @Override
    public int hashCode() {
        return Objects.hash(street, houseNumber, zipcode, city);
    }

    public boolean validate(Address address) {
        if (address.getCity() == null || address.getStreet() == null || address.getZipcode() == null
            || address.getHouseNumber() == null) {
            return false;
        }
        return true;
    }
}
