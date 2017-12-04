import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class fromNewCSVTest {
	
    @Test
    void exceptionTestingFilter1() {
        Throwable exception = assertThrows(StringIndexOutOfBoundsException.class, () ->{fromNewCSV.filterCSV("C:\\Users\\Lowhacker\\Downloads\\CSV\\newUpgradedCSV.csv", "ONEPLUS A3003");});
        assertEquals("Wrong filter statement ! \n try like this: \"id/date/location = Lenovo PB2-690Y/2017-10-27 16:13:51/32.16,34.80,46.34\" ", exception.getMessage());
    }
    
    @Test
    void exceptionTestingFilter2() {
        Throwable exception = assertThrows(StringIndexOutOfBoundsException.class, () ->{fromNewCSV.filterCSV("C:\\Users\\Lowhacker\\Downloads\\CSV\\newUpgradedCSV.csv", "id : ONEPLUS A3003");});
        assertEquals("Wrong filter statement ! \n try like this: \"id/date/location = Lenovo PB2-690Y/2017-10-27 16:13:51/32.16,34.80,46.34\" ", exception.getMessage());
    }
    
    @Test
    void exceptionTestingFilter3() {
        Throwable exception = assertThrows(StringIndexOutOfBoundsException.class, () ->{fromNewCSV.filterCSV("C:\\Users\\Lowhacker\\Downloads\\CSV\\newUpgradedCSV.csv", "location - ONEPLUS A3003");});
        assertEquals("Wrong filter statement ! \n try like this: \"id/date/location = Lenovo PB2-690Y/2017-10-27 16:13:51/32.16,34.80,46.34\" ", exception.getMessage());
    }
    

    
    @Test
    void exceptionTestingPath1() {
        Throwable exception = assertThrows(IllegalArgumentException.class, () ->{fromNewCSV.filterCSV("C:\\Users\\Lowhacker\\Downloads\\CSV\\newUpgradedCSV", "id = ONEPLUS A3003");});
        assertEquals("Not a valid CSV file !", exception.getMessage());
    }
    
    @Test
    void exceptionTestingPath2() {
        Throwable exception = assertThrows(IllegalArgumentException.class, () ->{fromNewCSV.filterCSV("C:\\Users\\Lowhacker\\Downloads\\CSV\\newUpgradedCSV.kml", "id = ONEPLUS A3003");});
        assertEquals("Not a valid CSV file !", exception.getMessage());
    }
    
    @Test
    void exceptionTestingPath3() {
        Throwable exception = assertThrows(IllegalArgumentException.class, () ->{fromNewCSV.filterCSV("C:\\Users\\Lowhacker\\Downloads\\CSV\\", "id = ONEPLUS A3003");});
        assertEquals("Not a valid CSV file !", exception.getMessage());
    }
    
    @Test
    void exceptionTestingPath4() {
        Throwable exception = assertThrows(IllegalArgumentException.class, () ->{fromNewCSV.filterCSV("C:\\Users\\Lowhacker\\Downloads\\CSV\\UpgradedCSV.csv", "id = ONEPLUS A3003");});
        assertEquals("Not a valid CSV file !", exception.getMessage());
    }
    
}
