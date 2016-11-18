/*
 * RandomAccessFileReader.java
 *
 * 05/06/2011 
 */
package com.fabriciojf.standalone.helper.navigator;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.util.Map;
import java.util.TreeMap;

/**
 * Reader para leitura e navegação por linhas de um arquivo.
 * 
 * @author Guga
 * @see #lineCount()
 * @see #lineNumber()
 * @see #lineNumber(int)
 * @see #readLine()
 */
public class TextFileReader extends Reader {

	private final TextFileInputStream in;
	private final Map<Integer, Long> index;
	private int lineNumber = 0;
	
	/**
	 * Constrói o reader para ler linhas do stream devidamente codificados no
	 * charset indicado.
	 * 
	 * @param in
	 *            O stream para leitura do arquivo.
	 * @param charset
	 *            Codificação para interpretação dos caracteres do arquivo.
	 *            UTF-8, ISO-8859-1, etc. Se {@code null} for inforamdo os bytes
	 *            serão lidos na codificação padrão da JVM.
	 * @throws IOException
	 */
	public TextFileReader(TextFileInputStream in) throws IOException {
		this.in = in;
		this.index = buildIndex();
	}

	/**
	 * Constrói o reader para ler linhas do stream devidamente codificados no
	 * charset indicado.
	 * 
	 * @param file
	 *            Arquivo a ser lido.
	 * @param charset
	 *            Codificação para interpretação dos caracteres do arquivo.
	 *            UTF-8, ISO-8859-1, etc. Se {@code null} for inforamdo os bytes
	 *            serão lidos na codificação padrão da JVM.
	 * @throws IOException
	 */
	public TextFileReader(File file, String charset) throws IOException {
		this(new TextFileInputStream(file, charset));
	}

	/**
	 * Constrói o reader para ler linhas do stream devidamente codificados no
	 * charset indicado.
	 * 
	 * @param file
	 *            Arquivo a ser lido.
	 * @param charset
	 *            Codificação para interpretação dos caracteres do arquivo.
	 *            UTF-8, ISO-8859-1, etc. Se {@code null} for inforamdo os bytes
	 *            serão lidos na codificação padrão da JVM.
	 * @throws IOException
	 */
	public TextFileReader(String file, String charset) throws IOException {
		this(new TextFileInputStream(file, charset));
	}

	/**
	 * Indexa as linhas do arquivo. O mapa obtido contém o número de cada linha
	 * e sua posição inicial em bytes no stream.
	 * 
	 * @return O índice de linhas.
	 * @throws IOException
	 */
	private Map<Integer, Long> buildIndex() throws IOException {
		Map<Integer, Long> map = new TreeMap<Integer, Long>();

		long bookmark = 0L;
		int lineCount = 0;

		do {
			bookmark = in.pointer();
			if (in.readLine() == null) {
				break;
			}
			map.put(lineCount++, bookmark);
		} while (true);
		in.reset();

		return map;
	}

	/**
	 * Quantidade de linhas no arquivo.
	 * 
	 * @return Quantidade de linhas no arquivo.
	 * @throws IOException
	 */
	public int lineCount() throws IOException {
		return index.size();
	}

	/**
	 * Número da linha atual.
	 * 
	 * @return Número da linha atual.
	 * @throws IOException
	 */
	public int lineNumber() throws IOException {
		return lineNumber;
	}

	/**
	 * Aponta o stream para a linha indicada. Os próximos bytes serão lidos a
	 * partir da posição desta linha.
	 * 
	 * @param value
	 *            O número da linha.
	 * @throws IOException
	 */
	public void lineNumber(int value) throws IOException {
		if (value < 0)
			throw new IOException("Line number cannot be negative");
		if (value >= lineCount())
			throw new IOException("Line number exceeds line count");

		in.reset();
		in.skip(index.get(value));
		lineNumber = value;
	}

	/** {@inheritDoc} */
	@Override
	public boolean ready() throws IOException {
		return in.available() > 0;
	}

	/** {@inheritDoc} */
	@Override
	public int read(char[] cbuf, int off, int len) throws IOException {
		throw new RuntimeException("Not implemented yet!");
	}

	/**
	 * Lê a próxima linha do stream. O número da linha corrente (
	 * {@link #lineNumber()}) é incrementado em um.
	 * 
	 * @return O conteúdo da linha corrente.
	 * @throws IOException
	 */
	public String readLine() throws IOException {
		int currentLine = lineNumber();
		String string = in.readLine();
		if (string != null) {
			if (++currentLine < lineCount()) {
				lineNumber(currentLine);
			}
		}
		return string;
	}

	/** {@inheritDoc} */
	@Override
	public long skip(long n) throws IOException {
		return in.skip(n);
	}

	/** {@inheritDoc} */
	@Override
	public void close() throws IOException {
		in.close();
	}

	/** {@inheritDoc} */
	@Override
	public boolean markSupported() {
		return false;
	}

	/** {@inheritDoc} */
	@Override
	public void mark(int readAheadLimit) throws IOException {
		throw new RuntimeException("mark/reset not supported");
	}

	/** {@inheritDoc} */
	@Override
	public void reset() throws IOException {
		throw new RuntimeException("mark/reset not supported");
	}
}
