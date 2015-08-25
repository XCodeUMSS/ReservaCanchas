package reservaCanchas.common.logger;

import java.io.File;

import org.apache.log4j.FileAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.apache.log4j.RollingFileAppender;

/**
 * Class Log Appender used as a root Logger appender for all the Logger Process
 * @Date  1/30/2014
 * @author Walter Ramirez
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
		//logger.addAppender(getInfoAppender());
		//logger.addAppender(getDebugAppender());
		//logger.addAppender(getTestScriptsAppender());
		logger.addAppender(testScriptsAppender());
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
	/**
	 * Return the debugging Rolling File Appender to be used as a complete Logger 
	 * for the test scripts execution
	 * @return 
	 */
	public static RollingFileAppender getTestScriptsAppender(){
		RollingFileAppender rollingFileAppender = new RollingFileAppender();
		rollingFileAppender.setName("scriptDebugger");
		File dataFolder = new File("C:\\Data");
		if(!dataFolder.exists())
			dataFolder.mkdir();
		rollingFileAppender.setFile(dataFolder.getAbsolutePath() + "\\debugLog.log");
		rollingFileAppender.setMaxBackupIndex(10000);
		rollingFileAppender.setMaxFileSize("100MB");
		rollingFileAppender.setLayout(new PatternLayout("%d|%m%n"));
		rollingFileAppender.setThreshold(Level.ALL);
		rollingFileAppender.setAppend(true);
		rollingFileAppender.activateOptions();
		return rollingFileAppender;
	}
	public static FileAppender testScriptsAppender(){
		FileAppender fileAppender = new FileAppender();
		fileAppender.setName("scriptDebugger");
		File dataFolder = new File("C:\\Data");
		if(!dataFolder.exists())
			dataFolder.mkdir();
		fileAppender.setFile(dataFolder.getAbsolutePath() + "\\debugLog.log");
		fileAppender.setAppend(false);
		fileAppender.setImmediateFlush(true);
		fileAppender.setLayout(new PatternLayout("%d|%m%n"));
		fileAppender.setThreshold(Level.ALL);
		fileAppender.activateOptions();
		return fileAppender;
	}
}