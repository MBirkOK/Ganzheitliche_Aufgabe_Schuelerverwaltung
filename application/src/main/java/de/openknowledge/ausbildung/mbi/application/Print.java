package de.openknowledge.ausbildung.mbi.application;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import de.openknowlegde.ausbildung.mbi.domain.abstractclass.Human;
import de.openknowlegde.ausbildung.mbi.domain.entities.Class;
import de.openknowlegde.ausbildung.mbi.domain.entities.Student;
import de.openknowlegde.ausbildung.mbi.domain.entities.Teacher;

/**
 * The start.Print class is used to produce various outputs with context to the classes, teachers or students.
 *
 * @see Student
 * @see Teacher
 * @see Human
 * @see Class
 */
@SuppressWarnings("checkstyle:RegexpSingleline")
public class Print {

    private static final int FIVE = 5;

    private static final String TEACHER = "Lehrer";

    private static final String STUDENT = "Schueler";

    private static final int THREE = 3;

    private static final int FOUR = 4;

    private static final String BLANKSPACE = " ";

    private static final String TABLEFORMAT = "%-25s%-25s%-25s%n";

    private static final String ASK_FOR_LAST_NAME = "Bitte gib den Nachnamen ein:";

    private static final String Y = "y";

    private static final String WHICH_DATA_SHOULD_BE_CHANGED = "Welche Daten sollen verändert werden?";

    private static final String WHICH_CLASS = "Bitte gib die Klasse an:";

    private static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    /**
     * This is the default selection of actions the user can perform with the stored data. On the left side are the data types (teacher,
     * student, class) and in the second to the fifth column are the different actions that can be performed. Exceptions are the commands
     * for sorting, exporting and importing the data. Here, too, you can see that the terms are arranged on the left side and the commands
     * are right next to them.
     */
    public static void printTable() {
        System.out.println("Tippe das rechts stehende Wort ein um die entsprechende Liste zu bekommen.");
        final Object[][] table = new String[FIVE][];
        table[0] = new String[] {"Klassen", "classlist", "classadd", "classedit", "classdelete"};
        table[1] = new String[] {STUDENT, "studentlist", "studentadd", "studentedit", "studentdelete"};
        table[2] = new String[] {TEACHER, "teacherlist", "teacheradd", "teacheredit", "teacherdelete"};
        table[THREE] = new String[] {"Datenexport", "export", "", "Datenimport", "import"};
        table[FOUR] = new String[] {"Beenden", "ende", "", "Sortierung ändern", "sort"};

        for (final Object[] row: table) {
            System.out.format("%-25s%-25s%-25s%-25s%-25s%n", row);
        }
    }

    /**
     * The method is used to output all students that are stored. This output is done depending on the sorting, but with all the data stored
     * for the student. The student number and the first and last name are output in order.
     *
     * @param students
     *   the list of students in all classes
     */
    public static void printTableStudentList(List<Student> students) {
        final Object[][] table = new String[students.size()][];
        for (int i = 0; i < students.size(); i++) {
            table[i] = new String[] {STUDENT,
              students.get(i).getFirstName().getValue() + BLANKSPACE + students.get(i).getLastName().getValue(),
              String.valueOf(students.get(i).getNumber())};
        }

        for (final Object[] row: table) {
            System.out.format(TABLEFORMAT, row);
        }
    }

    /**
     * The method can be used to output all data of a class in detail. Beside the data about level, description, name, also the data of the
     * class teacher, as well as all students contained in the class are output.
     *
     * @param print
     *   the class to be printed
     */
    public static void printTableClassDetails(Class print) {
        int amount = print.getStudents().size() + FOUR;
        final Object[][] table = new String[amount][];
        table[0] = new String[] {"Stufe", print.getLevel()};
        table[1] = new String[] {"Beschreibung", print.getDescription()};
        table[2] = new String[] {"Name", print.getName()};
        System.out.println(print.getTeacher().getFirstName().getValue());
        table[THREE] = new String[] {TEACHER,
          print.getTeacher().getFirstName().getValue() + BLANKSPACE + print.getTeacher().getLastName().getValue()};
        for (int i = FOUR; i < amount; i++) {
            table[i] = new String[] {STUDENT,
              print.getStudents().get(i - FOUR).getFirstName().getValue() + BLANKSPACE + print.getStudents().get(i - FOUR).getLastName()
                .getValue()};
        }

        for (final Object[] row: table) {
            System.out.format(TABLEFORMAT, row);
        }
    }

    /**
     * The rudimentary data about a teacher is displayed. In addition to the teacher's number, the first name and surname are also
     * displayed.
     *
     * @param teachers
     *   a list of teachers
     */
    public static void printTableTeacherList(List<Teacher> teachers) {
        final Object[][] table = new String[teachers.size()][];
        int i = 0;
        for (Teacher teacher: teachers) {
            table[i] = new String[] {TEACHER, teacher.getFirstName().getValue() + BLANKSPACE + teacher.getLastName().getValue(),
              String.valueOf(teacher.getNumber().toString())};
            i++;
        }
        for (final Object[] row: table) {
            System.out.format(TABLEFORMAT, row);
        }
    }

    /**
     * The equivalent of the output of the teacher and the student. Here, too, the rudimentary data for the class is output, including the
     * name of the class, the level, and the first and last name of the class teacher.
     *
     * @param classes
     *   a list of school classes
     */
    public static void printTableClasses(List<Class> classes) {
        final Object[][] table = new String[classes.size()][];
        int i = 0;
        for (Class schoolClass: classes) {
            table[i] = new String[] {"Klasse", schoolClass.getName(), schoolClass.getLevel(),
              schoolClass.getTeacher().getFirstName().getValue() + BLANKSPACE + schoolClass.getTeacher().getLastName().getValue()};
            i++;
        }
        for (final Object[] row: table) {
            System.out.format(TABLEFORMAT, row);
        }
    }

    /**
     * This method is intended to simplify the entry of personal data. Especially this method would like to have a valid first name and
     * surname first. Also, a valid phone number, address and date of birth should be entered. All these data are returned in the form of an
     * array.
     *
     * @return an array with all personal data
     * @throws IOException
     */
    public static String[] inputPersonalData() throws IOException {
        String[] data = new String[FIVE];
        System.out.println("Bitte gib den Vornamen ein:");
        data[0] = reader.readLine();
        System.out.println(ASK_FOR_LAST_NAME);
        data[1] = reader.readLine();
        data[2] = inputPhone();
        data[THREE] = inputAdress();
        data[FOUR] = inputBirthday();

        return data;
    }

    /**
     * This method is called when a person is to be changed and is used to survey the person. First, the table is displayed so that the
     * person to be changed can be selected. By entering the first and last name, this person can be selected.
     *
     * @param students
     *   a list of students
     * @param teachers
     *   a list of teachers
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
        for (Student student: students) {
            if (data[0].equals(student.getFirstName().getValue()) && data[1].equals(student.getLastName().getValue())) {
                person = student;
            }
        }
        return person;
    }

    /**
     * A simple redundant query whether the action should really be performed.
     *
     * @return true/false, depends on user decision.
     * @throws IOException
     */
    public static boolean redundant() throws IOException {
        System.out.println("Soll die Aktion wirklich durchgeführt werden? y/n");
        String input = reader.readLine();
        return input.toLowerCase(Locale.ROOT).equals(Y);
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
        table[0] = new String[] {"Vorname", "Nachname", "Geburtstag"};
        for (final Object[] row: table) {
            System.out.format(TABLEFORMAT, row);
        }
        String[] data = new String[2];
        data[0] = reader.readLine();
        System.out.println("Bitte gib die neuen Daten ein:");
        data[1] = reader.readLine();
        return data;
    }

    /**
     * Cornerstone for exporting the records. In addition to the query for the encoding, the file name is also queried and a compression of
     * the data is also requested.
     *
     * @return a String array with essentiel data
     * @throws IOException
     */
    public static String[] chooseFileData() throws IOException {
        String format = chooseFileFormat();
        String fileName = askForFileName();
        System.out.println("Soll eine .zip Datei erstellt werden? j/n");
        boolean compressed = false;
        if (reader.readLine().equalsIgnoreCase("j")) {
            compressed = true;
        }

        return new String[] {format, fileName, String.valueOf(compressed)};
    }

    private static String chooseFileFormat() throws IOException {
        String format = "UTF-8";
        try {
            do {
                System.out.println("Soll ein bestimmtes Format eingehalten werden?");
                format = reader.readLine();
            } while (format.isEmpty() || isBlank(format) || !Charset.isSupported(format));
        } catch (Exception e) {
            chooseFileFormat();
        }
        return format;
    }

    public static String askForFileName() throws IOException {
        System.out.println("Wie soll die Datei heißen?");
        return reader.readLine();
    }

    public static String whichFile() throws IOException {
        System.out.println("Welche Datei soll importiert werden?");
        return reader.readLine();
    }

    /**
     * Simple method for entering a generic name.
     *
     * @return a String with a name
     * @throws IOException
     */
    public static String chooseName() throws IOException {
        System.out.println("Bitte gib einen Namen ein:");
        return reader.readLine();
    }

    /**
     * One of the most elementary methods. This adds a student to a class. To do this, all students without a class are first collected and
     * then output. Now you can select which student should be added to which class based on the student number.
     *
     * @param classList
     *   a List of classes in the school
     * @param studentList
     *   a list of students in the school
     * @return a list of students or null
     * @throws IOException
     */
    public static List<Student> addStudentsToClass(List<Class> classList, List<Student> studentList) throws IOException {
        List<Student> studentsWithoutClass = getStudentsWithoutClass(classList, studentList);
        printTableStudentList(studentsWithoutClass);
        System.out.println("Bitte gib die Schuelernummer an:");
        String studentNr = reader.readLine();
        System.out.println("In welche Klasse soll der Schueler?");
        String className = reader.readLine();
        for (Class schoolClass: classList) {
            if (schoolClass.getName().equals(className)) {
                for (Student student: studentList) {
                    if (student.getNumber().toString().equals(studentNr)) {
                        schoolClass.getStudents().add(student);
                    }
                }
            }
        }
        for (Class classInList: classList) {
            if (classInList.getName().equals(className)) {
                return classInList.getStudents();
            }
        }
        return null;
    }

    private static List<Student> getStudentsWithoutClass(List<Class> classList, List<Student> studentList) {
        List<Student> studentsWithoutClass = new ArrayList<>();
        for (Class schoolClass: classList) {
            for (Student student: studentList) {
                if (schoolClass.getStudents() != null && !schoolClass.getStudents().contains(student)) {
                    studentsWithoutClass.add(student);
                }
            }
        }
        return studentsWithoutClass;
    }

    /**
     * One of the most elementary methods. This adds a teacher to a class. Now you can select which student should be added to which class
     * based on the student number.
     *
     * @param classList
     *   a List of classes in the school
     * @return a list of teachers or null
     * @throws IOException
     */
    public static Teacher addTeacherToClass(List<Class> classList, List<Teacher> teacherList) throws IOException {
        printTableTeacherList(teacherList);
        System.out.println("Bitte gib die Lehrernummer an:");
        String teacher = reader.readLine();
        System.out.println("Zu welcher Klasse soll der Lehrer gehören?");
        String schoolClass = reader.readLine();
        for (Class inList: classList) {
            if (inList.getName().equals(schoolClass)) {
                for (Teacher teacherInList: teacherList) {
                    if (teacherInList.getNumber().toString().equals(teacher)) {
                        inList.setTeacher(teacherInList);
                        return teacherInList;
                    }
                }
            }
        }
        return null;
    }

    /**
     * Adds the level of a class to a class. First the corresponding class is queried, then the corresponding level can be entered.
     *
     * @param classList
     *   a list of classes
     * @return the level of the class
     * @throws IOException
     */
    public static String addLevel(List<Class> classList) throws IOException {
        System.out.println(WHICH_CLASS);
        String className = reader.readLine();
        System.out.println("Welcher Stufe gehört die Klasse an?");
        String level = reader.readLine();
        for (Class schoolClass: classList) {
            if (schoolClass.getName().equals(className)) {
                schoolClass.setLevel(level);
            }
        }
        return level;
    }

    /**
     * Adds the description of a class to a class. First the corresponding class is queried, then the corresponding description can  be
     * entered.
     *
     * @param classList
     *   a list of classes
     * @return the description of the class
     * @throws IOException
     */
    public static String addDescription(List<Class> classList) throws IOException {
        System.out.println(WHICH_CLASS);
        String className = reader.readLine();
        System.out.println("Welche Beschreibung soll angefügt werden?");
        String description = reader.readLine();
        for (Class schoolClass: classList) {
            if (schoolClass.getName().equals(className)) {
                schoolClass.setDescription(description);
            }
        }
        return description;
    }

    /**
     * First displays all classes in the system, which can be used to enter the class name via the console in order to select a class.
     *
     * @param classList
     *   alist of classes
     * @return the chosen class
     * @throws IOException
     */
    public static Class whichClass(List<Class> classList) throws IOException {
        printTableClasses(classList);
        System.out.println(WHICH_CLASS);
        String className = reader.readLine();
        for (Class schoolClass: classList) {
            if (schoolClass.getName().equals(className)) {
                return schoolClass;
            }
        }
        return null;
    }

    /**
     * Used to modify the data of a class, this method is one of the simplest of its kind. Over an input, which data of a class are to be
     * changed, appropriate further methods from the actions are executed.
     *
     * @param schoolClass
     *   a Class in a school
     * @param teacherList
     *   a list of teachers
     * @param studentList
     *   a unused list of students
     * @throws IOException
     */
    public static void changeClassData(Class schoolClass, List<Teacher> teacherList, List<Student> studentList) throws IOException {
        System.out.println(WHICH_DATA_SHOULD_BE_CHANGED);
        List<Class> classList = new ArrayList<>();
        classList.add(schoolClass);
        switch (reader.readLine().toLowerCase(Locale.ROOT)) {
            case "teacher":
                addTeacherToClass(classList, teacherList);
            case "student":
                //TODO Keine Ahnung wie ich damit umgehen soll
            case "description":
                addDescription(classList);
            case "level":
                addLevel(classList);
            case "name":
                schoolClass.setName(chooseName());
            default:
                break;
        }
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

    private static String inputPhone() throws IOException {
        System.out.println("Bitte gib eine Telefonnummer ein:");
        return reader.readLine();
    }

    /**
     * A very simple method to insert a file path.
     *
     * @return a file path as string
     * @throws IOException
     */
    public static String inputPath() throws IOException {
        System.out.println("Bitte gib einen Pfad ein:");
        return reader.readLine();
    }

    /**
     * This method returns a boolean equals to the choice the user made.
     */

    public static boolean yesNo() throws IOException {
        return reader.readLine().toLowerCase(Locale.ROOT).equals(Y);
    }

    private static boolean isBlank(String charset) {
        charset.replaceAll("\\s", "");
        charset.trim();
        if (charset.isEmpty()) {
            return true;
        }
        return false;
    }

}
