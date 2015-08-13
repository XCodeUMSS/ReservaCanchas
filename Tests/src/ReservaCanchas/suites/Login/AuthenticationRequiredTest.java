package ReservaCanchas.suites.Login;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import ReservaCanchas.Asserts.Login.LoginAsserts;

import ReservaCanchas.suites.SuitesHelper;

import ReservaCanchas.common.Config;
import ReservaCanchas.common.DDT;
import ReservaCanchas.common.Functions;
import ReservaCanchas.Common.Browser;

/**
 * Script to test authentication is required for the browser
 *
 * @author Uday
 */
public class AuthenticationRequiredTest {

	public final static String configFile = "../properties.xml";
	private Browser browser;
	private Dashboard dashboard;
	private String testFlag;

	@DataProvider(name = "AuthenticationRequired")
	public static Object[][] data(){
		return DDT.DDTReader("DDT/AuthenticationRequired.csv" ,Config.getFilter());
	}

	@BeforeMethod (alwaysRun = true)
	public void setUp() {
		//Initializing the browser
		browser = Config.getBrowser("chrome");
	}

	@AfterMethod (alwaysRun = true)
	public void tearDown() {
		if("AUTHENTICATION_REQUIRE_IN_FIREFOX_BROWSER".equals(testFlag)){
			SuitesHelper.logout(browser);
			browser.close();
		}
	}

	@Test(dataProvider = "AuthenticationRequired")
	public void Login(String fc, String TCID,String key) {
		testFlag = key;
		switch (key) {
		case "NO_AUTHENTICATION_CHROME":
			SuitesHelper.basicLogin(browser);
			Browser newWin = openWindow("chrome");
			dashboard = new Dashboard(newWin);
			dashboard.closeWelcomeWizard();
			LoginAsserts.assertLoginSuccess(newWin,Config.getAppUser());
			Functions.closeBrowserInstance(newWin, "chrome");
			newWin.close();
			break;
		case "AUTHENTICATION_REQUIRE_IN_FIREFOX_BROWSER":
			SuitesHelper.basicLogin(browser);
			Browser firefox = Config.getBrowser("firefox");
			Assert.assertTrue(firefox.span("Domain\\user name").isVisible() && firefox.span("Password").isVisible(), "authentication requiered for the browser");
			firefox.close();
			break;
		case "AUTHENTICATION_REQUIRE_IN_OTHER_CHROME_BROWSER":
			SuitesHelper.basicLogin(browser);
			browser.close();
			browser = Config.getBrowser("chrome");
			Assert.assertTrue(browser.span("Domain\\user name").isVisible() && browser.span("Password").isVisible(), "authentication requiered for the browser");
			browser.close();
			break;
		}
	}


	public static Browser openWindow(String browserName) {
		Properties props = new Properties();
		InputStream is = null;
		try {
			is = new FileInputStream(configFile);
			props.loadFromXML(is);
		}catch (FileNotFoundException ex) {
			ex.printStackTrace();
		}
		catch (IOException ex) {
			ex.printStackTrace();
		}
		String browserHost =  props.getProperty("browser.host");
		String browserPort =  props.getProperty("browser.port");
		Browser browser = new Browser(browserName, browserHost, Integer.parseInt(browserPort));
		browser.open();
		browser.navigateTo(Config.getServer().toString());
		return browser;
	}
}