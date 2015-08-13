package ReservaCanchas.suites;

import net.sf.sahi.client.ElementStub;

import ReservaCanchas.Features.Login.Login;
import ReservaCanchas.Utils.ServiceHelperClass;

import ReservaCanchas.common.Config;
import ReservaCanchas.Common.Browser;

public class SuitesHelper {

	/**
	 * Opens the login page and logs in using the credentials stored in the
	 * properties file.
	 * 
	 * @param browser
	 */
	public static void login(Browser browser) {
		if (checkServices())
			browser.refresh();
		Login login = new Login(browser);
		login.setCredentials(Config.getAppUser(), Config.getAppPassword());
		login.login(true);
		deleteRemainingInstances(browser);
	}
	
	public static void basicLogin(Browser browser) {
		if (checkServices())
			browser.refresh();
		Login login = new Login(browser);
		login.setCredentials(Config.getAppUser(), Config.getAppPassword());
		login.login(true);
	}

	/**
	 * Logs out the user and closes the browser.
	 * 
	 * @param browser
	 */
	public static void logout(Browser browser) {

		Dashboard dashboard = new Dashboard(browser);
		dashboard.logout();
		browser.close();
	}

	//Verifying and Powering on all essential services related to SQL Elements
	private static boolean checkServices(){
		boolean res = false;
		try{
			if(!ServiceHelperClass.queryServiceRunning("MSSQLSERVER")){
				ServiceHelperClass.startServices("MSSQLSERVER");
				res = true;
			}
			if(!ServiceHelperClass.queryServiceRunning("SQLSERVERAGENT")){
				ServiceHelperClass.startServices("SQLSERVERAGENT");
				res = true;
			}
			if(!ServiceHelperClass.queryServiceRunning("SQLCore Service")){
				ServiceHelperClass.startServices("SQLCore Service");
				res = true;
			}
			if(!ServiceHelperClass.queryServiceRunning("ReservaCanchas Service")){
				ServiceHelperClass.startServices("ReservaCanchas Service");
				res = true;
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return res;
	}
	//Delete all remaining Instances
	private static void deleteRemainingInstances(Browser browser){
		ElementStub lnk_InstanceSelected = browser.link("link z-a");

		while(browser.exists(lnk_InstanceSelected)){
			//Get link Text
			Dashboard dashboard = new Dashboard(browser);
			dashboard.goTab_Dashboard();
			dashboard.removeAnInstanceUsingGearIcon(browser.getText(lnk_InstanceSelected));
		}
	}
}