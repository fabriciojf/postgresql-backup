/*
 * NavegadorTags.java
 * 
 * 05/06/2011
 */
package com.fabriciojf.standalone.helper.navigator;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

/**
 * Implementação de {@link Navegador} para adicionar a funcionalidade de
 * anotação de tags nas linhas navegadas. É possível anotar diversas tags em
 * cada linha navegada, além de acessar tags de outras linhas sem precisar
 * navegar até elas.
 * 
 * Tags são tipos e enumerados associados às linhas.
 * 
 * Este utilitário ainda permite filtrar o navegador por uma coleção de tags
 * indicada.
 * 
 * @author Guga
 */
public class NavegadorTags extends NavegadorDelegate {

	private Map<Integer, Tags> indice;
	private Filtro filtro;

	public NavegadorTags(Navegador delegate) {
		super(new NavegadorFiltro(delegate));
		((NavegadorFiltro) this.delegate).setFiltro(new TagFiltro());

		this.indice = new TreeMap<Integer, Tags>();
		this.filtro = new Filtro();
	}

	/**
	 * Coleção de tags da linha corrente.
	 * 
	 * @return Coleção de tags da linha corrente.
	 */
	public Tags tags() {
		return tags(getNumero());
	}

	/**
	 * Coleção de tags da linha indicada.
	 * 
	 * @param numero
	 *            A linha para a qual se deseja obter as tags.
	 * @return Coleção de tags da linha indicada.
	 */
	public Tags tags(int numero) {
		Tags tags = indice.get(numero);
		if (tags == null) {
			indice.put(numero, tags = new Tags());
		}
		return tags;
	}

	/**
	 * Coleção de tags para filtrar o navegador. Uma vez filtrado os métodos de
	 * navegação varrem apenas linhas com as anotações indicadas.
	 */
	public Filtro filtro() {
		return filtro;
	}

	/**
	 * Class do filtro de tags. Uma instância desta classe pode ser usada para
	 * ativar a navegação somente em linhas anotadas com as tags do filtro.
	 * 
	 * @author Guga
	 */
	@SuppressWarnings("serial")
	public class Filtro extends ArrayList<Enum<?>> {

		/**
		 * Redefine o filtro. Em outras palavras, limpa o filtro corrente e
		 * adiciona a tag indicada como novo filtro.
		 * 
		 * @param tag
		 *            A tag para filtro.
		 */
		public void set(Enum<?> tag) {
			clear();
			add(tag);
		}

		/**
		 * Adiciona várias tags de uma só vez ao filtro.
		 * 
		 * @param tags
		 *            A relação de tags
		 */
		public void add(Enum<?>... tags) {
			for (Enum<?> tag : tags) {
				super.add(tag);
			}
		}
	}

	/**
	 * Classe para a coleção de tags anotadas na linha. Qualquer tipo enumerado
	 * pode ser adicionado como tag de uma linha através desta classe.
	 * 
	 * @author Guga
	 */
	@SuppressWarnings("serial")
	public class Tags extends ArrayList<Enum<?>> {

		/** {@inheritDoc} */
		@Override
		public boolean add(Enum<?> tag) {
			// evita tags duplicadas
			return contains(tag) ? false : super.add(tag);
		}
		
		/**
		 * Refaz a lista de tags de um determinado tipo, isto é, remove todas as
		 * tags do mesmo tipo da tag indicada e adiciona a tag indicada à lista.
		 * <p>
		 * Por exemplo, digamos que existam tags do tipo UmEnum e tags do tipo
		 * OutroEnum anotados na linha. Se informado através deste método a tag
		 * OutroEnum.TAL então todas as tags do tipo OutroEnum serão removidas
		 * da lista e a tag OutroEnum.TAL será adicionada. Ao final teremos
		 * todas as tags UmEnum mais a tag OutorEnum.TAL.
		 * 
		 * @param tag
		 *            A tag a ser adicionada.
		 */
		public void set(Enum<?> tag) {
			Iterator<Enum<?>> it = tags().iterator();
			while (it.hasNext()) {
				Enum<?> item = it.next();
				if (item.getClass() == tag.getClass()) {
					it.remove();
				}
			}
			add(tag);
		}

		/**
		 * Faz a troca de uma tag {@code a} por uma tag {@code b}. Em outras
		 * palavras, remove a tag {@code a} da lista e pões a tag {@code b} no
		 * lugar.
		 * 
		 * @param a
		 *            A tag a ser removida.
		 * @param b
		 *            A tag a ser adicionada.
		 */
		public void swap(Enum<?> a, Enum<?> b) {
			remove(a);
			add(b);
		}

		/**
		 * Diz se existe na coleção de tags pelo menos uma do tipo indicado.
		 * 
		 * @param type
		 *            O tipo de um enumerado.
		 * @return Verdadeiro se existir na coleção de tags pelo menos uma do
		 *         tipo indicado.
		 */
		public boolean containsType(Class<? extends Enum<?>> type) {
			for (Enum<?> item : this) {
				if (type.isInstance(item)) {
					return true;
				}
			}
			return false;
		}

		/**
		 * Remove todas as tags do tipo indicado.
		 * 
		 * @param type
		 *            O tipo de um enumerado.
		 * @return boolean Se nesta lista havia um item deste tipo.
		 */
		public boolean removeType(Class<? extends Enum<?>> type) {
			boolean removido = false;
			Iterator<Enum<?>> it = iterator();
			while (it.hasNext()) {
				if (type.isInstance(it.next())) {
					it.remove();
					removido = true;
				}
			}
			return removido;
		}
	}

	/**
	 * Instância de filtro para filtrar o navegador limitando a navegação
	 * somente entre linhas que anotadas com todas as tags do atributo
	 * {@link NavegadorTags#filtro()}. Istro significa que para realizar a
	 * filtragem do navegador basta adicionar ou remover tags do atributo
	 * {@link NavegadorTags#filtro()}
	 * 
	 * @author Guga
	 */
	private class TagFiltro implements NavegadorFiltro.Filtro {

		/**
		 * Aceita somente linhas anotadas com tags definidas no atributo
		 * {@link NavegadorTags#fi]}. Caso nennhuma tag tenha sido definida como
		 * filtro então todas as linhas serão aceitas para navegação.
		 */
		@Override
		public boolean aceitar(Navegador navegador) {
			if (!filtro.isEmpty()) {
				Tags tags = tags(navegador.getNumero());
				for (Enum<?> tag : filtro) {
					if (!tags.contains(tag)) {
						return false;
					}
				}
			}
			return true;
		}
	}	
}
