/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package reservaCanchas.common;

import com.jacob.com.LibraryLoader;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;

import javax.imageio.ImageIO;

import org.apache.log4j.Logger;

import java.awt.AWTException;
import java.awt.HeadlessException;
import java.io.IOException;
import net.sf.sahi.client.Browser;
import net.sf.sahi.client.ElementStub;

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
     * Add a file in order to upload it to the browser
     *
     * @param browser
     * @param file File Element when we need to perform the click in order to
     * show the Open dialog
     * @param filePath path of the file that we need to upload
     * @throws AWTException
     */
    public void setFileUpload(Browser browser, ElementStub file, String filePath) throws AWTException {
        //Loading Jacob and AutoITX3 DLL Libraries
        loadAutoIT();
        //Loading AutoITX Library
        AutoItX x = new AutoItX();

        filePath = System.getProperty("user.dir") + File.separator + filePath;
        if (browser.isFF()) {
            browser.click(file);
            String upload = "[REGEXPTITLE:File Upload]";
            if (x.winWait(upload, "", 10)) {
                x.winActivate(upload);
                x.sleep(3000);
                x.controlFocus(upload, "", "[CLASS:Edit;INSTANCE:1]");
                x.send(filePath);
                x.sleep(3000);
                x.controlClick(upload, "", "[TEXT:&Open]");
                x.sleep(3000);
            } else {
                logger.error("Timeout exceeded to upload the File: " + filePath);
            }
        }
    }

    /**
     * Loads jacob and auto it dlls depending on bit mode of current jre. Loads
     * 32 bit dlls if current jre is 32 bit, loads 64 bit dlls if current jre is
     * 64 bits.
     */
    private void loadAutoIT() {
        try {
            File jacobDLL, autoITXDLL;
			 // Get the bit mode of jre using property "sun.arch.data.model". We might not be using Sun's java.
            // In that case, use "os.arch" to determine if jre is 32 bit or 64 bit
            int jreBitMode = ((System.getProperty("sun.arch.data.model") == "32") || (System.getProperty("os.arch") != null
                    && System.getProperty("os.arch").contains("86"))) ? 32 : 64;
            if (jreBitMode == 64) {
                logger.debug(" >>>Loading x64 libs");
                jacobDLL = new File("lib", "jacob-1.17-x64.dll");
                autoITXDLL = new File("lib", "AutoItX3_x64.dll");
            } else {
                logger.debug(" >>>Loading x86 libs");
                jacobDLL = new File("lib", "jacob-1.17-x86.dll");
                autoITXDLL = new File("lib", "AutoItX3.dll");
            }
            //Registering the JACOB and AutoITX DLL Libraries
            System.setProperty(LibraryLoader.JACOB_DLL_PATH, jacobDLL.getAbsolutePath());
            Process p = Runtime.getRuntime().exec("regsvr32.exe /s " + autoITXDLL.getAbsolutePath());
            p.waitFor();
        } catch (Exception e) {
            logger.error(e.fillInStackTrace());
        }
    }

    /**
     * Close all the browser instances related to the current Sahi browser
     *
     * @param browser
     * @param browserName name of the browser than we will use to close their
     * instances
     */
    /*
     * Adding support for PSList and PSKill instead of tasklist and taskkill
     */
    public static void closeBrowserInstance(Browser browser, String browserName) {
        logger.debug("First iteration to close: " + browserName);
        closeBrowserInstance(browserName);
        //Waiting 5 seconds until the process is successfully closed
        browser.waitFor(5000);
        logger.debug("Second iteration to close: " + browserName);
        closeBrowserInstance(browserName);
    }

    private static void closeBrowserInstance(String browserName) {
        //Searching by the PSTools Folder on the default location ("C:\PSTools")
        File psToolsFolder = new File("C:\\PSTools");
        boolean psToolsExists = psToolsFolder.exists();
        String searchProcessPre;
        String searchProcessPost = "";
        String osProcessCallPre;
        String osProcessCallPost = "";
        String searchProcessCommand;
        String killProcessCommand = "";
        if (System.getProperty("os.name").toLowerCase().contains("windows")) {
            if (psToolsExists) {
                searchProcessPre = "C:\\PSTools\\PSList.exe /AcceptEula ";
                osProcessCallPre = "C:\\PSTools\\PSKill.exe /AcceptEula -t ";
            } else {
                searchProcessPre = "tasklist /fi \"IMAGENAME eq ";
                osProcessCallPre = "cmd /c taskkill /F /T /IM ";
                searchProcessPost = ".exe\"";
                osProcessCallPost = ".exe";
            }
        } else {
            searchProcessPre = "ps -e | grep ";
            osProcessCallPre = "killall -9 ";
        }
        try {
            Process p;
            switch (browserName) {
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
                if (!"".equals(currentLine)) {
                    response = currentLine;
                }
                currentLine = in.readLine();
            }
            logger.debug("Java_Common - List process response: " + response);
            if (!response.isEmpty() && !response.contains("No tasks are running which match the specified criteria")) {
                switch (browserName) {
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
                    default:
                        logger.warn("Unknown Browser Name:\"" + browserName + "\"");
                }
                logger.debug("Java_Common - Kill process command: " + killProcessCommand);
                p = Runtime.getRuntime().exec(killProcessCommand);
                logger.debug("Java_Common - Exit code for the close browser process: " + p.waitFor());
            }
        } catch (IOException | InterruptedException ex) {
            logger.error(ex);
        }
    }

    /**
     * Method used to take a screenshot of the current screen
     *
     * @param screenshotName name to be used in the current screen screen shot
     */
    public static void takeScreenshot(String screenshotName) {
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
