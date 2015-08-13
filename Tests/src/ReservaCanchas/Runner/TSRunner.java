package ReservaCanchas.Runner;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.log4j.Logger;
import org.testng.xml.XmlClass;
import org.testng.xml.XmlSuite;
import org.testng.xml.XmlTest;
import org.testng.TestNG;
import org.xml.sax.SAXException;

import ReservaCanchas.common.ClassFinder;
import ReservaCanchas.common.Logger.LoggerAppender;
import ReservaCanchas.common.TestResults.Reporter;
import ReservaCanchas.common.TestResults.TestResults;

/**
 * Class designed to run testNG suites and test cases from Command line
 * @date 9/25/2013
 * @author Walter Ramirez
 *
 */
public class TSRunner {
	private TestNG tng;
	private final String defaultTs = "testng.xml";
	public final Logger logger;
	private Reporter reporter;

	/**
	 * Initialize all the basic variables and process all the parameters sent to the class
	 * @param arguments a list of all the parameters to be used to run the scripts
	 * @throws Exception Invalid Parameters Exception
	 */
	TSRunner(List<String[]> arguments) throws Exception{
    	LoggerAppender appender = new LoggerAppender();
    	logger = appender.getLogger();
		DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
		Date date = new Date();
		String build = dateFormat.format(date);
		String userID = "";
		String userLogin = "";
		String userPass = "";
		String testSuiteFiles = "";
		String testSuiteNumber = "none";
		String testCaseNames = "";
		String testCaseFiles = "";
		for(String[] arg:arguments){
			switch(arg[0]){
			case "-b":
				build = arg[1];
				break;
			case "-u":
				userID = arg[1];
				break;
			case "-l":
				userLogin = arg[1];
				break;
			case "-p":
				userPass = arg[1];
				break;
			case "-ts":
				testSuiteFiles = arg[1];
				break;
			case "-tsn":
				testSuiteNumber = arg[1];
				break;
			case "-tc":
				testCaseNames = arg[1];
				break;
			case "-tx":
				testCaseFiles = arg[1];
				break;
			default:
				TSRunner.showHelp("\n\n>>>>>>>>Invalid Argument:\n\t\t" + arg[0]
						          + "\n>>>>>>>>Parameters:\n\t\t" + arg[1]);
				return;
			}
		}

		tng = new TestNG();
        tng.addListener(new TestResults());
        reporter = new Reporter();
        tng.addListener(reporter);
        createTempFile(build,userID,userLogin,userPass,testSuiteNumber);
                
		if(testCaseNames.isEmpty() && testCaseFiles.isEmpty()){
			if(testSuiteFiles.isEmpty()){
				runTestSuite(defaultTs);
			}else{
				runTestSuite(testSuiteFiles);
			}
		}else if(!testCaseNames.isEmpty() && testCaseFiles.isEmpty()){
			runTestCases(testCaseNames);
		}else if(testCaseNames.isEmpty() && !testCaseFiles.isEmpty()){
			runTestFiles(testCaseFiles);
		}else{
			TSRunner.showHelp("Wrong parameters combination");
		}
		
	}
	/*
	 * Creates a temporal file that stores all the extra rally information
	 */
	private void createTempFile(String build, String userID, String userLogin, String userPass, String testSuite) throws IOException {
		FileWriter fw = null;
        PrintWriter pw = null;
        fw = new FileWriter("rally.tmp");
        pw = new PrintWriter(fw);
        pw.println(build);
        pw.println(userID);
        pw.println(userLogin);
        pw.println(userPass);
        pw.println(testSuite);
        if (null != fw)
        	fw.close();
	}

	/*
	 * Runs a TestNG XML test suite
	 */
	private void runTestSuite(String tsList){
		List<String> files = new ArrayList<String>();
		//splits the Test Suite values and add those values to an ArrayList
		String[] list= tsList.split(",");
		for(int i = 0; i < list.length; i++){
			files.add(list[i]);
		}
		//Set all the test suite file Names into the TestNG instance
		tng.setTestSuites(files);
		//Runs the TestNG instance
		runTestNG();
	}
	private void runTestNG() {
		tng.run();
		if(reporter.getTotalExec()>0)
			System.exit(tng.getStatus());
		else
			System.exit(-1);
	}
	/*
	 * Runs a Test File list using the information from 1 or more test suites
	 */
	private void runTestFiles(String tcFiles) throws IOException, ParserConfigurationException, SAXException{
		String tcList = "";
		String[] files = tcFiles.split(",");
		for (int i = 0; i < files.length; i++){
			File fl = null;
			FileReader fr = null;
			BufferedReader br = null;
			fl = new File (files[i]);
			fr = new FileReader (fl);
			br = new BufferedReader(fr);
			
			String ln;
			while((ln=br.readLine())!=null)
				tcList += ln + ",";
			if( null != fr )
				fr.close();
		}
		runTestCases(tcList);
	}

	private void runTestCases(String tcList) throws ParserConfigurationException, SAXException, IOException{
		XmlSuite suite = new XmlSuite();
		suite.setName("TmpTS");

		XmlTest test = new XmlTest(suite);
		test.setName("TmpTest");
		List<XmlClass> usedClasses = new ArrayList<>();

		List<XmlClass> allClasses = getXmlClasses();
		String[] requestedTC = tcList.split(",");
		for(int i = 0; i < requestedTC.length; i++){
			if(!requestedTC[i].isEmpty()){
				for(XmlClass c:allClasses){
					String tcFullName = c.getName();
					String[] tcNameLarge = tcFullName.replace('.',' ').split(" ");
					String tcName = tcNameLarge[tcNameLarge.length-1];
					if(tcName.equals(requestedTC[i])){
						usedClasses.add(c);
					}
				}
			}
		}
		
		test.setXmlClasses(usedClasses);
		suite.setPreserveOrder("true");
		List<XmlSuite> suites = new ArrayList<XmlSuite>();
		suites.add(suite);
		tng.setXmlSuites(suites);
		runTestNG();
	}
	
	private List<XmlClass> getXmlClasses() throws ParserConfigurationException, SAXException, IOException{
		List<XmlClass> resp = new ArrayList<>();
		//Get the Classes in the current project
		String canonicalName = this.getClass().getCanonicalName();
		int firstDot = canonicalName.indexOf(".");
		String packageBaseName = canonicalName.substring(0, firstDot);
		List<Class<?>> classes = ClassFinder.find(packageBaseName);
		for(Class<?> cls : classes){
			resp.add(new XmlClass(cls));
		}
		return resp;
	}

	public static void main(String[] args) throws Exception{
		/** args = new String[]{
				//"-b","\"ReservaCanchas\"",
				//"-u","10657335842",
				//"-l","wilge.vargas@jalasoft.com",
				//"-p","February-2013",
				//"-tsn","none",
				//"-ts","testng.xml"};/**/
				//"-ts","testng.xml,src/CDP/EE/suites/MySQL/mysqlSuite.xml"};/**/
				//"-ts","src/CDP/EE/suites/MySQL/mysqlSuite.xml"};/**/
				//"-tc","AuthenticationTest"};/**/
		if (args.length == 0)
			showHelp("No Parameters");
		else if (args.length%2 == 1){
			showHelp("Help");
		}
		else{
			List<String[]> arguments = new ArrayList<>();
			int counter = args.length/2;
			
			for (int i = 0; i < counter; i++){
				arguments.add(new String[]{args[i*2],args[(i*2) + 1]});	
			}
			new TSRunner(arguments);
		}
	}

	/**
	 * Return the Help Information like an exception
	 * @param title Exception Title
	 * @throws Exception
	 */
	private static void showHelp(String title) throws Exception{
		String message ="Syntax:\n"
				+ "        -b <Current Product Build>\n"
				+ "        -u <Rally UserID>\n"
				+ "        -l <Rally UserLogin>\n"
				+ "        -p <Rally UserPass>\n"
				+ "        -tsn <Rally test suite number in the format TSXXXX>\n"
				+ "\n"
				+ "        -h|-? Show this Help\n"
				+ "\n"
				+ "        To execute 1 or more test suites:\n"
				+ "                -ts <Comma separated XML TS File list>\n"
				+ "\n"
				+ "        To execute 1 or more scripts:\n"
				+ "                -tc <Comma separated TC name list>\n"
				+ "\n"
				+ "        To execute 1 or more Test cases from a plain text:\n"
				+ "                -tx <Comma Spearated TXT test lists>\n"
				+ "------------------------------------------------------------------------------------------------------------------------------\n"
				+ "Examples:\n"
				+ "        To execute the default testng.xml test suite:\n"
				+ "                java tsRunner -b 1.5.256 -u 145254221\n"
				+ "\n"
				+ "        To execute a different XML test suite:\n"
				+ "                java tsRunner -b 1.5.256 -u 145254221 -l user@idera.com -p pass123 -ts myTestSuite.xml\n"
				+ "\n"
				+ "        To execute a simple test case:\n"
				+ "                java tsRunner -b 1.5.256 -u 145254221 -l user@idera.com -p pass123 -tsn TS12345 -tc TC1234_MyTestCase\n"
				+ "\n"
				+ "        To execute a txt test list content in diferent Test Suites:\n"
				+ "                java tsRunner -b 1.5.256 -u 145254221 -l user@idera.com -p pass123 -tsn TS12345 -ts mySecondTS.xml -tx MyTCList.txt\n"
				+ "\n"
				+ "        To execute a txt test list:\n"
				+ "                java tsRunner -b 1.5.256 -u 145254221 -l user@idera.com -p pass123 -tsn TS12345 -tx MyTCList.txt\n"
				+ "\n";
		//javax.swing.JOptionPane.showMessageDialog(null,message,title,javax.swing.JOptionPane.INFORMATION_MESSAGE);
		//System.out.println(title + "\n\n" + message);
		throw new Exception(title + "\n\n" + message);
	}
}