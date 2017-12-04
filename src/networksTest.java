import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

class networksTest {

	networks networkTest = new networks("ID", "Time", 0, 0, 0);
	ArrayList<wifi> xpoints = new ArrayList<wifi>();
	
	
	@Test
	final void testAdd() {
		networkTest.add("ssid1","mac", -80, 10);
		networkTest.add("ssid2","mac", -65, 10);
		networkTest.add("ssid3","mac", -99, 10);
		networkTest.add("ssid4","mac", -120, 10);  //worse
		networkTest.add("ssid5","mac", -98, 10);
		networkTest.add("ssid6","mac", -99, 10);
		networkTest.add("ssid7","mac", -87, 10);
		networkTest.add("ssid8","mac", -75, 10);
		networkTest.add("ssid9","mac", -88, 10);
		networkTest.add("ssid10","mac", -93, 10);
		networkTest.add("ssid11","mac", -83, 10);
		assertEquals(networkTest.getPoints().get(3).getSignal(),-83 );
	}

	@Test
	final void testGetID() {
		assertEquals(networkTest.getID(),"ID");
	}

	@Test
	final void testGetTime() {
		assertEquals(networkTest.getTime(),"Time");
	}

	@Test
	final void testGetPoints() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	final void testCheckSignal() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	final void testIsId() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	final void testIsLocation() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	final void testIsTime() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	final void testFilter() {
		fail("Not yet implemented"); // TODO
	}

}
