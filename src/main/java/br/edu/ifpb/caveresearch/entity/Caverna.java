package br.edu.ifpb.caveresearch.entity;

import br.edu.ifpb.caveresearch.embeddable.*;
import jakarta.persistence.*;
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
@Table(name = "tb_caverna")
public class Caverna {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_caverna", nullable = false)
    private Long id;

    @Column(name = "nome", nullable = false)
    private String nomeOficial;

    @Column(name = "cod_cadastro_ambiental", nullable = false , length = 41)
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

    @Column(name = "data_ultima_inspencao")
    private LocalDate dataUltimaInspencao;

    @Column(name = "acesso_permitido", nullable = false)
    private Boolean acessoPermitido;

    @OneToMany(mappedBy = "caverna", fetch = FetchType.LAZY)
    private List<Expedicao> expedicoes = new ArrayList<>();

    @OneToMany(mappedBy = "caverna", fetch = FetchType.LAZY)
    private List<SetorPesquisa> setores = new ArrayList<>();
}
