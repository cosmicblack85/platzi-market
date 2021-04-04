package com.platzi.market.persistence.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "categorias")
public class Categoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_categoria")
    private Integer idCategoria;

    private String descripcion;

    private Boolean estado;

    /* Como la relación con Producto es a muchos, tiene que ser un atributo de tipo Lista.
    * El parámetro mappedBy es quien respalda esta relación con este nuevo atributo creado. En Producto
    * agregamos el objeto tipo Categoria llamado categoria. Este último será el atributo que respalda
    * la relación */
    @OneToMany(mappedBy = "categoria")
    private List<Producto> productos;

    /* GETTERS & SETTERS*/

    public Integer getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(Integer idCategoria) {
        this.idCategoria = idCategoria;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }
}
