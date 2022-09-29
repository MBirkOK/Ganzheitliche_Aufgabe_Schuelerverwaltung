package de.openknowlegde.ausbildung.mbi.domain.entities;

import java.util.List;

/**
 * The class of the class is used to represent a school class. It is important that each class has a name, a description, a level, a teacher
 * and a corresponding number of students.
 */
public class Class implements Comparable<Class> {
    private String name;

    private String description;

    private String level;

    private Teacher teacher;

    private List<Student> students;

    /**
     * Used as a default constructor to create an object of type class. The given parameters name, description, level, teacher and students
     * are used to store in the class.
     *
     * @param name
     *   name of the class
     * @param description
     *   a short description of the class
     * @param level
     *   the level of the class
     * @param teacher
     *   the primary teacher of the class
     * @param students
     *   a list of students in the class
     */
    public Class(String name, String description, String level, Teacher teacher, List<Student> students) {
        this.name = name;
        this.description = description;
        this.level = level;
        this.teacher = teacher;
        this.students = students;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
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
     * @param o
     *   the object to be compared.
     * @return a negative integer, zero, or a positive integer as this object is less than, equal to, or greater than the specified object.
     */
    @Override public int compareTo(Class o) {
        return this.name.compareTo(o.name);
    }
}
