package com.fabriciojf.standalone.start;

import java.util.ArrayList;
import java.util.List;

import com.fabriciojf.postgresql.backup.controller.Controller;
import com.fabriciojf.standalone.helper.HashHelper;

/**
 * Classe startup do sistema
 * @author Fabricio S Costa fabriciojf@gmail.com
 * @version 1.0
 * @since 24/03/2013
 */
public class Bootstrap {
	
	public static void main(String[] args) {	
		
		dicas();
		if (args.length > 0) {
			List<String> encrypt = new ArrayList<String>();
			
			encrypt.add("");
			encrypt.add(String.format("$ %s = %s",
					args[0], HashHelper.encrypt(args[0])));
			
			print(encrypt);
			
			/**
			 * Printa a senha e sai do sistema
			 */
			System.exit(0);
		}
		
		try {			
			Controller.getInstance().execute();
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
	
	/**
	 * Escreva aqui as dicas que voce deseja que o usuario veja no start do sistema 
	 */
	private static void dicas() {
		
		List<String> tips = new ArrayList<String>();
		
		tips.add("|---------------------------------------------------------|");
		tips.add("| DICAS (fabriciojf.com - fabriciojf@gmail.com)           |");
		tips.add("|                                                         |");
		tips.add("| 1. Para saber como ficara uma senha encriptada,         |");
		tips.add("|    execute o programa passando-a como parametro. ex:    |");
		tips.add("| $ java -jar database-backup.jar minha_senha             |");
		tips.add("|                                                         |");
		tips.add("|   Eh altamente recomendado rodar o comando 'history -c' |");
		tips.add("|   apos excutar o comando acima para nao deixar senha    |");
		tips.add("|   exposta no history                                    |");
		tips.add("|                                                         |");
		tips.add("| 2. Para descobrir o path do pg_dump digite no terminal  |");
		tips.add("| $ which pg_dump                                         |");
		tips.add("|                                                         |");
		tips.add("| 3. Ao rodar o software pela primeira vez ele ira criar  |");
		tips.add("|    os arquivos register e settings (rode como root)     |");
		tips.add("|    eles sao utilizados para:                            |");		
		tips.add("|                                                         |");
		tips.add("|    /etc/database_backup/register                        |");
		tips.add("|         onde devem estar setados os bancos para         |");
		tips.add("|         backup, o help sera criado dentro dele          |");
		tips.add("|                                                         |");
		tips.add("|    /etc/database_backup/settings                        |");
		tips.add("|         contem enderecos importantes para o             |");
		tips.add("|         sistema, como path onde os backups serao        |");
		tips.add("|         alocados                                        |");
		tips.add("|                                                         |");
		tips.add("|_________________________________________________________|");
		
		print(tips);
	}
	
	/**
	 * TODO reescrever o metodo no futuro para adicionar print em log
	 */
	private static void print(List<String> text) {
		for (String line : text) {
			System.out.println(line);
		}
	}
}
