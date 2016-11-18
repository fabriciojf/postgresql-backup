package com.fabriciojf.standalone.helper;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Classe para manipulacao de arquivos de texto com tamanhos muito conteudo
 * <pre>
 * TextFileOut buffer = new TextFileOut("/opt/texto.conf");
 * buffer.append("Novo texto").quebrarLinha();
 * buffer.salvar();
 * </pre>
 * 
 * @author Fabricio S Costa fabriciojf@gmail.com
 * @since 17/10/2012
 * @version 1.0
 */
public class TextFileOutHelper {
	
	private FileWriter fw;  
	private BufferedWriter bw;
	
	/**
	 * Construtor recebendo um path de arquivo de texto <br /> 
	 * <code>TextFileOut("/opt/texto.conf");</code>
	 */
	public TextFileOutHelper(String arquivo) {
		prepararClasse(new File(arquivo));
	}
	
	/**
	 * Construtor recebendo um arquivo do tipo File de texto <br /> 
	 * <code>TextFileOut(new File("/opt/texto.conf"));</code>
	 */
	public TextFileOutHelper(File arquivo) {
		prepararClasse(arquivo);  
	}
	
	/**
	 * Instancia os objetos de apoio da classe
	 */  
	private void prepararClasse(File arquivo) {
		try {
			fw = new FileWriter(arquivo);
			bw = new BufferedWriter(fw);
		} catch (IOException e) {
			e.printStackTrace();
		}  
	}
	
	/**
	 * Adiciona um conteudo (String) ao final do arquivo 
	 * <code>append("Novo texto");</code>
	 */ 
	public TextFileOutHelper append(String texto) {
		try {
			bw.write(texto);
		} catch (IOException e) {
			e.printStackTrace();
		}  
		return this;
	}
	
	/**
	 * Adiciona um conteudo (String) ao final do arquivo
	 * <code>append("Novo texto");</code>
	 */ 
	public TextFileOutHelper enter() {
		try {
			bw.write("\n");
		} catch (IOException e) {
			e.printStackTrace();
		}  
		return this;
	}
	  
	/**
	 * Salva os dados do arquivo no HD
	 */ 
	public void save() {
		try {
			bw.flush();
			bw.close();  
		} catch (IOException e) {
			e.printStackTrace();
		}  
	}
}
