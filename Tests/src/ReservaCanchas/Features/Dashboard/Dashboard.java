package ReservaCanchas.Features.Dashboard;

import java.util.List;

import net.sf.sahi.client.ElementStub;

import ReservaCanchas.Common.Browser;

/**
 * Class Dashboard Header that allow to us access to any object content in the
 * Main Header of the application.
 * 
 * @date 11/18/2013
 * @author Wilge Vargas
 */
public class Dashboard {
	// Instance of the SAHI browser object
	/** The browser. */
	private Browser browser;

	// Header options
	/** The lbl_ user. */
	private ElementStub lbl_User;

	/** The lnk_ logout. */
	private ElementStub lnk_Logout;

	/** The lnk_ help. */
	private ElementStub lnk_Help;

	/** The lnk_ settings. */
	private ElementStub lnk_Settings;
	private ElementStub lnk_GearIconHelp;
	private ElementStub lnk_GearIconKnowledgeBase;
	private ElementStub lnk_GearIconForum;
	private ElementStub lnk_GearIconSupport;
	private ElementStub lnk_GearIconUpdate;
	private ElementStub lnk_GearIconOther;
	private ElementStub lnk_GearIconFacebook;
	private ElementStub lnk_GearIconTwitter;
	private ElementStub lnk_GearIconAbout;

	// Main menu section
	/** The tab_ dashboard. */
	private ElementStub tab_Dashboard;

	/** The tab_ explorer. */
	private ElementStub tab_Explorer;

	/** The tab_ instances. */
	private ElementStub tab_Instances;

	/** The tab_ administration. */
	private ElementStub tab_Administration;

	// Welcome Wizard
	/** The lbl_ welcome. */
	private ElementStub lbl_Welcome;

	/** The btn_ cancel. */
	private ElementStub btn_Cancel;

	private ElementStub lbl_AutoRegistration;
	private ElementStub lbl_DataSize;
	private ElementStub lbl_LogSize;

	// Cancel Welcome Wizard dialog
	/** The lbl_ cancel wizard. */
	private ElementStub lbl_CancelWizard;

	/** The btn_ yes. */
	private ElementStub btn_Yes;

	/** The btn_ no. */
	private ElementStub btn_No;

	// Add SQL Server Instance link
	private ElementStub lnk_AddSQLInstance;

	//Instance link
	private ElementStub lnk_Instance;

	//Side Bar: Tags
	private TagsSection tagsSection;
	//Instance grid section
	private InstanceGrid instanceGrid;
	// Health recommendation export
	private ElementStub lnk_Export;

	/** The health recommendation. */
	private HealthRecommendation healtRecomendation;

	private ElementStub lbl_HealthCheckRecommendationCount;

	private ElementStub btn_PagingFirst;
	private ElementStub btn_PagingLast;
	private ElementStub btn_PagingNext;
	private ElementStub btn_PagingPrevious;
	private ElementStub lbl_PagingText;

	/**
	 * Create a new instance of the Dashboard Object.
	 * 
	 * @param browser
	 *            An instance of the current browser
	 */
	public Dashboard(Browser browser) {
		this.browser = browser;

		// Instantiate Header options
		lbl_User = browser.span("H4 z-label");
		lnk_Logout = browser.link("LOG OUT");
		lnk_Help = browser.link("HELP");

		// Gear Icon on header
		lnk_Settings = browser.image("settings_icon.png");
		// Links under gear icon on header
		lnk_GearIconHelp = browser.link("Help");
		lnk_GearIconKnowledgeBase = browser.link("Search knowledge base");
		lnk_GearIconForum = browser.link("Visit community forum");
		lnk_GearIconSupport = browser.link("Contact customer support");
		lnk_GearIconUpdate = browser.link("Check for updates...");
		lnk_GearIconOther = browser.link("Other Idera products");
		lnk_GearIconFacebook = browser.link("Idera on Facebook");
		lnk_GearIconTwitter = browser.link("Follow Idera on Twitter");
		lnk_GearIconAbout = browser.link("About SQL Elements");

		// Main menu
		tab_Dashboard = browser.div("DASHBOARD");
		tab_Explorer = browser.div("EXPLORER");
		tab_Instances = browser.div("INSTANCES");
		tab_Administration = browser.div("ADMINISTRATION");

		// Instantiate Welcome Wizard
		lbl_Welcome = browser.span("Welcome to Idera SQL Elements");
		btn_Cancel = browser.cell("CANCEL");

		lbl_AutoRegistration = browser.span("Auto-Registration");
		lbl_DataSize = browser.span("Data Size");
		lbl_LogSize = browser.span("Log Size");
		// Instantiate Cancel Welcome Wizard dialog
		lbl_CancelWizard = browser.span("Cancel Wizard");
		btn_Yes = browser.cell("Yes");
		btn_No = browser.cell("No");

		//Initializing Add SQL Server Instance link
		lnk_AddSQLInstance = browser.link("Add SQL Server Instance");
		// initialize export health recommendation
		lnk_Export = browser.link("Export");
		//Instantiate TagsSection
		tagsSection = new TagsSection(browser);

		//Initializing Instance grid
		instanceGrid = new InstanceGrid(browser);

		//Initializing HealthRecommendation
		healtRecomendation = new HealthRecommendation(browser);

		lbl_HealthCheckRecommendationCount = browser.div(
				"lighter-gray-background side-bar-item-count shadow z-div")
				.near(browser.span("Health Recommendations"));

		btn_PagingFirst = browser.button("z-paging-first");
		btn_PagingLast = browser.button("z-paging-last");
		btn_PagingNext = browser.button("z-paging-next");
		btn_PagingPrevious = browser.button("z-paging-prev");
		lbl_PagingText = browser.span("z-paging-text[1]");
	}

	/**
	 * Click on Instances link in side bar
	 * @return Instances object
	 */
	public Instances goLnk_InstancesFromSideBar() {
		browser.click(browser.span("Instances"));
		browser.waitFor(2000);
		DashboardAsserts.assertCurrentDashboard(browser, "INSTANCES", this);
		return new Instances(browser);
	}

	/**
	 * Click on Instance link
	 * @param instanceName
	 * @return InstanceDetails for an Instance
	 */
	public InstanceDetails goLnk_Instance(String instanceName){
		lnk_Instance = browser.link(instanceName);
		browser.click(lnk_Instance);
		browser.waitFor(5000);
		browser.waitObject(browser.span("INSTANCE DETAILS"));

		DashboardAsserts.assertOpenInstanceDetails(browser,"INSTANCE DETAILS");
		return new InstanceDetails(browser, instanceName);
	}

	/**
	 * Verify If exist an Instance(s)
	 * @param sqlServerNames
	 */
	public void verifyIfExistInstances(String... sqlServerNames) {

		for(String item:sqlServerNames){
			DashboardAsserts.assertExistInstance(browser,item);
		}
	}

	/**
	 * Add an Instance from Dashboard page
	 * 
	 * @param sqlServerName
	 * @param sqlUsername
	 * @param sqlPassword
	 * @param wmiUsername
	 * @param wmiPassword
	 * @param owner
	 * @param location
	 * @param comments
	 */
	public void addInstanceWizardAction(String sqlServerName,
			String sqlUsername, String sqlPassword, String wmiUsername,
			String wmiPassword, String owner, String location, String comments,
			String tag) {
		// Click on Add SQL Server link
		AddInstanceWizard addInstanceWizard = goLnk_AddSQLInstance();

		// use the add instance wizard to add the instance
		addInstanceWizard.addInstance(sqlServerName, sqlUsername, sqlPassword,
				wmiUsername, wmiPassword, owner, location, comments, tag);

		// Verify If the Instance was added
		verifyIfExistInstances(sqlServerName);
	}

	/**
	 * Method to verify if the tag present in see all tags list
	 * @param sqlServerName
	 * @param sqlUsername
	 * @param sqlPassword
	 * @param wmiUsername
	 * @param wmiPassword
	 * @param owner
	 * @param location
	 * @param comments
	 * @param tag
	 */
	public void verifyTagInAddInstanceWizard(String sqlServerName, String sqlUsername,
			String sqlPassword, String wmiUsername, String wmiPassword, String owner,
			String location, String comments, String tag) {

		// Click on Add SQL Server link
		AddInstanceWizard addInstanceWizard = goLnk_AddSQLInstance();

		// use the add instance wizard to add the instance
		addInstanceWizard.verifyTagInAddInstanceWizard(sqlServerName, sqlUsername, sqlPassword,
				wmiUsername, wmiPassword, owner, location, comments, tag);
	}

	/**
	 * Registers a list of instances using the Add Instance Wizard
	 * 
	 * @param sqlServerNames
	 * @param sqlUsername
	 * @param sqlPassword
	 * @param wmiUsername
	 * @param wmiPassword
	 * @param owner
	 * @param location
	 * @param comments
	 * @param tag
	 */
	public void addBulkInstanceWizardAction(List<String> sqlServerNames,
			String sqlUsername, String sqlPassword, String wmiUsername,
			String wmiPassword, String owner, String location, String comments,
			String tag, boolean verifyExistence) {

		// Click on Add SQL Server link
		AddInstanceWizard addInstanceWizard = goLnk_AddSQLInstance();

		// add all instances in one go using the add instance wizard
		addInstanceWizard.addInstances(sqlServerNames, sqlUsername,
				sqlPassword, wmiUsername, wmiPassword, owner, location,
				comments, tag);

		if (verifyExistence) {
			for (String instance : sqlServerNames) {
				// Verify If the Instance was added
				verifyIfExistInstances(instance);
			}
		}
	}

	/**
	 * Remove an Instance using gear Icon
	 * @param instanceName
	 */
	public void removeAnInstanceUsingGearIcon(String instanceName){

		if(instanceName == null || instanceName.trim().isEmpty()) return;
		// Initializing Row Instance
		instanceGrid.initiliazingRowInstanceSelected(instanceName);

		if(!browser.exists(instanceGrid.getImg_GearIconSelected())) return;
		//Click on Gear Icon
		instanceGrid.goImg_GearIconSelected();

		//Click on Remove option
		instanceGrid.goLnk_GearOptionRemove();

		//Close Wizard
		closeWelcomeWizard();

		//Remove Dialog: Click on Yes
		instanceGrid.goBtn_RemoveDialogYes();

		verifyNoExistInstances(instanceName);

		//Close Wizard
		closeWelcomeWizard();
	}

	/**
	 * Refresh an Instance using gear Icon
	 * @param instanceName
	 */
	public void refreshAnInstanceUsingGearIcon(String instanceName){

		// Initializing Row Instance
		instanceGrid.initiliazingRowInstanceSelected(instanceName);

		//Click on Gear Icon
		instanceGrid.goImg_GearIconSelected();

		//Click on Refresh option
		instanceGrid.goLnk_GearOptionRefreshData();

		browser.waitFor(4000);

		InstancesAsserts
		.assertOpenConfirmationDialog(browser, "Success");

		ElementStub okButton = browser.cell("OK");		
		browser.click(okButton);
	}

	/**
	 * Method to disable an instance using gear icon menu
	 * @param instanceName
	 */
	public void disableAnInstanceUsingGearIcon(String instanceName) {

		// Initializing Row Instance
		instanceGrid.initiliazingRowInstanceSelected(instanceName);
		// Click on Gear Icon
		instanceGrid.goImg_GearIconSelected();
		// Select link disable monitoring
		instanceGrid.goLnk_GearOptionDisableMonitoring();
		browser.waitFor(2000);
		InstancesAsserts.assertOpenConfirmationDialog(browser, "Disable Monitoring? " + instanceName);
		ElementStub yes = browser.cell("Yes");		
		browser.click(yes);
		browser.waitFor(2000);
		// Verify instance is disabled
		DashboardAsserts.assertInstanceIsDisabled(browser, instanceName);
	}

	/**
	 * Method to open Edit Properties dialog from Gear icon
	 * @param server
	 * @return
	 */
	public EditProperties getEditProperties(String server) {
		// Initializing Row Instance
		instanceGrid.initiliazingRowInstanceSelected(server);
		// Click on Gear Icon
		instanceGrid.goImg_GearIconSelected();		
		// Select link disable monitoring
		instanceGrid.goLnk_GearOptionEditProperties();
		browser.waitFor(1000);
		InstancesAsserts.assertOpenConfirmationDialog(browser, "Edit Instance Properties");
		return new EditProperties(browser, server);
	}

	/**
	 * Method to enable an instance using gear icon menu
	 * @param instanceName
	 */
	public void enableAnInstanceUsingGearIcon(String instanceName) {

		// Initializing Row Instance
		instanceGrid.initiliazingRowInstanceSelected(instanceName);
		// Click on Gear Icon
		instanceGrid.goImg_GearIconSelected();
		// Select link enable monitoring
		browser.click(browser.link("Enable Monitoring"));
		browser.waitFor(2000);
	}

	/**
	 * Verify If no exists an Instance(s)
	 * @param sqlServerNames
	 */
	private void verifyNoExistInstances(String... sqlServerNames) {

		for(String item:sqlServerNames){
			DashboardAsserts.assertNoExistInstance(browser,item);
		}

	}

	/**
	 * Click on Add SQL Instance link
	 * @return Instance AddInstanceWizard
	 */
	public AddInstanceWizard goLnk_AddSQLInstance(){
		browser.click(lnk_AddSQLInstance);
		browser.waitFor(1000);

		AddInstanceWizard addWizard = new AddInstanceWizard(browser);
		AddInstanceWizardAsserts.assertCurrentAddInstanceWizard(browser, "SQL Server Instance", addWizard);

		return addWizard;
	}

	public WelcomeWizard getWelcomeWizard() {
		DashboardAsserts.assertElementExists(browser, lbl_Welcome);
		return new WelcomeWizard(browser);
	}

	/**
	 * Close the Welcome Wizard using the Cancel button and selecting the Yes option
	 */
	public void closeWelcomeWizard() {
		if (browser.exists(lbl_Welcome)) {
			browser.click(btn_Cancel);
		}
		browser.waitFor(1000);
		if (browser.exists(lbl_CancelWizard)) {
			browser.click(btn_Yes);
		}
		browser.waitFor(2000);
	}

	/**
	 * Click the Dashboard menu and return the objects in the browser.
	 * 
	 * @return The Dashboard objects
	 */
	public Dashboard goTab_Dashboard(){
		browser.click(tab_Dashboard);
		DashboardAsserts.assertCurrentDashboard(browser, "DASHBOARD", this);
		// Close no instance dialog if opened.
		if (browser.exists(browser.span("WELCOME"))) {
			browser.click(browser.cell("CANCEL"));
			browser.waitFor(500);
			browser.click(browser.cell("Yes"));
		}
		return new Dashboard(browser);
	}

	/**
	 * Click the Dashboard menu and return the objects in the browser.
	 * @return The Dashboard objects
	 */
	public Dashboard open_Dashboard() {
		browser.click(tab_Dashboard);
		DashboardAsserts.assertCurrentDashboard(browser, "DASHBOARD", this);
		return new Dashboard(browser);
	}

	/**
	 * Click the Explorer menu and return the objects in the browser.
	 * 
	 * @return The Explorer objects
	 */
	public Explorer goTab_Explorer(){
		browser.click(tab_Explorer);
		DashboardAsserts.assertCurrentDashboard(browser, "EXPLORER", this);

		browser.waitFor(2000);
		return new Explorer(browser);
	}

	/**
	 * Click the Explorer menu and return the objects in the browser.
	 * 
	 * @return The Explorer objects
	 */
	public Instances goTab_Instances() {
		browser.click(tab_Instances);
		DashboardAsserts.assertCurrentDashboard(browser, "INSTANCES", this);
		Instances instances = new Instances(browser);
		InstancesAsserts.assertElementExists(browser, instances.getBtn_managedInstances());
		InstancesAsserts.assertElementExists(browser, instances.getBtn_discoveredInstances());
		InstancesAsserts.assertElementExists(browser, instances.getBtn_ignoredInstances());
		InstancesAsserts.assertElementExists(browser, instances.getBtn_licensingInstances());
		return instances;
	}

	/**
	 * Click on Add SQL Instance link
	 * @return Instance AddInstanceWizard
	 */
	/*public AddInstanceWizard goLnk_AddSQLInstance(){
		browser.click(lnk_AddSQLInstance);
		browser.waitFor(1000);

		AddInstanceWizard addWizard = new AddInstanceWizard(browser);
		AddInstanceWizardAsserts.assertCurrentAddInstanceWizard(browser, "SQL Server Instance", addWizard);

		return addWizard;
	}*/

	/**
	 * Click the Explorer menu and return the objects in the browser
	 * @return The Explorer objects
	 */
	public Administration goTab_Administration() {
		browser.click(tab_Administration);
		DashboardAsserts
		.assertCurrentDashboard(browser, "ADMINISTRATION", this);
		return new Administration(browser);
	}

	public String getLogSize() {
		return browser.span("/.*MB/").near(getLbl_LogSize()).getText();
	}

	public String getDataSize() {
		return browser.span("/.*MB/").near(getLbl_DataSize()).getText();
	}

	/**
	 * Gets the tab_ dashboard.
	 * 
	 * @return The Dashboard object
	 */
	public ElementStub getTab_Dashboard() {
		return tab_Dashboard;
	}

	/**
	 * Gets the tab_ explorer.
	 * 
	 * @return The Explorer object
	 */
	public ElementStub getTab_Explorer() {
		return tab_Explorer;
	}

	/**
	 * Gets the tab_ instances.
	 * 
	 * @return The Instances object
	 */
	public ElementStub getTab_Instances() {
		return tab_Instances;
	}

	/**
	 * Gets the tab_ administration.
	 * 
	 * @return The Administration object
	 */
	public ElementStub getTab_Administration() {
		return tab_Administration;
	}

	/**
	 * Returns a Dashboard ID based on a Dashboard name.
	 * 
	 * @param dashboardName
	 *            The name of the Dashboard
	 * @return The Dashboard ID
	 */
	public ElementStub getDashboardID(String dashboardName) {
		return browser.div(dashboardName);
	}

	/**
	 * Returns the Username label.
	 * 
	 * @return The Username value in the header
	 */
	public ElementStub getLbl_User() {
		return lbl_User;
	}

	/**
	 * Returns the element identifier for the Logout link.
	 * 
	 * @return The LOGOUT link
	 */
	public ElementStub getLnk_Logout() {
		return lnk_Logout;
	}

	/**
	 * Returns the element identifier for the Help link.
	 * 
	 * @return The HELP link
	 */
	public ElementStub getLnk_Help() {
		return lnk_Help;
	}

	/**
	 * Returns the element identifier for the auto registration link
	 * @return
	 */
	public ElementStub getLbl_AutoRegistration() {
		return lbl_AutoRegistration;
	}

	/**
	 * Open the Auto registration dialog.
	 * @return AutoRegistrationOptions
	 */
	public AutoRegistrationOptions goLbl_AutoRegistration() {
		browser.click(lbl_AutoRegistration);
		browser.waitFor(1000);

		AutoRegistrationOptionsAsserts.assertAutoRegistrationOptionsOpen(browser, "Auto Registration Options");
		return new AutoRegistrationOptions(browser);
	}

	/**
	 * Returns the element identifier for the Setting image (gear).
	 * 
	 * @return The gear (Settings) link
	 */
	public ElementStub getLnk_Settings() {
		return lnk_Settings;
	}

	public ElementStub getLnk_GearIconHelp() {
		return lnk_GearIconHelp;
	}

	public ElementStub getLnk_GearIconKnowledgeBase() {
		return lnk_GearIconKnowledgeBase;
	}

	public ElementStub getLnk_GearIconForum() {
		return lnk_GearIconForum;
	}

	public ElementStub getLnk_GearIconSupport() {
		return lnk_GearIconSupport;
	}

	public ElementStub getLnk_GearIconUpdate() {
		return lnk_GearIconUpdate;
	}

	public ElementStub getLnk_GearIconOther() {
		return lnk_GearIconOther;
	}

	public ElementStub getLnk_GearIconFacebook() {
		return lnk_GearIconFacebook;
	}

	public ElementStub getLnk_GearIconTwitter() {
		return lnk_GearIconTwitter;
	}

	public ElementStub getLnk_GearIconAbout() {
		return lnk_GearIconAbout;
	}

	/**
	 * Method to open about dialog
	 */
	public void goLnk_GearIconAbout() {
		// Click on gear icon
		goLnk_Settings();
		// Open the about page dialog
		browser.click(lnk_GearIconAbout);
		browser.waitFor(2000);
		DashboardAsserts.assertElementExists(browser, browser.span("Idera SQL Elements"));
		DashboardAsserts.assertElementExists(browser, browser.link("Visit http://www.idera.com"));
	}

	/**
	 * Click on gear icon on header
	 */
	public void goLnk_Settings() {
		browser.click(lnk_Settings);
		browser.waitFor(1000);
		DashboardAsserts.assertHeaderGearIconAllLinksPresent(this);
	}

	/**
	 * Click on a specified link under gear icon on header
	 * @param lnk
	 */
	public void goLnk_UnderGearIcon(ElementStub lnk) {
		// Click on gear icon
		goLnk_Settings();
		lnk.click();
	}

	/**
	 * Clicks LOGOUT link of the Header menu.
	 */
	public void logout() {
		browser.click(lnk_Logout);
		DashboardAsserts.assertLogout(browser, this);
	}

	/**
	 * Gets the tags section.
	 * 
	 * @return the tagsSection
	 */
	public TagsSection getTagsSection() {
		return tagsSection;
	}

	/**
	 * Sets the tags section.
	 * 
	 * @param tagsSection
	 *            the tagsSection to set
	 */
	public void setTagsSection(TagsSection tagsSection) {
		this.tagsSection = tagsSection;
	}

	/**
	 * @return the lnk_AddSQLInstance
	 */
	public ElementStub getLnk_AddSQLInstance() {
		return lnk_AddSQLInstance;
	}


	/**
	 * @param lnk_AddSQLInstance the lnk_AddSQLInstance to set
	 */
	public void setLnk_AddSQLInstance(ElementStub lnk_AddSQLInstance) {
		this.lnk_AddSQLInstance = lnk_AddSQLInstance;
	}

	/**
	 * @return the instanceGrid
	 */
	public InstanceGrid getInstanceGrid() {
		return instanceGrid;
	}

	/**
	 * @param instanceGrid the instanceGrid to set
	 */
	public void setInstanceGrid(InstanceGrid instanceGrid) {
		this.instanceGrid = instanceGrid;
	}

	/**
	 * Gets the health recommendation.
	 * 
	 * @return the health recommendation
	 */
	public HealthRecommendation getHealhtRecommendation() {
		return this.healtRecomendation;
	}

	/**
	 * Sets the healt recomendation.
	 * 
	 * @param recomendation
	 *            the new healt recomendation
	 */
	public void setHealtRecomendation(HealthRecommendation recomendation) {
		this.healtRecomendation = recomendation;
	}

	/**
	 * Gets the link of an instances according the name.
	 * 
	 * @param name
	 *            the name
	 * @return the lnk_ instances
	 */
	public ElementStub getLnk_Instances(String name) {

		if (browser.link(name).exists()) {
			return browser.link(name);
		}

		return null;
	}

	/**
	 * Click on instance.
	 * 
	 * @param name the name
	 */
	public void clickOnInstance(String name) {
		ElementStub instance = getLnk_Instances(name);

		if (instance != null) {
			browser.click(instance);
		}
	}

	public ElementStub getLnk_Export()
	{
		return lnk_Export;
	}

	/**
	 * Click on Refresh Data link
	 */
	public ExportHealthCheckRecommendations goLnk_Export(){
		browser.click(lnk_Export);
		browser.waitFor(2000);

		ExportHealthCheckReportAsserts.assertExportDialogOpened(browser, "Export Health Check Recommendations");
		return new ExportHealthCheckRecommendations(browser);
	}

	public ElementStub getLbl_HealthCheckRecommendationCount() {
		return lbl_HealthCheckRecommendationCount;
	}

	public ElementStub getBtn_PagingFirst() {
		return btn_PagingFirst;
	}

	public ElementStub getBtn_PagingLast() {
		return btn_PagingLast;
	}

	public ElementStub getBtn_PagingNext() {
		return btn_PagingNext;
	}

	public ElementStub getBtn_PagingPrevious() {
		return btn_PagingPrevious;
	}

	public ElementStub getLbl_PagingText() {
		return lbl_PagingText;
	}

	public int getListedInstancesCount() {
		int counter = 0;
		while(browser.image("gray-gear-16x16.png[" + counter + "]").exists()) {
			counter++;
		}
		return counter;
	}

	public ElementStub getLbl_Welcome() {
		return lbl_Welcome;

	}

	public ElementStub getLbl_LogSize(){
		return lbl_LogSize;
	}

	public ElementStub getLbl_DataSize(){
		return lbl_DataSize;
	}

	public void goBtn_Yes() {
		browser.click(btn_Yes);
	}

}