# Modelagem Conceitual

Este documento registra a modelagem conceitual do dominio caveresearch. A etapa atual descreve entidades persistentes, objetos incorporaveis, enumeracoes e relacionamentos principais alinhados ao modelo Java.

`Pessoa` foi planejada como superclasse abstrata de uma hierarquia JPA com estrategia `JOINED`, contendo pelo menos `Pesquisador` e `GuiaEspeleologia`. `Localizacao` e `Endereco` foram modelados como objetos incorporaveis, sem identidade propria e sem tabela propria.

```mermaid
classDiagram
    direction LR

    class Caverna {
        <<Entity>>
        +Long idCaverna
        +String nomeOficial
        +String codigoAmbiental
        +String municipio
        +String unidadeFederativa
        +BigDecimal altitude
        +BigDecimal extensaoConhecida
        +LocalDate dataUltimaInspecao
        +Boolean acessoPermitido
    }

    class SetorPesquisa {
        <<Entity>>
        +Long idSetor
        +String denominacao
        +NivelDificuldadeSetor dificuldade
        +BigDecimal profundidadeMaxima
        +BigDecimal extensaoAproximada
        +String descricao
        +Boolean riscoInundacao
        +SituacaoSetor situacaoSetor
    }

    class Pessoa {
        <<Entity>>
        <<abstract>>
        +Long idPessoa
        +String nome
        +String cpf
        +LocalDate dataNascimento
        +String email
        +String telefone
        +boolean situacaoAtiva
    }

    class Pesquisador {
        <<Entity>>
        +Integer registroInstitucional
        +String areaPrincipalPesquisa
        +String titulacao
        +BigDecimal valorDiarioBolsa
    }

    class GuiaEspeleologia {
        <<Entity>>
        +Integer numeroCredencial
        +String nivelCertificacao
        +LocalDate dataValidadeCertificacao
        +int qtdExpedicoesConcluidas
    }

    class Expedicao {
        <<Entity>>
        +Long idExpedicao
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
        <<Entity>>
        +Long idPlanoSeguranca
        +String procedimentosEvacuacao
        +String pontoExternoEncontro
        +Integer tempoMaximoSemComunicacaoHoras
        +String telefoneEmergencia
        +boolean necessitaEquipeMedica
        +byte[] mapaRota
    }

    class AutorizacaoAmbiental {
        <<Entity>>
        +Long idAutorizacaoAmbiental
        +String numero
        +String orgaoEmissor
        +LocalDate dataEmissao
        +LocalDate dataValidade
        +SituacaoAutorizacao situacao
        +String observacoes
        +byte[] arquivoPdfAssinado
    }

    class ParticipacaoExpedicao {
        <<Entity>>
        +Long idParticipacaoExpedicao
        +PapelParticipante papel
        +LocalDate dataConfirmacao
        +BigDecimal valorDiaria
        +Integer quantidadePrevistaDias
        +boolean presencaConfirmada
        +String observacoes
    }

    class Equipamento {
        <<Entity>>
        +Long idEquipamento
        +String codigoPatrimonial
        +String nomeEquipamento
        +TipoEquipamento tipoEquipamento
        +String fabricante
        +BigDecimal valorAquisicao
        +LocalDate dataCompra
        +LocalDate dataUltimaManutencao
        +SituacaoEquipamento situacaoEquipamento
        +Boolean exigeCalibracao
    }

    class MovimentacaoEquipamento {
        <<Entity>>
        +Long idMovimentacao
        +LocalDateTime dataHoraRetirada
        +LocalDateTime dataHoraDevolucaoPrevista
        +LocalDateTime dataHoraDevolucaoEfetiva
        +String estadoSaida
        +String estadoRetorno
        +BigDecimal custoAvaria
    }

    class ColetaCientifica {
        <<Entity>>
        +Long idColeta
        +LocalDateTime dataHoraColeta
        +String metodoEmpregado
        +String descricaoPonto
        +BigDecimal temperatura
        +BigDecimal umidadeRelativa
        +BigDecimal profundidade
        +String observacoes
        +SituacaoValidacaoColeta situacaoDeValidacao

        +addAmostra(Amostra amostra) void
    }

    class Amostra {
        <<Entity>>
        +Long idAmostra
        +String codigoCampo
        +BigDecimal massaOuVolume
        +String unidadeMedida
        +LocalDate dataAcondicionamento
        +CategoriaAmostra categoria
        +CondicaoConservacao condicaoConservacao
        +boolean materialPerigoso
        +byte[] fotografia
        +String observacoes
    }

    class RelatorioFinal {
        <<Entity>>
        +Long idRelatorioFinal
        +String titulo
        +String resumo
        +LocalDate dataSubmissao
        +Integer numeroTotalPaginas
        +SituacaoRelatorio situacao
        +byte[] arquivoCompleto
        +boolean publicacaoAutorizada
    }

    class Localizacao {
        <<Embeddable>>
        +BigDecimal latitude
        +BigDecimal longitude
        +String datumGeodesico
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

    class SituacaoSetor {
        <<enumeration>>
        SECO
        MOLHADO
        ALAGADO
        INSTAVEL
        INTERDITADO
    }

    class TipoEquipamento {
        <<enumeration>>
        TOPOGRAFIA
        NAVEGACAO
        ILUMINACAO
        COMUNICACAO
        SEGURANCA
        PROTECAO_INDIVIDUAL
        RESGATE
        DOCUMENTACAO
        COLETA
        MEDICAO
    }

    class SituacaoEquipamento {
        <<enumeration>>
        DISPONIVEL
        EM_USO
        EM_MANUTENCAO
        INOPERANTE
        DANIFICADO
        PERDIDO
        DESCARTADO
    }

    class PapelParticipante {
        <<enumeration>>
        COORDENADOR
        PESQUISADOR
        GUIA_ESPELEOLOGIA
        RESPONSAVEL_SEGURANCA
        EQUIPE_MEDICA
        APOIO_LOGISTICO
        COMUNICACAO
        TOPOGRAFO
    }

    class CategoriaAmostra {
        <<enumeration>>
        ROCHA
        SOLO
        SEDIMENTO
        AGUA
        MINERAL
        FOSSIL
        MATERIAL_BIOLOGICO
        MATERIAL_ORGANICO
    }

    class CondicaoConservacao {
        <<enumeration>>
        ADEQUADA
        COMPROMETIDA
        CONTAMINADA
        INUTILIZAVEL
    }

    class SituacaoAutorizacao {
        <<enumeration>>
        SOLICITADO
        INDEFERIDO
        DEFERIDO
        PENDENTE_MUDANCA
        CANCELADO
    }

    class SituacaoRelatorio {
        <<enumeration>>
        RASCUNHO
        SUBMETIDO
        EM_ANALISE
        AJUSTES_SOLICITADOS
        APROVADO
        REPROVADO
    }

    class SituacaoValidacaoColeta {
        <<enumeration>>
        PENDENTE
        VALIDADA
        REJEITADA
    }

    Pessoa <|-- Pesquisador
    Pessoa <|-- GuiaEspeleologia

    Caverna "1" *-- "1" Localizacao : localizacao
    Pessoa "1" *-- "1" Endereco : endereco


    Caverna "1" --> "0..*" SetorPesquisa : setores
    Caverna "1" --> "0..*" Expedicao : expedicoes

    Expedicao "0..*" --> "0..*" SetorPesquisa : setores

    Expedicao "1" *-- "1" PlanoSeguranca : planoSeguranca
    Expedicao "1" --> "0..1" AutorizacaoAmbiental : autorizacaoAmbiental
    Expedicao "1" --> "0..1" RelatorioFinal : relatorioFinal

    Expedicao "1" --> "0..*" ParticipacaoExpedicao : participacoes
    Pessoa "1" --> "0..*" ParticipacaoExpedicao : participacoes


    Expedicao "1" --> "0..*" MovimentacaoEquipamento : movimentacoesEquipamento
    Equipamento "1" --> "0..*" MovimentacaoEquipamento : movimentacoes
    Pessoa "1" --> "0..*" MovimentacaoEquipamento : movimentacoesEquipamento

    Expedicao "1" --> "0..*" ColetaCientifica : coletas
    SetorPesquisa "1" --> "0..*" ColetaCientifica : coletas
    Pesquisador "1" --> "0..*" ColetaCientifica : pesquisadorResponsavel

    ColetaCientifica "1" --> "0..*" Amostra : amostras



    SetorPesquisa --> NivelDificuldadeSetor : dificuldade
    SetorPesquisa --> SituacaoSetor : situacaoSetor

    Expedicao --> SituacaoExpedicao : situacao

    AutorizacaoAmbiental --> SituacaoAutorizacao : situacao

    ParticipacaoExpedicao --> PapelParticipante : papel

    Equipamento --> TipoEquipamento : tipoEquipamento
    Equipamento --> SituacaoEquipamento : situacaoEquipamento

    ColetaCientifica --> SituacaoValidacaoColeta : situacaoDeValidacao

    Amostra --> CategoriaAmostra : categoria
    Amostra --> CondicaoConservacao : condicaoConservacao

    RelatorioFinal --> SituacaoRelatorio : situacao
```

## Observacoes de modelagem

- `ParticipacaoExpedicao` e uma entidade associativa planejada entre `Pessoa` e `Expedicao`, com identidade propria.
- A combinacao entre pessoa e expedicao em `ParticipacaoExpedicao` devera ser unica quando o mapeamento persistente for implementado.
- `Expedicao` deve possuir exatamente um `PlanoSeguranca`.
- `AutorizacaoAmbiental` e `RelatorioFinal` sao opcionais para uma `Expedicao`.
- Os valores dos enums foram alinhados com as classes Java em `model.enums`.

## Pessoas, objeto incorporável e herança

A classe é abstrata porque o cadastro deve representar uma especialização concreta, como `Pesquisador` ou `GuiaEspeleologia`. 
Essa estrutura permite acrescentar outras especializações no futuro sem duplicar os dados comuns. 
Por isso, foi escolhida a estratégia de herança JOINED, por meio de @Inheritance(strategy = InheritanceType.JOINED).
Os atributos compartilhados ficam em `tb_pessoa`, enquanto cada especialização possui uma tabela própria com os atributos específicos e uma 
chave primária vinculada à chave de `tb_pessoa` que é definida como `Long` com `GenerationType.IDENTITY`, e herdado pelas subclasses, não se declara um novo `@Id` em cada uma. 
A estratégia evita colunas de pesquisador na tabela de guia e vice-versa, mantendo os campos específicos obrigatórios em suas tabelas.
`Endereco` é um objeto de valor mapeado com `@Embeddable`, o campo correspondente em `Pessoa` utiliza `@Embedded`. 
Seus atributos são colunas da própria `tb_pessoa`, sem identidade nem tabela de endereço.
Os atributos obrigatórios de Endereco são mapeados com restrições nullable = false, de modo que o esquema relacional não aceite endereço incompleto nos campos definidos como essenciais.
Os limites explícitos de nome, CPF, e-mail e telefone restringem os dados de acordo com o domínio. 
O CPF recebe `unique = true` e é armazenado como texto de até 11 caracteres, preservando zeros à esquerda. 
`LocalDate` representa nascimento e validade de certificação, pois esses valores não exigem hora. 
A situação ativa usa `boolean`, correspondente ao tipo booleano do PostgreSQL, sem conversão para caracteres.

## Coleta científica e suas associações

A situação de validação é um enum Java persistido por `@Enumerated(EnumType.STRING)`: nomes como `PENDENTE`, `VALIDADA` e `REJEITADA` são legíveis e não dependem da posição dos elementos no enum. 
Alterar o nome de uma constante futuramente exigirá tratar os valores já gravados no banco.
Cada coleta pertence a uma expedição, ocorre em um setor e possui um pesquisador responsável. 
As três associações são `@ManyToOne`, com `@JoinColumn(nullable = false)`, porque uma expedição, um setor e um pesquisador podem estar relacionados a várias coletas. 
As colunas de chave estrangeira ficam em `tb_coleta_cientifica`, por isso, `ColetaCientifica` é o lado proprietário dessas relações. 
No codigo, a coluna que aponta para `Pesquisador` foi nomeada como `id_pessoa`, acompanhando a chave herdada da hierarquia `JOINED`.
A associacao aponta para a entidade `Pesquisador`, garantindo no modelo JPA que o responsavel pela coleta seja desse subtipo especifico da hierarquia de `Pessoa`.
Essas referências foram configuradas como `LAZY` para evitar trazer expedição, setor e pessoa em consultas que precisam somente dos dados da coleta. 

## Amostras, integridade e ciclo de vida

Uma coleta pode gerar várias amostras, com isso em `ColetaCientifica`, a lista usa `@OneToMany(mappedBy = "coleta", fetch = LAZY)`.
Em `Amostra`, o campo `coleta` usa `@ManyToOne` e `@JoinColumn(nullable = false)`. 
Portanto, a chave estrangeira pertence a `tb_amostra` e **`Amostra` é o lado proprietário**. 
Um método de inclusão deve atualizar os dois lados em memória: adicionar a amostra à lista e executar `amostra.setColeta(this)`.
A coleção é carregada sob demanda, pois listar coletas não exige recuperar todas as suas amostras. 
`cascade = CascadeType.ALL` e `orphanRemoval = true` expressam a decisão de que as amostras têm ciclo de vida dependente da coleta: ao persistir a coleta, as amostras associadas podem ser persistidas junto. 
ao remover uma amostra da coleção, ela pode ser excluída do banco. Essa escolha só é adequada se uma amostra não puder continuar existindo sem sua coleta. 
Ao atualizar, deve-se modificar a coleção gerenciada em vez de substituí-la com `setAmostras(...)`, a retirada de um elemento significa exclusão. 
Não se aplica essa cascata às associações com `Pesquisador`, `SetorPesquisa` e `Expedicao`, que existem independentemente da coleta.
`Amostra` possui identificador autogerado e código de campo com `unique = true`, conforme a exigência de unicidade. 
Categoria e condição de conservação são enums persistidos como `STRING`, isso torna os valores legíveis e evita que a reordenação das constantes altere o significado dos dados.
A fotografia é um `byte[]` mapeado com `@Lob`, mantendo o conteúdo binário no domínio, sem Base64. 
`@Basic(fetch = LAZY)` indica a intenção de carregá-la apenas quando necessário, mas o carregamento tardio de atributos básicos depende do suporte e da configuração do provedor. 
Por isso, as consultas de listagem devem selecionar explicitamente somente os atributos necessários, sem fotografia, uma consulta separada atende ao download do arquivo.
