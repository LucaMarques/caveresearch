package br.edu.ifpb.caveresearch.embeddable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.math.BigDecimal;

@Embeddable
public class Localizacao {

    @Column(name = "latitude", nullable = false)
    private BigDecimal latitude;

    @Column(name = "longitude", nullable = false)
    private BigDecimal longitude;

   @Column(name = "datum_geodesico", nullable = false)
    private String satumGeodesico;

}
