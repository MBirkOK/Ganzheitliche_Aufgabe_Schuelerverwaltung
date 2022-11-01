package de.openknowlegde.ausbildung.mbi.domain.adressingObjects;

import java.util.Objects;

public class Zip implements Comparable<Zip> {

    int number;

    public Zip(int number) {
        this.number = number;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Zip zip = (Zip)o;
        return number == zip.number;
    }

    @Override
    public int hashCode() {
        return Objects.hash(number);
    }


    @Override
    public int compareTo(Zip that) {
        if (this.getNumber() != that.getNumber()) {
            return (this.getNumber() < that.getNumber() ? -1 : 1);
        }

        return 0;
    }
}
