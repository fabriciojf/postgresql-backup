package com.fabriciojf.postgresql.backup.helper;

import java.io.File;

import com.fabriciojf.postgresql.backup.ambiente.Path;
import com.fabriciojf.standalone.helper.PropertiesHelper;
import com.fabriciojf.standalone.helper.TextFileOutHelper;

/**
 * Classe de apoio para o arquivo settings
 * @author Fabricio S Costa fabriciojf@gmail.com
 * @since 24/02/2015
 * @version 1.0
 */
public class SettingsHelper {
	
	/**
	 * Propriedades validas, inserir uma referencia aqui sempre que novas 
	 * propriedades forem criadas
	 */
	public static String pathPgDump;
	public static String pathBackups;	
	
	public static String PATH_PGDUMP = "path.pgdump";
	public static String PATH_BACKUPS = "path.backups";
	
	/** pras 
	 * Path do arquivo settings
	 */
	private static String settingsPath = String.format("%s/settings", Path.getPathConf(true));
	
	static {
		validate();
		pathPgDump = getProperties(PATH_PGDUMP);
		pathBackups = getProperties(PATH_BACKUPS);
	}
		
	/**
	 * Retorna o parametro desejado
	 * @return
	 */
	public static String getProperties(String property) {
		return PropertiesHelper.getProperty(settingsPath, property);
	}
	
	/** 
	 * Cria o arquivo register no raiz do projeto e configura a linha de 
	 * ajuda, iniciada por #
	 */
	private static void createRegister() {		
		TextFileOutHelper out = new TextFileOutHelper(settingsPath);
		out.append(PATH_BACKUPS + "=/opt/backups").enter();
		out.append(PATH_PGDUMP + "=/usr/bin/pg_dump").enter();
		out.save();		
	}
	
	/**
	 * Verifica se o arquivo register contendo as confs do banco de dados existe
	 * @return
	 */
	private static void validate() {		
		if (!new File(settingsPath).exists()) {
			createRegister();
			System.out.println(String.format(
					"Arquivo %s foi criado, configure os enderecos para backup!", 
					settingsPath));
			
			/*
			 * faz a validacao do arquivo register tambem
			 */
			new RegisterHelper();
			System.exit(0);
		}		
	}
	
	
}
