package br.edu.ifpb.caveresearch.repository;

import br.edu.ifpb.caveresearch.model.entity.Expedicao;
import br.edu.ifpb.caveresearch.model.enums.SituacaoExpedicao;
import br.edu.ifpb.caveresearch.repository.dto.ExpedicaoResumo;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

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

    public List<ExpedicaoResumo> listarResumoPorPeriodoESituacao(
            LocalDateTime inicio,
            LocalDateTime termino,
            SituacaoExpedicao situacao
    ) {
        return entityManager
                .createNamedQuery("Expedicao.listarResumoPorPeriodoESituacao", ExpedicaoResumo.class)
                .setParameter("inicio", inicio)
                .setParameter("termino", termino)
                .setParameter("situacao", situacao)
                .getResultList();
    }

    public Optional<Expedicao> buscarDetalhesComParticipantes(Long idExpedicao) {
        try {
            Expedicao expedicao = entityManager
                    .createNamedQuery("Expedicao.carregarDetalhesComParticipantes", Expedicao.class)
                    .setParameter("idExpedicao", idExpedicao)
                    .getSingleResult();
            return Optional.of(expedicao);
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }
}
