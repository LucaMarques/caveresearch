package br.edu.ifpb.caveresearch.repository;

import br.edu.ifpb.caveresearch.model.entity.RelatorioFinal;
import br.edu.ifpb.caveresearch.model.enums.SituacaoRelatorio;
import jakarta.persistence.EntityManager;

import java.util.List;
import java.util.Optional;

public class RelatorioFinalRepository {

    private final EntityManager entityManager;

    public RelatorioFinalRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public List<RelatorioFinal> buscarPorSituacao(SituacaoRelatorio situacao) {
        return entityManager.createQuery("""
            SELECT r
            FROM RelatorioFinal r
            JOIN FETCH r.expedicao e
            WHERE r.situacao = :situacao
            ORDER BY r.dataSubmissao DESC
            """, RelatorioFinal.class)
                .setParameter("situacao", situacao)
                .getResultList();
    }

    public Optional<byte[]> baixarArquivoCompletoPorExpedicao(Long idExpedicao) {
        List<byte[]> arquivos = entityManager.createQuery("""
            SELECT r.arquivoCompleto
            FROM RelatorioFinal r
            WHERE r.expedicao.idExpedicao = :idExpedicao
            """, byte[].class)
                .setParameter("idExpedicao", idExpedicao)
                .getResultList();

        if (arquivos.isEmpty()) {
            return Optional.empty();
        }

        return Optional.ofNullable(arquivos.get(0));
    }
}
