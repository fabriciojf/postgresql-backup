# Database Backup

Database backup is a java system for generate PostgreSQL and Mysql backups. This is a linux documentation to install, schedulle and run the jar file for generate your backups.

Technologies

* Java
* PostgreSQL 
* Mysql
* Cron

## Usage the Project

With eclipse 

* Clone the project 
* Eclipse: File -> Import -> Maven -> Existing Maven Projects 
* Select the project folder
* Run the class [Bootstrap.java](https://github.com/fabriciojf/postgresql-backup/blob/master/src/main/java/com/fabriciojf/standalone/start/Bootstrap.java)

## Jar Packing

Use the fatjar plugin to  pack the jar file, adding all libs just one jar. See the link below to know more about fat jar

* [Fat Jar Eclipse Plug-In](http://fjep.sourceforge.net/)


## Jar Install 

Create the folders

```console
$ mkdir /etc/database_backup
$ mkdir /opt/backups
```

Move the generated file **database_backup.jar** to folder **/etc/database_backup**

```console
$ mv {your compiled jar} /etc/database_backup

Example: 

$ mv /opt/database_backup.jar /etc/database_backup
```

If you wish, download the compiled jar file: [database_backup-1.7.jar](https://github.com/fabriciojf/postgresql-backup/releases/tag/1.7)

```console
$ wget https://github.com/fabriciojf/postgresql-backup/releases/download/1.7/database_backup-1.7.jar
```

Execute the jar file to create a initial configuration at /etc/database_backup folder

```console
$ java -jar /etc/database_backup/database_backup.jar 
```

See the file models

* [register](https://github.com/fabriciojf/postgresql-backup/blob/master/database_backup/register) - use this file to register your databases to backup.
* [settings](https://github.com/fabriciojf/postgresql-backup/blob/master/database_backup/settings) - use this file to register a folder to alocate your backups.

## Configuring Database

Edit the file **/etc/database_backup/register** adding the database configurations following the role:

```
host database user pass label type[postgre ou mysql]
```

Example:

```
127.0.0.1 dbuser postgres XXXX== userdatabase postgre
127.0.0.1 cms root XXXX== cmsdatabase mysql
```

* In the above example, first line is generating a PostgreSQL backup and below a Mysql Backup. 
* XXXX== is the encrypted password


## Encrypting the password

To register a new backup in the **register** file is necessary to encrypt the password before. For this, follow the tip below. 

Run the jar file passing the password, generating a new encrypted string.
 
```console
$ java -jar /etc/database_backup/database_backup.jar PASSWORD
```
Change the **register** file introducing the encrypted password in the corresponding line.

If you wish, clean the terminal history with the command below.

```console
$ history -c
```


## Scheduling a Database-backup on Cron with crontab

Type **crontab -e** in the linux terminal.

```console
$ crontab -e
```

Insert a call to database_backup.jar at the end of the file cron. In the case below, the backup will be generated all days at 23:00 o'clock.

```console
0 23 * * * java -jar /etc/database_backup/database_backup.jar
```



### Author

Fabricio S Costa - fabriciojf@gmail.com

[![Linkedin: fabriciojf](https://img.shields.io/badge/-Linkedin-blue?style=flat-square&logo=Linkedin&logoColor=white&link=https://www.linkedin.com/in/fabricioscosta/)](https://www.linkedin.com/in/fabricioscosta/)
[![Site: fabriciojf](https://img.shields.io/badge/-PersonalSite-blue?style=flat-square&logo=wordpress&logoColor=white&link=https://fabriciojf.com)](https://fabriciojf.com)
