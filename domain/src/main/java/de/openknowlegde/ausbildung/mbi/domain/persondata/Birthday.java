package de.openknowlegde.ausbildung.mbi.domain.persondata;

import java.time.LocalDate;
import java.util.Objects;

public class Birthday {
    private LocalDate birthday;

    public Birthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Birthday newBirthday = (Birthday)o;
        return Objects.equals(birthday, newBirthday.birthday);
    }

    @Override
    public int hashCode() {
        return Objects.hash(birthday);
    }
}
