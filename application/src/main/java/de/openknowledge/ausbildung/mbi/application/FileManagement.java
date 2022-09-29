package de.openknowledge.ausbildung.mbi.application;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

import de.openknowlegde.ausbildung.mbi.domain.abstractclass.Human;
import de.openknowlegde.ausbildung.mbi.domain.entities.Adress;
import de.openknowlegde.ausbildung.mbi.domain.entities.Class;
import de.openknowlegde.ausbildung.mbi.domain.entities.Phone;
import de.openknowlegde.ausbildung.mbi.domain.entities.Student;
import de.openknowlegde.ausbildung.mbi.domain.entities.Teacher;

/**
 * The FileManagament is used to manage files that are intended for either export or import. Also, an automatic import of the files is
 * regulated here with system start, as well as an automatic export, as soon as the system is terminated.
 */
@SuppressWarnings("checkstyle:RegexpSingleline")
public class FileManagement {
    private static final String CSV_FILE_END = ".csv";

    private static final String COMMA = ",";

    private static final int THREE = 3;

    private static final int FOUR = 4;

    private static final int FIVE = 5;

    private static final int SIX = 6;

    private static final int SEVEN = 7;

    private static final int HUNDERT = 100;

    private static final int THOUSANDTWENTYTWO = 1024;

    private static final String TEACHER = "lehrer";

    private static final String STUDENT = "schueler";

    private static final String CLASS = "klasse";

    private static final String MINUS = "-";

    private static final String BLANKSPACE = " ";

    private static final String SLASHES = "\\";

    private static final String KLASSENLISTE_CSV = "Klassenliste.csv";

    public FileManagement() {
    }

    /**
     * With the help of the method, teachers should be able to be imported as easily as possible. The passed path serves as location where
     * the file is located. The path must also contain the file name and extension. A valid path can have the following format
     * "C://xxx/xxx/xxx/xx.csv". The method is overloaded by another variant, which is, however, set private and is only used within
     * start.FileManagement.
     *
     * @param path
     *   path with file name and filetype
     * @return a list of teachers
     * @throws IOException
     */
    public List<Teacher> importTeachers(String path) throws IOException {
        try {
            String line;
            List<Teacher> teacherList = new ArrayList<>();
            BufferedReader bufferedReader = new BufferedReader(new FileReader(path));
            while ((line = bufferedReader.readLine()) != null && !line.isEmpty()) {
                String[] lineArray = line.split(COMMA);
                if (!lineArray[FOUR].equals(TEACHER)) {
                    continue;
                }
                String[] birthday = lineArray[THREE].split(MINUS);
                List<Adress> adresses = new ArrayList<>();
                List<Phone> phones = new ArrayList<>();
                for (int i = FIVE; i < lineArray.length; i++) {
                    int alphabeticInLinePosition = getAlphabeticInLinePosition(lineArray[i]);
                    if (alphabeticInLinePosition != 0) {
                        String[] adress = lineArray[i].split(BLANKSPACE);
                        adresses.add(new Adress(adress[0], adress[1], Integer.parseInt(adress[2]), adress[THREE]));
                    } else {
                        String[] phone = lineArray[i].split(BLANKSPACE);
                        phones.add(new Phone(phone[0]));
                    }
                }
                LocalDate birthdate = LocalDate.of(Integer.parseInt(birthday[0]), Integer.parseInt(birthday[1]),
                    Integer.parseInt(birthday[2]));
                Teacher teacherToAdd = new Teacher(UUID.fromString(lineArray[0]), lineArray[1], lineArray[2], null, adresses, birthdate);

                teacherList.add(teacherToAdd);
            }
            return teacherList;
        } catch (Exception e) {
            return importTeachers(Actions.createPath(Print.inputPath()));
        }
    }

    private List<Teacher> importTeachers(BufferedReader bufferedReader) throws IOException {
        String line;
        List<Teacher> teacherList = new ArrayList<>();
        while ((line = bufferedReader.readLine()) != null) {
            String[] lineArray = line.split(COMMA);
            if (lineArray.length > FOUR) {
                if (lineArray[FOUR].equals(TEACHER)) {
                    String[] birthday = lineArray[THREE].split(MINUS);
                    List<Adress> adresses = new ArrayList<>();
                    List<Phone> phones = new ArrayList<>();
                    int lineArraySize = lineArray.length;
                    for (int i = FIVE; i < lineArraySize; i++) {
                        if (lineArray.length > SEVEN) {
                            lineArraySize = lineArraySize - 1;
                        }
                        int alphabeticInLinePosition = getAlphabeticInLinePosition(lineArray[i]);
                        if (alphabeticInLinePosition != 0) {
                            String[] adress = lineArray[i].split(BLANKSPACE);
                            adresses.add(new Adress(adress[0], adress[1], Integer.parseInt(adress[2]), adress[THREE]));
                        } else {
                            String[] phone = lineArray[i].split(BLANKSPACE);
                            phones.add(new Phone(phone[0]));
                        }
                    }
                    int day = Integer.parseInt(birthday[0]);
                    int month = Integer.parseInt(birthday[1]);
                    int year = Integer.parseInt(birthday[2]);
                    LocalDate birthdate = LocalDate.of(day, month, year);
                    UUID uuidFromString = UUID.fromString(lineArray[0]);
                    Teacher teacherToAdd = new Teacher(uuidFromString, lineArray[1], lineArray[2], null, adresses, birthdate);

                    teacherList.add(teacherToAdd);
                }
            }
        }
        return teacherList;
    }

    private static int getAlphabeticInLinePosition(String lineArray) {
        int alphabeticInLinePosition = 0;
        char[] chars = lineArray.toCharArray();
        for (char c: chars) {
            if (Character.isAlphabetic(c) && Character.isUpperCase(c)) {
                alphabeticInLinePosition = alphabeticInLinePosition + 1;
            }
        }
        return alphabeticInLinePosition;
    }

    /**
     * With the help of the method, student should be able to be imported as easily as possible. The passed path serves as location where
     * the file is located. The path must also contain the file name and extension. A valid path can have the following format
     * "C://xxx/xxx/xxx/xx.csv". The method is overloaded by another variant, which is, however, set private and is only used within
     * start.FileManagement.
     *
     * @param path
     *   path with file name and filetype
     * @return a list of students
     * @throws IOException
     */
    public List<Student> importStudents(String path) throws IOException {
        String line;
        List<Student> studentList = new ArrayList<>();
        BufferedReader bufferedReader = new BufferedReader(new FileReader(path));
        while ((line = bufferedReader.readLine()) != null && !line.isEmpty()) {
            String[] lineArray = line.split(COMMA);
            if (lineArray[FOUR].equals(STUDENT)) {
                String[] birthday = lineArray[THREE].split(MINUS);
                int day = Integer.parseInt(birthday[0]);
                int month = Integer.parseInt(birthday[1]);
                int year = Integer.parseInt(birthday[2]);
                LocalDate birthDate = LocalDate.of(day, month, year);
                UUID uuidFromString = UUID.fromString(lineArray[0]);
                studentList.add(new Student(uuidFromString, lineArray[1], lineArray[2], null, null, birthDate));
            }
        }
        return studentList;
    }

    private List<Student> importStudents(BufferedReader bufferedReader) throws IOException {
        String line;
        List<Student> studentList = new ArrayList<>();
        while ((line = bufferedReader.readLine()) != null) {
            String[] lineArray = line.split(COMMA);
            if (lineArray.length > FOUR) {
                if (lineArray[FOUR].equals(STUDENT)) {
                    String[] birthday = lineArray[THREE].split(MINUS);
                    int day = Integer.parseInt(birthday[0]);
                    int month = Integer.parseInt(birthday[1]);
                    int year = Integer.parseInt(birthday[2]);
                    LocalDate birthDate = LocalDate.of(day, month, year);
                    UUID uuidFromString = UUID.fromString(lineArray[0]);
                    studentList.add(new Student(uuidFromString, lineArray[1], lineArray[2], null, null, birthDate));
                }
            }
        }
        return studentList;
    }

    /**
     * With the help of the method, school classes should be able to be imported as easily as possible. The passed path serves as location
     * where the file is located. The path must also contain the file name and extension. A valid path can have the following format
     * "C://xxx/xxx/xxx/xx.csv". The method is overloaded by another variant, which is, however, set private and is only used within
     * start.FileManagement.
     *
     * @param path
     *   path with file name and filetype
     * @param teacherList
     *   a list of teachers who can be assigned to classes
     * @param students
     *   a list of students who can be assigned to classes
     * @return a list of school classes
     * @throws IOException
     */
    public List<Class> importClasses(String path, List<Teacher> teacherList, List<Student> students) throws IOException {
        String line;
        List<Class> classes = new ArrayList<>();
        Teacher teacherInArray = null;
        BufferedReader bufferedReader = new BufferedReader(new FileReader(path));
        while ((line = bufferedReader.readLine()) != null && !line.isEmpty()) {
            String[] lineArray = line.split(COMMA);
            if (lineArray.length <= FOUR) {
                continue;
            }
            for (Teacher teacher: teacherList) {
                if (teacher.getNumber().toString().equals(lineArray[THREE])) {
                    teacherInArray = new Teacher(teacher.getNumber(), teacher.getFirstName().getValue(), teacher.getLastName().getValue(),
                      teacher.getPhone(), teacher.getAdress(), teacher.getBirthday());
                }
            }
            List<Student> studentFromFile = new ArrayList<>();
            for (int i = FIVE; i < lineArray.length; i++) {
                for (Student student: students) {
                    if (student.getNumber().toString().equals(lineArray[i])) {
                        studentFromFile.add(student);
                    }
                }
            }
            if (lineArray[FOUR].equals(CLASS)) {
                classes.add(new Class(lineArray[0], lineArray[2], lineArray[1], teacherInArray, studentFromFile));
            }
        }
        return classes;
    }

    private List<Class> importClasses(BufferedReader bufferedReader, List<Teacher> teacherList, List<Student> students) throws IOException {
        String line;
        List<Class> classes = new ArrayList<>();
        Teacher teacherInArray = null;
        while ((line = bufferedReader.readLine()) != null) {
            String[] lineArray = line.split(COMMA);
            if (lineArray.length > FOUR) {
                for (Teacher teacher: teacherList) {
                    if (teacher.getNumber().toString().equals(lineArray[THREE])) {
                        teacherInArray = new Teacher(teacher.getNumber(), teacher.getFirstName().getValue(),
                          teacher.getLastName().getValue(), teacher.getPhone(), teacher.getAdress(), teacher.getBirthday());
                    }
                }
                List<Student> studentFromFile = new ArrayList<>();
                for (int i = FIVE; i < lineArray.length; i++) {
                    for (Student student: students) {
                        if (student.getNumber().toString().equals(lineArray[i])) {
                            studentFromFile.add(student);
                        }
                    }
                }

                if (lineArray[FOUR].equals(CLASS)) {
                    classes.add(new Class(lineArray[0], lineArray[2], lineArray[1], teacherInArray, studentFromFile));
                }
            }
        }
        return classes;
    }

    private void writeFile(String[][] csvMatrix, File file, String format) throws IOException {
        if (file.createNewFile()) {
            FileWriter pw = new FileWriter(file, Charset.forName(format));
            for (int i = 0; i < csvMatrix.length; i++) {
                for (int j = 0; j < csvMatrix[i].length; j++) {
                    if (csvMatrix[i][j] != null) {
                        pw.write(csvMatrix[i][j]);
                        pw.write(COMMA);
                    }
                }
                pw.append("\n");
            }
            pw.flush();
            pw.close();
        }
    }

    /**
     * Exports the data associated with a person. Regardless of the type, i.e. whether student or teacher. At the beginning it is checked if
     * the file already exists and then deleted accordingly. Based on a 2 dimensional matrix, the data is then loaded and saved in a valid
     * format for CSV files. For each teacher, a prefix "teacher" is inserted in the data set, correspondingly "student" is inserted for the
     * student. For both entities, the corresponding class will be saved as well. Since a student can only be in one class and each class
     * has a class teacher, this can be a valid representation.
     *
     * @param teachers
     *   a list of teachers at the school
     * @param students
     *   a list of students at the school
     * @param classList
     *   a list of school classes
     * @param path
     *   the path, where the file should be stored
     * @param format
     *   the format, i.e. UTF-8
     * @param fileName
     *   the name of the file
     * @throws Exception
     */

    public File exportPersonData(List<Teacher> teachers, List<Student> students, List<Class> classList, String path, String format,
        String fileName) throws IOException {
        File file = new File(path + SLASHES + fileName + CSV_FILE_END);
        try {
            if (file.exists()) {
                file.delete();
            }

            final String[][] csvMatrix = new String[teachers.size() + students.size() + classList.size() + 1][HUNDERT];
            int teachercount = 0;
            csvMatrix[0][0] = "ID";
            csvMatrix[0][1] = "Vorname";
            csvMatrix[0][2] = "Nachname";
            csvMatrix[0][THREE] = "Geburtstag";
            csvMatrix[0][FOUR] = "Typ";
            csvMatrix[0][FIVE] = "Adresse";
            csvMatrix[0][SIX] = "Telefon";
            csvMatrix[0][SEVEN] = "Klasse";

            for (int i = 1; i < teachers.size() + 1; i++) {
                csvMatrix[i] = addHumanToArray(csvMatrix[i], teachers.get(teachercount), classList);
                teachercount = teachercount + 1;
            }
            int studentcount = 0;

            for (int i = teachers.size() + 1; i < students.size() + teachers.size() + 1; i++) {
                csvMatrix[i] = addHumanToArray(csvMatrix[i], students.get(studentcount), classList);
                studentcount = studentcount + 1;
            }
            this.writeFile(csvMatrix, file, format);
            return file;
        } catch (Exception e) {
            path = Actions.createPath(Print.inputPath());
            return exportPersonData(teachers, students, classList, path, format, fileName);
        }
    }

    /**
     * The method is the equivalent of the "exportPersonData" method. It exports the school classes in a valid format. Via the header the
     * records can be sorted accordingly. Besides the name of the class, the data for the level, the description and the ID of the teacher
     * are saved.
     *
     * @param classList
     *   a list of school classes
     * @param path
     *   the path, where the file should be stored
     * @param format
     *   the format, i.e. UTF-8
     * @param teachers
     *   a list of teachers at the school
     * @param students
     *   a list of students at the school
     * @return
     * @throws IOException
     */
    public File exportClasses(List<Class> classList, String path, String format, List<Teacher> teachers, List<Student> students) {
        File file = new File(path + SLASHES + "Klassenliste" + CSV_FILE_END);

        try {
            if (file.exists()) {
                file.delete();
            }
            final String[][] csvMatrix = new String[teachers.size() + students.size() + classList.size() + 1][HUNDERT];
            csvMatrix[0][0] = "Name";
            csvMatrix[0][1] = "Stufe";
            csvMatrix[0][2] = "Beschreibung";
            csvMatrix[0][THREE] = "Lehrer";
            int classcount = 0;
            for (int i = 1; i < classList.size() + 1; i++) {
                csvMatrix[i] = addClassToArray(csvMatrix[i], classList.get(classcount));
                classcount = classcount + 1;
            }
            this.writeFile(csvMatrix, file, format);
            return file;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Adds the two files in which the data for the persons, i.e. teachers and students, are stored and the school classes are stored to a
     * .zip directory and saves it under the given path.
     *
     * @param firstFile
     *   First file to store in .zip
     * @param secondFile
     *   Second file to store in .zip
     * @param path
     *   the path, where the file should be stored
     */
    public void mergeToZipFile(File firstFile, File secondFile, String path) {
        try {
            byte[] buffer = new byte[THOUSANDTWENTYTWO];
            File file = new File(path + SLASHES + "compressed.zip");
            if (file.exists()) {
                file.delete();
            }
            file.createNewFile();
            FileOutputStream fos = new FileOutputStream(file.getAbsolutePath());

            ZipOutputStream zos = new ZipOutputStream(fos);
            List<File> srcFiles = new ArrayList<>();
            srcFiles.add(firstFile);
            srcFiles.add(secondFile);
            for (int i = 0; i < srcFiles.size(); i++) {
                File srcFile = srcFiles.get(i);
                FileInputStream fis = new FileInputStream(srcFile);
                zos.putNextEntry(new ZipEntry(srcFile.getName()));
                int length;
                while ((length = fis.read(buffer)) > 0) {
                    zos.write(buffer, 0, length);
                }
                zos.closeEntry();
                fis.close();
            }
            zos.close();
            firstFile.delete();
            secondFile.delete();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private String[] addHumanToArray(String[] data, Human human, List<Class> classList) {
        data[0] = human.getNumber().toString();
        data[1] = human.getFirstName().getValue();
        data[2] = human.getLastName().getValue();
        data[THREE] = human.getBirthday().toString();
        if (human instanceof Teacher) {
            data[FOUR] = TEACHER;
            for (Class schoolClass: classList) {
                if (human.getNumber().equals(schoolClass.getTeacher().getNumber())) {
                    data[SEVEN] = schoolClass.getName();
                }
            }
        } else if (human instanceof Student) {
            data[FOUR] = STUDENT;
            for (Class schoolClass: classList) {
                for (Student student: schoolClass.getStudents()) {
                    if (human.getNumber().toString().equals(student.getNumber().toString())) {
                        data[SEVEN] = schoolClass.getName();
                    }
                }
            }
        }

        addAdressAndPhoneToArray(data, human);
        return data;
    }

    private String[] addAdressAndPhoneToArray(String[] data, Human human) {
        int arrayLength = FIVE;
        int iterator = 0;

        if (human.getAdress() == null) {
            human.addAdress(new Adress().randomAdress());
        }
        for (int j = FIVE; j < human.getAdress().size() + FIVE; j++) {
            data[j] = addAdressFromHuman(iterator, human);
            arrayLength = arrayLength + 1;
            iterator = iterator + 1;
        }
        iterator = 0;
        if (human.getPhone() == null || human.getPhone().isEmpty()) {
            human.addPhone(new Phone(String.valueOf((int)(Math.random() * HUNDERT))));
        }
        for (int j = arrayLength; j < human.getPhone().size() + arrayLength; j++) {
            data[j] = human.getPhone().get(iterator).getNumber();
            iterator = iterator + 1;
        }
        return data;
    }

    @SuppressWarnings("checkstyle:Indentation") private String addAdressFromHuman(int iterator, Human human) {
        if (human.getAdress() == null) {
            human.addAdress(new Adress().randomAdress());
        }
        String data =
          human.getAdress().get(iterator).getStreet() + BLANKSPACE + human.getAdress().get(iterator).getHouseNumber() + BLANKSPACE
            + human.getAdress().get(iterator).getZipcode() + BLANKSPACE + human.getAdress().get(iterator).getCity();

        return data;
    }

    private String[] addClassToArray(String[] data, Class schoolClass) {
        data[0] = schoolClass.getName();
        data[1] = schoolClass.getLevel();
        data[2] = schoolClass.getDescription();
        data[THREE] = schoolClass.getTeacher().getNumber().toString();
        data[FOUR] = CLASS;

        return data;
    }

    /**
     * Import teachers from a given .zip file. This uses the file that contains the personal data. The prefix "lehrer" can be used to check
     * whether the teacher is valid. Then the data is transferred to the entity and written to a list. The list is returned.
     *
     * @param path
     *   the path, where the file should be imported from
     * @return a list of teachers
     * @throws IOException
     */
    public List<Teacher> importTeachersFromZip(String path) throws IOException {
        ZipFile zipFile = new ZipFile(path);
        Enumeration<? extends ZipEntry> entries = zipFile.entries();
        while (entries.hasMoreElements()) {
            ZipEntry entry = entries.nextElement();
            if (!entry.getName().equals(KLASSENLISTE_CSV)) {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(zipFile.getInputStream(entry)));
                return importTeachers(bufferedReader);
            }
        }
        return null;
    }

    /**
     * Import students from a given .zip file. This uses the file that contains the personal data. The prefix "schueler" can be used to
     * check whether the teacher is valid. Then the data is transferred to the entity and written to a list. The list is returned.
     *
     * @param path
     *   the path, where the file should be imported from
     * @return a list of students
     * @throws IOException
     */
    public List<Student> importStudentsFromZip(String path) throws IOException {
        ZipFile zipFile = new ZipFile(path);
        Enumeration<? extends ZipEntry> entries = zipFile.entries();
        while (entries.hasMoreElements()) {
            ZipEntry entry = entries.nextElement();
            if (!entry.getName().equals(KLASSENLISTE_CSV)) {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(zipFile.getInputStream(entry)));
                return importStudents(bufferedReader);
            }
        }
        return null;
    }

    /**
     * Import all classes from the given .zip file. The method also transfers the memberships from the various teacher-classes and
     * student-classes relations.
     *
     * @param path
     *   the path, where the file should be imported fro
     * @param teachers
     *   a list of teachers
     * @param students
     *   a list of students
     * @return a list of school classes
     * @throws IOException
     */
    public List<Class> importClassesFromZip(String path, List<Teacher> teachers, List<Student> students) throws IOException {
        ZipFile zipFile = new ZipFile(path);
        Enumeration<? extends ZipEntry> entries = zipFile.entries();
        while (entries.hasMoreElements()) {
            ZipEntry entry = entries.nextElement();
            if (entry.getName().equals(KLASSENLISTE_CSV)) {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(zipFile.getInputStream(entry)));
                return importClasses(bufferedReader, teachers, students);
            }
        }
        return null;
    }

}
