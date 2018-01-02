
public class IDFilter implements Filter {
	
	private String _id;
	public IDFilter(String id) {
		_id = id;
	}

	@Override
	public boolean test(Networks p) {
		// TODO Auto-generated method stub
		return this._id.equals(p.getID());
	}

}
