package com.fabriciojf.standalone.helper;

import java.io.File;
import java.io.IOException;

import com.fabriciojf.standalone.helper.navigator.NavegadorArquivo;
import com.fabriciojf.standalone.helper.navigator.NavegadorTags;
import com.fabriciojf.standalone.helper.navigator.TextFileReader;

/**
 * Classe para manipulacao de arquivos de texto de entrada
 * <pre>
 * TextFileIn linha = new TextFileIn("/opt/texto.conf");
 * linha.reset();
 * while (linha.temProximo()) {
 *     linha.proximo();		
 *     System.out.println(linha);
 * }
 * </pre>
 * 
 * @author Fabricio S Costa fabriciojf@gmail.com
 * @since 17/10/2012
 * @version 1.0
 */
public class TextFileInHelper {

	private TextFileReader reader;
	private NavegadorTags linha;
	private String encoding;
	
	/**
	 * Construtor recebendo um path de arquivo de texto <br /> 
	 * <code>TextFileIn("/opt/texto.conf");</code>
	 */
	public TextFileInHelper(String arquivo) {
		this.encoding = "UTF-8";
		prepararClasse(new File(arquivo));
	}
	
	/**
	 * Construtor recebendo um path de arquivo de texto <br /> 
	 * <code>TextFileIn("/opt/texto.conf");</code>
	 */
	public TextFileInHelper(String arquivo, String encoding) {
		this.encoding = encoding;
		prepararClasse(new File(arquivo));
	}
	
	/**
	 * Construtor recebendo um arquivo do tipo File de texto <br /> 
	 * <code>TextFileIn(new File("/opt/texto.conf"));</code>
	 */
	public TextFileInHelper(File arquivo) {
		this.encoding = "UTF-8";
		prepararClasse(arquivo);
	}
	
	/**
	 * Construtor recebendo um path de arquivo de texto <br /> 
	 * <code>TextFileIn("/opt/texto.conf");</code>
	 */
	public TextFileInHelper(File arquivo, String encoding) {
		this.encoding = encoding;
		prepararClasse(arquivo);
	}
	
	/**
	 * Instancia os objetos de apoio da classe
	 */ 
	private void prepararClasse(File arquivo) {
		try {
			reader = new TextFileReader(arquivo, getEncoding());
			linha = new NavegadorTags(new NavegadorArquivo(reader));
			linha.setNumero(0);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Seta o ponteiro para a primeira linha do arquivo
	 */ 
	public void reset() {
		linha.setNumero(0);		
	}
	
	/**
	 * Retorna se ainda possui alguma linha para leitura
	 */ 
	public boolean temProximo() {
		return linha.temProximo();
	}
	
	/**
	 * Move o ponteiro para a proxima linha
	 */ 
	public TextFileInHelper proximo() {
		linha.proximo();
		return this;
	}
	
	/**
	 * Retorna a linha corrente em formato String
	 */ 
	public String toString() {
		return linha.toString();
	}	
	
	/**
	 * Seta o ponteiro para a linha desejada dentro do arquivo
	 */ 
	public TextFileInHelper vaiPara(int posicao) {
		linha.setNumero(posicao);
		return this;
	}
	
	/**
	 * Fecha o reader para libera-lo no sistema
	 */
	public void close() {
		try {
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Define o encoding do arquivo 
	 * @return
	 */
	public String getEncoding() {
		return encoding;
	}

	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}
	
}
