
public class IDFilter implements Filter {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String _id;
	
	public IDFilter(String id) {
		_id = id;
	}

	@Override
	public boolean test(Networks p) {
		// TODO Auto-generated method stub
		return p.getID().contains(this._id);
	}

}
