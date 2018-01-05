
public class IDFilter implements Filter {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 654L;
	private String _id;
	private boolean _not = true;
	
	public IDFilter(String id) {
		_id = id;
	}
	
	public IDFilter(String id, boolean not) {
		_id = id;
		_not = not;
	}

	@Override
	public boolean test(Networks p) {
		// TODO Auto-generated method stub
		if(_not)
		return p.getID().contains(this._id);
		else return !(p.getID().contains(this._id));
	}

	@Override
	public String toString() {
		return "IDFilter [_id=" + _id + ", _not=" + _not + ", toString()=" + super.toString() + "]";
	}

}
