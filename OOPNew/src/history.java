import java.util.Hashtable;

public class history {

	Hashtable<String, Networks> strongPointsCSV;
	Hashtable<String, Networks> strongPoints;
	Hashtable<String, Networks> filteredStrongPoints = new Hashtable<>();
	

	public history(Hashtable<String, Networks> strongPoints) {
		this.strongPoints = strongPoints;
	}

	public Hashtable<String, Networks> getPoints(){
		if(!this.filteredStrongPoints.isEmpty())
			return this.filteredStrongPoints;
		else 
			return this.strongPoints;
	}

	public void updateHistory(Hashtable<String, Networks> strongPoints) {
		this.strongPoints = strongPoints;
	}

	public void updateHistoryCSV(Hashtable<String, Networks> strongPoints) {
		this.strongPointsCSV = strongPoints;
		updateHistory(this.strongPointsCSV);
	}

	public void clear() {
		strongPointsCSV.clear();
		strongPoints.clear();
		filteredStrongPoints.clear();
	}

	public void filter(Filter filter1, Filter filter2, String operator) {
		filteredStrongPoints = new Hashtable<>();
		for (String key : this.strongPoints.keySet()) {
			if(filter1 == null) {
				if(filter2.test(this.strongPoints.get(key)))
					this.filteredStrongPoints.put(key, strongPoints.get(key));
			}
			else if(filter2 == null) {
				if(filter1.test(this.strongPoints.get(key)))
					this.filteredStrongPoints.put(key, strongPoints.get(key));
			}
			else if(operator.equals("AND")) {
				if((filter1.test(this.strongPoints.get(key))) && (filter2.test(this.strongPoints.get(key))))
					this.filteredStrongPoints.put(key, strongPoints.get(key));
			} else if(operator.equals("OR")) {
				if((filter1.test(strongPoints.get(key))) || (filter2.test(strongPoints.get(key))))
					this.filteredStrongPoints.put(key, strongPoints.get(key));
			}
		}
		System.out.println("Filtered " + filteredStrongPoints.size() + " Points");
	}
}
