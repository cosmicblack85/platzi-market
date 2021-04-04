package com.platzi.market.persistence.entity;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;


@Entity
@Table(name = "compras_productos")
public class ComprasProducto {

    /* Como tiene una PK Compuesta. Estas PK se tienen que colocar en otra clase donde se coloca
    * la anotación @Embeddable y se colocan los atributos con sus anotaciones correspondientes */

    @EmbeddedId
    private ComprasProductoPK id;

    private Integer cantidad;

    private Double total;

    private Boolean estado;
}
