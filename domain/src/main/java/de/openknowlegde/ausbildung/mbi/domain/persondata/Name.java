package de.openknowlegde.ausbildung.mbi.domain.persondata;

import java.util.Objects;

/**
 * The class Name is used as a Value Object , which just holds the information about the specific name.
 */

public class Name implements Comparable<Name> {
    private String value;

    public Name(String name) {
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
        Name name = (Name)o;
        return Objects.equals(value, name.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }


    @Override
    public int compareTo(Name that) {
        return this.getValue().compareTo(that.getValue());
    }

    private boolean validate(Name name) {
        if (name.getValue() == null) {
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
