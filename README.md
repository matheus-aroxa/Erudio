# Erudio API

API RESTful desenvolvida com Spring Boot para gerenciamento de pessoas e livros. O projeto implementa uma arquitetura organizada em camadas, incluindo suporte a diferentes formatos de resposta, documentação com Swagger, tratamento global de exceções e versionamento de dados via DTOs.

## Tecnologias e Ferramentas

- Java
- Spring Boot
- Spring Data JPA
- Flyway
- Postgresql
- Swagger / OpenAPI
- JUnit 5 / Mockito
- Content Negotiation (JSON, XML, YAML)

## Funcionalidades

- CRUD completo para recursos de pessoas e livros
- Versionamento de DTOs
- Documentação da API com Swagger
- Content negotiation com suporte a múltiplos formatos
- Tratamento global de exceções
- Versionamento do banco de dados com Flyway
- Testes unitários com JUnit e Mockito

## Endpoints

### Pessoas (`/api/person/v1`)
- `GET`    - Lista todas as pessoas
- `POST`   - Cria uma nova pessoa
- `PUT`    - Atualiza uma pessoa existente
- `DELETE` - Remove uma pessoa

### Livros (`/api/book/v1`)
- `GET`    - Lista todos os livros
- `POST`   - Cria um novo livro
- `PUT`    - Atualiza um livro existente
- `DELETE` - Remove um livro

## Execução

```bash
git clone https://github.com/matheus-aroxa/Erudio.git
cd Erudio
docker compose up -d
```

Aplicação disponível em: `http://localhost:8080`  
Swagger: `http://localhost:8080/swagger-ui.html`

## Testes

Para executar os testes:

```bash
./mvnw test
```

## Banco de Dados

O projeto utiliza o Flyway para versionamento de banco de dados. Os scripts de criação e inserção inicial estão localizados em:

```
src/main/resources/db/migration/
```

## Autor

Desenvolvido por [Matheus Aroxa](https://github.com/matheus-aroxa) / [Twitter](https://x.com/mir0mori).
