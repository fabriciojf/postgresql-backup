package com.fabriciojf.postgresql.backup.tools;

import java.io.File;
import java.io.IOException;

/**
 * Dados sobre o ambiente em que o sistema esta rodando
 * @author Fabricio S Costa fabriciojf@gmail.com
 * @version 1.0
 * @date 08/04/2013
 */
public class AmbienteTools {
	
	private static AmbienteTools instance;
	public static AmbienteTools getInstance() {
		if (instance == null) {
			instance = new AmbienteTools();
		}
		return instance;
	}
	
	/**
	 * Diretorio da aplicacao
	 * @return
	 */
	public String getPathApp() {
		File file = new File(".");
		try {
			return file.getCanonicalPath();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}	

	/**
	 * Diretorio download da aplicacao
	 * @return
	 */
	public String getPathDownload() {
		
		if (!new File(getPathApp() + "/download").exists()) {
			new File(getPathApp() + "/download").mkdir();
		}
		return getPathApp() + "/download";
	}	
	
	public static void main(String[] args) {
		System.out.println(new AmbienteTools().getPathDownload());
	}
	
}
