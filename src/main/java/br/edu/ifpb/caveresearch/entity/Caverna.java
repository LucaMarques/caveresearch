package br.edu.ifpb.caveresearch.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
@Table(name = "tb_caverna")
public class Caverna {
    // terminar implementacao

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_caverna", nullable = false)
    private Long idCaverna;

    @OneToMany(mappedBy = "caverna", fetch = FetchType.LAZY)
    private List<Expedicao> expedicoes = new ArrayList<>();

    @OneToMany(mappedBy = "caverna", fetch = FetchType.LAZY)
    private List<SetorPesquisa> setores = new ArrayList<>();
}
