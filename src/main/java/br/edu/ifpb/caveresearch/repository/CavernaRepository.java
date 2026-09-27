package br.edu.ifpb.caveresearch.repository;

import br.edu.ifpb.caveresearch.model.entity.Caverna;
import jakarta.persistence.EntityManager;

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
}
