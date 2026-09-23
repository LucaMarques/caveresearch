package br.edu.ifpb.caveresearch.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "tb_pessoa_guia_espeleologia_joined")
public class GuiaEspeleologia extends Pessoa {
    @Column(name = "num_credencial", nullable = false, unique = true)
    private Integer numeroCredencial;

    @Column(name = "nivel_certificacao", nullable = false, length = 50)
    private String nivelCertificacao;

    @Column(name = "data_validade_certificacao", nullable = false)
    private LocalDate dataValidadeCertificacao;

    @Column(name = "qtd_expedicao_concluidas", nullable = false)
    private int qtdExpedicoesConcluidas;
}
