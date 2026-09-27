package br.edu.ifpb.caveresearch.repository;

import br.edu.ifpb.caveresearch.entity.Caverna;
import jakarta.persistence.EntityManager;

public class CavernaRepository {

 private final EntityManager entityManager;

 public CavernaRepository(EntityManager entityManager){
   this.entityManager = entityManager;
 }

 private void salvar(Caverna caverna){
   entityManager.persist(caverna);
 }

 private Caverna findById(long id){
   return entityManager.find(Caverna.class, id);
 }

 private Caverna atualizar(Caverna  caverna){
   return entityManager.merge(caverna);
 }

 private void remover(Caverna caverna){
   entityManager.remove(caverna);
 }



}


