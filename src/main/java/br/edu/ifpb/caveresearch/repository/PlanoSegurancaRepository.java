package br.edu.ifpb.caveresearch.repository;

import br.edu.ifpb.caveresearch.model.entity.PlanoSeguranca;
import jakarta.persistence.EntityManager;

import java.util.List;
import java.util.Optional;

public class PlanoSegurancaRepository {

    private final EntityManager entityManager;

    public PlanoSegurancaRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public List<PlanoSeguranca> buscarPorNecessidadeEquipeMedica(boolean necessitaEquipeMedica) {
        return entityManager.createQuery("""
            SELECT p
            FROM PlanoSeguranca p
            JOIN FETCH p.expedicao e
            WHERE p.necessitaEquipeMedica = :necessitaEquipeMedica
            ORDER BY e.inicioPrevisto
            """, PlanoSeguranca.class)
                .setParameter("necessitaEquipeMedica", necessitaEquipeMedica)
                .getResultList();
    }

    public Optional<byte[]> baixarMapaRotaPorExpedicao(Long idExpedicao) {
        List<byte[]> mapas = entityManager.createQuery("""
            SELECT p.mapaRota
            FROM PlanoSeguranca p
            WHERE p.expedicao.idExpedicao = :idExpedicao
            """, byte[].class)
                .setParameter("idExpedicao", idExpedicao)
                .getResultList();

        if (mapas.isEmpty()) {
            return Optional.empty();
        }

        return Optional.ofNullable(mapas.get(0));
    }
}
