package com.fabriciojf.standalone.log;

import com.fabriciojf.postgresql.backup.ambiente.Path;
import com.fabriciojf.standalone.helper.DateTimeHelper;
import com.fabriciojf.standalone.helper.LogHelper;
import com.fabriciojf.standalone.helper.MascaraHelper;

/**
 * Gerencia o arquivo de log gerado sempre que o sistema esta em funcionamento
 * @author Fabricio S Costa fabriciojf@gmail.com
 * @version 1.0
 * @since 25/03/2013
 */
public class SystemLog {
	
	private String fileName;
	private LogHelper dialerLog;
	
	/**
	 * Singleton
	 */
	private static SystemLog instance;
	public static SystemLog getInstance() {
		if (instance == null) {
			instance = new SystemLog();
		}
		return instance;
	}

	/**
	 * Ao criar a classe, abre arquivo
	 */
	public SystemLog() {
		fileName = "integracao." + new DateTimeHelper().formatar("yyyyMMdd-hhmmss") + ".out";
		dialerLog = new LogHelper(Path.logs + fileName);
	}
	
	/**
	 * Adiciona uma linha ao arquivo
	 */
	public void append(String linha) {
		dialerLog.append(linha).quebrarLinha();
		save();
	}
	
	/**
	 * Adiciona uma linha ao arquivo
	 */
	public void appendPrompt(String linha) {
		dialerLog.append(getPrompt() + linha).quebrarLinha();
		save();
	}
	
	/**
	 * Salva o arquivo de log
	 */
	public void save() {
		dialerLog.salvar();
	}
		
	/**
	 * Captura a data e hora corrente e joga no prompt 
	 */
	private String getPrompt() {
		return new DateTimeHelper().formatar(MascaraHelper.DD_MM_YYYY_HH_MM_SS) + "> "; 
	}
}
