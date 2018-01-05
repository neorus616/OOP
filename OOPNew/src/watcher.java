import java.io.File;
import java.util.Hashtable;

public class watcher extends Thread {

	File _f;
	long _modified;
	long _size;
	history _a;

	
	public watcher(String path, history a) {
		this._f = new File(path);
		this._modified = _f.lastModified();
		this._a = a;
		this._a.updateHistory(a.getPoints());
		this._size = _f.length();
	}
	
	@Override
	public void run() {
		this._a.updateHistory(ImportCSV.mergeHash(this._a.getPoints(), ImportCSV.validPath(this._f.getAbsolutePath()+"\\")));
		System.out.println(this._a.getPoints().size());
		while(true) {
			if(this._modified != this._f.lastModified()) {
				System.out.println("changed!!!!");
				this._modified = this._f.lastModified();
				try {
					sleep(500);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
					this._a.updateHistory(ImportCSV.validPath(this._f.getAbsolutePath()+"\\"));
					System.out.println(this._a.getPoints().size());
			}
		}
	}
}
