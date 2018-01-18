package algorithms;
/**
 * 
 */


import static org.junit.Assert.assertEquals;
import org.junit.jupiter.api.Test;

import algorithms.EstimateAlgo;
import wifi.AP;
import wifi.APNetworks;

/**
 * @author Adminchuwi
 *
 */
class EstimateAlgoTest {
	
	
	APNetworks networks = new APNetworks();
	
	
	@Test
	final void testAdd() {
		AP ap = new AP("ssid6", "mac6", -110, 0, 0, 0, 0);
		networks.add("ssid1", "mac1", -75, 0, 0, 0, 0);
		networks.add("ssid2", "mac2", -82, 0, 0, 0, 0);
		networks.add("ssid3", "mac3", -77, 0, 0, 0, 0);
		networks.add("ssid4", "mac4", -77, 0, 0, 0, 0);
		networks.add("ssid5", "mac5", -65, 0, 0, 0, 0);
		networks.add("ssid6", "mac6", -110, 0, 0, 0, 0); // worse
		networks.add("ssid7", "mac7", -95, 0, 0, 0, 0);
		networks.add("ssid8", "mac8", -88, 0, 0, 0, 0);
		networks.add("ssid9", "mac9", -109, 0, 0, 0, 0);
		networks.add("ssid10", "mac10", -100, 0, 0, 0, 0);
		networks.add("ssid11", "mac11", -108, 0, 0, 0, 0);
		assertEquals(networks.getPoints().contains(ap),false);
	}
	
	
	/**
	 * Test method for {@link algorithms.EstimateAlgo#wcenter(java.util.ArrayList)}.
	 */
	@Test
	void testWcenterArrayListOfAP() {
		double[] p = new double[] {120.61508153095312, 124.09062976186316, 42.01144156135058};
		networks.add("ssid1", "mac1", -75, 10, 120, 125, 50);
		networks.add("ssid2", "mac2", -82, 10, 109, 122, 45);
		networks.add("ssid3", "mac3", -77, 10, 150, 130, 35);
		networks.add("ssid4", "mac4", -77, 10, 110, 122, 45);
		networks.add("ssid5", "mac5", -65, 10, 115, 122, 37);
		double[] d = EstimateAlgo.wcenter(networks.getPoints());
		assertEquals(d[0]==p[0],true);
		assertEquals(d[1]==p[1],true);
		assertEquals(d[2]==p[2],true);
	}

//	/**
//	 * Test method for {@link algorithms.EstimateAlgo#wcenter(wifi.Networks[], double[], int)}.
//	 */
//	@Test
//	void testWcenterNetworksArrayDoubleArrayInt() {
//		fail("Not yet implemented");
//	}

}
