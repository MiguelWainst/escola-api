# Escola API

Projeto criado com foco em aprendizado prático de **APIs REST** com Spring Boot, documentação com **Swagger/OpenAPI**, modelagem e **relacionamento entre tabelas SQL**, além de evolução para **Spring Security** para autenticação e autorização.

## Tecnologias utilizadas e planejadas

### Em uso no projeto
- Java (atualmente configurado no projeto em **JDK 17**)
- Spring Boot 3
- Spring Web (APIs REST)
- Spring Data JPA
- PostgreSQL
- JDBC (driver PostgreSQL)
- Bean Validation
- MapStruct
- Lombok
- Swagger / OpenAPI (`springdoc-openapi`)
- Maven

### Planejadas para evolução
- Java **JDK 21**
- Spring Security

## Objetivo do projeto

Construir uma API acadêmica para gerenciamento de cursos (e evolução para professores, alunos e usuários), praticando:
- construção de endpoints REST;
- persistência com JPA;
- mapeamentos entre DTOs e entidades;
- documentação de API;
- boas práticas de organização em camadas.

## Estrutura do projeto

```text
src/main/java/com/escola/escola_api
├── controller
├── model/entity
├── repository
├── repository/mapper
└── service
```

## Banco de dados

O repositório possui script SQL inicial em:

- `comandos-sql`

Tabelas previstas no script:
- `cursos`
- `professores`
- `professores_cursos`
- `alunos`
- `usuarios`

## Como executar localmente

### Pré-requisitos
- Java 17+ (recomendado: 17 para compatibilidade atual do projeto)
- Maven 3.9+
- PostgreSQL

### Configuração
1. Crie um banco PostgreSQL (ex.: `escola-api`).
2. Ajuste as credenciais em `src/main/resources/application.yaml`:
   - `spring.datasource.url`
   - `spring.datasource.username`
   - `spring.datasource.password`
3. Execute o script `comandos-sql`.

### Executar aplicação
```bash
./mvnw spring-boot:run
```

## Documentação da API (Swagger)

Com a aplicação em execução, acesse:

- Swagger UI: `http://localhost:8080/swagger-ui/index.html`
- OpenAPI JSON: `http://localhost:8080/v3/api-docs`

## Endpoints disponíveis atualmente

### Cursos
- `GET /cursos` — lista cursos
- `POST /cursos` — cadastra curso
- `GET /cursos/{id}` — busca curso por ID

## Testes

Para executar os testes:

```bash
./mvnw test
```

