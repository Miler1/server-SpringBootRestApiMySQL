# server-SpringBootRestApiMySQL

O servidor foi implementado na linguagem java com Spring Boot que é baseada no Spring e para o armazenamento de dados foi utilizado o servidor MySQL.

Para executar o servidor spring boot deverão ser executadas as seguintes etapas:

# baixar o arquivo do servidor no repositorio do git com o seguinte comando:
git clone (https://github.com/Miler1/server-SpringBootRestApiMySQL.git)

após baixar o arquivo entrar dentro da pasta do repositório com o comando:
cd "SpringBootRestApiMySQL"

dentro da pasta executar o comando:
"mvn spring-boot:run"

O comando acima instala as dependencias do maven quando é executado pela primeira vez e depois executa o servidor.

caso o comando exibir a seguinte mensagem: 
"The JAVA_HOME environment variable is not defined correctly This environment variable is needed to run this program NB: JAVA_HOME should point to a JDK not a JRE" 

basta executar o comando:
"export PATH=$JAVA_HOME/jre/bin:$PATH" na qual permitirá a execução do comando do maven normalmente.

Para executar o servidor em modo de produção basta executar os comandos:
# Construir o projeto
mvn package 
# Executar o servidor 
java -jar target/spring-boot-restapi-mysql-0.0.1-SNAPSHOT.jar
