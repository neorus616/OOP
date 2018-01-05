import java.io.Serializable;

public interface Filter extends Serializable {

	/**
	 * test if the Network p is pass the filter 
	 * @param rec
	 * @return true if the record pass the filter otherwise returns false 
	 */

	public boolean test(Networks p);
	
}
