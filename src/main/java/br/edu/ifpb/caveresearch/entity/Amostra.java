package br.edu.ifpb.caveresearch.entity;

import br.edu.ifpb.caveresearch.enums.CategoriaAmostra;
import br.edu.ifpb.caveresearch.enums.CondicaoConservacao;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "tb_amostra")
public class Amostra {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_amostra")
    private Long id;

    @Column(name = "codigo_campo", nullable = false, unique = true, length = 50)
    private String codigoCampo;

    @Enumerated(EnumType.STRING)
    @Column(name = "categoria", nullable = false)
    private CategoriaAmostra categoria;

    @Column(name = "massa_volume", nullable = false, precision = 10, scale = 3)
    private BigDecimal massaOuVolume;

    @Column(name = "unidade_medida", nullable = false, length = 20)
    private String unidadeMedida;

    @Column(name = "data_acondicionamento", nullable = false)
    private LocalDate dataAcondicionamento;

    @Enumerated(EnumType.STRING)
    @Column(name = "condicao_conservacao", nullable = false)
    private CondicaoConservacao condicaoConservacao;

    @Column(name = "material_perigoso", nullable = false)
    private boolean materialPerigoso;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(name = "fotografia")
    private byte[] fotografia;

    @Column(name = "observacoes", length = 500)
    private String observacoes;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_coleta", nullable = false)
    private ColetaCientifica coleta;
}