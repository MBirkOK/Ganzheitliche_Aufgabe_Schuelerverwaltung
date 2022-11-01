package de.openknowlegde.ausbildung.mbi.domain.persondata;

import java.util.List;
import java.util.Objects;

import de.openknowlegde.ausbildung.mbi.domain.person.Student;
import de.openknowlegde.ausbildung.mbi.domain.person.Teacher;

/**
 * The class of the class is used to represent a school class. It is important that each class has a name, a description, a level, a teacher
 * and a corresponding number of students.
 */
public class SchoolClass implements Comparable<SchoolClass> {
    private Name name;

    private String description;

    private String level;

    private Teacher teacher;

    private List<Student> students;

    /**
     * Used as a default constructor to create an object of type class. The given parameters name, description, level, teacher and students
     * are used to store in the class.
     *
     * @param name        name of the class
     * @param description a short description of the class
     * @param level       the level of the class
     * @param teacher     the primary teacher of the class
     * @param students    a list of students in the class
     */
    public SchoolClass(Name name, String description, String level, Teacher teacher, List<Student> students) {
        this.name = name;
        this.description = description;
        this.level = level;
        this.teacher = teacher;
        this.students = students;
    }

    public Name getName() {
        return name;
    }

    public void setName(Name name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    //TODO setTeacher vs addTeacher
    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    //TODO setTeacher vs addTeacher
    public boolean addTeacher(Teacher additionalTeacher) {
        if (getTeacher() != null) {
            return false;
        }
        this.setTeacher(additionalTeacher);
        return true;
    }

    /**
     * Overwrite the method from the interface Comparable.
     *
     * @param o the object to be compared.
     * @return a negative integer, zero, or a positive integer as this object is less than, equal to, or greater than the specified object.
     */
    @Override
    public int compareTo(SchoolClass o) {
        return this.name.compareTo(o.name);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SchoolClass that = (SchoolClass)o;
        return Objects.equals(name, that.name) && Objects.equals(description, that.description)
                && Objects.equals(level, that.level) && Objects.equals(teacher, that.teacher) && Objects.equals(students, that.students);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description, level, teacher, students);
    }

    private boolean validate(SchoolClass schoolClass) {
        if (checkLists(schoolClass.getTeacher(), schoolClass.getStudents()) || checkClassData(schoolClass.getName(),
            schoolClass.getDescription(), schoolClass.getLevel())) {
            return false;
        }
        return true;
    }

    private boolean checkLists(Teacher checkTeacher, List<Student> checkStudents) {
        if (checkTeacher == null || checkStudents == null) {
            return false;
        }
        return true;
    }

    private boolean checkClassData(Name className, String classDescription, String classLevel) {
        if (className == null || classDescription == null || classLevel == null) {
            return false;
        }
        return true;
    }
}
