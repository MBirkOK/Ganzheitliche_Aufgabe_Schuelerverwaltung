package de.openknowlegde.ausbildung.mbi.domain.actions;

import static de.openknowlegde.ausbildung.mbi.domain.actions.ClassActions.TABLEFORMAT;
import static de.openknowlegde.ausbildung.mbi.domain.actions.ClassActions.printTableStudentList;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;


import de.openknowlegde.ausbildung.mbi.domain.adressing.City;
import de.openknowlegde.ausbildung.mbi.domain.adressing.CityName;
import de.openknowlegde.ausbildung.mbi.domain.adressing.HouseNumber;
import de.openknowlegde.ausbildung.mbi.domain.adressing.Street;
import de.openknowlegde.ausbildung.mbi.domain.adressing.Zip;
import de.openknowlegde.ausbildung.mbi.domain.person.Address;
import de.openknowlegde.ausbildung.mbi.domain.person.Birthday;
import de.openknowlegde.ausbildung.mbi.domain.person.FirstName;
import de.openknowlegde.ausbildung.mbi.domain.person.Human;
import de.openknowlegde.ausbildung.mbi.domain.person.Student;
import de.openknowlegde.ausbildung.mbi.domain.person.Teacher;
import de.openknowlegde.ausbildung.mbi.domain.school.SchoolClass;


/**
 * The class is used to perform various actions on the Student, Teacher and Class objects. Besides editing, adding and listing, it is also
 * possible to delete specific objects. The required classes are mostly imported from the entities module and are split to build a clear
 * structure between data management and processing.
 *
 * @see Student imported Class from domain module, represents a student
 * @see Teacher imported Class from domain module, represents a teacher
 * @see SchoolClass imported Class from domain module, represents a school class
 */

@SuppressWarnings("checkstyle:RegexpSingleline")
public class Actions {
    public static final String ASK_FOR_LAST_NAME = "Bitte gib den Nachnamen ein:";

    private static final String BLANKSPACE = " ";

    private static final String PLEASE_REPEAT_INPUT = "Bitte wiederhole deine Eingabe:";

    //Constant for number. Used for seperating adresses
    private static final int THREE = 3;

    //Used for seperation in the birthday parts
    private static final String MINUS = "-";

    private static final String WHICH_DATA_SHOULD_BE_CHANGED = "Welche Daten sollen verändert werden?";


    private static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));


    /**
     * Create an address for a person. To store a valid address for a person, the user must enter it in the valid format. He will be
     * reminded of this before entering. The method is repeated using recursivity until an address is entered in the valid format.
     *
     * @param street      the street, where the person lives
     * @param houseNumber number of the house
     * @param zipCode     postal code of the city or district
     * @param city        city name
     * @return new Adress of the given Information
     * @throws IOException
     */
    static Address createAdress(String street, String houseNumber, String zipCode, String city) throws IOException {
        try {
            return new Address(new Street(new FirstName(street)), new HouseNumber(Integer.parseInt(houseNumber)),
                new Zip(Integer.parseInt(zipCode)), new City(new CityName(city)));
        } catch (Exception e) {
            System.out.println(PLEASE_REPEAT_INPUT);
            String parts = inputAdress();
            String[] adressParts = parts.split(BLANKSPACE);
            return createAdress(adressParts[0], adressParts[1], adressParts[2], adressParts[THREE]);
        }
    }

    static Birthday createBirthday(String[] birthday) throws IOException {
        try {
            return new Birthday(LocalDate.of(Integer.parseInt(birthday[0]), Integer.parseInt(birthday[1]), Integer.parseInt(birthday[2])));
        } catch (Exception e) {
            System.out.println(PLEASE_REPEAT_INPUT);
            String parts = inputBirthday();
            String[] birthdayParts = parts.split(MINUS);
            return createBirthday(birthdayParts);
        }
    }

    /**
     * Creates a valid file path for the system. The user is informed that the path has been entered correctly by means of recursivity and
     * asked to enter it again.
     *
     * @param path path, where the user wants to store or get the file
     * @return a path, that is correct
     * @throws IOException
     */
    public static String createPath(String path) {
        if (Files.isDirectory(Paths.get(path))) {
            return path;
        } else {
            return "false";
        }
    }

    /**
     * This method is called when a person is to be changed and is used to survey the person. First, the table is displayed so that the
     * person to be changed can be selected. By entering the first and last name, this person can be selected.
     *
     * @param students a list of students
     * @param teachers a list of teachers
     * @return a human, rather teacher or student
     * @throws IOException
     */
    public static Human whichPerson(List<Student> students, List<Teacher> teachers) throws IOException {
        printTableStudentList(students);
        String[] data = new String[2];
        System.out.println("Bitte gib den Vornamen der Person ein:");
        data[0] = reader.readLine();
        System.out.println(ASK_FOR_LAST_NAME);
        data[1] = reader.readLine();
        Student person = null;
        for (Student student : students) {
            if (data[0].equals(student.getFirstName().getValue()) && data[1].equals(student.getLastName().getValue())) {
                person = student;
            }
        }
        return person;
    }

    /**
     * A very simple method to insert a birthday date.
     *
     * @return a valid birthday as string
     * @throws IOException
     */
    public static String inputBirthday() throws IOException {
        System.out.println("Bitte gib deinen Geburtstag in folgendem Format ein: YYYY-MM-DD");
        return reader.readLine();
    }

    /**
     * A very simple method to insert an adress.
     *
     * @return a valid adress
     * @throws IOException
     */
    public static String inputAdress() throws IOException {
        System.out.println("Bitte gib eine Adresse in folgendem Format ein: Straße Hausnummer, PLZ Stadt");
        return reader.readLine();
    }

    public static String inputPhone() throws IOException {
        System.out.println("Bitte gib eine Telefonnummer ein:");
        return reader.readLine();
    }

    /**
     * A method to query which personal data should be changed. The restriction is that only first name, last name and birthday can be
     * changed subsequently. For other actions the entity must be created again.
     *
     * @return a String array filled with the new data
     * @throws IOException
     */
    public static String[] whichPersonalData() throws IOException {
        System.out.println(WHICH_DATA_SHOULD_BE_CHANGED);
        final Object[][] table = new String[1][];
        table[0] = new String[]{"Vorname", "Nachname", "Geburtstag"};
        for (final Object[] row : table) {
            System.out.format(TABLEFORMAT, row);
        }
        String[] data = new String[2];
        data[0] = reader.readLine();
        System.out.println("Bitte gib die neuen Daten ein:");
        data[1] = reader.readLine();
        return data;
    }
}

