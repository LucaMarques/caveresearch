# caveresearch

Sistema academico para modelagem e persistencia de informacoes relacionadas a expedicoes cientificas subterraneas.

## Objetivo

O projeto tem como objetivo transformar regras de negocio de um dominio de expedicoes cientificas em um modelo orientado a objetos Java e, posteriormente, realizar seu mapeamento objeto-relacional utilizando Jakarta Persistence/JPA e PostgreSQL.

## Dominio

O sistema representa uma organizacao de pesquisa ambiental responsavel por expedicoes cientificas em cavernas e ambientes subterraneos. O dominio envolve:

- cavernas;
- setores de pesquisa;
- pessoas e suas especializacoes;
- expedicoes;
- participantes;
- planos de seguranca;
- autorizacoes ambientais;
- equipamentos;
- movimentacoes de equipamentos;
- coletas cientificas;
- amostras;
- relatorios cientificos.

## Tecnologias

Nesta etapa:

- Java 25
- Maven Wrapper com Apache Maven 3.9.16
- Jakarta Persistence/JPA
- Hibernate ORM
- PostgreSQL
- Lombok
- Docker
- Docker Compose
- Git

Planejadas para as proximas etapas:

- entidades JPA;
- objetos incorporaveis com `@Embedded`;
- heranca JPA com estrategia `JOINED`;
- consultas JPA;
- regras de negocio.

Jakarta Persistence/JPA define a especificacao utilizada para persistencia. Hibernate ORM e o provider utilizado para implementar essa especificacao. PostgreSQL e o banco de dados do projeto. Lombok fica disponivel apenas para reduzir boilerplate Java em etapas futuras.

## Modelagem

A documentacao conceitual do dominio e o diagrama UML em Mermaid estao em [docs/modelagem.md](docs/modelagem.md).

## Banco local

Copie `.env.example` para `.env` e ajuste os valores se necessario. O arquivo `.env` nao deve ser versionado.

Variaveis usadas pelo PostgreSQL no Docker:

- `POSTGRES_DB`
- `POSTGRES_USER`
- `POSTGRES_PASSWORD`
- `POSTGRES_PORT`

Para subir o PostgreSQL:

```bash
docker compose up -d
```

Para visualizar os servicos:

```bash
docker compose ps
```

Para acompanhar os logs:

```bash
docker compose logs -f postgres
```

Para encerrar os containers:

```bash
docker compose down
```

Para encerrar os containers e remover tambem o volume persistente:

```bash
docker compose down -v
```

O comando `docker compose down -v` apaga os dados locais armazenados no volume do PostgreSQL.

## Aplicacao

Pre-requisitos para execucao local:

- Java 25 ou superior;
- PostgreSQL local iniciado pelo Docker Compose.

A configuracao JPA usa, por padrao, os mesmos valores definidos em `.env.example`:

- banco: `caveresearch`
- usuario: `caveresearch`
- senha: `caveresearch`
- porta: `5432`

`JpaUtil` centraliza a criacao do `EntityManagerFactory` e pode ler as variaveis de ambiente abaixo:

- `POSTGRES_HOST`
- `POSTGRES_PORT`
- `POSTGRES_DB`
- `POSTGRES_USER`
- `POSTGRES_PASSWORD`

Para compilar no Windows:

```bash
mvnw.cmd clean compile
```

Para compilar no Linux/macOS:

```bash
./mvnw clean compile
```

O PostgreSQL deve estar em execucao antes do uso efetivo da unidade de persistencia.

## Estado atual

Esta etapa contem:

- infraestrutura PostgreSQL com Docker;
- bootstrap Java com Maven;
- configuracao de persistencia JPA para PostgreSQL;
- Hibernate ORM como provider JPA;
- Lombok configurado para uso futuro;
- documentacao inicial do dominio;
- UML conceitual em Mermaid.

Ainda nao existem entidades JPA, objetos incorporaveis implementados, enums do dominio, repositories, services, controllers, DTOs, consultas JPA, migrations, testes automatizados, API REST ou regras de negocio.

Java 25 foi adotado como decisao de implementacao por ser a versao LTS atual e ser compativel com Jakarta Persistence 3.2 e Hibernate ORM 7.4. O enunciado nao determina uma versao especifica de Java.
O Maven Wrapper foi configurado com Apache Maven 3.9.16 por ser a versao estavel recomendada da serie 3.9. O Lombok permanece em 1.18.46, versao atual estavel, configurado como dependencia `provided` e annotation processor.
