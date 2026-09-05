package br.edu.ifpb.caveresearch.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "tb_pesquisador")
public class Pesquisador extends Pessoa {
    @Column(name = "rg_institucional", nullable = false, unique = true)
    private Integer registroInstitucional;

    @Column(name = "area_pesquisa", nullable = false,)
    private String areaPrincipalPesquisa;

    @Column(name = "titulacao", nullable = false)
    private String titulacao;

    @Column(name = "valor_diario_bolsa", nullable = false, precision = 10, scale = 2)
    private BigDecimal valorDiarioBolsa;

}
