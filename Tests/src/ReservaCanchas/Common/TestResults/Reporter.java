package ReservaCanchas.common.TestResults;

import java.util.List;
import java.util.Map;

import org.testng.IReporter;
import org.testng.ISuite;
import org.testng.ISuiteResult;
import org.testng.ITestContext;
import org.testng.xml.XmlSuite;

public class Reporter implements IReporter{

	private int totalExec = 0;
	@Override
	public void generateReport(List<XmlSuite> xmlSuites, List<ISuite> suites,
			String outputDirectory) {
		//Iterating over each suite included in the test
		for (ISuite suite : suites) {
			//Getting the results for the said suite
			Map<String, ISuiteResult> suiteResults = suite.getResults();
			for (ISuiteResult sr : suiteResults.values()) {
				ITestContext tc = sr.getTestContext();
				totalExec += tc.getPassedTests().getAllResults().size() +
						tc.getFailedTests().getAllResults().size() +
						tc.getSkippedTests().getAllResults().size();
			}
		}
	}
	public int getTotalExec() {
		return totalExec;
	}
}