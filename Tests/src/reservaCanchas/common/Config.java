package reservaCanchas.common;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import net.sf.sahi.client.Browser;

import org.apache.log4j.Logger;

import net.sf.sahi.config.Configuration;

import reservaCanchas.common.server.Server;

/**
 * @date 24/08/2015
 * @author Khrono
 */
public class Config {

    public final static String configFile = "./properties.xml";
    private final static Logger logger = Logger.getRootLogger();
    private final Properties props;
    private static Config myConfig;
    
    public static Properties getConfig(){
        if(myConfig == null)
            myConfig = new Config();
        return myConfig.props;
    }
    private Config(){
        props = new Properties();

        InputStream is = null;
        try {
            is = new FileInputStream(configFile);
        } catch (FileNotFoundException ex) {
            logger.error(ex);
        }
        try {
            props.loadFromXML(is);
        } catch (IOException ex) {
            logger.error(ex);
        }
        try {
            String basePath = props.getProperty("sahi.basePath");
            String userDataDir = props.getProperty("sahi.userDataDir");
            Configuration.initJava(basePath, userDataDir);
        } catch (Exception ex) {
            System.out.println("No se detecto un proyecto SAHI, por favor, "
                    + "corrija el archivo properties si usted esta trabajando "
                    + "en un proyecto SAHI");
        }
    }
    
    public static String getBrowserName() {
        return Config.getConfig().getProperty("browser.name");
    }

    public static Server getServer() {
        String serverUrl = Config.getConfig().getProperty("server.url");
        return new Server(serverUrl);
    }

    public static Browser getBrowser() {
        String browserName = Config.getConfig().getProperty("browser.name");
        String browserHost = Config.getConfig().getProperty("browser.host");
        String browserPort = Config.getConfig().getProperty("browser.port");
        Browser browser = new Browser(browserName, browserHost, Integer.parseInt(browserPort));
        Functions.closeBrowserInstance(browser, browserName);
        browser.open();
        browser.navigateTo(getServer().toString());
        return browser;
    }

    public static Browser getBrowser(String browserName) {
        String browserHost = Config.getConfig().getProperty("browser.host");
        String browserPort = Config.getConfig().getProperty("browser.port");
        Browser browser = new Browser(browserName, browserHost, Integer.parseInt(browserPort));
        Functions.closeBrowserInstance(browser, browserName);
        browser.open();
        browser.navigateTo(getServer().toString());
        return browser;
    }
}
