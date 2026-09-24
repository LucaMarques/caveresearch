package br.edu.ifpb.caveresearch.crud.controller;

import br.edu.ifpb.caveresearch.model.entity.ColetaCientifica;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.transaction.UserTransaction;

import java.util.List;

public class ColetaController {
    private final EntityManagerFactory emf;

    public ColetaController(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public void criar(ColetaCientifica coleta) {
        UserTransaction tx = com.arjuna.ats.jta.UserTransaction.userTransaction();
        EntityManager em = null;

        try{
            tx.begin();

            em = emf.createEntityManager();
            em.joinTransaction();

            em.persist(coleta);

            tx.commit();
        } catch (Exception e) {
            try {
                tx.rollback();
            } catch (Exception rollbackException) {
                e.addSuppressed(rollbackException);
            }
            throw new RuntimeException("Erro ao executar transação JTA", e);
        } finally {
            em.close();
        }
    }

    public ColetaCientifica buscarPorId(Long id) {
        EntityManager em =  emf.createEntityManager();
        try {
            return em.find(ColetaCientifica.class, id);
        } finally {
            em.close();
        }
    }

    public boolean atualizar(Long id, ColetaCientifica coletaAtualizada) {
        UserTransaction tx = com.arjuna.ats.jta.UserTransaction.userTransaction();
        EntityManager em = null;

        try{
            tx.begin();

            em = emf.createEntityManager();
            em.joinTransaction();

            ColetaCientifica coleta = em.find(ColetaCientifica.class, id);

            if (coleta == null) {
                tx.rollback();
                return false;
            }

            coleta.setDataHoraColeta(coletaAtualizada.getDataHoraColeta());
            coleta.setMetodoEmpregado(coletaAtualizada.getMetodoEmpregado());
            coleta.setAmostras(coletaAtualizada.getAmostras());
            coleta.setDescricaoPonto(coletaAtualizada.getDescricaoPonto());
            coleta.setTemperatura(coletaAtualizada.getTemperatura());
            coleta.setUmidadeRelativa(coletaAtualizada.getUmidadeRelativa());
            coleta.setProfundidade(coletaAtualizada.getProfundidade());
            coleta.setObservacoes(coletaAtualizada.getObservacoes());
            coleta.setSituacaoDeValidacao(coletaAtualizada.getSituacaoDeValidacao());
            coleta.setPesquisadorResponsavel(coletaAtualizada.getPesquisadorResponsavel());
            coleta.setSetor(coletaAtualizada.getSetor());
            coleta.setExpedicao(coletaAtualizada.getExpedicao());

            tx.commit();
            return true;
        } catch (Exception e) {
            try {
                tx.rollback();
            } catch (Exception rollbackException) {
                e.addSuppressed(rollbackException);
            }

            throw new RuntimeException("Erro ao executar transação JTA", e);
        } finally {
            em.close();
        }
    }

    public boolean apagar(Long id) {
        UserTransaction tx = com.arjuna.ats.jta.UserTransaction.userTransaction();
        EntityManager em = null;

        try {
            tx.begin();

            em = emf.createEntityManager();
            em.joinTransaction();

            ColetaCientifica coleta = em.find(ColetaCientifica.class, id);

            if (coleta == null) {
                tx.rollback();
                return false;
            }

            em.remove(coleta);
            tx.commit();
            return true;
        } catch (Exception e) {
            try {
                tx.rollback();
            } catch (Exception rollbackException) {
                e.addSuppressed(rollbackException);
            }
            throw new RuntimeException("Erro ao executar transação JTA", e);
        } finally {
            em.close();
        }
    }

    public List<ColetaCientifica> listarTodos() {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery(
                    "select c from ColetaCientifica c order by c.idColeta",
                    ColetaCientifica.class).getResultList();
        } finally {
            em.close();
        }
    }
}
