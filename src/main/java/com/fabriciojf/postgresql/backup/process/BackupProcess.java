package com.fabriciojf.postgresql.backup.process;

import com.fabriciojf.postgresql.backup.helper.RegisterHelper;
import com.fabriciojf.postgresql.backup.pojo.Register;
import com.fabriciojf.postgresql.backup.tools.MysqlSQL;
import com.fabriciojf.postgresql.backup.tools.PostgreSQL;


/**
 * Controller contendo a logica de geracao
 * @author Fabricio S Costa fabriciojf@gmail.com
 * @since 24/02/2015
 * @version 1.0
 */
public class BackupProcess {

	/**
	 * Executa o backup dos bancos capturando os dados do arquivo register
	 * no raiz do projeto 
	 */
	public void execute() {
		
		RegisterHelper helper = new RegisterHelper();
		while (helper.hasNext()) {
			Register r = helper.get(); 
			if (r != null) {
				
				if (r.getType().equals("postgresql")) {
				
					try {
						PostgreSQL.backup(r.getHost(), r.getUser(), 
								r.getPass(), r.getDatabase(), r.getLabel());
					} catch (Exception e) {
						e.printStackTrace();
					}
				} else if (r.getType().equals("mysql")) {
					
					try {
						MysqlSQL.backup(r.getHost(), r.getUser(), 
								r.getPass(), r.getDatabase(), r.getLabel());
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
			helper.next();
		}
	}	
}
