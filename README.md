<h2>Desafio PubFuture</h2>

Projeto desenvolvido para o desafio PubFuture.

<h3>Sobre o projeto</h3>

O desafio era implementar uma solução que auxiliasse no controle das finanças pessoais.
O projeto deveria possuir as receitas, despesas e contas. 
Nesse projeto foi utilizado a arquitetura MVC seguindo as especificações de uma API RESTful.

<h3>Ferramentas e tecnologias utilizadas</h3>
<li>Java SE 11</li>
<li>Spring Boot 2.5.7, Spring Web, Spring Data, Spring Test, Spring Validation</li>
<li>Maven</li>
<li>H2 (Banco de dados em memória)/PostgreSQL (banco de dados relacional)</li>
<li>Junit 5 (Testes unitários)</li>
<li>Project Lombok</li>
<li>ModelMapper 2.4.2</li>
<li>Swagger / Swagger UI 2.9.2 (Documentação API)</li>
<li>Hibernate (Mapeamento Objeto-Relacional)</li>

<h3>Como rodar o projeto</h3>

<li>Foi utilizado o banco de dados em memória H2 para auxiliar nos testes e na implementação do projeto,
sinta-se livre para utilizar qualquer outro banco de dados relacional, se atentando as alterações necessários no
<a href="https://github.com/jeffersontavaresl/desafiopubfuture/blob/main/src/main/resources/application.properties">applications.properties</a>
e adicionar a dependencia do banco no <a href="https://github.com/jeffersontavaresl/desafiopubfuture/blob/main/pom.xml">pom</a>.
Poderá ser utilizar qualquer IDE que suporte Java e Maven. Caso em sua IDE o projeto apresente algum erro, verifique se é necessário instalar o plugin do 
<a href="https://projectlombok.org/">Lombok</a>. 

<h3><b>Colocando a mão na massa...</b></h3>
<b>1. Clonar este repositório</b><br>
$ git clone https://github.com/jeffersontavaresl/desafiopubfuture <br>

<b>2. Abra o terminal na pasta do projeto e execute o comando, caso for utilizar o Maven</b><br>
mvnm spring-boot:run<br>

<b>3. Acesse o link para testar os endpoints e uso geral do projeto</b><br>
http://localhost:8080/swagger-ui.html#/<br>

<b>Se rodar em alguma IDE e utilizando o PostgreSQL</b><br>
<b>1.1 Instalar o plugin do Lombok</b><br>
	https://projectlombok.org/

<b>1.2 Em application-properties alterar as configurações para o postgresql</b><br>

<b>1.3 No pom alterar as dependencias para o postgresql</b><br>

<b> * Se necessário, atualizar o projeto, e então rodar, e então acessar o link para testar os endpoints e uso geral do projeto ( OU use o Postman e/ou outra ferramenta para testar os endpoints)
 http://localhost:8080/swagger-ui.html#/ </b><br>
 
 <h3>Programa em funcionamento</h3>
 
 

