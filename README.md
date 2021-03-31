# :rocket: Entrevista SpringBoot :rocket:
*by Axel Aviloff*
\
API Rest utilizando Spring Boot, Spring Data JPA, Spring Security, Swagger2 e Banco de dados H2

## Requerimentos

> 1. Java :coffee: - 1.8.x

> 2. Maven - 3.x.x

## Passos para configurar

**1. Clone o Projeto**

```shell
$ git clone https://github.com/axelaviloff/EntrevistaSpringBoot.git
```

**2. Teste a aplicação utilizando Maven**
```shell
$ mvn clean test

```

**3. Construa e rode a aplicação utilizando Maven**

```shell
$ mvn package
$ java -jar target/entrevista-0.0.1-SNAPSHOT.jar
```

A aplicação irá rodar em <http://localhost:8080>.

## Explore as Rest APIs
* ### Usando a documentação do Swagger
   *  Rode a aplicação.
      * Acesse http://localhost:8080/swagger-ui.html.
      
* ### Resumo dos endpoints
   *  #### Cidade
       * :green_book: POST /cidade
       * :blue_book: GET /cidade/buscarPeloEstado/{estado}
       * :blue_book: GET /cidade/buscarPeloNome/{nome}

  *  #### Cliente
       * :green_book: POST /cliente
       * :orange_book: PUT /cliente
       * :blue_book: GET /cliente/buscarPeloId/{id}
       * :blue_book: GET /cliente/buscarPeloNome/{nome}
       * :closed_book: DELETE /cliente/removerPeloId/{id}
   #####
   * Você também pode testar a aplicação usando Postman ou qualquer outro cliente Rest

