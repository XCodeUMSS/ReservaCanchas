package ReservaCanchas.suites.Login;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;


import ReservaCanchas.Features.Login.Login;
import ReservaCanchas.suites.SuitesHelper;

import ReservaCanchas.common.Config;
import ReservaCanchas.common.DDT;
import ReservaCanchas.Common.Browser;

/**
 * @author Uday
 */
public class LoginWithoutDomainTest {

	private Browser browser;
	private Dashboard dashboard;

	@DataProvider(name = "LoginWithoutDomain")
	public static Object[][] data() {
		return DDT.DDTReader("DDT/LoginWithoutDomain.csv", Config.getFilter());
	}

	@BeforeMethod(alwaysRun = true)
	public void setup() {
		browser = Config.getBrowser();
	}

	@AfterMethod(alwaysRun = true)
	public void tearDown() {
		browser.close();
	}

	@Test (dataProvider = "LoginWithoutDomain")
	public void loginTimeout(String fc, String tcId, String tcKey,String userName, String password) {
		switch(tcKey) {
		case "LOGIN_WITHOUT_DOMAIN":
			//Login with Domain\\username format and verify login successful
			SuitesHelper.login(browser);
			dashboard = new Dashboard(browser);
			dashboard.logout();
			//Login with without domain and verify login successful
			Login login = new Login(browser);
			login.setCredentials(userName, password);
			login.login(false);
			break;
		}
	}
}