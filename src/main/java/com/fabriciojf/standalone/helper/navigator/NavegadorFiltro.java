/*
 * NavegadorFiltro.java
 * 
 * 05/06/2011
 */
package com.fabriciojf.standalone.helper.navigator;

/**
 * Implementação de {@link Navegador} para adicionar a funcionalidade de filtro
 * das linhas listadas pelo navegador. Esta implementação filtra as linhas mas
 * repassa para outra implementação de {@link Navegador} a responsabilidade da
 * navegação.
 * 
 * O usuário deste utilitário deve informar uma instância de
 * {@link NavegadorFiltro.Filtro} contendo a regra para aceite ou não das linhas
 * navegadas. O filtro pode ser modificado a qualquer momento com o uso do
 * método {@link #setFiltro(Filtro)}. Se informado um filtro nulo todas as
 * linhas serão aceitas por padrão.
 * 
 * @author Guga
 * @see NavegadorFiltro.Filtro
 */
public class NavegadorFiltro extends NavegadorDelegate {

	/**
	 * Interface para o objeto que deve encapsular a regra de aceite ou não de
	 * linhas navegadas.
	 * 
	 * @author Guga
	 */
	public static interface Filtro {

		/**
		 * Diz se a linha corrente deve ou não ser aceita. Use a instância de
		 * {@link Navegador} informada no parâmetro para obter informações sobre
		 * a linha atualmente em análise.
		 * 
		 * @param navegador
		 *            A instância do navegador apontando para a linha atualmente
		 *            em análise.
		 * @return Verdadeiro se a linha deve ser aceita para navegação.
		 */
		boolean aceitar(Navegador navegador);
	}

	private Filtro filtro;

	public NavegadorFiltro(Navegador delegate) {
		super(delegate);
		setFiltro(null);
	}

	public NavegadorFiltro(Navegador delegate, Filtro filtro) {
		super(delegate);
		setFiltro(filtro);
	}

	/**
	 * Filtro contendo as regras para aceite ou não de linhas navegadas.
	 * 
	 * @return Filtro contendo as regras para aceite ou não de linhas navegadas.
	 */
	public Filtro getFiltro() {
		return filtro;
	}

	/**
	 * Filtro contendo as regras para aceite ou não de linhas navegadas.
	 * 
	 * @param filtro
	 *            Filtro contendo as regras para aceite ou não de linhas
	 *            navegadas.
	 */
	public void setFiltro(Filtro filtro) {
		if (filtro != null) {
			this.filtro = filtro;
		} else {
			this.filtro = new Filtro() {
				public boolean aceitar(Navegador navegador) {
					return true;
				}
			};
		}
	}

	/** {@inheritDoc} */
	@Override
	public boolean temAnterior() {
		boolean tem = false;
		int bookmark = delegate.getNumero();
		while (delegate.temAnterior()) {
			delegate.anterior();
			if (filtro.aceitar(delegate)) {
				tem = true;
				break;
			}
		}
		delegate.setNumero(bookmark);
		return tem;
	}

	/** {@inheritDoc} */
	@Override
	public boolean temProximo() {
		boolean tem = false;
		int bookmark = delegate.getNumero();
		while (delegate.temProximo()) {
			delegate.proximo();
			if (filtro.aceitar(delegate)) {
				tem = true;
				break;
			}
		}
		delegate.setNumero(bookmark);
		return tem;
	}

	/** {@inheritDoc} */
	@Override
	public void anterior() {
		while (delegate.temAnterior()) {
			delegate.anterior();
			if (filtro.aceitar(delegate)) {
				break;
			}
		}
	}

	/** {@inheritDoc} */
	@Override
	public void proximo() {
		while (delegate.temProximo()) {
			delegate.proximo();
			if (filtro.aceitar(delegate)) {
				break;
			}
		}
	}

}
