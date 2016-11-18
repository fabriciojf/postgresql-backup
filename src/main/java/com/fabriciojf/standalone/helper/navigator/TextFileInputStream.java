/*
 * RandomAccessFileInputStream.java
 * 
 * 5 jun 2011
 */
package com.fabriciojf.standalone.helper.navigator;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;

/**
 * Stream para leitura de arquivo. Este utilitário permite a navegação para
 * frente e para trás nos bytes do arquivo.
 * 
 * @author Guga
 */
public class TextFileInputStream extends InputStream {

	private static final String ISO_CHARSET = "ISO-8859-1";
	private final RandomAccessFile in;
	private final String charset;

	private long bookmark = 0L;

	/**
	 * Constrói uma instância para ler texto do arquivo indicado. As linhas são
	 * codificadas segundo o charset indicado, exemplo: UTF-8, ISO-8859-1, etc.
	 * 
	 * @param in
	 *            O arquivo a ser lido.
	 * @param charset
	 *            A codificação do arquivo, exemplo: UTF-8, ISO-8859-1, etc. Se
	 *            {@code null} for informado será assumido a codificação
	 *            {@value #ISO_CHARSET}.
	 * @throws IOException
	 */
	public TextFileInputStream(RandomAccessFile in, String charset)
			throws IOException {
		this.in = in;
		this.charset = charset;
	}

	/**
	 * Constrói uma instância para ler texto do arquivo indicado. As linhas são
	 * codificadas segundo o charset indicado, exemplo: UTF-8, ISO-8859-1, etc.
	 * 
	 * @param file
	 *            O arquivo a ser lido.
	 * @param charset
	 *            A codificação do arquivo, exemplo: UTF-8, ISO-8859-1, etc. Se
	 *            {@code null} for informado será assumido a codificação
	 *            {@value #ISO_CHARSET}.
	 * @throws IOException
	 */
	public TextFileInputStream(File file, String charset) throws IOException {
		this(new RandomAccessFile(file, "r"), charset);
	}

	/**
	 * Constrói uma instância para ler texto do arquivo indicado. As linhas são
	 * codificadas segundo o charset indicado, exemplo: UTF-8, ISO-8859-1, etc.
	 * 
	 * @param file
	 *            O arquivo a ser lido.
	 * @param charset
	 *            A codificação do arquivo, exemplo: UTF-8, ISO-8859-1, etc. Se
	 *            {@code null} for informado será assumido a codificação
	 *            {@value #ISO_CHARSET}.
	 * @throws IOException
	 */
	public TextFileInputStream(String file, String charset) throws IOException {
		this(new File(file), charset);
	}

	/**
	 * Codificação para leitura de linhas. Exemplo ISO-8859-1, UTF-8, etc.
	 * @return
	 */
	public String getCharset() {
		return charset == null ? ISO_CHARSET : charset;
	}

	/**
	 * Quantidade de bytes no arquivo.
	 * 
	 * @return Quantidade de bytes no stream.
	 * @throws IOException
	 */
	public long length() throws IOException {
		return in.length();
	}
	
	/**
	 * Posição de leitura corrente no stream.
	 * 
	 * @return Posição de leitura corrente no stream.
	 * @throws IOException
	 */
	public long pointer() throws IOException {
		return in.getFilePointer();
	}

	/** {@inheritDoc} */
	@Override
	public boolean markSupported() {
		return true;
	}

	/** {@inheritDoc} */
	@Override
	public synchronized void mark(int readlimit) {
		try {
			bookmark = in.getFilePointer();
		} catch (IOException e) {
			throw new RuntimeException("Could not bookmark current position", e);
		}
	}

	/** {@inheritDoc} */
	@Override
	public synchronized void reset() throws IOException {
		in.seek(bookmark);
	}

	/**
	 * Quantidade de bytes disponíveis para leitura no stream.
	 */
	@Override
	public int available() throws IOException {
		long available = in.length() - in.getFilePointer();
		if (available > 0) {
			if (available > Integer.MAX_VALUE) {
				return Integer.MAX_VALUE;
			}
			return (int) available;
		}
		return 0;
	}

	/**
	 * Lê um byte do stream.
	 * 
	 * @return O byte lido ou -1 caso o stream tenha atingido o fim de arquivo.
	 * @see #readLine()
	 */
	@Override
	public int read() throws IOException {
		return in.read();
	}

	/**
	 * Lê uma linha inteira.
	 * 
	 * @return Lê uma linha inteira.
	 * @throws IOException
	 */
	public String readLine() throws IOException {
		String content = in.readLine();
		if (content != null && !getCharset().equals(ISO_CHARSET)) {
			/* 
			 * O RandomAccesFile lê a linha do arquivo esperando o charset
			 * ISO-8859-1. Caso esta não seja a codificação do arquivo nós
			 * temos que desfazer esta bagunça na mão, obtendo os bytes do jeito
			 * que ele obteve, isto é, em ISO-8859-1 e depois gerando uma nova
			 * string desta vez informando o java ao codificação correta.
			 */
			content = new String(content.getBytes(ISO_CHARSET), getCharset());
		}
		return content;
	}

	/** {@inheritDoc} */
	@Override
	public long skip(long n) throws IOException {
		in.seek(in.getFilePointer() + n);
		return n;
	}

	/**
	 * Fecha o stream.
	 */
	@Override
	public void close() throws IOException {
		in.close();
	}
}
