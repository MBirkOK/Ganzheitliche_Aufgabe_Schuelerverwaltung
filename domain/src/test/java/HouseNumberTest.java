import org.junit.Assert;
import org.junit.Test;

import de.openknowlegde.ausbildung.mbi.domain.adressing.CityName;
import de.openknowlegde.ausbildung.mbi.domain.adressing.HouseNumber;

public class HouseNumberTest {
    @Test
    public void testCreateHouseNumber(){
        HouseNumber houseNumber = new HouseNumber(12);

        Assert.assertEquals(12, houseNumber.getNumber());
    }

    @Test
    public void testChangeValue(){
        HouseNumber houseNumber = new HouseNumber(12);

        Assert.assertEquals(12, houseNumber.getNumber());

        houseNumber.changeNumber(22);
        Assert.assertEquals(22, houseNumber.getNumber());
    }
}
