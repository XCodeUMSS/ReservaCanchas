package ReservaCanchas.suites.Login;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import ReservaCanchas.Asserts.Dashboard.DashboardAsserts;

import ReservaCanchas.Features.Login.Login;
import ReservaCanchas.suites.SuitesHelper;

import ReservaCanchas.common.Config;
import ReservaCanchas.common.DDT;
import ReservaCanchas.Common.Browser;

/**
* Script to test authentication<br>
* It uses <i>DDT/login.csv</i> to validate a success and a failed login attempt.
*
* @author Wilge Vargas
* @version 1.0
*/
public class AuthenticationTest {

	private Browser browser;
	private Login login;
	
	@DataProvider(name = "login")
    public static Object[][] data(){
		return DDT.DDTReader("DDT/Login.csv" ,Config.getFilter());
    }
    
    @BeforeMethod (alwaysRun = true)
    public void setUp() {
    	//Initializing the browser
    	this.browser = Config.getBrowser();
    	
    	//Initializing the Login page
        login = new Login(browser);
    }
    
    @AfterMethod (alwaysRun = true)
    public void tearDown() {
        browser.close();
    }
    
    @Test(dataProvider = "login")
    public void Login(String fc, String TCID, String userName, String userPass,
    		String expectedResult, String desc, String key) {

    	switch (key) {
    	case "VERIFY_COPYRIGHT_LINK":
    		String url = login.goLnk_Copyright();
    		Assert.assertTrue("http://www.idera.com/".equals(url), "Copyright link is not verified.");
    		break;
    	case "LISCESING_LINK_APPEARS_WITH_DEFAULT_LICENSE":
    		SuitesHelper.basicLogin(browser);
    		DashboardAsserts.assertElementExists(browser, browser.link("/Trial Copy. Valid until .*/"));
    		SuitesHelper.logout(browser);
    		break;
    	case "LOGIN_NOT_CASE_SENSITIVE_TEST":
    		verifyCaseSensitive(userName, userPass, expectedResult);
    		userName = userName.toLowerCase();
    		verifyCaseSensitive(userName, userPass, expectedResult);
    		userName = userName.toUpperCase();
    		verifyCaseSensitive(userName, userPass, expectedResult);
    		break;
    	default:
	    	login.setCredentials(userName, userPass);
	    	login.login(Boolean.valueOf(expectedResult));
	    	
	    	// only in the case of correct login
	    	if (Boolean.valueOf(expectedResult)){
	    		Dashboard dashboardHeader = new Dashboard(browser);
	        	dashboardHeader.logout();
	    	}
	    	break;
    	}
    	
    }

	/**
	 * @param userName
	 * @param userPass
	 * @param expectedResult
	 */
	private void verifyCaseSensitive(String userName, String userPass,
			String expectedResult) {
		login.setCredentials(userName, userPass);
		login.login(Boolean.valueOf(expectedResult));
		Dashboard dashboard = new Dashboard(browser);
		dashboard.logout();
		String loggedUser = login.getTxt_Username().getText();
		Assert.assertTrue(loggedUser.equals(userName), "Logged User Name in Not Same");
	}
}