package br.edu.ifpb.caveresearch.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "tb_pessoa_pesquisador_joined")
public class Pesquisador extends Pessoa {
    @Column(name = "rg_institucional", nullable = false, unique = true)
    private Integer registroInstitucional;

    @Column(name = "area_pesquisa", nullable = false, length = 100)
    private String areaPrincipalPesquisa;

    @Column(name = "titulacao", nullable = false, length = 50)
    private String titulacao;

    @Column(name = "valor_diario_bolsa", nullable = false, precision = 10, scale = 2)
    private BigDecimal valorDiarioBolsa;

    @OneToMany(mappedBy = "pesquisadorResponsavel", fetch = FetchType.LAZY)
    private List<ColetaCientifica> coletaCientificas = new ArrayList<>();
}
