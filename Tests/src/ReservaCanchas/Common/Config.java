package ReservaCanchas.common;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

import net.sf.sahi.config.Configuration;

import ReservaCanchas.common.Server.Server;
import ReservaCanchas.Common.Browser;
/**
 * @date 8/26/2013
 * @author Walter Ramirez
 */
public class Config{
    
    public final static String configFile = "../properties.xml";
	/*
	 * Initializing a logger instance using the root logger
	 */
	private final static Logger logger = Logger.getRootLogger();

    public static String getFilter() {
        return init().getProperty("runner.filter");
    }

    public static String getAppUser() {
        return init().getProperty("user.name");
    }

    public static String getAppPassword() {
        return init().getProperty("user.pass");
    }

    public static String getSSHUser() {
        return init().getProperty("sshuser.name");
    }

    public static String getSSHPassword() {
        return init().getProperty("sshuser.pass");
    }

    public static String getSSHHost() {
        return init().getProperty("sshserver.host");
    }

    public static String getSSHPort() {
        return init().getProperty("sshserver.port");
    }

    public static String getBrowserName() {
        return init().getProperty("browser.name");
    }

    public static Server getServer() {
        String serverUrl = init().getProperty("server.url");
        String serverPort = init().getProperty("server.port");
        return new Server(serverUrl, serverPort);
    }
    
    public static Browser getBrowser() {
        String browserName = init().getProperty("browser.name");
        String browserHost = init().getProperty("browser.host");
        String browserPort = init().getProperty("browser.port");
        Browser browser = new Browser(browserName, browserHost, Integer.parseInt(browserPort));
        Functions.closeBrowserInstance(browser, browserName);
        browser.open();
        browser.navigateTo(getServer().toString());
        return browser;
    }
    
    public static Browser getBrowser(String browserName ) {
        String browserHost = init().getProperty("browser.host");
        String browserPort = init().getProperty("browser.port");
        Browser browser = new Browser(browserName, browserHost, Integer.parseInt(browserPort));
        Functions.closeBrowserInstance(browser, browserName);
        browser.open();
        browser.navigateTo(getServer().toString());
        return browser;
    }
    
    private static Properties init(){
        Properties props = new Properties();
        
        InputStream is = null;
        try {
            is = new FileInputStream(configFile);
        } catch (FileNotFoundException ex) {
            logger.error(ex);
        }
        try {
            //load the xml file into properties format
            props.loadFromXML(is);
        } catch (IOException ex) {
            logger.error(ex);
        }
        try{
        	String basePath = props.getProperty("sahi.basePath");
        	String userDataDir = props.getProperty("sahi.userDataDir");
        	Configuration.initJava(basePath, userDataDir);
        }catch(Exception ex){
        	System.out.println("NON SAHI project detected, please fix property files if you are working on a Sahi Project");
        }
        return props;
    }
}