
import java.util.logging.Formatter;
import java.util.logging.LogRecord;

public class SolveFormatter extends Formatter { 
	
	public SolveFormatter() { 
		super(); 
	}

	@Override 
	public String format(final LogRecord record){
		return record.getMessage() + "\r\n";
	}  
}