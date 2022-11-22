package de.openknowlegde.ausbildung.mbi.domain.actions;

import static de.openknowlegde.ausbildung.mbi.domain.actions.Actions.whichPerson;
import static de.openknowlegde.ausbildung.mbi.domain.actions.Actions.whichPersonalData;
import static de.openknowlegde.ausbildung.mbi.domain.actions.ClassActions.printTableStudentList;
import static de.openknowlegde.ausbildung.mbi.domain.actions.ClassActions.redundant;
import static de.openknowlegde.ausbildung.mbi.domain.actions.TeacherActions.inputPersonalData;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import de.openknowlegde.ausbildung.mbi.domain.person.Address;
import de.openknowlegde.ausbildung.mbi.domain.person.FirstName;
import de.openknowlegde.ausbildung.mbi.domain.person.LastName;
import de.openknowlegde.ausbildung.mbi.domain.person.Phone;
import de.openknowlegde.ausbildung.mbi.domain.person.Student;

@SuppressWarnings("checkstyle:RegexpSingleline")
public class StudentActions {
    private static final String LIST = "list";

    private static final String ADD = "add";
    private static final String EDIT = "edit";

    private static final String DELETE = "delete";
    //Constant for number. Used for seperating adresses
    private static final int THREE = 3;
    //Fourth part is the birthdate in input array
    private static final int FOUR = 4;
    //Used for seperation in the birthday parts
    private static final String MINUS = "-";

    private static final String BLANKSPACE = " ";

    private static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));


    /**
     * This Method is used to interact with the user, if actions are needed where students are involved. List, adding, deleting or
     * modifiying are the options to use.
     *
     * @param action      action, that will be performed
     * @param studentList list of students at the school
     * @return ArrayList of students or null
     * @throws IOException
     */

    public static List<Student> studentActions(String action, List<Student> studentList) throws IOException {
        switch (action) {
            case LIST:
                printTableStudentList(studentList);
                break;
            case ADD:
                String[] inputs = inputPersonalData();

                Set<Phone> phone = new HashSet<>();
                phone.add(new Phone(inputs[2]));
                Set<Address> addresses = new HashSet<>();
                String[] adressParts = inputs[THREE].split(BLANKSPACE);
                addresses.add(Actions.createAdress(adressParts[0], adressParts[1], adressParts[2], adressParts[THREE]));
                String[] birthday = inputs[FOUR].split(MINUS);

                studentList.add(new Student(UUID.randomUUID(), new FirstName(inputs[0]), new LastName(inputs[1]), phone,
                    addresses, Actions.createBirthday(birthday), null));

                return studentList;
            case DELETE:
                Student input = (Student)whichPerson(studentList, null);
                if (redundant()) {
                    studentList.remove(input);
                }
                return studentList;
            case EDIT:
                Student student = (Student)whichPerson(studentList, null);
                String[] data = whichPersonalData();
                student.changePersonalData(data[0], data[1]);
                break;
            default:
                break;
        }
        return studentList;
    }
}
