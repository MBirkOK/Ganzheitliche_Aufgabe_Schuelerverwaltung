import org.junit.Assert;
import org.junit.Test;

import de.openknowlegde.ausbildung.mbi.domain.adressing.Street;
import de.openknowlegde.ausbildung.mbi.domain.adressing.Zip;
import de.openknowlegde.ausbildung.mbi.domain.person.FirstName;

public class ZipTest {

    @Test
    public void testCreateZip() {
        Zip zip = new Zip(26129);

        Assert.assertEquals(26129, zip.getNumber());
    }

    @Test
    public void testChangeValue() {
        Zip zip = new Zip(26129);

        Assert.assertEquals(26129, zip.getNumber());

        zip.changeNumber(26123);
        Assert.assertEquals(26123, zip.getNumber());
    }
}
