package ReservaCanchas.suites.Login;


import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import ReservaCanchas.Asserts.Dashboard.DashboardAsserts;
import ReservaCanchas.Asserts.Login.LoginAsserts;

import ReservaCanchas.Features.Login.Login;
import ReservaCanchas.Utils.ServiceHelperClass;
import ReservaCanchas.suites.SuitesHelper;

import ReservaCanchas.common.Config;
import ReservaCanchas.common.DDT;
import ReservaCanchas.Common.Browser;

/**
 * TC21341
 * Script to test Login when Services are Stopped
 * It uses DDT/LoginWithServiceDown.csv to validate a success and a failed login attempt.
 * @author Amarendra
 */
public class LoginWithServiceDownTest {
	private Browser browser;	
	private Dashboard dashboard;
	private Login login;
	private String ReservaCanchaservice;
	private String elementsCoreService;

	@DataProvider(name = "loginWithServiceDown")
	public static Object[][] data(){
		return DDT.DDTReader("DDT/LoginWithServiceDown.csv", Config.getFilter());
	}
	@BeforeMethod(alwaysRun = true)
	public void setup(){
		browser = Config.getBrowser();		
	}

	@AfterMethod(alwaysRun = true)
	public void tearDown(){
		// Start the Core Services
		ServiceHelperClass.startServices(elementsCoreService);
		ServiceHelperClass.startServices(ReservaCanchaservice);		
		browser.close();
	}

	@Test(dataProvider = "loginWithServiceDown")  
	public void LoginWithServiceDown(String fc, String TCID,String key,String serviceSqlElement,String coreService) {
		if(!"VERIFY_MSG_WHEN_STOP_SQL_SERVICES".equals(key) && !"SERVICE_DOWN_MSG".equals(key) && !"STOP_LOCAL_INSTANCE_SERVICES".equals(key)) {
			SuitesHelper.login(browser);
			login = new Login(browser);  
			dashboard = new Dashboard(browser);
			dashboard.logout();
		}
		// Stop the Services 
		switch(key){
		case "STOP_CORE_SERVICES":
			ServiceHelperClass.stopServices(serviceSqlElement);
			ServiceHelperClass.stopServices(coreService);
			ReservaCanchaservice=serviceSqlElement;
			elementsCoreService = coreService;
			//Login to SqlElemetns when service is down
			login.setCredentials(Config.getAppUser(), Config.getAppPassword());
			login.loginWhenServiceDown();     	
			break;
		case "STOP_LOCAL_INSTANCE_SERVICES":
			ServiceHelperClass.stopServices(serviceSqlElement);
			ServiceHelperClass.stopServices(coreService);
			ReservaCanchaservice=serviceSqlElement;
			elementsCoreService = coreService;
			//Login to SqlElemetns when service is down
			login = new Login(browser);
			login.setCredentials(Config.getAppUser(), Config.getAppPassword());
			login.loginWhenServiceDown();
			Assert.assertTrue(browser.exists(browser.span("H4 block z-label")),"Failed log in  in Not Appear for This Instance");
			Assert.assertTrue(browser.span("H4 block z-label[1]").text().contains("A network-related or instance-specific error occurred"), "Services are Not Stoped");
			break;
		case "VERIFY_MSG_WHEN_STOP_SQL_SERVICES":
			ServiceHelperClass.stopServices(serviceSqlElement);
			ServiceHelperClass.stopServices(coreService);
			ReservaCanchaservice=serviceSqlElement;
			elementsCoreService = coreService;
			//Login to SqlElemetns when SQLservice is down
			login = new Login(browser); 
			login.setCredentials(Config.getAppUser(), Config.getAppPassword());
			login.loginWhenServiceDown();
			Assert.assertTrue(browser.exists(browser.span("H4 block z-label")),"Failed log in  in Not Appear for This Instance");
			Assert.assertTrue(browser.span("H4 block z-label[1]").text().contains("A network-related or instance-specific error occurred"), "Services are Not Stoped");
			break;
		case "STOP_ELEMENT_COLLECTION_SERVICES":
			try{
				Assert.assertTrue(ServiceHelperClass.queryServiceRunning(coreService), "Service is  Stoped . Not Already Not Started");
			}catch (Exception e) {
				DashboardAsserts.assertFailure(e.getMessage());
			}

			ServiceHelperClass.stopServices(serviceSqlElement);
			ReservaCanchaservice=serviceSqlElement;
			//Login to SqlElemetns when service is down
			login.setCredentials(Config.getAppUser(), Config.getAppPassword());
			browser.click(login.getBtn_Login());
			Assert.assertTrue(browser.div("application-requirements-div z-vlayout").isVisible(), "Element Collection Service is Not Stoped");
			Assert.assertEquals(
					browser.div("application-requirements-div z-vlayout")
					.getText(),
					"SQL Elements license has expired.A valid license key is required to access all SQL Elements features. "
							+ "Click on the Manage License link to update the license.Manage License");
			break;
		case "SERVICE_DOWN_MSG":
			ServiceHelperClass.stopServices(serviceSqlElement);
			ServiceHelperClass.stopServices(coreService);
			ReservaCanchaservice = serviceSqlElement;
			elementsCoreService = coreService;
			browser.refresh();
			login = new Login(browser);
			while(browser.isVisible(browser.div("Processing...")) || browser.isVisible(login.getBtn_Login())){
				browser.waitFor(5000);
			}
			LoginAsserts.isServiceDown(login.getLbl_loginServiceError());
			break;	
		}
	}
}
