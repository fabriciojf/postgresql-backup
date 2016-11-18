package com.fabriciojf.postgresql.backup.tools;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import com.fabriciojf.postgresql.backup.ambiente.Path;
import com.fabriciojf.standalone.helper.DateTimeHelper;
import com.fabriciojf.standalone.helper.HashHelper;

/**
 * Classe para manipulacao do postgresql, como geracao de backup e restore
 * 
 * @author Fabricio S Costa fabriciojf@gmail.com
 * @since 24/02/2015
 * @version 1.0
 */
public class MysqlSQL {

	/**
	 * Gera backup de um banco de dados, recebendo o host, usuario e senha
	 * 
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public static void backup(String host, String user, String pass, 
			String database, String label)
			throws IOException, InterruptedException {

		String outputFile = pathBackup(database, label);
		final List<String> comandos = new ArrayList<String>();
		
		comandos.add("mysqldump");
		comandos.add("-u");
		comandos.add(user);
		comandos.add("-p" + HashHelper.decrypt(pass));
		comandos.add("-h");
		comandos.add(host);
		comandos.add(database);
		comandos.add("--result-file=" + outputFile);
		ProcessBuilder pb = new ProcessBuilder(comandos);

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
		
		if (!new File(outputFile).exists()) {
			System.out.println(String.format(
					"Backup do banco %s NÃO realizado, arquivo %s não foi criado",
					database, outputFile));
		} else {
			System.out.println(String.format(
					"Database %s backupeada com sucesso!!", database));
		}

	}
	
	/**
	 * Validando o path para alocar os arquivos backupeados
	 */
	private static String pathBackup(String database, String label) {

		DateTimeHelper data = new DateTimeHelper();		
		return String.format("%s/%s-%s.%s.mysql.sql",
				Path.backups, label, database, data.getDiaCorrente());	
	}
}
