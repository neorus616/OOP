/**
 * 
 * @author Kostia Kazakov &amp; Yogev Rahamim <br>
 * @version 2.0 
 * Description:
 * or operator between two filters.
 */

public class OrFilter implements Filter {


	private static final long serialVersionUID = 5954418761842190714L;
	Filter _filter1;
	Filter _filter2;
	
	/**
	 * Constructor
	 * @param filter1 - first filter
	 * @param filter2 - second filter
	 */
	public OrFilter(Filter filter1, Filter filter2) {
		_filter1 = filter1;
		_filter2 = filter2;
	}
	
	@Override
	public boolean test(Networks p) {
		// TODO Auto-generated method stub
		return (_filter1.test(p) || _filter2.test(p));
	}

	@Override
	public String toString() {
		return "OrFilter [filter1=" + _filter1 + ", filter2=" + _filter2 + "]";
	}

}
