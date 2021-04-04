package com.platzi.market.persistence.crud;

import com.platzi.market.persistence.entity.Producto;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface ProductoCrudRepository extends CrudRepository<Producto, Integer> {

    /* Método para recuperar una lista de productos que correspondan a una categoría específica*/
    List<Producto> findByIdCategoriaOrderByNombreAsc(int idCategoria);

    /* Recuperar los productos escasos*/
    Optional<List<Producto>> findByCantidadStockLessThanAndEstado(int cantidadStock, boolean estado);

    /* Buscar un producto en particular */

}