package ReservaCanchas.Features.Login;

import org.testng.Assert;

import net.sf.sahi.client.ElementStub;
import ReservaCanchas.Asserts.Dashboard.DashboardAsserts;
import ReservaCanchas.Asserts.Login.LoginAsserts;


import ReservaCanchas.Common.Browser;

/**
 * @date 10/31/2013
 * @author Wilge Vargas
 */
public class Login {
	/*
	 * Instance of the SAHI browser object used to identify any object content on that
	 */
	private Browser browser;
	/*
	 * Identifier of the userName textbox in the Login UI
	 */
	private ElementStub txt_Username;
	/*
	 * Identifier of the password textbox in the Login UI
	 */
	private ElementStub txt_Password;
	/*
	 * Identifier of the Authentication Failure Text in the UI
	 */
	private ElementStub lbl_AuthFail;
	/*
	 * Identifier of the Login Button in the UI
	 */
	private ElementStub btn_Login;
	private ElementStub lbl_loginServiceError;
	private ElementStub lnk_Copyright;
	
	/**
	 * @return the lbl_loginServiceError
	 */
	public ElementStub getLbl_loginServiceError() {
		return lbl_loginServiceError;
	}

	/**
	 * @param lbl_loginServiceError the lbl_loginServiceError to set
	 */
	public void setLbl_loginServiceError(ElementStub lbl_loginServiceError) {
		this.lbl_loginServiceError = lbl_loginServiceError;
	}

	public Login(Browser b){
		this.browser = b;
		//Creating the UI objects instances
		lbl_loginServiceError=browser.div("application-requirements-div z-vlayout");
		txt_Username = browser.textbox("j_username");
		txt_Password = browser.password("j_password");
		lbl_AuthFail = browser.span("The user name or password you entered isn't correct. Contact your SQL Elements administrator to make sure you have permission to access SQL Elements.");
		btn_Login = browser.cell("LOG IN");
		lnk_Copyright = browser.span("z-text");
	}
	
	public ElementStub getLnk_Copyright() {
		return lnk_Copyright;
	}

	/**
	 * Method to open copyright link and its verification
	 */
	public String goLnk_Copyright() {
		Assert.assertTrue(browser.isVisible(lnk_Copyright), "Copyright link is not visible");
		Assert.assertTrue(lnk_Copyright.getText().contains("Idera Inc."),
				"Invalid copyright link text.");
		// Open link page
		browser.click(lnk_Copyright);
		browser.waitFor(10000);
		String url = browser.parentNode(lnk_Copyright).fetch("href");
		return url;
	}

	/**
	 * Sets the user name value in the textbox
	 * @param txt_Username The value to be entered in the textbox
	 */
	public void setUser(String user){
		browser.setValue(txt_Username,user);
		LoginAsserts.assertUserName(browser, user, this);
	}
	
	/**
	 * Sets the user password value in the textbox
	 * @param txt_Password The value to be entered in the textbox
	 */
	public void setPassword(String pass){
		browser.setValue(txt_Password, pass);
		LoginAsserts.assertUserPassword(browser, pass, this);
	}
	
	/**
	 * Sets the login information in the Login UI
	 * @param txt_Username The value to be used as user name
	 * @param txt_Password The value to be used as password
	 */
	public void setCredentials(String user, String pass){
		setUser(user);
		setPassword(pass);
	}

	/**
	 * Make the Login action doing a click over the login Button and waits 1 second by a response from the Server
	 */
	public void login(boolean success){
		browser.waitFor(3000);
		String userName = browser.getValue(getTxt_Username());
		String password = browser.getValue(getTxt_Password());
		
        browser.click(getBtn_Login());
        browser.waitFor(3000);

        if (browser.isVisible(getBtn_Login()))
        {	
    		setCredentials(userName, password);
    		browser.click(getBtn_Login());
            browser.waitFor(1000);
        }

        Dashboard dashboard = new Dashboard(browser);
        dashboard.closeWelcomeWizard();
        if (success)
        	LoginAsserts.assertLoginSuccess(browser, userName);
        else
        	LoginAsserts.assertLoginFailed(browser, userName,this);
	}
	/**
	 * Make the Login action After Stooping the Core Services
	 */
	public void loginWhenServiceDown(){
		String userName = browser.getValue(getTxt_Username());
		String password = browser.getValue(getTxt_Password());
		int numOfTries=0;
        browser.click(getBtn_Login());
        browser.waitFor(1000);
                
        if (browser.isVisible(getBtn_Login()))
        {	
    		setCredentials(userName, password);
    		
    		browser.click(getBtn_Login());
            browser.waitFor(1000);
        }
        
        Dashboard dashboard = new Dashboard(browser);
        dashboard.closeWelcomeWizard();  
        //Check if Service is Down
        while(!browser.isVisible(lbl_loginServiceError) && numOfTries < 60){
        	numOfTries++;
        }
        if(numOfTries==60)
        	DashboardAsserts.assertFailure("Object is Not Visible in Specifide Amout of Time");
        LoginAsserts.isServiceDown(lbl_loginServiceError);
	}
	 
	/**
	 * @return the username textbox
	 */
	public ElementStub getTxt_Username() {
		return txt_Username;
	}

	/**
	 * @return the password textbox
	 */
	public ElementStub getTxt_Password() {
		return txt_Password;
	}

	/**
	 * @return the login button
	 */
	public ElementStub getBtn_Login() {
		return btn_Login;
	}

	/**
	 * @return the authentication Failed message
	 */
	public ElementStub getLbl_AuthFail() {
		return lbl_AuthFail;
	}
	
}