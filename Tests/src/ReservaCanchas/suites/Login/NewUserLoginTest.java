package ReservaCanchas.suites.Login;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import ReservaCanchas.Asserts.Administration.ManageUserAsserts;
import ReservaCanchas.Asserts.Login.LoginAsserts;
import ReservaCanchas.Features.Administration.Administration;
import ReservaCanchas.Features.Administration.ManageUsers;

import ReservaCanchas.Features.Login.Login;
import ReservaCanchas.Utils.DBConnectHelper;
import ReservaCanchas.suites.SuitesHelper;

import ReservaCanchas.common.Config;
import ReservaCanchas.common.DDT;
import ReservaCanchas.Common.Browser;

/**
 * Tests the use of newly added users for valid logins
 * 
 * @author robin
 * 
 */
public class NewUserLoginTest {

	private Browser browser;
	private Dashboard dashboard;
	private String user;

	@DataProvider(name = "newUserLogin")
	public static Object[][] data() {
		return DDT.DDTReader("DDT/NewUserLogin.csv", Config.getFilter());
	}

	@BeforeMethod(alwaysRun = true)
	public void setup() {

		browser = Config.getBrowser();

		SuitesHelper.login(browser);

		dashboard = new Dashboard(browser);
	}

	@AfterMethod(alwaysRun = true)
	public void tearDown() {

		// if we are not on the login page, logout
		if (!browser.fetch("top.location.href").contains("login.zul")) {
			dashboard.logout();
		}

		SuitesHelper.login(browser);

		// remove the previously added user
		Administration administration = dashboard.goTab_Administration();

		ManageUsers manageUsers = administration.goLnk_ManageUsers();

		// if the newly added user exists, remove it
		if (browser.isVisible(browser.link(this.user))) {

			manageUsers.selectUser(this.user);
			manageUsers.removeSelectedUsers();
			ManageUserAsserts.assertUserNotExists(browser, this.user);
		}

		SuitesHelper.logout(browser);
	}

	@Test(dataProvider = "newUserLogin")
	public void NewUserLogin(String fc, String TCID, String elementsUsername,
			String elementsPassword, String coreDbName, String elementsDbName,
			String sqlServerURL, String sqlUsername, String sqlPassword, String key) {

		this.user = elementsUsername;
		String addUserQuery = null;
		// give the new elements login user required permissions on the elements
		// repositories
		// =======================================================================
		if(key.equals("LOGIN_WITH_PUBLIC_PERMISSION_USER"))
			addUserQuery = "CREATE LOGIN [" + elementsUsername
			+ "] FROM WINDOWS; " + "use " + coreDbName + "; "
			+ "Create User [" + elementsUsername + "] For LOGIN ["
			+ elementsUsername + "]; "
			+ "use " + elementsDbName + "; " + "Create User ["
			+ elementsUsername + "] For LOGIN [" + elementsUsername + "]; ";
		else
			addUserQuery = "CREATE LOGIN [" + elementsUsername
			+ "] FROM WINDOWS; " + "use " + coreDbName + "; "
			+ "Create User [" + elementsUsername + "] For LOGIN ["
			+ elementsUsername + "]; "
			+ "exec sp_addrolemember 'db_owner', [" + elementsUsername
			+ "]; " + "use " + elementsDbName + "; " + "Create User ["
			+ elementsUsername + "] For LOGIN [" + elementsUsername + "]; "
			+ "exec sp_addrolemember 'db_owner', [" + elementsUsername
			+ "]; ";

		Connection conn = DBConnectHelper.createConnection(coreDbName,
				sqlServerURL, sqlUsername, sqlPassword);
		Assert.assertNotNull(conn, "Failed to get database connection.");
		try {
			DBConnectHelper.runUpdateQuery(conn, addUserQuery);
			// =======================================================================
	
			// add the new user
			Administration administration = dashboard.goTab_Administration();
	
			ManageUsers manageUsers = administration.goLnk_ManageUsers();
	
			manageUsers.addUser(elementsUsername);
			ManageUserAsserts.assertUserExists(browser, elementsUsername);
	
			dashboard.logout();
			// log in using the newly added user
			Login login = new Login(browser);
			login.setCredentials(elementsUsername, elementsPassword);
			LoginAsserts.assertUserName(browser, user, login);
			login.login(true);

			String pKey = "sql-elements-instance-grid-items-per-page-key";
			ResultSet rs = null;
			String qry = "";
	
			switch(key){
			case "VERIFY_LATEST_USER_DISPLAY_ON_LOGIN_PAGE":
				dashboard.logout();
				LoginAsserts.assertUserName(browser, elementsUsername, login);
				break;
			case "VERIFY_NEW_USER_PREF":
				qry = "SELECT UP.UserID, U.UserName, UP.PreferenceKey, UP.PreferenceValue "
						+ "FROM [" + coreDbName + "].[Common].[UserPreferences] as UP join "
						+ "[" + coreDbName + "].[Common].[Users] as U on UP.UserID = U.ID "
						+ "WHERE U.UserName = '" + elementsUsername + "'";
				this.verifyNewUserPreferences(conn, elementsUsername, elementsPassword,
						coreDbName, sqlServerURL, sqlUsername, sqlPassword, qry);
				dashboard.logout();
				break;
			case "VERIFY_USER_PREF_REMOVED":
				qry = "SELECT UP.UserID, U.UserName, UP.PreferenceKey, UP.PreferenceValue "
						+ "FROM [" + coreDbName + "].[Common].[UserPreferences] as UP join "
						+ "[" + coreDbName + "].[Common].[Users] as U on UP.UserID = U.ID "
						+ "WHERE U.UserName = '" + elementsUsername + "'";
				this.verifyNewUserPreferences(conn, elementsUsername, elementsPassword,
						coreDbName, sqlServerURL, sqlUsername, sqlPassword, qry);
				dashboard.logout();
				SuitesHelper.basicLogin(browser);
				// Remove the new User
				administration = dashboard.goTab_Administration();
				manageUsers = administration.goLnk_ManageUsers();
				manageUsers.selectUser(elementsUsername);
				manageUsers.removeSelectedUsers();
				DBConnectHelper.runUpdateQuery(conn, "DELETE FROM [" + coreDbName +
						"].[Common].[Users] WHERE Active = 0");
				rs = DBConnectHelper.runQuery(conn, qry);
				boolean res = this.checkPrefKeyExistsWithValue(rs, pKey, "50");
				Assert.assertFalse(res, pKey + " exists in the database after user has"
						+ " been removed.");
				dashboard.logout();
				break;
			case "VERIFY_USER_PREF_REMOVED_ADDED":
				qry = "SELECT UP.UserID, U.UserName, UP.PreferenceKey, UP.PreferenceValue "
						+ "FROM [" + coreDbName + "].[Common].[UserPreferences] as UP join "
						+ "[" + coreDbName + "].[Common].[Users] as U on UP.UserID = U.ID "
						+ "WHERE U.UserName = '" + elementsUsername + "'";
				this.verifyNewUserPreferences(conn, elementsUsername, elementsPassword,
						coreDbName, sqlServerURL, sqlUsername, sqlPassword, qry);
				dashboard.logout();
				SuitesHelper.basicLogin(browser);
				// Remove the new User
				administration = dashboard.goTab_Administration();
				manageUsers = administration.goLnk_ManageUsers();
				manageUsers.selectUser(elementsUsername);
				manageUsers.removeSelectedUsers();
				DBConnectHelper.runUpdateQuery(conn, "DELETE FROM [" + coreDbName +
						"].[Common].[Users] WHERE Active = 0");
				rs = DBConnectHelper.runQuery(conn, qry);
				boolean val = this.checkPrefKeyExistsWithValue(rs, pKey, "50");
				Assert.assertFalse(val, pKey + " exists in the database after user has"
						+ " been removed.");
				dashboard.logout();
				SuitesHelper.basicLogin(browser);
				// Create user again and login
				administration = dashboard.goTab_Administration();
				manageUsers = administration.goLnk_ManageUsers();
				manageUsers.addUser(elementsUsername);
				ManageUserAsserts.assertUserExists(browser, elementsUsername);
				dashboard.logout();
				// log in using the newly added user
				login = new Login(browser);
				login.setCredentials(elementsUsername, elementsPassword);
				LoginAsserts.assertUserName(browser, user, login);
				login.login(true);
				Assert.assertNotEquals(browser.textbox(1).getText(), "50",
						"Page size preference exists after adding removed user again.");
				break;
			default:
				dashboard.logout();
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
			Assert.assertTrue(false, e.getMessage());
		} finally {
			// remove the previously given permissions on the elements repositories
			// =======================================================================
			String removeUserQuery = "use " + coreDbName + "; " + "DROP USER ["
					+ elementsUsername + "]; " + "use " + elementsDbName + "; "
					+ "DROP USER [" + elementsUsername + "]; " + "DROP LOGIN ["
					+ elementsUsername + "]; ";
	
			conn = DBConnectHelper.createConnection(coreDbName, sqlServerURL,
					sqlUsername, sqlPassword);
			DBConnectHelper.runUpdateQuery(conn, removeUserQuery);
			// =======================================================================
		}

	}

	/**
	 * Method to verify the user preferences after new user login
	 * @param conn
	 * @param elementsUsername
	 * @param elementsPassword
	 * @param coreDbName
	 * @param sqlServerURL
	 * @param sqlUsername
	 * @param sqlPassword
	 */
	private void verifyNewUserPreferences(Connection conn, String elementsUsername,
			String elementsPassword, String coreDbName, String sqlServerURL,
			String sqlUsername, String sqlPassword, String qry) {
		// set instance count preference
		browser.setValue(browser.textbox(1), "50");
		browser.waitFor(1000);
		Assert.assertEquals(browser.textbox(1).getText(), "50", "Invalid page size");
		browser.textbox(1).keyDown(13, 0);
		browser.waitFor(5000);
		ResultSet rs = DBConnectHelper.runQuery(conn, qry);
		String prefKey = "sql-elements-instance-grid-items-per-page-key";
		boolean res = this.checkPrefKeyExistsWithValue(rs, prefKey, "50");
		Assert.assertTrue(res, prefKey + " is not found in the database.");
		// logout and login again to verify if prefvalue exists.
		dashboard.logout();
		// log in using the newly added user
		Login login = new Login(browser);
		login.setCredentials(elementsUsername, elementsPassword);
		LoginAsserts.assertUserName(browser, user, login);
		login.login(true);
		Assert.assertEquals(browser.textbox(1).getText(), "50",
				"Page size preference is not set correctly.");
	}

	/**
	 * Method to verify if the preference key and value exists in result set. 
	 * @param rs
	 * @param prefKey
	 * @param prefVal
	 * @return
	 */
	private boolean checkPrefKeyExistsWithValue(ResultSet rs, String prefKey, String prefVal) {
		boolean res = false;
		if (rs != null) {
			try {
				while (rs.next()) {
					String key = rs.getString("PreferenceKey");
					if (key != null && key.equals(prefKey)) {
						String val = rs.getString("PreferenceValue");
						if (val != null && val.equals(prefVal)) {
							res = true;
							break;
						}
					}
				}
			} catch (SQLException e) {
				e.printStackTrace();
				Assert.assertTrue(false, e.getMessage());
			} finally {
				try {
					rs.close();
				} catch (SQLException e) {}
			}
		}
		return res;
	}
}