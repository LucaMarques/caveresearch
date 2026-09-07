package br.edu.ifpb.caveresearch.repository;

import br.edu.ifpb.caveresearch.entity.Amostra;
import jakarta.persistence.EntityManager;

import java.util.List;

public class AmostraRepository {

    private final EntityManager entityManager;

    public AmostraRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public List<Amostra> buscarAmostrasPorColeta(Long idColeta){
        return entityManager.createQuery("""
            SELECT a
            FROM Amostra a
            WHERE a.coleta.id = :idColeta
            """, Amostra.class)
                .setParameter("idColeta", idColeta)
                .getResultList();
    }
}
