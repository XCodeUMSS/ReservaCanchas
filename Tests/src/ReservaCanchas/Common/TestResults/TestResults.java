package ReservaCanchas.common.TestResults;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.util.Calendar;

import org.apache.log4j.Logger;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

import rallyapilib.RallyAPILib;
/**
 * Test Results Class extends from a Test Listener that is used during the execution of the TestNG Scripts
 * @author Walter Ramirez
 *
 */
/*
 * 10/11/13 -> Adding comments and enabling the Rally Update module
 */
public class TestResults extends TestListenerAdapter{
	/**
	 * Constants used to specify the expected execution result
	 */
	public enum status {PASS,FAIL,
		BLOCKED,PERCENTFAIL,ERROR};
		private String build;
		private String userID;
		private String userLogin;
		private String userPass;
		private String testSet;
		/*
		 * Initializing a logger instance using the root logger
		 */
		private Logger logger = Logger.getRootLogger();
		@Override
		public void beforeConfiguration(ITestResult tr) {
			if(tr.getMethod().isBeforeMethodConfiguration() ||
					tr.getMethod().isBeforeClassConfiguration() ||
					tr.getMethod().isBeforeGroupsConfiguration() ||
					tr.getMethod().isBeforeSuiteConfiguration() ||
					tr.getMethod().isBeforeTestConfiguration()){
				logger.info("Test Script: " + tr.getInstanceName());
				logger.info("Current Method: " + tr.getMethod().getMethodName());
			}
			super.beforeConfiguration(tr);
		}
		@Override
		public void onStart(ITestContext testContext) {
			//RollingFileAppender rfa = (RollingFileAppender) logger.getAppender("scriptDebugger");
			//rfa.rollOver();
			super.onStart(testContext);
		}
		@Override
		public void onTestSuccess(ITestResult tr){
			updateRally(tr, status.PASS);
			super.onTestSuccess(tr);
		}
		@Override
		public void onTestFailure(ITestResult tr){
			updateRally(tr, status.FAIL);
			super.onTestFailure(tr);
		}
		@Override
		public void onTestSkipped(ITestResult tr){
			updateRally(tr, status.BLOCKED);
			super.onTestSkipped(tr);
		}
		@Override
		public void onTestFailedButWithinSuccessPercentage(ITestResult tr){
			updateRally(tr, status.PERCENTFAIL);
			super.onTestFailedButWithinSuccessPercentage(tr);
		}
		@Override
		public void onConfigurationFailure(ITestResult tr){
			updateRally(tr, status.ERROR);
			super.onConfigurationFailure(tr);
		}
		/*
		 * Update Rally using the Test Case results and the returned status
		 */
		private void updateRally(ITestResult tr, status stat) {
			//Ensure to the test script executed has a related test case
			if(tr.getParameters().length > 1) {
				//Get the TC ID from the Test script
				String testCase = (String)tr.getParameters()[1];
				//Verify if the TC ID content the "TC" String at the beginning
				//in other cases ignore the Rally Update task
				if (testCase.startsWith("TC")) {
					//Reads all the common information from the Rally temporary File
					readTempFile();
					//Get the duration time in Milliseconds and convert it to Hours and a time format
					long time = tr.getEndMillis() - tr.getStartMillis();
					long milliseconds = time%1000;
					long seconds = (time/1000)%60;
					long minutes = (time/(60*1000))%60;
					long hours = (time/(60*60*1000))%24;
					String duration = hours + ":" + minutes + ":" + seconds + "." + milliseconds;
					DecimalFormat df = new DecimalFormat("###.##");
					double hoursDuration = Double.valueOf(df.format(time/3600000.0));
					//Create the notes to send to Rally using the execution result:
					//Passed: send the notes content in the CSV File
					//Any Other Case send the script information
					String notes = "";
					String verdict = "";
					notes = compileTestResults(stat, tr, duration);
					verdict = getVeredict(stat);
					if (!testSet.equals("none")){
						logger.debug("---------------------------------- Starting Rally update logger ----------------------------------");
						//Creates and instance of the Ralli API lib
						RallyAPILib rally = new RallyAPILib(userLogin, userPass);
						//Update Rally
						try{
							rally.saveResult(testCase, testSet, build, notes, verdict, "" + hoursDuration, userID);
						}catch (Exception ex){
							logger.error(ex);
						}
						logger.debug("----------------------------------- Ending Rally update logger -----------------------------------");
					}
					logTestCaseResult(verdict, testCase, duration, hoursDuration, notes);
				}else
					logExceptions(tr, stat);
			}else
				logExceptions(tr, stat);
			
			//cleanUpLog(tr, stat);
		}
		/**
		 * Returns the current execution verdict into a string
		 * @param stat current execution status
		 * @return
		 */
		private String getVeredict(status stat) {
			String verdict;
			switch (stat) {
			case PASS: verdict = "Pass";
			break;
			case FAIL: verdict = "Fail";
			break;
			case BLOCKED: verdict = "Blocked";
			break;
			case PERCENTFAIL: verdict = "Inconclusive";
			break;
			default: verdict = "Error";
			}
			return verdict;
		}
		/**
		 * Method used to manage the exceptions raised by the update Rally method 
		 * @param tr current Test Results
		 * @param stat current execution status
		 */
		private void logExceptions(ITestResult tr, status stat) {
			//Reading all parameters of the current Method
			String params = "";
			for(Object param : tr.getParameters())
				params += "\"" + (String)param + "\"";
			//reading Method name 
			String methodName = tr.getMethod().getMethodName();
			//Error Message to be send to the logs if the current method is a Test
			String notes = "Failed reading parameters for method: " + methodName + ", current params: " + params;
			if(tr.getMethod().isTest()){
				logTestCaseResult(getVeredict(stat), "TC0000", "0" , 0, notes);
			}else{
				//Generating the execution message just in case of case of the test has issues during the TearDown Section
				if(tr.getMethod().isAfterClassConfiguration() ||
						tr.getMethod().isAfterGroupsConfiguration() ||
						tr.getMethod().isAfterMethodConfiguration() ||
						tr.getMethod().isAfterSuiteConfiguration() ||
						tr.getMethod().isAfterTestConfiguration()){
					logger.info("NOTES: TearDown Failures: " + getExceptionInfo(tr));
				}else{
					logger.info("NOTES: Setup Failures: " + getExceptionInfo(tr));
				}
			}
		}
		/**
		 * logTestCaseResult, logs test case result in logger output.
		 *
		 * @param verdict, is the final status of the execution
		 * @param testCase, is the rally test case ID
		 * @param duration, is the execution time in 'hh:MM:ss.mmmm' format
		 * @param hoursDuration, is the execution time in hours
		 * @param notes, is the execution message
		 */
		private void logTestCaseResult(String verdict, String testCase,
				String duration, double hoursDuration, String notes) {
			System.out.println("----------------------------------------------" + verdict);
			logger.info("VERDICT: " + verdict);
			System.out.println("VERDICT: " + verdict);
			logger.info("TEST_CASE: " + testCase);
			System.out.println("TEST_CASE: " + testCase);
			logger.info("TEST_SET: " + testSet);
			System.out.println("TEST_SET: " + testSet);
			logger.info("BUILD: " + build);
			System.out.println("BUILD: " + build);
			logger.info("NOTES: " + notes);
			System.out.println("NOTES: " + notes);
			logger.info("DURATION: " + duration);
			System.out.println("DURATION: " + duration);
			logger.info("HOURS_DURATION: " + hoursDuration);
			System.out.println("HOURS_DURATION: " + hoursDuration);
			logger.info("RALLY_USER_ID: " + userID);
			System.out.println("RALLY_USER_ID: " + userID);
			System.out.println("----------------------------------------------" + verdict);
		}
		/*
		 * Reads the execution Log from a test result
		 */
		private String compileTestResults(status stat, ITestResult tr, String duration) {
			String methodName = tr.getMethod().getMethodName();
			Boolean testPassed = true;
			if (stat != status.PASS)
				testPassed = false;
			StringBuilder msg = new StringBuilder("Finished " + methodName + " test - ");
			if (testPassed) {
				msg.append("PASSED - Total Time: " + duration);
			} else {
				msg.append("FAILED");
				msg.append(getExceptionInfo(tr));
			}
			return msg.toString();
		}
		/**
		 * @param tr
		 * @param msg
		 */
		private StringBuilder getExceptionInfo(ITestResult tr) {
			StringBuilder msg = new StringBuilder();
			// If the test failed due to an exception, include the exception info in the log message, unless it's a TestNG timeout exception
			Throwable t = tr.getThrowable();
			if (t != null) {
				String nl = System.getProperty("line.separator");
				msg.append(nl);
				msg.append(" ");
				msg.append(t.toString());
				// If it's not a thread timeout, include the stack trace too
				if (!(t instanceof org.testng.internal.thread.ThreadTimeoutException)) {
					for (StackTraceElement e : t.getStackTrace()) {
						msg.append(nl);
						msg.append(" ");
						msg.append(e.toString());
					}
				}
			}
			return msg;
		}
		/*
		 * Reads a temporary file when we have some extra rally information
		 */
		private void readTempFile() {
			File fl = null;
			FileReader fr = null;
			BufferedReader br = null;
			//Opening the file and reading line by line all the common values to use at the moment to update Rally
			try {
				fl = new File ("rally.tmp");
				fr = new FileReader (fl);
				br = new BufferedReader(fr);
				this.build = br.readLine();
				this.userID = br.readLine();
				this.userLogin = br.readLine();
				this.userPass = br.readLine();
				this.testSet = br.readLine();
			}catch(Exception e){
				e.printStackTrace();
			}finally{
				try{
					if( null != fr ){
						fr.close();
					}
				}catch (Exception e){
					e.printStackTrace();
				}
			}
			//Overriding the build field using the value recovered from the UI
			try {
				fl = new File ("current.build");
				fr = new FileReader (fl);
				br = new BufferedReader(fr);
				this.build = br.readLine();
			}catch(Exception e){}
			finally{
				try{
					if( null != fr ){
						fr.close();
					}
				}catch (Exception e){}
			}
		}
		/*
		 *
		 */
		private void cleanUpLog(ITestResult tr, status stat){
			if(tr!=null){
				File tempLog = new File("temp.log");
				Calendar cal = Calendar.getInstance();
				cal.setTimeInMillis(tr.getEndMillis());
				String year = String.valueOf(cal.get(Calendar.YEAR));
				String month = String.valueOf(cal.get(Calendar.MONTH));
				String day = String.valueOf(cal.get(Calendar.DAY_OF_MONTH)+1);
				String hour = String.valueOf(cal.get(Calendar.HOUR));
				String min = String.valueOf(cal.get(Calendar.MINUTE));
				String sec = String.valueOf(cal.get(Calendar.SECOND));
				String mil = String.valueOf(cal.get(Calendar.MILLISECOND));
				//adding support to create the Logs folder
				File logsFolder = new File("C:\\Logs");
				if(!logsFolder.exists())
					logsFolder.mkdir();
				String logFileHeader = "C:\\Logs\\" + tr.getInstanceName() + "-"
						+ year + "-"
						+ month + "-"
						+ day + "_"
						+ hour + "."
						+ min + "."
						+ sec + "."
						+ mil;
				File dest = new File(logFileHeader + ".log");
				//if (stat != status.PASS)
				//	Functions.takeScreenshot(logFileHeader);
				try {
					InputStream in = new FileInputStream(tempLog);
					OutputStream out = new FileOutputStream(dest);
					byte[] buf = new byte[1024];
					int len;
					while ((len = in.read(buf)) > 0) {
						out.write(buf, 0, len);
					}
					in.close();
					out.close();
				} catch (Exception e){
					e.printStackTrace();
				}
				//RollingFileAppender rfa = (RollingFileAppender) logger.getAppender("appender");
				//rfa.rollOver();
			}
		}
}