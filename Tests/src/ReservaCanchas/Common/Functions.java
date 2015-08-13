/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ReservaCanchas.common;


import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;

import javax.imageio.ImageIO;

import org.apache.log4j.Logger;

import ReservaCanchas.Common.Browser;
import java.awt.AWTException;
import java.awt.HeadlessException;
import java.io.IOException;

/**
 *
 * @author Administrator
 */
public class Functions {
	/*
	 * Initializing a logger instance using the root logger
	 */
	private final static Logger logger = Logger.getRootLogger();

    /**
     * Convert a String Array of Numbers to an array of hours in the day
     * @param args Hours
     * @return 
     */
    public static String[] convertToHours(String[] args){
        String[] res = new String[args.length];
        for (int i = 0; i<args.length; i++){
            int value = Integer.parseInt(args[i]);
            if (value == 0 || value >= 24)
                res[i] = "12AM";
            else if (value == 12)
                res[i] = "12PM";
            else if (value<12 && value>0)
                res[i] = value + "AM";
            else
                res[i] = (value-12) + "PM";
        }
        return res;
    }
    /**
     * Convert a String Array of Strings to an array of Standard strings format with the first letter Uppercase
     * @param args Strings to be converted
     * @return 
     */
    public static String[] standardize(String[] args){
        String[] res = new String[args.length];
        for (int i = 0; i<args.length; i++){
            String value = args[i].substring(0, 1).toUpperCase() + args[i].substring(1).toLowerCase();
            res[i] = value;
        }
        return res;
    }
    /**
     * Convert a String Hours from the format HH:MM to an array with 2 values {Hour,Minute}
     * @param time The hours in format HH:MM
     * @return 
     */
    public static String[] convertToTime(String time){
        String[] res = time.split(":");
        res[0] = String.valueOf(Integer.valueOf(res[0]));
        res[1] = String.valueOf(Integer.valueOf(res[1]));
        return res;
    }
    /**
     * Search by a specific value in an Array
     * @param value
     * @param allowedValues
     * @return
     */
	public static boolean arraySearch(String value, String[] allowedValues) {
		for(String allowValue : allowedValues)
			if(allowValue.equals(value))
				return true;
		return false;
	}
    /**
     * Search by an array of values in other Array
     * @param values
     * @param allowedValues
     * @return
     */
	public static boolean arraySearch(String[] values, String[] allowedValues) {
		for(String value : values)
			if(!arraySearch(value, allowedValues))
				return false;
		return true;
	}
	/**
	 * Close all the browser instances related to the current Sahi browser
	 * @param browser 
	 * @param browserName name of the browser than we will use to close their instances
	 */
	/*
	 * Adding support for PSList and PSKill instead of tasklist and taskkill
	 */
	public static void closeBrowserInstance(Browser browser, String browserName){
		logger.debug("First iteration to close: " + browserName);
		closeBrowserInstance(browserName);
		//Waiting 5 seconds until the process is successfully closed
		browser.waitFor(5000);
		logger.debug("Second iteration to close: " + browserName);
		closeBrowserInstance(browserName);
	}
	private static void closeBrowserInstance(String browserName){
		//Searching by the PSTools Folder on the default location ("C:\PSTools")
		File psToolsFolder = new File("C:\\PSTools");
		boolean psToolsExists = psToolsFolder.exists();
		String searchProcessPre;
		String searchProcessPost = "";
		String osProcessCallPre;
		String osProcessCallPost = "";
		String searchProcessCommand;
		String killProcessCommand = "";
		if(System.getProperty("os.name").toLowerCase().contains("windows")) {
			if(psToolsExists){
				searchProcessPre = "C:\\PSTools\\PSList.exe /AcceptEula ";
				osProcessCallPre = "C:\\PSTools\\PSKill.exe /AcceptEula -t ";
			}else{
				searchProcessPre = "tasklist /fi \"IMAGENAME eq ";
				osProcessCallPre = "cmd /c taskkill /F /T /IM ";
				searchProcessPost = ".exe\"";
				osProcessCallPost = ".exe";
			}
		}else{
			searchProcessPre = "ps -e | grep ";
			osProcessCallPre = "killall -9 ";
		}
		try{
			Process p;
			switch(browserName){
			case "firefox":
			case "firefox4":
				searchProcessCommand = searchProcessPre + "firefox" + searchProcessPost;
				break;
			case "ie":
				searchProcessCommand = searchProcessPre + "iexplore" + searchProcessPost;
				break;
			case "chrome":
				searchProcessCommand = searchProcessPre + "chrome" + searchProcessPost;
				break;
			case "safari":
				searchProcessCommand = searchProcessPre + "safari" + searchProcessPost;
				break;
			case "opera":
				searchProcessCommand = searchProcessPre + "opera" + searchProcessPost;
				break;
			default:
				logger.warn("Unknown Browser Name:\"" + browserName + "\"");
				searchProcessCommand = "error";
			}
			logger.debug("Java_Common - Search process command: " + searchProcessCommand);
			p = Runtime.getRuntime().exec(searchProcessCommand);
			p.waitFor();
			BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
			String response = "";
			String currentLine = in.readLine();
			while (currentLine != null) {
				currentLine = currentLine.trim();
				if(!"".equals(currentLine)) response = currentLine;
				currentLine = in.readLine();
			}
			logger.debug("Java_Common - List process response: " + response);
			if(!response.isEmpty() && !response.contains("No tasks are running which match the specified criteria")){
				switch(browserName){
					case "firefox":
					case "firefox4":
						killProcessCommand = osProcessCallPre + "firefox" + osProcessCallPost;
						break;
					case "ie":
						killProcessCommand = osProcessCallPre + "iexplore" + osProcessCallPost;
						break;
					case "chrome":
						killProcessCommand = osProcessCallPre + "chrome" + osProcessCallPost;
						break;
					case "safari":
						killProcessCommand = osProcessCallPre + "safari" + osProcessCallPost;
						break;
					case "opera":
						killProcessCommand = osProcessCallPre + "opera" + osProcessCallPost;
						break;
					default: logger.warn("Unknown Browser Name:\"" + browserName + "\"");
				}
				logger.debug("Java_Common - Kill process command: " + killProcessCommand);
				p = Runtime.getRuntime().exec(killProcessCommand);
				logger.debug("Java_Common - Exit code for the close browser process: " + p.waitFor());
			}
		}catch(IOException | InterruptedException ex){
			logger.error(ex);
		}
	}
	/**
	 * Method used to take a screenshot of the current screen
	 * @param screenshotName name to be used in the current screen screen shot
	 */
	public static void takeScreenshot(String screenshotName){
		try {
			String outputFormat = "bmp";
			Rectangle screenRect = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
			BufferedImage capture = new Robot().createScreenCapture(screenRect);
			ImageIO.write(capture, outputFormat, new File(screenshotName + "." + outputFormat));
		} catch (HeadlessException | AWTException | IOException e) {
			logger.error("Error taking the screenshot for the current script.", e.getCause());
		}
	}
}
