package br.edu.ifpb.caveresearch.repository;

import br.edu.ifpb.caveresearch.model.entity.Equipamento;
import br.edu.ifpb.caveresearch.model.enums.*;
import jakarta.persistence.EntityManager;

import java.time.LocalDateTime;
import java.util.List;

public class EquipamentoRepository {

  private EntityManager entityManager;

  public EquipamentoRepository(EntityManager entityManager){
    this.entityManager = entityManager;
  }


  public List<Equipamento> equipamentosDisponiveisPorData(
      LocalDateTime dataInicio,
      LocalDateTime dataFim) {

    return entityManager.createQuery("""
        SELECT e
        FROM Equipamento e
        WHERE e.situacaoEquipamento = :situacao
        AND NOT EXISTS (
            SELECT m
            FROM MovimentacaoEquipamento m
            WHERE m.equipamento = e
              AND m.dataHoraRetirada < :dataFim
              AND (
                    m.dataHoraDevolucaoEfetiva IS NULL
                    OR m.dataHoraDevolucaoEfetiva > :dataInicio
                  )
        )
        """, Equipamento.class)
        .setParameter("situacao", SituacaoEquipamento.DISPONIVEL)
        .setParameter("dataInicio", dataInicio)
        .setParameter("dataFim", dataFim)
        .getResultList();
  }




}
