package de.openknowlegde.ausbildung.mbi.domain.entities;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import de.openknowlegde.ausbildung.mbi.domain.abstractclass.Human;

/**
 * The class is used to represent the teacher. By abstracting with the teacher into the human class, it is possible that the separation of
 * the classes can be done more clearly.
 */

public class Teacher extends Human {
    public Teacher(UUID number, String firstName, String lastName, List<Phone> phone, List<Adress> adress, LocalDate birthday) {
        super(number, new Name(firstName), new Name(lastName), phone, adress, birthday);
    }

    /**
     * Overwrites the compareTo method from the Comparable Interface, that is used in Human.
     *
     * @param o
     *     the object to be compared.
     * @return a negative integer, zero, or a positive integer as this object is less than, equal to, or greater than the specified object.
     */
    @Override public int compareTo(Human o) {
        return this.getLastName().getValue().compareTo(o.getLastName().getValue());
    }
}
