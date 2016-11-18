package com.fabriciojf.postgresql.backup.pojo;

/**
 * POJO para o objeto register
 * 
 * @author Fabricio S Costa fabriciojf@gmail.com
 * @since 24/02/2015
 * @version 1.0
 */
public class Register {

	private String host;
	private String database;
	private String user;
	private String pass;
	private String label;
	private String type;

	public Register(String host, String database, String user, String pass,
			String label, String type) {
		this.host = host;
		this.database = database;
		this.user = user;
		this.pass = pass;
		this.label = label;
		this.type = type;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getDatabase() {
		return database;
	}

	public void setDatabase(String database) {
		this.database = database;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
