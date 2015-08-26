package reservaCanchas.common.logger;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.apache.log4j.RollingFileAppender;

/**
 * Class Log Appender used as a root Logger appender for all the Logger Process
 * @Date  26/08/2015
 * @author Khronos
 */
public class LoggerAppender {
	private static Logger logger;

	/**
	 * Main constructor of this class initialize the Information Logger and an extra 
	 * Debugging Logger.
	 */
	public LoggerAppender() 
	{
		logger = Logger.getRootLogger();
		logger.addAppender(getInfoAppender());
		logger.addAppender(getDebugAppender());
	}
	/**
	 * Return the current Logger to be used by the Main Runner Class in the Sahi Project
	 * @return
	 */
	public Logger getLogger() {
		return logger;
	}
	/**
	 * Return the information Rolling File Appender to be used as a temporary Logger 
	 * for the Test Scripts
	 * @return
	 */
	public static RollingFileAppender getInfoAppender(){
		RollingFileAppender rollingFileAppender = new RollingFileAppender();
		rollingFileAppender.setName("appender");
		rollingFileAppender.setFile("temp.log");
		rollingFileAppender.setMaxBackupIndex(0);
		rollingFileAppender.setLayout(new PatternLayout("%d|%p|%m%n"));
		rollingFileAppender.setThreshold(Level.INFO);
		rollingFileAppender.setAppend(true);
		rollingFileAppender.activateOptions();
		return rollingFileAppender;
	}
	/**
	 * Return the debugging Rolling File Appender to be used as a complete Logger 
	 * for all the test scripts execution
	 * @return
	 */
	public static RollingFileAppender getDebugAppender(){
		RollingFileAppender rollingFileAppender = new RollingFileAppender();
		rollingFileAppender.setName("debugging");
		String logsPath = System.getProperty("user.dir") + System.getProperty("file.separator")
				+ "debugLogs" + System.getProperty("file.separator");
		rollingFileAppender.setFile(logsPath + "debugLogs.log");
		rollingFileAppender.setMaxBackupIndex(100);
		rollingFileAppender.setMaxFileSize("100MB");
		rollingFileAppender.setLayout(new PatternLayout("%d|%p|%m%n"));
		rollingFileAppender.setThreshold(Level.DEBUG);
		rollingFileAppender.setAppend(true);
		rollingFileAppender.activateOptions();
		return rollingFileAppender;
	}
}