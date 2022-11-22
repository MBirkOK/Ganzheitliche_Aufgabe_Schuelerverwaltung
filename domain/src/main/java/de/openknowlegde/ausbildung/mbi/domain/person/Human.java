package de.openknowlegde.ausbildung.mbi.domain.person;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

import de.openknowlegde.ausbildung.mbi.domain.school.SchoolClass;

/**
 * Human is used to abstract the classes of student and teacher. Both mentioned classes contain the same information and differ only in
 * their naming/types. Any information held in human is still very important. By abstracting the classes it is possible that in the classes
 * student and teacher only the super constructor has to be called.
 */

public abstract class Human implements Comparable<Human> {

    private final UUID number;

    private final FirstName firstName;

    private final LastName lastName;

    private Set<Phone> phone;

    private Set<Address> addresses;

    private final Birthday birthday;

    private SchoolClass schoolClass;

    /**
     * Standard constructor for the human class. This constructor is used indirectly for teachers and students and gets the most important
     * information.
     *
     * @param number         used as an ID
     * @param firstName firstname of the person
     * @param lastName  last name of the person
     * @param phone          a list of phone numbers of the person
     * @param addresses      a list of adresses of the person
     * @param birthday       a birthday as LocalDate standart
     * @param schoolClass   the schoolClass, the person belongs to
     */
    public Human(UUID number, FirstName firstName, LastName lastName, Set<Phone> phone, Set<Address> addresses,
        Birthday birthday, SchoolClass schoolClass) {
        this.number = number;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.addresses = addresses;
        this.birthday = birthday;
        this.schoolClass = schoolClass;
    }

    public UUID getNumber() {
        return number;
    }

    public FirstName getFirstName() {
        return firstName;
    }

    public LastName getLastName() {
        return lastName;
    }

    public Set<Phone> getPhone() {
        return phone;
    }

    /**
     * This method first checks if a valid List of phone numbers exists and then adds the given number.
     *
     * @param additionalPhone phone number to be added
     */
    public void addPhone(Phone additionalPhone) {
        if (this.phone == null) {
            this.phone = new HashSet<>();
        }
        this.phone.add(additionalPhone);
    }

    protected void deletePhone(Phone toRemove) {
        this.phone.remove(toRemove);
    }

    public Set<Address> getAdress() {
        return addresses;
    }

    /**
     * This method first checks if a valid List of adress' exists and then adds the given adress.
     *
     * @param additionalAddress adress to be added
     */
    public void addAdress(Address additionalAddress) {
        if (this.addresses == null) {
            this.addresses = new HashSet<>();
        }
        if (!this.addresses.contains(additionalAddress)) {
            this.addresses.add(additionalAddress);
        }
    }

    protected void deleteAdress(Address toRemove) {
        this.addresses.remove(toRemove);
    }

    public Birthday getBirthday() {
        return birthday;
    }

    public SchoolClass getSchoolClass() {
        return schoolClass;
    }

    public void changeSchoolClass(SchoolClass newSchoolClass) {
        this.schoolClass = newSchoolClass;
    }

    /**
     * In this method, essential data about the person are changed. A distinction is made via the type passed. The first name, last name and
     * date of birth can be changed.
     *
     * @param type  type of data, that will be changed
     * @param value the new value
     */

    public void changePersonalData(String type, String value) {
        switch (type) {
            case "Vorname":
                this.getFirstName().changeValue(value);
                break;
            case "Nachname":
                this.getLastName().changeValue(value);
                break;
            default:
                break;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Human human = (Human)o;
        return Objects.equals(number, human.number) && Objects.equals(firstName, human.firstName)
            && Objects.equals(lastName, human.lastName) && Objects.equals(phone, human.phone)
            && Objects.equals(addresses, human.addresses) && Objects.equals(birthday, human.birthday);
    }

    @Override
    public int hashCode() {
        return Objects.hash(number, firstName, lastName, phone, addresses, birthday);
    }


    @Override
    public int compareTo(Human that) {
        int numberComparison = this.getNumber().compareTo(that.getNumber());
        if (numberComparison != 0) {
            return numberComparison < 0 ? -1 : 1;
        }

        int firstNameComparison = this.getFirstName().compareTo(that.getFirstName());
        if (firstNameComparison != 0) {
            return firstNameComparison < 0 ? -1 : 1;
        }

        return this.getLastName().compareTo(that.getLastName());
    }

    public boolean validate(Human human) {
        if (human.getBirthday().toString().isEmpty() || human.getFirstName().isValid() || human.getLastName().isValid()
            || areSetsEmpty(human.getAdress(), human.getPhone())) {
            throw new IllegalArgumentException("Human nicht valide.");
        } else {
            return true;
        }
    }

    public boolean areSetsEmpty(Set<Address> address, Set<Phone> phones) {
        return address.isEmpty() || phones.isEmpty();
    }

    public boolean isValid() {
        if (validate(this)) {
            return true;
        }
        return false;
    }
}
