package com.thehen101.csgoexternal.util;

public final class TypeUtil {
	
	/**
	 * Converts a String of bytes into their actual (signed) byte values. 
	 * For example, a string containing the following: 
	 * "5E 33 D2 6A ??" 
	 * is converted into a byte array containing the following: 
	 * [94, 51 -46, 106, 0]. 
	 * 
	 * An integer array is used as Java does not support signed bytes, 
	 * and this method supports wildcards in the form of each byte being 
	 * represented as ??.
	 * @param string The string to be processed.
	 * @return An array of signed bytes in the form of an integer array.
	 */
	public static final int[] stringToByteArray(String string) {
		int spaces = string.length() - string.replace(" ", "").length();
		int[] array = new int[spaces + 1];
		int counter = 0;
		string = sanitiseSignatureString(string);
		while (string.length() != 0) {
			String aByte = string.substring(0, Math.min(3, string.length()));
			string = string.replaceFirst(aByte, "");
			aByte = aByte.substring(0, 2);
			array[counter] = aByte.contains("x") ? 0 : ((byte) Integer.parseInt(aByte, 16));
			counter++;
		}
		return array;
	}

	public static final String sanitiseSignatureString(String sigString) {
		return sigString.replaceAll("\\?\\?", "xx");
	}
}
