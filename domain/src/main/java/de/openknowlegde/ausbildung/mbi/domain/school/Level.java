package de.openknowlegde.ausbildung.mbi.domain.school;

import java.util.Objects;

public class Level {
    private String value;

    public Level(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Level level = (Level)o;
        return Objects.equals(value, level.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
