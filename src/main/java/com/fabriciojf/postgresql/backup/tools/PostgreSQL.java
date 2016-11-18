package com.fabriciojf.postgresql.backup.tools;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import com.fabriciojf.postgresql.backup.ambiente.Path;
import com.fabriciojf.postgresql.backup.helper.SettingsHelper;
import com.fabriciojf.standalone.helper.DateTimeHelper;
import com.fabriciojf.standalone.helper.HashHelper;

/**
 * Classe para manipulacao do postgresql, como geracao de backup e restore
 * 
 * @author Fabricio S Costa fabriciojf@gmail.com
 * @since 24/02/2015
 * @version 1.0
 */
public class PostgreSQL {

	/**
	 * Gera backup de um banco de dados, recebendo o host, usuario e senha
	 * 
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public static void backup(String host, String user, String pass, 
			String database, String label)
			throws IOException, InterruptedException {

		final List<String> comandos = new ArrayList<String>();

		comandos.add(pathPgDump());
		comandos.add("-h");
		comandos.add(host);
		comandos.add("-U");
		comandos.add(user);
		comandos.add("-F");
		comandos.add("c");
		comandos.add("-b");
		comandos.add("-v");
		comandos.add("-f");
		comandos.add(pathBackup(database, label));
		
		comandos.add(database);
		ProcessBuilder pb = new ProcessBuilder(comandos);

		pb.environment().put("PGPASSWORD", HashHelper.decrypt(pass));

		try {
			final Process process = pb.start();

			final BufferedReader r = new BufferedReader(new InputStreamReader(
					process.getErrorStream()));
			String line = r.readLine();
			while (line != null) {
				System.err.println(line);
				line = r.readLine();
			}
			r.close();

			process.waitFor();
			process.destroy();
			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException ie) {
			ie.printStackTrace();
		}

	}
	
	/**
	 * Gera backup de um banco de dados, recebendo o host, usuario e senha
	 * 
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public static void oldmethod(String host, String user, String pass, 
			String database, String label)
			throws IOException, InterruptedException {

		final List<String> comandos = new ArrayList<String>();

		comandos.add(pathPgDump());
		comandos.add("-i");
		comandos.add("-h");
		comandos.add(host);
		comandos.add("-p");
		comandos.add("5432");
		comandos.add("-U");
		comandos.add(user);
		comandos.add("-F");
		comandos.add("c");
		comandos.add("-b");
		comandos.add("-v");
		comandos.add("-f");
		comandos.add(pathBackup(database, label));
		comandos.add(database);
		ProcessBuilder pb = new ProcessBuilder(comandos);

		pb.environment().put("PGPASSWORD", HashHelper.decrypt(pass));

		try {
			final Process process = pb.start();

			final BufferedReader r = new BufferedReader(new InputStreamReader(
					process.getErrorStream()));
			String line = r.readLine();
			while (line != null) {
				System.err.println(line);
				line = r.readLine();
			}
			r.close();

			process.waitFor();
			process.destroy();
			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException ie) {
			ie.printStackTrace();
		}

	}
	
	/**
	 * Validando o sistema operacional para pegar o path correto
	 */
	private static String pathPgDump() {
		if ((SettingsHelper.pathPgDump != null) &&
				!SettingsHelper.pathPgDump.equals("")) 
			return SettingsHelper.pathPgDump;
		return null;
	}
	
	/**
	 * Validando o path para alocar os arquivos backupeados
	 */
	private static String pathBackup(String database, String label) {

		DateTimeHelper data = new DateTimeHelper();
		return String.format("%s/%s-%s.%s.backup",
					Path.backups, label, 
					database, data.getDiaCorrente());
	}
}
