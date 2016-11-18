package com.fabriciojf.postgresql.backup.ambiente;

import java.io.File;
import java.io.IOException;

import com.fabriciojf.postgresql.backup.helper.SettingsHelper;

/**
 * Retorna todos os paths utilizados pelo sistema
 * 
 * @author Fabricio S Costa fabriciojf@gmail.com
 * @version 1.0
 * @since 25/04/2013
 */
public class Path {
	
	/**
	 * Variaveis estáticas
	 */
	public static String application;
	public static String resources;
	public static String downloads;
	public static String logs;
	public static String temp;
	public static String backups;
	public static String conf;

	/**
	 * Iniciando as variaveis estaticas
	 */
	static {
		application = getPathApp();
		resources = getPathResources();
		backups = getPathBackups();
		downloads = getPathDownload(false);
		logs = getPathLog(false);
		temp = getPathTemp(false);
		conf = getPathConf(true);
	}
	
	/**
	 * Retorna o path da aplicacao
	 * 
	 * @return
	 */
	private static String getPathApp() {
		try {
			return new File(".").getCanonicalPath();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * Retorna o path da pasta de backups
	 * 
	 * @return
	 */
	private static String getPathBackups() {
		
		if (!SettingsHelper.pathBackups.equals(""))
			return makePath(SettingsHelper.pathBackups);
		return null;	
	}
	
	/**
	 * Retorna o path da pasta resources
	 * 
	 * @return
	 */
	private static String getPathResources() {
		return getPathApp() + "/META-INF/";
	}

	/**
	 * Retorna o path da pasta downloads
	 * boolean create = true cria o path, = false nao faz nada
	 * @return
	 */
	private static String getPathDownload(boolean create) {
		if (create) {
			return makePath(getPathApp() + "/downloads/");
		}
		return null;
	}

	/**
	 * Retorna o path da pasta temp
	 * boolean create = true cria o path, = false nao faz nada
	 * @return
	 */
	private static String getPathTemp(boolean create) {
		if (create) {
			return makePath(getPathApp() + "/temp/");
		}
		return null;
	}

	/**
	 * Retorna o path da pasta de saida da app onde os dados ficarao disponiveis
	 * para o usuario
	 * boolean create = true cria o path, = false nao faz nada
	 * @return
	 */
	private static String getPathLog(boolean create) {
		if (create) {
			if (System.getProperty("os.name").toLowerCase().contains("windows")) {
				return makePath("c:/local/log/");
			}
			return makePath("/var/log/jumbo/");
		}
		return null;
	}

	/**
	 * Retorna o path da pasta de etc + nome_app
	 * boolean create = true cria o path, = false nao faz nada
	 * @return
	 */
	public static String getPathConf(boolean create) {
		if (create) {
			return makePath("/etc/database_backup");
		}
		return "/etc/database_backup";
	}
	
	/**
	 * Trata o path para criar a pasta caso ela nao exista
	 * 
	 * @return
	 */
	private static String makePath(String path) {
		if (!new File(path).exists()) {
			new File(path).mkdirs();
		}
		return path;
	}

}
