package com.jinternals.demo.infrastructure.adapter.in.rest.mappers;

import com.jinternals.demo.domain.model.Currency;
import com.jinternals.demo.domain.model.LineItem;
import com.jinternals.demo.domain.model.ProductId;
import com.jinternals.demo.domain.model.Unit;
import com.jinternals.demo.infrastructure.adapter.in.rest.OrderedLineItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", imports = {Unit.class, Currency.class})
public interface LineItemDTOMapper {

    public static final LineItemDTOMapper INSTANCE = Mappers.getMapper(LineItemDTOMapper.class);

    @Mapping(source = "productId", target = "productId", qualifiedByName = "mapStringToProductId")
    @Mapping(target = "quantity", expression = "java(new Quantity(dto.getQuantity(), Unit.valueOf(dto.getQuantityUnit())))")
    @Mapping(target = "price", expression = "java(new Amount(dto.getPrice(), Currency.valueOf(dto.getPriceCurrency())))")
    LineItem toDomain(OrderedLineItem dto);


    @Named("mapStringToProductId")
    static ProductId mapStringToProductId(String id) {
        return new ProductId(id);
    }

}
