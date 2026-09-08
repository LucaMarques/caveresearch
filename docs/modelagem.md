# Modelagem Conceitual

Este documento registra a modelagem conceitual inicial do dominio TurmalinaPB. A etapa atual descreve entidades persistentes planejadas, objetos incorporaveis, enumeracoes e relacionamentos principais, sem implementar classes Java, mapeamentos JPA ou migrations.

`Pessoa` foi planejada como superclasse abstrata de uma hierarquia JPA com estrategia `JOINED`, contendo pelo menos `Pesquisador` e `GuiaEspeleologia`. `Localizacao` e `Endereco` foram modelados como objetos incorporaveis, sem identidade propria e sem tabela propria.

```mermaid
classDiagram
direction LR

class Caverna {
    +Long id
    +String nomeOficial
    +String codigoAmbiental
    +String municipio
    +String unidadeFederativa
    +BigDecimal altitude
    +BigDecimal extensaoConhecida
    +LocalDate dataUltimaInspecao
    +boolean acessoPermitido
}

class Localizacao {
    <<Embeddable>>
    +BigDecimal latitude
    +BigDecimal longitude
    +String datumGeodesico
}

class SetorPesquisa {
    +Long id
    +String denominacao
    +NivelDificuldadeSetor dificuldade
    +BigDecimal profundidadeMaxima
    +BigDecimal extensaoAproximada
    +String descricao
    +boolean riscoInundacao
}

class Pessoa {
    <<abstract>>
    +Long id
    +String nome
    +String cpf
    +LocalDate dataNascimento
    +String email
    +String telefone
    +boolean ativa
}

class Endereco {
    <<Embeddable>>
    +String logradouro
    +String numero
    +String complemento
    +String bairro
    +String cidade
    +String unidadeFederativa
    +String cep
}

class Pesquisador {
    +String registroInstitucional
    +String areaPrincipalPesquisa
    +String titulacao
    +BigDecimal valorDiarioBolsa
}

class GuiaEspeleologia {
    +String numeroCredenciamento
    +String nivelCertificacao
    +LocalDate validadeCertificacao
    +Integer expedicoesConcluidas
}

class Expedicao {
    +Long id
    +String codigo
    +String titulo
    +String objetivo
    +LocalDateTime inicioPrevisto
    +LocalDateTime terminoPrevisto
    +BigDecimal orcamentoAprovado
    +BigDecimal custoRealizado
    +Integer quantidadeMaximaParticipantes
    +SituacaoExpedicao situacao
    +boolean cancelamentoEmergencial
}

class PlanoSeguranca {
    +Long id
    +String procedimentosEvacuacao
    +String pontoEncontro
    +Integer tempoMaxSemComunicacao
    +String telefoneEmergencia
    +boolean necessitaEquipeMedica
    +byte[] mapaRota
}

class AutorizacaoAmbiental {
    +Long id
    +String numero
    +String orgaoEmissor
    +LocalDate dataEmissao
    +LocalDate dataValidade
    +SituacaoAutorizacao situacao
    +String observacoes
    +byte[] arquivoPdf
}

class ParticipacaoExpedicao {
    +Long id
    +PapelParticipante papel
    +LocalDateTime dataConfirmacao
    +BigDecimal valorDiaria
    +Integer quantidadePrevistaDias
    +boolean presencaConfirmada
    +String observacoes
}

class Equipamento {
    +Long id
    +String codigoPatrimonial
    +String nome
    +TipoEquipamento tipo
    +String fabricante
    +BigDecimal valorAquisicao
    +LocalDate dataCompra
    +LocalDate dataUltimaManutencao
    +SituacaoOperacionalEquipamento situacao
    +boolean exigeCalibracao
}

class MovimentacaoEquipamento {
    +Long id
    +LocalDateTime retirada
    +LocalDateTime devolucaoPrevista
    +LocalDateTime devolucaoEfetiva
    +String estadoSaida
    +String estadoRetorno
    +BigDecimal custoAvaria
}

class ColetaCientifica {
    +Long id
    +LocalDateTime dataHora
    +String metodo
    +String descricaoPonto
    +BigDecimal temperatura
    +BigDecimal umidadeRelativa
    +BigDecimal profundidade
    +String observacoes
    +SituacaoValidacaoColeta situacao
}

class Amostra {
    +Long id
    +String codigoCampo
    +CategoriaAmostra categoria
    +BigDecimal massaOuVolume
    +String unidadeMedida
    +LocalDate dataAcondicionamento
    +CondicaoConservacao conservacao
    +boolean materialPerigoso
    +byte[] fotografia
    +String observacoes
}

class RelatorioFinal {
    +Long id
    +String titulo
    +String resumo
    +LocalDate dataSubmissao
    +Integer totalPaginas
    +SituacaoRelatorio situacao
    +byte[] arquivo
    +boolean publicacaoAutorizada
}

class SituacaoExpedicao {
    <<enumeration>>
    PLANEJADA
    AUTORIZADA
    EM_ANDAMENTO
    CONCLUIDA
    CANCELADA
}

class NivelDificuldadeSetor {
    <<enumeration>>
    BAIXO
    MODERADO
    ALTO
    EXTREMO
}

class TipoEquipamento {
    <<enumeration>>
}

class SituacaoOperacionalEquipamento {
    <<enumeration>>
}

class PapelParticipante {
    <<enumeration>>
}

class CategoriaAmostra {
    <<enumeration>>
}

class CondicaoConservacao {
    <<enumeration>>
}

class SituacaoAutorizacao {
    <<enumeration>>
}

class SituacaoRelatorio {
    <<enumeration>>
}

class SituacaoValidacaoColeta {
    <<enumeration>>
}

Pessoa <|-- Pesquisador
Pessoa <|-- GuiaEspeleologia

Caverna *-- Localizacao
Pessoa *-- Endereco

Caverna "1" --> "0..*" SetorPesquisa
Caverna "1" --> "0..*" Expedicao

Expedicao "0..*" --> "0..*" SetorPesquisa

Expedicao "1" --> "1" PlanoSeguranca
Expedicao "1" --> "0..1" AutorizacaoAmbiental
Expedicao "1" --> "0..1" RelatorioFinal

Expedicao "1" --> "0..*" ParticipacaoExpedicao
Pessoa "1" --> "0..*" ParticipacaoExpedicao

Expedicao "1" --> "0..*" MovimentacaoEquipamento
Equipamento "1" --> "0..*" MovimentacaoEquipamento
Pessoa "1" --> "0..*" MovimentacaoEquipamento : responsavel retirada

Expedicao "1" --> "0..*" ColetaCientifica
SetorPesquisa "1" --> "0..*" ColetaCientifica
Pesquisador "1" --> "0..*" ColetaCientifica : responsavel

ColetaCientifica "1" --> "0..*" Amostra

SetorPesquisa --> NivelDificuldadeSetor
Expedicao --> SituacaoExpedicao
AutorizacaoAmbiental --> SituacaoAutorizacao
ParticipacaoExpedicao --> PapelParticipante
Equipamento --> TipoEquipamento
Equipamento --> SituacaoOperacionalEquipamento
ColetaCientifica --> SituacaoValidacaoColeta
Amostra --> CategoriaAmostra
Amostra --> CondicaoConservacao
RelatorioFinal --> SituacaoRelatorio
```

## Observacoes de modelagem

- `ParticipacaoExpedicao` e uma entidade associativa planejada entre `Pessoa` e `Expedicao`, com identidade propria.
- A combinacao entre pessoa e expedicao em `ParticipacaoExpedicao` devera ser unica quando o mapeamento persistente for implementado.
- `Expedicao` deve possuir exatamente um `PlanoSeguranca`.
- `AutorizacaoAmbiental` e `RelatorioFinal` sao opcionais para uma `Expedicao`.
- Os valores de `SituacaoExpedicao` e `NivelDificuldadeSetor` ja foram definidos pelo enunciado.
- Os valores dos demais enums serao refinados em etapa posterior.
