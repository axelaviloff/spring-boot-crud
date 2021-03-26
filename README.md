# EntrevistaSpringBoot
:rocket: Entrevista SpringBoot Compasso UOL :rocket:

## Requirements

1. Java :coffee: - 1.8.x

2. Maven - 3.x.x

## Steps to Setup

**1. Clone the application**

```bash
git clone https://github.com/axelaviloff/EntrevistaSpringBoot.git
```


**2. Build and run the app using maven**

```bash
mvn package
java -jar target/entrevista-0.0.1-SNAPSHOT.jar
```

The app will start running at <http://localhost:8080>.

## Explore Rest APIs

The app defines following APIs.

### Cidade
   :green_book: POST /cidade
    
   :blue_book: GET /cidade/buscarPeloEstado/{estado}

   :blue_book: GET /cidade/buscarPeloNome/{nome}

### Cliente
   :green_book: POST /cliente
    
   :orange_book: PUT /cliente
    
   :blue_book: GET /cliente/buscarPeloId/{id}
    
   :blue_book: GET /cliente/buscarPeloNome/{nome}
    
   :closed_book: DELETE /cliente/removerPeloId/{id}

You can test them using postman or any other rest client.

