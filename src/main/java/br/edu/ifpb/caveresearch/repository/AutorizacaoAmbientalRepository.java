package br.edu.ifpb.caveresearch.repository;

import jakarta.persistence.EntityManager;

import java.util.List;
import java.util.Optional;

public class AutorizacaoAmbientalRepository {

    private final EntityManager entityManager;

    public AutorizacaoAmbientalRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public Optional<byte[]> baixarArquivoPdfPorExpedicao(Long idExpedicao) {
        List<byte[]> arquivos = entityManager.createQuery("""
            SELECT a.arquivoPdfAssinado
            FROM AutorizacaoAmbiental a
            WHERE a.expedicao.idExpedicao = :idExpedicao
            """, byte[].class)
                .setParameter("idExpedicao", idExpedicao)
                .getResultList();

        if (arquivos.isEmpty()) {
            return Optional.empty();
        }

        return Optional.ofNullable(arquivos.get(0));
    }
}
