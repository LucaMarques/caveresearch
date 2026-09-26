package br.edu.ifpb.caveresearch.crud.controller;

import br.edu.ifpb.caveresearch.model.entity.Pesquisador;
import jakarta.transaction.UserTransaction;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import java.util.List;

public class PesquisadorController {
    private final EntityManagerFactory emf;

    public PesquisadorController(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public void criarPesquisador(Pesquisador pesquisador) {
        UserTransaction tx = com.arjuna.ats.jta.UserTransaction.userTransaction();

        EntityManager em = null;

        try {
            tx.begin();

            em = emf.createEntityManager();
            em.joinTransaction();

            em.persist(pesquisador);

            tx.commit();
        } catch (Exception e) {
            try {
                tx.rollback();
            } catch (Exception rollbackException){
                e.addSuppressed(rollbackException);
            }

            throw new RuntimeException("Erro ao executar transação JTA", e);
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public Pesquisador buscarPorPesquisador(Long id) {
        EntityManager em = emf.createEntityManager();

        try {
            return em.find(Pesquisador.class, id);
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public boolean atualizarPesquisador(Long id, Pesquisador pesquisadorAtualizado) {
        UserTransaction tx = com.arjuna.ats.jta.UserTransaction.userTransaction();

        EntityManager em = null;

        try {
            tx.begin();

            em = emf.createEntityManager();
            em.joinTransaction();

            Pesquisador pesquisador = em.find(Pesquisador.class, id);

            if (pesquisador == null){
                tx.rollback();
                return false;
            }

            // Atributos herdados de Pessoa
            pesquisador.setNome(pesquisadorAtualizado.getNome());
            pesquisador.setCpf(pesquisadorAtualizado.getCpf());
            pesquisador.setDataNascimento(pesquisadorAtualizado.getDataNascimento());
            pesquisador.setEmail(pesquisadorAtualizado.getEmail());
            pesquisador.setTelefone(pesquisadorAtualizado.getTelefone());
            pesquisador.setSituacaoAtiva(pesquisadorAtualizado.isSituacaoAtiva());
            pesquisador.setEndereco(pesquisadorAtualizado.getEndereco());

            // Atributos específicos de Pesquisador
            pesquisador.setRegistroInstitucional(pesquisadorAtualizado.getRegistroInstitucional());
            pesquisador.setAreaPrincipalPesquisa(pesquisadorAtualizado.getAreaPrincipalPesquisa());
            pesquisador.setTitulacao(pesquisadorAtualizado.getTitulacao());
            pesquisador.setValorDiarioBolsa(pesquisadorAtualizado.getValorDiarioBolsa());

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

    public boolean apagarPesquisador(Long id) {
        UserTransaction tx = com.arjuna.ats.jta.UserTransaction.userTransaction();

        EntityManager em = null;

        try {
            tx.begin();

            em = emf.createEntityManager();
            em.joinTransaction();

            Pesquisador pesquisador = em.find(Pesquisador.class, id);

            if (pesquisador == null) {
                tx.rollback();
                return false;
            }

            em.remove(pesquisador);

            tx.commit();
            return true;

        } catch (Exception e){
            try {
                tx.rollback();
            } catch (Exception rollbackException){
                e.addSuppressed(rollbackException);
            }

            throw new RuntimeException("Erro ao executar transação JTA", e);
        }finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Pesquisador> listarTodosPesquisadores() {
        EntityManager em = emf.createEntityManager();

        try {
            return em.createQuery(
                    """
                    select p
                    from Pesquisador p
                    order by p.idPessoa
                    """,
                    Pesquisador.class
            ).getResultList();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }
}
