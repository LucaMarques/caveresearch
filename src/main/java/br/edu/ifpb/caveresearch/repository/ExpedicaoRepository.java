package br.edu.ifpb.caveresearch.repository;

import br.edu.ifpb.caveresearch.model.entity.Expedicao;
import br.edu.ifpb.caveresearch.model.enums.SituacaoExpedicao;
import jakarta.persistence.EntityManager;

import java.util.List;

public class ExpedicaoRepository {

    private final EntityManager entityManager;

    public ExpedicaoRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public List<Expedicao> buscarPorSituacao(SituacaoExpedicao situacao) {
        return entityManager.createQuery("""
            SELECT e
            FROM Expedicao e
            LEFT JOIN FETCH e.caverna
            WHERE e.situacao = :situacao
            ORDER BY e.inicioPrevisto
            """, Expedicao.class)
                .setParameter("situacao", situacao)
                .getResultList();
    }
}
