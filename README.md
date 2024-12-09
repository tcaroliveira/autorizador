# Mini Autorizador

## Descrição do Projeto

O **Mini Autorizador** é uma aplicação RESTful desenvolvida para simular um sistema de autorização de transações financeiras. Ele permite criar cartões, consultar saldos e realizar transações respeitando as regras de negócio definidas.

## Funcionalidades

- **Criação de cartões** com saldo inicial de R$ 500,00.
- **Consulta de saldo** de cartões.
- **Processamento de transações**, com validações para:
  - Existência do cartão.
  - Validade da senha.
  - Disponibilidade de saldo suficiente.
- **Mensagens de erro específicas**:
  - `SALDO_INSUFICIENTE`
  - `SENHA_INVALIDA`
  - `CARTAO_INEXISTENTE`

## Tecnologias Utilizadas

- **Java 17** - Linguagem de programação.
- **Spring Boot 3.1.3** - Framework principal.
  - **Spring Web** - Para construção de APIs RESTful.
  - **Spring Data MongoDB** - Para integração com MongoDB.
  - **Spring Security** - Configuração de autenticação básica.
- **MongoDB 4.2** - Banco de dados NoSQL.
- **Lombok 1.18.28** - Para reduzir boilerplate de código.
- **JUnit 5.9.3** - Para testes unitários.
- **Mockito 4.11.0** - Para mocks em testes.

## Estrutura do Projeto

```plaintext
src/main/java
└── br.com.vr.miniautorizador.autorizador
    ├── controller        # Controladores REST
    ├── dto               # Classes de transferência de dados
    ├── exception         # Exceções personalizadas
    ├── model             # Entidades de domínio
    ├── repository        # Repositórios para MongoDB
    └── service           # Regras de negócio

