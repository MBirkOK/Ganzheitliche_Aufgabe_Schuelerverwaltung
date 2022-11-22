package de.openknowlegde.ausbildung.mbi.domain.person;

import java.util.Set;
import java.util.UUID;

import de.openknowlegde.ausbildung.mbi.domain.school.SchoolClass;

/**
 * The class is used to represent the student. By abstracting with the teacher into the human class, it is possible that the separation of
 * the classes can be done more clearly.
 */

public class Student extends Human {
    public Student(UUID number, FirstName firstFirstName, LastName lastFirstName, Set<Phone> phone,
        Set<Address> addresses, Birthday birthday, SchoolClass schoolClass) {
        super(number, firstFirstName, lastFirstName, phone, addresses, birthday, schoolClass);
    }

    /**
     * Overwrites the compareTo method from the Comparable Interface, that is used in Human.
     *
     * @param o
     *     the object to be compared.
     * @return a negative integer, zero, or a positive integer as this object is less than, equal to, or greater than the specified object.
     */
    @Override public int compareTo(Human o) {
        return this.getNumber().compareTo(o.getNumber());
    }
}
