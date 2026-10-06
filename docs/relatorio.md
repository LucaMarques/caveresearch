# Relatorio de consultas da aplicacao

O relatorio foi feito dentro da classe `Application`, usando o proprio menu da aplicacao. A opcao criada para isso e:

```text
19 - Gerar relatorio com uma amostra de cada busca
```

Ao escolher essa opcao, o metodo `gerarRelatorioBuscas` e executado. Ele nao percorre todos os dados retornados pelas consultas; para cada busca, ele chama o metodo correspondente no repository e mostra apenas o primeiro resultado encontrado.

Antes de chamar algumas consultas, o metodo busca um registro base de cada entidade necessaria, como `Caverna`, `Expedicao`, `ColetaCientifica`, `Equipamento`, `Pesquisador`, `PlanoSeguranca`, `RelatorioFinal` e `SetorPesquisa`. Esses registros base servem apenas para montar os parametros exigidos pelas buscas.

## Consultas chamadas no relatorio

O comando da aplicacao chama as seguintes buscas:

- `CavernaRepository.findById`
- `CavernaRepository.buscarPorMunicipioEAcesso`
- `ExpedicaoRepository.buscarPorSituacao`
- `ExpedicaoRepository.listarResumoPorPeriodoESituacao`
- `ExpedicaoRepository.buscarDetalhesComParticipantes`
- `ColetaRepository.buscarPorExpedicao`
- `AutorizacaoAmbientalRepository.baixarArquivoPdfPorExpedicao`
- `PlanoSegurancaRepository.baixarMapaRotaPorExpedicao`
- `RelatorioFinalRepository.baixarArquivoCompletoPorExpedicao`
- `AmostraRepository.buscarAmostrasPorColeta`
- `EquipamentoRepository.equipamentosDisponiveisPorData`
- `MovimentacaoEquipamentoRepository.buscarHistoricoPorEquipamento`
- `PesquisadorRepository.buscarPorArea`
- `PesquisadorRepository.buscarComColetas`
- `PesquisadorRepository.buscarPorBolsaAcimaDaMedia`
- `PlanoSegurancaRepository.buscarPorNecessidadeEquipeMedica`
- `RelatorioFinalRepository.buscarPorSituacao`
- `SetorPesquisaRepository.buscarPorCavernaESituacao`

## Saida exibida

A saida:

```text
=== Relatorio de consultas dos repositories ===
- CavernaRepository.findById: Caverna 1 | nome: Gruta do Cristal | municipio: Joao Pessoa | uf: PB | acesso: sim
- CavernaRepository.buscarPorMunicipioEAcesso: Caverna 1 | nome: Gruta do Cristal | municipio: Joao Pessoa | uf: PB | acesso: sim
- ExpedicaoRepository.buscarPorSituacao: Expedicao 1 | codigo: EXP-001 | titulo: Grupo Ametista | caverna: Gruta do Cristal | inicio: 07/09/2026 22:24 | termino: 09/09/2026 22:24 | situacao: CONCLUIDA
- ExpedicaoRepository.listarResumoPorPeriodoESituacao: Expedicao 1 | codigo: EXP-001 | titulo: Grupo Ametista | caverna: Gruta do Cristal | inicio: 07/09/2026 22:24 | termino: 09/09/2026 22:24 | situacao: CONCLUIDA
- ExpedicaoRepository.buscarDetalhesComParticipantes: Expedicao 1 | codigo: EXP-001 | titulo: Grupo Ametista | caverna: Gruta do Cristal | inicio: 07/09/2026 22:24 | termino: 09/09/2026 22:24 | situacao: CONCLUIDA
- ColetaRepository.buscarPorExpedicao: Coleta 1 | data: 08/09/2026 02:24 | setor: Salao Principal | responsavel: Luca | situacao: VALIDADA
- AutorizacaoAmbientalRepository.baixarArquivoPdfPorExpedicao: sem arquivo.
- PlanoSegurancaRepository.baixarMapaRotaPorExpedicao: sem arquivo.
- RelatorioFinalRepository.baixarArquivoCompletoPorExpedicao: sem arquivo.
- AmostraRepository.buscarAmostrasPorColeta: Amostra 2 | codigo: AMT-AGUA-02 | categoria: AGUA | conservacao: COMPROMETIDA | quantidade: 1.250 L | perigoso: nao
- EquipamentoRepository.equipamentosDisponiveisPorData: Equipamento 2 | codigo: EQ-RAD-002 | nome: Radio comunicador | tipo: COMUNICACAO | situacao: DISPONIVEL
- MovimentacaoEquipamentoRepository.buscarHistoricoPorEquipamento: Movimentacao 1 | equipamento: GPS de mapeamento | expedicao: EXP-001 | responsavel: Luca | retirada: 07/09/2026 20:24 | devolucao prevista: 10/09/2026 02:24 | devolucao efetiva: 10/09/2026 00:24
- PesquisadorRepository.buscarPorArea: Pesquisador 1 | nome: Luca | area: Bioespeleologia | titulacao: Mestrando | bolsa: 180.00
- PesquisadorRepository.buscarComColetas: Pesquisador 1 | nome: Luca | area: Bioespeleologia | titulacao: Mestrando | bolsa: 180.00
- PesquisadorRepository.buscarPorBolsaAcimaDaMedia: Pesquisador 1 | nome: Luca | area: Bioespeleologia | titulacao: Mestrando | bolsa: 180.00
- PlanoSegurancaRepository.buscarPorNecessidadeEquipeMedica: Plano 1 | expedicao: EXP-001 | ponto encontro: Base externa - EXP-001 | equipe medica: nao
- RelatorioFinalRepository.buscarPorSituacao: Relatorio 1 | titulo: Relatorio Ametista | expedicao: EXP-001 | situacao: APROVADO | paginas: 12
- SetorPesquisaRepository.buscarPorCavernaESituacao: Setor 1 | denominacao: Salao Principal | caverna: Gruta do Cristal | dificuldade: BAIXO | situacao: SECO
```

## Relacao com as opcoes individuais

As opcoes de `1` a `18` permitem executar cada busca separadamente pelo menu. A opcao `19` centraliza essas mesmas buscas em um unico relatorio, usando uma amostra de cada consulta.
