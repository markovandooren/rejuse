package be.kuleuven.cs.distrinet.rejuse.string;

import java.util.ArrayList;
import java.util.List;

public class Strings {

	public static List<String> splitUnescapedPipe(String string) {
		return splitUnescaped(string, '|');
	}
	
	public static List<String> splitUnescaped(String string, char splitter) {
		List<String> result = new ArrayList<String>();
		int length = string.length();
		boolean escaped = false;
		int lastIndex = 0;
		for(int i = 0; i < length; i++) {
			char c = string.charAt(i);
			if((! escaped) && c == '\\') {
			  escaped = true;
			} else if (escaped) {
				escaped = false;
			} else if (c == splitter) {
				result.add(string.substring(lastIndex,i));
				lastIndex = i+1;
			}
		}
		if(lastIndex < length) {
			result.add(string.substring(lastIndex,length));
		}
		return result;
	}

}
