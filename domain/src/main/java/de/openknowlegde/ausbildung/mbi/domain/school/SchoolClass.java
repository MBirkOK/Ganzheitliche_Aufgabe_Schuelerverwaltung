package de.openknowlegde.ausbildung.mbi.domain.school;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import de.openknowlegde.ausbildung.mbi.domain.person.Student;
import de.openknowlegde.ausbildung.mbi.domain.person.Teacher;

/**
 * The class of the class is used to represent a school class. It is important that each class has a name, a description, a level, a teacher
 * and a corresponding number of students.
 */
public class SchoolClass implements Comparable<SchoolClass> {
    private ClassName name;

    private Description description;

    private Level level;

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
    public SchoolClass(ClassName name, Description description, Level level, Teacher teacher, List<Student> students) {
        this.name = name;
        this.description = description;
        this.level = level;
        this.teacher = teacher;
        this.students = students;
    }

    public SchoolClass() {
        this.name = new ClassName("");
        this.description = new Description("");
        this.level = new Level("");
        this.teacher = new Teacher();
        this.students = new ArrayList<>();
    }

    public ClassName getName() {
        return name;
    }

    public void setName(ClassName name) {
        this.name = name;
    }

    public Description getDescription() {
        return description;
    }

    public void setDescription(Description description) {
        this.description = description;
    }

    public Level getLevel() {
        return level;
    }

    public Level setLevel(Level newLevel) {
        this.level = newLevel;
        return this.level;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public Teacher changeTeacher(Teacher updatingTeacher) {
        if (teacher == null || teacher.isValid()) {
            this.teacher = updatingTeacher;
            return this.teacher;
        } else {
            throw new IllegalArgumentException("Lehrer nicht valide.");
        }
    }

    public List<Student> getStudents() {
        return students;
    }

    public List<Student> changeStudents(List<Student> updatingStudents) {
        if (this.students.containsAll(updatingStudents)) {
            return this.students;
        }
        this.students = updatingStudents;
        return this.students;
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
            schoolClass.getDescription().getValue(), schoolClass.getLevel().getValue())) {
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

    private boolean checkClassData(ClassName className, String classDescription, String classLevel) {
        if (className == null || classDescription == null || classLevel == null) {
            return false;
        }
        return true;
    }
}
