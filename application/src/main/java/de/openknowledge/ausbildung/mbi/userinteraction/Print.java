package de.openknowledge.ausbildung.mbi.userinteraction;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Locale;

import de.openknowlegde.ausbildung.mbi.domain.person.Human;
import de.openknowlegde.ausbildung.mbi.domain.person.Student;
import de.openknowlegde.ausbildung.mbi.domain.person.Teacher;
import de.openknowlegde.ausbildung.mbi.domain.school.SchoolClass;

/**
 * The start.Print class is used to produce various outputs with context to the classes, teachers or students.
 *
 * @see Student
 * @see Teacher
 * @see Human
 * @see SchoolClass
 */
@SuppressWarnings("checkstyle:RegexpSingleline")
public class Print {

    //Used for the creation of the printed table
    private static final int FIVE = 5;

    private static final String TEACHER = "Lehrer";

    private static final String STUDENT = "Schueler";

    //Used for a printed table
    private static final int THREE = 3;

    //Used for printing teachers/students
    private static final int FOUR = 4;

    private static final String BLANKSPACE = " ";

    private static final String TABLEFORMAT = "%-25s%-25s%-25s%n";

    private static final String ASK_FOR_LAST_NAME = "Bitte gib den Nachnamen ein:";

    private static final String Y = "y";

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
        table[0] = new String[]{"Klassen", "classlist", "classadd", "classedit", "classdelete"};
        table[1] = new String[]{STUDENT, "studentlist", "studentadd", "studentedit", "studentdelete"};
        table[2] = new String[]{TEACHER, "teacherlist", "teacheradd", "teacheredit", "teacherdelete"};
        table[THREE] = new String[]{"Datenexport", "export", "", "Datenimport", "import"};
        table[FOUR] = new String[]{"Beenden", "ende", "", "Sortierung ändern", "sort"};

        for (final Object[] row : table) {
            System.out.format("%-25s%-25s%-25s%-25s%-25s%n", row);
        }
    }

    /**
     * The method is used to output all students that are stored. This output is done depending on the sorting, but with all the data stored
     * for the student. The student number and the first and last name are output in order.
     *
     * @param students the list of students in all classes
     */
    public static void printTableStudentList(List<Student> students) {
        final Object[][] table = new String[students.size()][];
        for (int i = 0; i < students.size(); i++) {
            table[i] = new String[]{STUDENT,
                students.get(i).getFirstName().getValue() + BLANKSPACE + students.get(i).getLastName().getValue(),
                String.valueOf(students.get(i).getNumber())};
        }

        for (final Object[] row : table) {
            System.out.format(TABLEFORMAT, row);
        }
    }

    /**
     * The method can be used to output all data of a class in detail. Beside the data about level, description, name, also the data of the
     * class teacher, as well as all students contained in the class are output.
     *
     * @param print the class to be printed
     */
    public static void printTableClassDetails(SchoolClass print) {
        int amount = print.getStudents().size() + FOUR;
        final Object[][] table = new String[amount][];
        table[0] = new String[]{"Stufe", print.getLevel().toString()};
        table[1] = new String[]{"Beschreibung", print.getDescription().getValue()};
        table[2] = new String[]{"Name", print.getName().getValue()};
        System.out.println(print.getTeacher().getFirstName().getValue());
        table[THREE] = new String[]{TEACHER,
            print.getTeacher().getFirstName().getValue() + BLANKSPACE + print.getTeacher().getLastName().getValue()};
        for (int i = FOUR; i < amount; i++) {
            table[i] = new String[]{STUDENT,
                print.getStudents().get(i - FOUR).getFirstName().getValue() + BLANKSPACE + print.getStudents().get(i - FOUR).getLastName()
                    .getValue()};
        }

        for (final Object[] row : table) {
            System.out.format(TABLEFORMAT, row);
        }
    }

    /**
     * The rudimentary data about a teacher is displayed. In addition to the teacher's number, the first name and surname are also
     * displayed.
     *
     * @param teachers a list of teachers
     */
    public static void printTableTeacherList(List<Teacher> teachers) {
        final Object[][] table = new String[teachers.size()][];
        int i = 0;
        for (Teacher teacher : teachers) {
            table[i] = new String[]{TEACHER, teacher.getFirstName().getValue() + BLANKSPACE + teacher.getLastName().getValue(),
                String.valueOf(teacher.getNumber().toString())};
            i++;
        }
        for (final Object[] row : table) {
            System.out.format(TABLEFORMAT, row);
        }
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

        return new String[]{format, fileName, String.valueOf(compressed)};
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
        System.out.println("Sollen die Daten vom letzten Mal genutzt werden? y/n");
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
