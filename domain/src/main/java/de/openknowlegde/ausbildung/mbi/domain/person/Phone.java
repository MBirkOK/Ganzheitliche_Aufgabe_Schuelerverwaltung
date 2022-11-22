package de.openknowlegde.ausbildung.mbi.domain.person;

import java.util.Arrays;
import java.util.Objects;

/**
 * Phone is a Value Object, which holds important information about the phone number of a human.
 */

public class Phone implements Comparable<Phone> {
    public static final int THREE = 3;

    public static final int THIRTYONE = 31;

    private String country;

    private String number;

    private char[] accepted = "()/\\- ".toCharArray();

    public Phone(String number) {
        split(number);
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    /**
     * In this method the number of a human is given. If the human used an international prefix (+49 for germany) the number will be splited
     * and returned as area code and actual number.
     *
     * @param toSplit the typed in phone number
     * @return a String array, that contains the area code and the number
     */
    public String[] split(String toSplit) {
        char plus = '+';
        if (toSplit.toCharArray()[0] == plus) {
            this.country = toSplit.substring(0, 2);
            this.number = toSplit.substring(THREE);
            return new String[]{this.country, this.number};
        }
        this.number = number;
        return new String[]{number};
    }

    private boolean validate(Phone ignoredNumber) {
        if (ignoredNumber.getNumber() == null || ignoredNumber.getCountry() == null) {
            return false;
        }
        return true;
    }

    public void changeNumber(String changedNumber) {
        String[] newNumber = split(changedNumber);
        this.country = newNumber[0];
        this.number = newNumber[1];
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Phone phone = (Phone)o;
        return Objects.equals(country, phone.country) && Objects.equals(number, phone.number) && Arrays.equals(accepted, phone.accepted);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(country, number);
        result = THIRTYONE * result + Arrays.hashCode(accepted);
        return result;
    }


    @Override
    public int compareTo(Phone that) {
        int countryComparison = this.getCountry().compareTo(that.getCountry());
        if (countryComparison != 0) {
            return countryComparison < 0 ? -1 : 1;
        }

        return this.getNumber().compareTo(that.getNumber());
    }
}
