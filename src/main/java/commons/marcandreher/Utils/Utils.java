package commons.marcandreher.Utils;

public class Utils {

    public static boolean isInteger(String str) {
		try {
			Integer.parseInt(str);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

    
}
