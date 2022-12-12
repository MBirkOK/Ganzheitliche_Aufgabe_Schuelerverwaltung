import org.junit.Assert;
import org.junit.Test;

import de.openknowlegde.ausbildung.mbi.domain.person.Phone;

public class PhoneTest {

    @Test
    public void testCreateFilledPhone(){
        Phone phone = new Phone("0152/33445566");

        Assert.assertEquals("0152/33445566", phone.getNumber());
    }

}
