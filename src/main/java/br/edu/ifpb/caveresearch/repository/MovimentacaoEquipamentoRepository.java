package br.edu.ifpb.caveresearch.repository;

import br.edu.ifpb.caveresearch.model.entity.MovimentacaoEquipamento;
import jakarta.persistence.EntityManager;

import java.time.LocalDateTime;
import java.util.List;

public class MovimentacaoEquipamentoRepository {

    private final EntityManager entityManager;

    public MovimentacaoEquipamentoRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public List<MovimentacaoEquipamento> buscarHistoricoPorEquipamento(Long idEquipamento) {

        return entityManager.createQuery("""
        SELECT m
        FROM MovimentacaoEquipamento m
        JOIN FETCH m.expedicao
        JOIN FETCH m.pessoaResponsavel
        WHERE m.equipamento.idEquipamento = :idEquipamento
        ORDER BY m.dataHoraRetirada DESC
        """, MovimentacaoEquipamento.class)
            .setParameter("idEquipamento", idEquipamento)
            .getResultList();
    }
}
