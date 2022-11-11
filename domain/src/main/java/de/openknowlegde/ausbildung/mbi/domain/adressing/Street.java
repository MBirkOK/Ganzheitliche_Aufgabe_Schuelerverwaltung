package de.openknowlegde.ausbildung.mbi.domain.adressing;

import java.util.Objects;

import de.openknowlegde.ausbildung.mbi.domain.persondata.Name;

public class Street implements Comparable<Street> {

    Name street;

    public Street(Name street) {
        this.street = street;
    }

    public Name getStreet() {
        return street;
    }

    public Name changeStreet(Name updatedStreet) {
        if (this.street.valid(updatedStreet.getValue())) {
            this.street = updatedStreet;
        } else {
            throw new IllegalArgumentException("Name nicht valide.");
        }
        return new Name(this.street.getValue());
    }

    public Street fromString(String newStreet) {
        return new Street(new Name(newStreet));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Street streetOne = (Street)o;
        return Objects.equals(street, streetOne.street);
    }

    @Override
    public int hashCode() {
        return Objects.hash(street);
    }


    @Override
    public int compareTo(Street that) {
        return this.getStreet().compareTo(that.getStreet());
    }
}