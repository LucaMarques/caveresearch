package br.edu.ifpb.caveresearch.repository;

import br.edu.ifpb.caveresearch.model.entity.Pesquisador;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class PesquisadorRepository {
    private final EntityManager entityManager;

    public PesquisadorRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public List<Pesquisador> buscarPorArea(String area) {
        TypedQuery<Pesquisador> consultaPesquisador = entityManager.createQuery(
                "select p " +
                        "from Pesquisador p " +
                        "where lower(p.areaPrincipalPesquisa) like lower(:area)", Pesquisador.class);
        consultaPesquisador.setParameter("area", "%" + area + "%");
        return consultaPesquisador.getResultList();
    }

    public List<Pesquisador> buscarComColetas() {
        TypedQuery<Pesquisador> consultaPesquisadorColeta = entityManager.createQuery(
                "select p " +
                        "from Pesquisador p " +
                        "where p.coletaCientificas is not empty ", Pesquisador.class);
        return consultaPesquisadorColeta.getResultList();
    }

    public List<Pesquisador> buscarPorBolsaAcimaDaMedia() {
        TypedQuery<Pesquisador> consulta = entityManager.createNamedQuery(
                "Pesquisador.listarPesquisadorPorBolsa",
                Pesquisador.class
        );
        return consulta.getResultList();
    }

    // Lista todos os pesquisadores por area
    public static void listarPesquisador(EntityManager em, String area) {
        List<Pesquisador> pesquisadorList = new PesquisadorRepository(em).buscarPorArea(area);
        for (Pesquisador p : pesquisadorList) {
            System.out.println(p);
        }
    }

    // Lista pesquisadores que fizeram coletas
    public static void pesquisadoresColetas(EntityManager em) {
        List<Pesquisador> listaPesquisadores = new PesquisadorRepository(em).buscarComColetas();

        for (Pesquisador p : listaPesquisadores) {
            System.out.println(p);
        }
    }

    public static void pesquisadorMediaBolsa(EntityManager em) {
        List<Pesquisador> lista = new PesquisadorRepository(em).buscarPorBolsaAcimaDaMedia();

        for (Pesquisador p : lista) {
            System.out.println(p);
        }
    }
}
