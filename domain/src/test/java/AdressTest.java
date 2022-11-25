//import org.junit.Assert;
//import org.junit.Test;
//
//import de.openknowlegde.ausbildung.mbi.domain.adressing.City;
//import de.openknowlegde.ausbildung.mbi.domain.adressing.CityName;
//import de.openknowlegde.ausbildung.mbi.domain.adressing.HouseNumber;
//import de.openknowlegde.ausbildung.mbi.domain.adressing.Street;
//import de.openknowlegde.ausbildung.mbi.domain.adressing.Zip;
//import de.openknowlegde.ausbildung.mbi.domain.person.Address;
//import de.openknowlegde.ausbildung.mbi.domain.person.FirstName;
//
//public class AdressTest {
//
//    private Street street = new Street(new FirstName("Klaasstraße"));
//    private HouseNumber houseNumber = new HouseNumber(12);
//    private Zip zip = new Zip(26129);
//    private City city = new City(new CityName("Oldenburg"));
//
//    @Test
//    public void testCreateAdressFilled() {
//        Address adress = new Address(street, houseNumber, zip, city);
//
//        Assert.assertNotNull(adress.getCity());
//        Assert.assertNotNull(adress.getStreet());
//        Assert.assertNotNull(adress.getZipcode());
//        Assert.assertNotNull(adress.getHouseNumber());
//    }
//
//    @Test
//    public void testGetterTest() {
//        Address address = new Address(street, houseNumber, zip, city);
//
//        Assert.assertEquals("Klaasstraße", address.getStreet().getStreet().getValue());
//        Assert.assertEquals("Oldenburg", address.getCity().getName().getValue());
//        Assert.assertEquals(26129, address.getZipcode().getNumber());
//        Assert.assertEquals(12, address.getHouseNumber().getNumber());
//    }
//}
