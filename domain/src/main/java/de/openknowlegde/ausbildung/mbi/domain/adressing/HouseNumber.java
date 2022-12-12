package de.openknowlegde.ausbildung.mbi.domain.adressing;

import java.util.Objects;

public class HouseNumber implements Comparable<HouseNumber> {

    int number;

    public HouseNumber(int number) {
        this.number = number;
    }

    public int getNumber() {
        return number;
    }

    public int changeNumber(int updatedNumber) {
        this.number = updatedNumber;
        return this.number;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        HouseNumber that = (HouseNumber)o;
        return number == that.number;
    }

    @Override
    public int hashCode() {
        return Objects.hash(number);
    }


    @Override
    public int compareTo(HouseNumber that) {
        if (this.getNumber() != that.getNumber()) {
            return (this.getNumber() < that.getNumber() ? -1 : 1);
        }

        return 0;
    }
}
