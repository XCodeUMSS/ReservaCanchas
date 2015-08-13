package ReservaCanchas.Asserts.Login;

import net.sf.sahi.client.ElementStub;

import org.testng.Assert;


import ReservaCanchas.Features.Login.Login;

import ReservaCanchas.Common.Browser;
/**
 * Assertions to verify the Login actions
 * @date 11/18/2013
 * @author Wilge Vargas
 */
public class LoginAsserts {

	/**
	 * Assertion to verify if the Login Actions has been successfully
	 * @param browser
	 * @param user User name of the account to be used in the login 
	 */
	public static void assertLoginSuccess(Browser browser, String user){
	}

	/**
	 * Assertion to verify if the login action has been failed
	 * @param browser
	 * @param user User name of the account to be used in the login 
	 * @param login Instance of the Login object to be used to get the objects
	 */
	public static void assertLoginFailed(Browser browser, String user, Login login){
//		Dashboard dh = new Dashboard(browser);
//		Assert.assertFalse(browser.isVisible(dh.getLbl_User()), "Login Success, User Name exists");
//		Assert.assertTrue(browser.isVisible(login.getLbl_AuthFail()), "Authentication Failure message doesn't exists");
//		Assert.assertFalse(browser.isVisible(dh.getLnk_Logout()), "Login Success, Logout Link exists");
	}

	/**
	 * Assertion to verify if the user name has been set correctly
	 * @param browser
	 * @param user user name that should be set in the User textbox
	 * @param login Instance of the Login Object to be used to get the objects
	 */
	public static void assertUserName(Browser browser, String user, Login login) {
		Assert.assertEquals(browser.getText(login.getTxt_Username()),user, "User Names was not entered correctly");
	}

	/**
	 * Assertion to verify if the user password has been set correctly
	 * @param browser
	 * @param pass Password value that should be set in the Password field
	 * @param login Instance of the Login Object to be used to get the objects
	 */
	public static void assertUserPassword(Browser browser, String pass, Login login) {
		Assert.assertEquals(browser.getText(login.getTxt_Password()),pass, "Password was not entered correctly");
	}

	/**
	 * 
	 * @param serviceStatus
	 */
	public static void assertStartServiceStatus(int serviceStatus){
		// If values equal 4 service started. If value equals 1056, service was already started
		Assert.assertTrue(serviceStatus == 4 || serviceStatus == 1056, "Service failed to start, exit code is: " + serviceStatus);
	}

	/**
	 * 
	 * @param serviceStatus
	 */
	public static void assertStopServiceStatus(int serviceStatus){
		// If values equal 1 service stopped. If value equals 1062, service was already stopped
		Assert.assertTrue(serviceStatus == 1 || serviceStatus == 1062, "Service failed to stop, exit code is: " + serviceStatus);
	}

	/**
	 * 
	 * @param serviceStatus
	 */
	public static void assertCmdStatus(int cmdStatus, String cmd) {
		// If values equal 0 process Success.
		Assert.assertTrue(cmdStatus == 0, "Command is not successful: " + cmd);
	}

	/**
	 * 
	 * @param lbl_loginServiceError
	 * @param errorMessage
	 */
	public static void isServiceDown(ElementStub lbl_loginServiceError) {
		Assert.assertTrue(lbl_loginServiceError.isVisible(), "Error Dialog not visible. Core Service is Not Stoped");
		Assert.assertTrue(
				lbl_loginServiceError.getText().startsWith(
				"ErrorSQL Elements cannot run because some application requirements are not met.Idera Core Services service is not available.Service URL: "),
				"Core Service is Not Stoped. Login Success");
		Assert.assertTrue(
				lbl_loginServiceError.getText().endsWith(
				"/IderaCoreServicesConnection refused: connect"),
				"Core Service is Not Stoped. Login Success");
	}

	public static void assertPauseServiceStatus(int serviceStatus) {
		// if values is not equal 7 process failed
		Assert.assertTrue(serviceStatus == 7 , "Service failed to pause");
	}
}