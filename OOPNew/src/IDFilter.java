/**
 * 
 * @author Kostia Kazakov &amp; Yogev Rahamim <br>
 * @version 2.0 
 * Description:
 * ID Filter.
 */

public class IDFilter implements Filter {

	private static final long serialVersionUID = 654L;
	private String _id;
	private boolean _not = true;

	/**
	 * Constructor
	 * @param id - Phone name
	 */
	public IDFilter(String id) {
		_id = id;
	}

	/**
	 * Constructor for not operator
	 * @param id - Phone name
	 * @param not - not operator
	 */
	public IDFilter(String id, boolean not) {
		_id = id;
		_not = not;
	}

	@Override
	public boolean test(Networks p) {
		// TODO Auto-generated method stub
		if(_not)
			return p.getID().contains(_id);
		else return !(p.getID().contains(_id));
	}

	@Override
	public String toString() {
		if(_not)
			return "IDFilter [_id=" + _id + "]";
		else return "Not(IDFilter [_id=" + _id + "])";
	}

}
