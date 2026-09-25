package br.edu.ifpb.caveresearch.crud.controller;

import br.edu.ifpb.caveresearch.model.entity.Amostra;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.transaction.UserTransaction;

import java.util.List;

public class AmostraController {
    private final EntityManagerFactory emf;

    public AmostraController(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public void criar(Amostra amostra) {
        UserTransaction tx = com.arjuna.ats.jta.UserTransaction.userTransaction();

        EntityManager em = null;

        try {
            tx.begin();

            em = emf.createEntityManager();
            em.joinTransaction();

            em.persist(amostra);

            tx.commit();
        } catch (Exception e) {
            try {
                tx.rollback();
            } catch (Exception rollbackException) {
                e.addSuppressed((rollbackException));
            }
            throw new RuntimeException("Erro ao executar transação JTA", e);
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public Amostra buscarPorId(Long id) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.find(Amostra.class, id);
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public boolean atualizar(Long id, Amostra amostraAtualizada) {
        UserTransaction tx = com.arjuna.ats.jta.UserTransaction.userTransaction();

        EntityManager em = null;

        try {
            tx.begin();

            em = emf.createEntityManager();

            em.joinTransaction();

            Amostra amostra = em.find(Amostra.class, id);

            if (amostra == null) {
                tx.rollback();
                return false;
            }

            amostra.setCodigoCampo(amostraAtualizada.getCodigoCampo());
            amostra.setMassaOuVolume(amostraAtualizada.getMassaOuVolume());
            amostra.setUnidadeMedida(amostraAtualizada.getUnidadeMedida());
            amostra.setDataAcondicionamento(amostraAtualizada.getDataAcondicionamento());
            amostra.setCategoria(amostraAtualizada.getCategoria());
            amostra.setCondicaoConservacao(amostraAtualizada.getCondicaoConservacao());
            amostra.setFotografia(amostraAtualizada.getFotografia());
            amostra.setMaterialPerigoso(amostraAtualizada.getMaterialPerigoso());
            amostra.setObservacoes(amostraAtualizada.getObservacoes());
            amostra.setColeta(amostraAtualizada.getColeta());

            tx.commit();
            return true;
        } catch (Exception e) {
            try {
                tx.rollback();
            } catch (Exception rollbackException)  {
                e.addSuppressed(rollbackException);
            }
            throw new RuntimeException("Erro ao executar transação JTA", e);
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public boolean apagar(Long id) {
        UserTransaction tx = com.arjuna.ats.jta.UserTransaction.userTransaction();
        EntityManager em = null;

        try {
            tx.begin();

            em = emf.createEntityManager();
            em.joinTransaction();

            Amostra amostra = em.find(Amostra.class, id);

            if (amostra == null) {
                tx.rollback();
                return false;
            }

            em.remove(amostra);
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
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Amostra> listarTodos() {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery(
                    "select a from Amostra a order by c.idAmostra",
                    Amostra.class).getResultList();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }
}
