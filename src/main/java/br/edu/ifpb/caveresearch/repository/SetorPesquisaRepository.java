package br.edu.ifpb.caveresearch.repository;

import br.edu.ifpb.caveresearch.model.entity.SetorPesquisa;
import br.edu.ifpb.caveresearch.model.enums.SituacaoSetor;
import jakarta.persistence.EntityManager;

import java.util.List;

public class SetorPesquisaRepository {

    private final EntityManager entityManager;

    public SetorPesquisaRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public List<SetorPesquisa> buscarPorCavernaESituacao(Long idCaverna, SituacaoSetor situacao) {
        return entityManager.createQuery("""
            SELECT s
            FROM SetorPesquisa s
            WHERE s.caverna.idCaverna = :idCaverna
              AND s.situacaoSetor = :situacao
            ORDER BY s.denominacao
            """, SetorPesquisa.class)
                .setParameter("idCaverna", idCaverna)
                .setParameter("situacao", situacao)
                .getResultList();
    }
}
