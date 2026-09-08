package br.edu.ifpb.caveresearch.entity;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "tb_plano_seguranca")
public class PlanoSeguranca {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_plano_seguranca", nullable = false)
    private Long idPlanoSeguranca;

    @Column(name = "procedimentos_evacuacao", nullable = false, length = 500)
    private String procedimentosEvacuacao;

    @Column(name = "ponto_externo_encontro", nullable = false, length = 100)
    private String pontoExternoEncontro;

    @Column(name = "tempo_maximo_sem_comunicacao_horas", nullable = false)
    private Integer tempoMaximoSemComunicacaoHoras;

    @Column(name = "telefone_emergencia", nullable = false, length = 20)
    private String telefoneEmergencia;

    @Column(name = "necessita_equipe_medica", nullable = false)
    private boolean necessitaEquipeMedica;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(name = "mapa_rota")
    private byte[] mapaRota;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_expedicao", nullable = false, unique = true)
    private Expedicao expedicao;
}
