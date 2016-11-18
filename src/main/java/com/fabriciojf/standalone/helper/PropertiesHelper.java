package com.fabriciojf.standalone.helper;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Arquivo utilitario de propriedades
 * 
 * @author Fabricio S Costa fabriciojf@gmail.com
 * @since 20/09/2010
 * @version 1.0
 */
public class PropertiesHelper {

	/**
	 * Pega uma propriedade de um arquivo property filePath
	 */
	public static String getProperty(String filePath, String property) {

		try {
			Properties p = new Properties();
			p.load(new FileInputStream(new File(filePath)));
			return p.getProperty(property);
		} catch (IOException ex) {
			ex.printStackTrace();
			return null;
		}
	}

	/**
	 * Seta uma propriedade em um arquivo property filePath
	 */
	public static void setProperty(String filePath, String property,
			String value) throws FileNotFoundException, IOException {

		Properties p = new Properties();
		try {
			p.load(new FileInputStream(new File(filePath)));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		p.setProperty(property, value);
		p.store(new FileOutputStream(new File(filePath)), "Arquivo properties");
	}

}
