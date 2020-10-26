# Database Backup

Database backup is a java system for generate PostgreSQL and Mysql backups. This is a linux documentation to install, schedulle and run the jar file for generate your backups.

Technologies

* Java
* PostgreSQL 
* Mysql
* Cron

## Jar Install 

Create the folders

```console
$ mkdir /etc/database_backup
$ mkdir /opt/backups
```

Move the files **database_backup.jar** to folder **/etc/database_backup**

```console
$ cd /etc/database_backup
$ wget http://fabriciojf.com/downloads/database_backup.jar 
```

Execute the jar file to create a initial configuration at /etc/database_backup folder

```console
$ java -jar /etc/database_backup/database_backup.jar 
```

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

* In the above example, first line is generating a PostgreSQL backup and bellow a Mysql Backup. 
* XXXX== is the encrypted password


## Encrypting the password

To register a new backup in the **register** file is necessary to encrypt the password before. For this, follow the tip bellow. 

Run the jar file passing the password, generating a new encrypted string.
 
```console
$ java -jar /etc/database_backup/database_backup.jar SENHA
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


## Jar Packing

Use the fatjar plugin to  pack the jar file, adding all libs just one jar. See the link below to know more about fat jar

* [Fat Jar Eclipse Plug-In](http://fjep.sourceforge.net/)


### Author

Fabricio S Costa - fabriciojf@gmail.com

[![Linkedin: fabriciojf](https://img.shields.io/badge/-Linkedin-blue?style=flat-square&logo=Linkedin&logoColor=white&link=https://www.linkedin.com/in/fabricioscosta/)](https://www.linkedin.com/in/fabricioscosta/)
[![Site: fabriciojf](https://img.shields.io/badge/-PersonalSite-blue?style=flat-square&logo=wordpress&logoColor=white&link=https://fabriciojf.com)](https://fabriciojf.com)
