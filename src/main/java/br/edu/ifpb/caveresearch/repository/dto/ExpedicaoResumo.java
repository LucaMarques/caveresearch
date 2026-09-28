package br.edu.ifpb.caveresearch.repository.dto;

import br.edu.ifpb.caveresearch.model.enums.SituacaoExpedicao;

import java.time.LocalDateTime;

public record ExpedicaoResumo(
        Long idExpedicao,
        String codigo,
        String titulo,
        String nomeCaverna,
        LocalDateTime inicioPrevisto,
        LocalDateTime terminoPrevisto,
        SituacaoExpedicao situacao
) {
}
