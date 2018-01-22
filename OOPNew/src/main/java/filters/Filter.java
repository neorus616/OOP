package main.java.filters;
/**
 * 
 * @author Kostia Kazakov &amp; Yogev Rahamim <br>
 * @version 2.0 
 * Description:
 * filter interface.
 */
import java.io.Serializable;

import main.java.wifi.Networks;

public interface Filter extends Serializable {

	/**
	 * test if the Network p is pass the filter 
	 * @param p - Networks object
	 * @return true if the record pass the filter otherwise returns false 
	 */
	public boolean test(Networks p);
	
}
