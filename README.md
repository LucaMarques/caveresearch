# caveresearch

Sistema academico para modelagem e persistencia de informacoes relacionadas a expedicoes cientificas subterraneas.

## Objetivo

O projeto transforma regras de negocio de um dominio de expedicoes cientificas em um modelo orientado a objetos Java, com mapeamento objeto-relacional usando Jakarta Persistence/JPA, Hibernate ORM e PostgreSQL.

Atualmente a aplicacao roda pelo console e permite popular o banco com dados iniciais, executar consultas do dominio e gerar um relatorio com uma amostra das buscas implementadas.

## Dominio

O sistema representa uma organizacao de pesquisa ambiental responsavel por expedicoes cientificas em cavernas e ambientes subterraneos. O dominio envolve:

- cavernas;
- setores de pesquisa;
- pessoas, pesquisadores e guias de espeleologia;
- expedicoes;
- participantes de expedicoes;
- planos de seguranca;
- autorizacoes ambientais;
- equipamentos;
- movimentacoes de equipamentos;
- coletas cientificas;
- amostras;
- relatorios cientificos.

## Tecnologias

- Java 25
- Maven Wrapper com Apache Maven 3.9.16
- Jakarta Persistence/JPA 3.2
- Hibernate ORM
- PostgreSQL
- Narayana JTA
- Lombok
- Docker
- Docker Compose
- Git

Jakarta Persistence/JPA define a especificacao usada para persistencia. Hibernate ORM e o provider JPA. PostgreSQL e o banco de dados local. Narayana JTA fornece a transacao JTA usada no seed, e Lombok reduz boilerplate nas entidades e embeddables.

## Implementacao atual

O projeto ja contem:

- entidades JPA em `src/main/java/br/edu/ifpb/caveresearch/model/entity`;
- objetos incorporaveis `Endereco` e `Localizacao`;
- enums do dominio em `model/enums`;
- heranca JPA `JOINED` em `Pessoa`, com subclasses `Pesquisador` e `GuiaEspeleologia`;
- relacionamentos JPA `OneToOne`, `OneToMany`, `ManyToOne` e `ManyToMany`;
- campos binarios mapeados com `@Lob` para arquivos, mapas e fotografias;
- `persistence.xml` com a unidade `caveresearchPU`;
- `orm.xml` com named queries;
- repositories com consultas JPQL, `JOIN FETCH`, projections e downloads de campos binarios;
- DTO `ExpedicaoResumo` para consulta projetada;
- seed de dados em `DatabaseSeeder`;
- menu de console em `Application`;
- documentacao de modelagem e consultas em `docs/`.

Ainda nao ha API REST, controllers web, camada de services separada, migrations ou testes automatizados. O schema local e atualizado pelo Hibernate via `hibernate.hbm2ddl.auto=update`.

## Documentacao

- [Modelagem conceitual](docs/modelagem.md)
- [Relatorio de consultas da aplicacao](docs/relatorio.md)
- [Tratamento de buscas N+1](docs/BuscasN+1.md)

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

## Configuracao JPA

`JpaUtil` centraliza a criacao do `EntityManagerFactory` e usa a unidade de persistencia `caveresearchPU`.

Por padrao, a conexao usa:

- host: `localhost`
- porta: `5432`
- banco: `caveresearch`
- usuario: `caveresearch`
- senha: `caveresearch`

As seguintes variaveis de ambiente podem sobrescrever a configuracao padrao:

- `POSTGRES_HOST`
- `POSTGRES_PORT`
- `POSTGRES_DB`
- `POSTGRES_USER`
- `POSTGRES_PASSWORD`

## Execucao

Pre-requisitos:

- JDK 25 ou superior;
- Docker e Docker Compose;
- PostgreSQL iniciado com `docker compose up -d`.

Para compilar no Windows:

```bash
.\mvnw.cmd clean compile
```

Para compilar no Linux/macOS:

```bash
./mvnw clean compile
```

Para executar a aplicacao pela IDE, rode a classe:

```text
br.edu.ifpb.caveresearch.Application
```

Tambem e possivel executar pelo Maven:

```bash
.\mvnw.cmd exec:java -Dexec.mainClass="br.edu.ifpb.caveresearch.Application"
```

No Linux/macOS:

```bash
./mvnw exec:java -Dexec.mainClass="br.edu.ifpb.caveresearch.Application"
```

## Menu da aplicacao

A aplicacao de console disponibiliza consultas individuais e tarefas auxiliares. As principais opcoes sao:

- listar expedicoes por periodo e situacao;
- carregar detalhes de uma expedicao com participantes;
- listar coletas e amostras;
- buscar cavernas por id, municipio e permissao de acesso;
- listar equipamentos disponiveis por periodo;
- consultar historico de movimentacao de equipamento;
- buscar pesquisadores por area, coletas e bolsa acima da media;
- consultar planos de seguranca, relatorios finais e setores;
- gerar um relatorio com uma amostra de cada busca;
- popular o banco com dados iniciais.

Use a opcao `20` para popular o banco. O seed e ignorado quando ja existem pessoas cadastradas.

Use a opcao `19` para gerar o relatorio consolidado das consultas implementadas.
