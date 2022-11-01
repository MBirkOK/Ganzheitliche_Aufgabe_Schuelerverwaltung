package de.openknowledge.ausbildung.mbi.userinteraction;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import de.openknowlegde.ausbildung.mbi.domain.adressingObjects.City;
import de.openknowlegde.ausbildung.mbi.domain.adressingObjects.HouseNumber;
import de.openknowlegde.ausbildung.mbi.domain.adressingObjects.Street;
import de.openknowlegde.ausbildung.mbi.domain.adressingObjects.Zip;
import de.openknowlegde.ausbildung.mbi.domain.person.Student;
import de.openknowlegde.ausbildung.mbi.domain.person.Teacher;
import de.openknowlegde.ausbildung.mbi.domain.persondata.Adress;
import de.openknowlegde.ausbildung.mbi.domain.persondata.Name;
import de.openknowlegde.ausbildung.mbi.domain.persondata.Phone;
import de.openknowlegde.ausbildung.mbi.domain.persondata.SchoolClass;

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

    public static final String BLANKSPACE = " ";

    private static final String LIST = "list";

    private static final String ADD = "add";

    private static final String EDIT = "edit";

    private static final String DELETE = "delete";

    private static final String PLEASE_REPEAT_INPUT = "Bitte wiederhole deine Eingabe:";

    //Constant for number. Used for seperating adresses
    private static final int THREE = 3;

    //Used for seperation in the birthday parts
    private static final String MINUS = "-";

    //Fourth part is the birthdate in input array
    private static final int FOUR = 4;

    /**
     * This method is used to interact with the user when actions involving school classes are required. List, Add, Delete or Modify are the
     * options to be used. Since these are school class actions, the lists for teachers and students must still be passed. In case of adding
     * a student to a class, it must be ensured that the correct student is added.
     *
     * @param action
     *   action that will be performed
     * @param schoolClassList
     *   the list of school classes
     * @param studentList
     *   list of all students at the school
     * @param teacherList
     *   list of all teachers at the school
     * @return ArrayList of school classes or null
     * @throws IOException
     */

    @SuppressWarnings("checkstyle:Indentation")
    public static List<SchoolClass> classActions(String action, List<SchoolClass> schoolClassList,
      List<Student> studentList, List<Teacher> teacherList) throws IOException {
        switch (action) {
            case LIST:
                Print.printTableClasses(schoolClassList);
                break;
            case ADD:
                String chosenName = Print.chooseName();
                for (SchoolClass schoolClassInList : schoolClassList) {
                    while (schoolClassInList.getName().equals(chosenName)) {
                        chosenName = Print.chooseName();
                    }
                }
                String[] classData = new String[] {Print.addDescription(schoolClassList), Print.addLevel(schoolClassList)};
                Teacher teacherInClass = Print.addTeacherToClass(schoolClassList, teacherList);
                List<Student> studentsInClass = Print.addStudentsToClass(schoolClassList, studentList);
                SchoolClass newSchoolClass = new SchoolClass(new Name(chosenName), classData[0], classData[1],
                        teacherInClass, studentsInClass);
                schoolClassList.add(newSchoolClass);

                return schoolClassList;
            case DELETE:
                Print.printTableClasses(schoolClassList);
                String name = Print.chooseName();
                if (Print.redundant()) {
                    schoolClassList.removeIf(schoolClass -> schoolClass.getName().equals(name));
                }
                return schoolClassList;
            case EDIT:
                SchoolClass schoolClass = Print.whichClass(schoolClassList);
                Print.changeClassData(schoolClass, teacherList, studentList);
                return schoolClassList;
            default:
                break;
        }
        return null;
    }

    /**
     * This Method is used to interact with the user, if actions are needed where students are involved. List, adding, deleting or
     * modifiying are the options to use.
     *
     * @param action
     *   action, that will be performed
     * @param studentList
     *   list of students at the school
     * @return ArrayList of students or null
     * @throws IOException
     */

    public static List<Student> studentActions(String action, List<Student> studentList) throws IOException {
        switch (action) {
            case LIST:
                Print.printTableStudentList(studentList);
                break;
            case ADD:
                String[] inputs = Print.inputPersonalData();

                Set<Phone> phone = new HashSet<>();
                phone.add(new Phone(inputs[2]));
                Set<Adress> adresses = new HashSet<>();
                String[] adressParts = inputs[THREE].split(BLANKSPACE);
                adresses.add(createAdress(adressParts[0], adressParts[1], adressParts[2], adressParts[THREE]));
                String[] birthday = inputs[FOUR].split(MINUS);

                studentList.add(new Student(UUID.randomUUID(), new Name(inputs[0]), new Name(inputs[1]), phone, adresses, createBirthday(birthday)));

                return studentList;
            case DELETE:
                Student input = (Student)Print.whichPerson(studentList, null);
                if (Print.redundant()) {
                    studentList.remove(input);
                }
                return studentList;
            case EDIT:
                Student student = (Student)Print.whichPerson(studentList, null);
                String[] data = Print.whichPersonalData();
                student.changePersonalData(data[0], data[1]);
                break;
            default:
                break;
        }
        return studentList;
    }

    /**
     * This Method is used to interact with the user, if actions are needed where teachers are involved. List, adding, deleting or
     * modifiying are the options to use.
     *
     * @param action
     *   action, that will be perfomed
     * @param teacherList
     *   list of teachers at the school
     * @return
     * @throws IOException
     */

    public static List<Teacher> teacherActions(String action, List<Teacher> teacherList) throws IOException {
        switch (action) {
            case LIST:
                Print.printTableTeacherList(teacherList);
                break;
            case ADD:
                String[] inputs = Print.inputPersonalData();

                Set<Phone> phone = new HashSet<>();
                phone.add(new Phone(inputs[2]));

                Set<Adress> adresses = new HashSet<>();
                String[] adressParts = inputs[THREE].split(BLANKSPACE);
                adresses.add(createAdress(adressParts[0], adressParts[1], adressParts[2], adressParts[THREE]));

                String[] birthday = inputs[FOUR].split(MINUS);
                teacherList.add(new Teacher(UUID.randomUUID(), new Name(inputs[0]), new Name(inputs[1]), phone, adresses,
                    createBirthday(birthday)));

                return teacherList;
            case DELETE:
                Student input = (Student)Print.whichPerson(null, teacherList);
                if (Print.redundant()) {
                    teacherList.remove(input);
                }
                return teacherList;
            case EDIT:
                Teacher teacher = (Teacher)Print.whichPerson(null, teacherList);
                String[] data = Print.whichPersonalData();
                teacher.changePersonalData(data[0], data[1]);
                break;
            default:
                break;
        }
        return teacherList;
    }

    /**
     * Create an address for a person. To store a valid address for a person, the user must enter it in the valid format. He will be
     * reminded of this before entering. The method is repeated using recursivity until an address is entered in the valid format.
     *
     * @param street
     *   the street, where the person lives
     * @param houseNumber
     *   number of the house
     * @param zipCode
     *   postal code of the city or district
     * @param city
     *   city name
     * @return new Adress of the given Information
     * @throws IOException
     */
    private static Adress createAdress(String street, String houseNumber, String zipCode, String city) throws IOException {
        try {
            return new Adress(new Street(new Name(street)), new HouseNumber(Integer.parseInt(houseNumber)),
                new Zip(Integer.parseInt(zipCode)), new City(new Name(city)));
        } catch (Exception e) {
            System.out.println(PLEASE_REPEAT_INPUT);
            String parts = Print.inputAdress();
            String[] adressParts = parts.split(BLANKSPACE);
            return createAdress(adressParts[0], adressParts[1], adressParts[2], adressParts[THREE]);
        }
    }

    private static LocalDate createBirthday(String[] birthday) throws IOException {
        try {
            return LocalDate.of(Integer.parseInt(birthday[0]), Integer.parseInt(birthday[1]), Integer.parseInt(birthday[2]));
        } catch (Exception e) {
            System.out.println(PLEASE_REPEAT_INPUT);
            String parts = Print.inputBirthday();
            String[] birthdayParts = parts.split(MINUS);
            return createBirthday(birthdayParts);
        }
    }

    /**
     * Creates a valid file path for the system. The user is informed that the path has been entered correctly by means of recursivity and
     * asked to enter it again.
     *
     * @param path
     *   path, where the user wants to store or get the file
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
}

