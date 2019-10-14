# Database Backup

Database Backup é um sistema desenvolvido em java por [Fabricio S Costa](http://fabriciojf.com) fabriciojf@gmail.com para gerar backups de bancos Mysql e PostgreSQL. 


## Instalação direta pelo JAR

Crie as pastas:

```console
$ mkdir /etc/database_backup
$ mkdir /opt/backups
```

Mova o arquivo **database_backup.jar** para a pasta **/etc/database_backup**

```console
$ cd /etc/database_backup
$ wget http://fabriciojf.com/downloads/database_backup.jar 
```

Execute o jar para criar os arquivos de configurações iniciais

```console
$ java -jar /etc/database_backup/database_backup.jar 
```

## Configurando Bancos para Backup

Edite o arquivo /etc/database_backup/register e insira uma linha contendo as configurações do banco que deseja backupear, seguindo o seguinte padrão:

```
host database user pass label type[postgre ou mysql]
```

Exemplo:

```
127.0.0.1 dbuser postgres XXXX== userdatabase postgre
127.0.0.1 cms root XXXX== cmsdatabase mysql
```

No exemplo acima serão gerados 2 backups 1 MySQL e outro PostgreSQL.


## Encriptando a Senha

Para encriptar uma senha antes de cadastrá-la no register, rode o database_backup.jar passando a senha como parametro.
 
```console
$ java -jar /etc/database_backup/database_backup.jar SENHA
```

O resultado da encriptação senha será mostrado no terminal, copie-o e cole dentro do arquivo register na coluna pass.

Recomenda-se após este passo a remoção do comando executado do history do terminal, através do comando history -c, neste caso todo o histórico de comando do linux será apagado.

```console
$ history -c
```


## Agendamento do Database-backup no Cron

Agende através do comando **crontab -e**

```cron
$ crontab -e

Digite no final do arquivo Cron

0 23 * * * java -jar /etc/database_backup/database_backup.jar
```
No caso acima o backup será gerado todos os dias as 23:00h


## Empacotando o jar

Utilize o plugin fatjar no eclipse para empacotar todas as libs em apenas 1 arquivo .jar. Conheça o fatjar

* [Fat Jar Eclipse Plug-In](http://fjep.sourceforge.net/)


## Author

* [Fabricio S Costa](http://fabriciojf.com) fabriciojf@gmail.com
