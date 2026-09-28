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

    //crud basicão
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

    //consulta a mais do que o crud

    public List<Caverna> buscaComAcessoPermitido(){
        return entityManager.createQuery("""
        select c from Caverna c where c.acessoPermitido = true
        """, Caverna.class).getResultList();
    }
}
