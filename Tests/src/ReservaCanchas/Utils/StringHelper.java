package ReservaCanchas.Utils;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringHelper {

	public static String joinStrings(List<String> strings, String separator) {

		String joinedString = "";
		for (String string : strings) {

			joinedString = joinedString + string + separator;
		}

		if (!joinedString.isEmpty()) {

			joinedString = joinedString.substring(0, joinedString.length()
					- separator.length());
		}

		return joinedString;
	}

	/**
	 * Method to return the number from input string.
	 * <br/><b><i>String should have only one number</i></b>
	 * @param text
	 * @return
	 */
	public static int getNumberFromString(String text) {

		int count = 0;
		Pattern pat = Pattern.compile(".*?([0-9]+).*");
		Matcher match = pat.matcher(text);
		if (match.matches()) {
			String val = match.group(1);
			try {
				count = Integer.parseInt(val);
			} catch (Exception ex) { /* ignore */ }
		}
		return count;
	}

 	/**
	 * Returns formatted version string. If any octet of version string is 00,
	 * it will be replaced with 0
	 * @param version
	 * @return
	 */
	public static String formatSQLServerVersion(String version) {
		String formattedVersion = "";
		String[] arr = version.split("\\.");
		for(int i = 0; i < arr.length; i++) {
			arr[i] = String.valueOf(Integer.parseInt(arr[i]));
			if(i < arr.length - 1)
				formattedVersion += arr[i] + ".";
			else
				formattedVersion += arr[i];
		}
		if(arr.length == 3)
			formattedVersion += ".0";
		return formattedVersion;
	}

	/**
	 * Method to get the number from string at specified group
	 * @param text
	 * @param group
	 * @return -1 if not found a match at specified group count
	 */
	public static int getNumberAtGroupFromString(String text, int group) {
		int res = -1;
		if (text != null && !text.trim().isEmpty() && group > 0) {
			Pattern p = Pattern.compile("(\\d+)");
			Matcher m = p.matcher(text);
			int cnt = 1;
			while (m.find()) {
				if (cnt == group) {
					try {
						res = Integer.parseInt(m.group(1));
					} catch (Exception ex) {
						ex.printStackTrace();
					}
					break;
				} else {
					cnt++;
				}
			}
		}
		return res;
	}
}
