package br.edu.ifpb.caveresearch.crud.controller;

import br.edu.ifpb.caveresearch.model.entity.GuiaEspeleologia;
import jakarta.transaction.UserTransaction;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import java.util.List;

public class GuiaEspeleologiaController {

    private final EntityManagerFactory emf;

    public GuiaEspeleologiaController(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public void criarGuiaEspeleologia(GuiaEspeleologia guia) {
        UserTransaction tx = com.arjuna.ats.jta.UserTransaction.userTransaction();

        EntityManager em = null;

        try {
            tx.begin();

            em = emf.createEntityManager();
            em.joinTransaction();

            em.persist(guia);

            tx.commit();
        } catch (Exception e) {
            try {
                tx.rollback();
            } catch (Exception rollbackException) {
                e.addSuppressed(rollbackException);
            }

            throw new RuntimeException(
                    "Erro ao executar transação JTA", e
            );
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public GuiaEspeleologia buscarPorGuiaEspeleologia(Long id) {
        EntityManager em = emf.createEntityManager();

        try {
            return em.find(GuiaEspeleologia.class, id);
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public boolean atualizarGuiaEspeleologia(Long id, GuiaEspeleologia guiaAtualizado) {
        UserTransaction tx = com.arjuna.ats.jta.UserTransaction.userTransaction();

        EntityManager em = null;

        try {
            tx.begin();

            em = emf.createEntityManager();
            em.joinTransaction();

            GuiaEspeleologia guia = em.find(GuiaEspeleologia.class, id);

            if (guia == null) {
                tx.rollback();
                return false;
            }

            // Atributos herdados de Pessoa
            guia.setNome(guiaAtualizado.getNome());
            guia.setCpf(guiaAtualizado.getCpf());
            guia.setDataNascimento(guiaAtualizado.getDataNascimento());
            guia.setEmail(guiaAtualizado.getEmail());
            guia.setTelefone(guiaAtualizado.getTelefone());
            guia.setSituacaoAtiva(guiaAtualizado.isSituacaoAtiva());
            guia.setEndereco(guiaAtualizado.getEndereco());

            // Atributos específicos de GuiaEspeleologia
            guia.setNumeroCredencial(guiaAtualizado.getNumeroCredencial());
            guia.setNivelCertificacao(guiaAtualizado.getNivelCertificacao());
            guia.setDataValidadeCertificacao(guiaAtualizado.getDataValidadeCertificacao());
            guia.setQtdExpedicoesConcluidas(guiaAtualizado.getQtdExpedicoesConcluidas());

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

    public boolean apagarGuiaEspeleologia(Long id) {
        UserTransaction tx = com.arjuna.ats.jta.UserTransaction.userTransaction();

        EntityManager em = null;

        try {
            tx.begin();

            em = emf.createEntityManager();
            em.joinTransaction();

            GuiaEspeleologia guia = em.find(GuiaEspeleologia.class, id);

            if (guia == null) {
                tx.rollback();
                return false;
            }

            em.remove(guia);

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

    public List<GuiaEspeleologia> listarTodosGuiasEspeleologia() {
        EntityManager em = emf.createEntityManager();

        try {
            return em.createQuery(
                    """
                    select g
                    from GuiaEspeleologia g
                    order by g.idPessoa
                    """,
                    GuiaEspeleologia.class
            ).getResultList();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }
}
