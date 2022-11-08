package de.openknowlegde.ausbildung.mbi.domain.adressing;

import java.util.Objects;

import de.openknowlegde.ausbildung.mbi.domain.persondata.Name;

public class City implements Comparable<City> {

    Name name;

    public City(Name name) {
        this.name = name;
    }

    public Name getName() {
        return name;
    }

    public Name changeName(Name newName) {
        if (this.name.valid(newName.getValue())) {
            this.name = newName;
        } else {
            throw new IllegalArgumentException("Name nicht valide.");
        }
        return new Name(this.name.getValue());
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
