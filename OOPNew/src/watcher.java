import java.io.File;
import java.util.Hashtable;

public class watcher extends Thread {

	File _f;
	long _modified;
	Hashtable<String, Networks> _strongPoints;
	long _size;
	
	public watcher(String path, Hashtable<String, Networks> strongPoints) {
		this._f = new File(path);
		this._modified = _f.lastModified();
		this._strongPoints = ImportCSV.validPath(path);
		this._size = _f.length();
	}
	
	@Override
	public void run() {
		System.out.println(this._strongPoints.size());
		while(true) {
			if(this._modified != this._f.lastModified()) {
				System.out.println("changed!!!!");
				this._modified = this._f.lastModified();
				System.out.println(_f.getAbsolutePath()+"\\");
				try {
					sleep(500);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
//				if(_f.length() > this._size)
//				this._strongPoints = (ImportCSV.mergeHash(this._strongPoints,ImportCSV.validPath(_f.getAbsolutePath()+"\\")));
//				else 
					this._strongPoints = ImportCSV.validPath(this._f.getAbsolutePath()+"\\");
				System.out.println(this._strongPoints.size());
			}
		}
	}
	
	public void updateStrong() {
		this._strongPoints = ImportCSV.validPath(this._f.getAbsolutePath()+"\\");
	}
	
	public void writer(String path) {
		ExportCSV.writeCsvFile(_strongPoints, path+".csv",1);
	}

}
