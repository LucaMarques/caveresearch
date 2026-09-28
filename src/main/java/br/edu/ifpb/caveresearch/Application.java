package br.edu.ifpb.caveresearch;

import br.edu.ifpb.caveresearch.config.JpaUtil;
import br.edu.ifpb.caveresearch.model.entity.Amostra;
import br.edu.ifpb.caveresearch.model.entity.ColetaCientifica;
import br.edu.ifpb.caveresearch.model.entity.Expedicao;
import br.edu.ifpb.caveresearch.model.entity.ParticipacaoExpedicao;
import br.edu.ifpb.caveresearch.model.enums.SituacaoExpedicao;
import br.edu.ifpb.caveresearch.repository.AmostraRepository;
import br.edu.ifpb.caveresearch.repository.AutorizacaoAmbientalRepository;
import br.edu.ifpb.caveresearch.repository.ColetaRepository;
import br.edu.ifpb.caveresearch.repository.ExpedicaoRepository;
import br.edu.ifpb.caveresearch.repository.PlanoSegurancaRepository;
import br.edu.ifpb.caveresearch.repository.RelatorioFinalRepository;
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

public class Application {

    private static final DateTimeFormatter FORMATO_DATA_HORA = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    public static void main(String[] args) {
        EntityManager entityManager = JpaUtil.getEntityManager();
        try (Scanner scanner = new Scanner(System.in)) {

            AmostraRepository amostraRepository = new AmostraRepository(entityManager);
            AutorizacaoAmbientalRepository autorizacaoAmbientalRepository = new AutorizacaoAmbientalRepository(entityManager);
            ColetaRepository coletaRepository = new ColetaRepository(entityManager);
            ExpedicaoRepository expedicaoRepository = new ExpedicaoRepository(entityManager);
            PlanoSegurancaRepository planoSegurancaRepository = new PlanoSegurancaRepository(entityManager);
            RelatorioFinalRepository relatorioFinalRepository = new RelatorioFinalRepository(entityManager);

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
                    case 8 -> popularBanco(entityManager);
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
        System.out.println("8 - Popular banco com dados iniciais");
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

        if (expedicoes.isEmpty()) {
            System.out.println("Nenhuma expedicao encontrada.");
            return;
        }

        for (ExpedicaoResumo expedicao : expedicoes) {
            System.out.println(
                    "Expedicao " + expedicao.idExpedicao()
                            + " | codigo: " + expedicao.codigo()
                            + " | titulo: " + expedicao.titulo()
                            + " | caverna: " + expedicao.nomeCaverna()
                            + " | inicio: " + formatarDataHora(expedicao.inicioPrevisto())
                            + " | termino: " + formatarDataHora(expedicao.terminoPrevisto())
                            + " | situacao: " + expedicao.situacao()
            );
        }
    }

    private static void carregarDetalhesExpedicao(Scanner scanner, ExpedicaoRepository repository) {
        Long idExpedicao = lerLong(scanner, "Id da expedicao: ");
        Optional<Expedicao> expedicaoEncontrada = repository.buscarDetalhesComParticipantes(idExpedicao);

        if (expedicaoEncontrada.isEmpty()) {
            System.out.println("Expedicao nao encontrada.");
            return;
        }

        Expedicao expedicao = expedicaoEncontrada.get();
        System.out.println(
                "Expedicao " + expedicao.getIdExpedicao()
                        + " | codigo: " + expedicao.getCodigo()
                        + " | titulo: " + expedicao.getTitulo()
                        + " | caverna: " + expedicao.getCaverna().getNomeOficial()
                        + " | inicio: " + formatarDataHora(expedicao.getInicioPrevisto())
                        + " | termino: " + formatarDataHora(expedicao.getTerminoPrevisto())
                        + " | situacao: " + expedicao.getSituacao()
        );
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
                            + " | confirmado: " + participacao.isPresencaConfirmada()
            );
        }
    }

    private static void listarColetasPorExpedicao(Scanner scanner, ColetaRepository repository) {
        Long idExpedicao = lerLong(scanner, "Id da expedicao: ");
        List<ColetaCientifica> coletas = repository.buscarPorExpedicao(idExpedicao);

        if (coletas.isEmpty()) {
            System.out.println("Nenhuma coleta encontrada.");
            return;
        }

        for (ColetaCientifica coleta : coletas) {
            System.out.println(
                    "Coleta " + coleta.getIdColeta()
                            + " | data: " + formatarDataHora(coleta.getDataHoraColeta())
                            + " | setor: " + coleta.getSetor().getDenominacao()
                            + " | responsavel: " + coleta.getPesquisadorResponsavel().getNome()
                            + " | situacao: " + coleta.getSituacaoDeValidacao()
            );
        }
    }

    private static void listarAmostrasPorColeta(Scanner scanner, AmostraRepository repository) {
        Long idColeta = lerLong(scanner, "Id da coleta: ");
        List<Amostra> amostras = repository.buscarAmostrasPorColeta(idColeta);

        if (amostras.isEmpty()) {
            System.out.println("Nenhuma amostra encontrada.");
            return;
        }

        for (Amostra amostra : amostras) {
            System.out.println(
                    "Amostra " + amostra.getIdAmostra()
                            + " | codigo: " + amostra.getCodigoCampo()
                            + " | categoria: " + amostra.getCategoria()
                            + " | conservacao: " + amostra.getCondicaoConservacao()
                            + " | quantidade: " + amostra.getMassaOuVolume() + " " + amostra.getUnidadeMedida()
                            + " | perigoso: " + amostra.isMaterialPerigoso()
            );
        }
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

    private static void exibirResultadoDownload(String descricao, Optional<byte[]> arquivo) {
        if (arquivo.isEmpty() || arquivo.get().length == 0) {
            System.out.println(descricao + " nao encontrado ou sem arquivo cadastrado.");
            return;
        }

        System.out.println(descricao + " carregado separadamente com " + arquivo.get().length + " bytes.");
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

    private static String formatarDataHora(LocalDateTime dataHora) {
        return dataHora.format(FORMATO_DATA_HORA);
    }

    private record PeriodoConsulta(LocalDateTime inicio, LocalDateTime termino) {
    }
}
