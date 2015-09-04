package reservaCanchas.runner;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.log4j.Logger;
import org.testng.xml.XmlClass;
import org.testng.xml.XmlSuite;
import org.testng.xml.XmlTest;
import org.testng.TestNG;
import org.xml.sax.SAXException;

import reservaCanchas.common.ClassFinder;
import reservaCanchas.common.logger.LoggerAppender;
import reservaCanchas.common.testResults.Reporter;
import reservaCanchas.common.testResults.TestResults;
import java.util.Arrays;

/**
 * Class designed to run testNG suites and test cases from Command line
 *
 * @date 26/08/2015
 * @author Khronos
 *
 */
public class TSRunner {

    private final TestNG tng;
    private final String defaultTs = "testng.xml";
    public final Logger logger;
    private final Reporter reporter;

    public static void main(String[] args) throws Exception {
        args = new String[]{
            "-ts","testng.xml"};/**/
            //"-ts","testng.xml,src/mySuite.xml"};/**/
            //"-tc", "AgregarCanchaTest"};/**/

        if (args.length % 2 == 1) {
            showHelp("Help");
        } else {
            List<String[]> arguments = new ArrayList<>();
            int counter = args.length / 2;

            for (int i = 0; i < counter; i++) {
                arguments.add(new String[]{args[i * 2], args[(i * 2) + 1]});
            }
            new TSRunner(arguments);
        }
    }

    /**
     * Initialize all the basic variables and process all the parameters sent to
     * the class
     *
     * @param arguments a list of all the parameters to be used to run the
     * scripts
     * @throws Exception Invalid Parameters Exception
     */
    TSRunner(List<String[]> arguments) throws Exception {
        tng = new TestNG();
        reporter = new Reporter();
        LoggerAppender appender = new LoggerAppender();
        logger = appender.getLogger();
        String testSuiteFiles = "";
        String testCaseNames = "";
        String testCaseFiles = "";
        for (String[] arg : arguments) {
            switch (arg[0]) {
                case "-ts":
                    testSuiteFiles = arg[1];
                    break;
                case "-tc":
                    testCaseNames = arg[1];
                    break;
                case "-tx":
                    testCaseFiles = arg[1];
                    break;
                default:
                    TSRunner.showHelp("\n\n!!!!!!!!!!!Argumento Invalido:\n\t\t" + arg[0]
                            + "\n!!!!!!!!!!!Parametros:\n\t\t" + arg[1]);
                    return;
            }
        }
        tng.addListener(new TestResults());
        tng.addListener(reporter);
        if (testCaseNames.isEmpty() && testCaseFiles.isEmpty()) {
            if (testSuiteFiles.isEmpty()) {
                runTestSuite(defaultTs);
            } else {
                runTestSuite(testSuiteFiles);
            }
        } else if (!testCaseNames.isEmpty() && testCaseFiles.isEmpty()) {
            runTestCases(testCaseNames);
        } else if (testCaseNames.isEmpty() && !testCaseFiles.isEmpty()) {
            runTestFiles(testCaseFiles);
        } else {
            TSRunner.showHelp("Combinacion de parametros incorrecta");
        }
    }

    /*
     * Runs a TestNG XML test suite
     */
    private void runTestSuite(String tsList) {
        List<String> files = new ArrayList<>();
        //splits the Test Suite values and add those values to an ArrayList
        String[] list = tsList.split(",");
        files.addAll(Arrays.asList(list));
        //Set all the test suite file Names into the TestNG instance
        tng.setTestSuites(files);
        //Runs the TestNG instance
        runTestNG();
    }

    private void runTestNG() {
        tng.run();
        if (reporter.getTotalExec() > 0) {
            System.exit(tng.getStatus());
        } else {
            System.exit(-1);
        }
    }
    /*
     * Runs a Test File list using the information from 1 or more test suites
     */

    private void runTestFiles(String tcFiles) throws IOException, ParserConfigurationException, SAXException {
        String tcList = "";
        String[] files = tcFiles.split(",");
        for (String file : files) {
            File fl;
            FileReader fr;
            BufferedReader br;
            fl = new File(file);
            fr = new FileReader(fl);
            br = new BufferedReader(fr);
            String ln;
            while ((ln = br.readLine()) != null) {
                tcList += ln + ",";
            }
            if (fr != null) {
                fr.close();
            }
        }
        runTestCases(tcList);
    }

    private void runTestCases(String tcList) throws ParserConfigurationException, SAXException, IOException {
        XmlSuite suite = new XmlSuite();
        suite.setName("TmpTS");

        XmlTest test = new XmlTest(suite);
        test.setName("TmpTest");
        List<XmlClass> usedClasses = new ArrayList<>();

        List<XmlClass> allClasses = getXmlClasses();
        String[] requestedTC = tcList.split(",");
        for (String requestedTC1 : requestedTC) {
            if (!requestedTC1.isEmpty()) {
                for (XmlClass c : allClasses) {
                    String tcFullName = c.getName();
                    String[] tcNameLarge = tcFullName.replace('.', ' ').split(" ");
                    String tcName = tcNameLarge[tcNameLarge.length - 1];
                    if (tcName.equals(requestedTC1)) {
                        usedClasses.add(c);
                    }
                }
            }
        }

        test.setXmlClasses(usedClasses);
        suite.setPreserveOrder("true");
        List<XmlSuite> suites = new ArrayList<>();
        suites.add(suite);
        tng.setXmlSuites(suites);
        runTestNG();
    }

    private List<XmlClass> getXmlClasses() throws ParserConfigurationException, SAXException, IOException {
        List<XmlClass> resp = new ArrayList<>();
        //Obtiene las clases en el proyecto actual
        String canonicalName = this.getClass().getCanonicalName();
        int firstDot = canonicalName.indexOf(".");
        String packageBaseName = canonicalName.substring(0, firstDot);
        List<Class<?>> classes = ClassFinder.find(packageBaseName);
        for (Class<?> cls : classes) {
            resp.add(new XmlClass(cls));
        }
        return resp;
    }

    /**
     * Retorna la informacion de ayuda a forma de excepcion
     *
     * @param title título para la excepcion
     * @throws Exception
     */
    private static void showHelp(String title) throws Exception {
        String message = "Syntaxis:\n"
                + "        Para Ejecutar 1 o más test suites:\n"
                + "                -ts <lista de archivos XMLComma separados por comas>\n"
                + "\n"
                + "        Para ejecutar 1 o más scripts:\n"
                + "                -tc <lista de Test Cases separada por comas>\n"
                + "\n"
                + "        Para ejecutar uno o más test cases desde archivos de texto plano:\n"
                + "                -tx <lista de archivos de texto TXT separados por commas>\n"
                + "        -h|-? Retorna esta ayuda\n"
                + "\n";
        javax.swing.JOptionPane.showMessageDialog(null, message, title, javax.swing.JOptionPane.INFORMATION_MESSAGE);
        System.out.println(title + "\n\n" + message);
        throw new Exception(title + "\n\n" + message);
    }
}
