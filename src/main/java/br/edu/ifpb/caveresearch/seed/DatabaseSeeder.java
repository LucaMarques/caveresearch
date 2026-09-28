package br.edu.ifpb.caveresearch.seed;

import br.edu.ifpb.caveresearch.model.embeddable.Endereco;
import br.edu.ifpb.caveresearch.model.embeddable.Localizacao;
import br.edu.ifpb.caveresearch.model.entity.Amostra;
import br.edu.ifpb.caveresearch.model.entity.AutorizacaoAmbiental;
import br.edu.ifpb.caveresearch.model.entity.Caverna;
import br.edu.ifpb.caveresearch.model.entity.ColetaCientifica;
import br.edu.ifpb.caveresearch.model.entity.Equipamento;
import br.edu.ifpb.caveresearch.model.entity.Expedicao;
import br.edu.ifpb.caveresearch.model.entity.GuiaEspeleologia;
import br.edu.ifpb.caveresearch.model.entity.MovimentacaoEquipamento;
import br.edu.ifpb.caveresearch.model.entity.ParticipacaoExpedicao;
import br.edu.ifpb.caveresearch.model.entity.Pesquisador;
import br.edu.ifpb.caveresearch.model.entity.Pessoa;
import br.edu.ifpb.caveresearch.model.entity.PlanoSeguranca;
import br.edu.ifpb.caveresearch.model.entity.RelatorioFinal;
import br.edu.ifpb.caveresearch.model.entity.SetorPesquisa;
import br.edu.ifpb.caveresearch.model.enums.CategoriaAmostra;
import br.edu.ifpb.caveresearch.model.enums.CondicaoConservacao;
import br.edu.ifpb.caveresearch.model.enums.NivelDificuldadeSetor;
import br.edu.ifpb.caveresearch.model.enums.PapelParticipante;
import br.edu.ifpb.caveresearch.model.enums.SituacaoAutorizacao;
import br.edu.ifpb.caveresearch.model.enums.SituacaoEquipamento;
import br.edu.ifpb.caveresearch.model.enums.SituacaoExpedicao;
import br.edu.ifpb.caveresearch.model.enums.SituacaoRelatorio;
import br.edu.ifpb.caveresearch.model.enums.SituacaoSetor;
import br.edu.ifpb.caveresearch.model.enums.SituacaoValidacaoColeta;
import br.edu.ifpb.caveresearch.model.enums.TipoEquipamento;
import jakarta.persistence.EntityManager;
import jakarta.transaction.UserTransaction;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public final class DatabaseSeeder {

    private DatabaseSeeder() {
    }

    public static boolean seed(EntityManager entityManager) {
        if (jaPossuiDados(entityManager)) {
            return false;
        }

        UserTransaction tx = com.arjuna.ats.jta.UserTransaction.userTransaction();

        try {
            tx.begin();
            entityManager.joinTransaction();

            Pesquisador luca = pesquisador("Luca", "00000000001", "Bioespeleologia", "Mestrando", "180.00");
            Pesquisador mariana = pesquisador("Mariana", "00000000002", "Geologia", "Graduanda", "150.00");
            Pesquisador thatyane = pesquisador("Thatyane", "00000000003", "Hidrologia", "Graduanda", "150.00");
            Pesquisador bianca = pesquisador("Bianca", "00000000007", "Microbiologia", "Graduanda", "140.00");
            Pesquisador gabriel = pesquisador("Gabriel", "00000000013", "Cartografia", "Mestrando", "180.00");

            Pesquisador vitor = pesquisador("Vitor", "00000000005", "Apoio de campo", "Graduando", "90.00");
            Pesquisador andrew = pesquisador("Andrew", "00000000006", "Apoio logistico", "Graduando", "90.00");
            Pesquisador ludmilla = pesquisador("Ludmilla", "00000000008", "Saude em campo", "Especialista", "120.00");
            Pesquisador eduardo = pesquisador("Eduardo", "00000000009", "Topografia", "Tecnico", "110.00");
            Pesquisador alan = pesquisador("Alan", "00000000010", "Comunicacao", "Tecnico", "100.00");
            Pesquisador jairo = pesquisador("Jairo", "00000000011", "Seguranca operacional", "Tecnico", "110.00");
            Pesquisador fred = pesquisador("Fred", "00000000015", "Apoio de campo", "Graduando", "85.00");
            Pesquisador ana = pesquisador("Ana", "00000000016", "Apoio de campo", "Graduanda", "85.00");
            Pesquisador carlos = pesquisador("Carlos", "00000000017", "Apoio logistico", "Graduando", "85.00");
            Pesquisador julia = pesquisador("Julia", "00000000018", "Documentacao", "Graduanda", "95.00");
            Pesquisador paula = pesquisador("Paula", "00000000019", "Apoio de campo", "Graduanda", "85.00");

            GuiaEspeleologia ryan = guia("Ryan", "00000000004", 1001, "Avancado", 21);
            GuiaEspeleologia leonidas = guia("Leonidas", "00000000012", 1002, "Intermediario", 13);
            GuiaEspeleologia petronio = guia("Petronio", "00000000014", 1003, "Avancado", 18);

            List<Pessoa> pessoas = List.of(
                    luca, mariana, thatyane, ryan, vitor, andrew, bianca, ludmilla, eduardo,
                    alan, jairo, gabriel, leonidas, petronio, fred, ana, carlos, julia, paula
            );
            pessoas.forEach(entityManager::persist);

            Caverna cavernaCristal = caverna("Gruta do Cristal", "PB-CR-0001", "Joao Pessoa", "PB", "-7.1215000", "-34.8829000");
            Caverna cavernaCalcita = caverna("Caverna da Calcita", "PB-CA-0002", "Cabaceiras", "PB", "-7.4880000", "-36.2860000");
            entityManager.persist(cavernaCristal);
            entityManager.persist(cavernaCalcita);

            SetorPesquisa salaoPrincipal = setor("Salao Principal", NivelDificuldadeSetor.BAIXO, SituacaoSetor.SECO, cavernaCristal);
            SetorPesquisa galeriaUmida = setor("Galeria Umida", NivelDificuldadeSetor.MODERADO, SituacaoSetor.MOLHADO, cavernaCristal);
            SetorPesquisa pocoFundo = setor("Poco Fundo", NivelDificuldadeSetor.ALTO, SituacaoSetor.ALAGADO, cavernaCalcita);
            entityManager.persist(salaoPrincipal);
            entityManager.persist(galeriaUmida);
            entityManager.persist(pocoFundo);

            Equipamento gps = equipamento("EQ-GPS-001", "GPS de mapeamento", TipoEquipamento.NAVEGACAO, "Garmin");
            Equipamento radio = equipamento("EQ-RAD-002", "Radio comunicador", TipoEquipamento.COMUNICACAO, "Motorola");
            Equipamento luximetro = equipamento("EQ-LUX-003", "Luximetro de campo", TipoEquipamento.MEDICAO, "Minipa");
            entityManager.persist(gps);
            entityManager.persist(radio);
            entityManager.persist(luximetro);

            Expedicao grupoAmetista = expedicao(
                    "EXP-001",
                    "Grupo Ametista",
                    cavernaCristal,
                    List.of(salaoPrincipal),
                    SituacaoExpedicao.CONCLUIDA,
                    LocalDateTime.now().minusDays(20),
                    LocalDateTime.now().minusDays(18)
            );
            Expedicao grupoCalcita = expedicao(
                    "EXP-002",
                    "Grupo Calcita",
                    cavernaCristal,
                    List.of(galeriaUmida),
                    SituacaoExpedicao.EM_ANDAMENTO,
                    LocalDateTime.now().minusDays(2),
                    LocalDateTime.now().plusDays(1)
            );
            Expedicao grupoEsmeralda = expedicao(
                    "EXP-003",
                    "Grupo Esmeralda",
                    cavernaCalcita,
                    List.of(pocoFundo),
                    SituacaoExpedicao.PLANEJADA,
                    LocalDateTime.now().plusDays(10),
                    LocalDateTime.now().plusDays(12)
            );

            entityManager.persist(grupoAmetista);
            entityManager.persist(grupoCalcita);
            entityManager.persist(grupoEsmeralda);

            persistirPlanos(entityManager, grupoAmetista, grupoCalcita, grupoEsmeralda);
            persistirAutorizacoes(entityManager, grupoAmetista, grupoCalcita, grupoEsmeralda);
            persistirRelatorios(entityManager, grupoAmetista, grupoCalcita);

            persistirParticipacoes(entityManager, grupoAmetista, luca, ryan, vitor, fred, bianca, alan);
            persistirParticipacoes(entityManager, grupoCalcita, mariana, leonidas, andrew, ludmilla, thatyane);
            persistirParticipacoes(entityManager, grupoEsmeralda, gabriel, petronio, eduardo, jairo, ana, carlos, julia, paula);

            persistirColetasEAmostras(entityManager, grupoAmetista, salaoPrincipal, luca, "AMT");
            persistirColetasEAmostras(entityManager, grupoCalcita, galeriaUmida, thatyane, "CAL");
            persistirColetasEAmostras(entityManager, grupoEsmeralda, pocoFundo, gabriel, "ESM");

            persistirMovimentacao(entityManager, gps, grupoAmetista, luca);
            persistirMovimentacao(entityManager, radio, grupoCalcita, mariana);
            persistirMovimentacao(entityManager, luximetro, grupoEsmeralda, gabriel);

            tx.commit();
            return true;
        } catch (Exception e) {
            try {
                tx.rollback();
            } catch (Exception rollbackException) {
                e.addSuppressed(rollbackException);
            }
            throw new RuntimeException("Erro ao popular o banco com seed", e);
        }
    }

    private static boolean jaPossuiDados(EntityManager entityManager) {
        Long totalPesquisadores = entityManager.createQuery("""
            SELECT COUNT(p)
            FROM Pesquisador p
            """, Long.class).getSingleResult();

        Long totalGuias = entityManager.createQuery("""
            SELECT COUNT(g)
            FROM GuiaEspeleologia g
            """, Long.class).getSingleResult();

        return totalPesquisadores + totalGuias > 0;
    }

    private static Pesquisador pesquisador(String nome, String cpf, String area, String titulacao, String bolsa) {
        return Pesquisador.builder()
                .nome(nome)
                .cpf(cpf)
                .dataNascimento(LocalDate.of(1998, 1, 10).plusDays(Integer.parseInt(cpf.substring(8))))
                .email(nome.toLowerCase() + "@caveresearch.local")
                .telefone("8399" + cpf.substring(7))
                .situacaoAtiva(true)
                .endereco(endereco(nome))
                .registroInstitucional(10000 + Integer.parseInt(cpf.substring(8)))
                .areaPrincipalPesquisa(area)
                .titulacao(titulacao)
                .valorDiarioBolsa(new BigDecimal(bolsa))
                .build();
    }

    private static GuiaEspeleologia guia(String nome, String cpf, Integer credencial, String nivel, int expedicoes) {
        return GuiaEspeleologia.builder()
                .nome(nome)
                .cpf(cpf)
                .dataNascimento(LocalDate.of(1995, 5, 15).plusDays(Integer.parseInt(cpf.substring(8))))
                .email(nome.toLowerCase() + "@caveresearch.local")
                .telefone("8398" + cpf.substring(7))
                .situacaoAtiva(true)
                .endereco(endereco(nome))
                .numeroCredencial(credencial)
                .nivelCertificacao(nivel)
                .dataValidadeCertificacao(LocalDate.now().plusYears(2))
                .qtdExpedicoesConcluidas(expedicoes)
                .build();
    }

    private static Endereco endereco(String nome) {
        int numero = Math.abs(nome.hashCode() % 900) + 100;
        return Endereco.builder()
                .logradouro("Rua das Cavernas")
                .numero(String.valueOf(numero))
                .complemento("Casa " + nome)
                .bairro("Centro")
                .cidade("Joao Pessoa")
                .unidadeFederativa("PB")
                .cep("58000000")
                .build();
    }

    private static Caverna caverna(String nome, String codigo, String municipio, String uf, String latitude, String longitude) {
        return Caverna.builder()
                .nomeOficial(nome)
                .codigoAmbiental(codigo)
                .municipio(municipio)
                .unidadeFederativa(uf)
                .localizacao(Localizacao.builder()
                        .latitude(new BigDecimal(latitude))
                        .longitude(new BigDecimal(longitude))
                        .datumGeodesico("SIRGAS 2000")
                        .build())
                .altitude(new BigDecimal("220.50"))
                .extensaoConhecida(new BigDecimal("1450.2500"))
                .dataUltimaInspecao(LocalDate.now().minusMonths(3))
                .acessoPermitido(true)
                .build();
    }

    private static SetorPesquisa setor(
            String denominacao,
            NivelDificuldadeSetor dificuldade,
            SituacaoSetor situacao,
            Caverna caverna
    ) {
        return SetorPesquisa.builder()
                .denominacao(denominacao)
                .dificuldade(dificuldade)
                .profundidadeMaxima(new BigDecimal("36.20"))
                .extensaoAproximada(new BigDecimal("410.00"))
                .descricao("Setor utilizado para observacao e coleta basica.")
                .riscoInundacao(situacao == SituacaoSetor.ALAGADO || situacao == SituacaoSetor.MOLHADO)
                .situacaoSetor(situacao)
                .caverna(caverna)
                .build();
    }

    private static Equipamento equipamento(String codigo, String nome, TipoEquipamento tipo, String fabricante) {
        return Equipamento.builder()
                .codigoPatrimonial(codigo)
                .nomeEquipamento(nome)
                .tipoEquipamento(tipo)
                .fabricante(fabricante)
                .valorAquisicao(new BigDecimal("850.00"))
                .dataCompra(LocalDate.now().minusYears(1))
                .dataUltimaManutencao(LocalDate.now().minusMonths(2))
                .situacaoEquipamento(SituacaoEquipamento.DISPONIVEL)
                .exigeCalibracao(tipo == TipoEquipamento.MEDICAO)
                .build();
    }

    private static Expedicao expedicao(
            String codigo,
            String titulo,
            Caverna caverna,
            List<SetorPesquisa> setores,
            SituacaoExpedicao situacao,
            LocalDateTime inicio,
            LocalDateTime termino
    ) {
        return Expedicao.builder()
                .codigo(codigo)
                .titulo(titulo)
                .objetivo("Levantamento basico de campo e coleta cientifica.")
                .inicioPrevisto(inicio)
                .terminoPrevisto(termino)
                .orcamentoAprovado(new BigDecimal("3500.00"))
                .custoRealizado(situacao == SituacaoExpedicao.PLANEJADA ? BigDecimal.ZERO : new BigDecimal("2100.00"))
                .quantidadeMaximaParticipantes(8)
                .situacao(situacao)
                .cancelamentoEmergencial(false)
                .caverna(caverna)
                .setores(new ArrayList<>(setores))
                .build();
    }

    private static void persistirPlanos(
            EntityManager entityManager,
            Expedicao grupoAmetista,
            Expedicao grupoCalcita,
            Expedicao grupoEsmeralda
    ) {
        entityManager.persist(plano(grupoAmetista, false));
        entityManager.persist(plano(grupoCalcita, true));
        entityManager.persist(plano(grupoEsmeralda, true));
    }

    private static PlanoSeguranca plano(Expedicao expedicao, boolean equipeMedica) {
        return PlanoSeguranca.builder()
                .procedimentosEvacuacao("Retorno pelo trajeto principal e conferencia nominal no ponto externo.")
                .pontoExternoEncontro("Base externa - " + expedicao.getCodigo())
                .tempoMaximoSemComunicacaoHoras(2)
                .telefoneEmergencia("193")
                .necessitaEquipeMedica(equipeMedica)
                .expedicao(expedicao)
                .build();
    }

    private static void persistirAutorizacoes(
            EntityManager entityManager,
            Expedicao grupoAmetista,
            Expedicao grupoCalcita,
            Expedicao grupoEsmeralda
    ) {
        entityManager.persist(autorizacao(grupoAmetista, "AUT-001", SituacaoAutorizacao.DEFERIDO));
        entityManager.persist(autorizacao(grupoCalcita, "AUT-002", SituacaoAutorizacao.DEFERIDO));
        entityManager.persist(autorizacao(grupoEsmeralda, "AUT-003", SituacaoAutorizacao.SOLICITADO));
    }

    private static AutorizacaoAmbiental autorizacao(Expedicao expedicao, String numero, SituacaoAutorizacao situacao) {
        return AutorizacaoAmbiental.builder()
                .numero(numero)
                .orgaoEmissor("Instituto Ambiental Local")
                .dataEmissao(LocalDate.now().minusMonths(1))
                .dataValidade(LocalDate.now().plusMonths(11))
                .situacao(situacao)
                .observacoes("Autorizacao criada para dados de exemplo.")
                .expedicao(expedicao)
                .build();
    }

    private static void persistirRelatorios(
            EntityManager entityManager,
            Expedicao grupoAmetista,
            Expedicao grupoCalcita
    ) {
        entityManager.persist(relatorio(grupoAmetista, "Relatorio Ametista", SituacaoRelatorio.APROVADO));
        entityManager.persist(relatorio(grupoCalcita, "Relatorio Calcita", SituacaoRelatorio.RASCUNHO));
    }

    private static RelatorioFinal relatorio(Expedicao expedicao, String titulo, SituacaoRelatorio situacao) {
        return RelatorioFinal.builder()
                .titulo(titulo)
                .resumo("Resumo basico da expedicao " + expedicao.getCodigo())
                .dataSubmissao(LocalDate.now().minusDays(5))
                .numeroTotalPaginas(12)
                .situacao(situacao)
                .publicacaoAutorizada(situacao == SituacaoRelatorio.APROVADO)
                .expedicao(expedicao)
                .build();
    }

    private static void persistirParticipacoes(EntityManager entityManager, Expedicao expedicao, Pessoa... pessoas) {
        PapelParticipante[] papeis = {
                PapelParticipante.COORDENADOR,
                PapelParticipante.GUIA_ESPELEOLOGIA,
                PapelParticipante.APOIO_LOGISTICO,
                PapelParticipante.APOIO_LOGISTICO,
                PapelParticipante.PESQUISADOR,
                PapelParticipante.COMUNICACAO,
                PapelParticipante.TOPOGRAFO,
                PapelParticipante.RESPONSAVEL_SEGURANCA
        };

        for (int i = 0; i < pessoas.length; i++) {
            entityManager.persist(ParticipacaoExpedicao.builder()
                    .expedicao(expedicao)
                    .pessoa(pessoas[i])
                    .papel(papeis[Math.min(i, papeis.length - 1)])
                    .dataConfirmacao(LocalDate.now().minusDays(10 - Math.min(i, 8)))
                    .valorDiaria(new BigDecimal("80.00"))
                    .quantidadePrevistaDias(3)
                    .presencaConfirmada(true)
                    .observacoes("Participacao no " + expedicao.getTitulo())
                    .build());
        }
    }

    private static void persistirColetasEAmostras(
            EntityManager entityManager,
            Expedicao expedicao,
            SetorPesquisa setor,
            Pesquisador responsavel,
            String prefixo
    ) {
        ColetaCientifica coleta = ColetaCientifica.builder()
                .dataHoraColeta(expedicao.getInicioPrevisto().plusHours(4))
                .metodoEmpregado("Registro visual e coleta manual")
                .descricaoPonto("Ponto central do setor " + setor.getDenominacao())
                .temperatura(new BigDecimal("23.50"))
                .umidadeRelativa(new BigDecimal("78.20"))
                .profundidade(new BigDecimal("12.00"))
                .observacoes("Coleta criada pelo seed.")
                .situacaoDeValidacao(SituacaoValidacaoColeta.VALIDADA)
                .pesquisadorResponsavel(responsavel)
                .setor(setor)
                .expedicao(expedicao)
                .build();

        entityManager.persist(coleta);

        entityManager.persist(amostra(prefixo + "-ROCHA-01", CategoriaAmostra.ROCHA, CondicaoConservacao.ADEQUADA, coleta));
        entityManager.persist(amostra(prefixo + "-AGUA-02", CategoriaAmostra.AGUA, CondicaoConservacao.COMPROMETIDA, coleta));
    }

    private static Amostra amostra(
            String codigo,
            CategoriaAmostra categoria,
            CondicaoConservacao conservacao,
            ColetaCientifica coleta
    ) {
        return Amostra.builder()
                .codigoCampo(codigo)
                .massaOuVolume(new BigDecimal("1.250"))
                .unidadeMedida(categoria == CategoriaAmostra.AGUA ? "L" : "kg")
                .dataAcondicionamento(LocalDate.now())
                .categoria(categoria)
                .condicaoConservacao(conservacao)
                .materialPerigoso(false)
                .observacoes("Amostra gerada no seed.")
                .coleta(coleta)
                .build();
    }

    private static void persistirMovimentacao(
            EntityManager entityManager,
            Equipamento equipamento,
            Expedicao expedicao,
            Pessoa responsavel
    ) {
        entityManager.persist(MovimentacaoEquipamento.builder()
                .dataHoraRetirada(expedicao.getInicioPrevisto().minusHours(2))
                .dataHoraDevolucaoPrevista(expedicao.getTerminoPrevisto().plusHours(4))
                .dataHoraDevolucaoEfetiva(expedicao.getSituacao() == SituacaoExpedicao.CONCLUIDA
                        ? expedicao.getTerminoPrevisto().plusHours(2)
                        : null)
                .estadoSaida("Funcionando")
                .estadoRetorno(expedicao.getSituacao() == SituacaoExpedicao.CONCLUIDA ? "Funcionando" : null)
                .equipamento(equipamento)
                .expedicao(expedicao)
                .pessoaResponsavel(responsavel)
                .build());
    }
}
