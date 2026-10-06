package br.edu.ifpb.caveresearch;

import br.edu.ifpb.caveresearch.config.JpaUtil;
import br.edu.ifpb.caveresearch.model.entity.Amostra;
import br.edu.ifpb.caveresearch.model.entity.Caverna;
import br.edu.ifpb.caveresearch.model.entity.ColetaCientifica;
import br.edu.ifpb.caveresearch.model.entity.Equipamento;
import br.edu.ifpb.caveresearch.model.entity.Expedicao;
import br.edu.ifpb.caveresearch.model.entity.MovimentacaoEquipamento;
import br.edu.ifpb.caveresearch.model.entity.ParticipacaoExpedicao;
import br.edu.ifpb.caveresearch.model.entity.Pesquisador;
import br.edu.ifpb.caveresearch.model.entity.PlanoSeguranca;
import br.edu.ifpb.caveresearch.model.entity.RelatorioFinal;
import br.edu.ifpb.caveresearch.model.entity.SetorPesquisa;
import br.edu.ifpb.caveresearch.model.enums.SituacaoExpedicao;
import br.edu.ifpb.caveresearch.model.enums.SituacaoRelatorio;
import br.edu.ifpb.caveresearch.model.enums.SituacaoSetor;
import br.edu.ifpb.caveresearch.repository.AmostraRepository;
import br.edu.ifpb.caveresearch.repository.AutorizacaoAmbientalRepository;
import br.edu.ifpb.caveresearch.repository.CavernaRepository;
import br.edu.ifpb.caveresearch.repository.ColetaRepository;
import br.edu.ifpb.caveresearch.repository.EquipamentoRepository;
import br.edu.ifpb.caveresearch.repository.ExpedicaoRepository;
import br.edu.ifpb.caveresearch.repository.MovimentacaoEquipamentoRepository;
import br.edu.ifpb.caveresearch.repository.PesquisadorRepository;
import br.edu.ifpb.caveresearch.repository.PlanoSegurancaRepository;
import br.edu.ifpb.caveresearch.repository.RelatorioFinalRepository;
import br.edu.ifpb.caveresearch.repository.SetorPesquisaRepository;
import br.edu.ifpb.caveresearch.repository.dto.ExpedicaoResumo;
import br.edu.ifpb.caveresearch.seed.DatabaseSeeder;
import jakarta.persistence.EntityManager;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.function.Function;

public class Application {

    private static final DateTimeFormatter FORMATO_DATA_HORA = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    public static void main(String[] args) {
        EntityManager entityManager = JpaUtil.getEntityManager();
        try (Scanner scanner = new Scanner(System.in)) {

            AmostraRepository amostraRepository = new AmostraRepository(entityManager);
            AutorizacaoAmbientalRepository autorizacaoAmbientalRepository = new AutorizacaoAmbientalRepository(entityManager);
            CavernaRepository cavernaRepository = new CavernaRepository(entityManager);
            ColetaRepository coletaRepository = new ColetaRepository(entityManager);
            EquipamentoRepository equipamentoRepository = new EquipamentoRepository(entityManager);
            ExpedicaoRepository expedicaoRepository = new ExpedicaoRepository(entityManager);
            MovimentacaoEquipamentoRepository movimentacaoEquipamentoRepository = new MovimentacaoEquipamentoRepository(entityManager);
            PesquisadorRepository pesquisadorRepository = new PesquisadorRepository(entityManager);
            PlanoSegurancaRepository planoSegurancaRepository = new PlanoSegurancaRepository(entityManager);
            RelatorioFinalRepository relatorioFinalRepository = new RelatorioFinalRepository(entityManager);
            SetorPesquisaRepository setorPesquisaRepository = new SetorPesquisaRepository(entityManager);

            int opcao;
            do {
                exibirMenu();
                opcao = lerInteiro(scanner, "Opcao: ");

                switch (opcao) {
                    case 1 -> listarExpedicoesPorPeriodoESituacao(scanner, expedicaoRepository);
                    case 2 -> carregarDetalhesExpedicao(scanner, expedicaoRepository);
                    case 3 -> listarColetasPorExpedicao(scanner, coletaRepository);
                    case 4 -> listarAmostrasPorColeta(scanner, amostraRepository);
                    case 5 -> baixarMapaSeguranca(scanner, planoSegurancaRepository);
                    case 6 -> baixarAutorizacaoAmbiental(scanner, autorizacaoAmbientalRepository);
                    case 7 -> baixarRelatorioFinal(scanner, relatorioFinalRepository);
                    case 8 -> buscarCavernaPorId(scanner, cavernaRepository);
                    case 9 -> listarCavernasPorMunicipioEAcesso(scanner, cavernaRepository);
                    case 10 -> listarExpedicoesPorSituacao(scanner, expedicaoRepository);
                    case 11 -> listarEquipamentosDisponiveisPorPeriodo(scanner, equipamentoRepository);
                    case 12 -> listarHistoricoEquipamento(scanner, movimentacaoEquipamentoRepository);
                    case 13 -> listarPesquisadoresPorArea(scanner, pesquisadorRepository);
                    case 14 -> listarPesquisadoresComColetas(pesquisadorRepository);
                    case 15 -> listarPesquisadoresPorBolsaAcimaDaMedia(pesquisadorRepository);
                    case 16 -> listarPlanosPorEquipeMedica(scanner, planoSegurancaRepository);
                    case 17 -> listarRelatoriosPorSituacao(scanner, relatorioFinalRepository);
                    case 18 -> listarSetoresPorCavernaESituacao(scanner, setorPesquisaRepository);
                    case 19 -> gerarRelatorioBuscas(
                            entityManager,
                            amostraRepository,
                            autorizacaoAmbientalRepository,
                            cavernaRepository,
                            coletaRepository,
                            equipamentoRepository,
                            expedicaoRepository,
                            movimentacaoEquipamentoRepository,
                            pesquisadorRepository,
                            planoSegurancaRepository,
                            relatorioFinalRepository,
                            setorPesquisaRepository
                    );
                    case 20 -> popularBanco(entityManager);
                    case 0 -> System.out.println("Programa encerrado.");
                    default -> System.out.println("Opcao invalida.");
                }
            } while (opcao != 0);
        } finally {
            if (entityManager.isOpen()) {
                entityManager.close();
            }
            JpaUtil.close();
        }
    }

    private static void exibirMenu() {
        System.out.println();
        System.out.println("=== Cave Research ===");
        System.out.println("1 - Listar expedicoes por periodo e situacao");
        System.out.println("2 - Carregar detalhes de uma expedicao com participantes");
        System.out.println("3 - Listar coletas de uma expedicao");
        System.out.println("4 - Listar amostras de uma coleta");
        System.out.println("5 - Baixar mapa de seguranca");
        System.out.println("6 - Baixar autorizacao ambiental");
        System.out.println("7 - Baixar relatorio final");
        System.out.println("8 - Buscar caverna por id");
        System.out.println("9 - Listar cavernas por municipio e acesso");
        System.out.println("10 - Listar expedicoes por situacao");
        System.out.println("11 - Listar equipamentos disponiveis por periodo");
        System.out.println("12 - Listar historico de equipamento");
        System.out.println("13 - Listar pesquisadores por area");
        System.out.println("14 - Listar pesquisadores com coletas");
        System.out.println("15 - Listar pesquisadores com bolsa acima da media");
        System.out.println("16 - Listar planos por necessidade de equipe medica");
        System.out.println("17 - Listar relatorios por situacao");
        System.out.println("18 - Listar setores por caverna e situacao");
        System.out.println("19 - Gerar relatorio com uma amostra de cada busca");
        System.out.println("20 - Popular banco com dados iniciais");
        System.out.println("0 - Sair");
    }

    private static void popularBanco(EntityManager entityManager) {
        boolean populou = DatabaseSeeder.seed(entityManager);
        if (populou) {
            System.out.println("Banco populado com dados iniciais.");
        } else {
            System.out.println("Seed ignorado: o banco ja possui pessoas cadastradas.");
        }
    }

    private static void listarExpedicoesPorPeriodoESituacao(Scanner scanner, ExpedicaoRepository repository) {
        PeriodoConsulta periodo = lerPeriodo(scanner);
        SituacaoExpedicao situacao = lerEnum(scanner, SituacaoExpedicao.class, "Situacao da expedicao");
        List<ExpedicaoResumo> expedicoes = repository.listarResumoPorPeriodoESituacao(
                periodo.inicio(),
                periodo.termino(),
                situacao
        );

        exibirLista(expedicoes, "Nenhuma expedicao encontrada.", Application::formatarExpedicaoResumo);
    }

    private static void carregarDetalhesExpedicao(Scanner scanner, ExpedicaoRepository repository) {
        Long idExpedicao = lerLong(scanner, "Id da expedicao: ");
        Optional<Expedicao> expedicaoEncontrada = repository.buscarDetalhesComParticipantes(idExpedicao);

        if (expedicaoEncontrada.isEmpty()) {
            System.out.println("Expedicao nao encontrada.");
            return;
        }

        Expedicao expedicao = expedicaoEncontrada.get();
        System.out.println(formatarExpedicao(expedicao));
        System.out.println("Objetivo: " + expedicao.getObjetivo());
        System.out.println("Participantes:");

        if (expedicao.getParticipacoes().isEmpty()) {
            System.out.println("Nenhum participante cadastrado.");
            return;
        }

        for (ParticipacaoExpedicao participacao : expedicao.getParticipacoes()) {
            System.out.println(
                    "- " + participacao.getPessoa().getNome()
                            + " | papel: " + participacao.getPapel()
                            + " | confirmado: " + formatarBoolean(participacao.isPresencaConfirmada())
            );
        }
    }

    private static void listarColetasPorExpedicao(Scanner scanner, ColetaRepository repository) {
        Long idExpedicao = lerLong(scanner, "Id da expedicao: ");
        exibirLista(
                repository.buscarPorExpedicao(idExpedicao),
                "Nenhuma coleta encontrada.",
                Application::formatarColeta
        );
    }

    private static void listarAmostrasPorColeta(Scanner scanner, AmostraRepository repository) {
        Long idColeta = lerLong(scanner, "Id da coleta: ");
        exibirLista(
                repository.buscarAmostrasPorColeta(idColeta),
                "Nenhuma amostra encontrada.",
                Application::formatarAmostra
        );
    }

    private static void baixarMapaSeguranca(Scanner scanner, PlanoSegurancaRepository repository) {
        Long idExpedicao = lerLong(scanner, "Id da expedicao: ");
        exibirResultadoDownload(
                "Mapa de seguranca",
                repository.baixarMapaRotaPorExpedicao(idExpedicao)
        );
    }

    private static void baixarAutorizacaoAmbiental(Scanner scanner, AutorizacaoAmbientalRepository repository) {
        Long idExpedicao = lerLong(scanner, "Id da expedicao: ");
        exibirResultadoDownload(
                "Autorizacao ambiental",
                repository.baixarArquivoPdfPorExpedicao(idExpedicao)
        );
    }

    private static void baixarRelatorioFinal(Scanner scanner, RelatorioFinalRepository repository) {
        Long idExpedicao = lerLong(scanner, "Id da expedicao: ");
        exibirResultadoDownload(
                "Relatorio final",
                repository.baixarArquivoCompletoPorExpedicao(idExpedicao)
        );
    }

    private static void buscarCavernaPorId(Scanner scanner, CavernaRepository repository) {
        Long idCaverna = lerLong(scanner, "Id da caverna: ");
        Caverna caverna = repository.findById(idCaverna);

        if (caverna == null) {
            System.out.println("Caverna nao encontrada.");
            return;
        }

        System.out.println(formatarCaverna(caverna));
    }

    private static void listarCavernasPorMunicipioEAcesso(Scanner scanner, CavernaRepository repository) {
        String municipio = lerTexto(scanner, "Municipio: ");
        boolean acessoPermitido = lerBoolean(scanner, "Acesso permitido");
        exibirLista(
                repository.buscarPorMunicipioEAcesso(municipio, acessoPermitido),
                "Nenhuma caverna encontrada.",
                Application::formatarCaverna
        );
    }

    private static void listarExpedicoesPorSituacao(Scanner scanner, ExpedicaoRepository repository) {
        SituacaoExpedicao situacao = lerEnum(scanner, SituacaoExpedicao.class, "Situacao da expedicao");
        exibirLista(
                repository.buscarPorSituacao(situacao),
                "Nenhuma expedicao encontrada.",
                Application::formatarExpedicao
        );
    }

    private static void listarEquipamentosDisponiveisPorPeriodo(Scanner scanner, EquipamentoRepository repository) {
        PeriodoConsulta periodo = lerPeriodo(scanner);
        exibirLista(
                repository.equipamentosDisponiveisPorData(periodo.inicio(), periodo.termino()),
                "Nenhum equipamento disponivel encontrado.",
                Application::formatarEquipamento
        );
    }

    private static void listarHistoricoEquipamento(Scanner scanner, MovimentacaoEquipamentoRepository repository) {
        Long idEquipamento = lerLong(scanner, "Id do equipamento: ");
        exibirLista(
                repository.buscarHistoricoPorEquipamento(idEquipamento),
                "Nenhuma movimentacao encontrada.",
                Application::formatarMovimentacao
        );
    }

    private static void listarPesquisadoresPorArea(Scanner scanner, PesquisadorRepository repository) {
        String area = lerTexto(scanner, "Area de pesquisa: ");
        exibirLista(
                repository.buscarPorArea(area),
                "Nenhum pesquisador encontrado.",
                Application::formatarPesquisador
        );
    }

    private static void listarPesquisadoresComColetas(PesquisadorRepository repository) {
        exibirLista(
                repository.buscarComColetas(),
                "Nenhum pesquisador com coletas encontrado.",
                Application::formatarPesquisador
        );
    }

    private static void listarPesquisadoresPorBolsaAcimaDaMedia(PesquisadorRepository repository) {
        exibirLista(
                repository.buscarPorBolsaAcimaDaMedia(),
                "Nenhum pesquisador com bolsa acima da media encontrado.",
                Application::formatarPesquisador
        );
    }

    private static void listarPlanosPorEquipeMedica(Scanner scanner, PlanoSegurancaRepository repository) {
        boolean necessitaEquipeMedica = lerBoolean(scanner, "Necessita equipe medica");
        exibirLista(
                repository.buscarPorNecessidadeEquipeMedica(necessitaEquipeMedica),
                "Nenhum plano de seguranca encontrado.",
                Application::formatarPlanoSeguranca
        );
    }

    private static void listarRelatoriosPorSituacao(Scanner scanner, RelatorioFinalRepository repository) {
        SituacaoRelatorio situacao = lerEnum(scanner, SituacaoRelatorio.class, "Situacao do relatorio");
        exibirLista(
                repository.buscarPorSituacao(situacao),
                "Nenhum relatorio encontrado.",
                Application::formatarRelatorioFinal
        );
    }

    private static void listarSetoresPorCavernaESituacao(Scanner scanner, SetorPesquisaRepository repository) {
        Long idCaverna = lerLong(scanner, "Id da caverna: ");
        SituacaoSetor situacao = lerEnum(scanner, SituacaoSetor.class, "Situacao do setor");
        exibirLista(
                repository.buscarPorCavernaESituacao(idCaverna, situacao),
                "Nenhum setor encontrado.",
                Application::formatarSetorPesquisa
        );
    }

    private static void gerarRelatorioBuscas(
            EntityManager entityManager,
            AmostraRepository amostraRepository,
            AutorizacaoAmbientalRepository autorizacaoAmbientalRepository,
            CavernaRepository cavernaRepository,
            ColetaRepository coletaRepository,
            EquipamentoRepository equipamentoRepository,
            ExpedicaoRepository expedicaoRepository,
            MovimentacaoEquipamentoRepository movimentacaoEquipamentoRepository,
            PesquisadorRepository pesquisadorRepository,
            PlanoSegurancaRepository planoSegurancaRepository,
            RelatorioFinalRepository relatorioFinalRepository,
            SetorPesquisaRepository setorPesquisaRepository
    ) {
        Optional<Caverna> cavernaBase = buscarPrimeiro(entityManager, Caverna.class, "c", "c.idCaverna");
        Optional<Expedicao> expedicaoBase = buscarPrimeiro(entityManager, Expedicao.class, "e", "e.idExpedicao");
        Optional<ColetaCientifica> coletaBase = buscarPrimeiro(entityManager, ColetaCientifica.class, "c", "c.idColeta");
        Optional<Equipamento> equipamentoBase = buscarPrimeiro(entityManager, Equipamento.class, "e", "e.idEquipamento");
        Optional<Pesquisador> pesquisadorBase = buscarPrimeiro(entityManager, Pesquisador.class, "p", "p.idPessoa");
        Optional<PlanoSeguranca> planoBase = buscarPrimeiro(entityManager, PlanoSeguranca.class, "p", "p.idPlanoSeguranca");
        Optional<RelatorioFinal> relatorioBase = buscarPrimeiro(entityManager, RelatorioFinal.class, "r", "r.idRelatorioFinal");
        Optional<SetorPesquisa> setorBase = buscarPrimeiro(entityManager, SetorPesquisa.class, "s", "s.idSetor");
        PeriodoConsulta periodo = periodoParaRelatorio(expedicaoBase);

        System.out.println();
        System.out.println("=== Relatorio de consultas dos repositories ===");

        if (cavernaBase.isPresent()) {
            Caverna caverna = cavernaBase.get();
            exibirOpcionalRelatorio(
                    "CavernaRepository.findById",
                    Optional.ofNullable(cavernaRepository.findById(caverna.getIdCaverna())),
                    Application::formatarCaverna
            );
            exibirPrimeiroRelatorio(
                    "CavernaRepository.buscarPorMunicipioEAcesso",
                    cavernaRepository.buscarPorMunicipioEAcesso(caverna.getMunicipio(), caverna.getAcessoPermitido()),
                    Application::formatarCaverna
            );
        } else {
            exibirSemBaseRelatorio("CavernaRepository.findById", "caverna");
            exibirSemBaseRelatorio("CavernaRepository.buscarPorMunicipioEAcesso", "caverna");
        }

        if (expedicaoBase.isPresent()) {
            Expedicao expedicao = expedicaoBase.get();
            exibirPrimeiroRelatorio(
                    "ExpedicaoRepository.buscarPorSituacao",
                    expedicaoRepository.buscarPorSituacao(expedicao.getSituacao()),
                    Application::formatarExpedicao
            );
            exibirPrimeiroRelatorio(
                    "ExpedicaoRepository.listarResumoPorPeriodoESituacao",
                    expedicaoRepository.listarResumoPorPeriodoESituacao(periodo.inicio(), periodo.termino(), expedicao.getSituacao()),
                    Application::formatarExpedicaoResumo
            );
            exibirOpcionalRelatorio(
                    "ExpedicaoRepository.buscarDetalhesComParticipantes",
                    expedicaoRepository.buscarDetalhesComParticipantes(expedicao.getIdExpedicao()),
                    Application::formatarExpedicao
            );
            exibirPrimeiroRelatorio(
                    "ColetaRepository.buscarPorExpedicao",
                    coletaRepository.buscarPorExpedicao(expedicao.getIdExpedicao()),
                    Application::formatarColeta
            );
            exibirArquivoRelatorio(
                    "AutorizacaoAmbientalRepository.baixarArquivoPdfPorExpedicao",
                    autorizacaoAmbientalRepository.baixarArquivoPdfPorExpedicao(expedicao.getIdExpedicao())
            );
            exibirArquivoRelatorio(
                    "PlanoSegurancaRepository.baixarMapaRotaPorExpedicao",
                    planoSegurancaRepository.baixarMapaRotaPorExpedicao(expedicao.getIdExpedicao())
            );
            exibirArquivoRelatorio(
                    "RelatorioFinalRepository.baixarArquivoCompletoPorExpedicao",
                    relatorioFinalRepository.baixarArquivoCompletoPorExpedicao(expedicao.getIdExpedicao())
            );
        } else {
            exibirSemBaseRelatorio("ExpedicaoRepository.buscarPorSituacao", "expedicao");
            exibirSemBaseRelatorio("ExpedicaoRepository.listarResumoPorPeriodoESituacao", "expedicao");
            exibirSemBaseRelatorio("ExpedicaoRepository.buscarDetalhesComParticipantes", "expedicao");
            exibirSemBaseRelatorio("ColetaRepository.buscarPorExpedicao", "expedicao");
            exibirSemBaseRelatorio("AutorizacaoAmbientalRepository.baixarArquivoPdfPorExpedicao", "expedicao");
            exibirSemBaseRelatorio("PlanoSegurancaRepository.baixarMapaRotaPorExpedicao", "expedicao");
            exibirSemBaseRelatorio("RelatorioFinalRepository.baixarArquivoCompletoPorExpedicao", "expedicao");
        }

        if (coletaBase.isPresent()) {
            exibirPrimeiroRelatorio(
                    "AmostraRepository.buscarAmostrasPorColeta",
                    amostraRepository.buscarAmostrasPorColeta(coletaBase.get().getIdColeta()),
                    Application::formatarAmostra
            );
        } else {
            exibirSemBaseRelatorio("AmostraRepository.buscarAmostrasPorColeta", "coleta");
        }

        exibirPrimeiroRelatorio(
                "EquipamentoRepository.equipamentosDisponiveisPorData",
                equipamentoRepository.equipamentosDisponiveisPorData(periodo.inicio(), periodo.termino()),
                Application::formatarEquipamento
        );

        if (equipamentoBase.isPresent()) {
            exibirPrimeiroRelatorio(
                    "MovimentacaoEquipamentoRepository.buscarHistoricoPorEquipamento",
                    movimentacaoEquipamentoRepository.buscarHistoricoPorEquipamento(equipamentoBase.get().getIdEquipamento()),
                    Application::formatarMovimentacao
            );
        } else {
            exibirSemBaseRelatorio("MovimentacaoEquipamentoRepository.buscarHistoricoPorEquipamento", "equipamento");
        }

        if (pesquisadorBase.isPresent()) {
            exibirPrimeiroRelatorio(
                    "PesquisadorRepository.buscarPorArea",
                    pesquisadorRepository.buscarPorArea(pesquisadorBase.get().getAreaPrincipalPesquisa()),
                    Application::formatarPesquisador
            );
        } else {
            exibirSemBaseRelatorio("PesquisadorRepository.buscarPorArea", "pesquisador");
        }

        exibirPrimeiroRelatorio(
                "PesquisadorRepository.buscarComColetas",
                pesquisadorRepository.buscarComColetas(),
                Application::formatarPesquisador
        );
        exibirPrimeiroRelatorio(
                "PesquisadorRepository.buscarPorBolsaAcimaDaMedia",
                pesquisadorRepository.buscarPorBolsaAcimaDaMedia(),
                Application::formatarPesquisador
        );

        if (planoBase.isPresent()) {
            exibirPrimeiroRelatorio(
                    "PlanoSegurancaRepository.buscarPorNecessidadeEquipeMedica",
                    planoSegurancaRepository.buscarPorNecessidadeEquipeMedica(planoBase.get().isNecessitaEquipeMedica()),
                    Application::formatarPlanoSeguranca
            );
        } else {
            exibirSemBaseRelatorio("PlanoSegurancaRepository.buscarPorNecessidadeEquipeMedica", "plano de seguranca");
        }

        if (relatorioBase.isPresent()) {
            exibirPrimeiroRelatorio(
                    "RelatorioFinalRepository.buscarPorSituacao",
                    relatorioFinalRepository.buscarPorSituacao(relatorioBase.get().getSituacao()),
                    Application::formatarRelatorioFinal
            );
        } else {
            exibirSemBaseRelatorio("RelatorioFinalRepository.buscarPorSituacao", "relatorio final");
        }

        if (setorBase.isPresent()) {
            SetorPesquisa setor = setorBase.get();
            exibirPrimeiroRelatorio(
                    "SetorPesquisaRepository.buscarPorCavernaESituacao",
                    setorPesquisaRepository.buscarPorCavernaESituacao(setor.getCaverna().getIdCaverna(), setor.getSituacaoSetor()),
                    Application::formatarSetorPesquisa
            );
        } else {
            exibirSemBaseRelatorio("SetorPesquisaRepository.buscarPorCavernaESituacao", "setor");
        }
    }

    private static void exibirResultadoDownload(String descricao, Optional<byte[]> arquivo) {
        if (arquivo.isEmpty() || arquivo.get().length == 0) {
            System.out.println(descricao + " nao encontrado ou sem arquivo cadastrado.");
            return;
        }

        System.out.println(descricao + " carregado separadamente com " + arquivo.get().length + " bytes.");
    }

    private static <T> void exibirLista(List<T> itens, String mensagemVazia, Function<T, String> formatador) {
        if (itens.isEmpty()) {
            System.out.println(mensagemVazia);
            return;
        }

        for (T item : itens) {
            System.out.println(formatador.apply(item));
        }
    }

    private static <T> void exibirPrimeiroRelatorio(
            String consulta,
            List<T> resultados,
            Function<T, String> formatador
    ) {
        exibirOpcionalRelatorio(consulta, resultados.stream().findFirst(), formatador);
    }

    private static <T> void exibirOpcionalRelatorio(
            String consulta,
            Optional<T> resultado,
            Function<T, String> formatador
    ) {
        if (resultado.isEmpty()) {
            System.out.println("- " + consulta + ": sem resultado.");
            return;
        }

        System.out.println("- " + consulta + ": " + formatador.apply(resultado.get()));
    }

    private static void exibirArquivoRelatorio(String consulta, Optional<byte[]> arquivo) {
        if (arquivo.isEmpty() || arquivo.get().length == 0) {
            System.out.println("- " + consulta + ": sem arquivo.");
            return;
        }

        System.out.println("- " + consulta + ": " + arquivo.get().length + " bytes.");
    }

    private static void exibirSemBaseRelatorio(String consulta, String entidade) {
        System.out.println("- " + consulta + ": sem " + entidade + " base para consultar.");
    }

    private static <T> Optional<T> buscarPrimeiro(
            EntityManager entityManager,
            Class<T> tipo,
            String alias,
            String ordenacao
    ) {
        List<T> resultados = entityManager
                .createQuery("SELECT " + alias + " FROM " + tipo.getSimpleName() + " " + alias + " ORDER BY " + ordenacao, tipo)
                .setMaxResults(1)
                .getResultList();

        return resultados.stream().findFirst();
    }

    private static PeriodoConsulta periodoParaRelatorio(Optional<Expedicao> expedicaoBase) {
        if (expedicaoBase.isPresent()) {
            Expedicao expedicao = expedicaoBase.get();
            return new PeriodoConsulta(
                    expedicao.getInicioPrevisto().minusDays(1),
                    expedicao.getTerminoPrevisto().plusDays(1)
            );
        }

        return new PeriodoConsulta(LocalDateTime.now().minusYears(10), LocalDateTime.now().plusYears(10));
    }

    private static PeriodoConsulta lerPeriodo(Scanner scanner) {
        while (true) {
            LocalDate dataInicio = lerData(scanner, "Data inicial (AAAA-MM-DD): ");
            LocalDate dataFim = lerData(scanner, "Data final (AAAA-MM-DD): ");

            if (!dataFim.isBefore(dataInicio)) {
                return new PeriodoConsulta(dataInicio.atStartOfDay(), dataFim.plusDays(1).atStartOfDay());
            }

            System.out.println("A data final deve ser igual ou posterior a data inicial.");
        }
    }

    private static String lerTexto(Scanner scanner, String mensagem) {
        System.out.print(mensagem);
        return scanner.nextLine().trim();
    }

    private static Long lerLong(Scanner scanner, String mensagem) {
        while (true) {
            System.out.print(mensagem);
            String entrada = scanner.nextLine();
            try {
                return Long.parseLong(entrada);
            } catch (NumberFormatException e) {
                System.out.println("Informe um numero valido.");
            }
        }
    }

    private static int lerInteiro(Scanner scanner, String mensagem) {
        while (true) {
            System.out.print(mensagem);
            String entrada = scanner.nextLine();
            try {
                return Integer.parseInt(entrada);
            } catch (NumberFormatException e) {
                System.out.println("Informe um numero valido.");
            }
        }
    }

    private static boolean lerBoolean(Scanner scanner, String mensagem) {
        while (true) {
            System.out.print(mensagem + " (s/n): ");
            String entrada = scanner.nextLine().trim();

            if (entrada.equalsIgnoreCase("s") || entrada.equalsIgnoreCase("sim")) {
                return true;
            }
            if (entrada.equalsIgnoreCase("n") || entrada.equalsIgnoreCase("nao")) {
                return false;
            }

            System.out.println("Informe s ou n.");
        }
    }

    private static LocalDate lerData(Scanner scanner, String mensagem) {
        while (true) {
            System.out.print(mensagem);
            String entrada = scanner.nextLine().trim();
            try {
                return LocalDate.parse(entrada);
            } catch (DateTimeParseException e) {
                System.out.println("Informe a data no formato AAAA-MM-DD.");
            }
        }
    }

    private static <E extends Enum<E>> E lerEnum(Scanner scanner, Class<E> enumClass, String mensagem) {
        E[] valores = enumClass.getEnumConstants();

        while (true) {
            System.out.println(mensagem + ":");
            for (int i = 0; i < valores.length; i++) {
                System.out.println((i + 1) + " - " + valores[i]);
            }

            int opcao = lerInteiro(scanner, "Escolha: ");
            if (opcao >= 1 && opcao <= valores.length) {
                return valores[opcao - 1];
            }

            System.out.println("Opcao invalida.");
        }
    }

    private static String formatarExpedicaoResumo(ExpedicaoResumo expedicao) {
        return "Expedicao " + expedicao.idExpedicao()
                + " | codigo: " + expedicao.codigo()
                + " | titulo: " + expedicao.titulo()
                + " | caverna: " + expedicao.nomeCaverna()
                + " | inicio: " + formatarDataHora(expedicao.inicioPrevisto())
                + " | termino: " + formatarDataHora(expedicao.terminoPrevisto())
                + " | situacao: " + expedicao.situacao();
    }

    private static String formatarExpedicao(Expedicao expedicao) {
        return "Expedicao " + expedicao.getIdExpedicao()
                + " | codigo: " + expedicao.getCodigo()
                + " | titulo: " + expedicao.getTitulo()
                + " | caverna: " + expedicao.getCaverna().getNomeOficial()
                + " | inicio: " + formatarDataHora(expedicao.getInicioPrevisto())
                + " | termino: " + formatarDataHora(expedicao.getTerminoPrevisto())
                + " | situacao: " + expedicao.getSituacao();
    }

    private static String formatarColeta(ColetaCientifica coleta) {
        return "Coleta " + coleta.getIdColeta()
                + " | data: " + formatarDataHora(coleta.getDataHoraColeta())
                + " | setor: " + coleta.getSetor().getDenominacao()
                + " | responsavel: " + coleta.getPesquisadorResponsavel().getNome()
                + " | situacao: " + coleta.getSituacaoDeValidacao();
    }

    private static String formatarAmostra(Amostra amostra) {
        return "Amostra " + amostra.getIdAmostra()
                + " | codigo: " + amostra.getCodigoCampo()
                + " | categoria: " + amostra.getCategoria()
                + " | conservacao: " + amostra.getCondicaoConservacao()
                + " | quantidade: " + amostra.getMassaOuVolume() + " " + amostra.getUnidadeMedida()
                + " | perigoso: " + formatarBoolean(amostra.isMaterialPerigoso());
    }

    private static String formatarCaverna(Caverna caverna) {
        return "Caverna " + caverna.getIdCaverna()
                + " | nome: " + caverna.getNomeOficial()
                + " | municipio: " + caverna.getMunicipio()
                + " | uf: " + caverna.getUnidadeFederativa()
                + " | acesso: " + formatarBoolean(caverna.getAcessoPermitido());
    }

    private static String formatarEquipamento(Equipamento equipamento) {
        return "Equipamento " + equipamento.getIdEquipamento()
                + " | codigo: " + equipamento.getCodigoPatrimonial()
                + " | nome: " + equipamento.getNomeEquipamento()
                + " | tipo: " + equipamento.getTipoEquipamento()
                + " | situacao: " + equipamento.getSituacaoEquipamento();
    }

    private static String formatarMovimentacao(MovimentacaoEquipamento movimentacao) {
        return "Movimentacao " + movimentacao.getIdMovimentacao()
                + " | equipamento: " + movimentacao.getEquipamento().getNomeEquipamento()
                + " | expedicao: " + movimentacao.getExpedicao().getCodigo()
                + " | responsavel: " + movimentacao.getPessoaResponsavel().getNome()
                + " | retirada: " + formatarDataHora(movimentacao.getDataHoraRetirada())
                + " | devolucao prevista: " + formatarDataHora(movimentacao.getDataHoraDevolucaoPrevista())
                + " | devolucao efetiva: " + formatarDataHora(movimentacao.getDataHoraDevolucaoEfetiva());
    }

    private static String formatarPesquisador(Pesquisador pesquisador) {
        return "Pesquisador " + pesquisador.getIdPessoa()
                + " | nome: " + pesquisador.getNome()
                + " | area: " + pesquisador.getAreaPrincipalPesquisa()
                + " | titulacao: " + pesquisador.getTitulacao()
                + " | bolsa: " + pesquisador.getValorDiarioBolsa();
    }

    private static String formatarPlanoSeguranca(PlanoSeguranca planoSeguranca) {
        return "Plano " + planoSeguranca.getIdPlanoSeguranca()
                + " | expedicao: " + planoSeguranca.getExpedicao().getCodigo()
                + " | ponto encontro: " + planoSeguranca.getPontoExternoEncontro()
                + " | equipe medica: " + formatarBoolean(planoSeguranca.isNecessitaEquipeMedica());
    }

    private static String formatarRelatorioFinal(RelatorioFinal relatorioFinal) {
        return "Relatorio " + relatorioFinal.getIdRelatorioFinal()
                + " | titulo: " + relatorioFinal.getTitulo()
                + " | expedicao: " + relatorioFinal.getExpedicao().getCodigo()
                + " | situacao: " + relatorioFinal.getSituacao()
                + " | paginas: " + relatorioFinal.getNumeroTotalPaginas();
    }

    private static String formatarSetorPesquisa(SetorPesquisa setorPesquisa) {
        return "Setor " + setorPesquisa.getIdSetor()
                + " | denominacao: " + setorPesquisa.getDenominacao()
                + " | caverna: " + setorPesquisa.getCaverna().getNomeOficial()
                + " | dificuldade: " + setorPesquisa.getDificuldade()
                + " | situacao: " + setorPesquisa.getSituacaoSetor();
    }

    private static String formatarDataHora(LocalDateTime dataHora) {
        if (dataHora == null) {
            return "nao informada";
        }

        return dataHora.format(FORMATO_DATA_HORA);
    }

    private static String formatarBoolean(boolean valor) {
        return valor ? "sim" : "nao";
    }

    private record PeriodoConsulta(LocalDateTime inicio, LocalDateTime termino) {
    }
}
