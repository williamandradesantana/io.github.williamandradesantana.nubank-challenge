# Nubank Challenge

API REST para cadastro de **clientes** e seus **contatos** (telefones), desenvolvida como desafio técnico. Um cliente pode ter vários contatos; cada contato pertence a exatamente um cliente e tem telefone único.

> Implementação do desafio **["Desafio Back-End Nubank"](https://github.com/Maykon-JDS/desafio-backend-nubank)**, disponibilizado publicamente por [Maykon-JDS](https://github.com/Maykon-JDS). Todos os requisitos técnicos e diferenciais propostos ali (Spring Boot + Spring Data JPA, PostgreSQL, DTOs, Lombok, Docker, testes automatizados e Swagger) foram cobertos nesta implementação.

## Tecnologias

- **Java 21**
- **Spring Boot 4.1.1** (Web MVC, Data JPA)
- **PostgreSQL 16**
- **Lombok**
- **springdoc-openapi** (Swagger UI)
- **JUnit 5** + **Mockito** (testes unitários)
- **Testcontainers** (testes de integração)
- **Maven**
- **GitHub Actions** (CI)

## Arquitetura

O projeto segue uma separação em camadas simples:

```
controllers/  → endpoints REST
services/     → orquestração e regras de aplicação (@Transactional)
validators/   → regras de negócio de validação dos requests
mappers/      → conversão entidade ↔ DTO
repositories/ → acesso a dados (Spring Data JPA)
domain/       → entidades JPA (Client, Contact)
dtos/         → records de request/response
exceptions/   → exceções de negócio + handler global
```

### Modelo de domínio

- `Client` (`tb_clients`): `id`, `fullName`, `contacts` (`Set<Contact>`, lazy).
- `Contact` (`tb_contacts`): `id`, `phone` (único), `client` (obrigatório).
- Relação `Client 1—N Contact`, com `cascade = ALL` e `orphanRemoval = true`: remover um cliente remove seus contatos.

## Como rodar localmente

### Pré-requisitos

- Java 21
- Docker (para o Postgres via `docker-compose`, e para o Testcontainers rodar os testes)

### 1. Subir o banco

Crie um `.env` na raiz (baseado no `.env.example`, se houver) com:

```env
POSTGRES_HOST=localhost
POSTGRES_PORT=5432
POSTGRES_DB=nubank_db
POSTGRES_USER=postgres
POSTGRES_PASSWORD=postgres
OUTPUT_ANSI_ENABLED=ALWAYS
```

```bash
docker compose --env-file .env up -d
```

### 2. Rodar a aplicação

```bash
export $(cat .env | xargs) && ./mvnw spring-boot:run
```

A API sobe em `http://localhost:8080`.

### 3. Documentação interativa (Swagger)

Com a aplicação rodando, acesse:

```
http://localhost:8080/swagger-ui.html
```

## Endpoints

| Método | Rota                      | Descrição                                   | Sucesso | Erros                     |
|--------|---------------------------|----------------------------------------------|---------|---------------------------|
| GET    | `/clientes`                | Lista todos os clientes com seus contatos     | 200     | —                          |
| GET    | `/clientes/{id}/contatos`  | Busca um cliente (com contatos) por id        | 200     | 404 (cliente não existe)  |
| POST   | `/clientes`                 | Cria um novo cliente                          | 201     | 409 (nome inválido)       |
| POST   | `/contatos`                 | Cria um contato e associa a um cliente        | 201     | 404 (cliente não existe), 409 (telefone inválido/duplicado) |

### Exemplo — criar cliente

```http
POST /clientes
Content-Type: application/json

{
  "fullName": "Zeca Ramos"
}
```

### Exemplo — criar contato

```http
POST /contatos
Content-Type: application/json

{
  "phone": "11999998888",
  "clientId": 1
}
```

### Formato de erro

Todas as exceções de negócio retornam o mesmo formato, via `GlobalExceptionHandler`:

```json
{
  "timestamp": "2026-09-05T13:57:00.681950Z",
  "message": "Client not found: 99",
  "details": "uri=/clientes/99/contatos"
}
```

| Exceção                        | Status HTTP |
|---------------------------------|-------------|
| `ResourceNotFoundException`     | 404         |
| `BusinessException`             | 409         |
| `RequiredObjectIsNullException` | 400         |
| Demais exceções não tratadas    | 500         |

## Regras de validação

**Cliente**
- `fullName` obrigatório, entre 1 e 100 caracteres.

**Contato**
- `phone` obrigatório, entre 1 e 20 caracteres, único no sistema.
- `clientId` obrigatório e deve corresponder a um cliente existente.

## Testes

O projeto tem duas camadas de teste:

- **Unitários** (JUnit + Mockito): domínio, validators e services, com dependências mockadas — rápidos e isolados de infraestrutura.
- **Integração** (JUnit + Testcontainers): repositories, services e o fluxo HTTP completo, rodando contra um PostgreSQL real em container. Usam `PostgresIntegrationTest` como classe base, com um container Postgres compartilhado entre as classes da suíte.

```bash
./mvnw test
```

> Os testes de integração exigem Docker disponível na máquina/CI — o Testcontainers sobe e derruba o container automaticamente.

## CI

Todo push/PR para `main` e `develop` roda o pipeline definido em `.github/workflows/nubank-challenge-ci.yaml`: build + suíte de testes completa (unitários e integração) via `mvn clean verify`, com publicação do relatório do Surefire como artefato.

## Decisões técnicas

- **`open-in-view: false`**: desabilitado deliberadamente (é considerado anti-pattern manter a sessão do Hibernate aberta até a serialização da view). Por isso, todo acesso a coleções lazy (`Client.contacts`) acontece dentro de métodos `@Transactional` explícitos na camada de serviço.
- **`ddl-auto: none`** em produção: o schema não é gerenciado pelo Hibernate; no perfil de teste (`application-test.yaml`) usa-se `create-drop`, já que cada execução parte de um container novo.

## Licença

Distribuído sob a licença MIT. Veja [LICENSE](LICENSE) para mais detalhes.