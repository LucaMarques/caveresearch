package br.edu.ifpb.caveresearch.entity;

import br.edu.ifpb.caveresearch.enums.SituacaoOperacionalEquipamento;
import br.edu.ifpb.caveresearch.enums.TipoEquipamento;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "tb_equipamento")
public class Equipamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_equipamento", nullable = false)
    private Long id;

    @Column(name = "codigo_patrimonial", nullable = false)
    private String codigoPatrimonial;

    @Column(name = "nome_equipamento", nullable = false, length = 255)
    private String nomeEquipamento;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_equipamento", nullable = false)
    private TipoEquipamento tipoEquipamento;

    @Column(name = "fabricante", nullable = false, length = 100)
    private String fabricante;

    @Column(name = "valor_aquisicao", nullable = false)
    private BigDecimal valorAquisicao;

    @Column(name = "data_compra", nullable = false)
    private LocalDate dataCompra;

    @Column(name = "data_ultima_manutencao")
    private LocalDate dataUltimaManutencao;

    @Enumerated(EnumType.STRING)
    @Column(name = "situacao", nullable = false)
    private SituacaoOperacionalEquipamento situacao;

    @Column(name = "exige_calibracao", nullable = false)
    private Boolean exigeCalibracao;

    @OneToMany(
            mappedBy = "equipamento",
            fetch = FetchType.LAZY
    )
    private List<MovimentacaoEquipamento> movimentacoes = new ArrayList<>();
}