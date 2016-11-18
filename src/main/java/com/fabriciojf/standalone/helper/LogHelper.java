package com.fabriciojf.standalone.helper;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Classe para manipulacao de arquivos de texto com tamanhos muito conteudo
 * 
 * <pre>
 * TextFileOut buffer = new TextFileOut(&quot;/opt/texto.conf&quot;);
 * buffer.append(&quot;Novo texto&quot;).quebrarLinha();
 * buffer.salvar();
 * </pre>
 * 
 * @author Fabricio S Costa fabriciojf@gmail.com
 * @version 1.0
 * @since 25/03/2013
 */
public class LogHelper {

	private FileWriter fw;
	private BufferedWriter bw;

	/**
	 * Construtor recebendo um path de arquivo de texto <br />
	 * <code>TextFileOut("/opt/texto.conf");</code>
	 */
	public LogHelper(String arquivo) {
		prepararClasse(new File(arquivo));
	}

	/**
	 * Construtor recebendo um arquivo do tipo File de texto <br />
	 * <code>TextFileOut(new File("/opt/texto.conf"));</code>
	 */
	public LogHelper(File arquivo) {
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
	public LogHelper append(String texto) {
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
	public LogHelper quebrarLinha() {
		try {
			bw.write("\n");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return this;
	}
	
	/**
	 * 
	 */
	public void fechar() {
		try {
			bw.flush();
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
		
	/**
	 * Salva os dados do arquivo no HD
	 */
	public void salvar() {
		try {
			bw.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
