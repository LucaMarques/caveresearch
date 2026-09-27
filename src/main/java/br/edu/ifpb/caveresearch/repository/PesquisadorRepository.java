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

    public static void listarPesquisador(EntityManager em, String area) {
        TypedQuery<Pesquisador> consultaPesquisador = em.createQuery(
                "select p " +
                        "from Pesquisador p " +
                        "where lower(p.areaPrincipalPesquisa) like lower(:area)", Pesquisador.class);
        consultaPesquisador.setParameter("area", "%" + area + "%");
        List<Pesquisador> pesquisadorList = consultaPesquisador.getResultList();
        for (Pesquisador p : pesquisadorList) {
            System.out.println(p);
        }
    }

    public static void pesquisadoresColetas(EntityManager em) {
        TypedQuery<Pesquisador> consultaPesquisadorColeta = em.createQuery(
                "select p " +
                        "from Pesquisador p " +
                        "where p.coletaCientificas is not empty ", Pesquisador.class);

        List<Pesquisador> listaPesquisadores = consultaPesquisadorColeta.getResultList();

        for (Pesquisador p : listaPesquisadores) {
            System.out.println(p);
        }
    }
}
