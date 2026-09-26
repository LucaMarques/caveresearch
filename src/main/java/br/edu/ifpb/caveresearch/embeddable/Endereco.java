package br.edu.ifpb.caveresearch.embeddable;

import jakarta.persistence.*;
import lombok.*;

@Embeddable
@Setter
@Getter
@NoArgsConstructor
public class Endereco {
    @Column(name = "logradouro", nullable = false, length = 120)
    private String logradouro;

    @Column(name = "numero", nullable = false, length = 10)
    private String numero;

    @Column(name = "complemento", length = 100)
    private String complemento;

    @Column(name = "bairro", nullable = false, length = 80)
    private String bairro;

    @Column(name = "cidade", nullable = false, length = 80)
    private String cidade;

    @Column(name = "uf", nullable = false, length = 2)
    private String unidadeFederativa;

    @Column(name = "cep", nullable = false, length = 8)
    private String cep;
}
