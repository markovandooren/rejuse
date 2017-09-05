package org.aikodi.rejuse.predicate;

import java.util.regex.Pattern;

public class GlobPredicate extends SafePredicate<String> {

	public GlobPredicate(String glob, char delimiter) {
		_pattern = globToRegex(glob, delimiter);
	}
	
	Pattern _pattern;
	
	/**
	 * Transform a tree glob that supports '**' for crossing level borders. Escape
	 * characters ('\') are not supported, but are instead matched literally.
	 * 
	 * @param glob The glob pattern.
	 * @param delimiter The delimiter that separates levels in the tree structure.
	 * @return
	 */
	public static Pattern globToRegex(String glob, char delimiter) {
		StringBuilder builder = new StringBuilder();
		String nonDelimiter = "[^" + delimiter + "]";
		for(int i =0; i<glob.length();i++) {
			char c = glob.charAt(i);
			if(c == '*') {
				if(glob.charAt(i+1) == '*') {
					i++;
					builder.append(".*");
				} else {
					builder.append(nonDelimiter);
					builder.append('*');
				}
			} else if(c == delimiter) {
				builder.append(delimiter);
			} else if(c == '?') {
				builder.append(nonDelimiter);
			} else if(c == '\\') {
				builder.append("\\\\");
			}	else if(c == '.') {
				builder.append("\\.");
			}else {
				builder.append(c);
			}
		}
		builder.append('$');
		return Pattern.compile(builder.toString());
	}

	@Override
	public boolean eval(String string) {
		return _pattern.matcher(string).find();
	}

}
