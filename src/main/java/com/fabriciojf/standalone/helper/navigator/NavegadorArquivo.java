/*
 * NavegadorReader.java
 * 
 * 05/06/2011
 */
package com.fabriciojf.standalone.helper.navigator;

import java.io.IOException;

/**
 * Implementação de {@link Navegador} para navegar em linhas de uma arquivo.
 * 
 * @author Guga
 */
public class NavegadorArquivo extends Navegador {

	private final TextFileReader reader;

	private int indice = -1;
	private int bookmark = -1;
	private String texto;

	public NavegadorArquivo(TextFileReader reader) {
		this.reader = reader;
	}

	/** {@inheritDoc} */
	@Override
	public boolean temAnterior() {
		return indice > 0;
	}

	/** {@inheritDoc} */
	@Override
	public boolean temProximo() {
		try {
			return indice < reader.lineCount() - 1;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	/** {@inheritDoc} */
	@Override
	public void anterior() {
		setNumero(getNumero() - 1);
	}

	/** {@inheritDoc} */
	@Override
	public void proximo() {
		setNumero(getNumero() + 1);
	}

	/** {@inheritDoc} */
	@Override
	public void marcar() {
		bookmark = getNumero();
	}

	/** {@inheritDoc} */
	@Override
	public void retornar() {
		setNumero(bookmark);
		bookmark = -1;
	}

	/** {@inheritDoc} */
	@Override
	public int getTotalDeLinhas() {
		try {
			return reader.lineCount();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	/** {@inheritDoc} */
	@Override
	public int getNumero() {
		return indice;
	}

	/** {@inheritDoc} */
	@Override
	public void setNumero(int value) {
		texto = null;
		this.indice = value;
	}

	/** {@inheritDoc} */
	@Override
	public String toString() {
		try {
			if (texto == null) {
				reader.lineNumber(getNumero());
				texto = reader.readLine();
			}
			return texto;
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
