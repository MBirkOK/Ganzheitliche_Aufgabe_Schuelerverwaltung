package de.openknowlegde.ausbildung.mbi.domain.entities;

/**
 * The class Name is used as a Value Object , which just holds the information about the specific name.
 */

public class Name {
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
     *
     * only blanks.
     *
     * @param validValue
     *   value for the name
     * @return a boolean to indicate the name
     */
    public boolean valid(String validValue) {
        return !(validValue.isEmpty()) && validValue.trim().length() > 2;
    }
}
