package ReservaCanchas.Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import ReservaCanchas.Common.Browser;

/**
 * Utility class containing methods to get data from pie and bar charts
 * 
 * @author Vishal
 */
public class ChartsHelper {

	// Javascript functions to inject into browser
	private static String jsToInject = ""
			+ "function getBarLabels(indexOfChart, retryCount) {"
			+ 	"var rootDiv = document.getElementsByClassName('z-d3-barchart')[indexOfChart];"
			+ 	"var labelRoot = rootDiv.getElementsByClassName('y axis')[0];"
			+ 	"var labels = '';"
			+ 	"if(labelRoot) {"
			+ 		"var labelNodes = labelRoot.getElementsByTagName('text');"
			+ 		"for(var i=0;i<labelNodes.length;i++){labels=labels+labelNodes[i].innerHTML+';';}"
			+ 	"}"
			// Handling auto-refresh: If result is empty, call function again after 2 seconds
			+	"retryFunc(indexOfChart, retryCount, labels, arguments.callee);"
			+ 	"return labels;"
			+ "}"
			+ "\n"
			// indexOfChart - Index of bar chart on current page, starting from 1.
			// retryCount - number of tries before timeout
			+ "function getBarUnits(indexOfChart, retryCount) {"
			+ 	"var rootDiv = document.getElementsByClassName('z-d3-barchart')[indexOfChart];"
			+ 	"var unitNodes = rootDiv.getElementsByClassName('sizeLabel');"
			+ 	"var units = '';"
			+ 	"if(unitNodes) {"
			+ 		"for(var i=0;i<unitNodes.length;i++){units=units+unitNodes[i].innerHTML+';';}"
			+ 	"}"
			+	"retryFunc(indexOfChart, retryCount, units, arguments.callee);"
			+ 	"return units;"
			+ "}"
			+ "\n"
			// indexOfChart - Index of pie chart on current page, starting from 1.
			// retryCount - number of tries before timeout
			+ "function getPieUnits(indexOfChart, retryCount) {"
			+ 	"var nodes=document.getElementsByClassName('z-d3-piechart')[indexOfChart].getElementsByClassName('units');"
			+ 	"var text='';"
			+	"for(var i=0;i<nodes.length;i++){text=text+nodes[i].innerHTML+';';}"
			+	"retryFunc(indexOfChart, retryCount, text, arguments.callee);"
			+ 	"return text;"
			+ "}"
			+ "\n"
			// indexOfChart - Index of pie chart on current page, starting from 1.
			// retryCount - number of tries before timeout
			+ "function getPieValues(indexOfChart, retryCount) {"
			+ 	"var nodes=document.getElementsByClassName('z-d3-piechart')[indexOfChart].getElementsByClassName('value');"
			+ 	"var text='';"
			+ 	"for(var i=0;i<nodes.length;i++){text=text+nodes[i].innerHTML+';';}"
			+ 	"retryFunc(indexOfChart, retryCount, text, arguments.callee);"
			+ 	"return text;"
			+ "}"
			+ "\n"
			// Removes ; from specified text and checks if it's empty and continues executing function
			// specified by argument caller, given number of times, repeating after every 2 seconds
			+ "function retryFunc(index, retryCount, text, caller) {"
			+ "var result = text.replace(/;/g, '');"
			+ 	"if (retryCount > 0 && result == '') {"
			+ 		"window.setTimeout(function() {"
			+ 			"caller(index, retryCount - 1);"
			+ 		"}, 2000);"
			+ 	"}"
			+ "}";

	/**
	 * Injects javascript into browser. This JS will be available to every web page in the session 
	 * @param browser
	 */
	public static void setJSFunctionsToBrowser(Browser browser) {
		browser.setBrowserJS(jsToInject);
		browser.refresh();
		browser.waitFor(5000);
	}

	/**
	 * Method to fetch values from bar chart
	 * @param browser
	 * @param chartIndexOnPage Serial number of chart on current page. If it's the only chart on current page, pass 1
	 * @param key x or y(upper or lower case). X to fetch labels, Y to fetch numeric values
	 * @return list of values
	 */
	public static List<String> getXYValuesBarChart(Browser browser, int chartIndexOnPage, String key) {
		boolean setJS = false;

		String functToExecute = "";
		switch(key.toUpperCase()) {
		case "X":
			functToExecute = "getBarLabels(" + (chartIndexOnPage - 1) + ", 5)";
			break;
		case "Y":
			functToExecute = "getBarUnits(" + (chartIndexOnPage - 1) + ", 5)";
			break;
		}

		String result = null;
		try {
			result = browser.fetch(functToExecute);
		} catch(Exception e) {
			setJS = true;
		}
		if(setJS) {
			setJSFunctionsToBrowser(browser);
		}
		List<String> jsResult = new ArrayList<String>();
		// Result of executing JS function will be a string containing semi-colon separated values
		if(result != null)
			jsResult.addAll(Arrays.asList(result.split(";")));
		else
			jsResult.addAll(Arrays.asList(browser
					.fetch(functToExecute).split(";")));
		// Remove all empty values
		jsResult.removeAll(Collections.singleton(""));
		return jsResult;
	}

	/**
	 * Method to fetch values from pie chart
	 * @param browser
	 * @param chartIndexOnPage Serial number of chart on current page. If it's the only chart on current page, pass 1
	 * @param key x or y(upper or lower case). X to fetch labels, Y to fetch numeric values
	 * @return list of values
	 */
	public static List<String> getXYValuesPieChart(Browser browser, int chartIndexOnPage, String key) {
		boolean setJS = false;
		
		String functToExecute = "";
		switch(key.toUpperCase()) {
		case "X":
			functToExecute = "getPieUnits(" + (chartIndexOnPage - 1) + ", 5)";
			break;
		case "Y":
			functToExecute = "getPieValues(" + (chartIndexOnPage - 1) + ", 5)";
			break;
		}

		String result = null;
		try {
			result = browser.fetch(functToExecute);
		} catch(Exception e) {
			setJS = true;
		}
		if(setJS) {
			setJSFunctionsToBrowser(browser);
		}
		List<String> jsResult = new ArrayList<String>();
		// Result of executing JS function will be a string containing semi-colon separated values
		if(result != null)
			jsResult.addAll(Arrays.asList(result.split(";")));
		else
			jsResult.addAll(Arrays.asList(browser
					.fetch(functToExecute).split(";")));
		// Remove all empty values
		jsResult.removeAll(Collections.singleton(""));
		return jsResult;
	}
}
