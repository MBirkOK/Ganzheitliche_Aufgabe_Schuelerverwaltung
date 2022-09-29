package de.openknowlegde.ausbildung.mbi.domain.abstractclass;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import de.openknowlegde.ausbildung.mbi.domain.entities.Adress;
import de.openknowlegde.ausbildung.mbi.domain.entities.Name;
import de.openknowlegde.ausbildung.mbi.domain.entities.Phone;

/**
 * Human is used to abstract the classes of student and teacher. Both mentioned classes contain the same information and differ only in
 * their naming/types. Any information held in human is still very important. By abstracting the classes it is possible that in the classes
 * student and teacher only the super constructor has to be called.
 */

public abstract class Human implements Comparable<Human> {

    private final UUID number;

    private final Name firstName;

    private final Name lastName;

    private List<Phone> phone;

    private List<Adress> adress;

    private LocalDate birthday;

    /**
     * Standard constructor for the human class. This constructor is used indirectly for teachers and students and gets the most important
     * information.
     *
     * @param number
     *   used as an ID
     * @param firstName
     *   firstname of the person
     * @param lastName
     *   last name of the person
     * @param phone
     *   a list of phone numbers of the person
     * @param adress
     *   a list of adresses of the person
     * @param birthday
     *   a birthday as LocalDate standart
     */
    public Human(UUID number, Name firstName, Name lastName, List<Phone> phone, List<Adress> adress, LocalDate birthday) {
        this.number = number;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.adress = adress;
        this.birthday = birthday;
    }

    public UUID getNumber() {
        return number;
    }

    public Name getFirstName() {
        return firstName;
    }

    public Name getLastName() {
        return lastName;
    }

    public List<Phone> getPhone() {
        return phone;
    }

    /**
     * This method first checks if a valid List of phone numbers exists and then adds the given number.
     *
     * @param phone
     *   phone number to be added
     */
    public void addPhone(Phone additionalPhone) {
        if (this.phone == null) {
            this.phone = new ArrayList<>();
        }
        this.phone.add(additionalPhone);
    }

    protected void deletePhone(Phone toRemove) {
        this.phone.remove(toRemove);
    }

    public List<Adress> getAdress() {
        return adress;
    }

    /**
     * This method first checks if a valid List of adress' exists and then adds the given adress.
     *
     * @param adress
     *   adress to be added
     */
    public void addAdress(Adress additionalAdress) {
        if (this.adress == null) {
            this.adress = new ArrayList<>();
        }
        if (!this.adress.contains(additionalAdress)) {
            this.adress.add(additionalAdress);
        }
    }

    protected void deleteAdress(Adress toRemove) {
        this.adress.remove(toRemove);
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    private void changeBirthday(LocalDate changedDay) {
        //TODO validiere das Datum und wirf Exception -> Was wird gebraucht um ein valides Geburtsdatum zu bauen?
        this.birthday = changedDay;
    }

    /**
     * In this method, essential data about the person are changed. A distinction is made via the type passed. The first name, last name and
     * date of birth can be changed.
     *
     * @param type
     *   type of data, that will be changed
     * @param value
     *   the new value
     */

    public void changePersonalData(String type, String value) {
        switch (type) {
            case "Vorname":
                this.getFirstName().changeValue(value);
                break;
            case "Nachname":
                this.getLastName().changeValue(value);
                break;
            case "Geburtsdatum":
                String[] newBirthday = value.split("-");
                int day = Integer.parseInt(newBirthday[0]);
                int month = Integer.parseInt(newBirthday[1]);
                int year = Integer.parseInt(newBirthday[2]);
                this.changeBirthday(LocalDate.of(day, month, year));
                break;
            default:
                break;
        }
    }
}
