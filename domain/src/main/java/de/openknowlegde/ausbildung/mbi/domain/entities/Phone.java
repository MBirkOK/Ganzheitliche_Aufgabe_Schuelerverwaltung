package de.openknowlegde.ausbildung.mbi.domain.entities;

import java.util.Arrays;

/**
 * Phone is a Value Object, which holds important information about the phone number of a human.
 */

public class Phone {
    public static final int THREE = 3;

    private String country;

    private String number;

    private char[] accepted = "()/\\- ".toCharArray();

    public Phone(String number) {
        validate(number);
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
     * @param toSplit
     *   the typed in phone number
     * @return a String array, that contains the area code and the number
     */
    public String[] split(String toSplit) {
        char plus = '+';
        if (toSplit.toCharArray()[0] == plus) {
            this.country = toSplit.substring(0, 2);
            this.number = toSplit.substring(THREE);
            return new String[] {this.country, this.number};
        }
        this.number = number;
        return new String[] {number};
    }

    private void validate(String number) {
        //TODO Validate Phonenumber
    }

    public void changeNumber(String changedNumber) {
        String[] newNumber = split(changedNumber);
        this.country = newNumber[0];
        this.number = newNumber[1];
    }
}
