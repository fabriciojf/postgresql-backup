package com.fabriciojf.postgresql.backup.controller;

import java.io.FileNotFoundException;
import java.io.IOException;

import com.fabriciojf.postgresql.backup.process.BackupProcess;

/**
 * Classe controladora do sistema
 * 
 * @author Fabricio S Costa
 * @since 20/10/2010
 * @version 1.0
 */
public class Controller {
	
	/**
	 * Singleton, utilizado para facilitar o refresh
	 */
	private static Controller instance;
	public static Controller getInstance() {
		if (instance == null) {
			instance = new Controller();
		}
		return instance;
	}
	
	/**
	 * Classe de entrada do Controller, dispara todo o fluxo do sistema
	 * @throws InterruptedException 
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 */
	public void execute() throws IOException, InterruptedException {
		/*
		 * registra os processos a serem executados no start do sistema 
		 */
		new BackupProcess().execute();		
		System.out.println("Processo Finalizado");
	}
	
}
