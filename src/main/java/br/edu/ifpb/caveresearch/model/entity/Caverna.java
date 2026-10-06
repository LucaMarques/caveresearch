package br.edu.ifpb.caveresearch.model.entity;

import br.edu.ifpb.caveresearch.model.embeddable.Localizacao;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "tb_caverna")
public class Caverna {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_caverna", nullable = false)
    private Long idCaverna;

    @Column(name = "nome", nullable = false, length = 150)
    private String nomeOficial;

    @Column(name = "cod_cadastro_ambiental", nullable = false, unique = true, length = 41)
    private String codigoAmbiental;

    @Column(name = "municipio", nullable = false, length = 30)
    private String municipio;

    @Column(name = "UF", nullable = false, length = 2)
    private String unidadeFederativa;

    @Embedded
    private Localizacao localizacao;

    @Column(name = "altitude", nullable = false, precision = 8, scale = 2)
    private BigDecimal altitude;

    @Column(name = "extensao_conhecida", nullable = false, precision = 12, scale = 4)
    private BigDecimal extensaoConhecida;

    @Column(name = "data_ultima_inspecao")
    private LocalDate dataUltimaInspecao;

    @Column(name = "acesso_permitido", nullable = false)
    private Boolean acessoPermitido;

    @Builder.Default
    @OneToMany(mappedBy = "caverna", fetch = FetchType.LAZY)
    private List<Expedicao> expedicoes = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "caverna", fetch = FetchType.LAZY)
    private List<SetorPesquisa> setores = new ArrayList<>();

    public void addExpedicao(Expedicao expedicao) {
        if (expedicao != null && this.expedicoes != null) {
            this.expedicoes.add(expedicao);
            expedicao.setCaverna(this);
        }
    }

    public void addSetor(SetorPesquisa setor) {
        if (setor != null && this.setores != null) {
            this.setores.add(setor);
            setor.setCaverna(this);
        }
    }
}
