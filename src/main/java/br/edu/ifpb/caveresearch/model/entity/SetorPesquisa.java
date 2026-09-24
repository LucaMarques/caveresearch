package br.edu.ifpb.caveresearch.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "tb_setor_pesquisa")
public class SetorPesquisa {
    // terminar implementacao

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_setor", nullable = false)
    private Long idSetor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_caverna", nullable = false)
    private Caverna caverna;

    @ManyToMany(mappedBy = "setores", fetch = FetchType.LAZY)
    private List<Expedicao> expedicoes = new ArrayList<>();

    @OneToMany(mappedBy = "setor", fetch = FetchType.LAZY)
    private List<ColetaCientifica> coletas = new ArrayList<>();
}
