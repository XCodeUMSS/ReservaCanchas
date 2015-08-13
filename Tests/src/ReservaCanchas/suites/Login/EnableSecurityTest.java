package ReservaCanchas.suites.Login;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import ReservaCanchas.Asserts.Administration.ManageHealthChecksAsserts;
import ReservaCanchas.Asserts.Administration.ManageUserAsserts;
import ReservaCanchas.Features.Administration.Administration;
import ReservaCanchas.Features.Administration.ManageHealthChecks;
import ReservaCanchas.Features.Administration.ManageLicense;
import ReservaCanchas.Features.Administration.ManageUsers;

import ReservaCanchas.Features.Explorer.Explorer;
import ReservaCanchas.Features.HealthRecommendation.HealthRecommendation;
import ReservaCanchas.Features.Instances.Instances;
import ReservaCanchas.Utils.ServiceHelperClass;
import ReservaCanchas.suites.SuitesHelper;
import ReservaCanchas.suites.Explorer.ExportExplorerViewInstancesListTest;

import ReservaCanchas.common.Config;
import ReservaCanchas.common.DDT;
import ReservaCanchas.Common.Browser;

/**
 * Tests the configuration file update of ReservaCanchas
 * @author Amarendra
 */
public class EnableSecurityTest {

	private Browser browser;
	private Dashboard dashboard;
	private File corefileName;
	private File elementFileName;
	private String xpathElementToReset;
	private String xpathCoreToReset;
	private List<String> instanceToRemove;

	@DataProvider(name = "enableSecurity")
	public static Object[][] data() {
		return DDT.DDTReader("DDT/EnableSecurity.csv", Config.getFilter());
	}

	@BeforeMethod(alwaysRun = true)
	public void setup() {

		browser = Config.getBrowser();

		SuitesHelper.login(browser);
		instanceToRemove = new ArrayList<>();
		corefileName = null ;
		xpathElementToReset = "";
		xpathCoreToReset = "";
		elementFileName = null;
		dashboard = new Dashboard(browser);
	}

	@AfterMethod(alwaysRun = true)
	public void tearDown() {

		dashboard.goTab_Dashboard();
		if(corefileName != null )
			updateConfigFile(corefileName, xpathCoreToReset, "False");
		if (elementFileName != null)
			updateConfigFile(elementFileName, xpathElementToReset, "False");
		for (String instance : instanceToRemove) {
			dashboard.removeAnInstanceUsingGearIcon(instance);
		}
		SuitesHelper.logout(browser);

	}

	@Test(dataProvider = "enableSecurity")
	public void EnableSecurity(String fc, String TCID, String key, String filePath, String fileName, String xPathElement, String coreFilePath, String coreFileName,
			String sqlServerName, String sqlUsername, String sqlPassword, String wmiUsername, String wmiPassword, String  xPathCoreService) {
		File file = null;
		instanceToRemove.add(sqlServerName);
		instanceToRemove.add("AUT-SQL2008R2\\SQL2008R2");
		switch(key){
		case "VERIFY_ENABLE_SECUTITY_OPTION":
			file= ExportExplorerViewInstancesListTest.validateDownloadedFile(filePath,fileName);
			String subTitleReport = ExportExplorerViewInstancesListTest.getTextAtXPathInXML(file,xPathElement);
			Assert.assertEquals(subTitleReport, "False", "Enable Security Option in Not Set To Default Mode");
			break;
		case "UPDATE_ENABLE_SECUTITY_OPTION":
			updateConfigrationFileForElements(filePath, fileName, xPathElement,
					coreFilePath, coreFileName, xPathCoreService);
			break;
		case "BASIC_DASHBOARD_VIEW_WITH_SECURITY_OPTION":
			dashboard.addInstanceWizardAction(sqlServerName, sqlUsername, sqlPassword, wmiUsername, wmiPassword, "", "", "", "");
			updateConfigrationFileForElements(filePath, fileName, xPathElement,
					coreFilePath, coreFileName, xPathCoreService);
			dashboard.addInstanceWizardAction(instanceToRemove.get(1), sqlUsername, "Control123", wmiUsername, wmiPassword, "", "", "", "");
			browser.waitFor(3000);
			dashboard.goLnk_Instance(sqlServerName);
			dashboard.goTab_Dashboard();
			dashboard.disableAnInstanceUsingGearIcon(instanceToRemove.get(1));
			dashboard.enableAnInstanceUsingGearIcon(instanceToRemove.get(1));
			browser.waitFor(5000);
			browser.refresh();
			HealthRecommendation hcRecomendation = dashboard.getHealhtRecommendation();
			hcRecomendation.loadRecommendations();
			hcRecomendation.dismissRecommendation("/.*databases do not have a backup./");
			hcRecomendation.clickLnk_ShowFewerRecommendations();
			break;
		case "BASIC_EXPLORER_VIEW_WITH_SECURITY_OPTION":
			dashboard.addInstanceWizardAction(sqlServerName, sqlUsername, sqlPassword, wmiUsername, wmiPassword, "OwnerTest", "LocationTest", "", "TagTest");
			updateConfigrationFileForElements(filePath, fileName, xPathElement,
					coreFilePath, coreFileName, xPathCoreService);
			dashboard.addInstanceWizardAction(instanceToRemove.get(1), sqlUsername, "Control123", wmiUsername, wmiPassword, "", "", "", "");
			Explorer explorer = dashboard.goTab_Explorer();
			explorer.goTagFilterGroupbox();
			explorer.selectFilter(explorer.getTagFilterGroupbox(), "TagTest");
			explorer.selectViewSelectionBy("Instance");
			explorer.goLnk_Instance(sqlServerName);
			dashboard.goTab_Explorer();
			explorer.refreshInstanceDataByGearIcon(sqlServerName);
			break;
		case "BASIC_INSTANCE_VIEW_WITH_SECURITY_OPTION":
			dashboard.addInstanceWizardAction(sqlServerName, sqlUsername, sqlPassword, wmiUsername, wmiPassword, "OwnerTest", "LocationTest", "", "TagTest");
			updateConfigrationFileForElements(filePath, fileName, xPathElement,
					coreFilePath, coreFileName, xPathCoreService);
			Instances instances = dashboard.goTab_Instances();
			instances.addInstanceWizardAction(instanceToRemove.get(1), sqlUsername, "Control123", wmiUsername, wmiPassword, "", "", "", "");
			instances.goLnk_Instance(sqlServerName);
			dashboard.goTab_Instances();
			instances.clickGearIconImage(sqlServerName);
			instances.goLnk_GearMenuItemDisableMonitoring();
			instances.goBtn_DisableInstancesYes();
			instances.refreshAnInstanceUsingGearIcon(instanceToRemove.get(1));
			browser.waitFor(3000);
			break;
		case "BASIC_ADMINISTRATION_VIEW_WITH_SECURITY_OPTION":
			dashboard.addInstanceWizardAction(sqlServerName, sqlUsername, sqlPassword, wmiUsername, wmiPassword, "OwnerTest", "LocationTest", "", "TagTest");
			updateConfigrationFileForElements(filePath, fileName, xPathElement,
					coreFilePath, coreFileName, xPathCoreService);
			Administration adminstration = dashboard.goTab_Administration();
			ManageLicense manageLicense = adminstration.goLnk_ManageLicense();
			manageLicense.goBtn_Close();
			ManageUsers user = adminstration.goLnk_ManageUsers();
			ManageUserAsserts.assertOpenManageUsersDialog(browser, user);
			user.goBtn_Close();
			ManageHealthChecks healthCheck = adminstration.goLnk_ManageHealthChecks();
			ManageHealthChecksAsserts.assertListOfHealthChecks(browser, healthCheck);
			healthCheck.goBtn_Close();
			break;
		}
	}

	/**
	 * update the Configuration for Sql Elements and Core Services and Re Login to Elements
	 * @param filePath
	 * @param fileName
	 * @param xPathElement
	 * @param coreFilePath
	 * @param coreFileName
	 * @param xPathCoreService 
	 * @throws TransformerFactoryConfigurationError
	 */
	private void updateConfigrationFileForElements(String filePath,
			String fileName, String xPathElement, String coreFilePath,
			String coreFileName, String xPathCoreService) throws TransformerFactoryConfigurationError {
		dashboard.logout();
		File file;
		String subTitleReport;
		file = ExportExplorerViewInstancesListTest.validateDownloadedFile(coreFilePath, coreFileName);
		xpathCoreToReset = xPathCoreService;
		updateConfigFile(file, xPathCoreService, "True");
		corefileName = file;
		subTitleReport = ExportExplorerViewInstancesListTest.getTextAtXPathInXML(file, xPathCoreService);
		Assert.assertEquals(subTitleReport, "True", "Enable Security Option in Not Set To Default Mode");
		file = ExportExplorerViewInstancesListTest.validateDownloadedFile(filePath,fileName);
		xpathElementToReset = xPathElement;
		updateConfigFile(file, xPathElement, "True");
		elementFileName = file;
		subTitleReport = ExportExplorerViewInstancesListTest.getTextAtXPathInXML(file, xPathElement);
		Assert.assertEquals(subTitleReport, "True", "Enable Security Option in Not Set To Default Mode");
		//Restart the Services
		ServiceHelperClass.stopServices("ReservaCanchas Service");
		ServiceHelperClass.stopServices("SQLCore Service");
		ServiceHelperClass.startServices("ReservaCanchas Service");
		//Currently the following defect is blocking this test case DE41876
		SuitesHelper.basicLogin(browser);
	}

	/**
	 * update the Enable Security value to True
	 * @param file
	 * @throws TransformerFactoryConfigurationError
	 */
	private void updateConfigFile(File file, String xPathofElement ,String valueToSet)
			throws TransformerFactoryConfigurationError {
		try {
			Document doc = ExportExplorerViewInstancesListTest.loadXMLReport(file);
			XPath xpath = XPathFactory.newInstance().newXPath();
			NodeList node = (NodeList) (xpath.compile(xPathofElement).evaluate(doc, XPathConstants.NODESET));
			for (int idx = 0; idx < node.getLength(); idx++) {
				node.item(idx).setTextContent(valueToSet);
			}
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			StreamResult fileStream = new StreamResult(file);
			transformer.transform(new DOMSource(doc), fileStream);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}