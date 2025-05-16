# Order Microservice

O **Fast Food Order Microservice** é um serviço desenvolvido em **Java 21** com **Spring Boot 3.4** que faz parte do ecossistema de microserviços do projeto Fast-Food. Ele é responsável por gerenciar pedidos de fast-food, oferecendo funcionalidades para:

- **Criar novos pedidos**  
- **Consultar pedidos por ID**  
- **Listar pedidos com paginação**  
- **Atualizar status de pedidos**  

---

## Tecnologias utilizadas

- **Java 21**  
- **Spring Boot 3.4**  
- **Maven**  
- **Lombok**  
- **Postgres: Banco de dados relacional**
- **Docker & Docker Compose**  
- **GitHub Actions** (CI/CD Pipeline)  

---

## Como rodar localmente

### Pré-requisitos

- Java 21 instalado  
- Maven 3.8+ instalado  
- Docker & Docker Compose instalados  

### Docker Compose

- Ter o Docker e Docker Compose instalados

```shell
  docker compose -p fast-food-order up -d
```

### Rodar a aplicação

No terminal, execute:

```bash
mvn clean install
mvn spring-boot:run
```

A aplicação ficará disponível em:  
```
http://localhost:8080
```

---

## API Endpoints

| Método | Rota                             | Descrição                       |
| ------ | -------------------------------- | ------------------------------- |
| GET    | `/orders/{id}`                   | Buscar pedido por ID            |
| GET    | `/orders?page={padrão0}&size={10}` | Listar pedidos paginados        |
| POST   | `/orders`                        | Criar novo pedido               |
| PUT    | `/orders/{id}/status`            | Atualizar status de um pedido   |


## Testes

Para executar os testes automatizados:

```bash
mvn test
```

O **GitHub Actions** está configurado para executar, a cada push:

1. Análise estática de código  
2. Testes unitários (JUnit + Mockito)  
3. Verificação de cobertura e qualidade via SonarQube  

