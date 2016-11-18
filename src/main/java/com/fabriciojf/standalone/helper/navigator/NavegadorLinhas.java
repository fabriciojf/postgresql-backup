/*
 * NavegadorLinhas.java
 * 
 * 05/06/2011
 */
package com.fabriciojf.standalone.helper.navigator;


/**
 * Implementação de {@link Navegador} para navegação em linhas de um vetor.
 * 
 * @author Guga
 */
public class NavegadorLinhas extends Navegador {

	private String[] linhas;
	private int indice = -1;
	private int bookmark = -1;

	public NavegadorLinhas(String[] linhas) {
		this.linhas = linhas;
	}

	/** {@inheritDoc} */
	@Override
	public boolean temAnterior() {
		return indice > 0;
	}

	/** {@inheritDoc} */
	@Override
	public boolean temProximo() {
		return indice < linhas.length - 1;
	}

	/** {@inheritDoc} */
	@Override
	public void anterior() {
		indice--;
	}

	/** {@inheritDoc} */
	@Override
	public void proximo() {
		indice++;
	}

	/** {@inheritDoc} */
	@Override
	public void marcar() {
		bookmark = getNumero();
	}

	/** {@inheritDoc} */
	public void bookmark(int indice) {
		bookmark = indice;
	}

	/** {@inheritDoc} */
	@Override
	public void retornar() {
		setNumero(bookmark);
		bookmark = -1;
	}

	/** {@inheritDoc} */
	public void reset(int indice) {
		bookmark(indice);
		retornar();
	}
	
	/** {@inheritDoc} */
	@Override
	public int getTotalDeLinhas() {
		return linhas.length;
	}

	/** {@inheritDoc} */
	@Override
	public int getNumero() {
		return indice;
	}

	/** {@inheritDoc} */
	@Override
	public void setNumero(int value) {
		this.indice = value;
	}

	/** {@inheritDoc} */
	@Override
	public String toString() {
		return linhas[indice];
	}
}
