package reservaCanchas.templates;

import net.sf.sahi.client.Browser;
import net.sf.sahi.client.ElementStub;

/**
 * @date 26/08/2015
 * @author Khronos
 */
public class FeatureTemplate {
	/*
	 * Instance of the SAHI browser object used to identify any object content on that
	 */
	private Browser browser;
	//example
	private ElementStub sample;
	
	public FeatureTemplate(Browser b){
		this.browser = b;
		//Creating the UI objects instances
		//example:
		//create = browser.link("Create New Policy");
		
		//Creating the references to some Messages displayed by the UI
		//example:
		//disableWarning = browser.cell("Disable Policy");
		
	}
	//Add Here the Actions
	
	//Add Here the Getters
}