package ReservaCanchas.Runner;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Enumeration;
import java.util.InvalidPropertiesFormatException;
import java.util.Properties;

public class CustomConfig {
    
    public final static String configFile = "../properties.xml";
	private final Properties props;

	public CustomConfig(String[] args) throws Exception {
		props = new Properties();
        FileInputStream is = new FileInputStream(configFile);
        //load the xml file into properties format
        props.loadFromXML(is);
        is.close();
		switch(args[0]){
		case "-h":
		case "-?":
			showHelp("Help");
			break;
		case "-s":
			setProperty(args);
			break;
		case "-c":
			listProperties();
			break;
		default:
			showHelp("\n\n>>>>>>>>Invalid Argument:\n\t\t" + args[0]);
			return;
		}
	}

	private void setProperty(String[] args) throws Exception {
		for(int i = 1; i<args.length;i++){
			//verify if all the arguments has been created correctly
			String[] val = customSplit(args[i]);
			if(val.length!=2)
				showHelp("Error in argument:\n\t\t" + args[i]);
			else{
				if (props.containsKey(val[0])){
					props.setProperty(val[0], val[1]);
					FileOutputStream out = new FileOutputStream(configFile);
					props.storeToXML(out, "Modified Values");
					out.close();
				}else{
					showHelp("The Key: \"" + val[0] + "\" doesn't exists");
				}
			}
		}
	}

	private String[] customSplit(String arg) {
		//Searching by double quotas into the argument
		//and splitting the string
		int quotasCounter = 0;
		int equalsCounter = 0;
		String key = "";
		String value = "";
		String extra = "";
		for(int i=0;i<arg.length();i++){
			switch(arg.charAt(i)){
				case '=':
					if (quotasCounter%2==0){
						equalsCounter++;
						break;
					}
				case '\"':
					quotasCounter++;
				default:
					switch(equalsCounter){
					case 0: key += arg.charAt(i);break;
					case 1: value += arg.charAt(i);break;
					default: extra += arg.charAt(i);break;
					}
			}
		}
		key = key.replaceAll("\"", "");
		value = value.replaceAll("\"","");
		//if we have more that one equals sign so the result is sent using three parameters
		return extra.length()>0?new String[]{key, value, extra}:new String[]{key, value};
	}

	private void listProperties() throws InvalidPropertiesFormatException, IOException {
        //get all the current Keys
        Enumeration<Object> keys = props.keys();
        //get all the current values
        Enumeration<Object> values = props.elements();
        for(;keys.hasMoreElements();)
            System.out.println(keys.nextElement() + " -> " + values.nextElement());
	}

	public static void main(String[] args) throws Exception {
		//args = new String[]{"-s","server.url=localhost","\"runner.filter\"=\"10 2 5 8\""};
		if (args.length == 0)
			showHelp("No Parameters");
		else
			new CustomConfig(args);
	}
	/**
	 * Return the Help Information like an exception
	 * @param title Exception Title
	 * @throws Exception
	 */
	private static void showHelp(String title) throws Exception{
		String message ="Syntax:\n"
				+ "        -s <Key>=<Value>[ <Key2>=<Value2>] <>\n"
				+ "        -c return the list of all the current values\n"
				+ "        -h|-? Show this Help\n"
				+ "------------------------------------------------------------------------------------------------------------------------------\n"
				+ "Examples:\n"
				+ "        To get the current keys and their values:\n"
				+ "                java CustomConfig -c\n"
				+ "\n"
				+ "        To modify a key:\n"
				+ "                java CustomConfig \"server.url=10.230.110.60\"\n"
				+ "\n";
		throw new Exception(title + "\n\n" + message);
	}
}
