import de.openknowledge.ausbildung.mbi.userinteraction.FileManagement;
import de.openknowlegde.ausbildung.mbi.domain.school.SchoolClass;
import de.openknowlegde.ausbildung.mbi.domain.person.Student;
import de.openknowlegde.ausbildung.mbi.domain.person.Teacher;

import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class FileManagementTest {

    private FileManagement fileManagement = new FileManagement();

    private String CSV_FILE_PATH = Paths.get("").toAbsolutePath().toString();

    private List<Teacher> teacherTestData = new ArrayList<>();

    private List<Student> studentTestData = new ArrayList<>();

    private List<SchoolClass> classTestData = new ArrayList<>();

    public void generateTestDataFile() throws IOException {
        teacherTestData = fileManagement.generateTeachers();
        studentTestData = fileManagement.generateStudents();
        classTestData = fileManagement.generateClasses(teacherTestData, studentTestData);
        fileManagement.exportPersonData(teacherTestData, studentTestData, classTestData, CSV_FILE_PATH, "UTF-8", "testdata");
        fileManagement.exportClasses(classTestData, CSV_FILE_PATH, "UTF-8", teacherTestData, studentTestData);
    }

    public void deleteCSVFileTestData() {
        File file = new File(CSV_FILE_PATH + "\\testdata.csv");
        file.delete();
    }

    /**
     * This test first generates a file with testdata and then imports it. After that it checks if all items have been imported. For that it
     * checks the right length of both lists.
     *
     * @throws IOException
     */

    public void importTeacherAndCheckOnLengthTest() throws IOException {
        List<Teacher> imported = fileManagement.importTeachers(CSV_FILE_PATH + "\\testdata.csv");
        Assert.assertFalse(imported.isEmpty());
        Assert.assertEquals(teacherTestData.size(), imported.size());
    }

    /**
     * This test first generates a file with testdata and then imports it. After that it checks if all items have been imported. For that it
     * checks the right length of both lists.
     *
     * @throws IOException
     */
    public void testImportStudentAndCheckOnLengthTest() throws IOException {
        List<Student> imported = fileManagement.importStudents(CSV_FILE_PATH + "\\testdata.csv");
        Assert.assertFalse(imported.isEmpty());
        Assert.assertEquals(studentTestData.size(), imported.size());
    }

    /**
     * This test first generates a file with testdata and then imports it. After that it checks if all items have been imported. For that it
     * checks the right length of both lists.
     *
     * @throws IOException
     */
    public void testImportClassesAndCheckOnLengthTest() throws IOException {
        List<Student> importedStudents = fileManagement.importStudents(CSV_FILE_PATH + "\\testdata.csv");
        List<Teacher> importedTeachers = fileManagement.importTeachers(CSV_FILE_PATH + "\\testdata.csv");
        List<SchoolClass> imported = fileManagement.importClasses(CSV_FILE_PATH + "\\testdata.csv", importedTeachers, importedStudents);
        Assert.assertFalse(imported.isEmpty());
        Assert.assertEquals(teacherTestData.size(), imported.size());
    }
}
