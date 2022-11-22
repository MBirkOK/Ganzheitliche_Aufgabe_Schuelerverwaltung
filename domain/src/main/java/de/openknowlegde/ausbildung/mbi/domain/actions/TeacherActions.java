package de.openknowlegde.ausbildung.mbi.domain.actions;

import static de.openknowlegde.ausbildung.mbi.domain.actions.Actions.ASK_FOR_LAST_NAME;
import static de.openknowlegde.ausbildung.mbi.domain.actions.Actions.inputAdress;
import static de.openknowlegde.ausbildung.mbi.domain.actions.Actions.inputBirthday;
import static de.openknowlegde.ausbildung.mbi.domain.actions.Actions.inputPhone;
import static de.openknowlegde.ausbildung.mbi.domain.actions.Actions.whichPerson;
import static de.openknowlegde.ausbildung.mbi.domain.actions.Actions.whichPersonalData;
import static de.openknowlegde.ausbildung.mbi.domain.actions.ClassActions.printTableTeacherList;
import static de.openknowlegde.ausbildung.mbi.domain.actions.ClassActions.redundant;

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
import de.openknowlegde.ausbildung.mbi.domain.person.Teacher;

@SuppressWarnings("checkstyle:RegexpSingleline")
public class TeacherActions {
    private static final String LIST = "list";

    private static final String ADD = "add";
    private static final String EDIT = "edit";

    private static final String DELETE = "delete";
    private static final String BLANKSPACE = " ";
    private static final int THREE = 3;
    private static final int FOUR = 4;
    private static final int FIVE = 5;

    //Used for seperation in the birthday parts
    private static final String MINUS = "-";
    private static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));


    /**
     * This Method is used to interact with the user, if actions are needed where teachers are involved. List, adding, deleting or
     * modifiying are the options to use.
     *
     * @param action      action, that will be perfomed
     * @param teacherList list of teachers at the school
     * @return
     * @throws IOException
     */

    public static List<Teacher> teacherActions(String action, List<Teacher> teacherList) throws IOException {
        switch (action) {
            case LIST:
                printTableTeacherList(teacherList);
                break;
            case ADD:
                String[] inputs = inputPersonalData();

                Set<Phone> phone = new HashSet<>();
                phone.add(new Phone(inputs[2]));

                Set<Address> addresses = new HashSet<>();
                String[] adressParts = inputs[THREE].split(BLANKSPACE);
                addresses.add(Actions.createAdress(adressParts[0], adressParts[1], adressParts[2], adressParts[THREE]));

                String[] birthday = inputs[FOUR].split(MINUS);
                teacherList.add(new Teacher(UUID.randomUUID(), new FirstName(inputs[0]), new LastName(inputs[1]), phone, addresses,
                    Actions.createBirthday(birthday), null));

                return teacherList;
            case DELETE:
                Student input = (Student)whichPerson(null, teacherList);
                if (redundant()) {
                    teacherList.remove(input);
                }
                return teacherList;
            case EDIT:
                Teacher teacher = (Teacher)whichPerson(null, teacherList);
                String[] data = whichPersonalData();
                teacher.changePersonalData(data[0], data[1]);
                break;
            default:
                break;
        }
        return teacherList;
    }

    /**
     * This method is intended to simplify the entry of personal data. Especially this method would like to have a valid first name and
     * surname first. Also, a valid phone number, address and date of birth should be entered. All these data are returned in a form of an
     * array.
     *
     * @return an array with all personal data
     * @throws IOException
     */
    public static String[] inputPersonalData() throws IOException {
        String[] data = new String[FIVE];
        System.out.println("Bitte gib den Vornamen ein:");
        data[0] = reader.readLine();
        System.out.println(ASK_FOR_LAST_NAME);
        data[1] = reader.readLine();
        data[2] = inputPhone();
        data[THREE] = inputAdress();
        data[FOUR] = inputBirthday();

        return data;
    }
}
