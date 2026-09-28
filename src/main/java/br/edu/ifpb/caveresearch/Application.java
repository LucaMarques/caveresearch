package br.edu.ifpb.caveresearch;

import br.edu.ifpb.caveresearch.config.JpaUtil;
import br.edu.ifpb.caveresearch.model.entity.Expedicao;
import br.edu.ifpb.caveresearch.model.entity.PlanoSeguranca;
import br.edu.ifpb.caveresearch.model.entity.RelatorioFinal;
import br.edu.ifpb.caveresearch.model.enums.SituacaoExpedicao;
import br.edu.ifpb.caveresearch.model.enums.SituacaoRelatorio;
import br.edu.ifpb.caveresearch.repository.ExpedicaoRepository;
import br.edu.ifpb.caveresearch.repository.PlanoSegurancaRepository;
import br.edu.ifpb.caveresearch.repository.RelatorioFinalRepository;
import br.edu.ifpb.caveresearch.seed.DatabaseSeeder;
import jakarta.persistence.EntityManager;

import java.util.List;
import java.util.Scanner;

public class Application {

    public static void main(String[] args) {
        EntityManager entityManager = JpaUtil.getEntityManager();
        try (Scanner scanner = new Scanner(System.in)) {

            ExpedicaoRepository expedicaoRepository = new ExpedicaoRepository(entityManager);
            PlanoSegurancaRepository planoSegurancaRepository = new PlanoSegurancaRepository(entityManager);
            RelatorioFinalRepository relatorioFinalRepository = new RelatorioFinalRepository(entityManager);

            int opcao;
            do {
                exibirMenu();
                opcao = lerInteiro(scanner, "Opcao: ");

                switch (opcao) {
                    case 1 -> listarExpedicoesPorSituacao(scanner, expedicaoRepository);
                    case 2 -> listarPlanosPorEquipeMedica(scanner, planoSegurancaRepository);
                    case 3 -> listarRelatoriosPorSituacao(scanner, relatorioFinalRepository);
                    case 4 -> popularBanco(entityManager);
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
        System.out.println("1 - Buscar expedicoes por situacao");
        System.out.println("2 - Buscar planos de seguranca por equipe medica");
        System.out.println("3 - Buscar relatorios finais por situacao");
        System.out.println("4 - Popular banco com dados iniciais");
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

    private static void listarExpedicoesPorSituacao(Scanner scanner, ExpedicaoRepository repository) {
        SituacaoExpedicao situacao = lerEnum(scanner, SituacaoExpedicao.class, "Situacao da expedicao");
        List<Expedicao> expedicoes = repository.buscarPorSituacao(situacao);

        if (expedicoes.isEmpty()) {
            System.out.println("Nenhuma expedicao encontrada.");
            return;
        }

        for (Expedicao expedicao : expedicoes) {
            System.out.println(
                    "Expedicao " + expedicao.getIdExpedicao()
                            + " | codigo: " + expedicao.getCodigo()
                            + " | titulo: " + expedicao.getTitulo()
                            + " | situacao: " + expedicao.getSituacao()
            );
        }
    }

    private static void listarPlanosPorEquipeMedica(Scanner scanner, PlanoSegurancaRepository repository) {
        boolean necessitaEquipeMedica = lerBoolean(scanner, "Necessita equipe medica? (s/n): ");
        List<PlanoSeguranca> planos = repository.buscarPorNecessidadeEquipeMedica(necessitaEquipeMedica);

        if (planos.isEmpty()) {
            System.out.println("Nenhum plano de seguranca encontrado.");
            return;
        }

        for (PlanoSeguranca plano : planos) {
            System.out.println(
                    "Plano " + plano.getIdPlanoSeguranca()
                            + " | expedicao: " + plano.getExpedicao().getCodigo()
                            + " | telefone: " + plano.getTelefoneEmergencia()
                            + " | equipe medica: " + plano.isNecessitaEquipeMedica()
            );
        }
    }

    private static void listarRelatoriosPorSituacao(Scanner scanner, RelatorioFinalRepository repository) {
        SituacaoRelatorio situacao = lerEnum(scanner, SituacaoRelatorio.class, "Situacao do relatorio");
        List<RelatorioFinal> relatorios = repository.buscarPorSituacao(situacao);

        if (relatorios.isEmpty()) {
            System.out.println("Nenhum relatorio final encontrado.");
            return;
        }

        for (RelatorioFinal relatorio : relatorios) {
            System.out.println(
                    "Relatorio " + relatorio.getIdRelatorioFinal()
                            + " | expedicao: " + relatorio.getExpedicao().getCodigo()
                            + " | titulo: " + relatorio.getTitulo()
                            + " | situacao: " + relatorio.getSituacao()
            );
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
            System.out.print(mensagem);
            String entrada = scanner.nextLine().trim();
            if (entrada.equalsIgnoreCase("s")) {
                return true;
            }
            if (entrada.equalsIgnoreCase("n")) {
                return false;
            }
            System.out.println("Informe s ou n.");
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
}
