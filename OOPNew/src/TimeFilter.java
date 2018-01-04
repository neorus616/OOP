import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class TimeFilter implements Filter {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String _start, _end;

	public TimeFilter(String start, String end) {
		_start = start;
		_end = end;
	}
	
	public TimeFilter(String [] time) {
		_start = time[0];
		_end = time[1];
	}
	
	

	@Override
	public boolean test(Networks p) {
		// TODO Auto-generated method stub
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

		LocalDateTime dateTime1= LocalDateTime.parse(this._start, formatter);
		LocalDateTime dateTime2= LocalDateTime.parse(this._end, formatter);
		LocalDateTime dateTime3= LocalDateTime.parse(p.getTime(), formatter);

		long diffInMilli = java.time.Duration.between(dateTime1, dateTime2).getSeconds();
		long diffInMilli1 = java.time.Duration.between(dateTime3, dateTime2).getSeconds();

		return (diffInMilli > diffInMilli1 && diffInMilli1 > 0);
	}

}
