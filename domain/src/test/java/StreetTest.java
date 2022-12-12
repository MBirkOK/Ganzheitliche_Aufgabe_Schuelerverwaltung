import org.junit.Assert;
import org.junit.Test;

import de.openknowlegde.ausbildung.mbi.domain.adressing.HouseNumber;
import de.openknowlegde.ausbildung.mbi.domain.adressing.Street;
import de.openknowlegde.ausbildung.mbi.domain.person.FirstName;

public class StreetTest {
    @Test
    public void testCreateStreet() {
        Street street = new Street(new FirstName("Marienstraße"));

        Assert.assertEquals("Marienstraße", street.getStreet().getValue());
    }

    @Test
    public void testChangeValue() {
        Street street = new Street(new FirstName("Marienstraße"));

        Assert.assertEquals("Marienstraße", street.getStreet().getValue());

        street.changeStreet(new FirstName("Klausstraße"));
        Assert.assertEquals("Klausstraße", street.getStreet().getValue());
    }
}
