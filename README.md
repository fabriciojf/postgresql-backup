# Database Backup

Database Backup é um sistema desenvolvido em java por [Fabricio S Costa](http://fabriciojf.com) fabriciojf@gmail.com para gerar backups de bancos Mysql e PostgreSQL. 


## Instalação

Crie as pastas:

```console
$ mkdir /var/lib/database_backup
$ mkdir /etc/database_backup
$ mkdir /opt/backups
```

Mova o arquivo **database-backup.jar** para a pasta **/var/lib/database_backup**

```console
$ mv ./database-backup.jar /var/lib/database_backup
```

Mova o conteúdo da pasta **database_backup** para o  diretório /etc

```console
$ mv ./database_backup /etc
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
$ java -jar /var/lib/database_backup/database-backup.jar SENHA
```

O resultado da encriptação senha será mostrado no terminal, copie-o e cole dentro do arquivo register na coluna pass.

Recomenda-se após este passo a remoção do comando executado do history do terminal, através do comando history -c, neste caso todo o histórico de comando do linux será apagado.

```console
$ history -c
```


## Agendamento do Database-backup no Cron

Mova o arquivo **cron-database-bkp** para a pasta **/etc/cron.daily/** 

```console
$ mv cron-database-bkp /etc/cron.daily/
```

Após copiar o script para o diretório desejado, é necessário reiniciar o daemon do cron, para que as alterações entrem em vigor.

```console
$ /etc/init.d/cron restart
```

Ps. Verifique se o arquivo **cron-database-bkp** está com permissão de execução:

```console
$ ls -l /etc/cron.daily
```

Verifique o horário que o sistema executa os crons diários

```console
$ nano /etc/crontab
```

Localize a linha que contenha o /etc/cron.daily, ex:

```cron
25 1    * * *   root    test -x /usr/sbin/anacron || ( cd / && run-parts --report /etc/cron.daily )
```


## Empacotando o jar

Utilize o plugin fatjar no eclipse para empacotar todas as libs em apenas 1 arquivo .jar. Conheça o fatjar

* [Fat Jar Eclipse Plug-In](http://fjep.sourceforge.net/)


## Author

* [Fabricio S Costa](http://fabriciojf.com) fabriciojf@gmail.com