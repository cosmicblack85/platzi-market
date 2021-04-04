package com.platzi.market.persistence.mapper;

import com.platzi.market.domain.PurchaseItem;
import com.platzi.market.persistence.entity.ComprasProducto;
import com.platzi.market.persistence.entity.ComprasProductoPK;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring", uses = {ProductMapper.class})
public interface PurchaseItemMapper {

    @Mappings({
            @Mapping(source = "id.idProducto", target = "productId"),
            @Mapping(source = "cantidad", target = "quantity"),
            //Se puede omitir el total porque se llaman igual
            //@Mapping(source = "total", target = "total"),
            @Mapping(source = "estado", target = "active"),
    })
    PurchaseItem toPurchaseItem(ComprasProducto producto);

    /* Aunque ignoremos el Producto, tenemos que usar el ProductMapper*/
    @InheritInverseConfiguration
    @Mappings({
            @Mapping(target = "id.idCompra", ignore = true),
            @Mapping(target = "compra", ignore = true),
            @Mapping(target = "producto", ignore = true),
    })

    ComprasProducto toComprasProducto(PurchaseItem product);
}
