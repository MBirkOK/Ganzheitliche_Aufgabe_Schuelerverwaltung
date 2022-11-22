package de.openknowlegde.ausbildung.mbi.domain.person;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import de.openknowlegde.ausbildung.mbi.domain.school.SchoolClass;

/**
 * The class is used to represent the teacher. By abstracting with the teacher into the human class, it is possible that the separation of
 * the classes can be done more clearly.
 */

public class Teacher extends Human {
    public Teacher(UUID number, FirstName firstName, LastName lastName, Set<Phone> phone, Set<Address> addresses,
        Birthday birthday, SchoolClass schoolClass) {
        super(number, firstName, lastName, phone, addresses, birthday, schoolClass);
    }
    public Teacher() {
        super(UUID.randomUUID(), new FirstName(""), new LastName(""), new HashSet<>(), new HashSet<>(), new Birthday(LocalDate.now()),
            new SchoolClass());
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
