package com.thehen101.csgoexternal.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class FileUtil {
	
	/**
	 * Converts a File into a multi-line String using a BufferedReader.
	 * @param file The file to be read.
	 * @return The contents of the file as a multi-line String.
	 */
	public static final String fileToString(File file) {
		try {
			BufferedReader br = new BufferedReader(new FileReader(file));
			String readLine, lines = "";
			while ((readLine = br.readLine()) != null) {
				lines += readLine + System.getProperty("line.separator");
			}
			br.close();
			return lines;
		} catch (IOException ioe) {
			ioe.printStackTrace();
			return ioe.getMessage();
		}
	}
}
