package com.fabriciojf.postgresql.backup.helper;

import java.io.File;

import com.fabriciojf.postgresql.backup.ambiente.Path;
import com.fabriciojf.postgresql.backup.pojo.Register;
import com.fabriciojf.standalone.helper.TextFileInHelper;
import com.fabriciojf.standalone.helper.TextFileOutHelper;

/**
 * Classe de apoio para o arquivo register
 * @author Fabricio S Costa fabriciojf@gmail.com
 * @since 24/02/2015
 * @version 1.0
 */
public class RegisterHelper {
	
	private String registerPath = String.format("%s/register", Path.getPathConf(true));
	private TextFileInHelper registerFile;
	private int last;
	
	/**
	 * Instancia o register no login
	 */
	public RegisterHelper() {
		validate();
		registerFile = new TextFileInHelper(registerPath);
		registerFile.vaiPara(0);		
		last = 0;
	}
		
	/**
	 * Retorna o arquivo register para ser manipulado no padrao 
	 * {@link TextFileInHelper} 
	 * @return
	 */
	public TextFileInHelper getFile() {
		return registerFile;
	}
	
	/**
	 * Pega o proximo item {@link Register}, montado a partir da linha corrente
	 */
	public Register get() {
		String linha[] = registerFile.toString().split(" ");
		Register register = null;
		
		if ((linha.length == 6) && (!linha[0].contains("#"))) {
			register = new Register(
					linha[0], linha[1], linha[2], linha[3], linha[4], linha[5]);
		}
		return register;
	}

	/**
	 * Pega o proximo item {@link Register}, montado a partir da linha corrente
	 */
	public void next() {
		if (registerFile.temProximo()) 
			registerFile.proximo();
	}
	
	/**
	 * Verifica se possui proximo item
	 */
	public boolean hasNext() {
		/*
		 * last < 2 garante que o sistema vai pegar a ultima linha, senao o
		 * loop vai ignora-la 
		 */
		if (!registerFile.temProximo() && last < 2) {
			last++;
		}
		return last < 2;
	}
		
	/** 
	 * Cria o arquivo register no raiz do projeto e configura a linha de 
	 * ajuda, iniciada por #
	 */
	private void createRegister() {		
		TextFileOutHelper out = new TextFileOutHelper(registerPath);
		
		out.append("# Toda senha deve ser encriptada antes de ser utilizada no comando,").enter(); 
		out.append("# para encripta-la execute o proprio database-backup.jar passando").enter();
		out.append("# a senha que deseja encriptar como parametro. ex:").enter();
		out.append("#").enter();
		out.append("# java -jar /var/lib/database_backup/database-backup.jar sua_senha").enter();
		out.append("#").enter();
		out.append("# Todos os campos sao obrigatorios, e devem seguir a order descrita").enter(); 
		out.append("# abaixo, sendo que o parametro type so aceita os valores postgresql ou mysql").enter();
		out.append("# no singular e somente caracteres minusculos.").enter();
		out.append("#").enter();
		out.append("# host database user pass label type[postgresql ou mysql]").enter();
		out.append("#").enter();
		out.append("# ex:").enter();
		out.append("# 127.0.0.1 jumbocrm postgres senha_database maq_local postgresql").enter();
		out.append("# 127.0.0.1 jumbo root senha_database maq_clientx mysql").enter();
		out.append("#").enter();
		out.append("# Por fim, deixe apenas 1 espaco entre os parametros para facilitar").enter();
		out.append("# a analise do comando.").enter();
		out.save();		
	}
	
	/**
	 * Verifica se o arquivo register contendo as confs do banco de dados existe
	 * @return
	 */
	private void validate() {		
		if (!new File(registerPath).exists()) {
			createRegister();
			System.out.println(String.format(
					"Arquivo %s foi criado, configure os bancos para backup!", 
					registerPath));
			System.exit(0);
		}		
	}	
}
