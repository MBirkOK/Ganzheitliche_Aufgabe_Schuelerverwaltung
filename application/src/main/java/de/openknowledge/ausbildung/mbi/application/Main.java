package de.openknowledge.ausbildung.mbi.application;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.SecureRandom;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

import de.openknowlegde.ausbildung.mbi.domain.data.FirstNames;
import de.openknowlegde.ausbildung.mbi.domain.data.LastNames;
import de.openknowlegde.ausbildung.mbi.domain.entities.Adress;
import de.openknowlegde.ausbildung.mbi.domain.entities.Class;
import de.openknowlegde.ausbildung.mbi.domain.entities.Phone;
import de.openknowlegde.ausbildung.mbi.domain.entities.Student;
import de.openknowlegde.ausbildung.mbi.domain.entities.Teacher;

@SuppressWarnings("checkstyle:RegexpSingleline")
public class Main {

    public static final String CSV = ".csv";

    public static final String ZIP = ".zip";

    private static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    private static HashMap<String, List<String>> map = new HashMap<>();

    private static List<Teacher> teachers;

    private static List<Student> students;

    private static List<Class> classList;

    private static String sort;

    private static final int THREE = 3;

    private static final int FIVE = 5;

    private static final int NINETEENSEVENTY = 1970;

    private static final int TWOTHOUSANDFIVETEEN = 2015;

    private static final int TWELVE = 12;

    private static final int TWENTYSIX = 26;

    private static final int THIRTYONE = 31;

    private static final int FOURTY = 40;

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
                File firstFile = fileManagement.exportPersonData(teachers, students, classList, path, fileData[0], fileData[1]);
                File secondFile = fileManagement.exportClasses(classList, firstFile.getParent(), fileData[0], teachers, students);
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
        fileManagement.exportPersonData(teachers, students, classList,
            "C:\\Users\\Marius.Birk\\IdeaProjects\\Ganzheitliche Aufgabe Schuelerverwaltung", "UTF-8", EXPORT);
    }

    private static void importData(FileManagement fileManagement, String path) throws IOException {
        if (path.contains("Klassenliste")) {
            if (path.contains(CSV)) {
                classList.addAll(fileManagement.importClasses(path, teachers, students));
            } else if (path.contains(ZIP)) {
                classList.addAll(fileManagement.importClassesFromZip(path, teachers, students));
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

    private static UUID generateId() {
        return UUID.randomUUID();
    }

    private static List<Teacher> generateTeachers() {

        List<Teacher> teacherList = new ArrayList<>();
        for (int i = 0; i < FIVE; i++) {
            UUID id = generateId();
            if (teachers == null) {
                teachers = new ArrayList<>();
            }
            for (Teacher eachTeacher: teachers) {
                while (id == eachTeacher.getNumber()) {
                    id = generateId();
                }
            }
            List<Phone> phones = new ArrayList<>();
            phones.add(new Phone(String.valueOf(i)));

            long minDay = LocalDate.of(NINETEENSEVENTY, 1, 1).toEpochDay();
            long maxDay = LocalDate.of(TWOTHOUSANDFIVETEEN, TWELVE, THIRTYONE).toEpochDay();
            long randomDay = ThreadLocalRandom.current().nextLong(minDay, maxDay);
            LocalDate randomDate = LocalDate.ofEpochDay(randomDay);

            Adress adress = new Adress();
            List<Adress> set = new ArrayList<>();
            set.add(adress.randomAdress());

            Teacher teacher = new Teacher(id, randomFirstName(), randomLastName(), phones, set, randomDate);
            teacherList.add(teacher);
        }
        return teacherList;
    }

    private static List<Student> generateStudents() {
        List<Student> studentList = new ArrayList<>();
        for (int i = 0; i < FOURTY; i++) {
            List<Phone> phones = new ArrayList<>();
            phones.add(new Phone(String.valueOf(i)));

            long minDay = LocalDate.of(NINETEENSEVENTY, 1, 1).toEpochDay();
            long maxDay = LocalDate.of(TWOTHOUSANDFIVETEEN, TWELVE, THIRTYONE).toEpochDay();
            long randomDay = ThreadLocalRandom.current().nextLong(minDay, maxDay);
            LocalDate randomDate = LocalDate.ofEpochDay(randomDay);

            Adress adress = new Adress();
            List<Adress> set = new ArrayList<>();
            set.add(adress.randomAdress());

            studentList.add(new Student(generateId(), randomFirstName(), randomLastName(), phones, set, randomDate));
        }
        return studentList;
    }

    private static List<Class> generateClasses(List<Teacher> teacherList, List<Student> studentList) {
        List<Class> classes = new ArrayList<>();
        for (int i = 0; i < THREE; i++) {
            int randomInt = (int)(Math.random() * teacherList.size());
            Random r = new Random();
            char c = (char)(r.nextInt(TWENTYSIX) + 'a');
            List<Student> studentArrayList = new ArrayList<>();
            for (int j = 0; j < (studentList.size() / THREE); j++) {
                studentArrayList.add(studentList.get(j));
            }
            Class cl = new Class(c + String.valueOf(randomInt), "Dies ist eine Schulklasse.", String.valueOf(randomInt),
                teacherList.get(randomInt), studentList);
            classes.add(cl);
        }
        return classes;
    }

    private static String randomFirstName() {
        return FirstNames.values()[RANDOM.nextInt(FirstNames.values().length)].name();
    }

    private static String randomLastName() {
        return LastNames.values()[RANDOM.nextInt(LastNames.values().length)].name();
    }

    private static void startUp() throws IOException {
        System.out.println("Starting Management");
        sort = ASC;
        System.out.println("Sollen die Daten vom letzten Mal genutzt werden? y/n");
        String defaultPath = "C:\\Users\\Marius.Birk\\IdeaProjects\\Ganzheitliche Aufgabe Schuelerverwaltung\\export.csv";
        if (Print.yesNo()) {
            FileManagement fileManagement = new FileManagement();
            teachers = fileManagement.importTeachers(defaultPath);
            students = fileManagement.importStudents(defaultPath);
            classList = fileManagement.importClasses(defaultPath, teachers, students);
        } else {
            teachers = generateTeachers();
            students = generateStudents();
            classList = generateClasses(teachers, students);
        }
        ArrayList<String> actions = new ArrayList<>();
        actions.add("add");
        actions.add("edit");
        actions.add("delete");
        actions.add("list");

        map.put(CLASS, actions);
        map.put(TEACHER, actions);
        map.put(STUDENT, actions);

        Collections.sort(classList);
        Collections.sort(teachers);
        Collections.sort(students);

        System.out.println("Start up complete.");
    }

    private static void chooseAction(String input) throws IOException {
        for (Map.Entry<String, List<String>> entry: map.entrySet()) {
            String action = input.substring(entry.getKey().length());
            if (input.substring(0, entry.getKey().length()).compareTo(CLASS) == 0) {
                Actions.classActions(action, classList, students, teachers);
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
        classList.sort(Collections.reverseOrder());
        teachers.sort(Collections.reverseOrder());
        students.sort(Collections.reverseOrder());
    }
}

