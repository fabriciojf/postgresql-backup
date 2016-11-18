/*
 * NavegadorDelegate.java
 * 
 * 05/06/2011
 */
package com.fabriciojf.standalone.helper.navigator;


/**
 * Útilitário para adaptação de implementações de {@link Navegador}.
 * 
 * Este utilitário implementa um corpo padrão para todos os métodos do
 * navegador, transferindo a responsabilidade de execução para um navegador
 * indicado no costrutor. Implementações de {@link Navegador} podem estender
 * este utilitário e implementar apenas os métodos que precisarem realmente de
 * reescrita.
 * 
 * @author Guga
 */
public abstract class NavegadorDelegate extends Navegador {

	protected Navegador delegate;

	public NavegadorDelegate(Navegador delegate) {
		this.delegate = delegate;
	}

	/** {@inheritDoc} */
	@Override
	public boolean temAnterior() {
		return delegate.temAnterior();
	}

	/** {@inheritDoc} */
	@Override
	public boolean temProximo() {
		return delegate.temProximo();
	}

	/** {@inheritDoc} */
	@Override
	public void anterior() {
		delegate.anterior();
	}

	/** {@inheritDoc} */
	@Override
	public void proximo() {
		delegate.proximo();
	}

	/** {@inheritDoc} */
	@Override
	public void marcar() {
		delegate.marcar();
	}

	/** {@inheritDoc} */
	@Override
	public void retornar() {
		delegate.retornar();
	}
	
	/** {@inheritDoc} */
	@Override
	public int getTotalDeLinhas() {
		return delegate.getTotalDeLinhas();
	}

	/** {@inheritDoc} */
	@Override
	public int getNumero() {
		return delegate.getNumero();
	}

	/** {@inheritDoc} */
	@Override
	public void setNumero(int valor) {
		delegate.setNumero(valor);
	}

	@Override
	public String toString() {
		return delegate.toString();
	}

}
