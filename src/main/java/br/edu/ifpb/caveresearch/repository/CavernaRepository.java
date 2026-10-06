package br.edu.ifpb.caveresearch.repository;

import br.edu.ifpb.caveresearch.model.entity.Caverna;
import jakarta.persistence.EntityManager;

import java.util.ArrayList;
import java.util.List;

public class CavernaRepository {

    private final EntityManager entityManager;

    public CavernaRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void salvar(Caverna caverna) {
        entityManager.persist(caverna);
    }

    public Caverna findById(Long id) {
        return entityManager.find(Caverna.class, id);
    }

    public Caverna atualizar(Caverna caverna) {
        return entityManager.merge(caverna);
    }

    public void remover(Caverna caverna) {
        entityManager.remove(caverna);
    }

    public List<Caverna> buscarPorMunicipioEAcesso(
        String municipio,
        Boolean acessoPermitido) {

        return entityManager.createQuery("""
        SELECT c
        FROM Caverna c
        WHERE c.municipio = :municipio
          AND c.acessoPermitido = :acessoPermitido
        ORDER BY c.nomeOficial
        """, Caverna.class)
            .setParameter("municipio", municipio)
            .setParameter("acessoPermitido", acessoPermitido)
            .getResultList();
    }



}
