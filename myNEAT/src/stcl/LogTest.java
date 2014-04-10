package stcl;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;


public class LogTest {
	
	private static final Logger logger = Logger.getLogger(LogTest.class);
	
	public static void main(String[] args){
		BasicConfigurator.configure();
		LogTest lt = new LogTest();
		lt.testing();
	}
	
	public LogTest() {
		

		
	}
	
	public void testing(){
		logger.debug("Sample debug message");
		logger.debug("Sample debug message");
		logger.info("Sample info message");
		logger.warn("Sample warn message");
		logger.error("Sample error message");
		logger.fatal("Sample fatal message");
	}

}
