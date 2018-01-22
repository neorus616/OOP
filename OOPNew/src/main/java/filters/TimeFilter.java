package main.java.filters;
/**
 * 
 * @author Kostia Kazakov &amp; Yogev Rahamim <br>
 * @version 2.0 
 * Description:
 * Time Filter.
 */

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import main.java.wifi.Networks;


public class TimeFilter implements Filter {
	
	private static final long serialVersionUID = 546L;
	private String _start, _end;
	private boolean _not = true;

	/**
	 * Constructor
	 * @param start - Start time
	 * @param end - End time
	 */
	public TimeFilter(String start, String end) {
		_start = start;
		_end = end;
	}
	
	/**
	 * Constructor
	 * @param time - array of start and end time
	 */
	public TimeFilter(String [] time) {
		_start = time[0];
		_end = time[1];
	}
	
	/**
	 * Constructor for not operator
	 * @param time - array of start and end time
	 * @param not - not operator
	 */
	public TimeFilter(String [] time, boolean not) {
		_start = time[0];
		_end = time[1];
		_not = not;
	}
	

	@Override
	public boolean test(Networks p) {
		// TODO Auto-generated method stub
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

		LocalDateTime startTime= LocalDateTime.parse(this._start, formatter);
		LocalDateTime endTime= LocalDateTime.parse(this._end, formatter);
		LocalDateTime networkTime= LocalDateTime.parse(p.getTime(), formatter);

		
		long diffInMilli = java.time.Duration.between(startTime, endTime).getSeconds();
		long diffInMilli1 = java.time.Duration.between(networkTime, endTime).getSeconds();

		if(_not)
		return (diffInMilli >= diffInMilli1 && diffInMilli1 >= 0);
		else return !(diffInMilli >= diffInMilli1 && diffInMilli1 >= 0);
	}

	@Override
	public String toString() {
		if(!_not)
		return "Not(TimeFilter [_start=" + _start + ", _end=" + _end + "])";
		else return "TimeFilter [_start=" + _start + ", _end=" + _end + "]";
	}

}
