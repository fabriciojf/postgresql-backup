/*
 * Navegador.java
 *
 * 05/06/2011
 */
package com.fabriciojf.standalone.helper.navigator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Interface para o navegador de linhas de arquivo. O navegador permite avançar
 * ou recuar linhas, comparar a linha corrente com strins e expressões
 * regulares, detre outros recursos.
 *
 * @author Guga
 */
public abstract class Navegador {

	/**
	 * Diz se o navegador está na posição BOF (Início de arquivo). Esta é a
	 * posição antes da primeira linha.
	 *
	 * @return Verdadeiro se o navegador estiver na posição BOF (Início de
	 *         arquivo)
	 */
	public boolean isBOF() {
		return getNumero() < 0;
	}

	/**
	 * Diz se o navegador está na posição EOF (Fim de arquivo). Esta é a posição
	 * posterior à última linha.
	 *
	 * @return Verdadeiro se o navegador estiver na posição EOF (Fim de arquivo)
	 */
	public boolean isEOF() {
		return getNumero() >= getTotalDeLinhas();
	}

	/**
	 * Direciona o navegador para a posição EOF (Fim de arquivo). Esta é a
	 * posição antes da primeira linha.
	 */
	public void bof() {
		setNumero(-1);
	}

	/**
	 * Direciona o navegador para a posição BOF (Início de arquivo). Esta é a
	 * posição antes da primeira linha.
	 */
	public void eof() {
		setNumero(getTotalDeLinhas());
	}

	/**
	 * Quantidade total de linhas no arquivo.
	 *
	 * @return Quantidade total de linhas no arquivo.
	 */
	public abstract int getTotalDeLinhas();

	/**
	 * Número da linha corrente.
	 *
	 * @return Número da linha corrente.
	 */
	public abstract int getNumero();

	/**
	 * Define o número da linha corrente.
	 *
	 * @param valor
	 *            Número da linha corrente.
	 */
	public abstract void setNumero(int valor);

	/**
	 * Diz se existe uma linha anterior.
	 *
	 * @return Verdadeir se existe uma linha anterior.
	 */
	public abstract boolean temAnterior();

	/**
	 * Diz se existe uma próxima linha.
	 *
	 * @return Verdadeiro se existe uma próxima linha.
	 */
	public abstract boolean temProximo();

	/**
	 * Aponta o navegador para a linha anterior do arquivo. O conteúdo da linha
	 * pode ser obtido com {@link #toString()} ou comparado diretamente com
	 * outras strings ou expressões regulares com os métodos
	 * {@link #matches(String)}, {@link #isMaiusculo()}, {@link #contem(String)},
	 * {@link #equals(Object)}, dentre outros.
	 */
	public abstract void anterior();

	/**
	 * Aponta o navegador para a próxima linha do arquivo. O conteúdo da linha
	 * pode ser obtido com {@link #toString()} ou comparado diretamente com
	 * outras strings ou expressões regulares com os métodos
	 * {@link #matches(String)}, {@link #isMaiusculo()}, {@link #contem(String)},
	 * {@link #equals(Object)}, dentre outros.
	 */
	public abstract void proximo();

	/**
	 * Marca a posição da linha corrente. A próxima chamada ao método
	 * {@link #retornar()} aponta o navegador para esta linha.
	 */
	public abstract void marcar();

	/**
	 * Aponta o navegador para BOF (Begin Of File ou Início de Arquivo) ou para
	 * a linha marcada com o método {@link #marcar()}. Depois do retorno
	 * informações de {@code bookmark} são descartadas, isto é, o
	 * bookmark volta a valer BOF.
	 */
	public abstract void retornar();

	/**
	 * Obtém o conteúdo da linha corrente.
	 *
	 * @return O conteúdo da linha corrente.
	 */
	@Override
	public abstract String toString();

	/**
	 * Diz se a linha corrente está toda em caixa-alta.
	 *
	 * @return Verdadeiro se a linha corrente estiver toda em caixa-alta.
	 */
	public boolean isMaiusculo() {
		// TODO: nos meus testes as vezes o primeiro caracter um fora da tabela
		// ascii provavelmente erro de encoding durante a geracao dele
		// por isto esto ignorando o primeiro caracter aqui.
		// isto precisa ser revisto
		return toString().matches(".[\\W\\p{Lu} ]+");
	}

	/**
	 * Diz se a linha está vazia.
	 * @return Verdadeiro se a linha estiver vazia.
	 */
	public boolean isEmpty() {
		return toString().isEmpty();
	}

	/**
	 * Diz se a linha corrente confere com a expressão regular indicada.
	 *
	 * @param regex
	 *            A expressão regular.
	 * @return Verdadeiro se a linha corrente confere com a expressão regular
	 *         indicada.
	 */
	public boolean matches(String regex) {
		return toString().matches(regex);
	}

	/**
	 * Gera uma instância de {@link Matcher} para comparação da linha corrente
	 * com a expressão regular correspondente.
	 * <p>
	 * Exemplo:
	 *
	 * <pre>
	 * Matcher matcher = navegador.matcher();
	 * if (matcher.matches()) {
	 * 		String tal = matcher.group(1);
	 * 		...
	 * }
	 * </pre>
	 *
	 * Veja a documentação de {@link Pattern} e {@link Matcher} para mais
	 * detalhes.
	 *
	 * @param regex
	 *            A expressão regular.
	 * @return A instância de {@link Matcher}.
	 * @see Pattern
	 * @see Matcher
	 */
	public Matcher matcher(Pattern regex) {
		return regex.matcher(toString());
	}

	/**
	 * Diz se a linha corrente possui a string indicada.
	 *
	 * @param sequencia
	 *            A string procurada.
	 * @return Verdadeiro se a linha corrente possui a string indicada.
	 */
	public boolean contem(String sequence) {
		return toString().contains(sequence);
	}

	/**
	 * Diz se a linha corrente começa com a string indicada.
	 *
	 * @param prefixo
	 * @return Verdadeiro se a linha corrente começa com a string indicada.
	 */
	public boolean iniciaCom(String prefix) {
		return toString().startsWith(prefix);
	}

	/**
	 * Diz se a linha corrente confere com o objeto indicado. Pode ser usado
	 * para comparar a linha corrente diretamente com uma string, por exemplo.
	 * <p>
	 * Exemplo:
	 *
	 * <pre>
	 * if (navegador.equals("TAL COISA")) {
	 * 		...
	 * }
	 * </pre>
	 *
	 * @param obj
	 * @return
	 */
	@Override
	public boolean equals(Object obj) {
		return toString().equals(obj);
	}

	/**
	 * Retorna espacos do inicio e fim da palavra
	 *
	 * @param obj
	 * @return
	 */
	public String trim() {
		return toString().trim();
	}

}