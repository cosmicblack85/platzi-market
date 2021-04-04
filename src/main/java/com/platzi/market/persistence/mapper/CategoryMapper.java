package com.platzi.market.persistence.mapper;

import com.platzi.market.domain.Category;
import com.platzi.market.persistence.entity.Categoria;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    @Mappings({
            @Mapping(source = "idCategoria", target = "categoryId"),
            @Mapping(source = "descripcion", target = "category"),
            @Mapping(source = "estado", target = "active"),
    })
    Category toCategory(Categoria categoria);

    /* Con la anotación @InheritInverseConfiguration hacemos la conversión inversa, sin necesidad de voler
    * a colocar los @Mapping
    * Con @Mapping(target = "productos", ignore = true) indicamos que ingnore el mapeo del atributo productos
    * de Categoria porque en el Domino, Category no tiene este elemento.*/

    @InheritInverseConfiguration
    @Mapping(target = "productos", ignore = true)
    Categoria toCategoria(Category category);
}