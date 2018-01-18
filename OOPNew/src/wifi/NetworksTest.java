package wifi;
import static org.junit.Assert.assertEquals;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import wifi.Networks;
import wifi.Wifi;

class NetworksTest {

	Networks networkTest = new Networks("ID", "Time", 0, 0, 0);
	ArrayList<Wifi> xpoints = new ArrayList<Wifi>();
	
	
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
		assertEquals(networkTest.getPoints().get(9).getSignal(),-83);
		assertEquals(networkTest.getPoints().get(3).getSignal(),-98);
		
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
		Wifi p1 = new Wifi("ssid1","mac", -80, 10);
		xpoints.add(p1);
		Wifi p2 = new Wifi("ssid2","mac", -65, 10);
		xpoints.add(p2);
		Wifi p3 = new Wifi("ssid3","mac", -99, 10);
		xpoints.add(p3);
		Wifi p5 = new Wifi("ssid5","mac", -98, 10);
		xpoints.add(p5);
		Wifi p6 = new Wifi("ssid6","mac", -99, 10);
		xpoints.add(p6);
		Wifi p7 = new Wifi("ssid7","mac", -87, 10);
		xpoints.add(p7);
		Wifi p8 = new Wifi("ssid8","mac", -75, 10);
		xpoints.add(p8);
		Wifi p9 = new Wifi("ssid9","mac", -88, 10);
		xpoints.add(p9);
		Wifi p10 = new Wifi("ssid10","mac", -93, 10);
		xpoints.add(p10);
		Wifi p4 = new Wifi("ssid11","mac", -83, 10);
		xpoints.add(p4);
		
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
		assertEquals(networkTest.getPoints().toString(),xpoints.toString());
	}


}
