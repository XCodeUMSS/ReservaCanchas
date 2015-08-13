package ReservaCanchas.Utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import org.apache.log4j.Logger;

import ReservaCanchas.Asserts.Login.LoginAsserts;
/**
 * Service Helper Class to Stop and Start Services
 * @author Amarendra
 *
 */
/*
 * 8/17/2014 - Walter Ramirez - Modifying Class in order to user "SC" instead of "Net Service"
 */
public class ServiceHelperClass {

	private static Logger logger = Logger.getRootLogger();
	public static final int STATE_UNKNOWN       = -1;  
	public static final int STATE_STOPPED       = 1;  
	public static final int STATE_START_PENDING = 2;  
	public static final int STATE_STOP_PENDING  = 3;  
	public static final int STATE_RUNNING       = 4;
	public static final int STATE_PAUSED        = 7;

	/**
	 * 
	 * @param serviceName
	 */
	public static void stopServices(String serviceName){
		int processResponse = STATE_UNKNOWN;
		int timeout = 30;
		try {
			processResponse = execServiceController("stop", serviceName, "localhost");
			while(timeout > 0 && processResponse != STATE_STOPPED){
				Thread.sleep(1000);
				processResponse = execServiceController("query", serviceName, "localhost");
				timeout--;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}		
		LoginAsserts.assertStopServiceStatus(processResponse);
	}

	public static void startServices(String serviceName){
		int processResponse = STATE_UNKNOWN;
		int timeout = 30;
		try {
			processResponse = execServiceController("start", serviceName, "localhost");
			while(timeout > 0 && processResponse != STATE_RUNNING){
				Thread.sleep(1000);
				processResponse = execServiceController("query", serviceName, "localhost");
				timeout--;
			}
		} catch (Exception e) {
			forceStartServices(serviceName);
			processResponse = STATE_RUNNING;
		}
		LoginAsserts.assertStartServiceStatus(processResponse);
	}

	/**
	 * Used to Start a Service retries the action by 10 times before continue 
	 * @param serviceName
	 */
	public static void forceStartServices(String serviceName){
		int tryCount = 0;
		boolean started = false;
		while(tryCount < 10 && !started){
			try {
				started = execServiceController("query", serviceName, "localhost") == STATE_RUNNING;
				if (!started)
					execServiceController("start", serviceName, "localhost");
				Thread.sleep(1000);
			} catch (Exception e) {
				e.printStackTrace();
			}
			tryCount++;
		}
	}

	public static boolean queryServiceRunning(String serviceName) throws Exception{
		return execServiceController("query", serviceName, "localhost") == STATE_RUNNING;
	}
	private static int execServiceController(String cmd, String serviceName, String serverName)  
			throws Exception  
	{
		String command = "sc \\\\" + serverName + " " + cmd + " \"" + serviceName + "\"";
		final Process proc = Runtime.getRuntime().exec(command);
		BufferedReader br = new BufferedReader(new InputStreamReader(proc.getInputStream()));  
		String line;  
		int state = STATE_UNKNOWN;  

		while ((line = br.readLine()) != null) // searches for state in the child process output  
		{  
			int p;  

			if ((p = line.indexOf(" STATE ")) != -1)  
			{  
				if ((p = line.indexOf(" : ", p)) != -1)  
				{
					state = Integer.parseInt(line.substring(p + 3, p + 4));
					break;
				}
			}  
		}
		proc.waitFor();  
		return state;  
	}

	/**
	 * Method to pause a local service
	 * @param serviceName name of the service to be paused
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public static void pauseService(String serviceName) {

		int processResponse = STATE_UNKNOWN;
		int timeout = 60;
		try {
			processResponse = execServiceController("pause", serviceName, "localhost");
			while(timeout > 0 && processResponse != STATE_PAUSED){
				Thread.sleep(1000);
				processResponse = execServiceController("query", serviceName, "localhost");
				timeout--;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		LoginAsserts.assertPauseServiceStatus(processResponse);
	}
	public static void continueService(String serviceName) {

		int processResponse = STATE_UNKNOWN;
		int timeout = 60;
		try {
			processResponse = execServiceController("continue", serviceName, "localhost");
			while(timeout > 0 && processResponse != STATE_RUNNING){
				Thread.sleep(1000);
				processResponse = execServiceController("query", serviceName, "localhost");
				timeout--;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		LoginAsserts.assertStartServiceStatus(processResponse);
	}

	/**
	 * Method to start the given service on remote machine
	 * @param serverName
	 * @param serviceName
	 * @param username username for machine
	 * @param password password for machine
	 */
	public static void startRemoteService(String serverName, String serviceName, String username, String password) {
		int processResponse = STATE_UNKNOWN;
		int timeout = 60;
		try {
			processResponse = execServiceController("start", serviceName, serverName);
			while(timeout > 0 && processResponse != STATE_RUNNING){
				Thread.sleep(1000);
				processResponse = execServiceController("query", serviceName, serverName);
				timeout--;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		LoginAsserts.assertStartServiceStatus(processResponse);
	}

	/**
	 * Method to start the given service on remote machine
	 * @param serverName
	 * @param serviceName
	 * @param username username for machine
	 * @param password password for machine
	 */
	public static void pauseRemoteService(String serverName, String serviceName, String username, String password) {
		int processResponse = STATE_UNKNOWN;
		int timeout = 60;
		try {
			processResponse = execServiceController("pause", serviceName, serverName);
			while(timeout > 0 && processResponse != STATE_PAUSED){
				Thread.sleep(1000);
				processResponse = execServiceController("query", serviceName, serverName);
				timeout--;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		LoginAsserts.assertPauseServiceStatus(processResponse);
	}
	public static void continueRemoteService(String serverName, String serviceName, String username, String password) {

		int processResponse = STATE_UNKNOWN;
		int timeout = 60;
		try {
			processResponse = execServiceController("continue", serviceName, serverName);
			while(timeout > 0 && processResponse != STATE_RUNNING){
				Thread.sleep(1000);
				processResponse = execServiceController("query", serviceName, serverName);
				timeout--;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		LoginAsserts.assertStartServiceStatus(processResponse);
	}

	/**
	 * Method to stop the given service on remote machine
	 * @param serverName
	 * @param serviceName
	 * @param username username for machine
	 * @param password password for machine
	 */
	public static void stopRemoteService(String serverName, String serviceName, String username, String password) {
		int processResponse = STATE_UNKNOWN;
		int timeout = 60;
		try {
			processResponse = execServiceController("stop", serviceName, serverName);
			while(timeout > 0 && processResponse != STATE_STOPPED){
				Thread.sleep(1000);
				processResponse = execServiceController("query", serviceName, serverName);
				timeout--;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}		
		LoginAsserts.assertStopServiceStatus(processResponse);
	}

	/**
	 * Connects to IPC share of remote machine. Should be called before executing any command on remote machine
	 * @param serverName
	 * @param username
	 * @param password
	 */
	public static void connectForIPCShare(String serverName, String username, String password) {
		try {
			String executeCmd = "cmd /c net use \\\\" + serverName + "\\IPC$ /U:" + username + " " + password;
			logger.info(executeCmd);
			Process runtimeProcess = Runtime.getRuntime().exec(executeCmd);
			int res = runtimeProcess.waitFor();
			// Check if command successful
			LoginAsserts.assertCmdStatus(res, executeCmd);
		} catch (IOException | InterruptedException ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * Disconnects from IPC share of remote machine. Should be called after commands has been executed
	 * @param serverName
	 */
	public static void disconnectIPCShare(String serverName) {
		try {
			String executeCmd = "cmd /c net use /delete \\\\" + serverName + "\\IPC$";
			logger.info(executeCmd);
			Process runtimeProcess = Runtime.getRuntime().exec(executeCmd);
			int res = runtimeProcess.waitFor();
			// Check if command successful
			LoginAsserts.assertCmdStatus(res, executeCmd);
		} catch (IOException | InterruptedException ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * Method to stop remote instances. It will stop SQL server and SQL agent services on remote instances
	 * @param insts list of instance names
	 * @param username username of remote machine on which instance is running
	 * @param password password of remote machine on which instance is running
	 */
	public static void stopRemoteInstances(List<String> insts, String username, String password) {
		for (String inst : insts) {
			String name = inst.replace("\\", ":");
			String[] arr = name.split(":");
			if (arr.length > 1) {
				try {
					// More reliable to open a network connection to remote machine before executing any command on it
					connectForIPCShare(arr[0], username, password);
					Thread.sleep(5000);
					ServiceHelperClass.stopRemoteService(arr[0], "SQLAgent$" + arr[1], username, password);
					Thread.sleep(10000);
					ServiceHelperClass.stopRemoteService(arr[0], "MSSQL$" + arr[1], username, password);
					Thread.sleep(10000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				} finally {
					disconnectIPCShare(arr[0]);
				}
			}
		}
	}

	/**
	 * Method to start remote instances. It will start SQL agent service on remote instances.
	 * SQL server service will automatically start as SQL agent service is dependent on it
	 * @param insts list of instance names
	 * @param username username of remote machine on which instance is running
	 * @param password password of remote machine on which instance is running
	 */
	public static void startRemoteInstances(List<String> insts, String username, String password) {
		for (String inst : insts) {
			String name = inst.replace("\\", ":");
			String[] arr = name.split(":");
			if (arr.length > 1) {
				try {
					// More reliable to open a network connection to remote machine before executing any command on it
					connectForIPCShare(arr[0], username, password);
					Thread.sleep(5000);
					// Starting SQLAgent service will also restart MSSQL service
					ServiceHelperClass.startRemoteService(arr[0], "SQLAgent$" + arr[1], username, password);
					Thread.sleep(10000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				} finally {
					disconnectIPCShare(arr[0]);
				}
			}
		}
	}
}
