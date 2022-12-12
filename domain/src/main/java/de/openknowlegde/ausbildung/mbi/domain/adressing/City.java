package de.openknowlegde.ausbildung.mbi.domain.adressing;

import java.util.Objects;

public class City implements Comparable<City> {

    private CityName name;

    public City(CityName name) {
        this.name = name;
    }

    public CityName getName() {
        return name;
    }

    public CityName changeName(CityName newName) {
        if (this.name.valid(newName.getValue())) {
            this.name = newName;
        } else {
            throw new IllegalArgumentException("Name nicht valide.");
        }
        return new CityName(this.name.getValue());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        City city = (City)o;
        return Objects.equals(name, city.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }


    @Override
    public int compareTo(City that) {
        return this.getName().compareTo(that.getName());
    }
}
