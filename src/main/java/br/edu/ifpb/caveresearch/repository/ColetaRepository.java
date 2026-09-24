package br.edu.ifpb.caveresearch.repository;

import br.edu.ifpb.caveresearch.model.entity.ColetaCientifica;
import jakarta.persistence.EntityManager;

import java.util.List;

public class ColetaRepository {

    private final EntityManager entityManager;

    public ColetaRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public List<ColetaCientifica> buscarPorExpedicao(Long idExpedicao) {
        return entityManager.createQuery("""
            SELECT c
            FROM ColetaCientifica c
            JOIN FETCH c.setor
            JOIN FETCH c.pesquisadorResponsavel
            WHERE c.expedicao.id = :idExpedicao
            """, ColetaCientifica.class)
                .setParameter("idExpedicao", idExpedicao)
                .getResultList();
    }
}