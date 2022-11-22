package de.openknowlegde.ausbildung.mbi.domain.actions;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import de.openknowlegde.ausbildung.mbi.domain.person.Student;
import de.openknowlegde.ausbildung.mbi.domain.person.Teacher;
import de.openknowlegde.ausbildung.mbi.domain.school.ClassName;
import de.openknowlegde.ausbildung.mbi.domain.school.Description;
import de.openknowlegde.ausbildung.mbi.domain.school.Level;
import de.openknowlegde.ausbildung.mbi.domain.school.SchoolClass;

@SuppressWarnings("checkstyle:RegexpSingleline")
public class ClassActions {

    public static final String TABLEFORMAT = "%-25s%-25s%-25s%n";

    private static final String LIST = "list";

    private static final String CLASS = "Klasse";

    private static final String ADD = "add";
    private static final String EDIT = "edit";

    private static final String DELETE = "delete";
    private static final String TEACHER = "Lehrer";

    private static final String TEACHER_ENG = "teacher";

    private static final String STUDENT = "Schueler";

    private static final String STUDENT_ENG = "student";

    private static final String DESCRIPTION = "description";

    private static final String BLANKSPACE = " ";
    private static final String Y = "y";

    private static final String WHICH_DATA_SHOULD_BE_CHANGED = "Welche Daten sollen verändert werden?";

    private static final String WHICH_CLASS = "Bitte gib die Klasse an:";

    private static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));


    /**
     * This method is used to interact with the user when actions involving school classes are required. List, Add, Delete or Modify are the
     * options to be used. Since these are school class actions, the lists for teachers and students must still be passed. In case of adding
     * a student to a class, it must be ensured that the correct student is added.
     *
     * @param action          action that will be performed
     * @param schoolClassList the list of school classes
     * @param studentList     list of all students at the school
     * @param teacherList     list of all teachers at the school
     * @return ArrayList of school classes or null
     * @throws IOException
     */

    @SuppressWarnings("checkstyle:Indentation")
    public static List<SchoolClass> classActions(String action, List<SchoolClass> schoolClassList,
                                                 List<Student> studentList, List<Teacher> teacherList) throws IOException {
        switch (action) {
            case LIST:
                printTableClasses(schoolClassList);
                break;
            case ADD:
                String chosenName = chooseName();
                for (SchoolClass schoolClassInList : schoolClassList) {
                    while (schoolClassInList.getName().equals(chosenName)) {
                        chosenName = chooseName();
                    }
                }
                String[] classData = new String[]{addDescription(schoolClassList), addLevel(schoolClassList)};
                Teacher teacherInClass = addTeacherToClass(schoolClassList, teacherList);
                List<Student> studentsInClass = addStudentsToClass(schoolClassList, studentList);
                SchoolClass newSchoolClass = new SchoolClass(new ClassName(chosenName), new Description(classData[0]),
                    new Level(classData[1]), teacherInClass, studentsInClass);
                schoolClassList.add(newSchoolClass);

                return schoolClassList;
            case DELETE:
                printTableClasses(schoolClassList);
                String name = chooseName();
                if (redundant()) {
                    schoolClassList.removeIf(schoolClass -> schoolClass.getName().equals(name));
                }
                return schoolClassList;
            case EDIT:
                SchoolClass schoolClass = whichClass(schoolClassList);
                changeClassData(schoolClass, teacherList, studentList);
                return schoolClassList;
            default:
                break;
        }
        return null;
    }

    /**
     * The equivalent of the output of the teacher and the student. Here, too, the rudimentary data for the class is output, including the
     * name of the class, the level, and the first and last name of the class teacher.
     *
     * @param schoolClasses a list of school classes
     */
    public static void printTableClasses(List<SchoolClass> schoolClasses) {
        final Object[][] table = new String[schoolClasses.size()][];
        int i = 0;
        for (SchoolClass schoolClass : schoolClasses) {
            if (schoolClass.getTeacher() != null) {
                table[i] = new String[]{CLASS, schoolClass.getName().getValue(), schoolClass.getLevel().getValue(),
                    schoolClass.getTeacher().getFirstName().getValue() + BLANKSPACE
                        + schoolClass.getTeacher().getLastName().getValue()};
            } else {
                table[i] = new String[]{CLASS, schoolClass.getName().getValue(), schoolClass.getLevel().getValue(),
                    BLANKSPACE};
            }

            i++;
        }
        for (final Object[] row : table) {
            System.out.format(TABLEFORMAT, row);
        }
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
     * Adds the description of a class to a class. First the corresponding class is queried, then the corresponding description can  be
     * entered.
     *
     * @param schoolClassList a list of classes
     * @return the description of the class
     * @throws IOException
     */
    public static String addDescription(List<SchoolClass> schoolClassList) throws IOException {
        System.out.println(WHICH_CLASS);
        String className = reader.readLine();
        System.out.println("Welche Beschreibung soll angefügt werden?");
        String description = reader.readLine();
        for (SchoolClass schoolClass : schoolClassList) {
            if (schoolClass.getName().equals(className)) {
                schoolClass.setDescription(new Description(description));
            }
        }
        return description;
    }

    /**
     * Adds the level of a class to a class. First the corresponding class is queried, then the corresponding level can be entered.
     *
     * @param schoolClassList a list of classes
     * @return the level of the class
     * @throws IOException
     */
    public static String addLevel(List<SchoolClass> schoolClassList) throws IOException {
        System.out.println(WHICH_CLASS);
        String className = reader.readLine();
        System.out.println("Welcher Stufe gehört die Klasse an?");
        String level = reader.readLine();
        for (SchoolClass schoolClass : schoolClassList) {
            if (schoolClass.getName().equals(className)) {
                schoolClass.setLevel(new Level(level));
            }
        }
        return level;
    }

    /**
     * One of the most elementary methods. This adds a teacher to a class. Now you can select which student should be added to which class
     * based on the student number.
     *
     * @param schoolClassList a List of classes in the school
     * @return a list of teachers or null
     * @throws IOException
     */
    public static Teacher addTeacherToClass(List<SchoolClass> schoolClassList, List<Teacher> teacherList) throws IOException {
        printTableTeacherList(teacherList);
        System.out.println("Bitte gib die Lehrernummer an:");
        String teacher = reader.readLine();
        System.out.println("Zu welcher Klasse soll der Lehrer gehören?");
        String schoolClass = reader.readLine();
        for (SchoolClass inList : schoolClassList) {
            if (inList.getName().equals(schoolClass)) {
                for (Teacher teacherInList : teacherList) {
                    if (teacherInList.getNumber().toString().equals(teacher)) {
                        inList.changeTeacher(teacherInList);
                        return teacherInList;
                    }
                }
            }
        }
        return null;
    }

    /**
     * One of the most elementary methods. This adds a student to a class. To do this, all students without a class are first collected and
     * then output. Now you can select which student should be added to which class based on the student number.
     *
     * @param schoolClassList a List of classes in the school
     * @param studentList     a list of students in the school
     * @return a list of students or null
     * @throws IOException
     */
    public static List<Student> addStudentsToClass(List<SchoolClass> schoolClassList, List<Student> studentList) throws IOException {
        List<Student> studentsWithoutClass = getStudentsWithoutClass(schoolClassList, studentList);
        printTableStudentList(studentsWithoutClass);
        System.out.println("Bitte gib die Schuelernummer an:");
        String studentNr = reader.readLine();
        System.out.println("In welche Klasse soll der Schueler?");
        String className = reader.readLine();
        for (SchoolClass schoolClass : schoolClassList) {
            if (schoolClass.getName().equals(className)) {
                for (Student student : studentList) {
                    if (student.getNumber().toString().equals(studentNr)) {
                        schoolClass.getStudents().add(student);
                    }
                }
            }
        }
        for (SchoolClass schoolClassInList : schoolClassList) {
            if (schoolClassInList.getName().equals(className)) {
                return schoolClassInList.getStudents();
            }
        }
        return null;
    }

    static List<Student> getStudentsWithoutClass(List<SchoolClass> schoolClassList, List<Student> studentList) {
        List<Student> studentsWithoutClass = new ArrayList<>();
        for (SchoolClass schoolClass : schoolClassList) {
            for (Student student : studentList) {
                if (schoolClass.getStudents() != null && !schoolClass.getStudents().contains(student)) {
                    studentsWithoutClass.add(student);
                }
            }
        }
        return studentsWithoutClass;
    }

    public static void printTableTeacherList(List<Teacher> teachers) {
        final Object[][] table = new String[teachers.size()][];
        int i = 0;
        for (Teacher teacher : teachers) {
            if (teacher.getSchoolClass() != null) {
                table[i] = new String[]{TEACHER, teacher.getFirstName().getValue() + BLANKSPACE + teacher.getLastName().getValue(),
                    teacher.getSchoolClass().getName().getValue()};
            } else {
                table[i] = new String[]{TEACHER, teacher.getFirstName().getValue() + BLANKSPACE + teacher.getLastName().getValue(),
                    ""};
            }
            i++;
        }
        for (final Object[] row : table) {
            System.out.format(TABLEFORMAT, row);
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
            if (students.get(i).getSchoolClass() != null) {
                table[i] = new String[]{STUDENT,
                    students.get(i).getFirstName().getValue() + BLANKSPACE + students.get(i).getLastName().getValue(),
                    String.valueOf(students.get(i).getSchoolClass().getName().getValue())};
            } else {
                table[i] = new String[]{STUDENT,
                    students.get(i).getFirstName().getValue() + BLANKSPACE + students.get(i).getLastName().getValue(),
                    BLANKSPACE};
            }
        }

        for (final Object[] row : table) {
            System.out.format(TABLEFORMAT, row);
        }
    }

    /**
     * Used to modify the data of a class, this method is one of the simplest of its kind. Over an input, which data of a class are to be
     * changed, appropriate further methods from the actions are executed.
     *
     * @param schoolClass a Class in a school
     * @param teacherList a list of teachers
     * @param studentList a unused list of students
     * @throws IOException
     */
    public static void changeClassData(SchoolClass schoolClass, List<Teacher> teacherList, List<Student> studentList)
            throws IOException {
        System.out.println(WHICH_DATA_SHOULD_BE_CHANGED);
        List<SchoolClass> schoolClassList = new ArrayList<>();
        schoolClassList.add(schoolClass);
        switch (reader.readLine().toLowerCase(Locale.ROOT)) {
            case TEACHER_ENG:
                addTeacherToClass(schoolClassList, teacherList);
            case STUDENT_ENG:
                //TODO Keine Ahnung wie ich damit umgehen soll
            case DESCRIPTION:
                addDescription(schoolClassList);
            case "level":
                addLevel(schoolClassList);
            case "name":
                schoolClass.setName(new ClassName(chooseName()));
            default:
                break;
        }
    }

    /**
     * First displays all classes in the system, which can be used to enter the class name via the console in order to select a class.
     *
     * @param schoolClassList alist of classes
     * @return the chosen class
     * @throws IOException
     */
    public static SchoolClass whichClass(List<SchoolClass> schoolClassList) throws IOException {
        printTableClasses(schoolClassList);
        System.out.println(WHICH_CLASS);
        String className = reader.readLine();
        for (SchoolClass schoolClass : schoolClassList) {
            if (schoolClass.getName().equals(className)) {
                return schoolClass;
            }
        }
        return null;
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
}
