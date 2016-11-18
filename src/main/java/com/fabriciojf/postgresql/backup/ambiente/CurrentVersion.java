package com.fabriciojf.postgresql.backup.ambiente;

import java.util.ArrayList;
import java.util.List;

/**
 * Retorna a versao da aplicacao
 * 
 * @author Fabricio S Costa fabriciojf@gmail.com
 * @version 1.0
 * @since 25/03/2013
 */
public class CurrentVersion {

	private static String app = "Jumbo Integracao";	
	private List<String> versoes = new ArrayList<String>();

	/**
	 * Versao atual do sistema
	 */
	private static final String currentVersion = "0.0.7.2027";
	
	/**
	 * Lista as versoes formada por versao + revisao
	 * @return
	 */
	public List<String> getVersoes() {
		
		versoes.add("0.0.7.2027");
		versoes.add("0.0.6.2014");
		versoes.add("0.0.5.1983");
		versoes.add("0.0.4.1981");
		versoes.add("0.0.3.1961");
		versoes.add("0.0.2.1918");
		versoes.add("0.0.1.1904");
		
		return versoes;
	}
	
	/**
	 * Retorna a versao corrente do software
	 * @return
	 */
	public static String get() {
		return currentVersion;
	}
	
	/**
	 * Retorna o nome da aplicacao
	 */
	public static String getApp() {
		return app;
	}
	

}
