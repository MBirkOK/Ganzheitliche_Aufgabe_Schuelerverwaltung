package de.openknowlegde.ausbildung.mbi.domain.person;

import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

import de.openknowlegde.ausbildung.mbi.domain.persondata.Adress;
import de.openknowlegde.ausbildung.mbi.domain.persondata.Birthday;
import de.openknowlegde.ausbildung.mbi.domain.persondata.Name;
import de.openknowlegde.ausbildung.mbi.domain.persondata.Phone;

/**
 * The class is used to represent the teacher. By abstracting with the teacher into the human class, it is possible that the separation of
 * the classes can be done more clearly.
 */

public class Teacher extends Human {
    public Teacher(UUID number, Name firstName, Name lastName, Set<Phone> phone, Set<Adress> adress, Birthday birthday) {
        super(number, firstName, lastName, phone, adress, birthday);
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
