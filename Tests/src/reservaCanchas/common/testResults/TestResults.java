package reservaCanchas.common.testResults;

import java.text.DecimalFormat;

import org.apache.log4j.Logger;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

/**
 * Test Results Class extends from a Test Listener that is used during the
 * execution of the TestNG Scripts
 *
 * @author Khronos
 *
 */
public class TestResults extends TestListenerAdapter {

    /**
     * Constants used to specify the expected execution result
     */
    public enum status {

        PASS, FAIL,
        BLOCKED, PERCENTFAIL, ERROR
    };
    /*
     * Initializing a logger instance using the root logger
     */
    private final Logger logger = Logger.getRootLogger();

    @Override
    public void beforeConfiguration(ITestResult tr) {
        if (tr.getMethod().isBeforeMethodConfiguration()
                || tr.getMethod().isBeforeClassConfiguration()
                || tr.getMethod().isBeforeGroupsConfiguration()
                || tr.getMethod().isBeforeSuiteConfiguration()
                || tr.getMethod().isBeforeTestConfiguration()) {
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
    public void onTestSuccess(ITestResult tr) {
        updateRally(tr, status.PASS);
        super.onTestSuccess(tr);
    }

    @Override
    public void onTestFailure(ITestResult tr) {
        updateRally(tr, status.FAIL);
        super.onTestFailure(tr);
    }

    @Override
    public void onTestSkipped(ITestResult tr) {
        updateRally(tr, status.BLOCKED);
        super.onTestSkipped(tr);
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult tr) {
        updateRally(tr, status.PERCENTFAIL);
        super.onTestFailedButWithinSuccessPercentage(tr);
    }

    @Override
    public void onConfigurationFailure(ITestResult tr) {
        updateRally(tr, status.ERROR);
        super.onConfigurationFailure(tr);
    }
    /*
     * Update Rally using the Test Case results and the returned status
     */

    private void updateRally(ITestResult tr, status stat) {
        //Ensure to the test script executed has a related test case
        if (tr.getParameters().length > 1) {
            //Get the duration time in Milliseconds and convert it to Hours and a time format
            long time = tr.getEndMillis() - tr.getStartMillis();
            long milliseconds = time % 1000;
            long seconds = (time / 1000) % 60;
            long minutes = (time / (60 * 1000)) % 60;
            long hours = (time / (60 * 60 * 1000)) % 24;
            String duration = hours + ":" + minutes + ":" + seconds + "." + milliseconds;
            DecimalFormat df = new DecimalFormat("###.##");
            double hoursDuration = Double.valueOf(df.format(time / 3600000.0));
            String notes;
            String verdict;
            notes = compileTestResults(stat, tr, duration);
            verdict = getVeredict(stat);
            logTestCaseResult(verdict, tr.getParameters()[0].toString(), duration, hoursDuration, notes);
        } else {
            logExceptions(tr, stat);
        }

        //cleanUpLog(tr, stat);
    }

    /**
     * Returns the current execution verdict into a string
     *
     * @param stat current execution status
     * @return
     */
    private String getVeredict(status stat) {
        String verdict;
        switch (stat) {
            case PASS:
                verdict = "Pass";
                break;
            case FAIL:
                verdict = "Fail";
                break;
            case BLOCKED:
                verdict = "Blocked";
                break;
            case PERCENTFAIL:
                verdict = "Inconclusive";
                break;
            default:
                verdict = "Error";
        }
        return verdict;
    }

    /**
     * Method used to manage the exceptions raised by the update Rally method
     *
     * @param tr current Test Results
     * @param stat current execution status
     */
    private void logExceptions(ITestResult tr, status stat) {
        //Reading all parameters of the current Method
        if (tr.getMethod().isAfterClassConfiguration()
                || tr.getMethod().isAfterGroupsConfiguration()
                || tr.getMethod().isAfterMethodConfiguration()
                || tr.getMethod().isAfterSuiteConfiguration()
                || tr.getMethod().isAfterTestConfiguration()) {
            logger.info("NOTES: TearDown Failures: " + getExceptionInfo(tr));
        } else {
            logger.info("NOTES: Setup Failures: " + getExceptionInfo(tr));
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
        System.out.println("------------------" + verdict + "------------------");
        logger.info("RESULTADO: " + verdict);
        System.out.println("RESULTADO: " + verdict);
        logger.info("TEST CASE: " + testCase);
        System.out.println("TEST CASE: " + testCase);
        logger.info("NOTAS: " + notes);
        System.out.println("NOTAS: " + notes);
        logger.info("DURACION: " + duration);
        System.out.println("DURACION: " + duration);
        logger.info("DURACION EN HORAS: " + hoursDuration);
        System.out.println("DURACION EN HORAS: " + hoursDuration);
        System.out.println("------------------" + verdict + "------------------");
    }
    /*
     * Reads the execution Log from a test result
     */

    private String compileTestResults(status stat, ITestResult tr, String duration) {
        String methodName = tr.getMethod().getMethodName();
        Boolean testPassed = true;
        if (stat != status.PASS) {
            testPassed = false;
        }
        StringBuilder msg = new StringBuilder("Finished " + methodName + " test - ");
        if (testPassed) {
            msg.append("PASSED - Total Time: ");
            msg.append(duration);
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
}
