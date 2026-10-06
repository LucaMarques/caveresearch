# Buscas N+1

O problema N+1 acontece quando uma consulta busca uma lista de entidades e, depois, ao acessar relacionamentos `LAZY` dentro de um loop, o JPA executa uma nova consulta para cada item da lista.

Exemplo geral: uma busca retorna 10 coletas. Se cada coleta acessa `setor` e `pesquisadorResponsavel` depois da consulta principal, o Hibernate pode fazer 1 consulta para as coletas e mais consultas extras para carregar esses relacionamentos. Isso aumenta o número de acessos ao banco sem necessidade.

## Como foi evitado

Nas buscas em que a aplicação ja sabe que vai exibir dados de entidades relacionadas, foi usado `JOIN FETCH`. Assim, o relacionamento necessario ja vem carregado na mesma consulta.

Isso aparece principalmente nas queries chamadas pelo menu da `Application`, incluindo o relatório da opção:

```text
19 - Gerar relatório com uma amostra de cada busca
```

## Exemplo 1: coletas por expedicao

Arquivo: `src/main/java/br/edu/ifpb/caveresearch/repository/ColetaRepository.java`

A busca `buscarPorExpedicao` retorna coletas e a aplicação exibe dados do setor e do pesquisador responsavel. Por isso, a query usa:

```java
JOIN FETCH c.setor
JOIN FETCH c.pesquisadorResponsavel
```

Sem isso, ao percorrer as coletas e chamar `coleta.getSetor().getDenominacao()` ou `coleta.getPesquisadorResponsavel().getNome()`, o JPA poderia buscar cada `SetorPesquisa` e cada `Pesquisador` separadamente.

Comparação somente dessa parte:

```java
// Como seria sem tratar o N+1
SELECT c
FROM ColetaCientifica c
WHERE c.expedicao.idExpedicao = :idExpedicao
ORDER BY c.dataHoraColeta
```

```java
// Como ficou
SELECT c
FROM ColetaCientifica c
JOIN FETCH c.setor
JOIN FETCH c.pesquisadorResponsavel
WHERE c.expedicao.idExpedicao = :idExpedicao
ORDER BY c.dataHoraColeta
```

## Exemplo 2: detalhes da expedicao

Arquivos:

- `src/main/java/br/edu/ifpb/caveresearch/repository/ExpedicaoRepository.java`
- `src/main/resources/META-INF/orm.xml`

O método `buscarDetalhesComParticipantes` chama a named query `Expedicao.carregarDetalhesComParticipantes`.

Essa named query usa:

```java
JOIN FETCH e.caverna
LEFT JOIN FETCH e.participacoes p
LEFT JOIN FETCH p.pessoa
```

Isso evita que, ao mostrar os detalhes da expedicao, a aplicação precise buscar separadamente a caverna, as participações e as pessoas de cada participação. Como o método já e voltado para a tela de detalhes, faz sentido carregar esses dados juntos.

## Resumo

O `JOIN FETCH` foi usado nas consultas em que o resultado principal já seria exibido junto com dados relacionados. Isso reduz consultas extras geradas por relacionamentos `LAZY` e evita o comportamento N+1 nos pontos em que a aplicação percorre e imprime esses dados.
Existem outras formas para tratar o problema, mas esse foi o utilizado no projeto.