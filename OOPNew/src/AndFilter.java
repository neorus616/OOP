
public class AndFilter implements Filter {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8277106136373590950L;
	Filter _filter1;
	Filter _filter2;
	
	
	public AndFilter(Filter filter1, Filter filter2) {
		_filter1 = filter1;
		_filter2 = filter2;
	}
	
	@Override
	public boolean test(Networks p) {
		// TODO Auto-generated method stub
		return (_filter1.test(p)&&_filter2.test(p));
	}

	@Override
	public String toString() {
		return "AndFilter [filter1=" + _filter1 + ", filter2=" + _filter2 + "]";
	}

}
