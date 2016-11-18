package com.fabriciojf.standalone.helper;

import java.io.BufferedReader;
import java.io.FileReader;
import java.text.Normalizer;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import javax.swing.text.MaskFormatter;

/**
 * Classe para tratamentos de string
 *
 * @author Fabricio S Costa fabriciojf@gmail.com
 * @since 18/10/2012
 * @version 1.0
 */
public class StringHelper {
	
	private String texto;
	
	/**
	 * Construtor vazio
	 */ 
	public StringHelper() {}
	
	/**
	 * Construtor recebendo a string a ser tratada
	 */ 
	public StringHelper(String texto) {
		this.texto = texto;
	}
	
	/**
	 * Construtor recebendo um byte array 
	 */ 
	public StringHelper(byte[] byteArray) {
		this.texto = new String(byteArray);
	}
	
	/**
	 * Verifica se um valor e numerico
	 * @param value
	 * @return
	 */
	public boolean isNumeric() {
		try {
			Integer.parseInt(texto);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * Verifica se um valor e numerico
	 * @param value
	 * @return
	 */
	public int toInt() {
		try {
			return Integer.parseInt(texto);
		} catch (Exception e) {
			return -1;
		}
	}
	
	/**
	 * Verifica se um valor e numerico
	 * @param value
	 * @return
	 */
	public String toIntStr() {
		try {
			return String.valueOf(Integer.parseInt(texto));
		} catch (Exception e) {
			return "";
		}
	}
	
	/**
	 * Formata uma string de acordo com a máscara
	 */
	public String formatar(String mascara) {
		MaskFormatter mf;		
		try {
			mf = new MaskFormatter(mascara);
			mf.setValueContainsLiteralCharacters(false);
			return mf.valueToString(texto);
		} catch (ParseException e) {
			e.printStackTrace();
			return "Problemas com a conversao";
		}
	}

	/**
	 * Retorna uma quantidade de caracateres do fim de uma string
	 */
	public String copiarUltimosCaracteres(Integer quantidade) {
		if (texto.length() <= quantidade) {
			return texto;
		}
		return texto.substring(texto.length() - quantidade, texto.length());
	}

	/**
	 * Limpa multiplos espacos em branco de uma string
	 */
	public String limparMultiplosEspacos() {
		return removerEspacosDuplos(texto);
	}
	
	/**
	 * Remove a acentuação dos caracteres da string.
	 *
	 * @param string
	 *            A string possivelmente acentuada.
	 * @return A string com os acentos removidos.
	 */
	public String removerAcentuacao() {
		return Normalizer.normalize(texto, Normalizer.Form.NFD).replaceAll(
				"[^\\p{ASCII}]", "");
	}

	/**
	 * Retira as letras acentuadas e transforma no padrao HTML
	 * @param nome
	 * @return
	 */
	public String toAcentosHtml() {
		return texto.replace("á", "&aacute;").
			replace("é", "&eacute;").replace("í", "&iacute;").
			replace("ó", "&oacute;").replace("ú", "&uacute;").
			replace("â", "&acirc;").replace("ê", "&ecirc;").
			replace("ô", "&ocirc;").replace("û", "&ucirc;").
			replace("ã", "&atilde;").replace("õ", "&otilde;").
			replace("ç", "&ccedil;").replace("Á", "&Aacute;").
			replace("É", "&Eacute;").replace("Í", "&Iacute;").
			replace("Ó", "&Oacute;").replace("Ú", "&Uacute;").
			replace("Â", "&Acirc;").replace("Ê", "&Ecirc;").
			replace("Ô", "&Ocirc;").replace("Û", "&Ucirc;").
			replace("Ã", "&Atilde;").replace("Õ", "&Otilde;").
			replace("Ç", "&Ccedil;").replace("ª", "a").
			replace("À", "&Agrave;").replace("à", "&agrave;").
			replace("ü", "u").replace("Ü", "U").
			replace("º", "o");
	}

	/**
     * Converte frase para camelcase
     * @param frase
     * @return
     */
    public String toCamelCase() {

    	String saidaCamelCase = "";
    	String[] temp = texto.toLowerCase().split(" ");
    	for (String termo : temp) {

    		/*
    		 * Mantem os termos do matches em caixa baixa para melhor
    		 * compreensão do texto
    		 */
    		if (!termo.trim().matches("|de|e|a|o|da|do|para|por|" +
    				"á|é|ó|à|")) {

	    		saidaCamelCase += termo.substring(0, 1).toUpperCase();

	    		if (termo.length() > 1) {
	    			saidaCamelCase +=
	    				termo.substring(1, termo.length());
	    		}
	    		saidaCamelCase += " ";
    		} else {
    			saidaCamelCase += termo + " ";
    		}
    	}
    	return saidaCamelCase;
    }
	
	/**
	 * Apoio ao limparMultiplosEspacos()
	 */ 
	private String removerEspacosDuplos(String termo) {
		if (termo.contains("  ")) {
			return removerEspacosDuplos(termo.replace("  ", " "));
		}
		return termo;
	}
	
	/**
	 * Converte um byte[] em ArrayList<String>
	 */
	public List<String> toList() {
		
		ArrayList<String> list = new ArrayList<String>();
		StringTokenizer tokens = new StringTokenizer(texto, "\n");

		while (tokens.hasMoreTokens()) {
			list.add((String) tokens.nextElement());
		}
		return list;
	}

	/**
	 * Retorna a {@link String} contida no texto
	 */ 
	public String toString() {
		return texto;
	}
	
	/**
	 * Adiciona quantidade de vezes do texto deQue no inicio da String da classe
	 * ex: new StringTools("la").encheNoInicio("s", 3) = "sssla";
	 */
	public String encherNoInicio(String deQue, int quantidade) {
		String temp = texto;
		for (int i = 0; i < quantidade; i++) {
			temp = deQue.concat(temp); 
		}
		return temp;
	}
	
	/**
	 * Adiciona quantidade de vezes do texto deQue no fim da String da classe
	 * ex: new StringTools("la").encheNoFim("s", 3) = "lasss";
	 */
	public String encherNoFim(String deQue, int quantidade) {
		String temp = texto;
		for (int i = 0; i < quantidade; i++) {
			temp += deQue; 
		}
		return temp;
	}
	
	/**
	 * Clona a String contida na classe quantidade vezes
	 * ex: new StringTools("la").duplicar(5) = "lalalalala";
	 */
	public String clonar(int quantidade) {
		String temp = "";
		for (int i = 0; i < quantidade; i++) {
			temp += texto; 
		}
		return temp;
	}
	
	/**
	 * Fixa o tamanho do texto em x
	 */
	public String fixarTamanho(int tamanho) {		
		if (texto.length() <= tamanho) {
			return encherNoFim(" ", tamanho - texto.length());
		}
		return texto.substring(0, tamanho);
	}
	
	/**
	 * Getter and Setter
	 */	
	public void setString(String texto) {
		this.texto = texto;
	}	
	
	public void setString(byte[] byteArray) {
		this.texto = new String(byteArray);
	}	
	
	/**
	 * Verifica se um valor e numerico
	 * @param value
	 * @return
	 */
	public static boolean isNumeric(String value) {
		try {
			Integer.parseInt(value);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * Formata uma string de acordo com a máscara
	 */
	public static String formatString(String string, String mask) {
		MaskFormatter mf;		
		try {
			mf = new MaskFormatter(mask);
			mf.setValueContainsLiteralCharacters(false);
			return mf.valueToString(string.replace("s", ""));
		} catch (ParseException e) {
			e.printStackTrace();
			return "Problemas com a conversao";
		}
	}

	/**
	 * Retorna uma quantidade de caracateres do fim de uma string
	 */
	public static String copyLast(String texto, Integer quantidade) {
		if (texto.length() <= quantidade) {
			return texto;
		}
		return texto.substring(texto.length() - quantidade, texto.length());
	}

	/**
	 * Limpa multiplos espacos em branco de uma string
	 */
	public static String limparMultiplosEspacos(String texto) {
		if (texto.contains("  ")) {
			return limparMultiplosEspacos(texto.replace("  ", " "));
		}
		return texto;
	}

	/**
	 * Converte int em String com 2 casas
	 */
	public static String strzero(Integer value) {
		if(value < 10)
			return "0" + value.toString();
		return value.toString();
	}

	/**
	 * Formata um inteiro numa String de tempo
	 */
	public static String int2Time(Integer value)	{

		int ss = value % 60;
		value /= 60;
		int min = value % 60;
		value /= 60;
		int hh = value % 24;

		return strzero(hh) + ":" + strzero(min) + ":" + strzero(ss);
	}

	/**
	 * Formata um inteiro numa String de tempo
	 */
	public static String int2TimeLong(Long valueLong)	{
		return (int2Time(Integer.parseInt(valueLong.toString())));
	}

	/**
	 * Abre um arquivo grande e retorna o stringbuilder desse arquivo
	 * ex:
	 * StringBuilder sb = abrirArquivoGrande("c:/texte.xml");<br />
	 * String conteudo = sb.toString();
	 */ 
	public static StringBuilder abrirArquivoGrande(String pathFile) {
		StringBuilder sb = new StringBuilder();
		String linha = "";		

		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(pathFile));
			while ((linha = br.readLine()) != null) {
				sb.append(linha);
			}
		} catch (Exception e) {
			return null;
		} finally {
		    if (br != null) {
		        try {
		            br.close();
		        } catch (Exception ex) {
		            // nada a fazer
		        }
		    }
		}
		return sb;
	}
	
	/**
	 * Retorna uma string somente com numeros numeros
	 */ 
	public static String getSomenteNumeros(String texto) {		
		if(texto == null)  
	         return "";	    
		return texto.replaceAll("[^0123456789]", "");		
	}
}
