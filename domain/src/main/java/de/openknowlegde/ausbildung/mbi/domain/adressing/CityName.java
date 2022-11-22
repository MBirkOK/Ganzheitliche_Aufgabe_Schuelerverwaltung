package de.openknowlegde.ausbildung.mbi.domain.adressing;

import java.util.Objects;

public class CityName implements Comparable<CityName> {

    private String value;

    public CityName(String name) {
        this.value = name;
    }

    public String getValue() {
        return value;
    }

    public void changeValue(String newValue) {
        if (valid(newValue)) {
            this.value = newValue;
        }
    }

    /**
     * Validates the name in the context of the Value Object. It is checked whether the name contains an empty input or
     * <p>
     * only blanks.
     *
     * @param validValue value for the name
     * @return a boolean to indicate the name
     */
    public boolean valid(String validValue) {
        return !(validValue.isEmpty()) && validValue.trim().length() > 2;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CityName cityName = (CityName)o;
        return Objects.equals(value, cityName.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }


    @Override
    public int compareTo(CityName that) {
        return this.getValue().compareTo(that.getValue());
    }

    private boolean validate(CityName cityName) {
        if (cityName.getValue() == null) {
            return false;
        }
        return true;
    }

    public boolean isValid() {
        if (this.valid(this.value)) {
            return true;
        }
        return false;
    }
}
