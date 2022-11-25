import org.junit.Test;
import org.junit.Assert;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;

import de.openknowlegde.ausbildung.mbi.domain.person.Birthday;
import de.openknowlegde.ausbildung.mbi.domain.person.FirstName;
import de.openknowlegde.ausbildung.mbi.domain.person.LastName;
import de.openknowlegde.ausbildung.mbi.domain.person.Student;
import de.openknowlegde.ausbildung.mbi.domain.person.Teacher;
import de.openknowlegde.ausbildung.mbi.domain.school.ClassName;
import de.openknowlegde.ausbildung.mbi.domain.school.Description;
import de.openknowlegde.ausbildung.mbi.domain.school.Level;
import de.openknowlegde.ausbildung.mbi.domain.school.SchoolClass;
import de.openknowlegde.ausbildung.mbi.domain.testdata.FirstNames;
import de.openknowlegde.ausbildung.mbi.domain.testdata.LastNames;

public class HumanTest {

    @Test
    public void testCreateEmptySchoolClassTest(){
        SchoolClass schoolClass = new SchoolClass();

        Assert.assertTrue(schoolClass.getName().getValue().isEmpty());
        Assert.assertTrue(schoolClass.getDescription().getValue().isEmpty());
        //Assert.assertThrows(IllegalArgumentException.class, schoolClass.getTeacher().isValid());
        Assert.assertEquals(schoolClass.getStudents().size(),0);
    }

    @Test
    public void testCreateFilledSchoolClassTest(){
        Teacher teacher = new Teacher(UUID.randomUUID(), new FirstName("Hans"), new LastName("Georg"), new HashSet<>(),
            new HashSet<>(), new Birthday(LocalDate.now()), null);
        List<Student> studentList = new ArrayList<>();
        for(int i =0; i<30; i++){
            Student student = new Student(UUID.randomUUID(), new FirstName(FirstNames.values()[i].toString()),
                new LastName(LastNames.values()[i].toString()), new HashSet<>(), new HashSet<>(),
                new Birthday(LocalDate.now()), null);
            studentList.add(student);
        }
        SchoolClass schoolClass = new SchoolClass(new ClassName("Test"), new Description("Test"), new Level("2"), teacher, studentList);

        Assert.assertEquals("Test", schoolClass.getName().getValue());
        Assert.assertEquals("Test", schoolClass.getDescription().getValue());
        //Assert.assertThrows(IllegalArgumentException.class, schoolClass.getTeacher().isValid());
        Assert.assertEquals(30, schoolClass.getStudents().size());

    }
}
