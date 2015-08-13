/**
 * 
 */
package ReservaCanchas.suites.Login;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;


import ReservaCanchas.suites.SuitesHelper;

import ReservaCanchas.common.Config;
import ReservaCanchas.common.DDT;
import ReservaCanchas.Common.Browser;

/**
 * @author Rajesh
 */
public class LoginTimeoutTest {

	private Browser browser;
	private Dashboard dashboard;

	@DataProvider(name = "LoginTimeout")
	public static Object[][] data() {
		return DDT.DDTReader("DDT/LoginTimeout.csv", Config.getFilter());
	}

	@BeforeMethod(alwaysRun = true)
	public void setup() {

		browser = Config.getBrowser();
		SuitesHelper.login(browser);
		dashboard = new Dashboard(browser);
	}

	@AfterMethod(alwaysRun = true)
	public void tearDown() {
		SuitesHelper.logout(browser);
	}

	@Test (dataProvider = "LoginTimeout")
	public void loginTimeout(String fc, String tcId, String tcKey) {
		// Wait for specified time
		switch(tcKey) {
		case "TIMEOUT_AT_10":
			browser.waitFor(10 * 60000);
			dashboard.goTab_Dashboard();
			break;
		}
	}
}
