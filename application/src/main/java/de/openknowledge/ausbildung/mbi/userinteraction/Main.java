package de.openknowledge.ausbildung.mbi.userinteraction;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.openknowlegde.ausbildung.mbi.domain.example.FirstNames;
import de.openknowlegde.ausbildung.mbi.domain.example.LastNames;
import de.openknowlegde.ausbildung.mbi.domain.person.Student;
import de.openknowlegde.ausbildung.mbi.domain.person.Teacher;
import de.openknowlegde.ausbildung.mbi.domain.persondata.Name;
import de.openknowlegde.ausbildung.mbi.domain.school.SchoolClass;

@SuppressWarnings("checkstyle:RegexpSingleline")
public class Main {

    //Only import/export CSV Files
    public static final String CSV = ".csv";

    //Import/Export ZIP Files
    public static final String ZIP = ".zip";

    public static final String DEFAULT_PATH = "C:\\Users\\Marius.Birk\\IdeaProjects\\Ganzheitliche Aufgabe Schuelerverwaltung";

    public static final String DEFAULT_FORMAT = "UTF-8";

    private static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    private static HashMap<String, List<String>> map = new HashMap<>();

    private static List<Teacher> teachers;

    private static List<Student> students;

    private static List<SchoolClass> schoolClassList;

    private static String sort;

    private static final String ASC = "asc";

    private static final String CLASS = "class";

    private static final String TEACHER = "teacher";

    private static final String STUDENT = "student";

    private static final String EXPORT = "export";

    private static final String IMPORT = "import";

    private static final SecureRandom RANDOM = new SecureRandom();

    public static void main(String[] args) throws Exception {
        FileManagement fileManagement = new FileManagement();
        startUp();
        Print.printTable();
        String input = reader.readLine();

        while (!input.equals("ende")) {
            if (input.equals("sort")) {
                if (sort.equals(ASC)) {
                    reverseSort();
                } else {
                    sort = ASC;
                }
            } else if (input.equals(EXPORT)) {
                String[] fileData = Print.chooseFileData();
                String path = Actions.createPath(Print.inputPath());
                while (path.equals("false")) {
                    path = Actions.createPath(Print.inputPath());
                }
                File firstFile = fileManagement.exportPersonData(teachers, students, schoolClassList, path, fileData[0], fileData[1]);
                File secondFile = fileManagement.exportClasses(schoolClassList, firstFile.getParent(), fileData[0], teachers, students);
                if (fileData[2].equals("true")) {
                    fileManagement.mergeToZipFile(firstFile, secondFile, path);
                }
            } else if (input.equals(IMPORT)) {
                String path = Actions.createPath(Print.inputPath());
                String fileName = Print.whichFile();
                String filePath = path + "\\" + fileName;
                importData(fileManagement, filePath);
            } else {
                chooseAction(input);
            }
            Print.printTable();
            input = reader.readLine();
        }
        fileManagement.exportPersonData(teachers, students, schoolClassList, DEFAULT_PATH, DEFAULT_FORMAT, EXPORT);
        fileManagement.exportClasses(schoolClassList, DEFAULT_PATH, DEFAULT_FORMAT, teachers, students);
    }

    private static void importData(FileManagement fileManagement, String path) throws IOException {
        if (path.contains("Klassenliste")) {
            if (path.contains(CSV)) {
                schoolClassList.addAll(fileManagement.importClasses(path, teachers, students));
            } else if (path.contains(ZIP)) {
                schoolClassList.addAll(fileManagement.importClassesFromZip(path, teachers, students));
            }
        } else {
            if (path.endsWith(CSV)) {
                teachers.addAll(fileManagement.importTeachers(path));
                students.addAll(fileManagement.importStudents(path));
            } else if (path.endsWith(ZIP)) {
                teachers.addAll(fileManagement.importTeachersFromZip(path));
                students.addAll(fileManagement.importStudentsFromZip(path));
            }
        }
    }


    static Name randomFirstName() {
        return new Name(FirstNames.values()[RANDOM.nextInt(FirstNames.values().length)].name());
    }

    static Name randomLastName() {
        return new Name(LastNames.values()[RANDOM.nextInt(LastNames.values().length)].name());
    }

    private static void startUp() throws IOException {
        System.out.println("Starting Management");
        sort = ASC;
        if (Print.yesNo()) {
            FileManagement fileManagement = new FileManagement();
            teachers = fileManagement.importTeachers(DEFAULT_PATH + "\\" + EXPORT + CSV);
            students = fileManagement.importStudents(DEFAULT_PATH + "\\" + EXPORT + CSV);
            schoolClassList = fileManagement.importClasses(DEFAULT_PATH + "\\Klassenliste.csv", teachers, students);
        } else {
            teachers = FileManagement.generateTeachers();
            students = FileManagement.generateStudents();
            schoolClassList = FileManagement.generateClasses(teachers, students);
        }
        ArrayList<String> actions = new ArrayList<>();
        actions.add("add");
        actions.add("edit");
        actions.add("delete");
        actions.add("list");

        map.put(CLASS, actions);
        map.put(TEACHER, actions);
        map.put(STUDENT, actions);

        Collections.sort(schoolClassList);
        Collections.sort(teachers);
        Collections.sort(students);

        System.out.println("Start up complete.");
    }
    private static void chooseAction(String input) throws IOException {
        for (Map.Entry<String, List<String>> entry : map.entrySet()) {
            String action = input.substring(entry.getKey().length());
            if (input.substring(0, entry.getKey().length()).compareTo(CLASS) == 0) {
                Actions.classActions(action, schoolClassList, students, teachers);
                break;
            } else if (input.substring(0, entry.getKey().length()).compareTo(TEACHER) == 0) {
                Actions.teacherActions(action, teachers);
                break;
            } else if (input.substring(0, entry.getKey().length()).compareTo(STUDENT) == 0) {
                Actions.studentActions(action, students);
                break;
            }
        }
    }

    private static void reverseSort() {
        sort = "desc";
        schoolClassList.sort(Collections.reverseOrder());
        teachers.sort(Collections.reverseOrder());
        students.sort(Collections.reverseOrder());
    }
}

