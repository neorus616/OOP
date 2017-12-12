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
		wifi p1 = new wifi("ssid1","mac", -80, 10);
		xpoints.add(p1);
		wifi p2 = new wifi("ssid2","mac", -65, 10);
		xpoints.add(p2);
		wifi p3 = new wifi("ssid3","mac", -99, 10);
		xpoints.add(p3);
		wifi p4 = new wifi("ssid11","mac", -83, 10);
		xpoints.add(p4);
		wifi p5 = new wifi("ssid5","mac", -98, 10);
		xpoints.add(p5);
		wifi p6 = new wifi("ssid6","mac", -99, 10);
		xpoints.add(p6);
		wifi p7 = new wifi("ssid7","mac", -87, 10);
		xpoints.add(p7);
		wifi p8 = new wifi("ssid8","mac", -75, 10);
		xpoints.add(p8);
		wifi p9 = new wifi("ssid9","mac", -88, 10);
		xpoints.add(p9);
		wifi p10 = new wifi("ssid10","mac", -93, 10);
		xpoints.add(p10);
		assertEquals(networkTest.getPoints(),xpoints);
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
